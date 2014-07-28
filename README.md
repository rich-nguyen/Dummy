Dummy
========

A mixed-bag repo, composed of the following:
* coursera contains my answers for the first scala coursera course.
* nodeApps is a template js node app.
* videprinter is a football videprinter using PA data.
* scalaApps is a project holder for trying out scala and useful tools. 
	*OptaFeedMonkey - Curls a URL endpoint to a new file every 5 minutes

Should be usable on any *nix system.

Steps for mac.
* Install Homebrew
* Install sbt, using 'brew install sbt' (check 'brew --cache' to see where it is installed).
* Play is a dependency of the application, so simply cd into "theHackDayApp", and run the command sbt.
* In the sbt prompt, enter 'run'. Beware! An error may occur, along the lines of "Error during sbt execution: java.lang.OutOfMemoryError: PermGen space". In this case, you need to make a ~/.sbtconfig file, with the following contents:

SBT_OPTS="-XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:PermSize=256M -XX:MaxPermSize=512M -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=1044"
