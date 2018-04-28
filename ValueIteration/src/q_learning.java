import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @author nirmoho-Mac
 *
 */
public class q_learning {
	/**
	 * Method to transition to next step and get the qvalue for the move.
	 * @param currentState
	 * @param act
	 * @param gamma
	 * @return
	 */
	private static double getQVal(environment env, StateParameters currentState, int act, double gamma)	{
		double imReward = env.step(act);
		StateParameters newState = env.getAgentState();
		return (imReward + (gamma * newState.getValue()));
	}
	/**
	 * Method to get action.
	 * @param currentState
	 * @param epsilon
	 * @return selected action
	 */
	public static int getAction(StateParameters currentState, double epsilon)	{
		double[] qVals = currentState.getqValues();
		int act = 0;
		Random r = new Random();
		double probSelected = r.nextInt(1001)/1000.0;
		if (probSelected > epsilon)	{
			double max = Double.NEGATIVE_INFINITY;
			for (int i = 0; i < qVals.length; i++)	{
				if (max < qVals[i])	{
					max = qVals[i];
					act = i;
				}
			}
			return act;
		}
		act = r.nextInt(4);
		return act;
	}
	/**
	 * Method to perform QLearning for each iteration.
	 * @param env
	 * @param episodeLength
	 * @param alpha
	 * @param gamma
	 * @param epsilon
	 */
	public static void performQLearning(environment env, int episodeLength, double alpha, double gamma, double epsilon)	{
		env.reset();
		for (int k = 0; k < episodeLength; k++)	{
			if (env.getAgentState().isTerminal())	{
				break;
			}
			StateParameters currentState = env.getAgentState();
			double[] oldQVals = currentState.getqValues();
			int act = getAction(currentState, epsilon);
			double newVal = getQVal(env, currentState, act, gamma);
			oldQVals[act] = ((1 - alpha) *  oldQVals[act]) + (alpha * newVal);
			//Update V* value
			double max = Double.NEGATIVE_INFINITY;
			double idx = 0;
			for (int i = 0; i < oldQVals.length; i++)	{
				if (oldQVals[i] > max)	{
					max = oldQVals[i];
					idx = i;
				}
			}
			currentState.setValue(max);
			currentState.setPolicy(idx);
			currentState.setqValues(oldQVals);
		}
	}
	/**
	 * Utility method to print data to file.
	 * @param fileName
	 * @param lines
	 * @throws IOException
	 */
	public static void printFile(String fileName, List<String> lines) throws IOException	{
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		for (String line : lines)	{
			bw.write(line);
			bw.write("\n");
		}
		bw.close();
		fileWriter.close();
	}
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
		
		//Final Maze DS
		StateParameters[][] mazeDS = env.getMazeDS();
		
		//Print to Value File.
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < mazeDS.length; i++)	{
			for (int j = 0; j < mazeDS[0].length; j++)	{
				if (mazeDS[i][j].isObstacle())	{
					continue;
				}
				String str = mazeDS[i][j].getxCoord() + " " + mazeDS[i][j].getyCoord() + " " + mazeDS[i][j].getValue();
				lines.add(str);
			}
		}
		printFile(valueFile, lines);
		
		//Print to Policy File
		lines = new ArrayList<>();
		for (int i = 0; i < mazeDS.length; i++)	{
			for (int j = 0; j < mazeDS[0].length; j++)	{
				if (mazeDS[i][j].isObstacle())	{
					continue;
				}
				String str = mazeDS[i][j].getxCoord() + " " + mazeDS[i][j].getyCoord() + " " + mazeDS[i][j].getPolicy();
				lines.add(str);
			}
		}
		printFile(policyFile, lines);
				
		//Print to q Values file.
		lines = new ArrayList<>();
		for (int i = 0; i < mazeDS.length; i++)	{
			for (int j = 0; j < mazeDS[0].length; j++)	{
				if (mazeDS[i][j].isObstacle())	{
					continue;
				}
				double[] temp = mazeDS[i][j].getqValues();
				for (int k = 0; k < temp.length; k++)	{
					String str = mazeDS[i][j].getxCoord() + " " + mazeDS[i][j].getyCoord() + " " + k + " " + temp[k];
					lines.add(str);
				}
			}
		}
		printFile(qFile, lines);
	}
}
