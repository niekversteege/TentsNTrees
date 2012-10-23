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

	/* Vertically placed hints: per row */
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
		return true;
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
		for (int i = 0; i < DEFAULT_BOARD_WIDTH; i++)
		{
			if (treesField[row][i] == TileTypes.EMPTY_TILE)
			{
				setTileAsGrass(i, row);
			}
		}
	}

	public void makeColumnGrass(int column)
	{
		for (int i = 0; i < DEFAULT_BOARD_HEIGHT; i++)
		{
			if (treesField[i][column] == TileTypes.EMPTY_TILE)
			{
				setTileAsGrass(column, i);
			}
		}
	}

	public boolean isEmptyTile(int column, int row)
	{
		if (isWithinBounds(column, row))
		{
			return false;
		}

		return treesField[row][column] == TileTypes.EMPTY_TILE;
	}

	public boolean isGrassTile(int column, int row)
	{
		if (isWithinBounds(column, row))
		{
			return false;
		}

		return treesField[row][column] == TileTypes.GRASS_TILE;
	}

	public boolean isTreeTile(int column, int row)
	{
		if (isWithinBounds(column, row))
		{
			return false;
		}

		return treesField[row][column] == TileTypes.TREE_TILE;
	}

	public boolean isTentTile(int column, int row)
	{
		if (isWithinBounds(column, row))
		{
			return false;
		}

		return treesField[row][column] == TileTypes.TENT_TILE;
	}

	private boolean isWithinBounds(int column, int row)
	{
		return column < 0 || column >= DEFAULT_BOARD_WIDTH || row < 0
				|| row >= DEFAULT_BOARD_HEIGHT;
	}

	public boolean hasAdjacentTrees(int column, int row)
	{
		if (isTreeTile(column + 1, row))
		{
			return true;
		}
		if (isTreeTile(column - 1, row))
		{
			return true;
		}
		if (isTreeTile(column, row + 1))
		{
			return true;
		}
		if (isTreeTile(column, row - 1))
		{
			return true;
		}
		if (isTreeTile(column - 1, row - 1))
		{
			return true;
		}
		if (isTreeTile(column + 1, row + 1))
		{
			return true;
		}

		return false;
	}

	public boolean setTileAsGrass(int column, int row)
	{
		return setTile(row, column, TileTypes.GRASS_TILE);
	}
	
	public boolean setTileAsTent(int column, int row)
	{
		return setTile(row, column, TileTypes.TENT_TILE);
	}

	public int getNrOfEmptyOrTentTilesInColumn(int column)
	{
		int retVal = 0;
		
		for (int i = 0; i < DEFAULT_BOARD_HEIGHT; i++)
		{
			if (isEmptyTile(column, i) || isTentTile(column, i))
			{
				retVal++;
			}
		}
		return retVal;
	}
	
	public int getNrOfEmptyOrTentTilesInRow(int row)
	{
		int retVal = 0;
		
		for (int i = 0; i < DEFAULT_BOARD_WIDTH; i++)
		{
			if (isEmptyTile(i, row) || isTentTile(i, row))
			{
				retVal++;
			}
		}
		return retVal;
	}

	public boolean setEmptyColumnAsTents(int column)
	{
		for (int i = 0; i < DEFAULT_BOARD_HEIGHT; i++)
		{
			if (isEmptyTile(column, i))
			{
				if (!setTileAsTent(column, i))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean setEmptyRowAsTents(int row)
	{
		for (int i = 0; i < DEFAULT_BOARD_HEIGHT; i++)
		{
			if (isEmptyTile(i, row))
			{
				if (!setTileAsTent(i, row))
				{
					return false;
				}
			}
		}
		
		return true;
	}
}
