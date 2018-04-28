/**
 * @author nirmoho-Mac
 *
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class value_iteration {
	/**
	 * method to calculate the Values for each state and corresponding action.
	 * @param mazeDS
	 * @param s
	 * @param i
	 * @param j
	 * @param act
	 * @param gamma
	 * @return value corresponding to each state and action.
	 */
	public static double getQVals(StateParameters[][] mazeDS, StateParameters s, int i, int j, int act, double gamma)	{
		if (s.isTerminal())	{
			return 0.0;
		}
		int xCord = i;
		int yCord = j;
		if (act == 0)	{
			yCord = yCord - 1;
		}	else if (act == 1)	{
			xCord = xCord - 1;
		}	else if (act == 2)	{
			yCord = yCord + 1;
		}	else if (act == 3)	{
			xCord = xCord + 1;
		}
		StateParameters newState = null;
		if (xCord < 0 || yCord < 0 || xCord >= mazeDS.length || yCord >= mazeDS[0].length)	{
			newState = s;
		}	else	 if (mazeDS[xCord][yCord].isObstacle())	{
			newState = s;
		}	else	{
			newState = mazeDS[xCord][yCord];
		}
		
		double immediateReward = -1.0;
		double qVal = immediateReward + (gamma * newState.getValue());
		return qVal;
	}
	/**
	 * method to perform valueIterations Algorithm.
	 * @param mazeDS
	 * @param actionsList
	 * @param numEpoch
	 * @param gamma
	 */
	public static void valueIterationsAlgorithm(StateParameters[][] mazeDS, int[] actionsList, int numEpoch, double gamma)	{
		double[][] values = new double[mazeDS.length][mazeDS[0].length];
		double[][] policy = new double[mazeDS.length][mazeDS[0].length];
		
		for (int n = 0; n < numEpoch; n++)	{
			System.out.println("Running Epoch = " + (n+1));
			for (int i = 0; i < mazeDS.length; i++)	{
				for (int j = 0; j < mazeDS[0].length; j++)	{
					StateParameters s = mazeDS[i][j];
					if (s.isObstacle())	{
						continue;
					}
					double[] qVals = new double[actionsList.length];
					for (int a = 0; a < actionsList.length; a++)	{
						int act = actionsList[a];
						qVals[a] = getQVals(mazeDS, s, i, j, act, gamma);
					}
					mazeDS[i][j].setqValues(qVals);
					double max = Double.NEGATIVE_INFINITY;
					double maxIdx = 0;
					for (int idx = 0; idx < qVals.length; idx++)	{
						if (qVals[idx] > max)	{
							max = qVals[idx];
							maxIdx = idx;
						}
					}
					values[i][j] = max;
					policy[i][j] = maxIdx;
				}
			}
			for (int i = 0; i < mazeDS.length; i++)	{
				for (int j = 0; j < mazeDS[0].length; j++)	{
					mazeDS[i][j].setValue(values[i][j]);
					mazeDS[i][j].setPolicy(policy[i][j]);
				}
			}
		}
	}
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
	 * Method to read input file
	 * @param fileName
	 * @return maze as a DS
	 */
	public static StateParameters[][] readMaze(String fileName, int[] actionsList) throws IOException	{
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		int lineCount = 0;
		String line = "";
		while(scanner.hasNextLine())	{
			lineCount++;
			line = scanner.nextLine();
		}
		scanner.close();
		StateParameters[][] maze = new StateParameters[lineCount][line.length()];
		scanner = new Scanner(file);
		int i = 0;
		while(scanner.hasNextLine())	{
			line = scanner.nextLine();
			for (int j = 0; j < line.length(); j++)	{
				char ch = line.charAt(j);
				maze[i][j] = new StateParameters(i, j, actionsList, ch);
				if (ch == '*')	{
					maze[i][j].setObstacle(true);
				}
				maze[i][j].setStartFlag(false);
				maze[i][j].setTerminal(false);
			}
			i++;
		}
		maze[lineCount - 1][0].setStartFlag(true);
		maze[0][lineCount - 1].setTerminal(true);
		scanner.close();
		return maze;
	}
	/**
	 * Command Line Arguments.
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//Get all command line arguments
		String mazeFile = args[0];
		String valueFile = args[1];
		String qFile = args[2];
		String policyFile = args[3];
		int numEpoch = Integer.parseInt(args[4]);
		double gamma = Double.parseDouble(args[5]);
		
		int[] actionsList = {0, 1, 2, 3};
		StateParameters[][] mazeDS = readMaze(mazeFile, actionsList);
		valueIterationsAlgorithm(mazeDS, actionsList, numEpoch, gamma);
		
		//Print to Value File.
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < mazeDS.length; i++)	{
			for (int j = 0; j < mazeDS.length; j++)	{
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
			for (int j = 0; j < mazeDS.length; j++)	{
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
			for (int j = 0; j < mazeDS.length; j++)	{
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
