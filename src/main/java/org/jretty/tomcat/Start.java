package org.jretty.tomcat;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.Tomcat.DefaultWebXmlListener;

/**
 *
 * @author hengyunabc
 * 
 * 把应用原来的src/main/webapp/WEB-INF 移动到 src/main/resources/WEB-INF下，
 * 把在src/main/webapp下面的所有文件移动到 src/main/META-INF/resources目录下
 *
 * https://github.com/hengyunabc/executable-embeded-tomcat-sample
 * 
 * java -jar target/executable-embeded-tomcat-jar-0.0.1-SNAPSHOT.jar
 */
class Start {
    
    public static void main(String[] args) throws ServletException, LifecycleException, IOException {
        if (args.length > 0) {
            Shutdown.shutdown();
            return;
        }
        TomcatConfig cfg = new TomcatConfig();
        cfg.setContextDocBase("D:/1sync/zoa-static");
        cfg.setContextPath("/zoa");
        cfg.setEnableSsi(true);
        startTomcat(cfg);
    }
    
    public static void startTomcat(TomcatConfig cfg) 
                    throws ServletException, LifecycleException, IOException {

        String tomcatBaseDir = cfg.getTomcatBaseDir();
        if (tomcatBaseDir == null) {
            tomcatBaseDir = createTempDir("tomcat_embed_works_tmpdir").getAbsolutePath();
        }
        System.out.println(cfg.toString());
        System.out.println("start to start: " + cfg.getHostName() + ":" + cfg.getPort() + cfg.getContextPath());
        System.out.println("tomcatBaseDir: " + tomcatBaseDir);
        System.out.println("contextDocBase: " + cfg.getContextDocBase());
        
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tomcatBaseDir);

        tomcat.setPort(cfg.getPort());
        tomcat.setHostname(cfg.getHostName());
        
        // 设置URI编码支持中文
        tomcat.getConnector().setURIEncoding("UTF-8");
        
        Host host = tomcat.getHost();
        Context context = tomcat.addWebapp(host, cfg.getContextPath(), 
                cfg.getContextDocBase(), (LifecycleListener) new EmbededContextConfig());
        
        if (!cfg.getEnableJsp()) {
            addMyWebXmlListener(context);
        }
        
        context.setJarScanner(new EmbededStandardJarScanner());

        ClassLoader classLoader = Start.class.getClassLoader();
        context.setParentClassLoader(classLoader);

        // context load WEB-INF/web.xml from classpath
        context.addLifecycleListener(new WebXmlMountListener());
        
        if(cfg.getEnableSsi()) {
            // 开启ssi
            context.setPrivileged(true);
            Wrapper servlet = Tomcat.addServlet(context, "ssi", "org.apache.catalina.ssi.SSIServlet");
            servlet.addInitParameter("buffered", "1");
            servlet.addInitParameter("inputEncoding", "UTF-8");
            servlet.addInitParameter("outputEncoding", "UTF-8");
            servlet.addInitParameter("debug", "0");
            servlet.addInitParameter("expires", "666");
            servlet.addInitParameter("isVirtualWebappRelative", "4");
            servlet.setLoadOnStartup(4);
            servlet.setOverridable(true);
            
            // Servlet mappings
            context.addServletMappingDecoded("*.html", "ssi");
            context.addServletMappingDecoded("*.shtml", "ssi");
        }

        tomcat.start();
        // 注册关闭端口以进行关闭
        // 可以通过Socket关闭tomcat： telnet 127.0.0.1 8005，输入SHUTDOWN字符串
        tomcat.getServer().setPort(cfg.getShutdownPort()); 
        tomcat.getServer().await();
        System.out.println("正在关闭tomcat，shutdown......");
//        
//        sleep(100L);
//        System.out.println("删除tomcat临时路径......");
//        try {
//            deleteAllFilesOfDir(tomcatBaseDirFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void addMyWebXmlListener(Context context) {
        LifecycleListener tmplf = null;
        for (LifecycleListener lfl : context.findLifecycleListeners()) {
            if (lfl instanceof DefaultWebXmlListener) {
                tmplf = lfl;
                break;
            }
        }
        if (tmplf != null) {
            context.removeLifecycleListener(tmplf);
        }
        context.addLifecycleListener(new MyWebXmlListener());
    }
    
    public static class MyWebXmlListener implements LifecycleListener {
        @Override
        public void lifecycleEvent(LifecycleEvent event) {
            if (Lifecycle.BEFORE_START_EVENT.equals(event.getType())) {
                Context context = (Context) event.getLifecycle();
                Tomcat.initWebappDefaults(context);
                // 去掉JSP
                context.removeServletMapping("*.jsp");
                context.removeServletMapping("*.jspx");
                context.removeChild(context.findChild("jsp"));
            }
        }
    }
    
    public static File createTempDir(String folderName) {
        File tmpdir = new File(System.getProperty("java.io.tmpdir"));
        tmpdir = new File(tmpdir, folderName);
        if (!tmpdir.exists()) {
            tmpdir.mkdir();
        }
        return tmpdir;
    }
    
    public static File createTempDir(String prefix, int port) throws IOException {
        File tempDir = File.createTempFile(prefix + ".", "." + port);
        tempDir.delete();
        tempDir.mkdir();
        tempDir.deleteOnExit();
        return tempDir;
    }
    
    static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteAllFilesOfDir(File path) throws IOException {
        System.out.println(path.getPath());
        if (!path.exists())
            return;
        if (path.isFile()) {
            java.nio.file.Files.delete(path.toPath());
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        java.nio.file.Files.delete(path.toPath());
    }
}