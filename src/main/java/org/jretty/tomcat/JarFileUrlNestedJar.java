/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jretty.tomcat;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.tomcat.util.scan.AbstractInputStreamJar;
import org.apache.tomcat.util.scan.NonClosingJarInputStream;

/**
 * Implementation of {@link org.apache.tomcat.Jar} that is optimised for file
 * based JAR URLs that refer to a JAR file nested inside a WAR
 * (e.g URLs of the form jar:file: ... .war!/ ... .jar).
 */
public class JarFileUrlNestedJar extends AbstractInputStreamJar {

    private final JarFile warFile;
    private final JarEntry jarEntry;

    public JarFileUrlNestedJar(URL url) throws IOException {
        super(url);
        JarURLConnection jarConn = (JarURLConnection) url.openConnection();
        jarConn.setUseCaches(false);
        warFile = jarConn.getJarFile();

        String urlAsString = url.toString();
        int pathStart = urlAsString.indexOf("!/") + 2;
        String jarPath = urlAsString.substring(pathStart);
        System.out.println("==== " + jarPath);
        jarEntry = warFile.getJarEntry(jarPath);
        Enumeration<JarEntry> ens = warFile.entries();
        System.out.println("----------------");
        while(ens.hasMoreElements()) {
            JarEntry e = ens.nextElement();
            System.out.println(e.getName());
        }
        System.out.println("-------END---------");
    }


    @Override
    public void close() {
        closeStream();
        if (warFile != null) {
            try {
                warFile.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }


    @Override
    protected NonClosingJarInputStream createJarInputStream() throws IOException {
        return new NonClosingJarInputStream(warFile.getInputStream(jarEntry));
    }
}
