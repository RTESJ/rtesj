/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.enforce;

import javax.realtime.RealtimeThreadGroup;

/**
 * A constraint to limit the amount of immortal memory available to a task.
 *
 * @since RTSJ 2.0
 */
public class ImmortalConstraint
  extends ResourceConstraint<ImmortalConstraint>
{
  /**
   * Get the root instance for this constraint type.
   *
   * @return the root constraint.
   */
  public static ImmortalConstraint getRootConstraint() { return null; }

  /**
   * Determine the processing constraint for the current execution context.
   *
   * @return the constraint for this context.
   */
  public static ImmortalConstraint currentConstraint() { return null; }

  /**
   * Determine the processing constraint for the give execution context.
   *
   * @param thread The given execution context.
   * @return the constraint for this context.
   */
  public static ImmortalConstraint currentConstraint(Thread thread)
  {
    return null;
  }

  /**
   * Determine the immortal constraint for the give execution context.
   *
   * @param group The given execution context.
   * @return the constraint for this context.
   */
  public static ImmortalConstraint currentConstraint(ThreadGroup group)
  {
    return null;
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
   * Create a new contraint for the given RealtimeThreadGroup.
   * @param group The {@code RealtimeThreadGroup} to govern.
   * @param limit the amount of memory to take from its parent to manage.
   */
  public ImmortalConstraint(RealtimeThreadGroup group, long limit)
  {
    super(group);
  }

  /**
   * Determine the total amount of a memory under the control of this
   * resource constraint instance.
   *
   * @return the total amount under control of this instance in bytes.
   */
  public long getBudget() { return 0; }

  /**
   * Determine how much of the resource have been used of the amount available.
   *
   * @return the amount used in number of bytes.
   */
  public long used() { return 0; }

  /**
   * Determine how much of the resource are available for use, i.e., the
   * amount of the limit minus amount lent.
   *
   * @return the amount used in number of bytes.
   */
  public long available() { return 0; }
}
