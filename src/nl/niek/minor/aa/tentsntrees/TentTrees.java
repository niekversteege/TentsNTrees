package nl.niek.minor.aa.tentsntrees;

import java.util.ArrayList;

public class TentTrees
{

	private TentTreesPrinter				printer;

	private TreesField						treesField	= null;

	private boolean							stop		= false;

	private boolean							unsolvable	= false;

	private ArrayList<TileCoordinate>[][]	surroundingEmptyTiles;

	public TentTrees()
	{
		printer = new TentTreesPrinter();
	}

	public TentTrees(TreesField treesField)
	{
		this();
		this.treesField = treesField;
	}

	public void go()
	{
		initBoard();
		printer.printLine("Before:");
		printer.printTreesField(treesField);

		solve();

		if (unsolvable)
		{
			printer.printLine("Puzzle is unsolvable.");
			return;
		}

		printer.printLine("After: ");
		printer.printTreesField(treesField);
	}

	private void solve()
	{
		/*
		 * look at rows and columns that should have zero tents mark the empty
		 * tiles in there as grass
		 */
		makeEmptyColumnGrass();
		makeEmptyRowsGrass();

		/* mark tiles that have no adjacent tree as grass */
		makeLoneTilesGrass();

		/*
		 * if number in hint equals number of empty tiles + number of existing
		 * tents: make empty tiles tents.
		 */
		placeTentsByHintNumbers();

		/*
		 * Tents made before this point cannot be changed: they have to be there
		 * according to logic.
		 */

		/*
		 * Make a list of possible tent locations per tree.
		 */
		initSurroundingEmptyTiles();

		recursiveSolve(0, 0);

		/*
		 * can we call placeTentsByHintNumbers recursively? expand lone tile
		 * method to count tiles with occupied trees as lone? call these until
		 * it is solved?
		 */

	}

	private void initSurroundingEmptyTiles()
	{
		for (int row = 0; row < treesField.getHeight(); row++)
		{
			for (int column = 0; column < treesField.getWidth(); column++)
			{
				if (treesField.isTreeTile(column, row))
				{
					surroundingEmptyTiles[row][column] = new ArrayList<TileCoordinate>();
					surroundingEmptyTiles[row][column].addAll(treesField
							.getSurroundingEmptyTiles(column, row));
				}
			}
		}
	}

	private void recursiveSolve(int row, int column)
	{
		if (column >= treesField.getWidth())
		{
			column = 0;
			row++;
		}
		if (row == treesField.getHeight() || stop || unsolvable)
		{
			stop = true;
			return;
		}
		
		printer.printLine("New recursiveSolve call for column: " + column + " row: " + row);
		printer.printTreesField(treesField);
		
		if (treesField.isTreeTile(column, row))
		{
			if (treesField.isUnoccupiedTree(column, row))
			{
				for (TileCoordinate emptyTile : surroundingEmptyTiles[row][column])
				{
					if (treesField.canPlaceTent(emptyTile))
					{
						treesField.setTileAsTent(emptyTile);
						recursiveSolve(row, column + 1);
					}
				}
			}
			else
			{
				recursiveSolve(row, column + 1);
			}
		}
		/*
		 * else if (treesField.isEmptyTile(column, row)) { if
		 * (!treesField.hasAdjacentUnoccupiedTrees(column, row)) {
		 * treesField.setTileAsGrass(column, row); }
		 * 
		 * recursiveSolve(row, column + 1); }
		 */
		else
		{
			recursiveSolve(row, column + 1);
		}
	}

	/**
	 * Check if the number of tents that are supposed to be in the row/column is
	 * equal to the number of empty spots (plus the number of existing tents).
	 * If so; put tents there.
	 */
	private void placeTentsByHintNumbers()
	{
		int width = treesField.getWidth();
		int height = treesField.getHeight();

		for (int i = 0; i < width; i++)
		{
			int columnHint = treesField.getColumnHint(i);
			if (columnHint != 0)
			{
				int nrOfEmptyOrTentTilesInColumn = treesField
						.getNrOfEmptyOrTentTilesInColumn(i);

				if (columnHint == nrOfEmptyOrTentTilesInColumn)
				{
					treesField.setEmptyColumnAsTents(i);
				}
			}
		}

		for (int i = 0; i < height; i++)
		{
			int rowHint = treesField.getRowHint(i);
			if (rowHint != 0)
			{
				int nrOfEmptyOrTentTilesInRow = treesField
						.getNrOfEmptyOrTentTilesInRow(i);

				if (rowHint == nrOfEmptyOrTentTilesInRow)
				{
					treesField.setEmptyRowAsTents(i);
				}
			}
		}
	}

	/**
	 * Find tiles that have no trees surrounding them (horizontally and
	 * vertically). If found these are made to grass tiles since they cannot
	 * ever house a tent.
	 */
	private void makeLoneTilesGrass()
	{
		int width = treesField.getWidth();
		int height = treesField.getHeight();

		for (int row = 0; row < height; row++)
		{
			for (int column = 0; column < width; column++)
			{
				if (treesField.isEmptyTile(row, column))
				{
					if (!treesField.hasAdjacentTrees(column, row))
					{
						treesField.setTileAsGrass(column, row);
					}
				}
			}
		}
	}

	private void makeEmptyRowsGrass()
	{
		int width = treesField.getWidth();

		for (int i = 0; i < width; i++)
		{
			if (treesField.getColumnHint(i) == 0)
			{
				treesField.makeColumnGrass(i);
			}
		}

	}

	private void makeEmptyColumnGrass()
	{
		int height = treesField.getHeight();

		for (int i = 0; i < height; i++)
		{
			if (treesField.getRowHint(i) == 0)
			{
				treesField.makeRowGrass(i);
			}
		}

	}

	private void initBoard()
	{
		if (treesField == null)
		{
			treesField = new TreesField();
			treesField.setDefault();
		}

		surroundingEmptyTiles = new ArrayList[treesField.getHeight()][treesField
				.getWidth()];
	}

}
