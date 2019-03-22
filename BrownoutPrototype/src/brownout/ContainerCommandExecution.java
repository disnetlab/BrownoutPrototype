package brownout;

import java.util.ArrayList;

import model.Container;

public class ContainerCommandExecution extends CommandExecution {

    /**
     * Get Container utilization of all running containers
     *
     * @return
     */
    String remote_connection = " ssh ubuntu@192.168.0.120 ";

    public String getContainersCPU() {
        String commandResults;
        String command = remote_connection + "sh scripts/getContainersUtil.sh";
        System.out.println("======Output Containers Utilization======");
        commandResults = executeCommand(command);
        return commandResults;
    }

    /**
     * Generate the containers with split operations from string
     *
     * @param containersInfo
     * @return
     */
    public ArrayList<Container> generateContainerList(String containersInfo) {
        ArrayList<Container> cl = new ArrayList<Container>();
        String[] stringLines = containersInfo.split("\\r?\\n");
        String hostName = null;
        String[] containerLine;

        String containerId;
        double cpuUtil;
        double memUtil;

        for (String singleStringLine : stringLines) {
            // Example: DockerCluster1 | SUCCESS | rc=0 >>
            if (singleStringLine.contains("SUCCESS") || singleStringLine.contains("success") ||
                    singleStringLine.contains("CHANGED")) {
                containerLine = singleStringLine.split(" \\| ");
                hostName = containerLine[0];
            }
            // Example: CONTAINERID CONTAINER_NAME CPU % MEM USAGE / LIMIT MEM % NET I/O BLOCK
            // I/O PIDS
            // Example: 04474571a642 NAME 0.00% 55.21MiB/3.36GiB 1.60% 4.22 kB /
            // 3.34 kB 0 B / 0 B 21
            if (singleStringLine.contains("%") && !singleStringLine.contains("CPU")) {
                containerLine = singleStringLine.split("\\s+ ");
                containerId = containerLine[0];
                cpuUtil = Double.parseDouble(containerLine[2].substring(0, containerLine[2].length() - 1));
                String mem = containerLine[3].split("/")[0].trim();
                memUtil = Double.parseDouble(mem.substring(0, mem.length() - 3));

                Container container = new Container(hostName, containerId, cpuUtil, memUtil);
                cl.add(container);
            }
        }
        return cl;

    }


    /**
     * Stop (deactivate) the containers in the deactivated container list
     *
     * @param p_deactivatedContainerList
     * @return
     */
    public String stopContatiners(ArrayList<Container> p_deactivatedContainerList) {
        String commandResults = null;
        String command;
        System.out.println("======Stop Containers======");
        String args_hostName;
        String arges_containerId;

        for (Container container : p_deactivatedContainerList) {
            args_hostName = container.getHostName();
            arges_containerId = container.getContainerId();
            // This shell file requires two parameters
            command = "sh stopContainer.sh " + args_hostName + " " + arges_containerId;
            commandResults = executeCommand(command);

        }
        return commandResults;
    }


}
