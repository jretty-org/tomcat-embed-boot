package org.jretty.tomcat.socket;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

class SocketTools {
    
    private SocketTools() {
    }
    
    static String copyToString(Reader in) throws IOException {
        StringWriter out = new StringWriter();
        clone(in, out);
        return out.toString();
    }

    static int clone(Reader in, Writer out) throws IOException {
        try {
            int byteCount = 0;
            char[] buffer = new char[4096];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            return byteCount;
        } finally {
            try {
                out.flush();
            } catch (IOException e) {
            }
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }

}
