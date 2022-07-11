/**
 * Mars Simulation Project
 * UnitEvent.java
 * @version 3.2.0 2021-06-20
 * @author Scott Davis
 */
package org.mars_sim.msp.core;

/**
 * A unit change event.
 */
public interface UnitEvent {


	/**
	 * Gets the type of event.
	 * @return event type
	 */
	UnitEventType getType();

	/**
	 * Gets the target object of the event.
	 * @return target object or null if none.
	 */
	Object getTarget();

	/**
	 * Get the Unit source of the event.
	 */
	Unit getSource();
}
