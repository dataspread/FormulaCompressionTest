#!/bin/bash

cd $(dirname "$0")
cd ../../
docker network create dataspread;
docker run \
	--rm \
	--net dataspread \
	--name fcomp-db \
	-p 5432:5432 \
	-e POSTGRES_DB=dataspread_db \
	-e POSTGRES_USER=admin \
	-e POSTGRES_PASSWORD=password \
	-d postgres:10.14 \
	-c enable_mergejoin=off \
	-c enable_hashjoin=off