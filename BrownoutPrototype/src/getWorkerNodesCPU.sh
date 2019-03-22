#!/bin/sh
ansible GreenContainerGroup -m shell -a 'sh getCpuUsage.sh'
# getCpuUsage.sh should be copied on all worker nodes
