Jenkins Pipeline
================
Jenkins Build Jobs are usually created and maintained using
the Web UI of Jenkins. The related configuration is stored on Jenkins.
This leads to the following problems:
- it is not possible to track changes
- in case of failure, e.g. hardware damage, the build job configuration is lost

Jenkins Pipeline allows to define the build job configuration within a file
which is added to the project code.
The build jobs are defined using Groovy and get executed after Jenkins has checked out the code from the version control system.

The build job configuration is part of the code and managed using a version control system.

Pipeline Syntax and a Script Generator is available at the endpoint pipeline-syntax of your Jenkins:
- localhost:8080/pipeline syntax

For more information visit [Getting Started](https://jenkins.io/doc/book/pipeline/getting-started/) on [jenkins.io](https://jenkins.io).
