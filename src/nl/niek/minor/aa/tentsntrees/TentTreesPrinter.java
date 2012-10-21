package nl.niek.minor.aa.tentsntrees;

public class TentTreesPrinter
{
	public TentTreesPrinter()
	{
		
	}
	
	public void printTreesField(final TreesField treesField)
	{		
		printLine(treesField.getPrintableField());
	}
	
	public void printNumber(Integer number)
	{
		print(" " + number.toString() + " ");
	}
	
	private void print(String string)
	{
		System.out.print(string);
	}

	public void printLine()
	{
		printLine("");
	}
	
	public void printLine(String toPrint)
	{
		System.out.println(toPrint);
	}
}
