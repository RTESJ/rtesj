/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.util.Date;

/**
 * An object that represents a specific point in time given by
 * milliseconds plus nanoseconds past some point in time fixed by its
 * {@code Chronograph}. For the universal clock, the
 * fixed point is the Epoch (January 1, 1970, 00:00:00 GMT).
 * The correctness of the Epoch as a time base depends on the
 * realtime clock synchronization with an external world time reference.
 * This representation was designed to be compatible with
 * the standard Java representation of an absolute time in the
 * {@code java.util.Date} class.
 *
 * <p> A time object in normalized form represents negative time when
 * both components are nonzero and negative, or one is nonzero and
 * negative and the other is zero.  For {@code add} and {@code subtract}
 * negative values behave as they do in arithmetic.
 *
 * @rtsj.warning.sync
 */
public class AbsoluteTime extends HighResolutionTime<AbsoluteTime>
{
  /**
   * Constructs an {@code AbsoluteTime} object with time millisecond
   * and nanosecond components past the epoch for {@code Chronograph}.
   *
   * <p> The value of the {@code AbsoluteTime} instance is based on the
   * parameter {@code millis} plus the parameter {@code nanos}. The
   * construction is subject to {@code millis} and {@code nanos}
   * parameters normalization.  When, after normalization, the time
   * object is negative, the time represented by this is time before
   * {@code this} chronograph's {@code epoch}.  The chronograph
   * association is made with the {@code Chronograph} parameter. When
   * {@code Chronograph} is {@code null} the association is made with
   * the default realtime clock.
   *
   * <p> Note that the start of a chronograph's epoch is an attribute of the
   * chronograph. It is defined as the Epoch (00:00:00 GMT on Jan 1,
   * 1970) for the calendar clock, but other classes of
   * chronograph may define other epochs.
   *
   * @param millis The desired value for the millisecond component of
   *          {@code this}.  The actual value is the result of parameter
   *          normalization.
   * @param nanos The desired value for the nanosecond component of
   *          {@code this}.  The actual value is the result of parameter
   *          normalization.
   * @param chronograph Provides the time reference for the
   *          newly constructed object.  The realtime clock is used when
   *          this argument is {@code null}.
   * @throws StaticIllegalArgumentException when there is an overflow in the
   *           millisecond component when normalizing.
   * @since RTSJ 2.0
   */
  public AbsoluteTime(long millis, int nanos, Chronograph chronograph)
    throws StaticIllegalArgumentException
  {
    super(millis, nanos, chronograph);
  }


  /**
   * Equivalent to {@link #AbsoluteTime(long, int, Chronograph)} with the
   * argument list {@code (millis, nanos, null)}
   *
   * @param millis The desired value for the millisecond component
   *               of {@code this}.  The actual value is the result
   *               of parameter normalization.
   * @param nanos The desired value for the nanosecond component
   *          of {@code this}.
   *          The actual value is the result of parameter normalization.
   * @throws StaticIllegalArgumentException
   *           when there is an overflow in the
   *           millisecond component when normalizing.
   */
  public AbsoluteTime(long millis, int nanos)
    throws StaticIllegalArgumentException
  {
    this(millis, nanos, null);
  }


  /**
   * Equivalent to {@link #AbsoluteTime(long, int, Chronograph)} with the
   * argument list {@code (date.getTime(), 0, chronograph)}.
   *
   * <p>Warning: While the {@code date} is used to set the milliseconds
   * component of the new {@code AbsoluteTime} object (with nanoseconds
   * component set to 0), the new object represents the {@code date}
   * only when the {@code Chronograph} parameter has an {@code epoch} equal to
   * Epoch.
   *
   * <p>The time reference is given by the {@code Chronograph}
   * parameter.  When {@code Chronograph} is {@code null} the
   * association is made with the default realtime clock.
   *
   * @param date The {@code java.util.Date} representation of the time
   *          past the  {@code epoch}.
   * @param chronograph Provides the time reference for
   *          the newly constructed object.
   * @throws StaticIllegalArgumentException
   *           when the {@code date} parameter is {@code null}.
   * @since RTSJ 2.0
   */
  public AbsoluteTime(Date date, Chronograph chronograph)
    throws StaticIllegalArgumentException
  {
    super(date.getTime(), 0, chronograph);
  }


  /**
   * Equivalent to {@link #AbsoluteTime(long, int, Chronograph)} with the
   * argument list {@code (date.getTime(), 0, null)}.
   *
   * @param date The {@code java.util.Date} representation of the time
   *          past the {@code epoch}.
   * @throws StaticIllegalArgumentException when the {@code date}
   *          parameter is {@code null}.
   */
  public AbsoluteTime(Date date) throws StaticIllegalArgumentException
  {
    super(date.getTime(), 0, null);
  }


  /**
   * Equivalent to {@link #AbsoluteTime(long, int, Chronograph)} with the
   * argument list {@code (time.getMilliseconds(), time.getNanoseconds(), time.getChronograph())}.
   *
   * @param time The {@code AbsoluteTime} object which is the source for
   *          the copy.
   *
   * @throws StaticIllegalArgumentException when the {@code time}
   *           parameter is {@code null}.
   */
  public AbsoluteTime(AbsoluteTime time) throws StaticIllegalArgumentException
  {
    super(time.getMilliseconds(), time.getNanoseconds(), time.getChronograph());
  }


  /**
   * Equivalent to {@link #AbsoluteTime(long, int, Chronograph)} with the
   * argument list {@code (0, 0, chronograph)}.
   *
   * @param chronograph
   *          Provides the time reference for the newly
   *          constructed object.
   * @since RTSJ 2.0
   */
  public AbsoluteTime(Chronograph chronograph)
  {
    super(0, 0, chronograph);
  }


  /**
   * Equivalent to {@link #AbsoluteTime(long, int, Chronograph)} with the
   * argument list {@code (0, 0, null)}.
   */
  public AbsoluteTime()
  {
    super(0, 0, null);
  }


  /**
   * Superceeded by and equivalent to
   * {@link #AbsoluteTime(long, int, Chronograph)}
   *
   * @param millis
   *          The desired value for the millisecond component
   *          of {@code this}.
   *          The actual value is the result of parameter normalization.
   * @param nanos
   *          The desired value for the nanosecond component
   *          of {@code this}.
   *          The actual value is the result of parameter normalization.
   * @param clock
   *          The clock providing the association for the newly
   *          constructed object.
   * @throws StaticIllegalArgumentException
   *           when there is an overflow in the
   *           millisecond component when normalizing.
   * @since RTSJ 1.0.1
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public AbsoluteTime(long millis, int nanos, Clock clock)
    throws StaticIllegalArgumentException
  {
    super(millis, nanos, clock);
  }


  /**
   * Equivalent to {@link #AbsoluteTime(long, int, Chronograph)} with the
   * arguments {@code time.getMilliseconds(), time.getNanoseconds(), clock()}.
   *
   * @param time The {@code AbsoluteTime} object which is the
   *          source for the copy.
   * @param clock The clock providing the association for the newly
   *          constructed object.
   * @throws StaticIllegalArgumentException
   *           when the {@code time} parameter is {@code null}.
   * @since RTSJ 1.0.1
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public AbsoluteTime(AbsoluteTime time, Clock clock)
    throws StaticIllegalArgumentException
  {
    super(time.getMilliseconds(), time.getNanoseconds(), clock);
  }

  /**
   * Equivalent to {@link #AbsoluteTime(long, int, Chronograph)} with
   * arguments {@code time.getMilliseconds(), time.getNanoseconds(), chronograph()}.
   *
   * @param time The {@code AbsoluteTime} object which is the
   *          source for the copy.
   * @param chronograph The chronograph providing the association for the newly
   *          constructed object.
   * @throws StaticIllegalArgumentException
   *           when the {@code time} parameter is {@code null}.
   * @since RTSJ 2.0
   */
  public AbsoluteTime(AbsoluteTime time, Chronograph chronograph)
    throws StaticIllegalArgumentException
  {
    super(time.getMilliseconds(), time.getNanoseconds(), chronograph);
  }


  /**
   * Superceeded by and equivalent to {@link #AbsoluteTime(Date, Chronograph)}
   *
   * @param date The {@code java.util.Date} representation of the time
   *          past the Epoch.
   * @param clock The clock providing the association for the newly
   *          constructed object.
   * @throws StaticIllegalArgumentException when the {@code date}
   *           parameter is {@code null}.
   * @since RTSJ 1.0.1
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public AbsoluteTime(Date date, Clock clock)
    throws StaticIllegalArgumentException
  {
    super(date.getTime(), 0, clock);
  }


  /**
   * Superceeded by and equivalent to {@link #AbsoluteTime(Chronograph)}
   *
   * @param clock
   *          The clock providing the association for the newly
   *          constructed object.
   * @since RTSJ 1.0.1
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public AbsoluteTime(Clock clock)
  {
    super(0, 0, clock);
  }


  /**
   * Creates a copy of {@code this} modified when necessary to have the
   * specified chronograph association.  A new object is allocated for
   * the result.  This method is the implementation of the
   * {@code abstract} method of the {@code HighResolutionTime} base
   * class.  No conversion into {@code AbsoluteTime} is needed in this
   * case.  The result is associated with the {@code Chronograph} passed
   * as a parameter.  When {@code Chronograph} is {@code null}, the
   * association is made with the default realtime clock.
   *
   * @param chronograph It is used only
   *          as the new time reference associated with the result, since
   *          no conversion is needed.
   * @return The copy of {@code this} in a newly allocated
   *         {@code AbsoluteTime} object, associated with the
   *         {@code Chronograph} parameter.
   *
   * @since RTSJ 2.0
   */
  @Override
  public AbsoluteTime absolute(Chronograph chronograph)
  {
    return null;
  }


  /**
   * Copies {@code this} into {@code dest}, when necessary modified to
   * have the specified chronograph association.  A new object is
   * allocated for the result.  This method is the implementation of the
   * {@code abstract} method of the {@code HighResolutionTime} base
   * class.  No conversion into {@code AbsoluteTime} is needed in this
   * case.  The result is associated with the {@code Chronograph} passed
   * as a parameter.  When {@code Chronograph} is {@code null}, the
   * association is made with the default realtime clock.
   *
   * @param chronograph It is used only as the new time reference
   *          associated with the result, since no conversion is needed.
   * @param dest the instance to fill.
   * @return The copy of {@code this} in a newly allocated
   *         {@code AbsoluteTime} object, associated with the
   *         {@code Chronograph} parameter.
   *
   * @since RTSJ 2.0
   */
  @Override
  public AbsoluteTime absolute(Chronograph chronograph, AbsoluteTime dest)
  {
    return null;
  }


  /**
   * Converts the time of {@code this} to a relative time, using the
   * given instance of {@link Chronograph} to determine the current
   * time.  The calculation is the current time indicated by the given
   * instance of {@link Chronograph} subtracted from the time given by
   * {@code this}.  When {@code Chronograph} is {@code null}, the default
   * realtime clock is assumed.  A destination object is allocated to
   * return the result.  The time reference of the result is given by the
   * {@code Chronograph} passed as a parameter.
   *
   * @param chronograph The instance of {@link Chronograph} used to
   *          convert the time of {@code this} into relative time, and
   *          the new chronograph association for the result.
   * @return the {@code RelativeTime} conversion in a newly allocated
   *         object, associated with the {@code Chronograph} parameter.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   *
   * @since RTSJ 2.0
   */
  @Override
  public RelativeTime relative(Chronograph chronograph)
  {
    return null;
  }


  /**
   * Converts the time of {@code this} to a relative time, using the
   * given instance of {@link Chronograph} to determine the current
   * time.  The calculation is the current time indicated by the given
   * instance of {@link Chronograph} subtracted from the time given by
   * {@code this}.  When {@code Chronograph} is {@code null}, the default
   * realtime clock is assumed.  When {@code dest} is not {@code null},
   * the result is placed in it and returned. Otherwise, a new object is
   * allocated for the result.  The time reference of the result is
   * given by the {@code Chronograph} passed as a parameter.
   *
   * @param chronograph The instance of {@link Chronograph} used to
   *          convert the time of {@code this} into relative time, and
   *          the new chronograph association for the result.
   * @param dest When {@code dest} is not {@code null}, the result is
   *          placed in it and returned.
   * @return the {@code RelativeTime} conversion in {@code dest} when
   *         {@code dest} is not {@code null},
   *         otherwise the result is returned in a newly allocated object,
   *         associated with the {@code Chronograph} parameter.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   */
  @Override
  public RelativeTime relative(Chronograph chronograph, RelativeTime dest)
  {
    return null;
  }


  /**
   * Creates a new object representing the result of adding
   * {@code millis} and {@code nanos} to the values from {@code this}
   * and normalizing the result. The result will have the same chronograph
   * association as {@code this}.
   *
   * @param millis The number of milliseconds to be added to {@code this}.
   * @param nanos The number of nanoseconds to be added to {@code this}.
   * @return a new {@code AbsoluteTime} object whose time is the
   *         normalization of {@code this} plus {@code millis} and
   *         {@code nanos}.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   */
  public AbsoluteTime add(long millis, int nanos)
    throws ArithmeticException
  {
    return null;
  }


  /**
   * Returns an object containing the value resulting from adding {@code
   * millis} and {@code nanos} to the values from {@code this} and
   * normalizing the result.  When {@code dest} is not {@code null}, the
   * result is placed in it and returned. Otherwise, a new object is
   * allocated for the result.  The result will have the same chronograph
   * association as {@code this}, and the chronograph association with
   * {@code dest} is ignored.
   *
   * @param millis The number of milliseconds to be added to {@code this}.
   * @param nanos The number of nanoseconds to be added to {@code this}.
   * @param dest When {@code dest} is not {@code null}, the result is
   *          placed in it and returned.
   * @return the result of the normalization of {@code this} plus
   *         {@code millis} and {@code nanos} in {@code dest} when
   *         {@code dest} is not {@code null}, otherwise the result is
   *         returned in a newly allocated object.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   */
  public AbsoluteTime add(long millis, int nanos, AbsoluteTime dest)
    throws ArithmeticException
  {
    return dest;
  }


  /**
   * Creates a new instance of {@code AbsoluteTime} representing the
   * result of adding {@code time} to the value of {@code this} and
   * normalizing the result.  The {@code Chronograph} associated with
   * {@code this} and the {@code Chronograph} associated with the {@code time}
   * parameter must be the same, and such association is used for the
   * result.
   *
   * @param time The time to add to {@code this}.
   * @return a new {@code AbsoluteTime} object whose time is the
   *         normalization of {@code this} plus the parameter {@code time}.
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *         associated with {@code this} and the {@code Chronograph}
   *         associated with the {@code time} parameter are different,
   *         or when the {@code time} parameter is {@code null}.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   */
  public AbsoluteTime add(RelativeTime time)
    throws ArithmeticException, StaticIllegalArgumentException
  {
    return null;
  }


  /**
   * Returns an object containing the value resulting from adding
   * {@code time} to the value of {@code this} and normalizing the result.
   * When {@code dest} is not {@code null}, the result is placed in it
   * and returned. Otherwise, a new object is allocated for the result.
   * The {@code Chronograph} associated with {@code this} and the
   * {@code Chronograph} associated with the {@code time} parameter must be
   * the same, and such association is used for the result.  The
   * {@code Chronograph} associated with the {@code dest} parameter is ignored.
   *
   * @param time The time to add to {@code this}.
   * @param dest When {@code dest} is not {@code null}, the result is
   *          placed in it and returned.
   * @return the result of the normalization of {@code this} plus the
   *         {@code RelativeTime} parameter {@code time} in
   *         {@code dest} when {@code dest} is not {@code null},
   *         otherwise the result is returned in a newly allocated object.
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *            associated with {@code this} and the {@code Chronograph}
   *            associated with the {@code time} parameter are
   *            different, or when the {@code time} parameter is {@code null}.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   */
  public AbsoluteTime add(RelativeTime time, AbsoluteTime dest)
    throws ArithmeticException, StaticIllegalArgumentException
  {
    return dest;
  }


  /**
   * Converts the time given by {@code this} to a {@code Date} format.
   * Note that {@code Date} represents time as milliseconds so the
   * nanoseconds of {@code this} will be lost.
   *
   * @return a newly allocated {@code Date} object with a value of the
   *         time past the Epoch represented by {@code this}.
   * @throws StaticUnsupportedOperationException when the {@code Chronograph}
   *           associated with {@code this} does not have
   *           the concept of date.
   */
  public Date getDate() throws StaticUnsupportedOperationException
  {
    return null;
  }


  /**
   * Changes the time represented by {@code this} to that given by the
   * parameter.  Note that {@code Date} represents time as milliseconds
   * so the nanoseconds of {@code this} will be set to 0.  The chronograph
   * association is implicitly made with the default realtime clock.
   *
   * @param date A reference to a {@code Date} which will become the
   *          time represented by {@code this} after the completion of
   *          this method.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when the parameter
   *         {@code date} is {@code null}.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public AbsoluteTime set(Date date) throws StaticIllegalArgumentException
  {
    return this;
  }


  /**
   * Creates a new instance of {@code RelativeTime} representing the
   * result of subtracting {@code time} from the value of {@code this}
   * and normalizing the result.  The {@code Chronograph} associated with
   * {@code this} and the {@code Chronograph} associated with the {@code time}
   * parameter must be the same, and such association is used for the
   * result.
   *
   * @param time The time to subtract from {@code this}.
   *
   * @return a new {@code RelativeTime} object whose time is
   *         the normalization of {@code this} minus the
   *         {@code AbsoluteTime} parameter {@code time}.
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *          associated with {@code this} and the {@code Chronograph}
   *          associated with the {@code time} parameter are different,
   *          or when the {@code time} parameter is {@code null}.
   * @throws ArithmeticException when the result does not fit
   *           in the normalized format.
   */
  public RelativeTime subtract(AbsoluteTime time)
    throws StaticIllegalArgumentException, ArithmeticException
  {
    return null;
  }


  /**
   * Returns an object containing the value resulting from subtracting
   * {@code time} from the value of {@code this} and normalizing the
   * result.  When {@code dest} is not {@code null}, the result is
   * placed there and returned. Otherwise, a new object is allocated for
   * the result.  The {@code Chronograph} associated with {@code this} and the
   * {@code Chronograph} associated with the {@code time} parameter must be
   * the same, and such association is used for the result.  The
   * {@code Chronograph} associated with the {@code dest} parameter is ignored.
   *
   * @param time The time to subtract from {@code this}.
   * @param dest When {@code dest} is not {@code null}, the result is
   *          placed in it and returned.
   * @return the result of the normalization of {@code this} minus the
   *         {@code AbsoluteTime} parameter {@code time} in {@code dest}
   *         when {@code dest} is not {@code null}, otherwise the result
   *         is returned in a newly allocated object.
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *           associated with {@code this} and the {@code Chronograph}
   *           associated with the {@code time} parameter are different,
   *           or when the {@code time} parameter is {@code null}.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   */
  public RelativeTime subtract(AbsoluteTime time, RelativeTime dest)
    throws StaticIllegalArgumentException, ArithmeticException
  {
    return dest;
  }


  /**
   * Creates a new instance of {@code AbsoluteTime} representing the
   * result of subtracting {@code time} from the value of {@code this}
   * and normalizing the result.  The {@code Chronograph} associated with
   * {@code this} and the {@code Chronograph} associated with the {@code time}
   * parameter must be the same, and such association is used for the
   * result.
   *
   * @param time The time to subtract from {@code this}.
   * @return a new {@code AbsoluteTime} object whose time is the
   *         normalization of {@code this} minus the parameter
   *         {@code time}.
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *           associated with {@code this} and the {@code Chronograph}
   *           associated with the {@code time} parameter are different,
   *           or when the {@code time} parameter is {@code null}.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   */
  public AbsoluteTime subtract(RelativeTime time)
    throws StaticIllegalArgumentException, ArithmeticException
  {
    return null;
  }


  /**
   * Returns an object containing the value resulting from subtracting
   * {@code time} from the value of {@code this} and normalizing the
   * result.  When {@code dest} is not {@code null}, the result is
   * placed there and returned. Otherwise, a new object is allocated for
   * the result.  The {@code Chronograph} associated with {@code this} and the
   * {@code Chronograph} associated with the {@code time} parameter must be
   * the same, and such association is used for the result.  The
   * {@code Chronograph} associated with the {@code dest} parameter is ignored.
   *
   * @param time The time to subtract from {@code this}.
   * @param dest When {@code dest} is not {@code null}, the result is
   *          placed there and returned.
   * @return the result of the normalization of {@code this} minus the
   *         {@code RelativeTime} parameter {@code time} in {@code dest}
   *         when {@code dest} is not {@code null}, otherwise the result
   *         is returned in a newly allocated object.
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *           associated with {@code this} and the {@code Chronograph}
   *           associated with the {@code time} parameter are different,
   *           or when the {@code time} parameter is {@code null}.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   */
  public AbsoluteTime subtract(RelativeTime time, AbsoluteTime dest)
    throws StaticIllegalArgumentException, ArithmeticException
  {
    return dest;
  }


  /**
   * Creates a printable string of the time given by {@code this}.
   * <p>
   * The string shall be a decimal representation of the milliseconds and
   * nanosecond values; formatted as follows "(2251 ms, 750000 ns)"
   *
   * @return a String object converted from the
   *         time given by {@code this}.
   */
  @Override
  public java.lang.String toString()
  {
    return "string";
  }


  /**
   * Superceeded by and equivalent to {@link #absolute(Chronograph)}.
   *
   * @param clock
   *          The {@code clock} parameter is
   *          used only as the new clock association with the result,
   *          since no conversion is needed.
   * @return the copy of {@code this} in a
   *         newly allocated {@code AbsoluteTime} object,
   *         associated with the {@code clock} parameter.
   *
   * @deprecated since version 2.0
   */
  @Override
  @Deprecated
  public AbsoluteTime absolute(Clock clock)
  {
    return null;
  }


  /**
   * Superceeded by and equivalent to
   * {@link #absolute(Chronograph, AbsoluteTime)}.
   *
   * @param clock The {@code clock} parameter is used only as the new
   *          clock association with the result, since no conversion is
   *          needed.
   * @param dest When {@code dest} is not {@code null}, the result is
   *          placed in it and returned.
   * @return the copy of {@code this} in {@code dest} when {@code dest}
   *         is not {@code null}, otherwise the result is returned in a
   *         newly allocated object.  It is associated with the {@code
   *         clock} parameter.
   *
   * @deprecated since version 2.0
   */
  @Override
  @Deprecated
  public AbsoluteTime absolute(Clock clock, AbsoluteTime dest)
  {
    return dest;
  }


  /**
   * Superceeded by and equivalent to {@link #relative(Chronograph)}.
   *
   * @param clock The instance of {@link Clock} used to convert the time
   *          of {@code this} into relative time, and the new clock
   *          association for the result.
   * @return the {@code RelativeTime} conversion in a newly allocated
   *         object, associated with the {@code clock} parameter.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   *
   * @deprecated since version 2.0
   */
  @Override
  @Deprecated
  public RelativeTime relative(Clock clock) throws ArithmeticException
  {
    return null;
  }


  /**
   * Superceeded by and equivalent to
   * {@link #relative(Chronograph, RelativeTime)}.
   *
   * @param clock The instance of {@link Clock} used to convert the time
   *          of {@code this} into relative time, and the new clock
   *          association for the result.
   * @param dest When {@code dest} is not {@code null}, the result is
   *          placed in it and returned.
   * @return the {@code RelativeTime} conversion in {@code dest} when
   *         {@code dest} is not {@code null}, otherwise the result is
   *         returned in a newly allocated object.  It is associated
   *         with the {@code clock} parameter.
   * @throws ArithmeticException when the result does not fit in the
   *           normalized format.
   *
   * @deprecated since version 2.0
   */
  @Override
  @Deprecated
  public RelativeTime relative(Clock clock, RelativeTime dest)
    throws ArithmeticException
  {
    return dest;
  }
}
