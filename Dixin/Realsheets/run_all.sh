#!/bin/bash

TEST_HOME=`pwd`

for dataset in enron
do
$TEST_HOME/runtest.sh $TEST_HOME/sheets_for_taco/${dataset}_new.conf $TEST_HOME/sheets_for_taco/${dataset} $TEST_HOME/report_${dataset}
done
