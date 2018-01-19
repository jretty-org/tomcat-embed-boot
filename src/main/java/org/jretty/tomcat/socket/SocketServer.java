package org.jretty.tomcat.socket;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * a TCP socket wrapper on server side, easy to use.
 * 
 * @author zollty
 * @since 2017-1-12
 */
public abstract class SocketServer extends Thread implements Closeable {
    private static final int DEFAULT_READ_TIMEOUT = 5000;

    private ServerSocket serverSocket;

    private volatile boolean running;
    private CountDownLatch latch;

    public abstract String ack(String msg);

    public abstract void handleMsg(String msg);

    @Override
    public void run() {
        // 接收从客户端发送过来的数据
        System.out.println("SocketServer is on，waiting for client to send data......");
        running = true;
        latch = new CountDownLatch(1);
        while (running) {
            try {
                new ClientSocketHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                if (!"Socket closed".equals(e.getMessage()))
                    e.printStackTrace();
            }
        }
        latch.countDown();
    }

    @Override
    public void close() throws IOException {
        System.out.println("Start to shutdown SocketServer......");
        running = false;
        try {
            latch.await(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        System.out.println("SocketServer closed.");
    }

    private class ClientSocketHandler extends Thread {
        private Socket socket;

        private ClientSocketHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                String recieveContent = socketRead(socket);
                socket.shutdownInput();
                System.out.println(System.currentTimeMillis() + " recieve msg: " + recieveContent);
                String responseStr;
                if ((responseStr = ack(recieveContent)) != null) {
                    response(responseStr, socket);
                }

                handleMsg(recieveContent);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null && !socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public String socketRead(Socket socket) throws IOException {
            socket.setSoTimeout(DEFAULT_READ_TIMEOUT);
            Reader reader = new InputStreamReader(socket.getInputStream());
            return SocketTools.copyToString(reader);
        }

        void response(String responseStr, Socket socket) throws IOException {
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            out.write(responseStr.getBytes());
            out.flush();
            socket.shutdownOutput();
        }
    }

    public void setDefaultServerSocket(int port) throws IOException {
        this.setServerSocket(new ServerSocket(port));
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

}