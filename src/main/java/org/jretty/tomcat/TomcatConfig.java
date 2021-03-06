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
    private String docBase;
    private String tomcatBaseDir;
    private Integer shutdownPort = 8005;
    private Boolean enableSsi = false;
    private Boolean enableJsp = false;
    /**
     * Maximum amount of worker threads.
     */
    private int maxThreads = 0;
    /**
     * Minimum amount of worker threads. if not set, default value is 10
     */
    private int minSpareThreads = 0;
    /**
     * When Tomcat expects data from the client, this is the time Tomcat will
     * wait for that data to arrive before closing the connection.
     */
    private int connectionTimeout = 0;
    /**
     * Maximum number of connections that the server will accept and process at any
     * given time. Once the limit has been reached, the operating system may still
     * accept connections based on the "acceptCount" property.
     */
    private int maxConnections = 0;

    /**
     * Maximum queue length for incoming connection requests when all possible request
     * processing threads are in use.
     */
    private int acceptCount = 0;

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

    public String getDocBase() {
        return docBase;
    }

    public void setDocBase(String docBase) {
        this.docBase = docBase;
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
    
    public int getMaxThreads() {
        return this.maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMinSpareThreads() {
        return this.minSpareThreads;
    }

    public void setMinSpareThreads(int minSpareThreads) {
        this.minSpareThreads = minSpareThreads;
    }
    
    /**
     * When Tomcat expects data from the client, this is the time Tomcat will
     * wait for that data to arrive before closing the connection.
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }
    
    public void setConnectionTimeout(int timeout) {
        this.connectionTimeout = timeout;
    }
    
    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getAcceptCount() {
        return this.acceptCount;
    }

    public void setAcceptCount(int acceptCount) {
        this.acceptCount = acceptCount;
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
        builder.append(", docBase=");
        builder.append(docBase);
        builder.append(", tomcatBaseDir=");
        builder.append(tomcatBaseDir);
        builder.append(", shutdownPort=");
        builder.append(shutdownPort);
        builder.append(", enableSsi=");
        builder.append(enableSsi);
        builder.append(", enableJsp=");
        builder.append(enableJsp);
        builder.append(", minSpareThreads=");
        builder.append(minSpareThreads);
        builder.append(", maxThreads=");
        builder.append(maxThreads);
        builder.append(", connectionTimeout=");
        builder.append(connectionTimeout);
        builder.append(", maxConnections=");
        builder.append(maxConnections);
        builder.append(", acceptCount=");
        builder.append(acceptCount);
        builder.append("]");
        return builder.toString();
    }
}
