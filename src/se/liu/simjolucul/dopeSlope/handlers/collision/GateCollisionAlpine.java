package se.liu.simjolucul.dopeSlope.handlers.collision;

import se.liu.simjolucul.dopeSlope.gameObjects.Gate;
import se.liu.simjolucul.dopeSlope.gameObjects.Obstacle;
import se.liu.simjolucul.dopeSlope.gameObjects.Player;

public class GateCollisionAlpine implements CollisionHandler {
    public boolean checkCollision(Player player, Obstacle obstacle) {
        if (obstacle instanceof Gate gate) {
            if (gate.getLeftHitbox().intersects(player.getHitbox())) {
                return true;
            } else return gate.getRightHitbox().intersects(player.getHitbox());
        }
        return false;
    }
}
