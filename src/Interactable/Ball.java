package Interactable;


import Collision.CollisionInfo;
import Collision.GameEnvironment;
import Sprites.Sprite;
import Geo.Velocity;
import Geo.Point;
import Geo.Line;
import Run.Game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * Class Ball.
 */
public class Ball implements Sprite, HitNotifier {
    private List<HitListener> hitListeners;
    private GameEnvironment gEnv;
    private Point center;
    private final int radius;
    private Color color;
    private Velocity v;

    /**
     * Constructor.
     * @param center center point
     * @param r radius
     * @param color color
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.v = new Velocity(Math.abs(100 - r), Math.abs(100 - r));
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Constructor 2.
     * @param x x-coors
     * @param y y-coord
     * @param r radius
     * @param color color
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.v = new Velocity(Math.abs(100 - r), Math.abs(100 - r));
        this.hitListeners = new ArrayList<>();
    }


    // accessors

    /**
     * Getter of velocity.
     * @return current velocity
     */
    public Velocity getVelocity() {
        return v;
    }

    /**
     * Getter of X-coord of the center point.
     * @return x-coord
     */
    public double getCenterX() {
        return (int) this.center.getX();
    }

    /**
     * Getter of Y-coord of the center point.
     * @return y-coord
     */
    public double getCenterY() {
        return (int) this.center.getY();
    }

    /**
     * Setter of new velocity.
     * @param v new velocity
     */
    public void setVelocity(Velocity v) {
        this.v.setDx(v.getDx());
        this.v.setDy(v.getDy());
    }
    /**
     * 1) compute the ball trajectory (the trajectory is "how the ball will move
     * without any obstacles" -- it's a line starting at current location, and
     * ending where the velocity will take the ball if no collisions will occur).
     * 2) Check (using the game environment) if moving on this trajectory will hit anything.
     * 2.1) If no, then move the ball to the end of the trajectory.
     * 2.2) Otherwise (there is a hit):
     * 2.2.2) move the ball to "almost" the hit point, but just slightly before it.
     * 2.2.3) notify the hit object (using its hit() method) that a collision occurred.
     * 2.2.4) update the velocity to the new velocity returned by the hit() method.
     */
    public void moveOneStep() {
        // 1) Calculate the trajectory
        Line trajectory = new Line(this.center, this.v.applyToPoint(this.center));
        CollisionInfo hit = gEnv.getClosestCollision(trajectory);

        if (hit != null && hit.getCollisionObject() != null) {
            // 2.2.2) Move to slightly before the collision point
            Point collisionPoint = hit.getCollisionPoint();
            double dx = this.v.getDx();
            double dy = this.v.getDy();

            // Calculate a small step back from the collision point
            double buffer = 0.1; // small value to prevent "sticking"
            double norm = Math.sqrt(dx * dx + dy * dy);
            double offsetX = dx / norm * buffer;
            double offsetY = dy / norm * buffer;

            // Move to just before the hit point
            this.center = new Point(collisionPoint.getX() - offsetX, collisionPoint.getY() - offsetY);

            // 2.2.3) Notify the hit object
            this.v = hit.getCollisionObject().hit(this, collisionPoint, this.v);

            if (this.center.inFrame(this.gEnv.getObjects().get(0).getCollisionRectangle())) {
                // No stuck in paddle
                this.center = trajectory.getStart();
                while ((this.center.inFrame(this.gEnv.getObjects().get(0).getCollisionRectangle()))) {
                    this.center = this.v.applyToPoint(this.center);
                }
            }
        } else {
            // 2.1) No collision — move normally
            this.center = this.v.applyToPoint(this.center);
        }

    }




    /**
     * Return radius.
     * @return radius
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Return color.
     * @return color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Color ball in given color.
     * @param c
     */
    public void setColor(Color c) {
        this.color = c;
    }


    /**
     * Return center point.
     * @return center of the ball
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Notifier for every listener assigned to this ball.
     * @param hitter
     */
    public void notifyHit(Block hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(hitter, this);
        }
    }

    /**
     * draw the ball on the given DrawSurface.
     * @param surface our surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.getSize());
    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Update game environment of the ball.
     * @param g game environment
     */
    public void setGameEnviroment(GameEnvironment g) {
        this.gEnv = g;
    }

    /**
     * Add ball to the game.
     * @param game gaaaame
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    /**
     * Remove ball from game.
     * @param game
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}