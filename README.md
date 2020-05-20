## 快速启动文档

### 环境准备

1. JDK11
2. Maven3.0+
3. Mysql5.7+
4. ElasticSearch6.8+
5. Redis

上述环境请自行准备并测试正常，后面3个推荐使用docker部署，这样非常的简便快捷。

### 修改配置文件

1. 在counsel\src\main\resources\application.yamll文件中修改mysql的url、username、password等配置项。变更为属于你自己的本地环境。MySQL的url连接字符串需要注意：在后面添加参数serverTimezone=CTT，否则时间可能会不正确。

   示例：

   ```
   url: jdbc:mysql://localhost:3306/PsySystem?serverTimezone=CTT
   ```

   

2. 在counsel\src\main\resources\application-master.yaml 中配置redis和Elasticsearch相关配置，若本地环境没有设置密码可以去除密码配置项。

### Maven打包

进入目录counsel，输入命令

```
mvn packge -Dmaven.test.skip=true
```

### 启动Jar包

进入目录counsel/target/。输入以下命令

```
java -jar counsel*.jar --jasypt.encryptor.password=自定义字符串
```

上面添加的参数是用与配置文件的加密，在添加了ENC()标识的内容即为加密内容，通过自己设定的密钥进行加密。如果不需要可以去掉ENC()的标识。

如果需要自己加密配置文件，在配置密钥后，到以下目录运行自己需要加密的配置GraduationDesign\counsel\src\test\java\org\gdou\counsel\utils\EncryptTest.java

