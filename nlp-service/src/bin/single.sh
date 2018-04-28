#!/bin/bash

#取当前目录
BASE_PATH=`cd "$(dirname "$0")"; pwd`

#设置java运行参数
DEFAULT_JAVA_OPTS=" -server -Xmx2g -Xms2g -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -Dfile.encoding=utf-8"


#引入外部参数配置文件:
SHELL_PARAMS="$BASE_PATH/params.conf"
if [ -f "$SHELL_PARAMS" ]; then
	. $SHELL_PARAMS
fi

#定义变量:
APP_PATH=${APP_PATH:-`dirname "$BASE_PATH"`}
CLASS_PATH=${CLASS_PATH:-$APP_PATH/config:$APP_PATH/lib/*}
JAVA_OPTS=${JAVA_OPTS:-$DEFAULT_JAVA_OPTS}
MAIN_CLASS=${MAIN_CLASS:-"com.biaoqing.search.FAApplication"}


exist(){
			if test $( pgrep -f "$MAIN_CLASS $APP_PATH" | wc -l ) -eq 0
			then
				return 1
			else
				return 0
			fi
}

start(){
		if exist; then
				echo "api is already running."
				exit 1
		else
	    	cd $APP_PATH
				nohup java $JAVA_OPTS -cp $CLASS_PATH $MAIN_CLASS $APP_PATH 2> /dev/null &
				echo "api is started."
		fi
}

stop(){
		runningPID=`pgrep -f "$MAIN_CLASS $APP_PATH"`
		if [ "$runningPID" ]; then
				echo "api pid: $runningPID"
        count=0
        kwait=5
        echo "api is stopping, please wait..."
        kill -15 $runningPID
					until [ `ps --pid $runningPID 2> /dev/null | grep -c $runningPID 2> /dev/null` -eq '0' ] || [ $count -gt $kwait ]
		        do
		            sleep 1
		            let count=$count+1;
		        done

	        if [ $count -gt $kwait ]; then
	            kill -9 $runningPID
	        fi
        clear
        echo "api is stopped."
    else
    		echo "api has not been started."
    fi
}

check(){
   if exist; then
   	 echo "api is alive."
   	 exit 0
   else
   	 echo "api is dead."
   	 exit -1
   fi
}

restart(){
        stop
        start
}

case "$1" in

start)
        start
;;
stop)
        stop
;;
restart)
        restart
;;
check)
        check
;;
*)
        echo "available operations: [start|stop|restart|check]"
        exit 1
;;
esac