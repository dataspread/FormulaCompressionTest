FROM ubuntu:18.04
### By default the Dockerfile runs in root mode inside the container to initialize your environment
### Instead, create a user and grant that user elevated privileges as needed via sudo

## Create an account inside your container with your username
RUN groupadd -r cdeleon --gid=18120 && \
    useradd -r -g cdeleon --uid=18120 --create-home --shell /bin/bash cdeleon

## If needed, install sudo and give that account sudo access
# install sudo
RUN apt-get update && apt-get -y install sudo
# Give yourself sudo privilege inside the container
RUN echo "cdeleon   ALL = NOPASSWD: ALL" >> /etc/sudoers
RUN adduser cdeleon sudo

# From this point on, the rest of the Dockerfile executes under your name. Use sudo where necessary.
USER cdeleon

# Make sure the commands you run below this point run from your homedir, and not from /root
WORKDIR /home/cdeleon

# Install helper packages
RUN sudo apt-get update && sudo apt-get install -y vim

# Install OpenJDK-8
RUN sudo apt-get update && \
    sudo apt-get install -y openjdk-8-jdk && \
    sudo apt-get install -y ant && \
    sudo apt-get clean;
    
# Fix certificate issues
RUN sudo apt-get update && \
    sudo apt-get install ca-certificates-java && \
    sudo apt-get clean && \
    sudo update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
