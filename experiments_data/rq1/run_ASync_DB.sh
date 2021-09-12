#!/bin/bash

JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64
JAVA_CMD=$JAVA_HOME/bin/java
JAVA_CONFIG=-Xss4m
MAVEN_HOME=/home/totemtang/.m2
DATASPREAD_HOME=/home/totemtang/dataspread/dataspread-web

TEST_HOME=/home/totemtang/dataspread/FormulaCompressionTest/experiments_data/rq1
REPORT_HOME=$TEST_HOME/report
COMMON_CONFIG=$TEST_HOME/commonConfig.properties
TEST_MAIN=FormulaCompressionTest.CompressionTestMain

CLASSPATH=$DATASPREAD_HOME/testcode/target/classes:$MAVEN_HOME/repository/org/apache/tomcat/tomcat-dbcp/8.0.33/tomcat-dbcp-8.0.33.jar:$MAVEN_HOME/repository/org/apache/tomcat/tomcat-juli/8.0.33/tomcat-juli-8.0.33.jar:$MAVEN_HOME/repository/org/apache/tomcat/tomcat-jdbc/8.5.11/tomcat-jdbc-8.5.11.jar:$DATASPREAD_HOME/blockstore/target/classes:$MAVEN_HOME/repository/com/esotericsoftware/kryo/4.0.0/kryo-4.0.0.jar:$MAVEN_HOME/repository/com/esotericsoftware/reflectasm/1.11.3/reflectasm-1.11.3.jar:$MAVEN_HOME/repository/org/ow2/asm/asm/5.0.4/asm-5.0.4.jar:$MAVEN_HOME/repository/com/esotericsoftware/minlog/1.3.0/minlog-1.3.0.jar:$MAVEN_HOME/repository/org/objenesis/objenesis/2.2/objenesis-2.2.jar:$MAVEN_HOME/repository/com/google/guava/guava/30.0-jre/guava-30.0-jre.jar:$MAVEN_HOME/repository/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar:$MAVEN_HOME/repository/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar:$MAVEN_HOME/repository/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar:$MAVEN_HOME/repository/org/checkerframework/checker-qual/3.5.0/checker-qual-3.5.0.jar:$MAVEN_HOME/repository/com/google/errorprone/error_prone_annotations/2.3.4/error_prone_annotations-2.3.4.jar:$MAVEN_HOME/repository/com/google/j2objc/j2objc-annotations/1.3/j2objc-annotations-1.3.jar:$MAVEN_HOME/repository/com/fasterxml/jackson/core/jackson-databind/2.9.10.5/jackson-databind-2.9.10.5.jar:$MAVEN_HOME/repository/com/fasterxml/jackson/core/jackson-annotations/2.9.10/jackson-annotations-2.9.10.jar:$MAVEN_HOME/repository/com/fasterxml/jackson/core/jackson-core/2.9.10/jackson-core-2.9.10.jar:$DATASPREAD_HOME/zss/target/classes:$MAVEN_HOME/repository/junit/junit/4.13.1/junit-4.13.1.jar:$MAVEN_HOME/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar:$MAVEN_HOME/repository/org/apache/commons/commons-math/2.2/commons-math-2.2.jar:$MAVEN_HOME/repository/commons-io/commons-io/1.3.2/commons-io-1.3.2.jar:$DATASPREAD_HOME/zssmodel/target/classes:$MAVEN_HOME/repository/com/github/davidmoten/rtree/0.8.6/rtree-0.8.6.jar:$MAVEN_HOME/repository/com/github/davidmoten/guava-mini/0.1.1/guava-mini-0.1.1.jar:$MAVEN_HOME/repository/io/reactivex/rxjava/1.3.8/rxjava-1.3.8.jar:$DATASPREAD_HOME/zpoi/target/classes:$MAVEN_HOME/repository/org/zkoss/common/zcommon/7.0.3/zcommon-7.0.3.jar:$MAVEN_HOME/repository/commons-fileupload/commons-fileupload/1.2.2/commons-fileupload-1.2.2.jar:$MAVEN_HOME/repository/org/zkoss/common/zel/7.0.3/zel-7.0.3.jar:$MAVEN_HOME/repository/org/beanshell/bsh/2.0b4/bsh-2.0b4.jar:$MAVEN_HOME/repository/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar:$MAVEN_HOME/repository/org/slf4j/slf4j-jdk14/1.7.5/slf4j-jdk14-1.7.5.jar:$MAVEN_HOME/repository/org/dom4j/dom4j/2.1.3/dom4j-2.1.3.jar:$MAVEN_HOME/repository/stax/stax-api/1.0.1/stax-api-1.0.1.jar:$MAVEN_HOME/repository/org/apache/xmlbeans/xmlbeans/2.3.0/xmlbeans-2.3.0.jar:$MAVEN_HOME/repository/org/apache/poi/ooxml-schemas/1.1/ooxml-schemas-1.1.jar:$MAVEN_HOME/repository/commons-codec/commons-codec/1.5/commons-codec-1.5.jar:$MAVEN_HOME/repository/org/zkoss/zk/zk/7.0.3/zk-7.0.3.jar:$MAVEN_HOME/repository/org/zkoss/common/zweb/7.0.3/zweb-7.0.3.jar:$MAVEN_HOME/repository/org/jsoup/jsoup/1.7.3/jsoup-1.7.3.jar:$MAVEN_HOME/repository/javax/servlet/servlet-api/2.4/servlet-api-2.4.jar:$MAVEN_HOME/repository/org/json/json/20180130/json-20180130.jar:$MAVEN_HOME/repository/org/postgresql/postgresql/42.2.1/postgresql-42.2.1.jar

declare -a depTableClassString=("ASync")
#declare -a depTableClassString=("PGImpl")
#declare -a spreadsheetString=("RunningTotalSlow" "RunningTotalFast" "Rate")
declare -a spreadsheetString=("Rate")
#declare -a rows=("5000" "10000")
declare -a rows=("15000")
declare -a runs=("1" "2" "3")
#declare -a runs=("1")

for run in "${runs[@]}"
do
	for i in "${!depTableClassString[@]}"
	do
		for j in "${!spreadsheetString[@]}"
		do
			for row in "${rows[@]}"
			do
				OUT_FOLDER=$REPORT_HOME/async/DB/${depTableClassString[$i]}/${spreadsheetString[$j]}/RUN-${run}/ROWS-${row}
				mkdir -p $OUT_FOLDER
				rm -f $OUT_FOLDER/*

				$JAVA_CMD $JAVA_CONFIG \
					-DdepTableClassString=${depTableClassString[$i]} \
					-DspreadsheetString=${spreadsheetString[$j]} \
					-DtestArg.0=${row} \
					-DoutFolder=$OUT_FOLDER \
					-classpath $CLASSPATH \
					$TEST_MAIN \
					$COMMON_CONFIG

				now="$(date)"
				echo "$now: Finished testing ${spreadsheetString[$j]} for ${depTableClassString[$i]} at row ${row}" >> out.log
				echo "" >> out.log
			done
		done
	done
done

