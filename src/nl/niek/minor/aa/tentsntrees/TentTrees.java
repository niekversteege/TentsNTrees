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
		// look at rows and columns that should have zero tents
		// mark those squares as grass
		makeEmptyColumnGrass();
		makeEmptyRowsGrass();
		
		// mark squares that have no adjacent tree as grass
		makeLoneSquaresGrass();
		
		// for every tree on the map
		// check possible tent locations
		// if 1 possibility; place there
		// if this conflicts with another tent, move that tent
		// if the conflicted tent has no alternative locations; error,
		// unsolvable
		// else if more locations
		// place in first
		// backtracking after this
	}

	private void makeLoneSquaresGrass()
	{
		
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
