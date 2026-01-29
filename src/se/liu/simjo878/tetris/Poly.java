package se.liu.simjo878.tetris;

public class Poly
{
    private SquareType[][] squares;

    public Poly(SquareType[][] squares) {
        this.squares = squares;
    }

    public int getWidth(){
        return squares[0].length;
    }

    public int getHeight() {
        return squares.length;
    }

    public SquareType getSquareType(int x, int y) {
        return squares[y][x];
    }
}
