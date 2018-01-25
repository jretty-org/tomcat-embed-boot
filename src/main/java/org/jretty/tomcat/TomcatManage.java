/**
 * 
 */
package org.jretty.tomcat;

import java.io.File;

/**
 * tomcat management.
 * 
 * @author zollty
 * @since 2018-01-19
 */
public class TomcatManage {
    
    public static void startTomcat(TomcatConfig cfg) {
        try {
            Start.startTomcat(cfg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        Shutdown.shutdown();
    }
    
    public static void shutdown(String serverHost, int serverPort) {
        Shutdown.shutdown(serverHost, serverPort);
    }
    
    public static File createTempDir(String folderName) {
        return Start.createTempDir(folderName);
    }
}
