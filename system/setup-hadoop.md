# How to set up Hadoop for trainings.
## Requirements
OS: Ubuntu

## Steps
1. Run `setup-hadoop.sh`
2. Set `JAVA_HOME` in `/opt/hadoop/etc/hadoop-env.sh`
Current setup has a `JAVA_HOME`: `/usr/lib/jvm/java-8-openjdk-amd64`

3. Add the following to the `.bashrc`
```
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export HADOOP_HOME=/opt/hadoop-2.8.4
export PATH=$PATH:$HADOOP_HOME/bin
export PATH=$PATH:$HADOOP_HOME/sbin
export PATH=$PATH:$HADOOP_HOME/../pig/bin
export HADOOP_MAPRED_HOME=$HADOOP_HOME
export HADOOP_COMMON_HOME=$HADOOP_HOME
export HADOOP_HDFS_HOME=$HADOOP_HOME
export YARN_HOME=$HADOOP_HOME
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
export LD_LIBRARY_PATH=$HADOOP_HOME/lib/native
export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib"
```

4. Generate SSH key
```
rm -f ~/.ssh/authorized_keys ~/.ssh/id_rsa ~/.ssh/id_rsa.pub
rm -f /home/ubuntu/.ssh/known_hosts
ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
chmod 0600 ~/.ssh/authorized_keys
```

```
5. Set the replication with HDFS in `hdfs-site.xml`
```
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
    <property>
        <name>dfs.namenode.name.dir</name>
        <value>file:/opt/hadoop-2.8.4/hadoop_data/hdfs/namenode</value>
    </property>
    <property>
        <name>dfs.datanode.data.dir</name>
        <value>file:/opt/hadoop-2.8.4/hadoop_store/hdfs/datanode</value>
    </property>
</configuration>
```

6. Set `etc/hadoop/core-site.xml`:
```
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://localhost:9000</value>
    </property>
</configuration>
```

7. Set up `yarn-site.xml`
```
<configuration>
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
    <property>
        <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
        <value> org.apache.hadoop.mapred.ShuffleHandler</value>
    </property>
    <property>
        <name>yarn.nodemanager.disk-health-checker.enable</name>
        <value>false</value>
    </property>
</configuration>
```

8. Run the following:
`hadoop namenode -format`
