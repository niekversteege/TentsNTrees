package nl.niek.minor.aa.tentsntrees;

public class TreesField
{
	private static final int	DEFAULT_BOARD_HEIGHT	= 5;

	private static final int	DEFAULT_BOARD_WIDTH		= 5;

	public enum TileTypes
	{
		EMPTY_TILE, GRASS_TILE, TREE_TILE, TENT_TILE;

		public String toString()
		{
			return null;
		}
	}

	private TileTypes[][]	treesField;

	/* Horizontally placed hints: per column */
	private int[]			columnHints;

	/* Vertically placed hints: per row*/
	private int[]			rowHints;

	public TreesField()
	{
		treesField = new TileTypes[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
		columnHints = new int[DEFAULT_BOARD_WIDTH];
		rowHints = new int[DEFAULT_BOARD_HEIGHT];
		initBoard();
	}

	/**
	 * Set whole field to EMPTY.
	 */
	private void initBoard()
	{
		for (int i = 0; i < treesField.length; i++)
		{
			for (int j = 0; j < treesField[i].length; j++)
			{
				treesField[i][j] = TileTypes.EMPTY_TILE;
			}
		}
	}

	/**
	 * use default example board as seen on
	 * http://www.brainbashers.com/tentshelp.asp.
	 */
	public void setDefault()
	{
		/* Set hints */
		columnHints[0] = 2;
		columnHints[1] = 0;
		columnHints[2] = 1;
		columnHints[3] = 0;
		columnHints[4] = 2;

		rowHints[0] = 2;
		rowHints[1] = 0;
		rowHints[2] = 2;
		rowHints[3] = 0;
		rowHints[4] = 1;

		/* Set trees */
		treesField[0][1] = TileTypes.TREE_TILE;
		treesField[1][4] = TileTypes.TREE_TILE;
		treesField[2][1] = TileTypes.TREE_TILE;
		treesField[2][3] = TileTypes.TREE_TILE;
		treesField[3][4] = TileTypes.TREE_TILE;
	}

	private boolean isPossible(int row, int column, TileTypes tile)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public final TileTypes getTile(int row, int column)
	{
		return treesField[row][column];
	}

	/**
	 * 
	 * @param row
	 * @param column
	 * @param tile
	 * @return successful or not
	 */
	public final boolean setTile(int row, int column, TileTypes tile)
	{
		if (isPossible(row, column, tile))
		{
			treesField[row][column] = tile;
			return true;
		}

		return false;
	}

	public final int getColumnHint(int row)
	{
		return columnHints[row];
	}

	public final int getRowHint(int column)
	{
		return rowHints[column];
	}

	public final int getHeight()
	{
		return DEFAULT_BOARD_HEIGHT;
	}

	public final int getWidth()
	{
		return DEFAULT_BOARD_WIDTH;
	}

	public final String getPrintableField()
	{
		StringBuffer fieldString = new StringBuffer("");

		fieldString.append("  ");

		for (int i : columnHints)
		{
			fieldString.append(" " + i + " ");
		}

		fieldString.append("\n");

		for (int i = 0; i < treesField.length; i++)
		{
			fieldString.append(rowHints[i] + " ");
			for (int j = 0; j < treesField[i].length; j++)
			{
				fieldString.append("[" + getTileString(treesField[i][j]) + "]");
			}
			fieldString.append("\n");
		}

		return fieldString.toString();
	}

	private String getTileString(TileTypes tile)
	{
		String ret = "";

		switch (tile)
		{
		case GRASS_TILE:
			ret = ".";
			break;
		case TENT_TILE:
			ret = "^";
			break;
		case TREE_TILE:
			ret = "T";
			break;
		default:
			ret = " ";
			/* EMPTY_TILE */
			break;
		}

		return ret;
	}

	public void makeRowGrass(int row)
	{
		for (int i = 0; i < treesField[row].length; i++)
		{
			if (treesField[row][i] == TileTypes.EMPTY_TILE)
			{
				treesField[row][i] = TileTypes.GRASS_TILE;
			}
		}
	}

	public void makeColumnGrass(int column)
	{
		for (int i = 0; i < treesField[column].length; i++)
		{
			if (treesField[i][column] == TileTypes.EMPTY_TILE)
			{
				treesField[i][column] = TileTypes.GRASS_TILE;
			}
		}
	}
}
