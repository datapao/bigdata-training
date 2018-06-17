#!/bin/bash -e

rm -rf out.csv

hadoop jar /opt/hadoop-2.8.4/share/hadoop/tools/lib/hadoop-streaming-2.8.4.jar -fs local -jt local -mapper mapper.py -reducer reducer.py -input payments.csv users.csv -output out.csv -file mapper.py -file reducer.py
