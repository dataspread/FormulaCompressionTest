#!/bin/bash

# To run this script on the RISE servers:
# 	bash main.sh RISE
#
# To run this script on a local dev environment:
#	bash main.sh

cd $(dirname "$0") && \
if [ "$1" == "RISE" ]; then
	bash ./scripts/local/db.sh # TODO: For some reason ./scripts/rise/db.sh doesn't start the container on the RISE machines.
	bash ./scripts/rise/build.sh && bash ./scripts/rise/run.sh fcomp-rise-container
else
	bash ./scripts/local/db.sh
	bash ./scripts/local/build.sh && bash ./scripts/local/run.sh fcomp-local-container
fi
