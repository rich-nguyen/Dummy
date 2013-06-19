Dummy
========

This is solely for playing with new toys!

The original aim is that it should hold a usable project, composed of the following:
* IntelliJ IDEA project
* Various Git-based experiments
* Scala stuff
* An sbt script
* Some sh scripts for common things I used to do in windows.

Should be usable from any mac/linux system.

Steps for mac.
1. Install Homebrew
2. Install sbt, using 'brew install sbt' (check 'brew --cache' to see where it is installed).
3. Play is a dependency of the application, so simply cd into "theHackDayApp", and run the command sbt.
4. In the sbt prompt, enter 'run'. Beware! An error may occur, along the lines of "Error during sbt execution: java.lang.OutOfMemoryError: PermGen space". In this case, you need to make a ~/.sbtconfig file, with the following contents: SBT_OPTS="-XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:PermSize=256M -XX:MaxPermSize=512M" 
