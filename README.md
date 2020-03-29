#阿里云DDNS动态域名服务

- starter形式，使用者无需编码，启动springboot直接可用。简单方便。
- 使用https://jsonip.com/获取本地IP
- 基于阿里云接口版本
```xml
        <!-- https://mvnrepository.com/artifact/com.aliyun/aliyun-java-sdk-core -->
        <dependency>
            <groupId>com.aliyun</groupId>
            <artifactId>aliyun-java-sdk-core</artifactId>
            <version>4.4.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.aliyun/aliyun-java-sdk-alidns -->
        <dependency>
            <groupId>com.aliyun</groupId>
            <artifactId>aliyun-java-sdk-alidns</artifactId>
            <version>2.0.10</version>
        </dependency>
```

##使用方法
1. 下载本项目，直接打包 mvn install 到本地仓库
2. 创建你自己的springboot应用
3. 在maven中增加
```xml
        <dependency>
            <groupId>cn.andyjee.jnas</groupId>
            <artifactId>ddns-aliyun-spring-boot-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```
4. 在application.yml中增加配置：
```yaml
jnas:
  ddns:
    #开启ddns服务
    enabled: true
    #阿里云区域
    region-id: cn-beijing
    #访问access-key ID
    access-key-id: ABCDEFG
    #访问access-key密码
    access-key-secret: HIJKLMN
    #在阿里云申请的域名
    domain-name: yourdomain.cn
    #刷新时长
    interval-seconds: 600
```