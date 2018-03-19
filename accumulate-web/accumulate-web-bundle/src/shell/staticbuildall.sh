# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`

# Only set SRC_HOME if not already set
[ -z "$SYNC_HOME" ] && SRC_HOME=`cd "$PRGDIR/.." >/dev/null; pwd`

####################################
#Section 1: Set Shell Const
####################################
WEB_ROOT=$SRC_HOME/main/webapp
JS_ROOT=$WEB_ROOT/static
JS_PUB_ROOT=$WEB_ROOT/statictarget
FTP_SYNC_ROOT=/mall/web/pc/js
FTP_CONF_FILE=~/ftp.conf
MODIFY_FTL_SCRIPT=$SRC_HOME/shell/modifyftl.js
JS_RES_FILE=$WEB_ROOT/WEB-INF/freemarker/modules/public/config_assign_static.ftl


####################################
#Section 2: Publish JS
####################################
#The variable $JS_PUB_ROOT which was configed cannot end with '/'
if [[ $JS_PUB_ROOT =~ .*/$ ]]; then
  echo "the variable of \$JS_PUB_ROOT cannot end with '/'"
  exit 21
fi

echo js publish directory:$JS_PUB_ROOT

if [ -d $JS_PUB_ROOT ]; then
  echo "start to clean js publish directory ..."
  rm -rf $JS_PUB_ROOT
  if [ $? -eq 0 ]; then
    echo "clean js publish directory ok"
  else
    echo "fail to clean js publish directory"
  	exit 22
  fi
fi

echo "start to publish js..."
fis release -opm -r $JS_ROOT -d $JS_PUB_ROOT
if [ $? -eq 0 ]; then
  echo "publish js ok"
else
  echo "fail to publish js"
  exit 23
fi

###############################################
#Section 3: Sync JS Publishments To FTP Server
###############################################
#The variable $FTP_SYNC_ROOT which was configed cannot end with '/'
if [[ $FTP_SYNC_ROOT =~ .*/$ ]]; then
  echo "the variable of \$FTP_SYNC_ROOT cannot end with '/'"
  exit 31
fi
#Load FTP Config
#Check FTP Config File
if [ -f $FTP_CONF_FILE ]; then
	echo "loading ftp config..."
else
	echo "ftp config file does not exist: "$FTP_CONF_FILE
	exit 32
fi
#Parse FTP Config only use the first line
FTP_CONFIG=`head -n 1 $FTP_CONF_FILE`
FTP_HOST=`echo $FTP_CONFIG | awk '{print $3}'`
FTP_USER=`echo $FTP_CONFIG | awk '{print $1}'`
FTP_PASS=`echo $FTP_CONFIG | awk '{print $2}'`
if [ -z $FTP_HOST ];then
  echo "FTP Host Name can not be empty"
  exit 33
fi
if [ -z $FTP_USER ];then
  echo "FTP User Name can not be empty"
  exit 34
fi
if [ -z $FTP_PASS ];then
  echo "FTP Password can not be empty"
  exit 35
fi

echo "start to sync ftp server:"$FTP_HOST" ..."
lftp -u $FTP_USER,$FTP_PASS ftp://$FTP_HOST <<EOF
mirror -R --ignore-time --verbose $JS_PUB_ROOT $FTP_SYNC_ROOT
bye
EOF
if [ $? -eq 0 ]; then
  echo "sync ftp server complete"
else
  echo "fail to sync ftp server"
  exit 36
fi

###############################################
#Section 4: Genergate JS Resource Conf Template
###############################################
echo "start to generate JS_RES_FILE"
node "$MODIFY_FTL_SCRIPT" "$JS_PUB_ROOT" "$FTP_SYNC_ROOT" "$JS_RES_FILE"

#return
if [ $? -eq 0 ]; then
  echo "generate JS_RES_FILE ok"
  exit 0
else
  echo "fail to generate JS_RES_FILE"
  exit 41
fi
