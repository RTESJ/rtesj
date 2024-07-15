/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
/*
 * Created on May 23, 2010
 * Copyright (C) 2004, 2005 TimeSys Corporation, All Rights Reserved.
 */
package javax.realtime;

/**
 * A new type of event that carries a long as a payload.
 *
 * @see AsyncEvent
 *
 * @since RTSJ 2.0
 */
public class AsyncLongEvent extends AsyncBaseEvent
{
  /**
   * Creates a new {@code AsyncLongEvent} object.
   */
  public AsyncLongEvent()
  {
  }

  /**
   * When enabled, releases the handlers associated with this
   * instance of {@code AsyncLongEvent} with the {@code long}
   * passed by {@link #fire(long)}.  When no handlers are attached or
   * this object is disabled the method does nothing, i.e., it skips the
   * release.
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
   *           scheduler's semantics, when there is a handler associated
   *           with this event that has its MIT violated by the call to
   *           fire (and it has set the minimum inter-arrival time
   *           violation behavior to MITViolationExcept). Only the
   *           handlers which do not have their MITs violated are
   *           released in this situation.
   * @throws EventQueueOverflowException when the queue of release
   *         information, arrival time and payload,
   *         overflows. Only the handlers which do not cause this exception
   *         to be thrown are released in this situation.  When fire is called
   *         from the infrastructure, such as for an {@link ActiveEvent},
   *         this exception is ignored.
   */
  public void fire(long value)
    throws MITViolationException, EventQueueOverflowException
  {
  }
}
