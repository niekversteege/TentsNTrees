package nl.niek.minor.aa.tentsntrees;

import java.util.ArrayList;
import java.util.List;

public class TentsAndTrees
{
	private int				height;

	private int				width;

	private TileTypes[][]	gameField;

	/* Horizontally placed hints: per column */
	private int[]			columnHints;

	/* Vertically placed hints: per row */
	private int[]			rowHints;

	public TentsAndTrees(PlayingField playingField)
	{
		if (playingField == null)
		{
			throw new IllegalArgumentException("PlayingField is null.");
		}

		gameField = playingField.getPlayingField();
		columnHints = playingField.getColumnHints();
		rowHints = playingField.getRowHints();

		this.height = gameField.length;
		this.width = gameField[0].length;
	}

	public boolean canPlaceTent(TileCoordinate tile)
	{
		if (isEmptyTile(tile.getColumn(), tile.getRow()))
		{
			return !columnIsFull(tile.getColumn()) && !rowIsFull(tile.getRow())
					&& !hasAdjacentTentInAnyDirection(tile);
		}

		return false;
	}

	private boolean rowIsFull(int row)
	{
		int expectedNrOfTents = rowHints[row];
		int nrOfTentsFound = 0;

		for (int i = 0; i < width; i++)
		{
			if (gameField[row][i] == TileTypes.TENT_TILE)
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

		for (int i = 0; i < height; i++)
		{
			if (gameField[i][column] == TileTypes.TENT_TILE)
			{
				nrOfTentsFound++;
			}
		}

		return nrOfTentsFound >= expectedNrOfTents;
	}

	/**
	 * 
	 * @param row
	 * @param column
	 * @param tile
	 */
	private void setTile(int row, int column, TileTypes tile)
	{
		if (tile == TileTypes.TREE_TILE)
		{
			throw new IllegalArgumentException("Cannot change tree tiles!");
		}

		gameField[row][column] = tile;
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
		return height;
	}

	public final int getWidth()
	{
		return width;
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

		for (int i = 0; i < gameField.length; i++)
		{
			fieldString.append(rowHints[i] + " ");
			for (int j = 0; j < gameField[i].length; j++)
			{
				fieldString.append("[" + getTileString(gameField[i][j]) + "]");
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
		for (int i = 0; i < width; i++)
		{
			if (isEmptyTile(i, row))
			{
				setTileAsGrass(i, row);
			}
		}
	}

	public void makeColumnGrass(int column)
	{
		for (int i = 0; i < height; i++)
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

		return gameField[row][column] == tile;
	}

	private boolean isWithinBounds(int column, int row)
	{
		return column < 0 || column >= width || row < 0 || row >= height;
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

	public boolean hasAdjacentTentInAnyDirection(TileCoordinate tile)
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

	public List<TileCoordinate> getEmptyTentsInColumn(int column)
	{
		List<TileCoordinate> retVal = new ArrayList<TileCoordinate>();

		for (int i = 0; i < height; i++)
		{
			if (isEmptyTile(column, i) || isTentTile(column, i))
			{
				retVal.add(new TileCoordinate(column, i));
			}
		}
		return retVal;
	}

	public List<TileCoordinate> getEmptyTentsInRow(int row)
	{
		List<TileCoordinate> retVal = new ArrayList<TileCoordinate>();

		for (int i = 0; i < width; i++)
		{
			if (isEmptyTile(i, row) || isTentTile(i, row))
			{
				retVal.add(new TileCoordinate(i, row));
			}
		}
		return retVal;
	}

	/**
	 * Given coordinates must have a tree. Excludes empty tiles that conflict
	 * with existing tents and sets these as grass.
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public List<TileCoordinate> getSurroundingEmptyTiles(
			TileCoordinate currentTree)
	{
		int column = currentTree.getColumn();
		int row = currentTree.getRow();

		if (!isTreeTile(column, row))
		{
			throw new IllegalArgumentException("Given tile must be a tree.");
		}

		List<TileCoordinate> retVal = new ArrayList<TileCoordinate>();

		for (TileCoordinate t : getSurroundingTiles(column, row,
				TileTypes.EMPTY_TILE))
		{
			if (hasAdjacentTentInAnyDirection(t))
			{
				/* setTileAsGrass(t); */
			}
			else
			{
				retVal.add(t);
			}
		}

		return retVal;
	}

	private List<TileCoordinate> getSurroundingTiles(int column, int row,
			TileTypes tile)
	{
		List<TileCoordinate> surroundingTiles = new ArrayList<TileCoordinate>();

		if (isTileType(column - 1, row, tile))
		{
			surroundingTiles.add(new TileCoordinate(column - 1, row));
		}
		if (isTileType(column, row - 1, tile))
		{
			surroundingTiles.add(new TileCoordinate(column, row - 1));
		}
		if (isTileType(column + 1, row, tile))
		{
			surroundingTiles.add(new TileCoordinate(column + 1, row));
		}
		if (isTileType(column, row + 1, tile))
		{
			surroundingTiles.add(new TileCoordinate(column, row + 1));
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

	public boolean hasAdjacentUnoccupiedTrees(TileCoordinate tile)
	{
		return hasAdjacentUnoccupiedTrees(tile.getColumn(), tile.getRow());
	}

	public boolean hasTent(TileCoordinate tree)
	{
		return getSurroundingTiles(tree.getColumn(), tree.getRow(),
				TileTypes.TENT_TILE).size() > 0;
	}

	public TreeCoordinate getTree(TileCoordinate newTent)
	{
		List<TileCoordinate> adjacentTrees = getSurroundingTiles(
				newTent.getColumn(), newTent.getRow(), TileTypes.TREE_TILE);

		if (adjacentTrees.size() == 1)
		{
			return new TreeCoordinate(adjacentTrees.get(0));
		}

		return null;
	}

	public void setTilesAsGrass(List<TileCoordinate> surroundingEmptyTiles)
	{
		for (TileCoordinate t : surroundingEmptyTiles)
		{
			setTileAsGrass(t);
		}
	}
}
