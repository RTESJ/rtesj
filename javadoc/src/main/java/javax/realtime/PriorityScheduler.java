/*-----------------------------------------------------------------------*\
 * Copyright 2012-2018, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;


/**
 * Class which represents the required (by the RTSJ) priority-based schedulers.
 * The default instance is the base scheduler which uses a fixed priority,
 * first-in-first-out, preemptive scheduling algorithm.
 *
 * <p> This scheduler, like all schedulers, governs the default values
 * for scheduling-related parameters in its client schedulables.
 * The defaults are as follows:
 *
 * <table width="95%" border="1">
 *   <caption>PriorityScheduler Default PriorityParameter Values</caption>
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
 * <p> Note that the system contains by default one instance of
 * {@code PriorityScheduler}, which is the system's base scheduler
 * and is returned by {@link FirstInFirstOutScheduler#instance()},
 * so a subclass of PriorityScheduler.
 * It may, however, contain other instances of subclasses of
 * {@code PriorityScheduler} created through this class'
 * protected constructor.  The instance returned by the
 * {@code FirstInFirstOutScheduler.instance()} method, the
 * <em>base scheduler</em>, is also returned by
 * {@link Scheduler#getDefaultScheduler()} unless the default scheduler
 * is changed with {@link Scheduler#setDefaultScheduler(Scheduler)}.
 *
 * @since RTSJ 2.0 PriorityScheduler is abstract.
 */
public abstract class PriorityScheduler extends Scheduler
{
  /*
   * The maximum priority value used by the implementation.
   * @deprecated as of RTSJ 1.0.1 Use the {@link #getMaxPriority}
   * method instead.
   */
  @Deprecated
  public static final int MAX_PRIORITY = instance().getMaxPriority();

  /*
   * The minimum priority value used by the implementation.
   * @deprecated as of RTSJ 1.0.1 Use the {@link #getMinPriority}
   * method instead.
   */
  @Deprecated
  public static final int MIN_PRIORITY = instance().getMinPriority();

  /**
   * Constructs an instance of {@code PriorityScheduler}.
   * Applications will likely not need any instance other than the
   * default instance.
   */
  protected PriorityScheduler()
  {
  }

  /**
   * Gets the policy name of {@code this}.
   *
   * @return the policy name (Fixed Priority) as a string.
   */
  @Override
  public String getPolicyName()
  {
    return new String("Fixed Priority");
  }

  /**
   * Gets the maximum priority available for a schedulable
   * managed by this scheduler.
   *
   * @return the value of the maximum priority.
   */
  public int getMaxPriority() { return 0; }

  /**
   * Gets the minimum priority available for a schedulable
   * managed by this scheduler.
   *
   * @return the minimum priority used by this scheduler.
   */
  public int getMinPriority() { return 0; }

  /**
   * Gets the normal priority available for a schedulable managed
   * by this scheduler.
   *
   * @return the value of the normal priority.
   */
  public int getNormPriority() { return 0; }

  @Override
  protected SchedulingParameters createDefaultSchedulingParameters()
  {
    return null;
  }

  /**
   * Gets the maximum priority for the given thread.  When the given
   * thread is a realtime thread that is scheduled by an instance of
   * {@code PriorityScheduler}, then the maximum priority for that
   * scheduler is returned.  When the given thread is not an instance of
   * {@link Schedulable}, the maximum priority of its thread group is
   * returned. Otherwise an exception is thrown.
   *
   * @param thread An instance of {@code Thread}.
   *       When {@code null}, the maximum priority of this scheduler
   *       is returned.
   *
   * @return the maximum priority for {@code thread}
   *
   * @throws StaticIllegalArgumentException when {@code thread} is a
   *         realtime thread that is not scheduled by an instance of
   *         {@code PriorityScheduler}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static int getMaxPriority(Thread thread)
  {
    return FirstInFirstOutScheduler.getMaxPriority(thread);
  }

  /**
   * Gets the minimum priority for the given thread.  When the given
   * thread is a realtime thread that is scheduled by an instance of
   * {@code PriorityScheduler}, then the minimum priority for that
   * scheduler is returned.  When the given thread is not an instance of
   * {@link Schedulable}, {@code Thread.MIN_PRIORITY} is
   * returned. Otherwise an exception is thrown.
   *
   * @param thread An instance of {@code Thread}.
   *       When {@code null}, the minimum priority of this scheduler
   *       is returned.
   *
   * @return the minimum priority for {@code thread}
   *
   * @throws StaticIllegalArgumentException when {@code thread} is a
   *         realtime thread that is not scheduled by an instance of
   *         {@code PriorityScheduler}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static int getMinPriority(Thread thread)
  {
    return FirstInFirstOutScheduler.getMinPriority(thread);
  }

  /**
   * Gets the "norm" priority for the given thread.  When the given
   * thread is a realtime thread that is scheduled by an instance of
   * {@code PriorityScheduler}, then the norm priority for that
   * scheduler is returned.  When the given thread is not an instance of
   * {@link Schedulable}, {@code Thread.NORM_PRIORITY} is
   * returned. Otherwise an exception is thrown.
   *
   * @param thread An instance of {@code Thread}.
   *       When {@code null}, the norm priority for this scheduler
   *       is returned.
   *
   * @return The norm priority for {@code thread}
   *
   * @throws StaticIllegalArgumentException when {@code thread} is a
   *         realtime thread that is not scheduled by an instance3 of
   *         {@code PriorityScheduler}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static int getNormPriority(Thread thread)
  {
    return FirstInFirstOutScheduler.getNormPriority(thread);
  }

  /**
   * Obtains a reference to the distinguished instance of
   * {@code PriorityScheduler}, which is the system's base
   * scheduler.
   *
   * @return A reference to the distinguished instance
   *         {@code PriorityScheduler}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static PriorityScheduler instance()
  {
    return new FirstInFirstOutScheduler();
  }

  /**
   * Queries this {@code Scheduler} about the feasibility of the set of
   * schedulables currently in the feasibility set.
   *
   * <p><b>Implementation Notes</b>
   *
   * <p>The default feasibility test for the {@code PriorityScheduler}
   * considers a set of schedulables with bounded resource
   * requirements, to always be feasible.  This covers all schedulable
   * objects with release parameters of types {@link PeriodicParameters}
   * and {@link SporadicParameters}.
   *
   * <p> When any schedulable within the feasibility set has
   * release parameters of the exact type {@link AperiodicParameters}
   * (not a subclass thereof), then the feasibility set is not feasible,
   * as aperiodic release characteristics require unbounded resources.
   * In that case, this method will return {@code false} and all
   * methods in the {@code setIfFeasible} family of methods will also
   * return {@code false}.  Consequently, any call to a
   * {@code setIfFeasible} method that passes a schedulable
   * which has release parameters of type {@link AperiodicParameters},
   * or passes proposed release parameters of type {@link
   * AperiodicParameters}, will return {@code false}.  The only time a
   * {@code setIfFeasible} method can return {@code true}, when there
   * exists in the feasibility set a schedulable with release
   * parameters of type {@link AperiodicParameters}, is when the method
   * will change those release parameters to not be {@link
   * AperiodicParameters}.
   *
   * <p>Implementations may provide a feasibility test other than the
   * default test just described. In which case the details of that test
   * should be documented here in place of this description of the
   * default implementation.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
  @Override
  public boolean isFeasible()
  {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @param schedulable The schedulable for which the changes are
   * proposed.
   *
   * @param release The proposed release parameters.  When
   *        {@code null}, the default value of this scheduler is
   *        used (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @param memory The proposed memory parameters.  When
   *        {@code null}, the default value of this scheduler is
   *        used (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made. {@code False}, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when {@code Schedulable} is
   *         {@code null}, or {@code Schedulable} is not
   *         associated with {@code this} scheduler, or the proposed
   *         parameters are not compatible with {@code this} scheduler.
   *
   * @throws IllegalAssignmentError when {@code Schedulable} cannot
   *         hold references to the proposed parameter objects, or the
   *         parameter objects cannot hold a reference to
   *         {@code Schedulable}.
   *
   * @throws IllegalThreadStateException when the new release parameters
   *         change {@code Schedulable} from periodic scheduling to
   *         some other protocol and {@code Schedulable} is currently
   *         waiting for the next release in
   *         {@link RealtimeThread#waitForNextPeriod()} or
   *         {@link RealtimeThread#waitForNextPeriodInterruptible()}.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate
   */
  @Deprecated
  @Override
  public boolean setIfFeasible(Schedulable schedulable,
                               ReleaseParameters<?> release,
                               MemoryParameters memory)
  {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   *
   * @param schedulable The schedulable for which the changes are
   * proposed.
   *
   * @param release The proposed release parameters.  When
   *        {@code null}, the default value of this scheduler is
   *        used (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @param memory The proposed memory parameters.  When
   *        {@code null}, the default value of this scheduler is
   *        used (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @param group The proposed processing group parameters.  When
   *        {@code null}, the default value of this scheduler is
   *        used (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made. {@code False}, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when {@code Schedulable} is
   *         {@code null}, or {@code Schedulable} is not
   *         associated with {@code this} scheduler, or the proposed
   *         parameters are not compatible with {@code this} scheduler.
   *
   * @throws IllegalAssignmentError when {@code Schedulable} cannot
   *         hold references to the proposed parameter objects, or the
   *         parameter objects cannot hold a reference to
   *         {@code Schedulable}.
   *
   * @throws IllegalThreadStateException when the new release parameters
   *         change {@code Schedulable} from periodic scheduling to
   *         some other protocol and {@code Schedulable} is currently
   *         waiting for the next release in
   *         {@link RealtimeThread#waitForNextPeriod()} or
   *         {@link RealtimeThread#waitForNextPeriodInterruptible()}.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate
   */
  @Deprecated
  @Override
  public boolean setIfFeasible(Schedulable schedulable,
                               ReleaseParameters<?> release,
                               MemoryParameters memory,
                               ProcessingGroupParameters group)
  {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @param schedulable The schedulable for which the changes are
   * proposed.
   *
   * @param scheduling The proposed scheduling parameters.
   * When {@code null}, the default value of this scheduler is used
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param release The proposed release parameters.
   * When {@code null}, the default value of this scheduler is used
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param memory The proposed memory parameters.
   * When {@code null}, the default value of this scheduler is used
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param group The proposed processing group parameters.
   * When {@code null}, the default value of this scheduler is used
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made.  {@code False}, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when {@code Schedulable} is
   *         {@code null}, or {@code Schedulable} is not
   *         associated with {@code this} scheduler, or the proposed
   *         parameters are not compatible with {@code this} scheduler.
   *
   * @throws IllegalAssignmentError when {@code Schedulable} cannot
   *         hold references to the proposed parameter objects, or the
   *         parameter objects cannot hold a reference to
   *         {@code Schedulable}.
   *
   * @throws IllegalThreadStateException when the new release parameters
   *         change {@code Schedulable} from periodic scheduling to
   *         some other protocol and {@code Schedulable} is currently
   *         waiting for the next release in
   *         {@link RealtimeThread#waitForNextPeriod()} or
   *         {@link RealtimeThread#waitForNextPeriodInterruptible()}.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate
   */
  @Deprecated
  @Override
  public boolean setIfFeasible(Schedulable schedulable,
                               SchedulingParameters scheduling,
                               ReleaseParameters<?> release,
                               MemoryParameters memory,
                               ProcessingGroupParameters group)
  {
    return false;
  }

  /**
   * {@inheritDoc}
   *
   * @param schedulable A reference to the given instance of {@link Schedulable}
   *
   * @return {@code true}, when the system is feasible after the addition, otherwise {@code False}.
   *
   * @throws StaticIllegalArgumentException when {@code schedulable} is
   *         {@code null}, or when {@code schedulable} is not
   *         associated with {@code this}; that is
   *         <code>schedulable.getScheduler() != this</code>.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
  @Override
  protected boolean addToFeasibility(Schedulable schedulable)
  {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @param schedulable A reference to the given instance of {@link Schedulable}
   *
   * @return {@code true}, when the removal was successful. {@code false},
   *         when the schedulable cannot be removed from the
   *         scheduler's feasibility set; e.g., the schedulable is not part
   *         of the scheduler's feasibility set.
   *
   *  @throws StaticIllegalArgumentException when {@code schedulable}
   *          is {@code null}.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   * is inadequate
   */
  @Deprecated
  @Override
  protected boolean removeFromFeasibility(Schedulable schedulable)
  {
    return true;
  }

  /**
   * {@inheritDoc}
   *
   * @param schedulable {@inheritDoc}
   *
   * @throws StaticUnsupportedOperationException Thrown in all cases by
   *         instance of {@code PriorityScheduler}.
   *
   * @deprecated RTSJ 2.0
   */
  @Deprecated
  @Override
  public void fireSchedulable(Schedulable schedulable)
  {
  }

  @Override
  public void reschedule(Thread thread, SchedulingParameters eligibility) {}
}
