# single-town
**单体版小镇商铺是一个轻量级网站**

1，支持的功能有
>文件上传：用户可以将自己图片或文件在前端上传，java后台对上传后的文件保存至服务器。

>文件下载：java能对服务器上的文件目录进行映射，这样只需给前端一个图片url地址，就能把上传的图片加载出来了。

>登录态：访问特定的网页需要用户进行登录，而有些资源的访问又不需要登录。在代码里仅仅几行就能实现登录态的判断。

>权限管理：前后端交互使用的是接口方式，对不同等级的用户能调用的接口权限也是不一样的。仅需一个注解就能对用户的接口权限进行管理。

2，使用的技术有
> 前端：bootstrap，jquery。

>后端：springboot，spring starter，shiro，mybatis，mysql，redis。

>运维部署：docker，nginx。

3，本地运行方式
* 安装数据库和redis，在frame和town项目中的配置文件中配置链接和账号密码。
* 执行建表sql语句和预置数据脚本。
* maven操作，先对frame项目的pom文件进行install，再对frame-starter项目进行install，就可以启动town项目的Application了。

在线体验分布式版： town.tacbin.club/ 账号密码45645

