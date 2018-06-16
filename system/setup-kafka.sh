#!/bin/bash
wget http://xenia.sote.hu/ftp/mirrors/www.apache.org/kafka/1.1.0/kafka_2.12-1.1.0.tgz
tar -xf kafka_2.12-1.1.0.tgz
sudo mv kafka_2.12-1.1.0 /opt
rm kafka_2.12-1.1.0.tgz


wget http://xenia.sote.hu/ftp/mirrors/www.apache.org/zookeeper/zookeeper-3.4.12/zookeeper-3.4.12.tar.gz
tar -xf zookeeper-3.4.12.tar.gz
sudo mv zookeeper-3.4.12 /opt
rm zookeeper-3.4.12.tar.gz
