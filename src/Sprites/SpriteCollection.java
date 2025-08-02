package Sprites;
import biuoop.DrawSurface;
import java.util.ArrayList;

/**
 * SpriteCollection class.
 */
public class SpriteCollection {

    private java.util.ArrayList<Sprite> sprites;

    /**
     * Constructor of class.
     */
    public SpriteCollection() {
        sprites = new ArrayList<>();
    }

    /**
     * Add new sprite to existing sprites.
     * @param s new sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Add sprite to the start of env.
     * @param s
     */
    public void addToStartSprite(Sprite s) {
        this.sprites.add(0, s);
    }

    /**
     * Delete sprite from existing sprites.
     * @param s sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * Getter of the sprites.
     * @return array of sprites
     */
    public ArrayList<Sprite> getSprites() {
        return this.sprites;
    }

    /**
     * Call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (Sprite fanta : new ArrayList<>(sprites)) {
            fanta.timePassed();
        }
    }

    /**
     * Call drawOn(d) on all sprites.
     * @param d Drawsurface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite fanta : sprites) {
            fanta.drawOn(d);
        }
    }
}