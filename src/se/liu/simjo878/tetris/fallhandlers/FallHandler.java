package se.liu.simjo878.tetris.fallhandlers;

import se.liu.simjo878.tetris.Board;

import java.awt.*;

public interface FallHandler
{
    public boolean hasCollision(Board board, Point oldPos);
}
