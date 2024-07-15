/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import javax.realtime.control.AsynchronouslyInterruptedException;

/**
 * Class {@code HighResolutionTime} is the base class for
 * {@code AbsoluteTime} and {@code RelativeTime}.  It can be
 * used to express time with nanosecond resolution. This class is never
 * used directly; it is abstract and has no public constructor.
 * Instead, one of its subclasses {@link AbsoluteTime} or {@link RelativeTime}
 * should be used.  When an API is defined that has a
 * {@code HighResolutionTime} as a parameter, it can take either an
 * absolute or a relative time and will do something appropriate.
 *
 * @rtsj.warning.sync
 *
 * @param <T> is the type of high resolution time.
 */
public abstract class HighResolutionTime<T extends HighResolutionTime<T>>
  implements Comparable<T>, Cloneable
{
  /**
   * Behaves like {@code target.wait()} but with the
   * enhancement that it waits with a precision of
   * {@code HighResolutionTime} and returns true when the
   * associated notify was received, false when timeout occured.
   * As for {@code target.wait()}, there is the
   * possibility of spurious wakeup behavior.
   *
   * <P> The wait {@code time} may be relative or absolute,
   * and it is controlled by the {@code clock} associated with it.
   * When the wait {@code time} is relative, then the calling
   * thread is blocked waiting on {@code target}
   * for the amount of time given by {@code time},
   * and  measured by the associated {@code clock}.
   * When the wait {@code time} is absolute, then the calling
   * thread is blocked waiting on {@code target}
   * until the indicated {@code time} value
   * is reached by the associated {@code clock}.
   *
   * @param target The object for which to wait.
   * The current thread must have a lock on the object.
   *
   * @param time The time for which to wait. When it is
   * {@code RelativeTime(0,0)} then wait indefinitely. When it is
   * {@code null} then wait indefinitely.
   *
   * @return {@code true} when the notify was received before the
   *         timeout; {@code false} otherwise.
   *
   * @throws InterruptedException when this schedulable is
   * interrupted by {@link RealtimeThread#interrupt} or
   * {@link AsynchronouslyInterruptedException#fire} while it is
   * waiting.
   *
   * @throws StaticIllegalArgumentException when {@code time}
   *          represents a relative time less than zero.
   *
   * @throws IllegalMonitorStateException when {@code target}
   *          is not locked by the caller.
   *
   * @throws StaticUnsupportedOperationException when the
   * wait operation is not supported
   * using the {@code clock} associated with {@code time}.
   *
   * @since RTSJ 2.0 updated to add a return value.
   */
  public static boolean waitForObject(Object target, HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalMonitorStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException
  {
    return false;
  }

  /**
   * A constructor to be called from a subclass
   * @param milliseconds is the millisecond value of the new time
   * @param nanoseconds is the nanosecond value of the new time
   * @param chronograph is the reference for the new time value
   */
  HighResolutionTime(long milliseconds, int nanoseconds, Chronograph chronograph)
  {
  }

  /**
   * Proves if the argument {@code time}
   * has the same type and values as {@code this}.
   *
   * <P> Equality includes {@code Chronograph} association.
   *
   * @param time Value to be compared with {@code this}.
   *
   * @return {@code true} when the parameter {@code time} is of the
   * same type and has the same values as {@code this}, as well as the
   * same {@code Chronograph} association.
   *
   * @since RTSJ 2.0
   */
  public boolean equals(T time) { return false; }

  /**
   * Gets the reference to the {@code clock} associated with
   * {@code this}.
   *
   * @return a reference to the {@code clock} associated with
   *  {@code this}.
   *
   * @throws StaticUnsupportedOperationException when the time is based on a
   *         {@link Chronograph} that is not a {@link Clock}.
   *
   * @since RTSJ 1.0.1
   */
  public Clock getClock() throws StaticUnsupportedOperationException
  {
    return null;
  }

  /**
   * Gets a reference to the {@link Chronograph} associated with
   * {@code this}.
   *
   * @return a reference to the {@link Chronograph} associated with
   *  {@code this}.
   *
   * @since RTSJ 2.0
   */
  public final Chronograph getChronograph() { return null; }

  /**
   * Gets the milliseconds component of {@code this}.
   *
   * @return the milliseconds component of the time
   *         represented by {@code this}.
   */
  public final long getMilliseconds() { return 0; }

  /**
   * Gets the nanoseconds component of {@code this}.
   *
   * @return the nanoseconds component of the time
   *    represented by {@code this}.
   */
  public final int getNanoseconds() { return 0; }

  /**
   * Changes the value represented by {@code this} to that of the
   * given {@code time}.
   * The {@code Chronograph} associated with {@code this} is set
   * to be the {@code Chronograph} associated with
   * the {@code time} parameter.
   *
   * @param time The new value for {@code this}.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when the parameter
   * {@code time} is {@code null}.
   *
   * @throws ClassCastException when the type of {@code this}
   * and the type of the parameter {@code time} are not the same.
   *
   * @since RTSJ 1.0.1 The description of the method in 1.0 was erroneous.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public T set(T time)
  {
    return set(time.getChronograph(),
               time.getMilliseconds(),
               time.getNanoseconds());
  }

  /**
   * Sets the all components of {@code this}.
   * The setting is subject to parameter normalization.
   * When after normalization the time is negative, the time represented by
   * {@code this} is set to a negative value, but note, negative
   * times are not supported everywhere.  For instance, a negative relative
   * time is an invalid value for a periodic thread's period.
   *
   * @param chronograph The time reference for the other components of
   *               {@code this} set during the call call.
   *
   * @param millis The desired value for the millisecond component of
   *               {@code this} at the completion of the call.
   *               The actual value is the result of parameter normalization.
   *
   * @param nanos The desired value for the nanosecond component of
   *              {@code this} at the completion of the call.
   *              The actual value is the result of parameter normalization.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when there is an overflow in the
   *         millisecond component while normalizing.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public T set(Chronograph chronograph, long millis, int nanos)
    throws StaticIllegalArgumentException
  {
    // set chronograph
    return set(millis, nanos);
  }

  /**
   * Sets the millisecond and nanosecond components of {@code this}.
   * The setting is subject to parameter normalization.
   * When after normalization the time is negative then the time represented by
   * {@code this} is set to a negative value, but note that negative
   * times are not supported everywhere.  For instance, a negative relative
   * time is an invalid value for a periodic thread's period.
   *
   * @param millis The desired value for the millisecond component of
   *               {@code this} at the completion of the call.
   *               The actual value is the result of parameter normalization.
   *
   * @param nanos The desired value for the nanosecond component of
   *              {@code this} at the completion of the call.
   *              The actual value is the result of parameter normalization.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when there is an overflow in the
   *         millisecond component while normalizing.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public T set(long millis, int nanos) throws StaticIllegalArgumentException
  {
    // set nanos
    return set(millis);
  }


  /**
   * Sets the millisecond component of {@code this} to the given
   * argument, and the nanosecond component of
   * {@code this} to 0.  This method is equivalent to
   * {@code set(millis, 0)}.
   *
   * @param millis This value shall be the value of the millisecond
   * component of {@code this} at the completion of the call.
   *
   * @return {@code this}
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public T set(long millis) { return (T)this; }

  /**
   * Returns a hash code for this object in accordance with the general
   *  contract of {@code Object.hashCode}. Time objects that are
   *  equal, as defined by {@link #equals}, have the same hash code.
   *
   *  @return the hashcode value for this instance.
   */
  @Override
  public int hashCode() { return 0; }

  /**
   * Returns a clone of {@code this}.  This method should behave
   * effectively as when it constructed a new object with the visible
   * values of {@code this}.  The new object is created in the
   * current allocation context.
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public Object clone() { return null; }

  /**
   *  Compares {@code this} {@code HighResolutionTime}
   *  with the specified {@code HighResolutionTime} {@code time}.
   *
   *  @param time To be compared with the time of {@code this}.
   *  @throws ClassCastException when the {@code time} parameter is
   * not of the same class as {@code this}.
   *  @throws StaticIllegalArgumentException when the
   * {@code time} parameter is
   * not associated with the same chronograph as {@code this}, or when
   * the {@code time} parameter is {@code null}.
   *
   * @return a negative integer, zero, or a positive integer as this object is
   * less than, equal to, or greater than {@code time}.
   *
   * @since RTSJ 2.0
   */
  @Override
  public int compareTo(T time) { return 0; }

  /**
   * Determined whether or not the argument {@code object} has the
   * same type and values as {@code this}.
   *
   * <P>
   * Equality includes
   * {@code Chronograph} association.
   *
   * @param  object  Value to be compared with {@code this}.
   * @return {@code true}
   * when the parameter {@code object} is of the same type and
   * has the same
   * values as {@code this}, as well as the same {@code Chronograph} association.
   */
  @Override
  public boolean equals(Object object) { return false; }

  /**
   * Converts the time of {@code this} to an absolute time, using the
   * given instance of {@link Chronograph} to determine the current time when
   * necessary.  When {@code Chronograph} is {@code null} the
   * default realtime clock is assumed.
   *
   * When {@code dest} is not {@code null}, the result is placed
   * in it and returned. Otherwise, a new object is allocated for the result.
   * The chronograph association of the result is the {@code Chronograph}
   * passed as a parameter.  See the subclass comments for more specific
   * information.
   *
   * @param chronograph The instance of {@link Chronograph} used to convert the
   * time of {@code this} into absolute time,
   * and the new chronograph association for the result.
   * @param dest When {@code dest} is not {@code null},
   * the result is placed in it and returned.
   * @return the {@code AbsoluteTime} conversion in
   * {@code dest} when {@code dest} is not {@code null},
   * otherwise the result is returned in a newly allocated object.
   * It is associated with the {@code Chronograph} parameter.
   */
  public abstract AbsoluteTime absolute(Chronograph chronograph, AbsoluteTime dest);

  /**
   * Converts the time of {@code this} to an absolute time, using the
   * given instance of {@link Chronograph} to determine the current time when
   * necessary.  When {@code Chronograph} is {@code null} the
   * realtime clock is assumed.
   *
   * <p> A destination object is allocated to return the result.
   * The chronograph association of the result is the {@code Chronograph}
   * passed as a parameter.  See the subclass comments for more specific
   * information.
   *
   * @param chronograph The instance of {@link Chronograph} used to convert
   *        the time of {@code this} into absolute time, and the new
   *        chronograph association for the result.
   *
   * @return the {@code AbsoluteTime} conversion in a newly allocated
   *         object, associated with the {@code Chronograph} parameter.
   */
  public abstract AbsoluteTime absolute(Chronograph chronograph);

  /**
   * Converts the time of {@code this} to a relative time, using the
   * given instance of {@link Chronograph} to determine the current time when
   * necessary.  When {@code Chronograph} is {@code null} the
   * realtime clock is assumed.
   *
   * When {@code dest} is not {@code null}, the result is placed
   * there and returned. Otherwise, a new object is allocated for the result.
   * The chronograph association of the result is the {@code Chronograph}
   * passed as a parameter.  See the subclass comments for more specific
   * information.
   *
   * @param chronograph The instance of {@link Chronograph} used to convert the
   *        time of {@code this} into relative time, and the new
   *        chronograph association for the result.
   *
   * @param dest When {@code dest} is not {@code null}, the result
   *        is placed in it and returned.
   *
   * @return the {@link RelativeTime} conversion in {@code dest}
   *         when {@code dest} is not {@code null}, otherwise the
   *         result is returned in a newly allocated object.
   *
   * @since RTSJ 2.0
   */
  public abstract
  RelativeTime relative(Chronograph chronograph, RelativeTime dest);

  /**
   * Converts the time of {@code this} to a relative time, using the
   * given instance of {@link Chronograph} to determine the current time when
   * necessary.  When {@code Chronograph} is {@code null} the
   * realtime clock is assumed.
   *
   * A destination object is allocated to return the result.
   * The chronograph association of the result is
   * the {@code Chronograph} passed as a parameter.
   * See the subclass comments for more specific information.
   *
   * @param chronograph The instance of {@link Chronograph} used to convert the
   *        time of {@code this} into relative time, and the new chronograph
   *        association for the result.
   *
   * @return the {@code RelativeTime} conversion in a newly allocated
   *         object, associated with the {@code Chronograph} parameter.
   *
   * @since RTSJ 2.0
   */
  public abstract RelativeTime relative(Chronograph chronograph);

  /**
   * Equivalent to and superseded by
   * {@link #absolute(Chronograph, AbsoluteTime)}.
   *
   * When {@code dest} is not {@code null}, the result is placed
   * in it and returned. Otherwise, a new object is allocated for the result.
   * The clock association of the result is the {@code clock} passed
   * as a parameter.
   * See the subclass comments for more specific information.
   *
   * @param clock The instance of {@link Clock} used to convert the
   *        time of {@code this} into absolute time, and the new clock
   *        association for the result.
   *
   * @param dest when {@code dest} is not {@code null},
   *        the result is placed it and returned.
   *
   * @return The {@code AbsoluteTime} conversion in {@code dest} when
   *         {@code dest} is not {@code null}, otherwise the result
   *         is returned in a newly allocated object.  It is associated with
   *         the {@code clock} parameter.
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public abstract AbsoluteTime absolute(Clock clock, AbsoluteTime dest);

  /**
   * Equivalent to and superseded by {@link #absolute(Chronograph)}.
   *
   * @param clock The instance of {@link Clock} used to convert the
   * time of {@code this} into absolute time, and the new clock
   * association for the result.
   *
   * @return the {@code AbsoluteTime} conversion in a newly allocated
   * object, associated with the {@code clock} parameter.
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public abstract AbsoluteTime absolute(Clock clock);

  /**
   * Equivalent to and superseded by
   * {@link #relative(Chronograph, RelativeTime)}
   *
   * @param clock The instance of {@link Clock} used to convert the
   *        time of {@code this} into relative time, and the new clock
   *        association for the result.
   *
   * @param dest When {@code dest} is not {@code null}, the result
   *        is placed in it and returned.
   *
   * @return the {@link RelativeTime} conversion in {@code dest}
   *         when {@code dest} is not {@code null}, otherwise the
   *         result is returned in a newly allocated object.
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public abstract RelativeTime relative(Clock clock, RelativeTime dest);

  /**
   * Equivalent to and superseded by {@link #relative(Chronograph)}
   *
   * @param clock The instance of {@link Clock} used to convert the
   *        time of {@code this} into relative time, and the new clock
   *        association for the result.
   *
   * @return the {@code RelativeTime} conversion in a newly allocated
   *         object, associated with the {@code clock} parameter.
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public abstract RelativeTime relative(Clock clock);
}
