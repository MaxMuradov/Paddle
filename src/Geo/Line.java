package Geo;

/**
 * class Line.
 */
public class Line {
    /**
     * Point start of segment.
     */
    private final Point start;
    /**
     * Point start of segment.
     */
    private final Point end;

    // constructors

    /**
     *constructor by ponts.
     * @param startp start point
     * @param endp end point
     */
    public Line(final Point startp, final Point endp) {
        this.start = new Point(startp.getX(), startp.getY());
        this.end = new Point(endp.getX(), endp.getY());
    }

    /**
     * constructor by coords.
     * @param x1 x coord of 1-st point
     * @param y1 y coord of 1-st point
     * @param x2 x coord of 2-st point
     * @param y2 y coord of 2-st point
     */
    public Line(final double x1, final double y1, final double x2, final double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Getter of start point of the line.
     * @return start point
     */
    public Point getStart() {
        return this.start;
    }

    /**
     * Getter of end point of the line.
     * @return end point
     */
    public Point getEnd() {
        return this.end;
    }
    /**
     * find length of segment.
     * @return length in double
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * find the middle of segment.
     * @return middle point
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * return 1-nd point of segment.
     * @return start point
     */
    public Point start() {
        return start;
    }

    /**
     * return 2-nd point of segment.
     * @return end point
     */
    public Point end() {
        return end;
    }

    /**
     * check if segments are intersecting.
     * inspirational: https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
     * @param other 2-nd line
     * @return true if intersecting
     */
    public boolean isIntersecting(Line other) {
        if (this.equals(other)) {
            return false;
        }

        int o1 = this.start.orientation(other);
        int o2 = this.end.orientation(other);
        int o3 = other.start().orientation(this);
        int o4 = other.end().orientation(this);

        if (o1 != o2 && o3 != o4) {
            return true;
        }

        // Special Cases
        //if collinear and point o1 lies on segment other
        if (o1 == 0 && this.start.onTheLine(other)) {
            return true;
        }

        //if collinear and point o2 lies on segment other
        if (o2 == 0 && this.end.onTheLine(other)) {
            return true;
        }

        //if collinear and point o3 lies on segment this.
        if (o3 == 0 && other.start().onTheLine(this)) {
            return true;
        }

        //if collinear and point o4 lies on segment this
        if (o4 == 0 && other.end().onTheLine(this)) {
            return true;
        }

        return false; // Doesn't fall in any of the above cases
    }

    /**
     * check if segmnent intersect with 2 lines.
     * @param other1 2-nd line
     * @param other2 3- line
     * @return true if intersect
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /**
     * find point on itersection of segments.
     * @param other line that's intersected
     * @return point on intersection
     */
    public Point intersectionWith(Line other) {
        if (!isIntersecting(other)) {
            return null;
        }

        //Check if this line is vertical
        boolean thisVertical = (this.end().getX() - this.start().getX() == 0);
        // Check if the other line is vertical
        boolean otherVertical = (other.end().getX() - other.start().getX() == 0);
        double x, y;

        if (thisVertical) {
            // This line is vertical, use its x value and plug into the other line's equation
            x = this.start().getX();
            double m2 = (other.end().getY() - other.start().getY())
                    / (other.end().getX() - other.start().getX());
            double b2 = other.start().getY() - m2 * other.start().getX();
            y = m2 * x + b2;
        } else if (otherVertical) {
            // Other line is vertical, use its x value and plug into this line's equation
            x = other.start().getX();
            double m1 = (this.end().getY() - this.start().getY())
                    / (this.end().getX() - this.start().getX());
            double b1 = this.start().getY() - m1 * this.start().getX();
            y = m1 * x + b1;
        } else {

            double m1 = (end.getY() - start.getY()) / (end.getX() - start.getX());
            double m2 = (other.end().getY() - other.start().getY())
                    / (other.end().getX() - other.start().getX());


            double b1 = start.getY() - m1 * start.getX();
            double b2 = other.start.getY() - m2 * other.start.getX();

            if (Math.abs(m1 - m2) < 1e-9) {
                return null; // Parallel or coincident lines
            }

            x = (b2 - b1) / (m1 - m2);
            y = m1 * x + b1;
        }

        return new Point(x, y);
    }


    /**
     * check if lines are equals (have same points).
     * @param other 2-nd segment
     * @return  true if equals
     */
    public boolean equals(Line other) {
        return (start.equals(other.start()) && end.equals(other.end()))
                || (start.equals(other.end()) && end.equals(other.start()));
    }


    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     * @param rect
     * @return point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        double minDist = Double.MAX_VALUE;
        java.util.List<Point> l = rect.intersectionPoints(this);
        Point returnP = null;

        for (Point point : l) {
            if (point != null) {
                double len = new Line(this.start, point).length();
                if (minDist > len) {
                    minDist = len;
                    returnP = point;
                }
            }
        }

        return returnP;
    }
}

