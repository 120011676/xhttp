# xhttp
一个java http操作工具

# releases
[v1.0.0](https://github.com/120011676/xhttp/releases)

# example

```java
String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
String html = XHttp.url("https://www.baidu.com/s")
        .userAgent(userAgent)
        .data("wd", "ss&sc").get().dataToString();
System.out.println(html)
```
