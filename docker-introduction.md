Docker Introduction
===================
Docker is a lightweight container technology based on linux technologies.
It is related to virtualization known from VirtualBox or VMWare, 
but technically it is very different from these technologies.

Docker containers do not emulate the underlaying hardware or Hardware Abstraction Layer and do not contain a
full-fledged operating system.
Because of this, docker containers have a much smaller footprint and are more efficient compared to 
virtualization products like VMWare or VirtualBox.

Docker containers need the runtime environment Docker Engine to be executed.
Docker Engine can be natively run on linux machines. 
To run docker containers on Windows or Mac an additional virtual linux machine needs to be run which
acts as host for the docker runtime environment.

Docker containers solve the problem of "it works on my machine, but not on the production/other developers machine".
The concept of docker is to provide the entire environment in which an application is run, 
bundled together with the application, into a container. 

The traditional way is to put an application into an existing environment and to ensure, 
that the environment is configured appropriate to the application needs.
The docker way is to provide an isolated container which already contains the configured environment and the application.
The container can be executed on any machine that is running the Docker Engine 
and it is guaranteed that the environment in which the application is executed is identical on each machine.

Docker containers can be used as deployment artifact for shipping applications.
The container packages the  application, e.g. a jar file, and additionally all of the necessary configuration and 
dependencies on operating system level.
The prepared container can then be released in a central public/private repository.

Cloud computing providers like Amazon Web Services or Microsoft Azure provide infrastructure for running docker containers.
Even on small laptops many docker containers can be run in parallel, which makes it very useful even during development time.

Public repositories like [Docker Hub](https://hub.docker.com/) provide pre-assembled containers which can be reused and adjusted.
By using pre-assembled containers you can provide a webserver or jenkins server within minutes. 
