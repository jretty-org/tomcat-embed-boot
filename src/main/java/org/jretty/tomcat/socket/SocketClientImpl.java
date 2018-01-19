package org.jretty.tomcat.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * a TCP socket wrapper on client side, easy to use.
 * 
 * @author zollty
 * @since 2017-1-12
 */
public class SocketClientImpl implements SocketClient {
    private static final int DEFAULT_READ_TIMEOUT = 5000; // 设置接收数据的超时时间

    private String distHost;
    private int distPort;

    @Override
    public String sendQuiet(String msg) {
        try {
            return send(msg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String send(String msg) throws IOException {
        return send(msg, distHost, distPort);
    }

    @Override
    public String send(String msg, String host, int port) throws IOException {
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            write(msg, socket);
            
            String ackMsg = socketRead(socket);
            socket.shutdownInput();
            System.out.println("[" + System.currentTimeMillis() + "] Reply from server [" + host + ":" + port + "] ");
            System.out.println("\t" + ackMsg);
            return ackMsg;

        } finally {
            if (socket != null)
                socket.close();
        }

    }

    public String socketRead(Socket socket) throws IOException {
        socket.setSoTimeout(DEFAULT_READ_TIMEOUT);
        Reader reader = new InputStreamReader(socket.getInputStream());
        return SocketTools.copyToString(reader);
    }

    void write(String msg, Socket socket) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        out.write(msg.getBytes());
        out.flush();
        socket.shutdownOutput();
    }

    public int getDistPort() {
        return distPort;
    }

    public void setDistPort(int distPort) {
        this.distPort = distPort;
    }

    public String getDistHost() {
        return distHost;
    }

    public void setDistHost(String distHost) throws UnknownHostException {
        this.distHost = distHost;
    }

}