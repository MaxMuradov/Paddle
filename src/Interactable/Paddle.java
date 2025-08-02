package Interactable;

import Collision.Collidable;
import Collision.GameEnvironment;
import Sprites.Sprite;
import Geo.Rectangle;
import Geo.Velocity;
import Geo.Point;
import Run.Game;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * Paddle Class.
 */
public class Paddle implements Sprite, Collidable {
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private int paddleSpeed = 7; // speed
    private final GameEnvironment gEnv;
    private final KeyboardSensor keyboard;
    private final Block paddle;
    private int timeskip;

    private int easteregg = 5;


    /**
     * Constructor.
     * @param gEnv Game Enviroment class
     */
    public Paddle(GameEnvironment gEnv) {
        this.gEnv = gEnv;
        this.paddle = new Block(new Rectangle(new Point(400, 560), PADDLE_WIDTH, PADDLE_HEIGHT),
                Color.getHSBColor(50, 10, 95));
        this.keyboard = gEnv.getGui().getKeyboardSensor();
    }


    /**
     * Moving paddle to the left.
     */
    public void moveLeft() {
        int newX = (int) this.paddle.getCollisionRectangle().getUpperLeft().getX() - paddleSpeed;
        int y = (int) this.paddle.getCollisionRectangle().getUpperLeft().getY();

        if (newX < -(this.paddle.getCollisionRectangle().getWidth())) {
            newX = gEnv.getGui().getDrawSurface().getWidth() + newX; // wrap around
            easteregg--;
        }

        this.paddle.moveTo(new Point(newX, y));
    }

    /**
     * Moving paddle to the right.
     */
    public void moveRight() {
        int newX = (int) this.paddle.getCollisionRectangle().getUpperLeft().getX() + paddleSpeed;

        if (newX > gEnv.getGui().getDrawSurface().getWidth()) {
            newX = newX - gEnv.getGui().getDrawSurface().getWidth(); // wrap around
            easteregg--;
        }

        this.paddle.moveTo(new Point(newX, (int) this.paddle.getCollisionRectangle().getUpperLeft().getY()));
    }

    /**
     * Check what key is pressed.
     */
    public void movePaddle() {
        if (easteregg == 0) {
            this.gEnv.getGui().getDialogManager().showInformationDialog("pesahEgg", "Paddle now faster");
            this.paddleSpeed += 2;
            easteregg += 10;
        }
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY) || this.keyboard.isPressed("a")
                || this.keyboard.isPressed("ש") || this.keyboard.isPressed("ф")) {
            moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY) ||  this.keyboard.isPressed("d")
                || this.keyboard.isPressed("ג") || this.keyboard.isPressed("в")) {
            moveRight();
        }
    }

    /**
     * Pause Screen drawer.
     * @param d drawsurface
     */
    public void showPauseScreen(DrawSurface d) {
        int w = this.gEnv.getGui().getDrawSurface().getWidth();
        int h = this.gEnv.getGui().getDrawSurface().getHeight();
        d.setColor(Color.getHSBColor(0.08f, 0.65f, 0.15f));
        d.fillRectangle(50, 50, w - 100, h - 100);
        drawPauseMessage(d);
    }

    /**
     * Pause Text writer.
     * @param d drawsurface
     */
    public void drawPauseMessage(DrawSurface d) {
        d.setColor(Color.getHSBColor(0.1f, 0.1f, 0.9f)); // muted cream
        d.drawText(250, 300, "Game Paused. Press SPACE to continue...", 20);
    }


    /**
     * Move of paddle + keyboard input.
     */
    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            Sleeper sleeper = new Sleeper();

            // Wait until SPACE is released
            while (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                sleeper.sleepFor(10);
            }

            // Display pause screen until SPACE is pressed again
            while (!this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                DrawSurface d = this.gEnv.getGui().getDrawSurface();
                showPauseScreen(d);
                this.gEnv.getGui().show(d); // <<<<<<<<<< KEY LINE to actually show the screen
                sleeper.sleepFor(10);
            }

            // Wait for SPACE release again to prevent instant unpause
            while (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                sleeper.sleepFor(10);
            }
        }


        if (this.timeskip == 0) {
            this.movePaddle();
        } else {
            this.timeskip--;
        }
    }

    /**
     * Draw paddle on surface.
     * @param d drawsurface
     */
    @Override
    public void drawOn(DrawSurface d) {
        this.paddle.drawOn(d);
    }

    /**
     * Getter of shape.
     *
     * @return Rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }

    /**
     * Collision with Paddle.
     * @param hitter Ball that hit given block
     * @param collisionPoint Point of collision
     * @param currentVelocity Velocity of collided shape
     * @return updated velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double xHit = collisionPoint.getX();
        double yHit = collisionPoint.getY();
        double xLeftRect = this.getCollisionRectangle().getUpperLeft().getX();
        double xRightRect = xLeftRect + this.getCollisionRectangle().getWidth();
        double yTopRect = this.getCollisionRectangle().getUpperLeft().getY();
        double yBottomRect = yTopRect + this.getCollisionRectangle().getHeight();

        double epsilon = 0.1; // small threshold

        boolean rightHit = Math.abs(xHit - xRightRect) < epsilon;
        boolean leftHit = Math.abs(xHit - xLeftRect) < epsilon;
        boolean topHit = Math.abs(yHit - yTopRect) < epsilon;
        boolean bottomHit = Math.abs(yHit - yBottomRect) < epsilon;

        int fifthPart = (int) (this.paddle.getCollisionRectangle().getWidth() / 5);
        boolean topHitsect1 = topHit && (xHit >= xLeftRect && xHit <= xLeftRect + fifthPart);
        boolean topHitsect2 = topHit && (xHit >= xLeftRect + fifthPart && xHit <= xLeftRect + (2 * fifthPart));
        boolean topHitsect3 = topHit && (xHit >= xLeftRect + (2 * fifthPart) && xHit <= xLeftRect + (3 * fifthPart));
        boolean topHitsect4 = topHit && (xHit >= xLeftRect + (3 * fifthPart) && xHit <= xLeftRect + (4 * fifthPart));
        boolean topHitsect5 = topHit && (xHit >= xLeftRect + (4 * fifthPart) && xHit <= xRightRect);
        if (leftHit || topHitsect1) {
            return currentVelocity.changeAngle(-60);
        }
        if (topHitsect2) {
            return currentVelocity.changeAngle(-30);
        }
        if (topHitsect3 || bottomHit) {
            currentVelocity.invertDy();
            return currentVelocity;
        }
        if (topHitsect4) {
            return currentVelocity.changeAngle(30);
        }
        if (rightHit || topHitsect5) {
            return currentVelocity.changeAngle(60);
        }

        this.timeskip = 2;
        return currentVelocity;
    }

    /**
     * Add this paddle to the game.
     * @param g Game class
     */
    public void addToGame(Game g) {
        g.addToStartCollidable(this);
        g.addToStartSprite(this);
    }
}