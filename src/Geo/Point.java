package Geo;
import biuoop.DrawSurface;

/**
 * class Point.
 */
public class Point {

    private double x;
    private double y;

    /**
     * Constructor for class Point.
     * @param x1 x coord
     * @param y1 y coord
     */
    public Point(final double x1, final double y1) {
        this.x = x1;
        this.y = y1;
    }

    /**
     * Distance between points calc method.
     * @param other 2-nd point
     * @return distance in double
     */
    public double distance(final Point other) {
        return Math.sqrt(Math.pow(other.getX() - this.x, 2) + Math.pow(other.getY() - this.y, 2));
    }

    /**
     * Check if point is on the same coords.
     * @param other 2-nd point
     * @return true if equals
     */
    public boolean equals(final Point other) {
        if (other == null) {
            return false;
        } else {
            return (Math.abs(this.x - other.getX()) < 1e-9 && Math.abs(this.y - other.getY()) < 1e-9);
        }
    }

    /**
     * Check if point is on the segment.
     * @param other segment
     * @return true if point on the segment
     */
    public boolean onTheLine(final Line other) {
        return this.x <= Math.max(other.start().getX(), other.end().getX())
                && this.x >= Math.min(other.start().getX(), other.end().getX())
                && this.y <= Math.max(other.start().getY(), other.end().getY())
                && this.y >= Math.min(other.start().getY(), other.end().getY());
    }

    /**
     * Determines what is the orientation of point to start/end of segment.
     * @param other segment
     * @return 0 if collinear, 1 if clockwise, -1 otherwise
     */
    public int orientation(final Line other) {
        double orientation = ((other.start().getY() - this.y) * (other.end().getX() - other.start().getX()))
                - ((other.start().getX() - this.x) * (other.end().getY() - other.start().getY()));

        if (orientation == 0) {
            return 0;
        } else {
            return orientation > 0 ? 1 : -1;
        }
    }

    /**
     * Getter of x coord.
     * @return x coord of point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Set new X-coord.
     * @param x new X-coord
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Getter of y coord.
     * @return y coord of point
     */
    public double getY() {
        return this.y;
    }

    /**
     * Set new Y-coord.
     * @param y new Y-coord
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Never actually used so whatever.
     * @param rect frame
     * @return possition in relation to a line
     */
    public boolean inFrame(Rectangle rect) {
        // Initialize boundaries
        double xMin = rect.getUpperLeft().getX(), xMax = xMin + rect.getWidth();
        double yMin = rect.getUpperLeft().getY(), yMax = yMin + rect.getHeight();

        // Check if this object's position is within the bounding box
        return (this.getX() >= xMin && this.getX() <= xMax)
                && (this.getY() >= yMin && this.getY() <= yMax);
    }

    /**
     * Teleports point to new location.
     * @param p new point
     * @return updated point
     */
    public Point teleportTo(Point p) {
        this.x = p.getX();
        this.y = p.getY();
        return this;
    }

    /**
     * Check if point in border of DrawSurface "almost".
     * @param d Drawsurface
     * @return true/false
     */
    public boolean inBorderOfDrawsurface(DrawSurface d) {
        // -50 so paddle leaves screen smoothly.
        return (this.x < d.getWidth() && this.x > -50) && (this.y < d.getHeight() && this.y > 0);
    }
}

