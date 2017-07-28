Hands on: Running a Jenkins server using Docker
===============================================
This is a short summary of the necessary steps to run jenkins in docker:
- install docker by following the [official guide](https://www.docker.com/get-docker)
- check that docker engine is up and running:
  ``docker version```
- start hello-world container to make sure everything is working correctly:
  ```docker run hello-world```
- start jenkins container by entering the following command:
  ```docker run -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts```
  - the -p option maps the exposed port of the running jenkins in the container to the port of the host machine
    - port 8080 is the port which provides the jenkins web interface
    - port 50000 is used for the communication with build executors / slaves,
      it is not needed of you only use the master for build job execution
  - the -v option creates a volume which is independent of the container lifecycle,
    it is used to store data outside of the container but provide access to the data within the container
    - to find the location of the volume use the command `docker inspect jenkins`
    - On Mac and Windows you should use a path under the Users Home directory (docker engine has limited access to the filesystem)
- after the docker engine has downloaded the image from the internet it will start the container
- jenkins should be available on http://localhost:8080
- get the container ID using `docker ps` and stop the container with `docker stop <ID>`

For more details refer to the [Official Jenkins Docker Image](https://github.com/jenkinsci/docker).
