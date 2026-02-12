package se.liu.simjo878.tetris;

public class Poly
{
    private SquareType[][] squares;

    public Poly(SquareType[][] squares) {
        this.squares = squares;
    }

    public int getSize(){
        return squares[0].length;
    }

    public SquareType getSquareType(int x, int y) {
        return squares[y][x];
    }

    public Poly rotate(boolean right) {
        Poly newPoly = new Poly(new SquareType[getSize()][getSize()]);

        for (int r = 0; r < getSize(); r++) {
            for (int c = 0; c < getSize(); c++) {
                if (right) {
                    // Rotate right (90° medurs)
                    newPoly.squares[c][getSize() - 1 - r] = this.squares[r][c];
                } else {
                    // Rotate left (90° moturs)
                    newPoly.squares[getSize() - 1 - c][r] = this.squares[r][c];
                }
            }
        }
        return newPoly;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < getSize(); y++) {
            for (int x = 0; x < getSize(); x++) {
                SquareType type = getSquareType(x, y);
                switch (type) {
                    case EMPTY -> builder.append(" - ");
                    case I -> builder.append(" I ");
                    case J -> builder.append(" J ");
                    case Z -> builder.append(" Z ");
                    case L -> builder.append(" L ");
                    case O -> builder.append(" O ");
                    case S -> builder.append(" S ");
                    case T -> builder.append(" T ");
                    case OUTSIDE -> builder.append(" # ");
                    default -> builder.append(" ? ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
