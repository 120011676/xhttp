# xhttp
一个java http操作工具

# releases

###### java 7:
```xml
<dependency>
    <groupId>com.github.120011676</groupId>
    <artifactId>xhttp-jdk_7</artifactId>
    <version>1.1.0</version>
</dependency>
```
###### java 5:
```xml
<dependency>
    <groupId>com.github.120011676</groupId>
    <artifactId>xhttp-jdk_5</artifactId>
    <version>1.1.0</version>
</dependency>
```

###### java 1.4:
```xml
<dependency>
    <groupId>com.github.120011676</groupId>
    <artifactId>xhttp-jdk_1.4</artifactId>
    <version>1.1.0</version>
</dependency>
```

# example

```java
String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
String html = XHttp.url("https://www.baidu.com/s")
        .userAgent(userAgent)
        .data("wd", "ss&sc").get().dataToString();
System.out.println(html);
```
