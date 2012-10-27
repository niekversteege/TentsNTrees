package nl.niek.minor.aa.tentsntrees;

import java.util.ArrayList;
import java.util.List;

public class TreesField
{
	private static final int	DEFAULT_BOARD_HEIGHT	= 5;

	private static final int	DEFAULT_BOARD_WIDTH		= 5;

	private TileTypes[][]		treesField;

	/* Horizontally placed hints: per column */
	private int[]				columnHints;

	/* Vertically placed hints: per row */
	private int[]				rowHints;

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

	public boolean canPlaceTent(TileCoordinate tile)
	{
		if (isEmptyTile(tile.getColumn(), tile.getRow()))
		{
			return !columnIsFull(tile.getColumn()) && !rowIsFull(tile.getRow())
					&& !hasAdjacentTentAllDirections(tile);
		}

		return false;
	}

	private boolean rowIsFull(int row)
	{
		int expectedNrOfTents = rowHints[row];
		int nrOfTentsFound = 0;

		for (int i = 0; i < DEFAULT_BOARD_WIDTH; i++)
		{
			if (treesField[row][i] == TileTypes.TENT_TILE)
			{
				nrOfTentsFound++;
			}
		}

		return nrOfTentsFound >= expectedNrOfTents;
	}

	private boolean columnIsFull(int column)
	{
		int expectedNrOfTents = columnHints[column];
		int nrOfTentsFound = 0;

		for (int i = 0; i < DEFAULT_BOARD_HEIGHT; i++)
		{
			if (treesField[i][column] == TileTypes.TENT_TILE)
			{
				nrOfTentsFound++;
			}
		}

		return nrOfTentsFound >= expectedNrOfTents;
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
	 */
	private void setTile(int row, int column, TileTypes tile)
	{
		treesField[row][column] = tile;
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
			if (isEmptyTile(i, row))
			{
				setTileAsGrass(i, row);
			}
		}
	}

	public void makeColumnGrass(int column)
	{
		for (int i = 0; i < DEFAULT_BOARD_HEIGHT; i++)
		{
			if (isEmptyTile(column, i))
			{
				setTileAsGrass(column, i);
			}
		}
	}

	public boolean isEmptyTile(int column, int row)
	{
		return isTileType(column, row, TileTypes.EMPTY_TILE);
	}

	public boolean isGrassTile(int column, int row)
	{
		return isTileType(column, row, TileTypes.GRASS_TILE);
	}

	public boolean isTreeTile(int column, int row)
	{
		return isTileType(column, row, TileTypes.TREE_TILE);
	}

	public boolean isTentTile(int column, int row)
	{
		return isTileType(column, row, TileTypes.TENT_TILE);
	}

	private boolean isTileType(int column, int row, TileTypes tile)
	{
		if (isWithinBounds(column, row))
		{
			return false;
		}

		return treesField[row][column] == tile;
	}

	private boolean isWithinBounds(int column, int row)
	{
		return column < 0 || column >= DEFAULT_BOARD_WIDTH || row < 0
				|| row >= DEFAULT_BOARD_HEIGHT;
	}

	/**
	 * Does this tile have a tree above, below, left or right of it?
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public boolean hasAdjacentTrees(int column, int row)
	{
		return hasNeighbouringTile(column, row, TileTypes.TREE_TILE);
	}

	private boolean hasNeighbouringTile(int column, int row, TileTypes tile)
	{
		if (isTileType(column + 1, row, tile))
		{
			return true;
		}
		if (isTileType(column - 1, row, tile))
		{
			return true;
		}
		if (isTileType(column, row + 1, tile))
		{
			return true;
		}
		if (isTileType(column, row - 1, tile))
		{
			return true;
		}

		return false;
	}

	private boolean hasDiagonallyNeighbouringTile(int column, int row,
			TileTypes tile)
	{
		if (isTileType(column + 1, row - 1, tile))
		{
			return true;
		}
		if (isTileType(column - 1, row + 1, tile))
		{
			return true;
		}
		if (isTileType(column - 1, row - 1, tile))
		{
			return true;
		}
		if (isTileType(column + 1, row + 1, tile))
		{
			return true;
		}

		return false;
	}

	public boolean hasAdjacentTentAllDirections(TileCoordinate tile)
	{
		int column = tile.getColumn();
		int row = tile.getRow();

		return hasNeighbouringTile(column, row, TileTypes.TENT_TILE)
				|| hasDiagonallyNeighbouringTile(column, row,
						TileTypes.TENT_TILE);
	}

	public void setTileAsGrass(int column, int row)
	{
		setTile(row, column, TileTypes.GRASS_TILE);
	}

	public void setTileAsTent(int column, int row)
	{
		setTile(row, column, TileTypes.TENT_TILE);
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

	public void setEmptyColumnAsTents(int column)
	{
		for (int i = 0; i < DEFAULT_BOARD_HEIGHT; i++)
		{
			if (isEmptyTile(column, i))
			{
				setTileAsTent(column, i);
			}
		}
	}

	public void setEmptyRowAsTents(int row)
	{
		for (int i = 0; i < DEFAULT_BOARD_HEIGHT; i++)
		{
			if (isEmptyTile(i, row))
			{
				setTileAsTent(i, row);
			}
		}
	}

	/**
	 * Given coordinates must have a tree.
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public List<TileCoordinate> getSurroundingEmptyTiles(int column, int row)
	{
		if (!isTreeTile(column, row))
		{
			throw new IllegalArgumentException("Given tile must be a tree.");
		}

		return getSurroundingTiles(column, row, TileTypes.EMPTY_TILE);
	}

	private List<TileCoordinate> getSurroundingTiles(int column, int row,
			TileTypes tile)
	{
		List<TileCoordinate> surroundingTiles = new ArrayList<TileCoordinate>();

		if (isTileType(column + 1, row, tile))
		{
			surroundingTiles.add(new TileCoordinate(column + 1, row));
		}
		if (isTileType(column - 1, row, tile))
		{
			surroundingTiles.add(new TileCoordinate(column - 1, row));
		}
		if (isTileType(column, row + 1, tile))
		{
			surroundingTiles.add(new TileCoordinate(column, row + 1));
		}
		if (isTileType(column, row - 1, tile))
		{
			surroundingTiles.add(new TileCoordinate(column, row - 1));
		}

		return surroundingTiles;
	}

	/**
	 * Set the tile at the given coordinate as a tent.
	 * 
	 * @param tile
	 */
	public void setTileAsTent(TileCoordinate tile)
	{
		setTileAsTent(tile.getColumn(), tile.getRow());
	}

	public void setTileAsGrass(TileCoordinate tile)
	{
		setTileAsGrass(tile.getColumn(), tile.getRow());
	}

	public boolean hasAdjacentUnoccupiedTrees(int column, int row)
	{
		List<TileCoordinate> unoccupiedTrees = getUnoccupiedAdjacentTrees(
				column, row);

		return unoccupiedTrees.size() > 0;
	}

	private List<TileCoordinate> getUnoccupiedAdjacentTrees(int column, int row)
	{
		List<TileCoordinate> adjacentTrees = getSurroundingTiles(column, row,
				TileTypes.TREE_TILE);
		List<TileCoordinate> unoccupiedTrees = new ArrayList<TileCoordinate>();

		for (TileCoordinate t : adjacentTrees)
		{
			if (isUnoccupiedTree(t))
			{
				unoccupiedTrees.add(t);
			}
		}

		return unoccupiedTrees;
	}

	public boolean isUnoccupiedTree(int column, int row)
	{
		return !hasNeighbouringTile(column, row, TileTypes.TENT_TILE);
	}

	private boolean isUnoccupiedTree(TileCoordinate tile)
	{
		return isUnoccupiedTree(tile.getColumn(), tile.getRow());
	}
}
