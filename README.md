# tomcat-embed-boot
like SpringBoot, start tomcat using Java Main method, and support jar file.

仿SpringBoot的启动方式

把应用原来的src/main/webapp/WEB-INF 
移动到 src/main/resources/WEB-INF下，

把在src/main/webapp下面的所有文件
移动到 src/main/META-INF/resources目录下。



### Usage (Start tomcat & shutdown):

```xml
<!-- start tomcat-embed server -->
<dependency>
  <groupId>org.jretty</groupId>
  <artifactId>tomcat-embed-boot</artifactId>
  <version>最新版本</version>
</dependency>

<dependency>
  <groupId>org.apache.tomcat.embed</groupId>
  <artifactId>tomcat-embed-core</artifactId>
  <version>${tomcat.version}</version>
</dependency>
<dependency>
  <groupId>org.apache.tomcat.embed</groupId>
  <artifactId>tomcat-embed-logging-juli</artifactId>
  <version>${tomcat.version}</version>
</dependency>
<!-- Optional -->
<!-- 
<dependency>
  <groupId>org.apache.tomcat.embed</groupId>
  <artifactId>tomcat-embed-el</artifactId>
  <version>${tomcat.version}</version>
</dependency>
<dependency>
  <groupId>org.apache.tomcat.embed</groupId>
  <artifactId>tomcat-embed-websocket</artifactId>
  <version>${tomcat.version}</version>
</dependency>
<dependency>
  <groupId>org.apache.tomcat.embed</groupId>
  <artifactId>tomcat-embed-jasper</artifactId>
  <version>${tomcat.version}</version>
</dependency> -->
```

```java
public static void main(String[] args) {
    if (args.length > 0) {
        TomcatManage.shutdown();
        return;
    }
    TomcatConfig cfg = new TomcatConfig();
    cfg.setContextDocBase("D:/1sync/zoa-html");
    cfg.setContextPath("/zoa");
    cfg.setEnableSsi(true);
    TomcatManage.startTomcat(cfg);
}
```

### 也可以打成Jar包执行
java -jar your-jar-1.0.jar

将应用打成一个fat jar的方式：

```xml
<plugin>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-maven-plugin</artifactId>
  <version>1.3.3.RELEASE</version>
  <configuration>
    <mainClass>com.demo.proj.Main</mainClass>
  </configuration>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>repackage</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```
