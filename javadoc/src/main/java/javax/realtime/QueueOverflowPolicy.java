/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Defines the set of policies for handling overflow on event queues
 * used by {@link ReleaseParameters}.  An event queue holds a number of
 * event arrival times with any respective payload provided with the event.
 * A reference to the event itself is only held when it happens to be the
 * payload, e.g., for an {@code AsyncObjectEvent} associated with a
 * {@code Timer}.
 *
 * @since RTSJ 2.0
 */
public enum QueueOverflowPolicy
{
  /**
   * Represents the "DISABLE" policy which means, when an arrival occurs,
   * no queuing takes place, thus no overflow can happen.  This policy
   * is for instances of {@link ActiveEvent} with no payload and instances
   * of {@link RealtimeThread} with {@link PeriodicParameters}.  In contrast
   * to {@link #IGNORE}, all incoming events increment the pending fire or
   * release count, respectively.  For this reason, it may not be used with
   * an event handler that supports an event payload or any instance of
   * {@link Schedulable} with {@link SporadicParameters}.  This policy is
   * also the default for {@link PeriodicParameters}.  Instances of
   * {@code RealtimeThread} with {@code null} release parameters
   * have this policy implicitly, as they do not have an event queue either.
   */
  DISABLE(),

  /**
   * Represents the "EXCEPT" policy which means, when an arrival
   * occurs and its event time and payload should be queued but the queue
   * already holds a number of event times and payloads equal to the initial
   * queue length, the {@code fire()} method shall throw an
   * {@link ArrivalTimeQueueOverflowException}.  When fire is used within a
   * {@link Timer}, the exception is ignored and the fire does nothing, i.e.,
   * it acts the same as ``IGNORE''.
   */
  EXCEPT(),

  /**
   * Represents the "IGNORE" policy which means, when an arrival occurs
   * and its event time and payload should be queued, but the queue already
   * holds a number of event times and payloads equal to the initial queue
   * length, the arrival is ignored.
   */
  IGNORE(),

  /**
   * Represents the "REPLACE" policy which means, when an arrival occurs
   * and should be queued but the queue already holds a number of event times
   * and payloads equal to the initial queue length, the information for this
   * arrival replaces a previous arrival.  When the queue length is zero, the
   * behavior is the same as the "IGNORE" policy.
   */
  REPLACE(),

  /**
   * Represents the "SAVE" policy which means, when an arrival occurs
   * and should be queued but the queue is full, the queue is lengthened
   * and the arrival time and payload are saved. This policy does not update
   * the"initial queue length" as it alters the actual queue length.  Since
   * the {@code SAVE} policy grows the arrival time queue as necessary,
   * for the {@code SAVE} policy the initial queue length is only
   * an optimization.  It is also the default for {@link AperiodicParameters}.
   */
  SAVE();

  QueueOverflowPolicy() {}

  /**
   * Converts a string into a policy type.
   *
   * @param value is the string to convert.
   *
   * @return the corresponding policy type.
   */
  public static QueueOverflowPolicy value(String value)
  {
    switch (value)
      {
      case "DISABLE": return DISABLE;
      case "EXCEPT":  return EXCEPT;
      case "IGNORE":  return IGNORE;
      case "REPLACE": return REPLACE;
      case "SAVE":    return SAVE;
      default: throw (StaticIllegalArgumentException)
          StaticIllegalArgumentException.get().
          init("There is no " + value + " queue policy!");
      }
  }
}
