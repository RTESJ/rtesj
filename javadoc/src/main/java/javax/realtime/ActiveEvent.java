
/*------------------------------------------------------------------------*
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime;


/**
 * <p>This is the interface for defining the active event system.  Classes
 * implementing {@code ActiveEvent} are used to connect events
 * that take place outside the Java virtual machine to RTSJ activities.
 *
 * <p> When an event takes place outside the Java virtual machine, some
 * event-specific code within the Java virtual machine executes.  That code
 * notifies the {@code ActiveEvent} infrastructure of this event by
 * calling a {@code trigger} method in the event.
 *
 * <p> An instance of this class holds a reference to its dispatcher.  When
 * {@link ActiveEvent#isActive} is {@code true}, the dispatcher must also
 * hold a reference to the instance.  For this reason, whenever an active
 * event instance is active, it is also a execution context, so that this
 * reference can be safely held during this time.  Only the active event
 * instance must be assignable to its dispatcher instance under the memory
 * assignment rules, but not visa versa.
 *
 * @since RTSJ 2.0
 */
public interface ActiveEvent<T extends Releasable<T, D>,
                             D extends ActiveEventDispatcher<D, T>>
  extends Releasable<T,D>
{
  /**
   * Determines the activation state of this event, i.e., it has been started
   * but not yet stopped again.
   *
   * @return {@code true} when active, {@code false} otherwise.
   */
  public boolean isActive();

  /**
   * Determines the running state of this event, i.e., it is both active and
   * enabled.
   *
   * @return {@code true} when active and enabled, {@code false}
   *         otherwise.
   */
  public boolean isRunning();

  /**
   * Starts this active event by registering it with its dispatcher.
   *
   * @throws StaticIllegalStateException when this event
   *         has already been started or its dispatcher has been destroyed.
   */
  public void start() throws StaticIllegalStateException;

  /**
   * Starts this active event by registering it with its dispatcher.
   *
   * @param disabled True for starting in a disabled state.
   *
   * @throws StaticIllegalStateException when this event
   *         has already been started or its dispatcher has been destroyed.
   */
  public void start(boolean disabled) throws StaticIllegalStateException;

  /**
   * Stops this active event by deregistering it from its dispatcher.
   *
   * @return the previous enabled state.
   *
   * @throws StaticIllegalStateException when this event
   *         is not running.
   */
  public boolean stop() throws StaticIllegalStateException;

  /**
   * Changes the state of the event so that associated handlers are
   * released on fire. Each subclass provides a fire method as a means
   * of dispatching its handlers when requested. This method enables
   * that request mechanism.
   */
  public void enable();

  /**
   * Changes the state of the event so that associated handlers are
   * skipped on fire. Each subclass provides a fire method as a means of
   * dispatching its handlers when requested. This method disables that
   * request mechanism.
   */
  public void disable();

  /**
   * Obtain the current dispatcher for this event.
   *
   * @return the dispatcher associated with this event.
   */
  public D getDispatcher();

  /**
   * Change the current dispatcher for this event.  When
   * {@code dispatcher} is {@code null}, the default dispatcher is restored.
   *
   * @return the dispatcher associated with this event.
   */
  public D setDispatcher(D dispatcher);
}
