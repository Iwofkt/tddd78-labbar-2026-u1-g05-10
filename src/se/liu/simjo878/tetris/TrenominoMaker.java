package se.liu.simjo878.tetris;

public class TrenominoMaker
{
    public int getNumberOfTypes() {
	return SquareType.values().length -1;
    }
    public Poly getPoly(int n) {
	switch (n) {
	    case 0:
		return new Poly(new SquareType[][]{
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
			{SquareType.I, SquareType.I, SquareType.I, SquareType.I},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY, SquareType.EMPTY}
		});
	    case 1:
		return new Poly(new SquareType[][]{
			{SquareType.O, SquareType.O},
			{SquareType.O, SquareType.O}
		});
	    case 2:
		return new Poly(new SquareType[][]{
			{SquareType.EMPTY, SquareType.T,  SquareType.EMPTY},
			{SquareType.T, SquareType.T, SquareType.T},
			{SquareType.EMPTY, SquareType.EMPTY,  SquareType.EMPTY}
		});
	    case 3:
		return new Poly(new SquareType[][]{
			{SquareType.EMPTY, SquareType.S,  SquareType.S},
			{SquareType.S, SquareType.S, SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.EMPTY,  SquareType.EMPTY}
		});
	    case 4:
		return new Poly(new SquareType[][]{
			{SquareType.Z, SquareType.Z,  SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.Z, SquareType.Z},
			{SquareType.EMPTY, SquareType.EMPTY,  SquareType.EMPTY}
		});
	    case 5:
		return new Poly(new SquareType[][]{
			{SquareType.EMPTY, SquareType.J,  SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.J, SquareType.EMPTY},
			{SquareType.J, SquareType.J,  SquareType.EMPTY}
		});
	    case 6:
		return new Poly(new SquareType[][]{
			{SquareType.EMPTY, SquareType.L,  SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.L, SquareType.EMPTY},
			{SquareType.EMPTY, SquareType.L,  SquareType.L}
		});
	    default:
	    	throw new IllegalArgumentException("Invalid Poly number: " + n);
	}
    }
}
