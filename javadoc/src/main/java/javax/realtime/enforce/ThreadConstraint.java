/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.enforce;

import javax.realtime.RealtimeThreadGroup;
import javax.realtime.ReturnsThis;

/**
 * A class for handling contraining the number of threads that can be
 * created in a realtime thread group.  As with {@code ThreadGroup} and
 * {@code RealtimeThreadGroup}, instances of {@code ProcessingConstraint}
 * can be nested. The number of the threads, including threads in
 * subgroups not subject to another {@code ProcessingConstraint}
 * instance, can be both tracked and bounded.
 *
 * @since RTSJ 2.0
 */
public class ThreadConstraint extends ResourceConstraint<ThreadConstraint>
{
  /**
   * Get the root instance for this constraint type.
   *
   * @return the root constraint.
   */
  public static ThreadConstraint getRootConstraint() { return null; }

  /**
   * Determine the processing constraint for the current execution context.
   *
   * @return the constraint for this context.
   */
  public static ThreadConstraint currentConstraint() { return null; }

  /**
   * Determine the processing constraint for the give execution context.
   *
   * @param thread The given execution context.
   * @return the constraint for this context.
   */
  public static ThreadConstraint currentConstraint(Thread thread)
  {
    return null;
  }

  /**
   * Determine the thread constraint for the give execution context.
   *
   * @param group The given execution context.
   * @return the constraint for this context.
   */
  public static ThreadConstraint currentConstraint(ThreadGroup group)
  {
    return null;
  }

  /**
   * Creates a {@code ThreadConstraint} to govern a group of threads.
   *
   * @param group The {@code RealtimeThreadGroup} to govern.
   *
   * @param max The maximum number of threads allowed.
   */
  public ThreadConstraint(RealtimeThreadGroup group, int max)
  {
    super(group);
  }

  /**
   * Determine whether or not enforcing is in effect.
   *
   * @return {@code true} when yes and {@code false} when not.
   */
  @Override
  public boolean isEnforcing() { return false; }

  /**
   * Start applying this constraint to is tasks.
   */
  @Override
  public void start() {}

  /**
   * Stop applying this constraint to is tasks.
   */
  @Override
  public void stop() {}

  /**
   * Get the number of threads that may be created in this group.
   * The limit takes effect on start.
   *
   * @return The limit of threads that can be created.
   */
  public int getBudget()
  {
    return 0;
  }

  /**
   * Gets the cost used in the current period so far.
   *
   * @return an new object containing the cost used in the current period.
   */
  public int used()
  {
    return 0;
  }

  /**
   * Gets the number of threads that still may be created.
   *
   * @return the cost lent in an new object.
   */
  public int available()
  {
    return 0;
  }
}
