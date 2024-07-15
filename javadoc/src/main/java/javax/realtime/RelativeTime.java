/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An object that represents a time interval
 * milliseconds/10<sup>3</sup> + nanoseconds/10<sup>9</sup> seconds long.
 * It generally is used to represent a time relative to now.
 *
 * <p>
 * The time interval is kept in normalized form. The range goes from
 * <tt>[(-2</tt><sup><tt>63</tt></sup><tt>)</tt> milliseconds
 * <tt>+ (-10</tt><sup><tt>6</tt></sup><tt>+1)</tt> nanoseconds<tt>]</tt>
 * to
 * <tt>[(2</tt><sup><tt>63</tt></sup><tt>-1)</tt> milliseconds
 * <tt>+ (10</tt><sup><tt>6</tt></sup><tt>-1)</tt> nanoseconds<tt>]</tt>.
 *
 * <p>
 * A negative interval relative to now represents time in the past.
 * For {@code add} and {@code subtract}, negative values behave
 * as they do in arithmetic.
 *
 * @rtsj.warning.sync
 */
public class RelativeTime extends HighResolutionTime<RelativeTime>
{
  /**
   * Constructs a {@code RelativeTime} object representing an
   * interval based on the parameter {@code millis} plus the
   * parameter {@code nanos}.  The construction is subject to
   * {@code millis} and {@code nanos} parameter
   * normalization.  When there is an overflow in the millisecond
   * component when normalizing then an
   * {@code StaticIllegalArgumentException} will be thrown.
   *
   * <P>
   * The chronograph association is made with the {@code chronograph} parameter.
   * When {@code chronograph} is {@code null} the association is made with
   * the default realtime clock.
   *
   * @param millis The desired value for the millisecond component
   * of {@code this}.
   * The actual value is the result of parameter normalization.
   * @param nanos The desired value for the nanosecond component
   * of {@code this}.
   * The actual value is the result of parameter normalization.
   * @param chronograph The time reference of the newly
   * constructed object.  Defaults to the realtime clock when {@code null}.
   * @throws StaticIllegalArgumentException when there is an overflow in the
   * millisecond component when normalizing.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime(long millis, int nanos, Chronograph chronograph)
    throws StaticIllegalArgumentException
  {
    super(millis, nanos, chronograph);
  }

  /**
   * Equivalent to {@link #RelativeTime(long, int, Chronograph)} with argument
   * list {@code (millis, nanos, null)}.
   *
   * @param millis The desired value for the millisecond component of
   *        {@code this}.  The actual value is the result of
   *        parameter normalization.
   * @param nanos The desired value for the nanosecond component of
   *        {@code this}.  The actual value is the result of
   *        parameter normalization.
   * @throws StaticIllegalArgumentException when there is an overflow in the
   *         millisecond component when normalizing.
   */
  public RelativeTime(long millis, int nanos)
    throws StaticIllegalArgumentException
  {
    super(millis, nanos, null);
  }

  /**
   * Equivalent to {@link #RelativeTime(long, int, Chronograph)} with argument
   * list {@code (time.getMilliseconds(), time.getNanoseconds(),
   * time.getChronograph())}.
   *
   * @param time The {@code RelativeTime} object which is the
   * source for the copy.
   */
  public RelativeTime(RelativeTime time)
  {
    this(time.getMilliseconds(),
         time.getNanoseconds(),
         time.getChronograph());
  }

  /**
   * Equivalent to {@link #RelativeTime(long, int, Chronograph)} with argument
   * list {@code (time.getMilliseconds(), time.getNanoseconds(),
   * time.getChronograph())}.
   *
   * @param time The {@code RelativeTime} object which is the
   * source for the copy.
   */
  public RelativeTime(RelativeTime time, Chronograph chronograph)
  {
    this(time.getMilliseconds(),
         time.getNanoseconds(),
         chronograph);
  }

  /**
   * Equivalent to {@link #RelativeTime(long, int, Chronograph)} with argument
   * list {@code (0, 0, chronograph)}.
  *
   * @param chronograph The time reference for the newly
   * constructed object.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime(Chronograph chronograph)
  {
    this(0, 0, chronograph);
  }

  /**
   * Equivalent to {@link #RelativeTime(long, int, Chronograph)} with argument
   * list {@code (0, 0, null)}.
   */
  public RelativeTime()
  {
    this(0, 0, null);
  }

  /**
   * Constructs a {@code RelativeTime} object representing an
   * interval based on the parameter {@code millis} plus the
   * parameter {@code nanos}.  The construction is subject to
   * {@code millis} and {@code nanos} parameter
   * normalization.  When there is an overflow in the millisecond
   * component when normalizing then an
   * {@code StaticIllegalArgumentException} will be thrown.
   *
   * <P>
   * The clock association is made with the {@code clock} parameter.
   * When {@code clock} is {@code null} the association is made with
   * the default realtime clock.
   *
   * @param millis The desired value for the millisecond component
   * of {@code this}.
   * The actual value is the result of parameter normalization.
   * @param nanos The desired value for the nanosecond component
   * of {@code this}.
   * The actual value is the result of parameter normalization.
   * @param clock The clock providing the association for the newly
   * constructed object.
   * @throws StaticIllegalArgumentException when there is an overflow in the
   * millisecond component when normalizing.
   *
   * @since RTSJ 1.0.1
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public RelativeTime(long millis, int nanos, Clock clock)
    throws StaticIllegalArgumentException
  {
    super(millis, nanos, clock);
  }

  /**
   * Makes a new {@code RelativeTime} object from the given
   * {@code RelativeTime} object.
   * <P>
   * The clock association is made with the {@code clock} parameter.
   * When {@code clock} is {@code null} the association is made with
   * the default realtime clock.
   *
   * @param time The {@code RelativeTime} object which is the
   * source for the copy.
   * @param clock The clock providing the association for the newly
   * constructed object.
   * @throws StaticIllegalArgumentException when the {@code time}
   * parameter is {@code null}.
   *
   * @since RTSJ 1.0.1
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public RelativeTime(RelativeTime time, Clock clock)
    throws StaticIllegalArgumentException
  {
    super(time.getMilliseconds(),
          time.getNanoseconds(),
          clock);
  }

  /**
   * Equivalent to {@code new RelativeTime(0,0,clock)}.
   * <P>
   * The clock association is made with the {@code clock} parameter.
   * When {@code clock} is {@code null} the association is made with
   * the default realtime clock.
   *
   * @param clock The clock providing the association for the newly
   * constructed object.
   *
   * @since RTSJ 1.0.1
   *
   * @deprecated since version 2.0
   */
  @Deprecated
  public RelativeTime(Clock clock)
  {
    super(0, 0, clock);
  }

  /**
   * @since RTSJ 2.0
   * @see HighResolutionTime#absolute(Chronograph)
   */
  @Override
  public AbsoluteTime absolute(Chronograph chronograph)
  {
    return null;
  }

  /**
   * @since RTSJ 2.0
   * @see HighResolutionTime#absolute(Chronograph, AbsoluteTime)
   */
  @Override
  public AbsoluteTime absolute(Chronograph chronograph, AbsoluteTime dest)
  {
    return null;
  }

  /**
   * @since RTSJ 2.0
   * @see HighResolutionTime#relative(Chronograph)
   */
  @Override
  public RelativeTime relative(Chronograph chronograph)
  {
    return null;
  }

  /**
   * @since RTSJ 2.0
   * @see HighResolutionTime#relative(Chronograph, RelativeTime)
   */
  @Override
  public RelativeTime relative(Chronograph chronograph, RelativeTime dest)
  {
    return null;
  }

  /**
   * Creates a new object representing the result of adding
   * {@code millis} and {@code nanos} to the values from
   * {@code this} and normalizing the result.
   * The result will have the same chronograph association as {@code this}.
   * An {@code ArithmeticException} is when the result does not
   * fit in the normalized format.
   *
   * @param millis The number of milliseconds to be added
   * to {@code this}.
   *
   * @param nanos The number of nanoseconds to be added
   * to {@code this}.
   *
   * @return a new {@code RelativeTime} object whose time is
   * the normalization of {@code this} plus {@code millis}
   * and {@code nanos}.
   *
   * @throws ArithmeticException when the result does not fit
   * in the normalized format.
   */
  public RelativeTime add(long millis, int nanos)
   throws ArithmeticException
  {
    return null;
  }

  /**
   * Returns an object containing the value resulting from adding
   * {@code millis} and {@code nanos} to
   * the values from {@code this} and normalizing the result.
   * When {@code dest} is not {@code null}, the result is placed
   * there and returned. Otherwise, a new object is allocated for the result.
   * The result will have the same chronograph association as {@code this},
   * and the chronograph association with {@code dest} is ignored.
   *
   * @param millis The number of milliseconds to be added
   * to {@code this}.
   *
   * @param nanos The number of nanoseconds to be added
   * to {@code this}.
   *
   * @param dest When {@code dest} is not {@code null},
   * the result is placed there and returned.
   *
   * @return  the result of the normalization of
   * {@code this} plus {@code millis} and {@code nanos} in
   * {@code dest} when {@code dest} is not {@code null},
   * otherwise the result is returned in a newly allocated object.
   *
   * @throws ArithmeticException when the result does not fit
   * in the normalized format.
   *
   */
  public RelativeTime add(long millis, int nanos, RelativeTime dest)
    throws ArithmeticException
  {
    return dest;
  }

  /**
   * Creates a new instance of {@code RelativeTime }
   * representing the result of adding {@code time} to
   * the value of {@code this} and normalizing the result.
   *
   * <p>
   * The chronograph associated with
   * {@code this} and the {@code clock}
   * associated with the {@code time} parameter
   * are expected to be the same, and such association
   * is used for the result.
   *
   * @param time The time to add to {@code this}.
   *
   * @return a new {@code RelativeTime} object whose time is the
   *         normalization of {@code this} plus the parameter
   *         {@code time}.
   *
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *         associated with {@code this} and the
   *         {@code Chronograph} associated with the {@code time}
   *         parameter are different, or when the {@code time}
   *         parameter is {@code null}.
   *
   * @throws ArithmeticException when the result does not fit
   *         in the normalized format.
   */
  public RelativeTime add(RelativeTime time)
    throws StaticIllegalArgumentException, ArithmeticException
  {
    return null;
  }

  /**
   * Returns an object containing the value resulting from adding
   * {@code time} to the value of {@code this} and normalizing
   * the result.  When {@code dest} is not {@code null}, the
   * result is placed there and returned. Otherwise, a new object is
   * allocated for the result.
   *
   * <p> The {@code Chronograph} associated with {@code this} and
   * the {@code Chronograph} associated with the {@code time}
   * parameter are expected to be the same, and such association is used
   * for the result.
   *
   * <p> The {@code Chronograph} associated with the {@code dest}
   * parameter is ignored.
   *
   * @param time The time to add to {@code this}.
   *
   * @param dest When {@code dest} is not {@code null}, the
   *        result is placed there and returned.
   *
   * @return the result of the normalization of {@code this} plus
   *         the {@code RelativeTime} parameter {@code time}
   *         in {@code dest} when {@code dest} is not
   *         {@code null}, otherwise the result is returned in a
   *         newly allocated object.
   *
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *         associated with {@code this} and the
   *         {@code Chronograph} associated with the {@code time}
   *         parameter are different, or when the {@code time}
   *         parameter is {@code null}.
   *
   * @throws ArithmeticException when the result does not fit
   *         in the normalized format.
   */
  public RelativeTime add(RelativeTime time, RelativeTime dest)
    throws StaticIllegalArgumentException, ArithmeticException
  {
    return dest;
  }

  /**
   * Creates a new instance of {@code RelativeTime}
   * representing the result of subtracting {@code time} from
   * the value of {@code this}
   * and normalizing the result.
   *
   * <p>
   * The {@code Chronograph} associated with
   * {@code this} and the {@code Chronograph}
   * associated with the {@code time} parameter
   * are expected to be the same, and such association
   * is used for the result.
   *
   * @param time The time to subtract from {@code this}.
   * @return a new {@code RelativeTime} object whose time is the
   *         normalization of {@code this} minus the parameter
   *         {@code time}.
   *
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *         associated with {@code this} and the
   *         {@code Chronograph} associated with the {@code time}
   *         parameter are different, or when the {@code time}
   *         parameter is {@code null}.
   *
   * @throws ArithmeticException when the result does not fit
   *         in the normalized format.
   */
  public RelativeTime subtract(RelativeTime time)
    throws StaticIllegalArgumentException, ArithmeticException
  {
    return null;
  }

  /**
   * Returns an object containing the value
   * resulting from subtracting the value of {@code time} from
   * the value of {@code this}
   * and normalizing the result.
   * When {@code dest} is not {@code null}, the result is placed
   * there and returned. Otherwise, a new object is allocated for the result.
   *
   * <p>
   * The {@code Chronograph} associated with
   * {@code this} and the {@code Chronograph}
   * associated with the {@code time} parameter
   * are expected to be the same, and such association
   * is used for the result.
   *
   * <p>
   * The {@code Chronograph}
   * associated with the {@code dest} parameter
   * is ignored.
   *
   * @param time The time to subtract from {@code this}.
   * @param dest When {@code dest} is not {@code null}, the
   * result is placed there and returned. Otherwise, a new object is
   * allocated for the result.
   * @return the result of the normalization of {@code this} minus
   * the {@code RelativeTime} parameter {@code time} in
   * {@code dest} when {@code dest} is not {@code null},
   * otherwise the result is returned in a newly allocated object.
   *
   * @throws StaticIllegalArgumentException when the {@code Chronograph}
   *         associated with {@code this} and the
   *         {@code Chronograph} associated with the {@code time}
   *         parameter are different, or when the {@code time}
   *         parameter is {@code null}.
   *
   * @throws ArithmeticException when the result does not fit
   *         in the normalized format.
   */
  public RelativeTime subtract(RelativeTime time, RelativeTime dest)
    throws StaticIllegalArgumentException, ArithmeticException
  {
    return dest;
  }

  /**
   * Changes the length of this relative time by multiplying it by
   * {@code factor}.
   *
   * @param factor Value by which to increase the time interval.
   *
   * @return a new object with {@code value} of this scaled by
   *         {@code factor}.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime scale(int factor) { return null; }

  /**
   * Sets {@code time} to the value of {@code this} time multiplied
   * by {@code factor}.
   * @param factor Value by which to increase the time in {@code this}.
   * @param time Where to store the result.
   *
   * @return {@code time} with the value of {@code this} scaled by
   *         {@code factor}
   *
   * @since RTSJ 2.0
   */
  public RelativeTime scale(int factor, RelativeTime time) { return null; }

  /**
   * Divide the current time by an integral factor.
   *
   * @param factor by which to divide this time, which must be greater
   *               than zero.
   *
   * @param destination the destination relative time object.
   *
   * @return destination or, when null, a new relative time object.
   *
   * @throws StaticIllegalArgumentException when {@code factor} is
   *         zero or negative.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime apportion(int factor, RelativeTime destination)
  {
    return null;
  }

  /**
   * multiply the current time by an integral scaling factor.
   *
   * @param factor by which to apportion this time.
   *
   * @return a new instance of RelativeTime with the result of the scaling.
   *
   * @throws ArithmeticException when the scaling results in an overflow.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime apportion(int factor)
  {
    return apportion(factor, null);
  }

  /**
   * Multiply this relative time by a floating point factor.
   *
   * @param factor the factor by which to multiply.
   *
   * @param dest an object into which to place the result.
   *
   * @return {@code dest}, when not {@code null}, or a new object
   *                       holding the result.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime multiply(float factor, RelativeTime dest)
  {
    return null;
  }

  /**
   * Multiply the current time by -1.  It gives the same results as
   * {@link #scale(int, RelativeTime)}, where scale is given as -1; however
   * it is much faster.
   *
   * @param destination the destination relative time object.
   *
   * @return destination or, when null, a new relative time object.
   *
   * @throws ArithmeticException when the negation results in an overflow.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime negate(RelativeTime destination)
  {
    return destination;
  }

  /**
   * Multiply the current time by -1.  It gives the same results as
   * {@link #scale(int)}, where scale is given as -1; however
   * it is much faster.
   *
   * @return a new relative time object having the same magnitude but
   *         the opposite sign.
   *
   * @throws ArithmeticException when the negation results in an overflow.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime negate()
  {
    return negate(null);
  }

  /**
   * Compares {@code this} to relative time zero returning the
   * result of the comparison.  Equivalent to constantZero.compareTo(this)
   *
   *
   * @return negative when {@code this} is less than zero, 0, when it is equal
   *    to zero and a positive when {@code this} is greater than zero.
   *
   * @since RTSJ 2.0
   */
  public int compareToZero() { return 0; }

  /**
   *
   * Creates a printable string of the time given by {@code this}.
   *
   * <p> The string shall be a decimal representation of the
   * milliseconds and nanosecond values; formatted as follows "(2251 ms,
   * 750000 ns)"
   *
   * @return a String object converted from the
   * time given by {@code this}.
   */
  @Override
  public java.lang.String toString() { return ""; }

  /**
   * Converts the time of {@code this} to an absolute time, using the
   * given instance of {@link Clock} to determine the current time.
   * The calculation is the current time indicated by the given instance of
   * {@link Clock} plus the interval given by {@code this}.
   * When {@code clock} is {@code null} the
   * realtime clock is assumed.
   * A destination object is allocated for the result.
   *
   * The clock association of the result is with
   * the {@code clock} passed as a parameter.
   *
   * @param clock The instance of {@link Clock} used to convert the
   *              time of {@code this} into absolute time,
   *              and the new clock association for the result.
   *
   * @return The {@code AbsoluteTime} conversion in a newly allocated
   *         object, associated with the {@code clock} parameter.
   *
   * @throws ArithmeticException when the result does not fit in the
   *                             normalized format.
   *
   * @deprecated since version 2.0
   */
  @Override
  @Deprecated
  public AbsoluteTime absolute(Clock clock)
    throws ArithmeticException
  {
    return null;
  }

  /**
   * Converts the time of {@code this} to an absolute time, using the given
   * instance of {@link Clock} to determine the current time.  The calculation
   * is the current time indicated by the given instance of {@code Clock}
   * plus the interval given by {@code this}.  When clock is {@code null} the
   * default realtime clock is assumed.  When {@code dest} is {@code null}, a
   * destination object is allocated for the result.  The clock association of
   * the result is with the {@code clock} passed as a parameter.
   *
   * @param clock The instance of {@link Clock} used to convert the
   *              time of {@code this} into absolute time,
   *              and the new clock association for the result.
   * @param dest When {@code dest} is not {@code null},
   *             the result is placed there and returned.
   * @return the {@code AbsoluteTime} conversion in
   *         {@code dest} when {@code dest} is not {@code null},
   *         otherwise the result is returned in a newly allocated object.
   *         The result is associated with the {@code clock} parameter.
   *
   * @throws ArithmeticException when the result does not fit in the
   *                             normalized format.
   *
   * @deprecated since version 2.0
   */
  @Override
  @Deprecated
  public AbsoluteTime absolute(Clock clock, AbsoluteTime dest)
    throws ArithmeticException
  {
    return dest;
  }


  /* FOLLOWING THREE TO BE DELETED */
  /* Jun 24 2003: FOLLOWING THREE TO BE DEPRECATED INSTEAD */
  /**
   * Returns a copy of {@code this}.  A new object is allocated for
   * the result.  This method is the implementation of the
   * {@code abstract} method of the {@code HighResolutionTime}
   * base class. No conversion into {@code RelativeTime} is needed
   * in this case.
   *
   * The clock association of the result is with the {@code clock}
   * passed as a parameter.  When {@code clock} is {@code null}
   * the association is made with the realtime clock.
   *
   * @param clock The {@code clock} parameter is used only as the
   *        new clock association with the result, since no conversion
   *        is needed.
   *
   * @return the copy of {@code this} in a newly allocated
   *         {@code RelativeTime} object, associated with the
   *         {@code clock} parameter.
   *
   * @deprecated since version 2.0
   */
  @Override
  @Deprecated
  public RelativeTime relative(Clock clock) { return null; }

  /**
   * Returns a copy of {@code this}.  When {@code dest} is not
   * {@code null}, the result is placed there and
   * returned. Otherwise, a new object is allocated for the result.
   * This method is the implementation of the {@code abstract}
   * method of the {@code HighResolutionTime} base class. No
   * conversion into {@code RelativeTime} is needed in this case.
   *
   * The clock association of the result is with the {@code clock}
   * passed as a parameter.  When {@code clock} is {@code null}
   * the association is made with the realtime clock.
   *
   * @param clock The {@code clock} parameter is used only as the
   *        new clock association with the result, since no conversion
   *        is needed.
   * @param dest When {@code dest} is not {@code null}, the
   *        result is placed there and returned.
   * @return the copy of {@code this} in {@code dest} when
   *         {@code dest} is not {@code null}, otherwise the
   *         result is returned in a newly allocated object.  It is
   *         associated with the {@code clock} parameter.
   *
   * @deprecated since version 2.0
   */
  @Override
  @Deprecated
  public RelativeTime relative(Clock clock, RelativeTime dest)
  {
    return dest;
  }

  /*
   * Adds the interval of {@code this} to the given instance of
   * {@link AbsoluteTime}.
   *
   * @param timeAndDestination A reference to the given instance of
   * {@link AbsoluteTime} and the result.
   *
   * @deprecated as of RTSJ 1.0.1
   */
  @Deprecated
  public void addInterarrivalTo(AbsoluteTime timeAndDestination) {}


  /*
   * Gets the interval defined by {@code this}. For an instance of
   * {@link RationalTime} it is the interval divided by the frequency.
   *
   * @return a reference to a new instance of {@link RelativeTime} with
   *         the same interval as {@code this}.
   *
   * @deprecated as of RTSJ 1.0.1
   */
  @Deprecated
  public RelativeTime getInterarrivalTime() { return null; }

  /*
   * Gets the interval defined by {@code this}. For an instance of
   * {@link RationalTime} it is the interval divided by the frequency.
   *
   * @param destination A reference to the new object holding the result.
   *
   * @return a reference to an object holding the result.
   *
   * @deprecated as of RTSJ 1.0.1
   */
  @Deprecated
  public RelativeTime getInterarrivalTime(RelativeTime destination)
  {
    return destination;
  }
}
