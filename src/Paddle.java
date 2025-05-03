import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.*;

public class Paddle implements Sprite, Collidable {

    private GameEnvironment gEnv;
    private biuoop.KeyboardSensor keyboard;
    private Block paddle;

    public Paddle(GameEnvironment gEnv) {
        this.paddle = new Block(new Rectangle(new Point(600, 500), 40, 10), Color.CYAN);
        biuoop.GUI gui = gEnv.getGui();
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
    }


    public void moveLeft() {

    }
    public void moveRight() {

    }

    public void movePaddle() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY) || keyboard.isPressed("a")) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY) ||  keyboard.isPressed("d")) {
            moveRight();
        }
    }



    // Sprite
    @Override
    public void timePassed(){
        this.movePaddle();
    }
    @Override
    public void drawOn(DrawSurface d) {
        this.paddle.drawOn(d);
    }

    // Collidable
    @Override
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        return this.paddle.hit(collisionPoint, currentVelocity);
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}