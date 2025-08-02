package Interactable;

/**
 * HitListener interface.
 */
public interface HitListener {
    /**
     * When notified executes Event.
     * @param beingHit
     * @param hitter
     */
    void hitEvent(Block beingHit, Ball hitter);
}

