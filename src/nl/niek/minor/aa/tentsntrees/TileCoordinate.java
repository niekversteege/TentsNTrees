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
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof TileCoordinate)
		{
			TileCoordinate c = (TileCoordinate) obj;
			
			return this.getColumn() == c.getColumn() && this.getRow() == c.getRow();
		}
		
		return false;
	}
	
	@Override
	public String toString()
	{
		return "Column: " + column + " Row: " + row;
	}
}
