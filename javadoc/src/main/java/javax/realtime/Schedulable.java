/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An interface for all types of task defined in this specification.  All
 * implementations of {@code Schedulable} can be scheduled by any
 * {@link Scheduler} defined here.  A scheduler uses the information available
 * through this interface to create a suitable context in which to execute
 * the code encapsulated by this object.
 */
public interface Schedulable extends Runnable, Subsumable<Schedulable>
{
  /**
   * Gets a reference to the {@link MemoryParameters} object for this
   * schedulable.
   *
   * @return a reference to the current {@link MemoryParameters} object.
   */
  public MemoryParameters getMemoryParameters();


  /**
   * Sets the memory parameters associated with this instance of
   * {@code Schedulable}.
   *
   * <p>This change becomes effective at the next allocation;
   * on multiprocessor systems, there may be some delay due
   * to synchronization between processors.
   *
   * @param memory A {@link MemoryParameters} object which will become
   *               the memory parameters associated with
   *               {@code this} after the method call.  When
   *               {@code null}, the default value is governed by
   *               the associated scheduler; a new object is created when
   *               the default value is not {@code null}.  (See
   *               {@link PriorityScheduler}.)
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code memory} is not
   *         compatible with the schedulable's scheduler.  Also
   *         when this schedulable may not use the heap and
   *         {@code memory} is located in heap memory.
   * @throws IllegalAssignmentError when the schedulable cannot
   *         hold a reference to {@code memory}, or when
   *         {@code memory} cannot hold a reference to this
   *         schedulable instance.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public Schedulable setMemoryParameters(MemoryParameters memory);


  /**
   * Gets a reference to the {@link ReleaseParameters} object  for this
   * schedulable.
   *
   * @return a reference to the current {@link ReleaseParameters} object.
   */
  public ReleaseParameters<?> getReleaseParameters();

  /**
   * Sets the release parameters associated with this instance of
   * {@code Schedulable}.
   *
   *
   * <p>This change becomes effective under conditions determined by the
   * scheduler controlling the schedulable.  For instance, the
   * change may be immediate or it may be delayed until the next release
   * of the schedulable.  The different properties of the release
   * parameters may take effect at different times.  See the
   * documentation for the scheduler for details.
   *
   * @param release A {@link ReleaseParameters} object which will become
   *        the release parameters associated with this after the method
   *        call, and take effect as determined by the associated
   *        scheduler.  When {@code null}, the default value is
   *        governed by the associated scheduler; a new object is
   *        created when the default value is not {@code null}.
   *        (See {@link PriorityScheduler}.)
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code release}
   *         is not compatible with the associated scheduler.  Also when
   *         this schedulable may not use the heap and {@code release}
   *         is located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} object cannot
   *          hold a reference to {@code release} or
   *          {@code release} cannot hold a reference to
   *          {@code this}.
   *
   * @throws IllegalTaskStateException when the task is running and
   *         the new release parameters are not compatible with the current
   *         scheduler.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public Schedulable setReleaseParameters(ReleaseParameters<?> release);


  /**
   * Gets a reference to the {@link Scheduler} object for this
   * schedulable.
   *
   * @return a reference to the associated {@link Scheduler} object.
   */
  public Scheduler getScheduler();

  /**
   * Sets the reference to the Scheduler object.  The timing of the
   * change must be agreed between the scheduler currently associated
   * with this schedulable, and {@code scheduler}.  If the
   * {@code Schedulable} is running, its associated
   * {@code SchedulingParameters} (if any) must be compatible with
   * {@code scheduler}.
   *
   * @param scheduler A reference to the scheduler that will manage
   *        execution of this schedulable. {@code Null} is
   *        not a permissible value.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code scheduler}
   *         is {@code null}, or the schedulable's existing
   *         parameter values are not compatible with
   *         {@code scheduler}.  Also when this schedulable
   *         may not use the heap and {@code scheduler} is located in heap
   *         memory.
   *
   * @throws IllegalAssignmentError when the schedulable cannot
   *         hold a reference to {@code scheduler} or the current
   *         {@code Schedulable} is running and its associated
   *         {@code SchedulingParameters} are incompatible with
   *         {@code scheduler}.
   *
   * @throws StaticSecurityException when the caller is not permitted to set
   *         the scheduler for this schedulable.
   *
   * @throws IllegalTaskStateException when {@code scheduler}
   *         has scheduling or release parameters that are not compatible
   *         with the new scheduler and this schedulable is running.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public Schedulable setScheduler(Scheduler scheduler)
    throws StaticSecurityException, IllegalTaskStateException;


  /**
   * Sets the scheduler and associated parameter objects.  The timing of
   * the change must be agreed between the scheduler currently
   * associated with this schedulable, and
   * {@code scheduler}.
   *
   * @param scheduler A reference to the scheduler that will manage the
   *        execution of this schedulable.  <Code>Null</Code> is
   *        not a permissible value.
   *
   * @param scheduling A reference to the {@link SchedulingParameters}
   *        which will be associated with {@code this}.  When
   *        {@code null}, the default value is governed by
   *        {@code scheduler}; a new object is created when the
   *        default value is not {@code null}.  (See {@link
   *        PriorityScheduler}.)
   *
   * @param release A reference to the {@link ReleaseParameters} which
   *        will be associated with {@code this}.  When
   *        {@code null}, the default value is governed by
   *        {@code scheduler}; a new object is created when the
   *        default value is not {@code null}.  (See {@link
   *        PriorityScheduler}.)
   *
   * @param memoryParameters A reference to the {@link MemoryParameters}
   *      which will be associated with {@code this}.  When
   *      {@code null}, the default value is governed by
   *      {@code scheduler}; a new object is created when the default
   *      value is not {@code null}.  (See {@link
   *      PriorityScheduler}.)
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code scheduler}
   *         is {@code null} or the parameter values are not
   *         compatible with {@code scheduler}.  Also thrown when
   *         this schedulable may not use the heap and
   *         {@code scheduler}, {@code scheduling}
   *         {@code release}, {@code memoryParameters}, or
   *         {@code group} is located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} object cannot
   *         hold references to all the parameter objects or the
   *         parameters cannot hold references to {@code this}.
   *
   * @throws StaticSecurityException when the caller is not permitted to set
   *         the scheduler for this schedulable.
   *
   * @since RTSJ 2.0
   */
  @ReturnsThis
  public Schedulable setScheduler(Scheduler scheduler,
                                  SchedulingParameters scheduling,
                                  ReleaseParameters<?> release,
                                  MemoryParameters memoryParameters);


  /**
   * Gets a reference to the {@link SchedulingParameters} object  for this
   * schedulable.
   *
   * @return A reference to the current {@link SchedulingParameters} object.
   */
  public SchedulingParameters getSchedulingParameters();

  /**
   * Sets the scheduling parameters associated with this instance of
   * {@code Schedulable}.
   *
   * <p>
   * This change becomes effective under conditions determined by the
   * scheduler controlling the schedulable.  For instance, the
   * change may be immediate or it may be delayed until the next release
   * of the schedulable.  See the documentation of the scheduler
   * for details.
   *
   * @param scheduling A reference to the {@link SchedulingParameters}
   *        object.  When {@code null}, the default value is governed
   *        by the associated scheduler; a new object is created when the
   *        default value is not {@code null}.  (See
   *        {@link PriorityScheduler}.).  When the Affinity is not defined
   *        in {@code scheduling}, then the affinity that will be used
   *        is the one of the creating Thread. However, this default
   *        affinity will not appear when calling
   *        {@link #getSchedulingParameters}, unless explicitly set using
   *        this method.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code scheduling} is
   *         not compatible with the associated scheduler.  Also when
   *         this schedulable may not use the heap and
   *         {@code scheduling} is located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} object cannot hold
   *         a reference to {@code scheduling} or {@code scheduling}
   *         cannot hold a reference to {@code this}.
   *
   * @throws IllegalTaskStateException when the task is active and
   *         the new scheduling parameters are not compatible with the current
   *         scheduler or when the task is active and the affinity in
   *         {@code scheduling} is not a subset of the affinity of {@code this}
   *         object's {@link RealtimeThreadGroup} or when the task is active and
   *         the affinity in {@code scheduling} is invalid.
   *
   * @since RTSJ 2.0,  method returns a reference to {@code this}.
   */
  @ReturnsThis
  public Schedulable setSchedulingParameters(SchedulingParameters scheduling)
    throws IllegalTaskStateException,
           IllegalAssignmentError,
           StaticIllegalArgumentException;


  /*
   * Gets a reference to the {@link RealtimeThreadGroup} instance of this
   * {@code Schedulable}.  For instance that are not also instances of
   * {@code RealtimeThread}, such as instance if {@code AsyncBaseEventHandler},
   * the group returned is the group that contains the thread or threads that
   * are used to execute said {@code Schedulable} instance.  For each instance
   * of {@code Schedulable}, there can be only one directly associated instance
   * of {@code Schedulable}.
   *
   * @return a reference to the associated {@link RealtimeThreadGroup} object.
   *
   * @since RTSJ 2.x (save for later)
   *//*
  public RealtimeThreadGroup getRealtimeThreadGroup();
     */

  /**
   * Gets a reference to the {@link ConfigurationParameters}
   * object for this schedulable.
   *
   * @return a reference to the associated
   *         {@link ConfigurationParameters} object.
   *
   * @since RTSJ 2.0
   */
  public ConfigurationParameters getConfigurationParameters();


  /**
   * Determines the minimum CPU consumption for this schedulable in
   * any single release.  When this method is called on the current
   * schedulable, the CPU consumption of the current release is
   * not considered.  When {@code dest} is {@code null}, returns
   * the minimum consumption in a {@link RelativeTime}
   * instance from the current allocation context. When {@code dest} is
   * not {@code null}, returns the minimum consumption in
   * {@code dest}
   *
   * @param dest When not {@code null}, the object in which to return
   *        the result.
   *
   * @return the minimum time consumed in any release.
   *
   * @since RTSJ 2.1
   */
  //  public RelativeTime getMinConsumption(RelativeTime dest);


  /*
   * Equivalent to {@code getMinConsumption(null)}.
   *
   * @return the minimum time consumed in any release.
   *
   * @since RTSJ 2.1
   */
  //public RelativeTime getMinConsumption();


  /*
   * Determines the maximum CPU consumption for this schedulable in any
   * single release.  When this method is called on the current
   * schedulable, the CPU consumption of the current release is not
   * considered.  When {@code dest} is {@code null}, returns the
   * maximum consumption in a {@link RelativeTime} instance from
   * the current allocation context. When {@code dest} is not
   * {@code null}, returns the maximum consumption in
   * {@code dest}
   *
   * @param dest When not {@code null}, the object in which to return
   *        the result.
   *
   * @return the maximum time consumed in any release.
   *
   * @since RTSJ 2.1
   */
  //public RelativeTime getMaxConsumption(RelativeTime dest);


  /*
   * Equivalent to {@code getMaxConsumption(null)}.
   *
   * @return the maximum time consumed in any release.
   *
   * @since RTSJ 2.1
   */
  //public RelativeTime getMaxConsumption();

  /**
   * Marks this schedulable as either a daemon or a user task.
   * A realtime virtual machine exits when the only tasks running are all
   * daemons.  This method must be called before the task is attached to any
   * event or started.  Once attached or started, it cannot be changed.
   *
   * @param on When {@code true}, marks this event handler as a daemon handler.
   *
   * @throws IllegalThreadStateException when this schedulable is active.
   *
   * @throws StaticSecurityException when the current schedulable
   *         cannot modify this event handler.
   *
   * @since RTSJ 2.0
   */
  public void setDaemon(boolean on);

  /**
   * Tests if this event handler is a daemon handler.
   * @return {@code true} when this event handler is a daemon handler;
   *         {@code false} otherwise.
   *
   * @since RTSJ 2.0
   */
  public boolean isDaemon();


  /**
   * Determines whether or not this {@code schedulable} may use the heap.
   *
   * @return {@code true} only when this {@code Schedulable} may allocate
   *         on the heap and may enter the {@code Heap}.
   *
   * @since RTSJ 2.0
   */
  public boolean mayUseHeap();


  /*
   * In a system where {@link RTSJModule#CONTROL} is implemented, it
   * makes the generic {@link AsynchronouslyInterruptedException} pending
   * for {@code this}, and sets the interrupted state to {@code true}.
   * As with {@code Thread.interrupt()}, blocking operations that are
   * interruptible are interrupted.  When {@code this.isRousable()} is
   * {@code true} a release happens event if the first release has not yet
   * happend.  In any case, {@code AsynchronouslyInterruptedException}
   * is thrown once a method is entered that implements
   * {@code AsynchronouslyInterruptedException}.
   *
   * <p> Otherwise, it behaves as if {@code Thread.interrupt()} were called.
   *
   * <p> For instances of {@link AsyncBaseEventHandler}, it behaves as if
   * {@link RealtimeThread#interrupt()} was called on the implementation
   * thread underlying it.
   *
   *  @throws IllegalTaskStateException when {@code this} is not
   *        currently releasable, i.e., is disabled, not firable, its
   *        start method has not been called, or it has terminated.
   *
   * @since RTSJ 2.0
   */
  //  public void interrupt() throws IllegalTaskStateException;


  /*
   * Determines whether or not any
   * {@link AsynchronouslyInterruptedException} is pending.
   *
   * @return {@code true} when and only when the generic
   *         {@code AsynchronouslyInterruptedException} is pending.
   *
   * @since RTSJ 2.0
   */
  //  public boolean isInterrupted();

  /**
   * Determine whether or not this instance of {@code Schedulable} is
   * more eligible than {@code other}.  On multicore systems, this only
   * gives a partial ordering over all schedulables.  Schedulables with
   * disjoint processor affinity do not subsume one another.
   *
   * @return {@code true} when and only when this instance of
   *         {@code Schedulable} is more eligible than {@code other}.
   *
   * @since RTSJ 2.0
   */
  public boolean subsumes(Schedulable other);

  /**
   * Gets a reference to the {@link ProcessingGroupParameters} object
   * for this schedulable.
   *
   * @return A reference to the current
   *         {@link ProcessingGroupParameters} object.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public default ProcessingGroupParameters getProcessingGroupParameters()
  {
    return null;
  }

  /**
   * Sets the {@link ProcessingGroupParameters} of {@code this}.
   *
   * <p>This change becomes effective under conditions determined by the
   * scheduler controlling the schedulable.  For instance, the
   * change may be immediate or it may be delayed until the next release
   * of the schedulable.  See the documentation for the scheduler
   * for details.
   *
   *
   * @param group A {@link ProcessingGroupParameters} object which will
   *        take effect as determined by the associated scheduler.  When
   *        {@code null}, the default value is governed by the
   *        associated scheduler (a new object is created when the default
   *        value is not {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @throws StaticIllegalArgumentException when {@code group} is
   *         not compatible with the scheduler for this schedulable
   *         object.  Also when this schedulable may not use the heap and
   *         {@code group} is located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} object cannot
   *         hold a reference to {@code group} or
   *         {@code group} cannot hold a reference to
   *         {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits the changing of the processing group
   *         parameter at this time due to the state of the schedulable
   *         object.
   *
   * @deprecated since RTSJ 2.0;
   * see {@link javax.realtime.enforce.ProcessingConstraint}.
   */
  @Deprecated
  public default
    void setProcessingGroupParameters(ProcessingGroupParameters group)
  {
  }

  /**
   * Informs the scheduler and cooperating facilities that this
   * instance of {@link Schedulable} should be considered in
   * feasibility analysis until further notified.
   * <p>
   * When the object is already included in the feasibility set, does nothing.
   *
   * @return {@code true}, when the resulting system is feasible.
   *         False, when not.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   * is inadequate.
   */
  @Deprecated
  public default boolean addToFeasibility() { return false; }

  /**
   * Informs the scheduler and cooperating facilities that
   * this instance of {@link Schedulable} should <em>not</em> be considered in
   * feasibility analysis until it is further notified.

   * @return {@code true} when the removal was successful.
   *         {@code false} when the
   *         schedulable cannot be removed from the scheduler's
   *         feasibility set; e.g., the schedulable is not part
   *         of the scheduler's feasibility set.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is
   *             inadequate
   */
  @Deprecated
  public default boolean removeFromFeasibility() { return false; }
}
