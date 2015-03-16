#!/bin/bash

# clean the evn
source ./setenv.sh
/bin/bash ./stop.sh

# Git update and build
cd $CRAZY_MONKEY_HOME
git pull
$ANT_HOME/bin/ant

# Run the testing
$JAVA_HOME/bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar

