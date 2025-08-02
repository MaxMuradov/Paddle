package Sprites;
import biuoop.DrawSurface;

/**
 * Sprite interface.
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     * @param d Drawsurface
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}