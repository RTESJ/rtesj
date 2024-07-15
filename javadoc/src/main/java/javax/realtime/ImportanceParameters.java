/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Importance is an additional scheduling metric that may be used by
 * some priority-based scheduling algorithms during overload conditions
 * to differentiate execution order among threads of the same priority.
 *
 * <p>In some realtime systems an external physical process determines
 * the period of many threads. When rate-monotonic priority assignment
 * is used to assign priorities, many of the threads in the system may
 * have the same priority because their periods are the same.  However,
 * it is conceivable that some threads may be more important than others
 * and in an overload situation importance can help the scheduler decide
 * which threads to execute first.
 *
 * The base scheduling algorithm represented by {@link PriorityScheduler}
 * must not consider importance.
 *
 * @deprecated since RTSJ 2.0
 */
@Deprecated
public class ImportanceParameters extends PriorityParameters
{

  /**
   *
   */
  private static final long serialVersionUID = 1799492473829253403L;

  /**
   * Creates an instance of {@code ImportanceParameters}.
   *
   * @param priority The priority value assigned to schedulables
   *        that use this parameter instance.  This value is used in
   *        place of the value passed to {@code Thread.setPriority}.
   *
   * @param importance The importance value assigned to schedulable
   *        objects that use this parameter instance.
   */
  public ImportanceParameters(int priority, int importance) { super(priority); }

  /**
   * Gets the importance value.
   *
   * @return the value of importance for the associated instances of
   * {@link Schedulable}.
   */
  public int getImportance() { return 0; }


  /**
   * Sets the importance value.  When this parameter object is associated
   * with any schedulable, either by being passed through the
   * schedulable's constructor or set with a method such as
   * {@link RealtimeThread#setSchedulingParameters(SchedulingParameters)},
   * the importance of those schedulables is altered at a moment
   * controlled by the schedulers for the respective schedulables.
   *
   * @param importance The value to which importance is set.
   *
   * @throws StaticIllegalArgumentException  when the given importance
   *         value is incompatible with the scheduler for any of the
   *         schedulables which are presently using this
   *         parameter object.
   */
  public void setImportance(int importance) {}

  /**
   * Prints the value of the priority and importance values
   * of the associated instance of {@link Schedulable}
   */
  @Override
  public java.lang.String toString()
  {
    return new String(super.toString() + ":" + Integer.toString(getImportance()));
  }
}
