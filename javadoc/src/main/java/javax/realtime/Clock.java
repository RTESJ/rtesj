/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A clock marks the passing of time.
 * It has a concept of now that can be queried through
 * {@code Clock.getTime()}, and it can have events
 * queued on it which will be fired when their appointed time is reached.
 *
 * <p> Note that while all {@code Clock} implementations use
 * representations of time derived from {@code HighResolutionTime},
 * which expresses its time in milliseconds and nanoseconds,  a
 * particular {@code Clock} may track time that is not delimited in
 * seconds or not related to wall clock time in any particular fashion
 * (<em>e.g.,</em> revolutions or event detections).  In this case, the
 * {@code Clock}'s timebase should be mapped to milliseconds and
 * nanoseconds in a manner that is computationally appropriate.
 */
public abstract class Clock implements Chronograph
{
  /**
   * There is always at least one clock object available:
   * the system realtime clock.  This clock is monotonically increasing and
   * does not need to start at the Epoch.  On a POSIX system, it is equivalent
   * to {@code CLOCK_MONOTONIC}.  It is the default {@code Clock}.
   *
   * @return the singleton instance of the default {@code Clock}
   */
  public static Clock getRealtimeClock()
  {
    return null;
  }

  /**
   * Sets the system default realtime clock.
   *
   * @param clock To be used for the realtime clock.  When {@code null}, the
   * default realtime clock is set to the original system default.
   */
  public static void setRealtimeClock(Clock clock)
  {
  }

  /**
   * A means of obtaining the Universal Time, which has no summer or winter
   * time.  Local time can be obtained by adding the appropriate time zone
   * offset.  Such a time source is not available on all systems and may take
   * a while to set up on some systems which support it.  It is not
   * guarenteed to be monotonic.
   *
   * @return a Clock that tracts UTC, such as the POSIX {@code CLOCK_REALTIME},
   *         when the timezone is set to UTC.
   *
   * @throws StaticUnsupportedOperationException when the system does
   *         not support UTC.
   *
   * @throws UninitializedStateException when UTC time is not yet available.
   *
   * @since RTSJ 2.0
   */
  public static Clock getUniversalClock()
    throws StaticUnsupportedOperationException, UninitializedStateException
  {
    throw UninitializedStateException.get();
  }

  /**
   * Sets the system default universal clock.
   *
   * @param clock To be used for the universal clock.  When {@code null}, the
   * default universal clock is set to the original system default.
   *
   * @since RTSJ 2.0
   */
  public static void setUniversalClock(Clock clock)
  {
  }

  /**
   * Constructor for the abstract class.
   */
  public Clock() {}


  /**
   * {@inheritDoc}
   *
   * @throws StaticUnsupportedOperationException {@inheritDoc}
   *
   * @throws UninitializedStateException {@inheritDoc}
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public RelativeTime getEpochOffset()
    throws StaticUnsupportedOperationException, UninitializedStateException
  {
    return getEpochOffset(null);
  }

  /**
   * Gets the precision of the clock for driving events, the nominal interval
   * between ticks that can trigger an event.  It is the same as calling
   * {@link #getDrivePrecision(RelativeTime)} with {@code null} as its
   * argument.
   *
   * @return a value representing the drive precision.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime getDrivePrecision()
  {
    return getDrivePrecision(null);
  }

  /**
   * Obtains the precision with which time can be read, i.e., the nominal
   * interval between ticks.  This base implementation does nothing
   * for compatibility and must be overridden in subclasses.
   *
   * @param dest To return the relative time value in {@code dest}.
   *        When {@code dest} is {@code null}, it allocates a new
   *        {@link RelativeTime} instance to hold the returned value.
   *
   * @return the read precision in {@code dest}, when {@code dest} is not
   *         {@code null}, or in a newly created object otherwise.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime getQueryPrecision(RelativeTime dest)
  {
    return dest;
  }

  /**
   * Gets the precision of the clock for driving events, the nominal interval
   * between ticks that can trigger an event.  The result may be larger than
   * that of {@link Chronograph#getQueryPrecision(RelativeTime)}.  The
   * base implementation does nothing for compatibility and must be
   * overridden in subclasses.
   *
   * @param dest To return the relative time value in {@code dest}.
   *        When {@code dest} is {@code null}, it allocates a new
   *        {@link RelativeTime} instance to hold the returned value.
   *
   * @return {@code dest} set to values representing the drive precision.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime getDrivePrecision(RelativeTime dest)
  {
    return dest;
  }

  @Override
  public AbsoluteTime getTime()
  {
    AbsoluteTime result = new AbsoluteTime();
    getTime(result);
    return result;
  }

  /**
   * {@inheritDoc}
   *
   * @param dest {@inheritDoc}
   * @return {@inheritDoc}
   * @since RTSJ 1.0.1 The return value is updated from {@code void} to
   *        {@code AbsoluteTime}.
   * @since RTSJ 2.0 When dest is {@code null}, a new object is allocated,
   *        when not chronograph is overwritten with {@code this}.
   */
  @Override
  public abstract AbsoluteTime getTime(AbsoluteTime dest);

  /**
   * Generic wait function that waits for a waiting for a deadlime.
   * The time given is a time on the given clock.  A user defined
   * clock uses the the {@code rendezvous}  object to hold the
   * schedulable until the time has elapsed.  The caller must be
   * synchronized on {@code rendezvous}.  This is used
   * to implement both {@link RealtimeThread#sleep} and
   * {@link RealtimeThread#waitForNextRelease}
   *
   * @param rendezvous an object to wait on until time has passed thus
   *                   letting the current thread to continue.
   *
   * @param deadline The time when the wait should end
   *
   * @return {@code true} when {@code deadline} is reached and
   *         {@code  false} when interrupted before {@code deadline}
   *
   * @throws InterruptedException when interrupted
   *
   * @throws IllegalArgumentException if time is a relative time less
   * than zero or if time is neither AbsoluteTime nor RelativeTime.
   *
   * @since since RTSJ 2.0
   */
  public boolean wait(Object rendezvous, HighResolutionTime<?> deadline)
    throws InterruptedException
  {
    return false;
  }

  /**
   * Sets the resolution of {@code this}.
   * For some hardware clocks setting resolution is
   * impossible and when this method is called on those clocks,
   * then an {@code StaticUnsupportedOperationException} is thrown.
   *
   * @param resolution The new resolution of {@code this}, when the
   *          requested value is supported by {@code this} clock.
   *          When {@code resolution} is smaller than the minimum
   *          resolution supported by {@code this} clock then it
   *          throws {@code StaticIllegalArgumentException}. When the
   *          requested {@code resolution} is not available and it
   *          is larger than the minimum resolution, then the clock will
   *          be set to the closest resolution that the clock supports,
   *          via truncation.  The value of the {@code resolution}
   *          parameter is not altered.  The clock association of the
   *          {@code resolution} parameter is ignored.
   * @throws StaticIllegalArgumentException
   *           when {@code resolution} is {@code null}, or when the
   *           requested {@code resolution} is smaller than the minimum
   *           resolution supported by this clock.
   * @throws StaticUnsupportedOperationException
   *           when the
   *           clock does not support setting its resolution.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public abstract void setResolution(RelativeTime resolution);

  /**
   * Gets the resolution of the clock, the nominal interval between ticks.
   *
   * @return A newly allocated {@link RelativeTime} object  representing
   *         the resolution of {@code this}.
   *         The returned object is associated with {@code this} clock.
   *
   * @deprecated since RTSJ 2.0
   *
   * @see #getDrivePrecision
   *
   * @see #getQueryPrecision
   */
  @Deprecated
  public abstract RelativeTime getResolution();
}
