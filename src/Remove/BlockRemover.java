package Remove;

import Interactable.Ball;
import Interactable.Block;
import Interactable.HitListener;
import Run.Counter;
import Run.Game;

/**
 * BlockRemover is in charge of removing blocks from the game, also keeping count of the number of remaining blocks.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     * @param game
     * @param remainingBlocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    // Blocks that are hit should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        this.remainingBlocks.decrease();
        hitter.setColor(beingHit.getCollisionRectangle().getColor());
        beingHit.removeHitListener(this);
    }

    /**
     * Getter of Counter.
     * @return counter
     */
    public Counter getRemainingBlocks() {
        return this.remainingBlocks;
    }

}
