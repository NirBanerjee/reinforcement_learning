/**
 * @author nirmoho-Mac
 *
 */
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class value_iteration {
	/**
	 * Method to read input file
	 * @param fileName
	 * @return
	 */
	public static StateParameters[][] readMaze(String fileName, int[] actionsList) throws IOException	{
		File file = new File(fileName);
		@SuppressWarnings("resource")
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
	}

}
