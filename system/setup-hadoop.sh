#!/bin/bash

sudo apt-get update
sudo apt-get install -y openjdk-8-jdk-headless
sudo apt-get install -y htop git curl
wget http://xenia.sote.hu/ftp/mirrors/www.apache.org/hadoop/common/hadoop-2.8.4/hadoop-2.8.4.tar.gz
tar -xf hadoop-2.8.4.tar.gz
sudo mv hadoop-2.8.4 /opt/
rm hadoop-2.8.4.tar.gz
