/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * When an arrival time occurs and should be queued, but the queue already
 * holds a number of times equal to the initial queue length, an
 * instance of this class is thrown.
 *
 * @since RTSJ 1.0.1 this is unchecked
 *
 * @since RTSJ 2.0 extends {@code EventQueueOverflowException}
 */
public class ArrivalTimeQueueOverflowException
  extends EventQueueOverflowException
{
  private static final long serialVersionUID = 212034151964082183L;

  private static final ArrivalTimeQueueOverflowException _singleton_ =
    new ArrivalTimeQueueOverflowException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this {@code Throwable}.
   *
   * @since RTSJ 2.0
   */
  public static ArrivalTimeQueueOverflowException get()
  {
    return _singleton_;
  }

  /**
   * The default constructor for {@code ArrivalTimeQueueOverflowException},
   * but user code should use {@link #get()} instead.
   */
  public ArrivalTimeQueueOverflowException() {}

  /**
   * A descriptive constructor for {@code ArrivalTimeQueueOverflowException}.
   *
   * @param description A description of the exception.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public ArrivalTimeQueueOverflowException(String description) {}

  @Override
  public ArrivalTimeQueueOverflowException getSingleton() { return null; }
}
