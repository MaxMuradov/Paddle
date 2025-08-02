package Collision;

import Geo.Rectangle;
import Geo.Point;
import Geo.Line;
import Interactable.Block;

import java.awt.Color;
import java.util.ArrayList;
import biuoop.GUI;

/**
 * Game Environment Class.
 */
public class GameEnvironment {
    public static final int GUI_HEIGHT = 600;
    public static final int GUI_WIDTH = 800;

    private GUI gui;
    private java.util.List<Collidable> objects;

    /**
     * Constructor with borders creation.
     */
    public GameEnvironment() {
        this.objects = new ArrayList<>();
        gui = new GUI("Padddle", GUI_WIDTH, GUI_HEIGHT);
        Block topBorder = new Block(new Rectangle(new Point(0, 50), GUI_WIDTH, 1), Color.GRAY);
        //Block bottomBorder = new Block(new Rectangle(new Point(0, GUI_HEIGHT), GUI_WIDTH, 1), Color.GRAY);
        Block leftBorder = new Block(new Rectangle(new Point(0, 0), 1, GUI_HEIGHT), Color.GRAY);
        Block rightBorder = new Block(new Rectangle(new Point(GUI_WIDTH, 0), 1, GUI_HEIGHT), Color.GRAY);
        this.addCollidable(topBorder);
        //this.addCollidable(bottomBorder);
        this.addCollidable(leftBorder);
        this.addCollidable(rightBorder);
    }

    /**
     * Getter of collidable objects.
     * @return list of collidable
     */
    public java.util.List<Collidable> getObjects() {
        return objects;
    }


    /**
     * Add the given collidable to the environment.
     * @param c new collidable
     */
    public void addCollidable(Collidable c) {
        objects.add(c);
    }

    /**
     * Add collidable but to the start of arryList (only for paddle and logic of movement).
     * @param c collidable
     */
    public void addToStartCollidable(Collidable c) {
        objects.add(0, c);
    }

    /**
     * Remove collidable from existing colidables.
     * @param c collidable
     */
    public void removeCollidable(Collidable c) {
        objects.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     * @param trajectory trajectory line
     * @return collison info (point and shape)
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo info = new CollisionInfo();
        for (Collidable obj : this.objects) {
            Point p = trajectory.closestIntersectionToStartOfLine(obj.getCollisionRectangle());
            if (p != null) {
                if (info.getCollisionPoint() == null || info.getCollisionObject() == null) {
                    info.setCollisionObject(obj);
                    info.setCollisionPoint(p);
                }
                if (trajectory.getStart().distance(info.getCollisionPoint()) > trajectory.getStart().distance(p)) {
                    info.setCollisionObject(obj);
                    info.setCollisionPoint(p);
                }
            }
        }

        return info;
    }

    /**
     * Getter of gui.
     * @return gui
     */
    public GUI getGui() {
        return gui;
    }
}
