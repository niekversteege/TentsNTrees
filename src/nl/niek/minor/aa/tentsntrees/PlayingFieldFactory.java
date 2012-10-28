package nl.niek.minor.aa.tentsntrees;

public class PlayingFieldFactory
{
	/**
	 * use default example board as seen on
	 * http://www.brainbashers.com/tentshelp.asp.
	 * 
	 * <table>
	 * <tr>
	 * <td></td>
	 * <td>2</td>
	 * <td>0</td>
	 * <td>1</td>
	 * <td>0</td>
	 * <td>2</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * </tr>
	 * <tr>
	 * <td>0</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * <td>[ ]</td>
	 * </tr>
	 * <tr>
	 * <td>0</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * </tr>
	 * </table>
	 */
	public static PlayingField getDefaultExample()
	{
		int widthAndHeight = 5;

		int[] columnHints = new int[widthAndHeight];

		/* Horizontally placed hints: per column */
		columnHints[0] = 2;
		columnHints[1] = 0;
		columnHints[2] = 1;
		columnHints[3] = 0;
		columnHints[4] = 2;

		int[] rowHints = new int[widthAndHeight];

		/* Vertically placed hints: per row */
		rowHints[0] = 2;
		rowHints[1] = 0;
		rowHints[2] = 2;
		rowHints[3] = 0;
		rowHints[4] = 1;

		/* Set trees */
		TileTypes[][] gameField = new TileTypes[widthAndHeight][widthAndHeight];

		initBoard(gameField);

		gameField[0][1] = TileTypes.TREE_TILE;
		gameField[1][4] = TileTypes.TREE_TILE;
		gameField[2][1] = TileTypes.TREE_TILE;
		gameField[2][3] = TileTypes.TREE_TILE;
		gameField[3][4] = TileTypes.TREE_TILE;

		return new PlayingField(gameField, columnHints, rowHints);
	}

	/**
	 * use another example board as seen on
	 * http://www.brainbashers.com/tentshelp.asp.
	 * 
	 * <table>
	 * <tr>
	 * <td></td>
	 * <td>0</td>
	 * <td>2</td>
	 * <td>0</td>
	 * <td>2</td>
	 * <td>1</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * <td>[ ]</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>[T]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * <td>[T]</td>
	 * <td>[ ]</td>
	 * <td>[ ]</td>
	 * </tr>
	 * </table>
	 */
	public static PlayingField getDifferentExample()
	{
		int widthAndHeight = 5;

		int[] columnHints = new int[widthAndHeight];

		/* Horizontally placed hints: per column */
		columnHints[0] = 0;
		columnHints[1] = 2;
		columnHints[2] = 0;
		columnHints[3] = 2;
		columnHints[4] = 1;

		int[] rowHints = new int[widthAndHeight];

		/* Vertically placed hints: per row */
		rowHints[0] = 1;
		rowHints[1] = 1;
		rowHints[2] = 1;
		rowHints[3] = 1;
		rowHints[4] = 1;

		/* Set trees */
		TileTypes[][] gameField = new TileTypes[widthAndHeight][widthAndHeight];

		initBoard(gameField);

		gameField[0][2] = TileTypes.TREE_TILE;
		gameField[1][2] = TileTypes.TREE_TILE;
		gameField[2][3] = TileTypes.TREE_TILE;
		gameField[3][0] = TileTypes.TREE_TILE;
		gameField[4][2] = TileTypes.TREE_TILE;

		return new PlayingField(gameField, columnHints, rowHints);
	}
	
	public static PlayingField getEightByEightExample()
	{
		int widthAndHeight = 8;

		int[] columnHints = new int[widthAndHeight];

		/* Horizontally placed hints: per column */
		columnHints[0] = 2;
		columnHints[1] = 1;
		columnHints[2] = 2;
		columnHints[3] = 0;
		columnHints[4] = 3;
		columnHints[5] = 1;
		columnHints[6] = 2;
		columnHints[7] = 1;

		int[] rowHints = new int[widthAndHeight];

		/* Vertically placed hints: per row */
		rowHints[0] = 0;
		rowHints[1] = 3;
		rowHints[2] = 1;
		rowHints[3] = 2;
		rowHints[4] = 2;
		rowHints[5] = 1;
		rowHints[6] = 2;
		rowHints[7] = 1;

		/* Set trees */
		TileTypes[][] gameField = new TileTypes[widthAndHeight][widthAndHeight];

		initBoard(gameField);

		gameField[0][2] = TileTypes.TREE_TILE;
		gameField[0][4] = TileTypes.TREE_TILE;
		
		gameField[1][5] = TileTypes.TREE_TILE;
		
		gameField[2][1] = TileTypes.TREE_TILE;
		gameField[2][7] = TileTypes.TREE_TILE;
		
		gameField[3][6] = TileTypes.TREE_TILE;
		
		gameField[4][3] = TileTypes.TREE_TILE;
		gameField[4][4] = TileTypes.TREE_TILE;
		
		gameField[5][0] = TileTypes.TREE_TILE;
		
		gameField[6][2] = TileTypes.TREE_TILE;
		gameField[6][5] = TileTypes.TREE_TILE;
		
		gameField[7][5] = TileTypes.TREE_TILE;
		

		return new PlayingField(gameField, columnHints, rowHints);
	}

	/**
	 * Set whole field to EMPTY.
	 */
	private static TileTypes[][] initBoard(TileTypes[][] gameField)
	{
		for (int i = 0; i < gameField.length; i++)
		{
			for (int j = 0; j < gameField[i].length; j++)
			{
				gameField[i][j] = TileTypes.EMPTY_TILE;
			}
		}

		return gameField;
	}
}
