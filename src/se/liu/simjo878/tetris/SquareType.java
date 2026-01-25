package se.liu.simjo878.tetris;

import java.util.Random;

// Lärt mig om
// sorter av klasser
public enum SquareType {
// ctrl-Q för Javadoc
// crtl-J för live templates
    EMPTY, I, O, T, S, Z, J, L;

    public static void main(String[] args) {
        Random rnd = new Random();
        for(int i = 0; i < 25; i++){
            System.out.println(SquareType.values()[rnd.nextInt(1, SquareType.values().length)]);
        }
    }
}