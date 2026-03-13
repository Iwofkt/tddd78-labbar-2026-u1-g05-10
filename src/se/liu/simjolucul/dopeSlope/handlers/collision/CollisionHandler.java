package se.liu.simjolucul.dopeSlope.handlers.collision;

import se.liu.simjolucul.dopeSlope.gameObjects.Obstacle;
import se.liu.simjolucul.dopeSlope.gameObjects.Player;

public interface CollisionHandler {
    public boolean checkCollision(Player player, Obstacle obstacle);
}
