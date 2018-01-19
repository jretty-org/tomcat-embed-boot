package org.jretty.tomcat.socket;

import java.io.IOException;

/**
 * a TCP socket wrapper on client side, easy to use.
 * 
 * @author zollty
 * @since 2017-1-12
 */
public interface SocketClient {

    String sendQuiet(String msg);

    String send(String msg) throws IOException;

    String send(String msg, String host, int port) throws IOException;

}