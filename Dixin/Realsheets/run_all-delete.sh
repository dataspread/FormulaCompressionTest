#!/bin/bash

TEST_HOME=`pwd`

#for dataset in enron github
for dataset in enron
do
#$TEST_HOME/runtest-delete.sh $TEST_HOME/sheets_for_taco/${dataset}_delete.conf $TEST_HOME/sheets_for_taco/${dataset} $TEST_HOME/report_delete_${dataset}
$TEST_HOME/runtest-delete.sh $TEST_HOME/sheets_for_taco/${dataset}_async_delete.conf $TEST_HOME/sheets_for_taco/${dataset} $TEST_HOME/report_delete_${dataset}
done
