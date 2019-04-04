package brownout;

/**
 * Execute compose files to deploy services
 * @author minxianx
 *
 */
public class ComposeFileExecution extends CommandExecution{

	String remote_connection = " ssh ubuntu@192.168.0.120 ";

	public void initialDeploymentByComposeFile(){
		System.out.println("######Initial Deployment From Compose File######");
		String command = remote_connection + "sh scripts/initialDeployment.sh";
		executeCommandWithLessInfo(command);
	}
	
	public void updateDeploymentByComposeFile(){
		System.out.println("######Update Deployment From Compose File######");
		String command = remote_connection + "sh scripts/updateDeployment.sh";
		executeCommandWithLessInfo(command);
	}
}
