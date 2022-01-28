#!/bin/bash

TEST_HOME=`pwd`

for dataset in enron github
do
$TEST_HOME/runtest.sh $TEST_HOME/sheets_for_taco/${dataset}.conf $TEST_HOME/sheets_for_taco/${dataset} $TEST_HOME/report_${dataset}
done
