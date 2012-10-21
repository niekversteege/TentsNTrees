package nl.niek.minor.aa.tentsntrees;


public class TreesField
{
	private static final int	DEFAULT_BOARD_HEIGHT	= 5;

	private static final int	DEFAULT_BOARD_WIDTH		= 5;

	public enum Tiles
	{
		EMPTY_TILE, GRASS_TILE, TREE_TILE, TENT_TILE
	}

	private Tiles[][]	treesField;

	private int[]	rowHints;

	private int[]	columnHints;

	public TreesField()
	{
		treesField = new Tiles[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
		rowHints = new int[DEFAULT_BOARD_WIDTH];
		columnHints = new int[DEFAULT_BOARD_HEIGHT];
		initBoard();
	}

	private void initBoard()
	{
		for (int i = 0; i < treesField.length; i++)
		{
			for (int j = 0; j < treesField[i].length; j++)
			{
				treesField[i][j] = Tiles.EMPTY_TILE;
			}
		}
	}

	/**
	 * use default example board as seen on
	 * http://www.brainbashers.com/tentshelp.asp.
	 */
	public void setDefault()
	{
		rowHints[0] = 2;
		rowHints[1] = 0;
		rowHints[2] = 1;
		rowHints[3] = 0;
		rowHints[4] = 2;

		columnHints[0] = 2;
		columnHints[1] = 0;
		columnHints[2] = 2;
		columnHints[3] = 0;
		columnHints[4] = 1;

	}

	public final String getPrintableField()
	{
		return null;
	}
}
