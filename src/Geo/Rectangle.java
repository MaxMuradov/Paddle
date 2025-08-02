package Geo;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Rectangle class.
 */
public class Rectangle {
    private Point upperLeft;
    private double width, height;
    private java.awt.Color color;

    /**
     * Constructor.
     * @param upperLeft upper left point
     * @param width width
     * @param height height
     * @param color color
     */
    public Rectangle(Point upperLeft, double width, double height, java.awt.Color color) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
        this.color = color;
    }

    /**
     * Constructor 2.
     * @param upperLeft upper left point
     * @param width width
     * @param height height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
    }

    /**
     * Return a (possibly empty) List of intersection points
     * with the specified line.
     * @param line trajectory*
     * @return list of intersected point
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        double x = this.getUpperLeft().getX();
        double y = this.getUpperLeft().getY();
        double w = this.getWidth();
        double h = this.getHeight();
        java.util.List<Point> list = new ArrayList<>();

        Point crossTop = line.intersectionWith(new Line(x, y, x + w, y));
        if (crossTop != null) {
            list.add(crossTop);
        }
        Point crossBot = line.intersectionWith(new Line(x, y + h, x + w, y + h));
        if (crossBot != null && !(crossBot.equals(crossTop))) {
            list.add(crossBot);
        }
        Point crossR = line.intersectionWith(new Line(x + w, y, x + w, y + h));
        if (crossR != null && !(crossR.equals(crossTop)) && !(crossR.equals(crossBot))) {
            list.add(crossR);
        }
        Point crossL = line.intersectionWith(new Line(x, y, x, y + h));
        if (crossL != null  && !(crossL.equals(crossTop)) && !(crossL.equals(crossBot)) && !(crossL.equals(crossR))) {
            list.add(crossL);
        }

        return list;
    }

    /**
     * Getter of width.
     * @return width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Getter of height.
     * @return height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the upper-left point of the rectangle.
     * @return upper left point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Update of upper left point.
     * @param newUpperLeft new point
     */
    public void setUpperLeft(Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
    }

    /**
     * Update color of rectangle.
     * @param color new color
     */
    public void setColor(java.awt.Color color) {
        this.color = color;
    }

    /**
     * Getter of color.
     * @return color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Find center of Rectangle.
     * @return center point
     */
    public Point getCenter() {
        double x = this.getUpperLeft().getX();
        double y = this.getUpperLeft().getY();
        return new Point(x + (this.width / 2), y + (this.height / 2));
    }

    /**
     * Draw Rectangle on the DrawSurface.
     * @param surface DrawSurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        double x = this.getUpperLeft().getX();
        double y = this.getUpperLeft().getY();
        double width = this.getWidth();
        double height = this.getHeight();
        surface.fillRectangle((int) x, (int) y, (int) width, (int) height);

        //frame
        surface.setColor(Color.BLACK);
        surface.drawRectangle((int) x, (int) y, (int) width, (int) height);
    }
}