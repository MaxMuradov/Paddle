package Score;

import Interactable.Ball;
import Interactable.Block;
import Interactable.HitListener;
import Run.Counter;

/**
 * ScoreTrackingListener class.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor.
     * @param scoreCounter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5);
    }

    /**
     * Gives Bonus scores.
     * @param bonus
     */
    public void bonusScore(int bonus) {
        this.currentScore.increase(bonus);
    }

    /**
     * Getter of counter.
     * @return counter
     */
    public Counter getCounter() {
        return currentScore;
    }

}
