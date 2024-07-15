/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * The interface for all devices that support the measurement of time
 * with great accuracy.
 *
 * @since RTSJ 2.0
 */
public interface Chronograph
{
  /**
   * Determines the difference between the epoch of this clock from the Epoch.
   * For the UTC, the result is always a {@code RelativeTime} value equal to
   * zero.  For other clocks, it is a value representing the difference between
   * zero on that clock and zero on the UTC measured on the UTC, where
   * a positive epoc is later than the EPOC.
   *
   * @return a newly allocated {@link RelativeTime} object in the current
   *    execution context with the UTC as its chronograph and containing
   *    the time when this chronograph was zero.
   *
   * @throws StaticUnsupportedOperationException when the chronograph
   *         does not have the concept of date.
   *
   * @throws UninitializedStateException when UTC time is not yet available.
   */
  public RelativeTime getEpochOffset()
    throws StaticUnsupportedOperationException, UninitializedStateException;

  /**
   * Determines the difference between the epoch of this clock from the Epoch.
   * For the UTC, the result is always a {@code RelativeTime} value equal to
   * zero.  For other clocks, it is a value representing the difference between
   * zero on that clock and zero on the UTC measured on the UTC.
   *
   * @param dest An instance of {@link RelativeTime} object
   *          which will be updated in place.
   *
   * @return the instance of {@link RelativeTime} passed as parameter,
   *         or a new object when {@code dest} is {@code null}.  The returned
   *         object represents the time differnce between its associated
   *         chronograph and the Epoch.
   *
   * @throws StaticUnsupportedOperationException when the chronograph
   *         does not have the concept of date.
   *
   * @throws UninitializedStateException when UTC time is not yet available.
   */
  public RelativeTime getEpochOffset(RelativeTime dest)
    throws StaticUnsupportedOperationException, UninitializedStateException;

  /**
   * Determines the current time.  This method returns an absolute time value
   * representing the chronograph's notion of absolute time.  For chronographs
   * that do not measure calendar time, this absolute time may not represent
   * a wall clock time.
   *
   * @return a newly allocated instance of {@link AbsoluteTime} in the
   *         current allocation context, representing the current time.
   *         The returned object has the chronograph from {@code this}.
   */
  public AbsoluteTime getTime();

  /**
   * Obtains the current time. The time represented by
   * the given {@link AbsoluteTime} is changed at some time between the
   * invocation of the method and the return of the method.   This
   * method will return an absolute time value that represents this
   * chronographs's notion of the absolute time.  For chronographs
   * that do not measure calendar time, this absolute time may not
   * represent a wall clock time.
   *
   * @param dest The instance of {@link AbsoluteTime} object
   *          which will be updated in place.
   * @return the instance of {@link AbsoluteTime} passed as parameter,
   *         or a new object when {@code dest} is {@code null}.  The returned
   *         object represents the current time and is associated with
   *         {@code this} chronograph.
   */
  public AbsoluteTime getTime(AbsoluteTime dest);

  /**
   * Determine whether or not this time keeper is asynchronously synchronized
   * with an external time source.  Synchronization requires the ability to
   * adjust the time to compensate for drift.  For example, a UTC is continually
   * synchronized with an external time source, but realtime clocks are not.
   *
   * @return {@code true} for chronographs that are synchronized and
   *        {@code false} otherwise.
   */
  public default boolean isUpdated() { return false; }

  /**
   * Determine the last time this chronograph was synchronized.
   * It is the same as calling {@link #lastSynchronized(AbsoluteTime)} with
   * {@code null} as an argument.
   *
   * @return a newly allocated time value holding last synchronized time.
   *
   * @throws StaticUnsupportedOperationException when the chronograph
   *         will never be updated, i.e., is never synchronized with an
   *         external time source.
   */
  public default AbsoluteTime lastSynchronized()
    throws StaticUnsupportedOperationException
  {
    return lastSynchronized(null);
  }


  /**
   * Determine the last time this chronograph was synchronized with an external
   * time source.
   *
   * @param result a time object to hold the result.
   *
   * @return when {@code result} is {@code null}, a newly allocated time value
   *         holding the value corresponding to the last synchronized time;
   *         otherwise {@code result} updated with that current value.
   *
   * @throws StaticUnsupportedOperationException when the chronograph
   *         will never be updated, i.e., is never synchronized with an
   *         external time source.
   */
  public default AbsoluteTime lastSynchronized(AbsoluteTime result)
    throws StaticUnsupportedOperationException
  {
    throw StaticUnsupportedOperationException.get();
  }

  /**
   * Obtains the precision with which time can be read, i.e.,
   * the nominal interval between ticks.
   * It is the same as calling {@link #getQueryPrecision(RelativeTime)} with
   * {@code null} as an argument.
   *
   * @return a newly allocated time value holding the read precision.
   */
  public default RelativeTime getQueryPrecision()
  {
    return getQueryPrecision(null);
  }

  /**
   * Obtains the precision with which time can be read, i.e., the nominal
   * interval between ticks.
   *
   * @param dest The time object in which to return the results.
   *
   * @return the read precision in {@code dest}, when {@code dest} is not
   *         {@code null}, or in a newly created object otherwise.
   */
  public RelativeTime getQueryPrecision(RelativeTime dest);

}
