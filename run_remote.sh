#!/bin/bash

if [ "$#" -ne 2 ]
  then
    echo "Please provide Scenario Class and IP as parameters. e.g.: `./run_remote.sh eastwood.testconnectivity.TestConnectivity 192.168.0.1`"
    exit 1;
fi

TESTCLASS=$1
WORKERIP=$2
TODAY=`date +%d%m%Y-%H%M%S`

echo ">> Starting performance test on worker: $WORKERIP"
mvn clean install -DskipTests && \
scp target/alexander-perf-lab-3.1.2.jar ec2-user@$WORKERIP:/tmp && \
ssh ec2-user@$WORKERIP "java -Dgatling.core.simulationClass=$TESTCLASS -jar /tmp/alexander-perf-lab-3.1.2.jar" && \
ssh ec2-user@$WORKERIP "zip -r /tmp/perfresults.zip /home/ec2-user/results*" && \
scp ec2-user@$WORKERIP:/tmp/perfresults.zip $TODAY-results.zip && \
ssh ec2-user@$WORKERIP "rm -rf /tmp/perfresults.zip ; rm -rf /tmp/alexander* ; rm -rf /home/ec2-user/results*";

echo ">> Performance Execution Done, results in zip: $TODAY-results.zip";
