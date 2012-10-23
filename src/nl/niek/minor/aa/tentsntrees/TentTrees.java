package nl.niek.minor.aa.tentsntrees;

public class TentTrees
{

	private TentTreesPrinter	printer;

	private TreesField			treesField	= null;

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

		// Tents made before this point cannot be changed: they have to be there
		// according to logic.

		// can we call placeTentsByHintNumbers recursively?
		// expand lone tile method to count tiles with occupied trees as lone?
		// call these until it is solved?
		
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
	 * Find tiles that have no trees surrounding them (horizontally and vertically).
	 * If found these are made to grass tiles since they cannot ever house a
	 * tent.
	 */
	private void makeLoneTilesGrass()
	{
		int width = treesField.getWidth();
		int height = treesField.getHeight();

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (treesField.isEmptyTile(i, j))
				{
					if (!treesField.hasAdjacentTrees(i, j))
					{
						treesField.setTileAsGrass(i, j);
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
	}

}
