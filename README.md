Dummy
========

This is solely for playing with new toys!

The original aim is that it should hold a usable project, composed of the following:
* IntelliJ IDEA projects, via gen-idea.
* Scala stuff, 2.10 sugar
* An sbt project
* Some sh scripts for common things I used to do in windows.
* A miniApps project for trying out scala and useful tools. 
	*OptaFeedMonkey - Curls a URL endpoint to a new file every 5 minutes
* A nodeApps project for trying out some js apps, with node, grunt and underscore

Should be usable on any *nix system.

Steps for mac.
* Install Homebrew
* Install sbt, using 'brew install sbt' (check 'brew --cache' to see where it is installed).
* Play is a dependency of the application, so simply cd into "theHackDayApp", and run the command sbt.
* In the sbt prompt, enter 'run'. Beware! An error may occur, along the lines of "Error during sbt execution: java.lang.OutOfMemoryError: PermGen space". In this case, you need to make a ~/.sbtconfig file, with the following contents:

SBT_OPTS="-XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:PermSize=256M -XX:MaxPermSize=512M -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1044"