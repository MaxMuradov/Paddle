/**
 * class Point.
 */
public class Point {

    private final double x;
    private final double y;

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
        return (Math.abs(this.x - other.getX()) < 1e-9 && Math.abs(this.y - other.getY()) < 1e-9);
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
     * Getter of y coord.
     * @return y coord of point
     */
    public double getY() {
        return this.y;
    }

    /**
     * Never actually used so whatever.
     * @param frame frame
     * @return possition in relation to a line
     */
    boolean inFrame(Line[] frame) {
        // Initialize boundaries
        double xMin = Double.MAX_VALUE, xMax = Double.MIN_VALUE;
        double yMin = Double.MAX_VALUE, yMax = Double.MIN_VALUE;

        // Find the min and max coordinates for the entire frame
        for (int i = 0; i < frame.length; i++) {
            xMin = Math.min(xMin, Math.min(frame[i].start().x, frame[i].end().x));
            xMax = Math.max(xMax, Math.max(frame[i].start().x, frame[i].end().x));
            yMin = Math.min(yMin, Math.min(frame[i].start().y, frame[i].end().y));
            yMax = Math.max(yMax, Math.max(frame[i].start().y, frame[i].end().y));
        }

        // Check if this object's position is within the bounding box
        return (this.getX() >= xMin && this.getX() <= xMax)
                && (this.getY() >= yMin && this.getY() <= yMax);
    }

}

