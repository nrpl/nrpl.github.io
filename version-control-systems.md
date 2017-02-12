Version control systems 
=======================

Overview
--------
Version control systems (VCS) are an essential tool of a professional software developer.
VCS are like a safety net for your project directory. 
Simplified, they provide the functionality of the undo and redo buttons known from text processors for a set of files.

VCS are typically not used for single files, they are used to keep track of changes to directory structures 
containing multiple files and folders.
They are usually used to keep a history of changes within a project directory. In order to keep track and save required data, VCS store additional data in a database or hidden files which is called Repository.

It is possible to add comments and create snapshots which represent a specific version of the project.
The changes to directories or files can be retraced using the version history. Each change is stored together with a timestamp, the author of the change and a comment.
These mechanisms enable the possibility to always go back to a defined state of the project.

It is also possible to make experimental or risky changes in an isolated copy of the project structure called Branches.
As soon as the changes in such a copy, which is called branch, are proven the can be transferred to the main branch. 

Another important feature of VCS is the possibility to work in a team on the same project.
The developers do not have to discuss and agree the work on specific files.
Modifications to the same files are coordinated by the VCS.
Non-conflicting changes can be  merged automatically by the VCS.
Only in the case of conflicting changes the developers have to take appropriate action.

Popular version control systems are:
- [CVS](http://savannah.nongnu.org/projects/cvs)
- [SVN](https://subversion.apache.org/)
- [Git](https://git-scm.com/)

I prefer using Git, which is state-of-the art and offers improvements like distributed repositories, offline support and fast and easy branching.
It is possible to create a local repository to start working on a local project on your own but with version control right from the start. Later you can switch to a remote repository on a central server and transfer the project to this repository.

The free online book [Pro Git](https://git-scm.com/book) is an excellent information source for learning git and provides technical details to understand how git works behind the scenes.
