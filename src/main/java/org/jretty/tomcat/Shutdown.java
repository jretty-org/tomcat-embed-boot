package org.jretty.tomcat;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.jretty.tomcat.socket.SocketClient;
import org.jretty.tomcat.socket.SocketClientImpl;

/**
 * @see TomcatManage
 */
class Shutdown {

    public static void main(String[] args) {
        shutdown();
    }

    public static void shutdown() {
        shutdown(null, null);
    }

    public static void shutdown(String serverHost, Integer serverPort) {
        if (serverHost == null) {
            serverHost = "localhost";
        }
        if (serverPort == null) {
            serverPort = 8005;
        }
        SocketClientImpl sc = new SocketClientImpl();
        try {
            sc.setDistHost(serverHost);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println("localIP: " + getSpecialHostAddress(sc.getDistHost()).getHostAddress());
        sc.setDistPort(serverPort);

        SocketClient client = sc;

        client.sendQuiet("SHUTDOWN");
    }

    private static InetAddress getSpecialHostAddress(String hostName) {
        try {
            return InetAddress.getByName(hostName);
        } catch (UnknownHostException e) {
            return null;
        }
    }

}
