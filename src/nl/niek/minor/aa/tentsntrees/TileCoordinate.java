package nl.niek.minor.aa.tentsntrees;

public class TileCoordinate
{
	private int row;
	
	private int column;
	
	public TileCoordinate(int column, int row)
	{
		this.row = row;
		this.column = column;
	}

	public final int getRow()
	{
		return row;
	}

	public final int getColumn()
	{
		return column;
	}
	
	
}
