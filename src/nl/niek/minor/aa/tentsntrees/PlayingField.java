package nl.niek.minor.aa.tentsntrees;

public class PlayingField
{
	private TileTypes[][]	playingField;

	private int				widthAndheight;

	/* Horizontally placed hints: per column */
	private int[]			columnHints;

	/* Vertically placed hints: per row */
	private int[]			rowHints;

	public PlayingField(TileTypes[][] playingField, int[] columnHints,
			int[] rowHints)
	{
		checkArguments(playingField, columnHints, rowHints);

		this.widthAndheight = playingField.length;
		this.playingField = playingField;
		this.columnHints = columnHints;
		this.rowHints = rowHints;
	}

	private void checkArguments(TileTypes[][] playingField, int[] columnHints,
			int[] rowHints)
	{
		if (playingField.length != playingField[0].length)
		{
			invalidArgs("Board must be square! (width and height must be the same)");
		}

		if (playingField.length != columnHints.length)
		{
			invalidArgs("Column hints must be the same as board width.");
		}

		if (playingField.length != rowHints.length)
		{
			invalidArgs("Row hints must be the same as board height.");
		}

		for (int i : columnHints)
		{
			if (i > playingField.length)
			{
				invalidArgs("Column hints contain too many tents.");
			}
		}

		for (int i : rowHints)
		{
			if (i > playingField.length)
			{
				invalidArgs("Row hints contain too many tents.");
			}
		}
	}

	private void invalidArgs(String message)
	{
		throw new IllegalArgumentException(message);
	}

	public final int getPlayingFieldWidthAndHeight()
	{
		return widthAndheight;
	}

	public final TileTypes[][] getPlayingField()
	{
		return playingField;
	}

	public final int[] getColumnHints()
	{
		return columnHints;
	}

	public final int[] getRowHints()
	{
		return rowHints;
	}
}
