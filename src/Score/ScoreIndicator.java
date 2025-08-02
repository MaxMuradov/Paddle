package Score;

import Geo.Point;
import Geo.Rectangle;
import Run.Counter;
import Run.Game;
import Sprites.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

/**
 * ScoreIndicator class.
 */
public class ScoreIndicator implements Sprite {

    private Rectangle background;
    private Counter score;

    /**
     * Constructor.
     * @param scoreCounter
     */
    public ScoreIndicator(Counter scoreCounter) {
        this.score = scoreCounter;
        this.background = new Rectangle(new Point(0, 0), 900, 50, Color.getHSBColor(40, 30, 92));
    }

    @Override
    public void drawOn(DrawSurface d) {
        background.drawOn(d);
        d.drawText(400, 20, "Score: " + this.score.getValue(), 15);
    }

    @Override
    public void timePassed() { }

    /**
     * Add this indicator to the game.
     * @param game
     */
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
