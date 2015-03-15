#!/bin/bash

# clean the evn
/bin/bash ./stop.sh

# run the vpn
cd $VPN_CLINET_HOME
git pull
$VPN_CLINET_HOME/autorun

# Git update and build
cd $CRAZY_MONKEY_HOME
git pull
$ANT_HOME/bin/ant

# Run the testing
$JAVA_HOME/bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar

