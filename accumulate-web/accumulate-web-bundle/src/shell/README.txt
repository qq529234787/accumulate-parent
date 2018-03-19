此目录为自动发布js文件到CDN的脚本目录
Windows平台请使用fisstatic.bat
Linux/Mac平台请使用staticbuildall.sh
该目录下的fis_bat目录已废弃，可以删除。

js文件发布CDN的过程可分为如下几步：
1.将JS原始文件优化（包括去除换行符，临时变量改名为更短的变量名称）
2.给JS优化后的文件加上MD5校验值（同时也当做文件版本标识）
例如：原始文件为：detail.js，经过加工后的文件名变为：detail_4c43f.js
3.将生成好的JS文件同步到CDN源站的对应目录下（使用FTP协议传输）
4.在项目的动态模板中会引入生成好的JS，为了使用方便，需要生成一个相对稳定的变量名为key，js远程路径为value的文件。
引用的JS也不是直接引用，是使用动态合并回传接口来获取，因此js远程路径中的"/"需要用"|"替换。

上述步骤中1、2步需要使用基于NodeJS的fis工具来完成；
步骤3使用lftp工具的镜像功能来实现；
步骤4需要保证跨平台时遍历到的文件顺序一致，因此使用了基于NodeJS的脚本来实现该功能。

注：自动发布脚本对获取绝对路径的方式进行了优化，在任意目录下执行发布脚本均不会有问题（即当前目录不一定在shell目录下）

环境安装：
通用步骤
1.安装NodeJS
2.安装fis
3.安装fis-postpackager-simple
安装步骤可参阅官方文档：http://fex.baidu.com/fis-site/index.html，另外还是用的fis2
4.准备FTP连接参数配置文件，文件格式为：[FTP_USER] [FTP_PASS] [FTP_HOST]，文件名为:ftp.conf
注意：配置文件只读取第一行。

平台相关步骤
Windows平台：
1.安装lftp工具：
下载地址：https://lftp.nwgat.ninja/lftp-4.6.5/
解压到某个目录，然后将该目录配置到环境变量LFTP_HOME,然后配置PATH环境变量，增加%LFTP_HOME%\bin。
最终效果为：在cmd中直接输入lftp能打开该工具。
2.将通用步骤4中的FTP配置文件放到%USERPROFILE%目录下，只需要在资源管理器中贴入改变量即可直接转到对应目录。

Linxu/MacOS平台
1.安装lftp工具：
详细过程请参阅Wiki：http://wiki.corpautohome.com/pages/viewpage.action?pageId=77534975
2.将通用步骤4中的FTP配置文件放到"~"目录下。