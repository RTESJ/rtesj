/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An object that represents a time interval milliseconds/10<sup>3</sup>
 * + nanoseconds/10<sup>9</sup> seconds long that is divided into
 * subintervals by some frequency. This is generally used in periodic
 * events, threads, and feasibility analysis to specify periods where
 * there is a basic period that must be adhered to strictly (the
 * interval), but within that interval the periodic events are supposed
 * to happen frequency times, as uniformly spaced as possible, but clock
 * and scheduling jitter is moderately acceptable.
 *
 * @rtsj.warning.sync
 *
 * @deprecated as of RTSJ 1.0.1
 */
@Deprecated
public class RationalTime extends RelativeTime
{
  int freq;

  /**
   * Constructs an instance of {@code RationalTime}.
   * All arguments must be greater than or equal to zero.
   *
   * @param  frequency The frequency value.
   *
   * @param  millis The milliseconds value.
   *
   * @param  nanos The nanoseconds value.
   *
   * @throws IllegalArgumentException when any of the argument values are
   *         less than zero, or when {@code frequency} is equal to zero.
   */
  public RationalTime(int frequency,
                      long millis,
                      int nanos)
  {
  }

  /**
   * Constructs an instance of {@code RationalTime} from the given
   * {@link RelativeTime}.
   *
   * @param frequency The frequency value.
   *
   * @param interval The given instance of {@link RelativeTime}.
   *
   * @throws IllegalArgumentException when either of the argument values
   *         are less than zero, or when {@code frequency} is equal to zero.
   */
  public RationalTime(int frequency,
                      RelativeTime interval)
  {
  }

  /**
   * Constructs an instance of {@code RationalTime}.  Equivalent to
   * new {@code RationalTime(1000, 0, frequency)}&mdash;essentially
   * a cycles-per-second value.
   *
   * @throws IllegalArgumentException when {@code frequency} is
   * less than or equal to zero.
   */
  public RationalTime(int frequency)
  {
  }

  /**
   * Converts time of {@code this} to an absolute time.
   *
   * @param clock The reference clock. When {@code null},
   * {@code Clock.getRealTimeClock()} is used.
   *
   * @param destination A reference to the destination instance.
   */
  @Override
  public AbsoluteTime absolute(Clock clock,
                               AbsoluteTime destination)
  {
    return destination;
  }

  /**
   * Adds the time of {@code this} to an {@link AbsoluteTime}
   *
   * It is almost the same {@code dest.add(this, dest)} except
   * that it accounts for (i.e. divides by) the frequency.
   *
   * @param destination A reference to the destination instance.
   */
  public void addInterarrivalTo(AbsoluteTime destination)
  {
  }

  /**
   * Gets the value of {@code frequency}.
   *
   * @return The value of {@code frequency} as an integer.
   */
  public int getFrequency()
  {
    return 0;
  }

  /**
   * Gets the interarrival time. This time is
   * <code>(milliseconds/10<sup>3</sup> +
   * nanoseconds/10<sup>9</sup>)/frequency</code> rounded down to the
   * nearest expressible value of the fields and their types of
   * {@link RelativeTime}.
   */
  @Override
  public RelativeTime getInterarrivalTime()
  {
    return null;
  }

  /**
   * Gets the interarrival time. This time is {@code
   * (milliseconds / 10<sup>3</sup> + nanoseconds / 10<sup>9</sup>) / frequency}
   * rounded down to the nearest expressible value of the fields and
   * their types of {@link RelativeTime}.
   *
   *  @param dest Result is stored in dest and returned, when
   *         {@code null}, a new object is returned.
   */
  @Override
  public RelativeTime getInterarrivalTime(RelativeTime dest)
  {
    return dest;
  }

  /**
   * Sets the indicated fields to the given values.
   *
   * @param millis The new value for the millisecond field.
   *
   * @param nanos The new value for the nanosecond field.
   *
   * @return {@code this}
   *
   * @since RTSJ 2.0 returns itself
   */
  @Override
  @ReturnsThis
  public RationalTime set(long millis, int nanos) { return this; }

  /**
   * Sets the value of the {@code frequency} field.
   *
   * @param frequency The new value for the {@code frequency}.
   *
   * @throws IllegalArgumentException when {@code frequency} is
   *         less than or equal to zero.
   */
  public void setFrequency(int frequency) {}

  /**
   *
   * Creates a printable string of the time given by {@code this}.
   *
   * <p> The string shall be a decimal representation of the frequency,
   * milliseconds and nanosecond values; formatted as follows "(100,
   * 2251 ms, 750000 ns)"
   *
   * @return a string object converted from the time given by
   *         {@code this}.
   */
  @Override
  public java.lang.String toString()
  {
    return "";
  }

}
