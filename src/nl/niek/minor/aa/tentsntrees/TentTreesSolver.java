package nl.niek.minor.aa.tentsntrees;

import java.util.ArrayList;
import java.util.List;

public class TentTreesSolver
{

	private TentTreesPrinter		printer;

	private TentsAndTrees			tentsAndTrees	= null;

	private boolean					solved			= false;

	private int						visitedTrees = 0;

	private int						totalTrees = 0;

	private List<TreeCoordinate>	trees;

	public TentTreesSolver()
	{
		printer = new TentTreesPrinter();
		trees = new ArrayList<TreeCoordinate>();
	}

	public TentTreesSolver(TentsAndTrees tentsAndTrees)
	{
		this();
		this.tentsAndTrees = tentsAndTrees;
	}

	public void go()
	{
		initBoard();
		printer.printLine("Start:");
		printer.printTentsAndTrees(tentsAndTrees);

		preSolve();

		printer.printLine("After preSolve:");
		printer.printTentsAndTrees(tentsAndTrees);

		solved = recursiveSolve();

		if (solved)
		{
			printer.printLine("After: ");
			printer.printTentsAndTrees(tentsAndTrees);
			
			printer.printLine("Validated: " + validate());
		}
		else
		{
			printer.printLine("Puzzle is unsolvable.");
		}
	}

	private boolean validate()
	{
		for (TileCoordinate t : trees)
		{
			if (!tentsAndTrees.hasTent(t))
			{
				return false;
			}
		}
		
		return true;
	}

	private void preSolve()
	{
		getAllTrees();
		/*
		 * look at rows and columns that should have zero tents mark the empty
		 * tiles in there as grass
		 */
		makeEmptyRowsGrass();
		makeEmptyColumnGrass();

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
	}

	private void getAllTrees()
	{
		for (int i = 0; i < tentsAndTrees.getHeight(); i++)
		{
			for (int j = 0; j < tentsAndTrees.getWidth(); j++)
			{
				if (tentsAndTrees.isTreeTile(j, i))
				{
					totalTrees++;
					trees.add(new TreeCoordinate(j, i));
				}
			}
		}
	}
	
	private boolean recursiveSolve()
	{
		return recursiveSolve(trees.get(0));
	}

	private boolean recursiveSolve(TreeCoordinate currentTree)
	{
		if (currentTree == null)
		{
			return visitedTrees == totalTrees;
		}

		printer.printLine("Tree " + (visitedTrees++ + 1) + " out of " + totalTrees + ".");

		TreeCoordinate nextTree = getNextTree();
		
		List<TileCoordinate> surroundingEmptyTiles = tentsAndTrees
				.getSurroundingEmptyTiles(currentTree);
		
		if (currentTree.hasTent())
		{
			tentsAndTrees.setTilesAsGrass(surroundingEmptyTiles);
			return recursiveSolve(nextTree);
		}


		if (surroundingEmptyTiles.isEmpty())
		{
			printer.printLine("No possible tents for this tree.");
			return false;
		}
		else
		{
			if (surroundingEmptyTiles.size() == 1)
			{
				TileCoordinate empty = surroundingEmptyTiles.get(0);
				if (!tentsAndTrees.canPlaceTent(empty))
				{
					return false;
				}

				tentsAndTrees.setTileAsTent(empty);
				printer.printLine("Set tent at " + empty + ". (1 possibility)");
				printer.printTentsAndTrees(tentsAndTrees);

				return recursiveSolve(nextTree);

			}
			else
			{
				boolean tentHasBeenSet = false;

				for (TileCoordinate emptyTile : surroundingEmptyTiles)
				{
					if (tentsAndTrees.canPlaceTent(emptyTile))
					{
						tentsAndTrees.setTileAsTent(emptyTile);
						tentHasBeenSet = true;
						printer.printLine("Set tent at " + emptyTile + ".");
						printer.printTentsAndTrees(tentsAndTrees);

						if (recursiveSolve(nextTree))
						{
							return true;
						}
						else
						{
							tentsAndTrees.setTileAsGrass(emptyTile);
							tentHasBeenSet = false;
							printer.printLine("Tent removed at " + emptyTile
									+ ".");
						}
					}
				}

				/*
				 * if the tent has been set at this point then success. If not:
				 * we have a tree without a tent, impossible i.e. we return
				 * false.
				 */
				return tentHasBeenSet;
			}
		}
	}
	
	private TreeCoordinate getNextTree()
	{
		if (visitedTrees >= trees.size())
		{
			return null;
		}
		return trees.get(visitedTrees);
	}

	/***
	 * TODO: cannot find tree in 8x8 example at: row 2 col 1
	 * 
	 * @param currentTile
	 * @return
	 */
	private TileCoordinate getNextTree(TileCoordinate currentTile)
	{
		int startRow = currentTile.getRow();
		int startColumn = currentTile.getColumn() + 1;

		if (startColumn == tentsAndTrees.getWidth())
		{
			startColumn = 0;
			startRow++;
		}

		for (int i = startRow; i < tentsAndTrees.getHeight(); i++)
		{
			for (int j = startColumn; j < tentsAndTrees.getWidth(); j++)
			{
				if (tentsAndTrees.isTreeTile(j, i))
				{
					return new TileCoordinate(j, i);
				}
			}
		}

		return null;
	}

	/**
	 * Check if the number of tents that are supposed to be in the row/column is
	 * equal to the number of empty spots (plus the number of existing tents).
	 * If so; put tents there.
	 */
	private void placeTentsByHintNumbers()
	{
		int width = tentsAndTrees.getWidth();
		int height = tentsAndTrees.getHeight();

		for (int i = 0; i < width; i++)
		{
			int columnHint = tentsAndTrees.getColumnHint(i);
			if (columnHint != 0)
			{
				List<TileCoordinate> emptyTentsInColumn = tentsAndTrees
						.getEmptyTentsInColumn(i);
				int nrOfEmptyOrTentTilesInColumn = emptyTentsInColumn.size();

				if (columnHint == nrOfEmptyOrTentTilesInColumn)
				{
					for (TileCoordinate empty : emptyTentsInColumn)
					{
						tentsAndTrees.setTileAsTent(empty);
						TreeCoordinate tree = tentsAndTrees.getTree(empty);
						
						if (tree != null)
						{
							setTentForTree(empty, tree);
						}
					}
				}
			}
		}

		for (int i = 0; i < height; i++)
		{
			int rowHint = tentsAndTrees.getRowHint(i);
			if (rowHint != 0)
			{
				List<TileCoordinate> emptyTentsInRow = tentsAndTrees
						.getEmptyTentsInRow(i);
				int nrOfEmptyOrTentTilesInRow = emptyTentsInRow.size();

				if (rowHint == nrOfEmptyOrTentTilesInRow)
				{
					for (TileCoordinate empty : emptyTentsInRow)
					{
						tentsAndTrees.setTileAsTent(empty);
						TreeCoordinate tree = tentsAndTrees.getTree(empty);
						
						if (tree != null)
						{
							setTentForTree(empty, tree);
						}
					}
				}
			}
		}
	}

	private void setTentForTree(TileCoordinate empty, TreeCoordinate tree)
	{
		for (TreeCoordinate t : trees)
		{
			if (t.equals(tree))
			{
				t.setTent(empty);
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
		int width = tentsAndTrees.getWidth();
		int height = tentsAndTrees.getHeight();

		for (int row = 0; row < height; row++)
		{
			for (int column = 0; column < width; column++)
			{
				if (tentsAndTrees.isEmptyTile(column, row))
				{
					if (!tentsAndTrees.hasAdjacentTrees(column, row))
					{
						tentsAndTrees.setTileAsGrass(column, row);
					}
				}
			}
		}
	}

	private void makeEmptyColumnGrass()
	{
		int width = tentsAndTrees.getWidth();

		for (int i = 0; i < width; i++)
		{
			if (tentsAndTrees.getColumnHint(i) == 0)
			{
				tentsAndTrees.makeColumnGrass(i);
			}
		}

	}

	private void makeEmptyRowsGrass()
	{
		int height = tentsAndTrees.getHeight();

		for (int i = 0; i < height; i++)
		{
			if (tentsAndTrees.getRowHint(i) == 0)
			{
				tentsAndTrees.makeRowGrass(i);
			}
		}

	}

	private void initBoard()
	{
		if (tentsAndTrees == null)
		{
			tentsAndTrees = new TentsAndTrees(
					PlayingFieldFactory.getDefaultExample());
		}
	}

}
