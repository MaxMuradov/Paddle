/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor.
     * @param dx x-acceleration
     * @param dy y-acceleration
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Func that transform angle and speed to x/y-acceleration.
     * @param angle angle in degrees
     * @param speed speed
     * @return velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = Math.cos(radians) * speed;
        double dy = Math.sin(radians) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * set x-acceleration.
     * @param dx x-acceleration
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * set y-acceleration.
     * @param dy y-acceleration
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * get x-acceleration.
     * @return x-acceleration
     */
    public double getDx() {
        return dx;
    }

    /**
     * get y-acceleration.
     * @return y-acceleration
     */
    public double getDy() {
        return dy;
    }

    /**
     * changes x-acceleration to opposite.
     */
    public void invertDx() {
        this.dx *= -1;
    }

    /**
     * changes y-acceleration to opposite.
     */
    public void invertDy() {
        this.dy *= -1;
    }


    /**
     *Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     * @param p point
     * @return new point
     */

    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }
}