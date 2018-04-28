import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	 * Private variable to hold the index of start state.
	 */
	private int startIndex;
	
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
					this.startIndex = i;
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
		this.agentState = new StateParameters(startIndex, 0, actionList, 'S');
		this.agentState.setStartFlag(true);
	}
	/**
	 * Method to update agent state based on the 
	 * @param action
	 * @return
	 */
	public double step(int action)	{
		if (this.agentState.isTerminal())	{
			return 0.0;
		}
		
		int xCord = this.agentState.getxCoord();
		int yCord = this.agentState.getyCoord();
		
		if (action == 0)	{
			yCord = yCord - 1;
		}	else if (action == 1)	{
			xCord = xCord - 1;
		}	else if (action == 2)	{
			yCord = yCord + 1;
		}	else if (action == 3)	{
			xCord = xCord + 1;
		}
		
		StateParameters newState = null;
		if (xCord < 0 || yCord < 0 || xCord >= mazeDS.length || yCord >= mazeDS[0].length)	{
			newState = this.agentState;
		}	else	 if (mazeDS[xCord][yCord].isObstacle())	{
			newState = this.agentState;
		}	else	{
			newState = mazeDS[xCord][yCord];
		}
		this.agentState = newState;
		return -1.0;
	}
	/**
	 * Get the current state of the maze.
	 * @return
	 */
	public StateParameters[][] getMazeDS() {
		return mazeDS;
	}
	/**
	 * Get the current state of the agent.
	 * @return
	 */
	public StateParameters getAgentState() {
		return agentState;
	}
	/**
	 * Get the index of the start cell.
	 * @return
	 */
	public int getStartIndex() {
		return startIndex;
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
	 * Utility method to write to feedback file.
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
		
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < actionsInt.length; i++)	{
			double reward = env.step(actionsInt[i]);
			int isTerm = env.agentState.isTerminal() ? 1 : 0;
			String str = env.agentState.getxCoord() + " " + env.agentState.getyCoord() + " " + reward + " " + isTerm;
			lines.add(str);
		}
		printFile(outputFile, lines);
	}
	
}
