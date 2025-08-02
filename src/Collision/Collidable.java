package Collision;

import Geo.Point;
import Geo.Rectangle;
import Geo.Velocity;
import Interactable.Ball;

/**
 * Collidable Class.
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     *
     * @return collition shape
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     * @param hitter ball
     * @param collisionPoint point of collision
     * @param currentVelocity current velocity
     * @return new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
