/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A version of {@link PriorityScheduler} where once a thread is scheduled
 * at a given priority, it runs until it is blocked or is preempted by a
 * higher priority thread.  When preempted, it remains the next thread ready
 * for its priority.  This is the default scheduler for realtime tasks.
 * It represents the required (by the RTSJ) priority-based scheduler.
 * The default instance is the base scheduler which does fixed priority,
 * preemptive scheduling.
 *
 * <p> This scheduler, like all schedulers, governs the default values
 * for scheduling-related parameters in its client schedulables.
 * The defaults are as follows:
 *
 * <table width="95%" border="1">
 *   <caption>FirstInFirstOut Default PriorityParameter Values</caption>
 *   <tr>
 *     <th align="center"><div><strong>Attribute</strong></div></th>
 *     <th align="center"><div><strong>Default Value</strong></div></th>
 *   </tr>
 *   <tr>
 *     <td>Priority</td>
 *     <td>norm priority</td>
 *   </tr>
 * </table>
 *
 * The system contains one instance of the
 * {@code FirstInFirstOutScheduler} which is the system's base scheduler
 * and is returned by {@code FirstInFirstOutScheduler.instance()}.
 * The instance returned by the {@link #instance()} method is the
 * <em>base scheduler</em> and is returned by
 * {@link Scheduler#getDefaultScheduler()} unless the default scheduler
 * is reset with {@link Scheduler#setDefaultScheduler(Scheduler)}.
 *
 * @since RTSJ 2.0
 */
public class FirstInFirstOutScheduler extends PriorityScheduler
{
  /**
   * Obtains a reference to the distinguished instance of
   * {@code PriorityScheduler} which is the system's base
   * scheduler.
   *
   * @return a reference to the distinguished instance
   *         {@code PriorityScheduler}.
   */
  public static FirstInFirstOutScheduler instance()
  {
    return new FirstInFirstOutScheduler();
  }

  /**
   * Ensure user code cannot construct an instance.
   */
  FirstInFirstOutScheduler()
  {
  }

  /**
   * Obtains the maximum priority available for a schedulable
   * managed by this scheduler.
   *
   * @return the value of the maximum priority.
   */
  public int getMaxPriority()
  {
    return 0;
  }

  /**
   * Obtains the minimum priority available for a schedulable
   * managed by this scheduler.
   *
   * @return the minimum priority used by this scheduler.
   */
  public int getMinPriority()
  {
    return 0;
  }

  /**
   * Obtains the normal priority available for a schedulable managed
   * by this scheduler.
   *
   * @return the value of the normal priority.
   */
  public int getNormPriority()
  {
    return 0;
  }

  /**
   * Obtains the policy name of {@code this}.
   *
   * @return the policy name (Fixed Priority First In First Out) as a string.
   */
  @Override
  public String getPolicyName()
  {
    return new String("Fixed Priority First In First Out");
  }

  @Override
  public void reschedule(Thread thread, SchedulingParameters eligibility) { }
}
