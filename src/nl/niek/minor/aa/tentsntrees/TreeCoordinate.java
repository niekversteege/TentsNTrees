package nl.niek.minor.aa.tentsntrees;

public class TreeCoordinate extends TileCoordinate
{
	private TileCoordinate	tent	= null;

	public TreeCoordinate(int column, int row)
	{
		super(column, row);
	}

	public TreeCoordinate(TileCoordinate tileCoordinate)
	{
		this(tileCoordinate.getColumn(), tileCoordinate.getRow());
	}

	public final TileCoordinate getTent()
	{
		return tent;
	}

	public final void setTent(TileCoordinate empty)
	{
		this.tent = empty;
	}

	public final boolean hasTent()
	{
		return tent != null;
	}
}
