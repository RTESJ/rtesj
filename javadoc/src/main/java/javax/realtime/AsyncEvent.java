/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An asynchronous event can have a set of handlers associated with it,
 * and when the event occurs, the {@code fireCount} of each handler
 * is incremented, and the handlers are released (see
 * {@link AsyncEventHandler}).
 *
 * @since RTSJ 2.0 extends AsyncBaseEvent
 */
public class AsyncEvent extends AsyncBaseEvent
{
  /**
   * Creates a new {@code AsyncEvent} object.
   */
  public AsyncEvent()
  {
  }

  /**
   * When enabled, release the asynchronous event handlers associated with
   * this instance of {@code AsyncEvent}.  When this object is disabled the
   * method does nothing, i.e., it skips the release.  When no handlers are
   * attached, the release is ignored.  This method does not suspend itself
   * and has a runtime complexity of {@code O(n)}, where {@code n} is the
   * number of attached handers.
   *
   * For an instance of {@code AsyncEvent} that has more than one instance of
   * {@code AsyncEventHandler},
   * <ul>
   * <li> when one of these handlers throws an exception, all instances of
   * {@code AsyncEventHandler} not affected by the exception must be released
   * normally before the exception is propagated, and </li>
   *
   * <li> when more than one of these handlers throws an exception, the
   * propagation of {@link MITViolationException} has precedence over
   * {@link ArrivalTimeQueueOverflowException}, which has precedence over
   * all others.</li>
   * </ul>
   *
   * The later case can only occur when more than one of the handlers has a
   * release parameters instance of type {@code SporadicParameters}, since
   * only them can {@link MITViolationException} and
   * {@link ArrivalTimeQueueOverflowException} be thrown.
   *
   * @throws MITViolationException under the base priority scheduler's
   *         semantics when there is a handler associated with this
   *         event that has its MIT violated by the call to fire (and it
   *         has set the minimum interarrival time violation behavior to
   *         MITViolationExcept). Only the handlers which do not have their
   *         MITs violated are released in this situation.
   *
   * @throws ArrivalTimeQueueOverflowException when the queue of release
   *         information, arrival time and payload, overflows. Only the
   *         handlers which do not cause this exception to be thrown are
   *         released in this situation.  When fire is called from the
   *         infrastructure, such as for an {@link ActiveEvent}, this
   *         exception is ignored.
   */
  public void fire() {}

  /**
   * Determines whether or not the handler given as the parameter is
   * associated with {@code this}.
   *
   * @param handler
   *          The handler to be tested to determine its association with
   *          {@code this}.
   * @return {@code true} when the parameter is associated with {@code this}.
   *         {@code false} when {@code handler} is {@code null} or the
   *         parameters is not associated with {@code this}.
   *
   * @deprecated since RTSJ 2.0, replaced by
   *             {@link AsyncBaseEvent#handledBy(AsyncBaseEventHandler)}
   */
  @Deprecated
  public boolean handledBy(AsyncEventHandler handler)
  {
    return handledBy((AsyncBaseEventHandler)handler);
  }

  /**
   * Adds a handler to the set of handlers associated with this event. An
   * instance of {@code AsyncBaseEvent} may have more than one
   * associated handler.  However, adding a handler to an event has no
   * effect when the handler is already attached to the event.
   * <p>
   * The execution of this method is atomic with respect to the execution of the
   * {@code fire()} method.
   * <p>
   * Note that there is an implicit reference to the handler stored in
   * {@code this}.  The assignment must be valid under any applicable memory
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
   *           when this {@code AsyncEvent} cannot hold a reference to
   *           {@code handler}.
   * @throws StaticIllegalStateException when the configured {@code Scheduler}
   *         and {@code SchedulingParameters} for {@code handler} are
   *         not compatible with one another.
   * @throws ScopedCycleException when {@code handler} has an explicit initial
   *         scoped memory area that has already been entered from a memory
   *         area other than the area where {@code handler} was allocated.
   *
   * @deprecated since RTSJ 2.0, replaced by
   *             {@link AsyncBaseEvent#addHandler(AsyncBaseEventHandler)}
   */
  @Deprecated
  public void addHandler(AsyncEventHandler handler) {}

  /**
   * Replaced by {@link AsyncBaseEvent#setHandler(AsyncBaseEventHandler)}
   *
   * @param handler For becoming the sole handler for {@code this}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public void setHandler(AsyncEventHandler handler) {}

  /**
   * Replaced by {@link AsyncBaseEvent#removeHandler(AsyncBaseEventHandler)}
   *
   * @param handler For removal.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public void removeHandler(AsyncEventHandler handler)
  {
    super.removeHandler(handler);
  }

  /**
   * Binds this to an external event, a <em>happening</em>.  The
   * meaningful values of {@code happening} are implementation
   * dependent. This instance of {@code AsyncEvent} is considered
   * to have occurred whenever the happening is triggered. More than one
   * happening can be bound to the same
   * {@code AsyncEvent}. However, binding a happening to an event
   * has no effect when the happening is already bound to the event.
   *
   * <p> When an event, which is declared in a scoped memory area, is
   * bound to an external happening, the reference count of that scoped
   * memory area is incremented (as if there is an external realtime
   * thread accessing the area). The reference count is decremented when
   * the event is unbound from the happening.
   *
   * @param happening An implementation-dependent value that binds
   *        this instance of {@code AsyncEvent} to a happening.
   *
   * @throws UnknownHappeningException when the String value is not
   *            supported by the implementation.
   * @throws StaticIllegalArgumentException when
   *            {@code happening} is {@code null}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public void bindTo(String happening) {}

  /**
   * Removes a binding to an external event, a <em>happening</em>. The
   * meaningful values of  {@code happening} are implementation
   * dependent. When the associated event is declared in a scoped memory
   * area, the reference count for the memory area is decremented.
   *
   *  @param happening An implementation-dependent value representing some
   *  external event to which this instance of {@code AsyncEvent}
   *  is bound.
   *
   *  @throws UnknownHappeningException when this instance of
   *         {@code AsyncEvent} is
   *         not bound to the given {@code happening}
   *         or the given {@code happening}
   *         is not supported by the implementation.
   * @throws StaticIllegalArgumentException when
   *         {@code happening} is {@code null}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public void unbindTo(String happening) {}
}
