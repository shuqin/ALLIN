#!/bin/bash

HOME=/Users/qinshu/
PROJ=$HOME/workspace/ALLIN

ZK_HOME=$HOME/setup/zookeeper362
KAFKA_HOME=$HOME/setup/kafka

# redis startup
redis-server &

# zk startup
cd $ZK_HOME
bin/zkServer.sh start-foreground &

sleep 15

# kafka startup
cd $KAFKA_HOME
bin/kafka-server-start.sh config/server.properties &

