/**
 * @author nirmoho-Mac
 * 
 * Class to Store State Variables
 *
 */
public class StateParameters {
	
	/**
	 * Private variable hold x coordinate of state. 
	 */
	private int xCoord;
	/**
	 * Private variable hold y coordinate of state. 
	 */
	private int yCoord;
	/**
	 * Array to hold q values for a particular state
	 */
	private double[] qValues;
	/**
	 * Variable hold V*(s) for the state
	 */
	private double value;
	/**
	 * Variable to hold pi*(s) for the state
	 */
	private double policy;
	/**
	 * Variable to hold maze value
	 */
	private char cellVal;
	/**
	 * Terminal Flag.
	 */
	private boolean isTerminal;
	/**
	 * Obstacle Flag.
	 */
	private boolean isObstacle;
	/**
	 * Start cell flag
	 */
	private boolean isStartFlag;
	/**
	 * Default Constructor.
	 */
	public StateParameters()	{
		this.xCoord = 0;
		this.yCoord = 0;
		this.value = 0;
		this.policy = 0;
	}
	/**
	 * Parameterized constructor.
	 * @param xCoord - of state
	 * @param yCoord - of state
	 * @param actionList - List of all possible actions.
	 */
	public StateParameters(int xCoord, int yCoord, int[] actionList, char cell) {
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.value = 0.0;
		this.policy = 0.0;
		this.qValues = new double[actionList.length];
		this.cellVal = cell;
	}
	/**
	 * Parameterized constructor.
	 * @param obj
	 */
	public StateParameters(StateParameters obj) {
		super();
		this.xCoord = obj.getxCoord();
		this.yCoord = obj.getyCoord();
		this.qValues = obj.getqValues();
		this.value = obj.getValue();
		this.policy = obj.getPolicy();
		this.cellVal = obj.getCellVal();
		this.isTerminal = obj.isTerminal();
		this.isObstacle = obj.isObstacle();
		this.isStartFlag = obj.isStartFlag();
	}
	/**
	 * Getter for xCoord.
	 * @return xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}
	/**
	 * Setter for xCoord.
	 * @param xCoord
	 */
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	/**
	 * Getter for yCoord.
	 * @return yCoord
	 */
	public int getyCoord() {
		return yCoord;
	}
	/**
	 * Setter for yCoord
	 * @param yCoord
	 */
	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	/**
	 * Getter for qValues.
	 * @return
	 */
	public double[] getqValues() {
		double[] retArray = new double[this.qValues.length];
		System.arraycopy(this.qValues, 0, retArray, 0, this.qValues.length);
		return retArray;
	}
	/**
	 * Setter for array
	 * @param qValues
	 */
	public void setqValues(double[] qValues) {
		this.qValues = qValues;
	}
	/**
	 * Getter for optimal Value of State
	 * @return
	 */
	public double getValue() {
		return value;
	}
	/**
	 * Setter for optimal Value
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * Getter for optimal policy.
	 * @return policy
	 */
	public double getPolicy() {
		return policy;
	}
	/**
	 * Setter for optimal policy.
	 * @param policy
	 */
	public void setPolicy(double policy) {
		this.policy = policy;
	}
	@Override
	public String toString()	{
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("Value = " + this.cellVal);
		sb.append(" ]");
		return sb.toString();
	}
	/**
	 * Get Value in maze.
	 * @return
	 */
	public char getCellVal() {
		return cellVal;
	}
	/**
	 * set cell value.
	 * @param cellVal
	 */
	public void setCellVal(char cellVal) {
		this.cellVal = cellVal;
	}
	/**
	 * is Terminal Cell getter.
	 * @return true/false
	 */
	public boolean isTerminal() {
		return isTerminal;
	}
	/**
	 * setTerminxal setter.
	 * @param isTerminal
	 */
	public void setTerminal(boolean isTerminal) {
		this.isTerminal = isTerminal;
	}
	/**
	 * is Obstacle getter
	 * @return
	 */
	public boolean isObstacle() {
		return isObstacle;
	}
	/**
	 * setter for obstacle
	 * @param isObstacle
	 */
	public void setObstacle(boolean isObstacle) {
		this.isObstacle = isObstacle;
	}
	/**
	 * check if start terminal.
	 * @return
	 */
	public boolean isStartFlag() {
		return isStartFlag;
	}
	/**
	 * set start flag.
	 * @param isStartFlag
	 */
	public void setStartFlag(boolean isStartFlag) {
		this.isStartFlag = isStartFlag;
	}
}
