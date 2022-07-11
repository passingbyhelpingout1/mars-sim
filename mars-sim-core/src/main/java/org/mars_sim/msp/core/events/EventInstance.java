package org.mars_sim.msp.core.events;

/**
 * This represents an event that is dispatched via the #EventDispatcher
 */
public interface EventInstance {
    /**
     * Deliver this event to the listeners; this is a blocking operation.
     */
    public void deliver();
}
