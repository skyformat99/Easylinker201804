# EasyLinker-部署和使用文档
## Easylinker地址:http://www.easylinker.xyz/(这个是项目地址，同步更新)
> # 一·前言  
>>我在大一（2014年的时候）第一次接触树莓派这个神奇的东西。当时B+刚出来，省了一周的伙食，买了一个B+板子.但是一直没有机会玩。一因为我不会玩Linux，二因为没有配件，所以一直吃灰，偶尔拿出来把玩一番。
时间晃得很快，一转眼就大三了，有一天我突然发现了我放在抽屉里两年多的B+树莓派。突然来了兴趣，赶紧上电，刷系统，这时候，我已经算是对Linux比较熟悉了。Putty连接上去，熟练的登陆。然后就一发不可收拾，玩的不亦乐乎，学会了一些嵌入式开发，甚至为了玩树莓派，专门学了Arduino.刚开始玩的是树莓派简单的GPIO控制，后来就不满足了，逐渐开始做一些远程控制的东西。再后来，就懂了这个技术叫物联网，于是就开始补物联网相关的东西，大三下直到大四上，都在学这些。刚开始的时候，用的51单片机，做了简单的串口收发案例，但是发现如果用了树莓派以后，又要重新设计一套传输方案，很麻烦。于是我就百度寻找解决方法，了解了大家玩的比较多的Yeelink平台.但是很快发现了弊端：Yeelink是基于HTTP协议的，数据传输，甚至接受，都是HTTP轮询，这是非常不科学的设计，这样会导致不能实时控制连接设备而且极度耗费低端硬件的资源。我想找一个免费的，可以统一数据传输形式，而且免费的平台，但是始终找不到满意的.于是就开始设计EasyLinker.期间写出了第一个版本,结果因为我技术不扎实,设计出问题,所以抛弃,后来又出了版本2,这个版本算是比较完整的一个,但是还是问题很多,都是因为前期没有设计好结构,所以留下很多问题.经过了半年以后,我对技术掌握的也比较熟悉了,所以现在又把版本2重新整理了一下,优化了结构,使用起来更加简单.  
>>为了方便大家学习，我们准备了一些学习资料:

 >>- 物联网系列课程:http://www.baidu.com
 >>- 学习文档:http://www.baidu.com
 >>- EasyLinkerAPP地址:http://www.baidu.com
 >>- 定制版EMQ下载地址:http://www.baidu.com
 >>- EasyLinker架构图和应用场景讲解:
 >>- 特别录制了一个部署安装小教程视频:http://www.baidu.com
### 附1:EasyLinker架构图:![http://www.easylinker.xyz/upload/userfile/1/277497e7a3a269325be23b0cfe835ff5.png][1]

 
> # 二·EasyLinker使用场景  
> ## 1.学习服务器开发框架
>>这个系统用了当前最流行的WEB服务框架-SpringBoot2.0和ORM框架Hibernate5,数据库则使用了MySQL5,整套系统相对来说很简单,业务逻辑分层清晰,很适合WEB开发者和物联网爱好者学习研究.同时MQ消息端采用了百万级消息服务器-EMQ作为支持,保证了系统的稳定性.
> ## 2.批量管理物联网设备  
>>如果你是一个物联网设备生产厂家,需要将设备进行网络对接,那么这个项目可以很好的帮助您管理成千上万的设备.同时支持批量生产,精确绑定,导出报表登常见功能.
> ## 3.构建家庭物联网服务器  
>>如果你是一个极客玩家,喜欢研究一些新技术,喜欢把自己的家打造的更智能化,这个系统也可以帮助您快速构建一个远程控制智能物联网系统,随时构建一个智能咖啡机,或者是智能家庭数据采集器?反正玩法很多,等你去开发.
> ## 4.数据传输存储平台  
>>集成了又拍云作为大批量数据保存服务器,让设备产生的数据放心的保存在云盘中,随时拿出来进行研究分析,安全可靠.
> # 三·EasyLinker基本结构  
> ## 1.整体架构  
>>本项目,使用了Java作为业务逻辑控制层,SpringBoot2.0作为基础框架,EMQ作为数据传输Broker,架构简单清晰,易于掌握和架设.
> ## 2.组件功能  
>>客户端-----|发送数据|---->Easylinker--|业务逻辑处理|--->EMQ
>>--|路由选择|-->返回给Easylinker--->客户端
> ## 3.组件原理  
>>Easylinker:处理所有的业务逻辑,包括设备管理,用户管理等等.  
>>EMQ:负责所有的消息路由转发.
> # 四·开始使用  
> ## 1.下载源代码编译  
>>在github上下载相关源代码，用maven编译成jar包,关键命令:```maven install pom.xml```
> ## 2.直接下载编译好的包  
>>同时也提供了编译好的文件，可以直接下载部署,地址:```www.easylinker.xyz```
> ## 3.下载EMQ消息服务器  
>>1.上官网下载:```http://www.emqtt.com/```
>>2.下载Easylinker定制版:```www.easylinker.xyz```
>##4.配置  
>>1.配置EMQ基本信息:
>>关闭匿名:  ```mqtt.allow_anonymous = false  ```
>>Broker的名字: ``` node.name = emq@127.0.0.1  (这个是默认的)```
>>2.把ACL.conf里面的东西覆盖如下:
```
%%--------------------------------------------------------------------
%%
%% [ACL](https://github.com/emqtt/emqttd/wiki/ACL)
%%
%% -type who() :: all | binary() |
%%                {ipaddr, esockd_access:cidr()} |
%%                {client, binary()} |
%%                {user, binary()}.
%%
%% -type access() :: subscribe | publish | pubsub.
%%
%% -type topic() :: binary().
%%
%% -type rule() :: {allow, all} |
%%                 {allow, who(), access(), list(topic())} |
%%                 {deny, all} |
%%                 {deny, who(), access(), list(topic())}.
%%
%%--------------------------------------------------------------------
%%
%% 服务器后台
{allow, {user, "dashboard"}, subscribe, ["$SYS/#"]}.
%% 代理服务器  名称可以随机设置，但是要和代理配置一样
{allow, {user, "easylinker_server"}, subscribe, ["IN/DEVICE/+/+/#"]}.
{allow, {user, "easylinker_server"}, publish, ["OUT/DEVICE/+/+/#"]}.
{allow, {user, "easylinker_server"}, subscribe, ["$SYS/brokers/+/clients/+/#"]}.
%%CMD监控
{allow, {user, "easylinker_server"}, subscribe, ["CMD/IN/#"]}.
{allow, {user, "websocket_client"}, subscribe, ["CMD/IN/#"]}.
%%实时消息监控 IN/REAL_TIME/#
{allow, {user, "easylinker_server"}, subscribe, ["OUT/REAL_TIME/#"]}.
%% websocket
{allow, {user, "websocket"}, subscribe, ["OUT/REAL_TIME/#"]}.
%%配置客户端
{allow, all, subscribe, ["OUT/DEVICE/+/+/#"]}.
{allow, all, publish, ["CMD/IN/#"]}.
{allow, all, publish, ["IN/DEVICE/+/+/#"]}.
```  
>##3.配置认证插件  
找到```etc/plugin目录```,里面有:```emq_auth_username.conf```和```emq_auth_mysql.conf```两个插件,打开编辑:  
>>###3.1用以下配资后直接覆盖:emq_auth_mysql.conf:  
```
## MySQL server
auth.mysql.server = 你的数据库地址（注意，和EMQ的配置一致）:3306
auth.mysql.pool = 8
auth.mysql.username =easylinker_dev
auth.mysql.password =easylinker_dev
auth.mysql.database = easylinker_dev
auth.mysql.auth_query = select open_id as password from device where open_id = '%u' limit 1
auth.mysql.password_hash = plain
auth.mysql.acl_query = select allow , ip_address, open_id AS username , client_id AS clientid , access, topic from device  where  open_id ='%u'
```
>>### 3.2用以下配资后直接覆盖:emq_auth_username.conf:   
```
##支持正则表达式
auth.user.1.username = easylinker
auth.user.1.password = easylinker
auth.user.2.username = websocket
auth.user.2.password = websocket
```
> ## 4.安装MYSQL数据库  
>>Mysql安装方式网络上资源很多,请各位自己检索,这里不做赘述.
> ## 5.安装Java  
>>Java安装方式网络上资源很多,请各位自己检索,这里不做赘述(注意:必须是Java8).  
> ## 6.application.properties配置
```
########################################################
###THYMELEAF (ThymeleafAutoConfiguration)
########################################################
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML5
spring.thymeleaf.encoding=UTF-8

spring.thymeleaf.cache=false
###################################################################################################
#server
#####################################################################################################
server.port=2500
spring.http.encoding.charset=utf8
spring.http.encoding.force=true
spring.aop.auto=true
spring.aop.proxy-target-class=true
####################################################################################################
####################################################################################################
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/easylinker?autoReconnect=true&useUnicode=true&characterEncoding=utf8&useSSL=true
spring.datasource.password=easylinker
spring.datasource.username=easylinker
spring.datasource.tomcat.validation-query=SELECT 1
spring.datasource.dbcp2.test-on-borrow=true
server.tomcat.uri-encoding=UTF-8
#####################################################################################################
#JPA Configuration:
#####################################################################################################
spring.jpa.database=MYSQL
spring.jpa.open-in-view=true
spring.jpa.show-sql=false
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
hibernate.event.merge.entity_copy_observer=allow
######################################################################################################
###mail setting
######################################################################################################
spring.mail.host=#
spring.mail.username=#
spring.mail.password=#
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.properties.mail.smtp.starttls.required=true
######################################################################################################
######################################################################################################
#EMQ server
######################################################################################################
emq.host=tcp://localhost
emq.api.host=http://localhost:8080/api/v2/
emq.api.user=easylinker
emq.api.password=easylinker
######################################################################################################
######################################################################################################
#upYun
######################################################################################################
upyun.account.bucketname=bucketname
upyun.account.username=username
upyun.account.password=password
upyun.account.apiKey=apikey
######################################################################################################
```
>##7.启动
>>配置好了以后,切换到Jar所在的目录;   
>>Java启动Jar的命令: ```java -jar easylinkerx.x.x.jar --spring.config.location=application.properties```,没问题的话,就可以启动成功了,关于Java,emq,MYSQL,相关知识,请自己去官网学习.
# 五·API接口的使用方法  
> ### 1.POSTMAN工具的使用  
>>POSTMAN介绍:Postman是一种网页调试与发送http请求的R软件。我们可以用来很方便的模拟get或者post或者其他方式的请求来调试接口。教程网上很多，这里给出一个参考:```https://www.cnblogs.com/xiaoxi-3-/p/7839278.html```
> ### 2.Nginx配置  
>>如果你要做反向代理，最好的办法就是使用nginx做反向代理,给出一个demo配置:
```
worker_processes  1;
events {
    worker_connections  1024;
}
http {
    include       mime.types;
    default_type  application/octet-stream;
    upstream server_group  {
        server localhost:2500;
    }

server {
    listen 80;
    server_name  localhost;
    gzip on;
    gzip_min_length  1k;
    gzip_buffers     4 32k;
    gzip_http_version 1.0;
    gzip_comp_level 2;
    gzip_types       text/plain application/x-javascript text/css application/xml;


            location /api/ {
            index  index.html;
            root html;
            #这个比较重要，规定了/api/路径下的路由才会被代理
            rewrite ^/api/(.*)$ /$1 break;
            proxy_pass http://server_group;
            #-------------------------------------------
            ssi on ;
            ssi_types text/shtml;
            proxy_read_timeout 300;
            proxy_connect_timeout 300;
            proxy_redirect     off;
            proxy_set_header   X-Forwarded-Proto $scheme;
            proxy_set_header   Host              $http_host;
            proxy_set_header   X-Real-IP         $remote_addr;

            add_header Access-Control-Allow-Origin *;
            add_header Access-Control-Allow-Headers X-Requested-With;
            add_header Access-Control-Allow-Methods GET,POST,DELETE,OPTIONS;
        }
    }
}
```
> # 五·快速写一个客户端  
>>###1.python-SDK demo
```python
import paho.mqtt.client as mqtt
import time
import json
import threading
from multiprocessing import Process
'''
0: Connection successful
1: Connection refused - incorrect protocol version
2: Connection refused - invalid client identifier
3: Connection refused - server unavailable
4: Connection refused - bad username or password
5: Connection refused - not authorised
6-255: Currently unused.
'''
#IN/DEVICE/DEFAULT_USER/XMRJY11/1522404991788
CLIENT_OPENID="你再管理控制台创建的设备ID"
CLIENT_NAME="你在管理控制台创建的设备名称"
CLIENT_GROUP="你在管理控制台创建的设备组名字"
client = mqtt.Client(CLIENT_NAME)
data={"temp":21,"hum":2.1}#模拟数据

def send_data():
    global  client
    client.publish("IN/DEVICE/USER_ID/"+你的设备组名+"/"+当前设备的ID, str(data))
    timer = threading.Timer(2.0, send_data)
    timer.start()
def on_disconnect(a,b,c):
    print("on_disconnect",a,b,c)

def on_connect(c, userdata, flags, rc):
    print("connect state:",rc)
    if rc==0:
        print("Connected Success! ")
        client.subscribe("OUT/DEVICE/你的USER_ID/你的设备组名/"+当前设备的ID)
        timer = threading.Timer(2.0, send_data)
        timer.start()
    else:
        print("Connected Failed! ")
        client.disconnect()
def on_message(client, userdata, msg):
    print("Received Data:",msg.payload)

client.on_connect = on_connect
client.on_message = on_message
client.on_disconnect=on_disconnect
client.username_pw_set(CLIENT_OPENID,CLIENT_OPENID)
client.connect("localhost", 1883, 60)#根据自己的情况修改
client.loop_forever()
```
>>其他客户端demo请看这里:http://www.easylinker.xyz/
> # 六·二次开发  
>>待完成
> ## 1.项目结构  
>>待完成
> ## 2.模块介绍  
>>待完成
> ## 3.插件编写  
>>待完成
> ## 4.功能扩展  
>>待完成
# 七·一个小案例:智能温度控制  
>>待完成


  [1]: http://www.easylinker.xyz/upload/userfile/1/277497e7a3a269325be23b0cfe835ff5.png