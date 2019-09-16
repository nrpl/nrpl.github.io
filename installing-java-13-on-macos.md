# Installing Java 13 on macOS 10.5 or later
To install Java 13 on your Mac follow these steps:
1. Download the latest build from [here](http://jdk.java.net/13/)
2. Extract the archive (e.g. in Downloads)
3. FInd the place where your Java Versions are stored on disk: `/usr/libexec/java_home`
3. Move the contents to install directory on disk: `mv ~/Downloads/jdk-13.jdk /Library/Java/JavaVirtualMachines/`
4. Check that Java 13 is available: `/usr/libexec/java_home -V`
5. Set environments variables in bash_profile:
```
alias setJDK10='export JAVA_HOME=`/usr/libexec/java_home -v 10`'
alias setJDK13='export JAVA_HOME=`/usr/libexec/java_home -v 13`'
export JAVA_HOME=`/usr/libexec/java_home`
```
6. reload bash_profile: `source .bash_profile`
7. set version using bash alias: `setJDK13`
6. Check java version `java --version`
7. Have fun with the new Features!

Please use the links below for additional information on the topic:
* https://dzone.com/articles/installing-openjdk-11-on-macos
* https://medium.com/notes-for-geeks/java-home-and-java-home-on-macos-f246cab643bd
* https://www.mkyong.com/java/how-to-set-java_home-environment-variable-on-mac-os-x/
