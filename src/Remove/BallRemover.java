package Remove;
import Interactable.Ball;
import Interactable.Block;
import Interactable.HitListener;
import Run.Counter;
import Run.Game;

/**
 * BallRemover class.
 */
public class BallRemover implements HitListener {

    private Game game;
    private Counter remainingBalls;

    /**
     * Constructor.
     * @param game
     * @param remainingBalls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }
    @Override
    public void hitEvent(Block hitter, Ball beingHit) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(game);
        this.remainingBalls.decrease();
    }

    /**
     * Getter of counter.
     * @return counter
     */
    public Counter getRemainingBalls() {
        return this.remainingBalls;
    }
}
