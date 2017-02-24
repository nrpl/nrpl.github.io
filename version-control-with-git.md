Version control with Git (in progress)
======================================
This document is still under construction.

It's intended use it to provide a quick reference for basic git usage and workflows which I am using in my day-to-day routine.
Please refer to the links in the additional info section for detailed documentation and references dealing with git.

Overview
--------
Git is a distributed version control system focussing on efficiency and speed.
It offers quick branching, a staging area to prepare files to be committed and makes it possible to work offline.

All features are available using a command line tool, but there are many GUI clients and plugins for IDEs.

Git was started by Linus Torvalds in 2005, the current version is 2.11.1 (02. Feb 2017).


Start local project
-------------------
Git offers the possibility to use a central server to host the repository,
but it is also possible to work completely locally and offline.
Many projects start locally, without contribution of other developers.
To get started using git a local repository can be created using
```
git init
```
This command creates a ``.git`` folder which contains the meta data of the local git repository.


Start by cloning an existing project
---------------------------------
You can clone an existing project containing all branches and version history using a single command.
You need the url to the git repository which is hosted on a central server.
To clone the repository of git itself, you can use the following command:
```
git clone https://github.com/git/git.git
```


Using a remote repository
-------------------------
Git repositories can be hosted on a central server like GitHub.
You can check if your local repository is linked to a remote repository:
```
git remote
```
If no remote name is printed, then no remote repository is configured.

To add a remote repository with the name origin use
```
git remote add origin https://github.com/nrpl/samplerepo.git
```

To get the changes from a remote repository use
```
git pull
```

To save you local committed changes to a remote repository use
```
git push
```


Basic git commands
------------------
You can use
```
git status
```
to get information on the current state of the local working copy.

Adding a file changes the state of the working copy.
```
touch hello.txt
git status
```

To add the file to version control use the following command:
```
git add hello.txt
```
The file is now in the Staging Area of git, which is used to prepare
a set of files that will be committed to the repository.
You can add other files to the Staging Area.

To finally commit the contents of the Staging Area to the repository together with a commit message describing the changes use this command:
```
git commit -m "Add file to git."
```

Exclude Files from Version control
---------------------------------
It is not practical to add each and every file to source control.
Typically the following types of files should not be added to version control:
* compiled files, like class files
* generated source files
* temporary files

Git uses a special file named ``.gitignore`` to manage files and file patterns which should not be watched.
To initially add such a file, you have to create it and add it to the git repo to make it available for contributors:
```
echo *.class > .gitignore
git add .gitignore
git commit -m "Add initial git ignore file."
```
After that all ``*.class`` files are not watched by git.
To test this, you can create a class-file and use ``git status`` to check that it does not show up in file listings.

GitHub offers a [collection of typically gitignore templates](https://github.com/github/gitignore) for different project types.


Switch between Versions
-----------------------
- switch directory to a specific revision
- switch only single files to a specific revision


Undo changes
------------

### Staging Area
- remove accidentally added file from staging area
- undo change which is already in the staging area

### Local Repository
- change commit message of a commit but not pushed change
- add file to a committed but not pushed change
- remove file from a committed but not pushed change
- undo change which is committed but not pushed

### Remote Repository
- change commit message of a pushed commit but not pushed change
- add file to a committed and pushed change
- remove file from a committed and pushed change
- undo change which is committed and pushed


Branching
---------
- list local branches
- list remote branches
- create new local branche
- add local branch to remote repository
- delete local branch
- delete remote branch


Merging
-------
- merge other branch into own branch
- deal with conflicts


Additional information
----------------------
* [Git on GitHub](https://github.com/git/git)
* [Git Reference](http://gitref.org/)
* [Git Project Website](https://git-scm.com/)
* [Git Flow Branching Model](http://nvie.com/posts/a-successful-git-branching-model/)
