# ALLIN
a project containing all that i have learned

包：
Springboot项目: cc.lovesq 下
Java 学习相关： zzz.study 下
  algorithm 算法
  datastructure 数据结构
  foundations Java语言基础
  function 函数式编程相关
  patterns 设计模式
  threadprogramming 多线程相关


Run ALLIN
---------------------------------------
应用启动依赖 Redis，否则会报 Unable to connect to Redis server: 127.0.0.1/127.0.0.1:6379 错误
解决办法： 安装 redis 服务器并启动。 移除 redis 依赖比较麻烦。

步骤一：启动 Redis 服务；
$ redis-server

步骤二：启动 Zookeeper 服务：
$ bin/zkServer.sh start-foreground

步骤三：启动 Kafka 服务：
$ bin/kafka-server-start.sh config/server.properties

