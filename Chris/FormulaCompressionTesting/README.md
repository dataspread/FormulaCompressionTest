# Getting Started

- If you're using your local machine, you can automatically set up the testing environment by running:
	```sh
	$ bash main.sh
	```
	This will start a shell session inside the container with the necessary packages and testing files. If developing locally, you can navigate to the root directory with:
	```sh
	$ cd home/cdeleon
	```
	To navigate to the directory with the testing scripts use:
	```sh
	$ cd home/cdeleon/FormulaCompressionTesting/testing
	```
	You can edit the configuations with `vim`:
	```sh
	$ vim runtest.sh
	```
	You can also modify the file directly from the host since the scripts use docker bind mounts. Once you're happy with the settings, you can run the script as usual:
	```sh
	$ bash runtest.sh
	```
	Once you're done, you can exit out of the container using
	```sh
	$ exit
	```
	If you'd like to run the container again use:
	```sh
	# For local development
	$ bash ./scripts/local/run.sh <container-name>

	# For the RISE servers:
	$ bash ./scripts/rise/run.sh <container-name>
	```

- If you're on the RISE servers, you can run:
	```sh
	$ bash main.sh RISE
	```
	**WARNING:** The command above is case sensitve. If you were to instead type "RISe" for instance then then none of the RISE server configurations would be applied, and it would the equivalent of calling `bash main.sh`.

	This will start a shell session inside the container with the necessary packages and testing files. You'll automatically be placed in the root directory. To navigate to the directory with the testing scripts use:
	```sh
	$ cd FormulaCompressionTesting/testing
	```
	From there the same commands from above apply.

- **NOTE:** The RISE servers do not have docker-compose installed (and you can't install it due to access privileges). As a result, this project includes a clean up script that wipes all containers, images, and networks used by `main.sh`:
	```sh
	$ bash clean.sh
	```
