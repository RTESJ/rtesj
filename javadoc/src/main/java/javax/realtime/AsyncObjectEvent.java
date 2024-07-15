/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
/*
 * Created on May 23, 2010
 *
 * Copyright (C) 2004, 2005 TimeSys Corporation, All Rights Reserved.
 */
package javax.realtime;

/**
 * A new type of event that carries an object as a payload.
 *
 * @param <P> is the type of object this event receives when fired.
 *
 * @see AsyncEvent
 *
 * @since RTSJ 2.0
 */
public class AsyncObjectEvent<P>
  extends AsyncBaseEvent
{
  /**
   * Creates a new {@code AsyncObjectEvent} instance.
   */
  public AsyncObjectEvent()
  {
  }

  /**
   * When enabled, fires this instance of {@code AsyncObjectEvent}.
   * The asynchronous event handlers associated with this event will be
   * released with the object passed by {@link #fire}.
   * When no handlers are attached or this object is disabled
   * the method does nothing, i.e., it skips the release.
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
   * @param value The payload passed to the event.
   *
   * @throws MITViolationException under the base priority
   *         scheduler's semantics when there is a handler associated with
   *         this event that has its MIT violated by the call to fire
   *         (and it has set the minimum inter-arrival time violation
   *         behavior to MITViolationExcept). Only the handlers which do
   *         not have their MITs violated are released in this
   *         situation.
   *
   * @throws ArrivalTimeQueueOverflowException when the queue of
   *         releases information, arrival time and payload,
   *         overflows. Only the handlers which do not cause this
   *         exception to be thrown are released in this situation.
   *         When fire is called from the infrastructure, such as for an
   *         {@link ActiveEvent}, this exception is ignored.
   *
   * @throws IllegalAssignmentError when {@code P} is not assignable the
   *         event queue of one of the associated handlers.
   */
  public void fire(P value)
    throws MITViolationException,
           EventQueueOverflowException,
           IllegalAssignmentError
  {
  }
}
