/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Defines the set of policies for handling interarrival time violations
 * in {@link SporadicParameters}.  Each policy governs every instance of
 * {@link Schedulable} which has {@link SporadicParameters} with that
 * minimum interarrival time policy.
 *
 * @since RTSJ 2.0
 */
public enum MinimumInterarrivalPolicy
{
  /**
   * Represents the "EXCEPT" policy for minimum interarrival time.
   * Under this policy, when an arrival time of a release occurs at a time
   * less than the last release time plus its minimum interarrival time,
   * the {@code fire()} method shall throw a preallocated instance of
   * {@link MITViolationException}.
   */
  EXCEPT(),

  /**
   * Represents the "IGNORE" policy for minimum interarrival time.
   * Under this policy, when an arrival time of a release occurs at a time
   * less than the last release time plus its minimum interarrival time,
   * the new arrival time is ignored.
   */
  IGNORE(),

  /**
   * Represents the "REPLACE" policy for minimum interarrival time.
   * Under this policy, when an arrival time of a release occurs at a time
   * less than the last release time plus its minimum interarrival time,
   * the information for this arrival replaces a previous arrival.
   * For cases when the previous event has already been released or the event
   * queue has a length of zero, the arrival is ignored as with the
   * {@link #IGNORE} policy.
   */
  REPLACE(),

  /**
   * Represents the "SAVE" policy for minimum interarrival time.
   * Under this policy, when an arrival time of a release occurs at a time
   * less than the last release time plus its minimum interarrival time,
   * the new release is queued until the last release time plus its minimum
   * interarrival time is reached, but its deadline is not changed.
   */
  SAVE();

  MinimumInterarrivalPolicy() {}

  /**
   * Converts a string into a policy type.
   *
   * @param value is the string to convert.
   *
   * @return the corresponding policy type.
   */
  public static MinimumInterarrivalPolicy value(String value)
  {
    switch (value)
    {
      case "EXCEPT":  return EXCEPT;
      case "IGNORE":  return IGNORE;
      case "REPLACE": return REPLACE;
      case "SAVE":    return SAVE;
      default: throw StaticIllegalArgumentException.get().
                     init("There is no " + value + " MIT policy!");
    }
  }
}
