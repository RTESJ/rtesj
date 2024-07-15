/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A timed {@link AsyncEvent} that is driven by a {@link Clock}.
 * It will fire once,
 * when the clock time reaches the time-out time, unless restarted after
 * expiration.
 * When the timer is <em>disabled</em> at the expiration of the indicated time,
 * the firing is lost (<em>skipped</em>).
 *
 * After expiration, the {@code OneShotTimer} becomes
 * <em>not-active</em> and <em>disabled</em>.
 *
 * When the clock time has
 * already passed the time-out time, it will fire immediately after it is
 * started or after it is rescheduled while <em>active</em>.
 *
 * <P>
 * Semantics details are described in the {@link Timer} pseudocode
 * and compact graphic representation of state transitions.
 *
 * @rtsj.warning.sync
 */
public class OneShotTimer extends Timer
{
  /**
   * Creates an instance of {@link OneShotTimer}, based on the given
   * {@code clock}, that will execute its
   * {@code fire} method according to the given time.
   * The {@link Clock} association of the parameter {@code time}
   * is ignored.
   *
   * @param time The time used to determine when to fire the event.
   * A {@code time} value of {@code null} is equivalent to a
   * {@code RelativeTime} of 0, and in this case
   * the {@code Timer} fires
   * immediately upon a call to {@code start()}.
   *
   * @param clock The clock on which to base this timer, overriding
   * the clock associated with the parameter {@code time}.
   * When {@code null}, the system {@code Realtime clock} is used.
   * The clock associated with the parameter {@code time} is always
   * ignored.
   *
   * @param handler The {@link AsyncEventHandler} that will be
   * released when {@code fire} is invoked.
   * When {@code null}, no handler is associated with this
   * {@code Timer} and nothing will happen when this event fires
   * unless a handler is subsequently associated with the timer using the
   * {@code addHandler()} or {@code setHandler()} method.
   *
   * @throws StaticIllegalArgumentException when {@code time}
   * is a {@code RelativeTime} instance less than zero.
   *
   * @throws StaticUnsupportedOperationException when the {@link Chronograph}
   *         associated with {@code time} is not a {@link Clock}.
   *
   * @throws IllegalAssignmentError when this {@code OneShotTimer}
   *         cannot hold references to {@code time}, {@code handler},
   *         or {@code clock}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public OneShotTimer(HighResolutionTime<?> time,
                      Clock clock,
                      AsyncEventHandler handler)
    throws StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           IllegalAssignmentError
  {
    super(time, clock, handler);
  }

  /**
   * Creates an instance of {@link OneShotTimer}, based on the given
   * {@code clock}, that will execute its {@code fire} method
   * according to the given time.  The {@link Clock} association of
   * the parameter {@code time} is ignored.
   *
   * @param time The time used to determine when to fire the event.
   *        A {@code time} value of {@code null} is equivalent to a
   *        {@code RelativeTime} of 0, and in this case the
   *        {@code Timer} fires immediately upon a call to {@code start()}.
   *
   * @param handler The default {@code handler} to use for this event.
   *        When {@code null}, no {@code handler} is associated with the
   *        timer and nothing will happen when this event fires unless a
   *        {@code handler} is subsequently associated with the timer
   *        using the {@code addHandler()} or {@code setHandler()} method.
   *
   * @param dispatcher The {@code dispatcher} used to interface between
   *        this {@code timer} and its associated clock.  When
   *        {@code null}, the system default dispatcher is used.
   *
   * @throws StaticIllegalArgumentException when {@code time} is a
   *         {@code RelativeTime} instance less than zero.
   *
   * @throws StaticUnsupportedOperationException when the {@link Chronograph}
   *         associated with {@code time} is not a {@link Clock}.
   *
   * @throws IllegalAssignmentError when this {@code OneShotTimer}
   *         cannot hold references to {@code time}, {@code handler},
   *         or {@code clock}.
   *
   * @since RTSJ 2.0
   */
  public OneShotTimer(HighResolutionTime<?> time,
                      AsyncBaseEventHandler handler,
                      TimeDispatcher dispatcher)
    throws StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           IllegalAssignmentError
  {
    super(time, handler, dispatcher);
  }

  /**
   * The equivalent of calling
   * {@code OneShotTimer(HighResolutionTime, AsyncBaseEventHandler, TimeDispatcher)}
   * with arguments {@code (time, handler, null)}.
   *
   * @param time Time to release its handlers.
   * @param handler Handler to be released.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public OneShotTimer(HighResolutionTime<?> time, AsyncEventHandler handler)
  {
    this(time, handler, null);
  }

  /**
   * The equivalent of calling
   * {@code OneShotTimer(HighResolutionTime, AsyncBaseEventHandler, TimeDispatcher)}
   * with arguments {@code (time, handler, null)}.
   *
   * @param time Time to release its handlers.
   * @param handler Handler to be released.
   */
  public OneShotTimer(HighResolutionTime<?> time, AsyncBaseEventHandler handler)
  {
    this(time, handler, null);
  }

  /**
   * This should not be called for application code, except for
   * emulation.  The fire method is reserved for the use of the system.
   * When {@code this} is enabled, it releases all handlers and then
   * calls {@link Timer#stop()}.  When distabled, but active, it only
   * calls {@code Timer.stop()}.  Otherwise it does nothing.
   *
   * @since RTSJ 2.0 moved here from Timer, since {@code OneShotTimer} and
   * {@code PeriodicTimer} have slightly different semantics.
   */
  @Override
  public void fire()
  {
  }
}
