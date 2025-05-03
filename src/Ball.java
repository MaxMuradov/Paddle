import biuoop.DrawSurface;

/**
 * Class Ball.
 */
public class Ball implements Shape {

    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity v;
    private int isOutside;

    /**
     * Constructor.
     * @param center center point
     * @param r radius
     * @param color color
     * @param pos position in relation to squares
     */
    public Ball(Point center, int r, java.awt.Color color, int pos) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.v = new Velocity(Math.abs(100 - r), Math.abs(100 - r));
        this.isOutside = pos;
    }

    /**
     * Constructor 2.
     * @param x x-coors
     * @param y y-coord
     * @param r radius
     * @param color color
     * @param pos position in relation to squares
     */
    public Ball(double x, double y, int r, java.awt.Color color, int pos) {
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        this.v = new Velocity(Math.abs(100 - r), Math.abs(100 - r));
        this.isOutside = pos;
    }


    // accessors

    @Override
    public Velocity getVelocity() {
        return v;
    }

    @Override
    public double getCenterX() {
        return (int) this.center.getX();
    }

    @Override
    public double getCenterY() {
        return (int) this.center.getY();
    }

    @Override
    public void setVelocity(Velocity v) {
        this.v.setDx(v.getDx());
        this.v.setDy(v.getDy());
    }

    @Override
    public void setVelocity(double dx, double dy) {
        this.v.setDx(dx);
        this.v.setDy(dy);
    }

    /**
     * Checks position of ball and apply changes to velocity if needed.
     * @param grey grey square
     */
    public void moveOneStepInFrames(Square grey) {
        int greyCollision = this.inFrames(grey);

        if (greyCollision == 0) { //ball on the edge
            double x = this.getCenterX();
            double y = this.getCenterY();
            int r = this.radius;

            double xMin = grey.getX();
            double xMax = xMin + grey.getSize();
            double yMin = grey.getY();
            double yMax = yMin + grey.getSize();

            boolean hitLeft = (x - r <= xMin);
            boolean hitRight = (x + r >= xMax);
            boolean hitTop = (y - r <= yMin);
            boolean hitBottom = (y + r >= yMax);

            // Corner collision
            if ((hitLeft || hitRight) && (hitTop || hitBottom)) {
                this.v.invertDx();
                this.v.invertDy();
            } else if (hitLeft || hitRight) { // Vertical wall collision
                this.v.invertDx();
            } else if (hitTop || hitBottom) { // Horizontal wall collision
                this.v.invertDy();
            }
        }
        // Apply movement after checking collisions
        this.center = this.v.applyToPoint(this.center);
    }

    /**
     * Checks position of ball and apply changes to velocity if needed.
     * @param surface our drawsurface
     * @param frames array of squares
     */
    public void moveOneStepOutFrames(DrawSurface surface, Square[] frames) {
        for (Square frame : frames) {
            int position = this.inFrames(frame);  // check ball location w.r.t the square

            if (position == 0) { // ball touches the square edge
                double x = this.getCenterX();
                double y = this.getCenterY();
                int r = this.radius;

                double xMin = frame.getX();
                double xMax = xMin + frame.getSize();
                double yMin = frame.getY();
                double yMax = yMin + frame.getSize();

                boolean hitLeft = (x - r <= xMin);
                boolean hitRight = (x + r >= xMax);
                boolean hitTop = (y - r <= yMin);
                boolean hitBottom = (y + r >= yMax);

                // Corner collision
                if ((hitLeft || hitRight) && (hitTop || hitBottom)) {
                    this.v.invertDx();
                    this.v.invertDy();
                } else if (hitLeft || hitRight) { // Vertical wall collision
                    this.v.invertDx();
                } else if (hitTop || hitBottom) { // Horizontal wall collision
                    this.v.invertDy();
                }
            }
        }

        // Apply movement after checking collisions
        this.center = this.v.applyToPoint(this.center);
        this.moveOneStep(surface);
    }

    /**
     * Checks that ball is in our border, if not apply changes to velocity.
     * @param surface our drawsurface
     */
    public void moveOneStep(DrawSurface surface) {
            int borderCollision = this.inBorder(surface);

            if (borderCollision != 0) {
                if (borderCollision == 2) { // Corner
                    this.v.invertDx();
                    this.v.invertDy();
                } else if (borderCollision == 1) { // Side walls
                    this.v.invertDx();
                } else if (borderCollision == -1) { // Top/bottom
                    this.v.invertDy();
                }
            }

        // Update the position using the velocity
        this.center = this.v.applyToPoint(this.center);
    }

    /**
     * Checks position of the ball in relation to the square.
     * @param sq square frame
     * @return position (1 - inside, 0 - on edge, -1 - outside)
     */
    public int inFrames(Square sq) {
        double x = this.getCenterX();
        double y = this.getCenterY();
        int r = this.radius;

        double xMin = sq.getX();
        double xMax = xMin + sq.getSize();
        double yMin = sq.getY();
        double yMax = yMin + sq.getSize();

        boolean fullyInside = (x - r >= xMin && x + r <= xMax) && (y - r >= yMin && y + r <= yMax);
        boolean touches = (x + r >= xMin && x - r <= xMax) && (y + r >= yMin && y - r <= yMax);

        if (fullyInside) {
            return 1;
        } else if (touches) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Check position of the ball in relation to the surface.
     * @param surface our drawsurface
     * @return position (0 - inside, 1 or-1 - on the edge, 2 - in corner)
     */
    public int inBorder(DrawSurface surface) {
        int width = surface.getWidth();
        int height = surface.getHeight();
        int p = 0;

        double x = this.getCenterX();
        double y = this.getCenterY();
        int r = this.radius;

        boolean hitVertical = (x - r <= 0 || x + r >= width); // Удар влево или вправо
        boolean hitHorizontal = (y - r <= 0 || y + r >= height); // Удар вверх или вниз

        if (hitVertical && hitHorizontal) {
            p = 2; // Corner collision
        } else if (hitVertical) {
            p = 1; // Right || Left edge collision
        } else if (hitHorizontal) {
            p = -1; // Top || Bottom collision
        }

        return p;
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
     * Return position.
     * @return indicator if outside of bounds
     */
    public int getIsOutside() {
        return this.isOutside;
    }

    /**
     * Return center point.
     * @return center of the ball
     */
    public Point getCenter() {
        return this.center;
    }


    /**
     * draw the ball on the given DrawSurface.
     * @param surface our surface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.getSize());
    }

}