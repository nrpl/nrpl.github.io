Docker Introduction
===================
Docker is a lightweight container technology based on linux features.
Docker containers do not emulate the underlaying hardware or Hardware Abstraction Layer.
Because of this, docker containers have a much smaller footprint and are more efficient compared to 
virtualization products like VMWare or VirtualBox.

Docker containers can be used as deployment artifact for shipping applications.
The container contains the applications, e.g. a jar file, and additionally all of the necessary configuration and 
dependencies on operating system level.

The traditional way of shipping a java application is packaging the application into a jar and shipping this jar 
together with a manual on how to prepare the system which runs the jar.
The configuration typicalle differs for different operating systems and maybe conflicts with other software on the target system.
The docker way is to prepare a container which includes the application and the prepared system and ship this container.
The target system does not need to be configured anymore, it acts just as runtime environment for the docker container.

Server / Cloud Applications can be distributed using docker containers.
All dependencies, e.g. installed Java Version or specific libraries, can be prepared within the container.
The prepared container can then be released in a central public/private Repository.

Docker containers start within seconds, many containers can be run in parallel even on small hardware like a laptop.
Cloud Computing Providers like Amazon Web Services or Azure provide infrastructure for running docker containers.
