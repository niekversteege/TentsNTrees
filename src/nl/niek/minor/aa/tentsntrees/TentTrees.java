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
		printer.printTreesField(treesField);

		solve();

		printer.printTreesField(treesField);
	}

	private void solve()
	{
		// look at rows and columns that should have zero tents
		// mark those squares as grass
		// mark squares that have no adjacent tree as grass
		// for every tree on the map
		// check possible tent locations
		// if 1 possibility; place there
		// if this conflicts with another tent, move that tent
		// if the conflicted tent has no alternative locations; error,
		// unsolvable
		// else if more locations
		// place in first
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
