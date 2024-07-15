/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An instance of {@code Scheduler} manages the execution of
 * schedulables.
 *
 * <p> Subclasses of {@code Scheduler} are used for alternative
 * scheduling policies and should define an {@code instance()}
 * class method to return the default instance of the subclass.  The
 * name of the subclass should be descriptive of the policy, allowing
 * applications to deduce the policy available for the scheduler
 * obtained via {@link Scheduler#getDefaultScheduler}, e.g.,
 * {@code EDFScheduler}.
 */
public abstract class Scheduler
{
  /**
   * Gets a reference to the default scheduler.
   *
   * @return a reference to the default scheduler.
   */
  public static Scheduler getDefaultScheduler()
  {
    return new FirstInFirstOutScheduler();
  }

  /**
   * Sets the default scheduler.  This is the scheduler given to
   * instances of schedulables when they are constructed by a
   * Java thread.  The default scheduler is set to the required
   * {@link PriorityScheduler} at startup.
   *
   * @param scheduler The {@code Scheduler} that becomes the
   *        default scheduler assigned to new schedulables
   *        created by Java threads. When {@code null} nothing
   *        happens.
   *
   * @throws StaticSecurityException when the caller is not permitted to set
   * the default scheduler.
   */
  public static void setDefaultScheduler(Scheduler scheduler)
  {
  }

  /**
   * Determines whether the current calling context is a {@link Schedulable}:
   * {@link RealtimeThread} or {@link AsyncBaseEventHandler}.
   *
   * @return {@code true} when yes and {@code false} otherwise.
   *
   * @since RTSJ 2.0
   */
  public static boolean inSchedulableExecutionContext()
  {
    return true;
  }

  /**
   * Gets the current execution context when called from a {@link Schedulable}
   * execution context.
   *
   * @return the current {@link Schedulable}.
   *
   * @throws ClassCastException when the caller is not a {@link Schedulable}
   *
   * @since RTSJ 2.0
   */
  public static Schedulable currentSchedulable()
  {
    return null;
  }

  /**
   * Creates an instance of {@code Scheduler}.
   */
  protected Scheduler()
  {
  }

  /**
   * Gets a string representing the policy of {@code this}.  The
   * string value need not be interned, but it must be created in a
   * memory area that does not cause an illegal assignment error when
   * stored in the current allocation context and does not cause a
   * {@link MemoryAccessError} when accessed.
   *
   * @return a {@code String} object which is the name of the scheduling
   * policy used by {@code this}.
   */
  public abstract String getPolicyName();

  /**
   * Informs this scheduler and cooperating facilities that the resource
   * demands of the given instance of {@link Schedulable} will be
   * considered in the feasibility analysis of the associated {@link
   * Scheduler} until further notice. Whether the resulting system is
   * feasible or not, the addition is completed.  <p> When the object is
   * already included in the feasibility set, does nothing.
   *
   * @param schedulable A reference to the given instance of {@link Schedulable}
   *
   * @return {@code true}, when the system is feasible after the addition, and
   *         {@code false}, when not.
   *
   * @throws StaticIllegalArgumentException when {@code schedulable} is
   *         {@code null}, or when {@code schedulable} is not
   *         associated with {@code this}; that is
   *         <code>schedulable.getScheduler() != this</code>.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
  protected abstract boolean addToFeasibility(Schedulable schedulable);

  /**
   * Queries the system about the feasibility of the system currently
   * being considered.  The definitions of &quot;feasible&quot; and
   * &quot;system&quot; are the responsibility of the feasibility
   * algorithm of the actual {@code Scheduler} subclass.
   *
   * @return {@code true}, when the system is feasible; {@code false},
   *         when not.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   * is inadequate
   */
  @Deprecated
  public abstract boolean isFeasible();

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter objects as replacements for the current
   * parameters of {@code Schedulable}.  When the resulting system is
   * feasible, this method replaces the current parameters of
   * {@code Schedulable} with the proposed ones.  <p> This method does
   * not require that the schedulable be in the feasibility set
   * before it is called. When it is not initially a member of the
   * feasibility set it will be added when the resulting system is
   * feasible.
   *
   * @param schedulable The schedulable for which the changes are
   * proposed.
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
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made; {@code false}, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when {@code Schedulable} is
   *         {@code null}, or {@code Schedulable} is not
   *         associated with {@code this} scheduler, or the proposed
   *         parameters are not compatible with {@code this} scheduler.
   * @throws IllegalAssignmentError when {@code Schedulable} cannot
   *         hold references to the proposed parameter objects, or the
   *         parameter objects cannot hold a reference to
   *         {@code Schedulable}.
   * @throws IllegalThreadStateException when the new ReleaseParameters
   *         changes {@code Schedulable} from periodic scheduling to
   *         some other protocol and {@code Schedulable} is currently
   *         waiting for the next release in
   *         {@link RealtimeThread#waitForNextPeriod()} or
   *         {@link RealtimeThread#waitForNextPeriodInterruptible()}.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate
   */
  @Deprecated
  public abstract boolean setIfFeasible(Schedulable schedulable,
                                        ReleaseParameters<?> release,
                                        MemoryParameters memory);

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter objects as replacements for the current
   * parameters of {@code Schedulable}.  When the resulting system is
   * feasible, this method replaces the current parameters of
   * {@code Schedulable} with the proposed ones.
   *
   * <p> This method does
   * not require that the schedulable be in the feasibility set
   * before it is called. When it is not initially a member of the
   * feasibility set it will be added when the resulting system is
   * feasible.
   *
   * @param schedulable The schedulable for which the changes are
   * proposed.
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
   *         are made; {@code false}, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when {@code Schedulable} is
   *         {@code null}, or {@code Schedulable} is not
   *         associated with {@code this} scheduler, or the proposed
   *         parameters are not compatible with {@code this} scheduler.
   * @throws IllegalAssignmentError when {@code Schedulable} cannot
   *         hold references to the proposed parameter objects, or the
   *         parameter objects cannot hold a reference to
   *         {@code Schedulable}.
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
  public abstract boolean setIfFeasible(Schedulable schedulable,
                                        ReleaseParameters<?> release,
                                        MemoryParameters memory,
                                        ProcessingGroupParameters group);

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter objects as replacements for the current
   * parameters of {@code Schedulable}.  When the resulting system is
   * feasible, this method replaces the current parameters of
   * {@code Schedulable} with the proposed ones.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
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
   *         are made; {@code false}, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when {@code Schedulable} is
   *         {@code null}, or {@code Schedulable} is not
   *         associated with {@code this} scheduler, or the proposed
   *         parameters are not compatible with {@code this} scheduler.
   * @throws IllegalAssignmentError when {@code Schedulable} cannot
   *         hold references to the proposed parameter objects, or the
   *         parameter objects cannot hold a reference to
   *         {@code Schedulable}.
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
  public abstract boolean setIfFeasible(Schedulable schedulable,
                                        SchedulingParameters scheduling,
                                        ReleaseParameters<?> release,
                                        MemoryParameters memory,
                                        ProcessingGroupParameters group);

  /**
   * Informs this scheduler and cooperating facilities that the resource
   * demands of the given instance of {@link Schedulable} should no
   * longer be considered in the feasibility analysis of the associated
   * {@link Scheduler}. Whether the resulting system is feasible or not,
   * the removal is completed.
   *
   * @param schedulable A reference to the given instance of {@link Schedulable}
   *
   * @return {@code true}, when the removal was successful, and
   *         {@code false}, when the schedulable cannot be removed from the
   *         scheduler's feasibility set; e.g., the schedulable is not
   *         part of the scheduler's feasibility set.
   *
   *  @throws StaticIllegalArgumentException when {@code schedulable}
   *          is {@code null}.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   * is inadequate
   */
  @Deprecated
  protected abstract boolean removeFromFeasibility(Schedulable schedulable);

  /**
   * Triggers the execution of a schedulable (like an
   * {@link AsyncEventHandler}).
   *
   * @param schedulable The schedulable to make active.
   *        When {@code null}, nothing happens.
   *
   * @throws StaticUnsupportedOperationException when the scheduler
   *         cannot release {@code schedulable} for execution.
   *
   * @deprecated RTSJ 2.0
   */
  @Deprecated
  public abstract void fireSchedulable(Schedulable schedulable);

  /**
   * Promotes a {@code java.lang.Thread} to realtime priority under this
   * scheduler.  The affected thread will be scheduled as if it was a
   * {@link RealtimeThread} with the given eligibility.  This does not make
   * the affected thread a {@code RealtimeThread}, however, and it will
   * not have access to facilities reserved for instances of
   * {@code RealtimeThread}.  Instances of {@code RealtimeThread} will
   * be treated as if their scheduling parameters were set to
   * {@code eligibility}.
   *
   * @param thread The thread to promote to realtime scheduling.
   *
   * @param eligibility A {@link SchedulingParameters} instance such as
   *        {@link PriorityParameters} for a {@code PriorityScheduler}.
   *
   * @throws StaticIllegalArgumentException when {@code eligibility}
   *         is not valid for the scheduler.
   *
   * @throws StaticIllegalStateException when {@code eligibility}
   *         specifies parameters that are out of range for the
   *         scheduler or the threads state or the intersection of affinity
   *         in {@code scheduling} and the affinity of realtime thread group
   *         associated with {@code thread} is empty.
   *
   * @throws StaticUnsupportedOperationException when {@code thread} a
   *         normal Java thread and the scheduler does not support
   *         promoting normal java threads.
   *
   * @since RTSJ 2.0
   */
  public abstract void reschedule(Thread thread,
                                  SchedulingParameters eligibility);

  /**
   * Create a default {@link SchedulingParameters} instance for this
   * schedulers.  A scheduler must define this in order to support
   * setting {@link Schedulable#setSchedulingParameters} with
   * {@code null} as its parameter.  Otherwise, null is not allowed.
   *
   * @return parameters that are suitable for this scheduler in the
   *         current context.
   *
   * @throws IllegalTaskStateException when the current task is using
   *         a scheduler that does not support {@code null} scheduling
   *         parameters.
   *
   * @since RTSJ 2.0
   */
  protected SchedulingParameters createDefaultSchedulingParameters()
  {
    throw IllegalTaskStateException.get().
      init("Scheduler " + this.getClass().getName() +
           " does not support null scheduling parameters!");
  }
}
