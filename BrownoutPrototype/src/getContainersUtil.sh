#!/bin/sh
ansible GreenContainerGroup -m shell -a 'sudo docker stats --no-stream'
exit