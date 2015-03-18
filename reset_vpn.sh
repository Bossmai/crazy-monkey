#!/bin/bash

source ./setenv.sh

echo "Kill vpn-daemon"
pgrep vpn-daemon | xargs -rt kill -9 

echo "Kill the main"
ps aux | grep main | awk '{print $2}' | xargs -rt kill -9

echo "Off the vpn"
sudo poff vpnpptp

echo "Kill the pptp"
ps aux | grep pptp | awk '{print $2}' | xargs -rt sudo kill -9

default_route=$(ip route list | grep default | awk {'print $1'})
if [ ! -n "$default_route" ]; then
    echo "Add the default route"
    sudo ip route add default via $DEFAULT_GATEWAY dev $NETWORK_INTERFACE proto static
else 
    echo "Replace the default route"
    sudo ip route replace default via $DEFAULT_GATEWAY dev $NETWORK_INTERFACE proto static
fi