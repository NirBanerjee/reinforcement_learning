import java.io.IOException;

/**
 * 
 */

/**
 * @author nirmoho-Mac
 *
 */
public class q_learning {

	
	/**
	 * Main method to perform q-learning.
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//Get all command line arguments
		String mazeFile = args[0];
		String valueFile = args[1];
		String qFile = args[2];
		String policyFile = args[3];
		int numEpochs = Integer.parseInt(args[4]);
		int episodeLength = Integer.parseInt(args[5]);
		double alpha = Double.parseDouble(args[6]);
		double gamma = Double.parseDouble(args[7]);
		double epsilon = Double.parseDouble(args[8]);
		
		//Initialize the env
		environment env = new environment(mazeFile);
		
		for (int n = 0; n < numEpochs; n++)	{
			performQLearning(env, episodeLength, alpha, gamma, epsilon);
		}
	}
}
