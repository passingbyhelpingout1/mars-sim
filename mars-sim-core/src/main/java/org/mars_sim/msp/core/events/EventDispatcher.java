/**
 * Mars Simulation Project
 * EventDispatcher.java
 * @date 11-07-2022
 * @author Barry Evans
 */
package org.mars_sim.msp.core.events;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.mars_sim.msp.core.logging.SimLogger;

/**
 * This class dispatches Event to a set of listeners in an asynchronous manner.
 * This avoids the Simulation execution thrads directly updating the UI and potentially
 * in rare cases could create a deadlock.
 */
public class EventDispatcher {

    /**
     * This runnable will dispatch the Events to the listeners
     */
    private class EventThread implements Runnable {

        @Override
        public void run() {
            try {
                while (running) {
                    EventInstance next = queue.take();
                    logger.info("Deliver " + next + " queue size " + queue.size());

                    // TODO Check the last event is not the same as the previous one
                    next.deliver();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static final SimLogger logger = SimLogger.getLogger(EventDispatcher.class.getName());

    private static final int MAX_QUEUE = 30;
    private static EventDispatcher instance = new EventDispatcher();

    private boolean running = false;
    private BlockingQueue<EventInstance> queue = new LinkedBlockingDeque<>(MAX_QUEUE);

    private boolean queuing = true;

    /**
     * Get the current Event dispatcher.
     */
    public static EventDispatcher getInstance() {
        return instance;
    }
 
    private EventDispatcher() {
        start();
    }

    /**
     * Add the event to be dispatched.
     * @param newEvent
     * @return Was the event queued
     */
    public boolean addEvent(EventInstance newEvent) {
        boolean result = queuing;
        if (queuing && !queue.offer(newEvent)) {
            logger.warning("Event " + newEvent + " can not be added as queue size is maximum " + queue.size());
            result = false;
        }

        return result;
    }

    /**
     * Start distributing events
     */
    public void start() {
        running = true;
        queuing = true;
        Thread eventThread = new Thread(new EventThread(), "Mars-Event-Thread");
        eventThread.start();
    }

    /**
     * Stop distributing events.
     */
    public void stop() {
        running = false;
    }

    /**
     * Set whether events will be queued or dropped. 
     * @param newQueue Setting for queue setting.
     */
    public void setQueuing(boolean newQueue) {
        this.queuing = newQueue;
    }
 }
