package Interactable;

/**
 * HitNotifier interface.
 */
public interface HitNotifier {
    /**
     * Add hitlistener as a listener to hit events.
     * @param hl
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hitlistener from the list of listeners to hit events.
     * @param hl
     */
    void removeHitListener(HitListener hl);
}
