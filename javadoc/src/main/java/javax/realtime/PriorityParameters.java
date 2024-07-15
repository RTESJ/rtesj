/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Instances of this class should be assigned to schedulables that are managed
 * by schedulers which use a single integer to determine execution order. The
 * base scheduler required by this specification and represented by the class
 * {@link PriorityScheduler} is such a scheduler.
 */
public class PriorityParameters extends SchedulingParameters
{
  /**
   *
   */
  private static final long serialVersionUID = 8433894048940996040L;

  private int priority_;

  /**
   * Creates an instance of {@link PriorityParameters} with the given features.
   *
   * @param priority The priority assigned to schedulables that use this
   *        parameter instance.
   *
   * @param affinity The affinity assigned to schedulables that use this
   *        parameter instance.
   *
   * @since RTSJ 2.0
   */
  public PriorityParameters(int priority, Affinity affinity)
  {
    super(affinity);
  }

  /**
   * Creates an instance of {@link PriorityParameters} with the default
   * affinity.
   *
   * @param priority
   *          The priority assigned to schedulables that
   *          use this parameter instance.
   */
  public PriorityParameters(int priority) {}


  /**
   * Gets the priority value.
   *
   * @return the priority.
   */
  public int getPriority() { return 0; }

  /**
   * Sets the priority value. When this parameter object is associated with
   * any schedulable (by being passed through the
   * schedulable's constructor or set
   * with a method such as
   * {@link RealtimeThread#setSchedulingParameters(SchedulingParameters)})
   * the base priority of those schedulables is altered as
   * specified by each schedulable's scheduler.
   *
   * @param priority
   *          The value to which priority is set.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException
   *           when the given priority value is incompatible with the
   *           scheduler for any of the schedulables which are presently
   *           using this parameter object.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public PriorityParameters setPriority(int priority) { return this; }

  @Override
  public boolean isCompatible(Scheduler scheduler)
  {
    return scheduler instanceof PriorityScheduler;
  }


  @Override
  public boolean subsumes(SchedulingParameters other)
  {
    return (other instanceof PriorityParameters) &&
           (priority_ >= ((PriorityParameters)other).priority_);
  }

  /**
   * Converts the priority value to a string.
   *
   * @return a string representing the value of priority.
   */
  @Override
  public String toString() { return ""; }
}
