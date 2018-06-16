#!/bin/bash

sudo apt-get update
sudo apt-get install -y default-jdk
sudo wget -O /etc/apt/sources.list.d/ambari.list http://public-repo-1.hortonworks.com/ambari/ubuntu16/2.x/updates/2.6.1.5/ambari.list
sudo apt-key adv --recv-keys --keyserver keyserver.ubuntu.com B9733A7A07513CAD
sudo apt-get update
sudo apt-get install -y ambari-server ambari-agent
sudo ambari-server setup
sudo ambari-server start
sudo ambari-agent start
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java_8.0.11-1ubuntu16.04_all.deb
sudo dpkg -i mysql-connector-java_8.0.11-1ubuntu16.04_all.deb
