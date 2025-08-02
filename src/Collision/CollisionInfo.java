package Collision;

import Geo.Point;

/**
 * CollisionInfo Class.
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObj;

    /**
     * Default null Constructor.
     */
    public CollisionInfo() {
        this.collisionObj = null;
        this.collisionPoint = null;
    }

    /**
     * Getter of collision point.
     *
     * @return the point at which the collision occurs.
     */
    public Point getCollisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Setter of collision point.
     * @param p new point
     */
    public void setCollisionPoint(Point p) {
        this.collisionPoint = p;
    }

    /**
     * Getter of collision object.
     * @return the collidable object involved in the collision.
     */
    public Collidable getCollisionObject() {
        return this.collisionObj;
    }

    /**
     * Setter of collision object.
     * @param c new collision object
     */
    public void setCollisionObject(Collidable c) {
        this.collisionObj = c;
    }
}