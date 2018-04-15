/**
 * 
 */
package org.jretty.tomcat;

/**
 * Tomcat Config
 * 
 * @author zollty
 * @since 2018-01-19
 */
public class TomcatConfig {
    private String hostName = "localhost";
    private Integer port = 8080;
    private String contextPath = "/";
    private String contextDocBase;
    private String tomcatBaseDir;
    private Integer shutdownPort = 8005;
    private Boolean enableSsi = false;
    private Boolean enableJsp = false;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContextDocBase() {
        return contextDocBase;
    }

    public void setContextDocBase(String contextDocBase) {
        this.contextDocBase = contextDocBase;
    }

    public String getTomcatBaseDir() {
        return tomcatBaseDir;
    }

    /**
     * Tomcat needs a directory for temp files. This should be the
     * first method called.
     *
     * <p>
     * By default, if this method is not called, we use:
     * <ul>
     *  <li>system properties - catalina.base, catalina.home</li>
     *  <li>$PWD/tomcat.$PORT</li>
     * </ul>
     * (/tmp doesn't seem a good choice for security).
     *
     * <p>
     * TODO: disable work dir if not needed ( no jsp, etc ).
     */
    public void setTomcatBaseDir(String tomcatBaseDir) {
        this.tomcatBaseDir = tomcatBaseDir;
    }

    public Integer getShutdownPort() {
        return shutdownPort;
    }

    public void setShutdownPort(Integer shutdownPort) {
        this.shutdownPort = shutdownPort;
    }

    public Boolean getEnableSsi() {
        return enableSsi;
    }

    public void setEnableSsi(Boolean enableSsi) {
        this.enableSsi = enableSsi;
    }

    public Boolean getEnableJsp() {
        return enableJsp;
    }

    public void setEnableJsp(Boolean enableJsp) {
        this.enableJsp = enableJsp;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TomcatConfig [hostName=");
        builder.append(hostName);
        builder.append(", port=");
        builder.append(port);
        builder.append(", contextPath=");
        builder.append(contextPath);
        builder.append(", contextDocBase=");
        builder.append(contextDocBase);
        builder.append(", tomcatBaseDir=");
        builder.append(tomcatBaseDir);
        builder.append(", shutdownPort=");
        builder.append(shutdownPort);
        builder.append(", enableSsi=");
        builder.append(enableSsi);
        builder.append(", enableJsp=");
        builder.append(enableJsp);
        builder.append("]");
        return builder.toString();
    }
}
