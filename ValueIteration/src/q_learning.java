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
	 * Method to return best Action
	 * @param qVals
	 * @return
	 */
	private static int getBestAction(double[] qVals)	{
		double max = Double.NEGATIVE_INFINITY;
		int act = 0;
		for (int i = 0; i < qVals.length; i++)	{
			if (qVals[i] > max)	{
				max = qVals[i];
				act = i;
			}
		}
		return act;
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
		double probSelected = Math.random();
		if (epsilon == 0.0 || probSelected >= epsilon)	{
			return getBestAction(qVals);
		}
		Random r = new Random();
		act = r.nextInt(4);
		return act;
	}
	/**
	 * 
	 */
	private static void updateValueAndPolicy(environment env)	{
		StateParameters[][] mazeDS = env.getMazeDS();
		for (int i = 0; i < mazeDS.length; i++)	{
			for (int j = 0; j < mazeDS[0].length; j++)	{
				double max = Double.NEGATIVE_INFINITY;
				int act = 0;
				double[] qVals = mazeDS[i][j].getqValues();
				for (int k = 0; k < qVals.length; k++)	{
					if (qVals[k] > max)	{
						max = qVals[k];
						act = k;
					}
				}
				mazeDS[i][j].setPolicy(act);
				mazeDS[i][j].setValue(max);
			}
		}
	}
	/**
	 * Method to perform QLearning for each iteration.
	 * @param env
	 * @param episodeLength
	 * @param alpha
	 * @param gamma
	 * @param epsilon
	 */
	public static int performQLearning(environment env, int episodeLength, double alpha, double gamma, double epsilon)	{
		int steps = 0;
		for (int k = 0; k < episodeLength; k++)	{
			StateParameters currentState = env.getAgentState();
			double[] oldQVals = currentState.getqValues();
			int act = getAction(currentState, epsilon);
		    double reward = env.step(act);
		    StateParameters newState = env.getAgentState();
		    double[] newQVals = newState.getqValues();
		    double max = Double.NEGATIVE_INFINITY;
		    for (int t = 0; t < newQVals.length; t++)	{
		    		if (newQVals[t] > max)	{
		    			max = newQVals[t];
		    		}
		    }
			oldQVals[act] = ((1 - alpha) *  oldQVals[act]) + (alpha * (reward + (gamma * max)));
			currentState.setqValues(oldQVals);
			steps++;
			if (env.getAgentState().isTerminal())	{
				break;
			}
		}
		return steps;
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
		
		long timeInit = System.currentTimeMillis();
		int steps = 0;
		for (int n = 0; n < numEpochs; n++)	{
			env.reset();
			steps = steps + performQLearning(env, episodeLength, alpha, gamma, epsilon);
			updateValueAndPolicy(env);
		}
		long timeExit = System.currentTimeMillis();
		System.out.println("Time Taken = " + (timeExit - timeInit));
		System.out.println("Steps = " + (steps) / 2000.0);
		updateValueAndPolicy(env);
		
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
