package Interactable;

import Collision.Collidable;
import Sprites.Sprite;
import Geo.Velocity;
import Geo.Rectangle;
import Geo.Point;
import Run.Game;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Block Class.
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private static final Random RANDOM = new Random();

    private boolean deathZone;
    private boolean spawn;
    private boolean tripleSpawn;
    private boolean bonusScore;
    private List<HitListener> hitListeners;
    private final Rectangle block;

    /**
     * Constructor.
     * @param rect shape
     * @param color color
     */
    public Block(Rectangle rect, Color color) {
        this.block = rect;
        this.block.setColor(color);
        this.hitListeners = new ArrayList<HitListener>();
        this.tripleSpawn = (randomNum(21) == 1);
        this.spawn = (randomNum(7) == 1);
        this.bonusScore = (randomNum(10) == 1);
        this.deathZone = false;
    }

    /**
     * Turn this block in deathzone.
     */
    public void turnDeathZone() {
        this.deathZone = true;
        this.spawn = false;
        this.tripleSpawn = false;
        this.bonusScore = false;
    }

    /**
     * Update upper left corner for the block.
     * @param newUpperLeft new point
     */
    public void moveTo(Point newUpperLeft) {
        this.block.setUpperLeft(newUpperLeft);
    }

    /**
     * Randomizer.
     * @param bound
     * @return int
     */
    public static int randomNum(int bound) {
        return RANDOM.nextInt(bound);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }

        if (this.deathZone) {
            hitter.notifyHit(this);
        }

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


        if (rightHit || leftHit) {
            currentVelocity.invertDx();
        }

        if (topHit || bottomHit) {
            currentVelocity.invertDy();
        }

        return currentVelocity;
    }

    /**
     * Draws block on Drawsurface.
     * @param surface Drawsurface
     */
    public void drawOn(DrawSurface surface) {
        this.block.drawOn(surface);
    }

    @Override
    public void timePassed() { }

    /**
     * Add block to the game.
     * @param game game
     */
    public void addToGame(Game game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * Checks if ball's color block's color is matching or not.
     * @param ball
     * @return boolean
     */
    public boolean ballColorMatch(Ball ball) {
        return ball.getColor() == this.block.getColor();
    }

    private void spawnBall(Game game) {
        Ball b = new Ball(this.block.getCenter(), randomNum(3) + 5, this.block.getColor());
        b.setVelocity(Velocity.fromAngleAndSpeed(randomNum(140) + 20, 3 + randomNum(3)));
        b.setGameEnviroment(game.getEnvironment());
        b.addToGame(game);
        b.addHitListener(game.getBallRemover());
        game.getBallRemover().getRemainingBalls().increase();
    }

    /**
     * Remove this block from game.
     * @param game
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
        if (this.spawn) {
            spawnBall(game);
        }
        if (this.tripleSpawn) {
            for (int i = 0; i < 3; i++) {
                spawnBall(game);
            }
        }
        if (this.bonusScore) {
            game.getSTL().bonusScore(randomNum(69));
        }
    }

    /**
     * Notifier for every listener assigned to this ball.
     * @param hitter
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
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
