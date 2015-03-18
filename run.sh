#!/bin/bash

# clean the evn
source ./setenv.sh

# Git update and build
echo "Update the crazy monkey code..."
cd $CRAZY_MONKEY_HOME && git pull

cd $CRAZY_MONKEY_HOME && /bin/bash ./stop.sh

echo "Run the vpn client"
cd $CRAZY_MONKEY_HOME && /bin/bash ./reset_vpn.sh
cd $VPN_CLINET_HOME && ./autorun

echo "Build the project"
cd $CRAZY_MONKEY_HOME && $ANT_HOME/bin/ant

# Run the testing
$JAVA_HOME/bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar

