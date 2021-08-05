#!/bin/bash

docker stop fcomp-db # Container will automatically be removed bc we used the --rm flag with docker run
docker image rm --force fcomp
docker network rm dataspread
