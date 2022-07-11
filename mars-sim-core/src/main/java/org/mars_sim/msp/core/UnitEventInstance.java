/**
 * Mars Simulation Project
 * UnitEventInstance.java
 * @date 11-07-2022
 * @author Barry Evans
 */
package org.mars_sim.msp.core;

import java.util.List;

import org.mars_sim.msp.core.events.EventInstance;

/**
 * A unit change event.
 */
class UnitEventInstance implements UnitEvent, EventInstance {


	// Data members
	/** The event type. */
	private final UnitEventType type;
	/** The event target object, if any. */
	private final Object target;
	private Unit source;
    private List<UnitListener> listeners;

	/**
	 * Constructor.
	 * @param source the object throwing the event.
	 * @param type the type of event.
	 */
	UnitEventInstance(Unit source, UnitEventType type, Object target, List<UnitListener> listeners) {
		// Use EventObject constructor
		this.source = source;
		this.type = type;
		this.target = target;
        this.listeners = listeners;
	}

	/**
	 * Gets the type of event.
	 * @return event type
	 */
    @Override
	public UnitEventType getType() {
		return type;
	}

	/**
	 * Gets the target object of the event.
	 * @return target object or null if none.
	 */
    @Override
	public Object getTarget() {
		return target;
	}

	/**
	 * Get the Unit source of the event.
	 */
    @Override
	public Unit getSource() {
		return source;
	}

	/**
	 * Override {@link Object#toString()} method.
	 */
	@Override
	public String toString() {
		return type.name() + " for " + source.getName();
	}

    /**
     * Deliver this event to the various listeners
     */
    @Override
    public void deliver() {

		synchronized (listeners) {
			for(UnitListener l : listeners) {
				l.unitUpdate(this);
			}
		}
    }
}
