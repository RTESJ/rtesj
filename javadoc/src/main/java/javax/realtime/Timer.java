/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A <i>timer</i> is a timed event that measures time according to a
 * given {@link Clock}.  This class defines basic functionality
 * available to all timers.  Applications will generally use either
 * {@link PeriodicTimer} to create an event that is fired repeatedly at
 * regular intervals, or {@link OneShotTimer} for an event that just
 * fires once at a specific time.  A timer is always associated with at
 * least one {@link Clock}, which provides the basic facilities of
 * something that ticks along following some time line (realtime,
 * CPU-time, user-time, simulation-time, etc.).  All timers are created
 * <em>disabled</em> and do nothing until {@code start()} is
 * called.
 */
public abstract class Timer
  extends AsyncEvent
  implements ActiveEvent<Timer, TimeDispatcher>
{
  /**
   * Creates a timer that fires according to the given {@code time} based
   * on the {@link Clock} associated with {@code time} and is dispatched
   * by the specified {@code dispatcher}.
   *
   * @param time The parameter used to determine when to fire the event.
   *        A {@code time} value of {@code null} is equivalent to a
   *        {@code RelativeTime} of 0, and in this case
   *        the {@code Timer} fires
   *        immediately upon a call to {@code start()}.
   *
   * @param handler The default {@code handler} to use for this event.
   *        When {@code null}, no {@code handler} is associated with the
   *        timer and nothing will happen when this event fires unless a
   *        {@code handler} is subsequently associated with the timer
   *        using the {@code addHandler()} or {@code setHandler()} method.
   *
   * @param dispatcher The object used to interface between
   *        this {@code timer} and its associated clock.  When
   *        {@code null}, the system default dispatcher is used.
   *
   * @throws StaticIllegalArgumentException when {@code time} is a
   *         negative {@code RelativeTime} value.
   *
   * @throws StaticUnsupportedOperationException when {@code time} has a
   *         {@link Chronograph} is not a {@code clock}.
   *
   * @throws IllegalAssignmentError when this {@code Timer}
   *   cannot hold references to
   *   {@code handler} and {@code clock}.
   *
   * @since RTSJ 2.0
   */
  protected Timer(HighResolutionTime<?> time,
                  AsyncBaseEventHandler handler,
                  TimeDispatcher dispatcher)
    throws StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           IllegalAssignmentError
  {
  }

  /**
   * Creates a timer that fires according to the given {@code time},
   * which must be based on the supplied {@link Clock} {@code clock} (if
   * any), and is handled by the specified {@link AsyncEventHandler}
   * {@code handler}.  The system default dispatcher will be used.
   * <p>
   * This constructor is slated for deprecation in a future release, and
   * a constructor that does not receive a {@code Clock} argument should
   * be used in preference.
   *
   * @param time The parameter used to determine when to fire the event.
   *        A {@code time} value of {@code null} is equivalent to a
   *        {@code RelativeTime} of 0, and in this case
   *        the {@code Timer} fires immediately upon a call to {@code start()}.
   *
   * @param clock The clock on which to base this timer.
   *        When {@code null}, the clock associated with {@code time} is used.
   *
   * @param handler The default {@code handler} to use for this event.
   *        When {@code null}, no {@code handler} is associated with the
   *        timer and nothing will happen when this event fires unless a
   *        {@code handler} is subsequently associated with the timer
   *        using the {@code addHandler()} or {@code setHandler()} method.
   *
   * @throws StaticIllegalArgumentException when {@code time} is a
   *         negative {@code RelativeTime} value or the supplied
   *         {@code clock} is not the {@code Clock} associated with
   *         {@code time}.
   *
   * @throws StaticUnsupportedOperationException when {@code time} has a
   *         {@link Chronograph} that is not an instance of
   *         {@code Clock}.
   *
   * @throws IllegalAssignmentError when this {@code Timer}
   *         cannot hold references to {@code handler} and {@code clock}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  protected Timer(HighResolutionTime<?> time,
                  Clock clock,
                  AsyncEventHandler handler)
    throws StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           IllegalAssignmentError
  {
  }

  /**
   * Obtains the instance of {@link Clock} on which this {@code timer} is based.
   *
   * @return the instance of {@link Clock} associated with this
   * {@code Timer}.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   *         has been <em>destroyed</em>.
   */
  public Clock getClock() throws StaticIllegalStateException
  {
    return null;
  }

  /**
   * Gets the start time of this {@code Timer}.  Note that the start time
   * uses copy semantics, so changes made to the value returned by this
   * method do not affect the start time of this {@code Timer}.
   *
   * @return a reference to the time (or start) parameter used when
   *         constructing this {@code Timer}, ensuring the content has
   *         the original values.
   *
   * @since RTSJ 2.0
   */
  public HighResolutionTime<?> getStart()
  {
    return null;
  }

  /**
   * Returns a newly-created time representing the time when the timer
   * actually started, or when the timer has been rescheduled, the
   * effective start time after the reschedule.
   *
   * @return the time {@code this} actually started.
   *
   * @throws StaticIllegalStateException when the timer is not active or has
   *         been destroyed.
   *
   * @throws ArithmeticException when the result does not fit in the
   * normalized format.
   *
   * @since RTSJ 2.0
   */
  public AbsoluteTime getEffectiveStartTime()
    throws StaticIllegalStateException, ArithmeticException
  {
    return null;
  }


  /**
   * Updates {@code dest} to represent the time when the timer actually
   * started, or when the timer has been rescheduled, the effective start
   * time after the reschedule.
   *
   * When {@code dest} is {@code null}, behaves as if
   * {@link #getEffectiveStartTime()} had been called.
   *
   * @param dest An object used to store the time {@code this}
   *        actually started.
   *
   * @return the time when the timer actually started, or when it has been
   *         rescheduled, the effective start time after the reschedule.
   *
   * @throws StaticIllegalStateException when the timer is not active or has
   * been destroyed.
   *
   * @throws ArithmeticException when the result does not fit in the
   * normalized format.
   *
   * @since RTSJ 2.0
   */
  public AbsoluteTime getEffectiveStartTime(AbsoluteTime dest)
    throws StaticIllegalStateException, ArithmeticException
  {
    return null;
  }

  /**
   * Gets the time at which this {@code Timer} is expected to fire.
   * When the {@code Timer} is <em>disabled</em>, the returned
   * time is that of the skipping or the firing.
   * When the {@code Timer} is <em>not-active</em>, it throws
   * {@code StaticIllegalStateException}.
   *
   * @return the absolute time at which {@code this} is expected to
   * fire (release handlers or skip), in a newly allocated {@link AbsoluteTime}
   * object.
   *
   * When the timer has been created or re-scheduled
   * (see {@link Timer#reschedule})
   * using an instance of
   * {@code RelativeTime} for its time parameter, then it will return
   * the sum of the current time and the {@code RelativeTime}
   * remaining time before the timer is expected to fire/skip.
   *
   * The clock association of the returned time is the clock
   * on which {@code this} timer is based.
   *
   * @throws ArithmeticException when the result does not fit
   * in the normalized format.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>, or when it is <em>not-active</em>.
   *
   */
  public AbsoluteTime getFireTime()
    throws StaticIllegalStateException, ArithmeticException
  {
    return null;
  }

  /**
   * Gets the time at which this {@code Timer} is expected to fire.
   * When the {@code Timer} is <em>disabled</em>, the returned
   * time is that of the skipping or the firing.
   * When the {@code Timer} is <em>not-active</em> it throws
   * {@code StaticIllegalStateException}.
   *
   * @param dest The instance of {@link AbsoluteTime}
   * which will be updated in place and returned.
   * The clock association of the {@code dest} parameter
   * is ignored.
   * When {@code dest} is {@code null}, a new object
   * is allocated for the result.
   *
   * @return the instance of {@link AbsoluteTime}
   * passed as parameter, with time values representing
   * the absolute time at which {@code this} is expected to
   * fire (release its handlers or skip).
   * When the {@code dest} parameter is {@code null},
   * the result is returned in a newly allocated object.
   *
   * When the timer has been created or rescheduled
   * (see {@link Timer#reschedule})
   * using an instance of
   * {@code RelativeTime} for its time parameter then it will return
   * the sum of the current time and the {@code RelativeTime}
   * remaining time before the timer is expected to fire.
   *
   * The clock association of the returned time is the clock
   * on which {@code this} timer is based.
   *
   * @throws ArithmeticException when the result does not fit
   * in the normalized format.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>, or when it is <em>not-active</em>.
   *
   * @since RTSJ 1.0.1
   */
  public AbsoluteTime getFireTime(AbsoluteTime dest)
    throws StaticIllegalStateException, ArithmeticException
  {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TimeDispatcher getDispatcher()
  {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TimeDispatcher setDispatcher(TimeDispatcher dispatcher)
  {
    return null;
  }

  /**
   * Determines the activation state of this happening,
   * i.e., it has been started.
   *
   * @return {@code true} when active, {@code false} otherwise.
   *
   * @since RTSJ 2.0
   */
  @Override
  public boolean isActive()
  {
    return false;
  }


  /**
   * Determines if {@code this} is <em>active</em> and is
   * <em>enabled</em> such that when the given time occurs it will
   * fire the event. Given the {@code Timer} current state it answers
   * the question <em>"Is firing expected?".</em>
   *
   * @return {@code true} when the timer is <em>active</em> and
   * <em>enabled</em>; otherwise
   * {@code false}, when the timer has either not been <em>started</em>,
   * it has been <em>started</em> but it is <em>disabled</em>,
   * or it has been <em>started</em> and is now <em>stopped.</em>
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>.
   */
  @Override
  public boolean isRunning() throws StaticIllegalStateException
  {
    return true;
  }

  /**
   * @throws StaticIllegalStateException when this {@code Timer} has been
   * <em>destroyed</em>.
   *
   * @param handler An event handler to be added to the Timer
   *
   * @return {@code true} when {@code handler} is associated with
   *         {@code this}, otherwise {@code false}.
   *
   * @since RTSJ 1.0.1
   *
   * @deprecated since RTSJ 2.0, replaced by
   *             {@link AsyncBaseEvent#handledBy(AsyncBaseEventHandler)}
   */
  @Deprecated
  @Override
  public boolean handledBy(AsyncEventHandler handler)
    throws StaticIllegalStateException
  {
    return false;
  }

  /**
   * Creates a {@link ReleaseParameters} object appropriate to the
   * timing characteristics of this event.
   * The default is the most pessimistic: {@link AperiodicParameters}.
   * This is typically called by code that is setting up a
   * {@code handler} for
   * this event that will fill in the parts of the release parameters
   * for which it has values, e.g. cost.
   *
   * @return a newly created {@link ReleaseParameters} object.
   *
   * @throws StaticIllegalStateException when this {@code Timer} has been
   * <em>destroyed</em>.
   */
  @Override
  public ReleaseParameters<?> createReleaseParameters()
    throws StaticIllegalStateException
  {
    return null;
  }

  /**
   * Re-enables this timer after it has been <em>disabled</em>.
   * (See {@link Timer#disable()}.)
   *
   * When the {@code Timer} is already <em>enabled</em>,
   * this method does nothing.
   *
   * When the {@code Timer} is <em>not-active</em>,
   * this method does nothing.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>.
   */
  @Override
  public void enable() throws StaticIllegalStateException
  {
  }

  /**
   * Disables this timer, preventing it from firing.
   * It may subsequently be re-<em>enabled</em>.
   * When the timer is <em>disabled</em> when its fire time occurs, then it
   * will not release its handlers.
   * However, a <em>disabled</em> timer created using an instance of
   * {@code RelativeTime} for its time parameter continues
   * to count while it is <em>disabled</em>, and no changes take place in
   * a <em>disabled</em> timer created using an instance of
   * {@code AbsoluteTime}. In both cases the potential firing is
   * simply masked, or skipped.
   * When the timer is subsequently re-<em>enabled</em>
   * before its fire time or(?) it is
   * <em>enabled</em> when its fire time occurs, then it will fire.
   * It is important to note that this method does not delay
   * the time before a possible firing.
   * For example, when the timer is set to fire at time
   * 42 and the {@code disable()} is called at time 30 and
   * {@code enable()} is  called at time 40 the firing will occur
   * at time 42 (not time 52). These semantics imply
   * also that firings are not queued. Using the above example,
   * when enable was called at time 43 no firing will occur,
   * since at time 42 {@code this} was <em>disabled</em>.
   *
   * When the {@code Timer} is already <em>disabled</em>,
   * whether it is <em>active</em> or <em>inactive</em>,
   * this method does nothing.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>.
   */
  @Override
  public void disable() throws StaticIllegalStateException
  {
  }

  /**
   * Starts this timer.
   * A timer starts measuring time from when it is started;
   * this method makes the timer <em>active</em> and <em>enabled.</em>
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>,
   * or when this timer is already <em>active.</em>
   */
  @Override
  public void start() throws StaticIllegalStateException
  {
  }

  /**
   * Starts this timer.
   * A timer starts measuring time from when it is started.
   * When {@code disabled} is {@code true} starts the
   * timer making it <em>active</em> in a <em>disabled</em> state.
   * When {@code disabled} is {@code false} this method behaves
   * like the {@code start()} method.
   *
   * @param disabled When {@code true}, the timer will be
   * <em>active</em> but <em>disabled</em> after it is started.
   * When {@code false} this method behaves
   * like the {@code start()} method.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>,
   * or when this timer is <em>active</em>.
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public void start(boolean disabled) throws StaticIllegalStateException
  {
  }


  /**
   * Starts the timer with the specified {@link PhasingPolicy}.
   *
   * @param phasingPolicy Determines what happens when the start is too late.
   * @throws LateStartException when this method is called after its absolute
   *         start time and the {@code phasingPolicy} is
   *         {@link PhasingPolicy#STRICT_PHASING}.
   * @throws StaticIllegalArgumentException when the start time of this timer
   *        is not an absolute time, or {@code phasingPolicy} is
   *        {@code null} or, when this in not a periodic timer,
   *        {@code ADJUST_FORWARD} or {@code ADJUST_BACKWARD}.
   *
   * @since RTSJ 2.0
   */
  public void start(PhasingPolicy phasingPolicy)
    throws LateStartException, StaticIllegalArgumentException
  {
    start(false, phasingPolicy);
  }

  /**
   * Starts the timer with the specified {@link PhasingPolicy} and the
   * specified disabled state.
   *
   * @param disabled It determines the mode of start: {@code true} for
   *        enabled and {@code false} for disabled for consistency with
   *        {@link Timer#start(boolean)}.
   * @param phasingPolicy It determines what happens when the start is too late.
   * @throws LateStartException when this method is called after its absolute
   *         start time and the {@code phasingPolicy} is
   *         {@link PhasingPolicy#STRICT_PHASING}.
   * @throws StaticIllegalArgumentException when the start time of this timer
   *        is not an absolute time, or {@code phasingPolicy} is
   *        {@code null} or, when this in not a periodic timer,
   *        {@code ADJUST_FORWARD} or {@code ADJUST_BACKWARD}.
   *
   * @since RTSJ 2.0
   */
  public void start(boolean disabled, PhasingPolicy phasingPolicy)
    throws LateStartException, StaticIllegalArgumentException
  {
  }

  /**
   * Stops a timer when it is <em>active</em> and changes its state
   * to <em>inactive</em> and <em>disabled</em>.
   *
   * @return {@code true} when {@code this} was
   *         <em>enabled</em> and {@code false} otherwise.
   *
   * @throws StaticIllegalStateException when this {@code Timer}
   * has been <em>destroyed</em>.
   */
  @Override
  public boolean stop() throws StaticIllegalStateException
  {
    return true;
  }

  /**
   * Changes the scheduled time for this event.
   * This method can take either an {@code AbsoluteTime}
   * or a {@code RelativeTime} for its argument, and the
   * {@code Timer} will behave as if created using that type
   * for its {@code time} parameter.
   * The rescheduling will take place between the invocation and
   * the return of the method.
   *
   * <P>
   * Note that while the scheduled time is changed as described above,
   * the rescheduling itself is applied only on the first firing
   * (or on the first skipping when <em>disabled</em>) of a timer's
   * activation. When {@code reschedule} is invoked after the
   * current activation timer's firing, then the rescheduled
   * {@code time} will be effective only upon the next
   * {@code start} or {@code startDisabled} command
   * (which may need to be preceded by a {@code stop} command).
   *
   * <P>
   * When {@code reschedule} is invoked with a
   * {@code RelativeTime} {@code time} on an
   * <em>active</em> timer before its first firing/skipping, then
   * the rescheduled firing/skipping {@code time} is
   * relative to the time of invocation.
   *
   * @param time The time to reschedule for this event firing.
   *        When {@code time} is {@code null}, the previous
   *        time is still the time used for the {@code Timer} firing.
   *
   * @throws StaticIllegalArgumentException when {@code time} is a
   *         negative {@code RelativeTime} value.
   *
   * @throws StaticIllegalStateException when this {@code Timer} has been
   * <em>destroyed</em>.
   */
  public void reschedule(HighResolutionTime<?> time)
    throws StaticIllegalStateException, StaticIllegalArgumentException
  {
  }

  /**
   * Stops {@code this} from counting or comparing
   * when <em>active</em>, removes from it all the
   * associated handlers if any, and releases as many of its
   * resources as possible back to the system.
   * Every method invoked on a {@code Timer}
   * that has been <em>destroyed</em>
   * will throw {@code StaticIllegalStateException}.
   *
   * @throws StaticIllegalStateException when this {@code Timer} has been
   * <em>destroyed</em>.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public void destroy() throws StaticIllegalStateException
  {
  }

  /**
   * Should not be called, since this was only meant for binding happenings
   * to normal instances {@code AsyncEvent}, not to special subclasses.
   *
   * @param happening to which to bind
   *
   * @throws StaticUnsupportedOperationException when {@code bindTo}
   * is called on a {@code Timer}.
   *
   * @since RTSJ 1.0.1
   *
   * @deprecated RTSJ 2.0
   */
  @Deprecated
  @Override
  public void bindTo(String happening)
    throws StaticUnsupportedOperationException
  {
  }
}
