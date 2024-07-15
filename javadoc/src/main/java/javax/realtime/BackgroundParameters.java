/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Parameters for realtime threads that are only released once.  A thread
 * using this release parameters may not use
 * {@link RealtimeThread#waitForNextRelease()} or have its
 * {@link RealtimeThread#release()} methods called.  Calling these methods
 * results in an {@code IllegalThreadStateException}.  Event handlers may not
 * use this type of {@code ReleaseParameters}.
 *
 * @since RTSJ 2.0
 */
public class BackgroundParameters
  extends ReleaseParameters<BackgroundParameters>
{
  private static final long serialVersionUID = 1640645486875317115L;

  /**
   * A constructor for both cost and deadline monitoring.
   *
   * @param cost The maximum cost for executing the run method
   *
   * @param deadline The deadline for the completion of the run method
   *
   * @param overrunHandler The handler to call on cost overrun.
   *
   * @param missHandler The handler to call on deadline miss.
   *
   * @throws StaticIllegalArgumentException when the time value of cost is less
   *         than zero, or the time value of deadline is less than
   *         or equal to zero, or the chronograph associated with the
   *         {@code cost} or {@code deadline} parameters is not an instance
   *         of {@link Clock}.
   *
   * @throws IllegalAssignmentError when cost, deadline,
   *         overrunHandler, or missHandler cannot be stored in this.
   *
   * @rtsj.warning.sync
   */
  public BackgroundParameters(RelativeTime cost,
                              RelativeTime deadline,
                              AsyncEventHandler overrunHandler,
                              AsyncEventHandler missHandler)
  {
    super(cost, deadline, overrunHandler, missHandler);
  }

  /**
   * A constructor for deadline monitoring.
   *
   * Equivalent to BackgroundParameters(null, deadline, null, missHandler)
   */
  public BackgroundParameters(RelativeTime deadline,
                              AsyncEventHandler missHandler)
  {
    super(null, deadline, null, missHandler);
  }

  /**
   * A constructor for not having any restrictions on, or monitoring
   * of, scheduling.
   *
   * Equivalent to BackgroundParameters(null, null, null, null, false)
   */
  public BackgroundParameters()
  {
    this(null, null, null, null);
  }
}
