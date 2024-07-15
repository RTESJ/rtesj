/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Singleton class specifying use of the priority inheritance protocol.
 * When a thread or schedulable
 * {@code t1} attempts to enter code that is synchronized on an object
 * {@code obj} governed by this protocol, and
 * {@code obj} is currently locked by a
 * lower-priority thread or schedulable {@code t2}, then
 * <ol>
 * <li>
 * When {@code t1}'s active priority does not exceed the maximum
 * priority allowed by {@code t2}'s scheduler,
 * then {@code t1} becomes a priority source for <code>t2;</code>
 * {@code t1} ceases to serve as a priority source for
 * {@code t2} when either {@code t2}
 * releases the lock on {@code obj}, or
 * {@code t1} ceases attempting to synchronize on {@code obj}
 * (e.g., when {@code t1} incurs an ATC).
 * </li>
 * <li>
 * Otherwise (i.e., {@code t1}'s active priority exceeds
 * the maximum priority allowed by {@code t2}'s scheduler), an
 * {@code IllegalTaskStateException} is thrown in {@code t1}.
 * </li>
 * </ol>
 * <p>
 * Note on the second rule, throwing the exception in
 * {@code t1}, rather than in {@code t2}, ensures that the
 * exception is synchronous.
 *
 * <p>
 * See also {@link MonitorControl} and {@link PriorityCeilingEmulation}
 */
public class PriorityInheritance extends MonitorControl
{
  private PriorityInheritance()
  {
  }


  /**
   * Obtains a reference to the singleton {@code PriorityInheritance}.
   * <p>
   * This is the default {@code MonitorControl}
   * policy in effect at system startup.
   * <p>
   * The {@code PriorityInheritance} instance shall be allocated in
   * {@code ImmortalMemory}.
   */
  public static PriorityInheritance instance()
  {
    return new PriorityInheritance();
  }
}
