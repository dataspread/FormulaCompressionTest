#!/bin/bash

TEST_HOME=`pwd`

for dataset in enron github
do
	REPORT_HOME=$TEST_HOME/report_${dataset}
	CONFFILE=$TEST_HOME/sheets_for_taco/${dataset}.conf

	while IFS=, read -r sheetname cell;
	do
		OUT_FOLDER=$REPORT_HOME/Comp/${sheetname}/memOnly/RUN1
		cat $OUT_FOLDER/taco.stat
	done < $CONFFILE
done

