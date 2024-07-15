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
 * @since RTSJ 2.0 Generalizes {@link ArrivalTimeQueueOverflowException}
 */
public class EventQueueOverflowException
  extends StaticRuntimeException
  implements StaticThrowable<EventQueueOverflowException>
{
  private static final long serialVersionUID = 212034151964082183L;

  private static final EventQueueOverflowException _singleton_ =
    new EventQueueOverflowException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static EventQueueOverflowException get()
  {
    return _singleton_;
  }

  /**
   * The default constructor for {@code QueueOverflowException},
   * but user code should use {@link #get()} instead.
   */
  EventQueueOverflowException() {}

  @Override
  public EventQueueOverflowException getSingleton() { return _singleton_; }
}
