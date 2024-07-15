/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This is the base class for all asynchronous events, where
 * asynchronous is in regards to running code, not external time. This
 * class unifies the original {@link AsyncEvent} with
 * {@link AsyncLongEvent} and {@link AsyncObjectEvent}.
 *
 * <p> Note that when this class is collected, all its handlers are
 * automatically removed as if {@link #setHandler} was called with a
 * {@code null} parameter.
 *
 * @since RTSJ 2.0
 */
public abstract class AsyncBaseEvent
{
  /**
   * Creates a new {@code AsyncBaseEvent} object.
   */
  AsyncBaseEvent()
  {
  }


  /**
   * Determines the firing state (releasing or skipping) of this event,
   * i.e., whether it is enabled or disabled.
   *
   * @return {@code true} when releasing, {@code false} when skipping.
   *
   * @since RTSJ 2.0 Inherited by AyncEvent
   */
  public boolean isRunning()
  {
    return false;
  }


  /**
   * Determines whether or not the handler given as the parameter is
   * associated with {@code this}.
   *
   * @param handler
   *          The handler to be tested to determine if it is associated with
   *          {@code this}.
   * @return true when the parameter is associated with {@code this}. False
   *         when {@code handler} is {@code null} or the parameters is
   *         not associated with {@code this}.
   *
   * @since RTSJ 2.0 Inherited by AyncEvent
   */
  public boolean handledBy(AsyncBaseEventHandler handler)
  {
    return false;
  }


  /**
   * Changes the state of the event so that associated handlers are
   * released on fire. Each subclass provides a fire method as means
   * of dispatching its handlers when requested. This method enables
   * that request mechanism.
   *
   * @since RTSJ 2.0 Inherited by AyncEvent
   */
  public void enable()
  {
  }


  /**
   * Changes the state of the event so that associated handlers are
   * skipped on fire. Each subclass provides a fire method as means of
   * dispatching its handlers when requested. This method disables that
   * request mechanism.
   *
   * @since RTSJ 2.0 Inherited by AyncEvent
   */
  public void disable()
  {
  }


  /**
   * Adds a handler to the set of handlers associated with this event. An
   * instance of {@code AsyncBaseEvent} may have more than one
   * associated handler. However, adding a handler to an event has no
   * effect when the handler is already attached to the event.
   * <p>
   * The execution of this method is atomic with respect to the execution of the
   * {@code fire()} method.
   * <p>
   * Note that there is an implicit reference to the handler stored in
   * {@code this}. The assignment must be valid under any applicable memory
   * assignment rules.
   *
   * @param handler
   *          The new handler to add to the list of handlers already
   *          associated with this.
   *          When {@code handler} is already associated with
   *          the event, the call has no effect.
   * @throws StaticIllegalArgumentException
   *           when {@code handler} is {@code null} or the handler has
   *           {@link PeriodicParameters}. Only the subclass
   *           {@link PeriodicTimer} is allowed to have handlers with
   *           {@link PeriodicParameters}.
   * @throws IllegalAssignmentError
   *           when this {@code AsyncBaseEvent} cannot hold a reference to
   *           {@code handler}.
   * @throws StaticIllegalStateException when the configured {@code Scheduler}
   *         and {@code SchedulingParameters} for {@code handler} are
   *         not compatible with one another.
   * @throws ScopedCycleException when {@code handler} has an explicit initial
   *         scoped memory area that has already been entered from a memory
   *         area other than the area where {@code handler} was allocated.
   *
   * @since RTSJ 2.0 Inherited by AyncEvent
   */
  public void addHandler(AsyncBaseEventHandler handler)
  {
  }


  /**
   * Associates a new handler with this event and removes all existing
   * handlers. The execution of this method is atomic with respect to
   * the execution of the {@code fire()} method.
   *
   * @param handler
   *          The instance of {@link AsyncBaseEventHandler} to be
   *          associated with {@code this}. When {@code handler} is
   *          {@code null} then no handler will be associated with {@code this},
   *          i.e., it behaves effectively as if {@code setHandler(null)}
   *          invokes {@link #removeHandler(AsyncBaseEventHandler)} for each
   *          associated handler.
   * @throws StaticIllegalArgumentException
   *           when {@code handler} has {@link PeriodicParameters}. Only
   *           the subclass {@link PeriodicTimer} is allowed to have handlers
   *           with {@link PeriodicParameters}.
   * @throws IllegalAssignmentError
   *           when this {@code AsyncBaseEvent} cannot hold a reference to
   *           {@code handler}.
   *
   * @since RTSJ 2.0 Inherited by AyncEvent
   */
  public void setHandler(AsyncBaseEventHandler handler)
  {
  }


  /**
   * Removes a handler from the set associated with this event. The
   * execution of this method is atomic with respect to the execution of
   * the {@code fire()} method.
   * <p>
   * A removed handler continues to execute until its fireCount becomes zero and
   * it completes.
   * <p>
   * When {@code handler} has a scoped non-default initial memory area and
   * execution of this method causes {@code handler} to become
   * unfirable, this method shall not return until all related finalization
   * has completed.
   *
   * @param handler
   *          The handler to be disassociated from {@code this}. When
   *          {@code null} nothing happens.
   *          When the {@code handler} is not already associated with
   *          {@code this} then nothing happens.
   *
   * @since RTSJ 2.0 Inherited by AyncEvent
   */
  public void removeHandler(AsyncBaseEventHandler handler)
  {
  }


  /**
   * Determines whether or not this event has any handlers.
   *
   * @return {@code true} when and only when at least one handler is associated
   *         with this event.
   *
   * @since RTSJ 2.0 Inherited by AyncEvent
   */
  public boolean hasHandlers()
  {
    return false;
  }


  /**
   * Creates a {@link ReleaseParameters} object appropriate to the
   * release characteristics of this event. The default is the most
   * pessimistic: {@link AperiodicParameters}. This is typically called
   * by code that is setting up a handler for this event that will fill
   * in the parts of the release parameters for which it has values,
   * e.g., cost. The returned {@link ReleaseParameters} object is not
   * bound to the event. Any changes in the event's release parameters
   * are not reflected in previously returned objects.
   * <p>
   * When an event returns {@link PeriodicParameters}, there is no requirement
   * for an implementation to check that the handler is released periodically.
   *
   * @return a new {@link ReleaseParameters} object.
   */
  public ReleaseParameters<?> createReleaseParameters()
  {
    return new AperiodicParameters(null, null, null, null);
  }

}
