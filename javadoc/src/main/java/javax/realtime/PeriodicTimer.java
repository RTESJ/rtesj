/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An {@link AsyncEvent} whose {@code fire} method is
 * executed periodically according to the
 * given parameters. The clock associated with the {@code Timer} start time
 * must be identical to the the clock associated with the
 * {@code Timer interval}
 *
 * <P> The first firing is at the beginning of the first interval.
 *
 * <p> When an interval greater than 0 is given, the timer will fire
 * periodically.  When an interval of 0 is given, the
 * {@code PeriodicTimer} will only fire once, unless restarted
 * after expiration, behaving like a {@code OneShotTimer}.  In all
 * cases, when the timer is <em>disabled</em> when the firing time is
 * reached, that particular firing is lost (<em>skipped</em>).  When
 * <em>enabled</em> at a later time, it will fire at its next scheduled
 * time.
 *
 * <p> When the clock time has already passed the beginning of the first
 * period, the {@code PeriodicTimer} will first fire according to
 * the {@link PhasingPolicy}.
 *
 * <p> Semantics details are described in the {@link Timer} pseudo-code
 * and compact graphic representation of state transitions.
 *
 * @rtsj.warning.sync
 */
public class PeriodicTimer extends Timer
{
  /**
   * Creates a timer that executes its fire method periodically.
   *
   * @param start The time that specifies when the first interval
   *        begins, based on the clock associated with it.  The first
   *        firing of the timer is modified according to the
   *        {@code PhasingPolicy} when the timer is started.  <P> A
   *        {@code start} value of {@code null} is equivalent
   *        to a {@code RelativeTime} of 0.
   *
   * @param interval The period of the timer. Its usage is based on the
   *        clock specified by the {@code clock} parameter.  When
   *        {@code interval} is zero or {@code null}, the
   *        period is ignored and the firing behavior of the
   *        {@code PeriodicTimer} is that of a {@link OneShotTimer}.
   *
   * @param handler The default {@code handler} to use for this event.
   *        When {@code null}, no {@code handler} is associated with the
   *        timer and nothing will happen when this event fires unless a
   *        {@code handler} is subsequently associated with the timer
   *        using the {@code addHandler()} or {@code setHandler()} method.
   *
   * @param dispatcher The dispatcher to use for triggering this event.
   *
   * @throws StaticIllegalArgumentException when {@code start} or
   *         {@code interval} is a {@code RelativeTime}
   *         instance with a value less than zero; or the clocks associated with
   *         {@code start} and
   *         {@code interval} are not the identical.
   *
   * @throws IllegalAssignmentError when this {@code PeriodicTimer}
   *         cannot hold references to {@code handler},
   *         {@code clock} and {@code interval}.
   *
   * @throws StaticUnsupportedOperationException when the {@link Chronograph}
   *         associated with {@code time} is not a {@link Clock}.
   *
   * @since RTSJ 2.0
   */
  public PeriodicTimer(HighResolutionTime<?> start,
                       RelativeTime interval,
                       AsyncBaseEventHandler handler,
                       TimeDispatcher dispatcher)
    throws StaticIllegalArgumentException,
           IllegalAssignmentError,
           StaticUnsupportedOperationException
  {
    super(start, handler, dispatcher);
  }

  /**
   * Creates a timer that executes its fire method periodically.
   * Equivalent to {@code PeriodicTimer(start, interval, handler, null)}.
   *
   * @since RTSJ 2.0
   */
  public PeriodicTimer(HighResolutionTime<?> start,
                       RelativeTime interval,
                       AsyncBaseEventHandler handler)
    throws StaticIllegalArgumentException, IllegalAssignmentError
  {
    this(start, interval, handler, null);
  }

  /**
   * Creates a timer that executes its fire method periodically.
   * Equivalent to {@code PeriodicTimer(start, interval, handler, null)}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public PeriodicTimer(HighResolutionTime<?> start,
                       RelativeTime interval,
                       AsyncEventHandler handler)
    throws StaticIllegalArgumentException, IllegalAssignmentError
  {
    this(start, interval, handler, null);
  }

  /**
   * Creates a timer that executes its fire method periodically.
   *
   * @param start The time that specifies when the first interval
   *        begins, based on the clock associated with it.  The first
   *        firing of the timer is modified according to the
   *        {@code PhasingPolicy} when the timer is started.  <P> A
   *        {@code start} value of {@code null} is equivalent
   *        to a {@code RelativeTime} of 0.
   *
   * @param interval The period of the timer. Its usage is based on the
   *        clock specified by the {@code clock} parameter.  When
   *        {@code interval} is zero or {@code null}, the
   *        period is ignored and the firing behavior of the
   *        {@code PeriodicTimer} is that of a {@link OneShotTimer}.
   *
   * @param clock The clock to be used to time the {@code start}
   *        and {@code interval}.  When {@code null}, the system
   *        Realtime clock is used.  The {@link Clock}
   *        association of the parameters {@code start} and
   *        {@code interval} is always ignored.
   *
   * @param handler The {@link AsyncEventHandler} that will be released
   *        when {@code fire} is invoked.  When {@code null}, no
   *        handler is associated with this {@code Timer} and
   *        nothing will happen when this event fires unless a handler
   *        is subsequently associated with the timer using the
   *        {@code addHandler()} or {@code setHandler()} method.
   *
   * @throws StaticIllegalArgumentException when {@code start} or
   *         {@code interval} is a {@code RelativeTime}
   *         instance with a value less than zero; or the clocks
   *         associated with {@code start} and
   *         {@code interval} are not the identical.
   *
   * @throws IllegalAssignmentError when this {@code PeriodicTimer}
   *         cannot hold references to {@code handler},
   *         {@code clock} and {@code interval}.
   *
   * @throws StaticUnsupportedOperationException when the {@link Chronograph}
   *         associated with {@code time} is not a {@link Clock}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public PeriodicTimer(HighResolutionTime<?> start,
                       RelativeTime interval,
                       Clock clock,
                       AsyncEventHandler handler)
    throws StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           IllegalAssignmentError
  {
    super(start, clock, handler);
  }

  /**
   * Each instance can only be associated with a single clock, which
   * this method can obtain.
   *
   * @return the instance of {@link Clock} that is associated with
   *          {@code this}.
   *
   * @throws StaticIllegalStateException when {@code this} has been destroyed.
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public Clock getClock() throws StaticIllegalStateException
  {
    return null;
  }

  /**
   * Creates a release parameters object with new objects containing
   * copies of the values corresponding to this timer.
   * When the {@code PeriodicTimer} interval is greater than 0, creates
   * a {@link PeriodicParameters} object with a start time and period that
   * correspond to the next firing (or skipping) time, and interval,
   * of this timer. When the interval is 0, creates
   * an {@link AperiodicParameters}
   * object, since in this case the timer behaves like
   * a {@link OneShotTimer}.
   *
   * <p> When this timer is active, then the start time is the next firing
   * (or skipping) time returned as an {@link AbsoluteTime}.  Otherwise,
   * the start time is the initial firing (or skipping) time, as set by
   * the last call to {@link Timer#reschedule}, or when there
   * was no such call, by the constructor of this timer.
   *
   * @return a new release parameters object with new objects containing
   *         copies of the values corresponding to this timer.  When the
   *         interval is greater than zero, returns a new instance of
   *         {@link PeriodicParameters}. When the interval is zero returns
   *         a new instance of {@link AperiodicParameters}.
   *
   * @throws StaticIllegalStateException when this {@code Timer} has
   *         been <em>destroyed</em>.
   */
  @Override
  public ReleaseParameters<?> createReleaseParameters()
  {
    return null;
  }

  /**
   * Gets the time at which this {@code PeriodicTimer}
   * is next expected to fire or to skip.
   * When the {@code PeriodicTimer} is <em>disabled</em>, the returned
   * time is that of the skipping or firing.
   * When the {@code PeriodicTimer} is <em>not-active</em> it throws
   * {@code StaticIllegalStateException}.
   *
   * @return the absolute time at which {@code this} is next expected
   * to fire or to skip, in a newly allocated {@link AbsoluteTime} object.
   *
   * When the timer has been created or re-scheduled
   * (see {@link Timer#reschedule(HighResolutionTime)})
   * using an instance of
   * {@code RelativeTime} for its time parameter, then it will return
   * the sum of the current time and the {@code RelativeTime}
   * remaining time before the timer is expected to fire/skip.
   *
   * Within a periodic timer activation, the returned time is
   * associated with the start clock before the first fire (or skip) time,
   * and associated with the interval clock otherwise.
   *
   * @throws ArithmeticException when the result does not fit
   * in the normalized format.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>, or when it is <em>not-active</em>.
   *
   */
  @Override
  public AbsoluteTime getFireTime()
    throws ArithmeticException, StaticIllegalStateException
  {
    return null;
  }

  /**
   * Gets the time at which this {@code PeriodicTimer}
   * is next expected to fire or to skip.
   * When the {@code PeriodicTimer} is <em>disabled</em>, the returned
   * time is that of the skipping.
   * When the {@code PeriodicTimer} is <em>not-active</em> it throws
   * {@code StaticIllegalStateException}.
   *
   * @param dest The instance of {@link AbsoluteTime} which will be
   *        updated in place and returned.  The clock association of the
   *        {@code dest} parameter is ignored.  When
   *        {@code dest} is {@code null}, a new object is
   *        allocated for the result.
   *
   * @return the instance of {@link AbsoluteTime} passed as parameter,
   *         with time values representing the absolute time at which
   *         {@code this} is expected to fire or to skip.  When the
   *         {@code dest} parameter is {@code null}, the result
   *         is returned in a newly allocated object.
   *
   * When the timer has been created or re-scheduled
   * (see {@link Timer#reschedule(HighResolutionTime)})
   * using an instance of
   * {@code RelativeTime} for its time parameter then it will return
   * the sum of the current time and the {@code RelativeTime}
   * remaining time before the timer is expected to fire/skip.
   *
   * Within a periodic timer activation, the returned time is
   * associated with the start clock before the first fire (or skip) time,
   * and associated with the interval clock otherwise.
   *
   * @throws ArithmeticException when the result does not fit
   *         in the normalized format.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   *         has been <em>destroyed</em>, or when it is <em>not-active</em>.
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public AbsoluteTime getFireTime(AbsoluteTime dest)
  {
    return null;
  }

  /**
   * Gets the interval of {@code this} {@code Timer}.
   *
   * @return the {@code RelativeTime} instance assigned as this
   *         periodic timer's interval by the constructor or
   *         {@link #setInterval(RelativeTime)}.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   *         has been <em>destroyed</em>.
   */
  public RelativeTime getInterval()
  {
    return null;
  }

  /**
   * Resets the {@code interval} value of {@code this}.
   *
   * @param interval A {@link RelativeTime} object which is the interval
   *        used to reset this Timer.  A {@code null}
   *        {@code interval} is interpreted as
   *        {@code RelativeTime(0,0)}.
   *
   *        <P> The {@code interval} does not affect the first
   *        firing (or skipping) of a timer's activation. At each firing
   *        (or skipping), the next fire (or skip) time of an
   *        <em>active</em> periodic timer is established based on the
   *        {@code interval} currently in use.  Resetting the
   *        {@code interval} of an <em>active</em> periodic timer
   *        only affects future fire (or skip) times after the next.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code interval} is a
   *         {@code RelativeTime} instance with a value less than
   *         zero, or the clock associated with {@code interval} is different
   *         to the clock associated with {@code this}.
   *
   * @throws IllegalAssignmentError when this {@code PeriodicTimer}
   *         cannot hold a reference to {@code interval}.
   *
   * @throws StaticIllegalStateException when this {@code Timer} has been
   *         <em>destroyed</em>.
   */
  @ReturnsThis
  public PeriodicTimer setInterval(RelativeTime interval) { return this; }

  /**
   * This should not be called for application code, except for
   * emulation.  The fire method is reserved for the use of the system.
   * When {@code this} is enabled, it releases all handlers and then
   * reschedules itself for the next period without changing state.
   * When distabled, but active, it simply reschedules itself.
   * Otherwise it does nothing.
   *
   * @since RTSJ 2.0 moved here from Timer, since {@code OneShotTimer} and
   * {@code PeriodicTimer} have slightly different semantics.
   */
  @Override
  public void fire()
  {
  }
}
