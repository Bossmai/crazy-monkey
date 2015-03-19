#!/bin/bash

# clean the evn
source ./setenv.sh

# Git update and build
echo "[Crazy Monkey] Update the crazy monkey code..."
cd $CRAZY_MONKEY_HOME && git pull

echo "[Crazy Monkey] Build the project..."
cd $CRAZY_MONKEY_HOME && $ANT_HOME/bin/ant

# Clean the env
cd $CRAZY_MONKEY_HOME && /bin/bash ./stop.sh

echo "[VPN Client] Run the vpn client..."
cd $CRAZY_MONKEY_HOME && /bin/bash ./reset_vpn.sh
cd $VPN_CLINET_HOME && ./autorun
sleep 10

# Run the testing
$JAVA_HOME/bin/java -jar $CRAZY_MONKEY_HOME/crazy-monkey-0.1.jar

