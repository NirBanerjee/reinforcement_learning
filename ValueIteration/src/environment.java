import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author nirmoho-Mac
 *
 */
public class environment {
	/**
	 * 2-D Array of State parameter object to host the dimesions of the maze.
	 */
	private StateParameters[][] mazeDS;
	
	/**
	 * State parameter object to hold the agent state.
	 */
	private StateParameters agentState;
	
	/**
	 * Constructor method to read the file and generate maze data structure.
	 * @param fileName - containing the maze
	 * @throws IOException
	 */
	public environment(String fileName) throws IOException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		int lineCount = 0;
		String line = "";
		while(scanner.hasNextLine())	{
			lineCount++;
			line = scanner.nextLine();
		}
		scanner.close();
		int[] actionList = { 0, 1, 2, 3};
		this.mazeDS = new StateParameters[lineCount][line.length()];
		scanner = new Scanner(file);
		int i = 0;
		while(scanner.hasNextLine())	{
			line = scanner.nextLine();
			for (int j = 0; j < line.length(); j++)	{
				char ch = line.charAt(j);
				this.mazeDS[i][j] = new StateParameters(i, j, actionList, ch);
				this.mazeDS[i][j].setStartFlag(false);
				this.mazeDS[i][j].setTerminal(false);
				this.mazeDS[i][j].setObstacle(false);
				if (ch == '*')	{
					this.mazeDS[i][j].setObstacle(true);
				}	else if (ch == 'G')	{
					this.mazeDS[i][j].setTerminal(true);
				}	else if (ch == 'S')	{
					this.mazeDS[i][j].setStartFlag(true);
				}
			}
			i++;
		}
		this.reset();
		scanner.close();
	}
	/**
	 * Method to reset the agentState to initial stage.
	 */
	public void reset()	{
		int[] actionList = { 0, 1, 2, 3};
		this.agentState = new StateParameters(0, 0, actionList, 'S');
		this.agentState.setStartFlag(true);
	}
	public double reward(int action)	{
		return 0.0;
	}
	/**
	 * Utility method to read action sequence file.
	 * @param sequenceFile
	 * @return List with squence of actions.
	 */
	public static String[] readActionSequence(String sequenceFile) throws IOException	{
		File file = new File(sequenceFile);
		Scanner scanner = new Scanner(file);
		String line = scanner.nextLine();
		String[] actions = line.split(" ");
		scanner.close();
		return actions;
	}
	/**
	 * Main method to test the environment class
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		String fileName = args[0];
		String outputFile = args[1];
		String sequenceFile = args[2];
		
		environment env = new environment(fileName);
		String[] actions = readActionSequence(sequenceFile);
		int[] actionsInt = new int[actions.length];
		for (int i = 0; i < actions.length; i++)	{
			actionsInt[i] = Integer.parseInt(actions[i]);
		}
		
		for (int i = 0; i < actionsInt.length; i++)	{
			
		}

	}

}
