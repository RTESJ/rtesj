/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An asynchronous event handler encapsulates code that is released
 * after an instance of {@link AsyncEvent} to which it is attached
 * occurs.
 *
 * <p> It is guaranteed that multiple releases of an event handler will
 * be serialized. It is also guaranteed that (unless the handler
 * explicitly chooses otherwise) for each release of the handler, there
 * will be one execution of the {@link AsyncEventHandler#handleAsyncEvent()}
 * method. Control over the number of calls to
 * {@link AsyncEventHandler#handleAsyncEvent()} is given by
 * methods which manipulate a {@code fireCount}. These may be
 * called by the application via sub-classing and overriding {@link
 * AsyncEventHandler#handleAsyncEvent()}.
 *
 * <p> Instances of {@code AsyncEventHandler} with a release
 * parameter of type {@link SporadicParameters} or {@link
 * AperiodicParameters} have a list of release times which correspond to
 * the occurrence times of instances of {@link AsyncEvent} to which they
 * are attached. The minimum interarrival time specified in {@link
 * SporadicParameters} is enforced when a release time is added to the
 * list. Unless the handler explicitly chooses otherwise, there will be
 * one execution of the code in {@link
 * AsyncEventHandler#handleAsyncEvent()} for each entry in the list.
 *
 * <p> The deadline and the time each release event causes the AEH to
 * become eligible for execution are properties of the scheduler that
 * controls the AEH.  For the base scheduler,
 * the deadline for each release event is relative to its fire time, and
 * the release takes place at fire time but execution eligibility may be
 * deferred when the queue's MIT violation policy is {@code SAVE}.
 *
 * <p> Handlers may do almost anything a realtime thread can do. They
 * may run for a long or short time, and they may block. (Note, blocked
 * handlers may hold system resources.) A handler may not use the
 * {@link RealtimeThread#waitForNextRelease} method.
 *
 * <p> Normally, handlers are bound to an execution context dynamically
 * when the instances of {@link AsyncEvent}s to which they are bound
 * occur. This can introduce a (small) time penalty. For critical
 * handlers that cannot afford the expense, and where this penalty is a
 * problem, {@link BoundAsyncEventHandler}s can be used.
 *
 * <p> The scheduler for an asynchronous event handler is inherited from
 * the task that created it.  When created from a task that is not an
 * instance of {@link Schedulable}, the scheduler is the current default
 * scheduler.
 *
 * <p>
 * The semantics for memory areas that were defined for realtime threads apply
 * in the same way to instances of {@code AsyncEventHandler} They may
 * inherit a scope stack when they are created, and the single parent rule
 * applies to the use of memory scopes for instances of
 * {@code AsyncEventHandler} just as it does in realtime threads.
 * <p>
 * @since RTSJ 2.0 extends AsyncBaseEventHandler.
 */
public class AsyncEventHandler extends AsyncBaseEventHandler
{
  private int fire_count_ = 0;

  /**
   * Creates a handler with the given scheduling, release, memory, group, and
   * configuration parameters to run the given logic.
   *
   * @param scheduling Parameter for scheduling the new handler
   *        (and possibly other instances of {@link Schedulable}).  When
   *        {@code scheduling} is {@code null} and the
   *        creator is an instance of {@link Schedulable},
   *        {@link SchedulingParameters} is a clone of the creator's value
   *        created in the same memory area as {@code this}.  When
   *        {@code scheduling} is {@code null} and the creator is
   *        a task that is not an instance of {@code Schedulable}, the
   *        contents and type of the new {@code SchedulingParameters}
   *        object are governed by the associated scheduler.
   *        The Affinity of the newly-created handler will be set as follow:
   *          <ul>
   *            <li>When defined, from SchedulingParameters.</li>
   *            <li>Otherwise, the Affinity will be inherited from the creating task.
   *          </ul>
   *        In the case where the affinity is not explicitly set using the constructor
   *        or {@link AsyncBaseEventHandler#setSchedulingParameters(SchedulingParameters)},
   *        the default affinity assigned to this Schedulable will not appear
   *        in the {@link SchedulingParameters} returned by
   *        {@link AsyncBaseEventHandler#getSchedulingParameters()}.
   *
   * @param release Parameter for scheduling the new handler
   *        (and possibly other instances of {@link Schedulable}).  When
   *        {@code release} is {@code null} the new
   *        {@code AsyncEventHandler} will use a clone of the
   *        default {@link ReleaseParameters} for the associated
   *        scheduler created in the memory area that contains the
   *        {@code AsyncEventHandler} object.
   *
   * @param memory Parameter for scheduling the new handler
   *        (and possibly other instances of {@link Schedulable}).  When
   *        {@code memory} is {@code null}, the new
   *        {@code AsyncEventHandler} receives {@code null} value
   *        for its memory parameters, and the amount or rate of memory
   *        allocation for the new handler is unrestricted.
   *
   * @param area The initial memory area of this handler.
   *
   * @param runner A pool of realtime threads to provide an execution context
   *        for this handler.
   *
   * @param logic The {@code Runnable} object whose
   *        {@code run()} method will serve as the logic for the
   *        new {@code AsyncEventHandler}.  When {@code logic}
   *        is {@code null}, the {@code handleAsyncEvent()}
   *        method in the new object will serve as its logic.
   *
   * @since RTSJ 2.0
   */
  public AsyncEventHandler(SchedulingParameters scheduling,
                           ReleaseParameters<?> release,
                           MemoryParameters memory,
                           MemoryArea area,
                           ConfigurationParameters config,
                           ReleaseRunner runner,
                           Runnable logic)
  {
    super(scheduling, release, memory, area, null, runner, false, logic);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ConfigurationParameters,
   *                           ReleaseRunner,
   *                           Runnable)}
   * with arguments {@code (scheduling, release, memory, area, null, null, logic)}.
   *
   * @since RTSJ 2.0
   */
  public AsyncEventHandler(SchedulingParameters scheduling,
                           ReleaseParameters<?> release,
                           MemoryParameters memory,
                           MemoryArea area,
                           Runnable logic)
  {
    this(scheduling, release, memory, area, null, null, logic);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ConfigurationParameters,
   *                           ReleaseRunner,
   *                           Runnable)}
   * with arguments {@code (scheduling, release, null, null, null, null, logic)}.
   *
   * @since RTSJ 2.0
   */
  public AsyncEventHandler(SchedulingParameters scheduling,
                           ReleaseParameters<?> release,
                           Runnable logic)
  {
    this(scheduling, release, null, null, null, null, logic);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ConfigurationParameters,
   *                           ReleaseRunner,
   *                           Runnable)}
   * with arguments {@code (scheduling, release, null, null, null, null, null)}
   *
   * @since RTSJ 2.0
   */
  public AsyncEventHandler(SchedulingParameters scheduling,
                           ReleaseParameters<?> release)
  {
    this(scheduling, release, null, null, null, null, null);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ConfigurationParameters,
   *                           ReleaseRunner,
   *                           Runnable)}
   * with arguments {@code (null, null, null, null, null, null, logic)}.
   */
  public AsyncEventHandler(Runnable logic)
  {
    this(null, null, null, null, null, null, logic);
  }

  /**
   * Creates an instance of {@code AsyncEventHandler} with default
   * values for all parameters.
   *
   * @see #AsyncEventHandler(SchedulingParameters,
   *                         ReleaseParameters,
   *                         MemoryParameters,
   *                         MemoryArea,
   *                         ConfigurationParameters,
   *                         ReleaseRunner,
   *                         Runnable)
   */
  public AsyncEventHandler()
  {
    this(null, null, null, null, null, null, null);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ProcessingGroupParameters,
   *                           boolean,
   *                           Runnable)}
   * with arguments {@code (scheduling, release, memory, area, pgp, nonheap, logic)}.
   *
   * @deprecated in version 2.0.
   */
  @Deprecated
  public AsyncEventHandler(SchedulingParameters scheduling,
                           ReleaseParameters<?> release,
                           MemoryParameters memory,
                           MemoryArea area,
                           ProcessingGroupParameters pgp,
                           boolean nonheap,
                           Runnable logic)
  {
    super(scheduling, release, memory, area, pgp, null, false, logic);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ProcessingGroupParameters,
   *                           boolean,
   *                           Runnable)}
   * with arguments {@code (scheduling, release, memory, area, pgp, false, logic)}.
   *
   * @deprecated in version 2.0.
   */
  @Deprecated
  public AsyncEventHandler(SchedulingParameters scheduling,
                           ReleaseParameters<?> release,
                           MemoryParameters memory,
                           MemoryArea area,
                           ProcessingGroupParameters pgp,
                           Runnable logic)
  {
    this(scheduling, release, memory, area, pgp, false, logic);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ProcessingGroupParameters,
   *                           boolean
   *                           Runnable)}
   * with arguments {@code (scheduling, release, memory, area, null, false, null)}.
   *
   * @deprecated in version 2.0.
   */
  @Deprecated
  public AsyncEventHandler(SchedulingParameters scheduling,
                           ReleaseParameters<?> release,
                           MemoryParameters memory,
                           MemoryArea area,
                           ProcessingGroupParameters pgp,
                           boolean nonheap)
  {
    this(scheduling, release, memory, area, null, (ReleaseRunner)null, null);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ProcessingGroupParameters,
   *                           boolean,
   *                           Runnable)}
   * with arguments {@code (null, null, null, null, null, nonheap, logic)}.
   *
   * @deprecated in version 2.0.
   */
  @Deprecated
  public AsyncEventHandler(boolean nonheap, Runnable logic)
  {
    this(null, null, null, null, null, nonheap, logic);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncEventHandler(SchedulingParameters,
   *                           ReleaseParameters,
   *                           MemoryParameters,
   *                           MemoryArea,
   *                           ProcessingGroupParameters,
   *                           boolean,
   *                           Runnable)}
   * with arguments {@code (null, null, null, null, null, nonheap, null)}.
   *
   * @deprecated in version 2.0.
   */
  @Deprecated
  public AsyncEventHandler(boolean nonheap)
  {
    this(null, null, null, null, null, nonheap, null);
  }

  /**
   * This method holds the logic which is to be executed when any
   * {@link AsyncEvent} with which this handler is associated is fired.
   * This method will be invoked repeatedly while {@code fireCount} is
   * greater than zero.
   * <p>
   * The default implementation of this method invokes the {@code run}
   * method of any non-null {@code logic} instance passed to the
   * constructor of this handler.
   * <p>
   * This AEH acts as a source of "reference" for its initial memory area while
   * it is released.
   * <p>
   * All throwables from (or propagated through) {@code handleAsyncEvent}
   * are caught, a stack trace is printed and execution continues as if
   * {@code handleAsyncEvent} had returned normally.
   */
  public void handleAsyncEvent()
  {
  }

  /**
   * Release this handler directly.
   */
  public void release()
  {
  }

  /**
   * This is an accessor method for {@code fireCount}.
   * This method atomically increments, by one, the value of
   * {@code fireCount} and returns the value from before the
   * increment.
   *
   * <p> Calling this method is effectively the same as firing an event
   * that is associated with this handler.  When called from outside the
   * handler's control flow, call it is effectively the same as firing
   * an event that is associated with this handler, <em>except that it
   * does not constitute a release event.</em>
   *
   * @throws MITViolationException when this AEH is controlled by
   *         sporadic scheduling parameters under the base scheduler,
   *         the parameters specify the {@code mitViolationExcept}
   *         policy, and this method would introduce a release that
   *         would violate the specified minimum interarrival time.
   * @throws ArrivalTimeQueueOverflowException when this AEH is
   *         controlled by aperiodic scheduling parameters under the
   *         base scheduler, the release parameters specify the
   *         {@code arrivalTimeQueueOverflowExcept} policy, and
   *         this method would cause the arrival time queue to overflow.
   * @return the value held by {@code fireCount} prior to
   *         incrementing it by one.
   * @deprecated as of RTSJ 2.0 Use ae.fire()
   */
  @Deprecated
  protected int getAndIncrementPendingFireCount()
  {
    return 0;
  }

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
   * @param pgp The processing group parameters to use. When
   *        {@code null}, the default value is governed by the
   *        associated scheduler (a new object is created when the default
   *        value is not {@code null}).  (See {@link PriorityScheduler}.)
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
  @Deprecated
  public void setScheduler(Scheduler scheduler,
                           SchedulingParameters scheduling,
                           ReleaseParameters<?> release,
                           MemoryParameters memoryParameters,
                           ProcessingGroupParameters pgp)
  {
  }

  /**
   * This method first performs a feasibility analysis with {@code this}
   * added to the system. When the resulting system is feasible,
   * informs the scheduler and cooperating facilities that this
   * instance of {@link Schedulable} should be considered in
   * feasibility analysis until further notified. When the analysis shows
   * that the system including {@code this} would not be feasible,
   * this method does not admit {@code this} to the feasibility set.
   * <p>
   * When the object is already included in the feasibility set, does nothing.
   *
   * @return {@code true} when inclusion of {@code this} in the feasibility
   *                    set yields a feasible system, and false
   *                    otherwise.  When {@code true} is returned then
   *                    {@code this} is known to be in the
   *                    feasibility set. When false is returned,
   *                    {@code this} was not added to the
   *                    feasibility set, but it may already have been
   *                    present.
   *
   * @since RTSJ 1.0.1 Promoted to the Schedulable interface
   *
   * @deprecated as of RTSJ 2.0, because the framework for feasibility
   * analysis is inadequate.
   */
  @Deprecated
  public boolean addIfFeasible() { return true; }


  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter objects as replacements for the current
   * parameters of {@code this}.  When the resulting system is feasible,
   * this method replaces the current parameters of {@code this} with
   * the proposed ones.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable.  For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param release The release parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param memory The memory parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made.  False, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter values
   *         are not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and any of the
   *         specified parameter objects are located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold
   *         references to the specified parameter objects, or the
   *         parameter objects cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits this parameter change at this time due
   *         to the state of the schedulable.
   *
   * @since RTSJ 1.0.1 Promoted to the {@code Schedulable} interface.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
  public boolean setIfFeasible(ReleaseParameters<?> release,
                               MemoryParameters memory)
  {
    return true;
  }

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter objects as replacements for the current
   * parameters of {@code this}.  When the resulting system is feasible,
   * this method replaces the current parameters of {@code this} with
   * the proposed ones.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable.  For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param scheduling The scheduling parameters to use.  When {@code
   *        null}, the default value is governed by the associated
   *        scheduler (a new object is created when the default value
   *        is not {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @param release The release parameters to use. When {@code null},
   *        the default value is governed by the associated scheduler
   *        (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @param memory The memory parameters to use. When {@code null},
   *        the default value is governed by the associated scheduler
   *        (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and
   *         the changes are made.  False, when the resulting system
   *         is not feasible and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter values
   *         are not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and any
   *         of the specified parameter objects are located in heap
   *         memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold
   *         references to the specified parameter objects, or the
   *         parameter objects cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits this parameter change at this time due
   *         to the state of the schedulable.
   *
   * @since RTSJ 1.0.1
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is
   *             inadequate.
   */
  @Deprecated
  public boolean setIfFeasible(SchedulingParameters scheduling,
                               ReleaseParameters<?> release,
                               MemoryParameters memory)
  {
    return true;
  }

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter objects as replacements for the current
   * parameters of {@code this}.  When the resulting system is feasible,
   * this method replaces the current parameters of {@code this} with
   * the proposed ones.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable.  For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param release The release parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param memory The memory parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param release The release parameters to use. When {@code null},
   *        the default value is governed by the associated scheduler
   *        (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @param memory The memory parameters to use. When {@code null},
   *        the default value is governed by the associated scheduler
   *        (a new object is created when the default value is not
   *        {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @param pgp The processing group parameters to use. When
   *        {@code null}, the default value is governed by the
   *        associated scheduler (a new object is created when the default
   *        value is not {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and
   *         the changes are made, or {@code false}, when the resulting
   *         system is not feasible and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter values
   *         are not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and any of the
   *         specified parameter objects are located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold
   *         references to the specified parameter objects, or the
   *         parameter objects cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits this parameter change at this time due
   *         to the state of the schedulable.
   *
   * @since RTSJ 1.0.1 Promoted to the {@code Schedulable} interface.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is
   *             inadequate.
   */
  @Deprecated
  public boolean setIfFeasible(ReleaseParameters<?> release,
                               MemoryParameters memory,
                               ProcessingGroupParameters pgp)
  {
    return true;
  }

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter objects as replacements for the current
   * parameters of {@code this}.  When the resulting system is feasible,
   * this method replaces the current parameters of {@code this} with
   * the proposed ones.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable.  For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param scheduling The scheduling parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param release The release parameters to use . When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param memory The memory parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @param pgp The processing group parameters to use. When
   * {@code null}, the default value is governed by the associated
   * scheduler (a new object is created when the default value is not
   * {@code null}).  (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made.  False, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter values
   *         are not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and any of the
   *         specified parameter objects are located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold
   *         references to the specified parameter objects, or the
   *         parameter objects cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits this parameter change at this time due
   *         to the state of the schedulable.
   *
   * @since RTSJ 1.0.1
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is
   *             inadequate.
   */
  @Deprecated
  public boolean setIfFeasible(SchedulingParameters scheduling,
                               ReleaseParameters<?> release,
                               MemoryParameters memory,
                               ProcessingGroupParameters pgp)
  {
    return true;
  }

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter object as replacement for the current parameter
   * of {@code this}.  When the resulting system is feasible, this method
   * replaces the current parameter of {@code this} with the proposed
   * one.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable.  For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param release The release parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made.  False, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter value is
   *         not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and the
   *         specified parameter object is located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold a
   *         reference to the specified parameter object, or the
   *         parameter object cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits the changing of the release parameter
   *         at this time due to the state of the schedulable.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is
   *             inadequate.
   */
  @Deprecated
  public boolean setReleaseParametersIfFeasible(ReleaseParameters<?> release)
  {
    return false;
  }

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter objects as replacements for the current
   * parameters of {@code this}.  When the resulting system is feasible,
   * this method replaces the current parameters of {@code this} with
   * the proposed ones.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable. For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param release The release parameters to use. When
   *        {@code null}, the default value is governed by the
   *        associated scheduler (a new object is created when the default
   *        value is not {@code null}).  (See {@link
   *        PriorityScheduler}.)
   *
   * @param pgp The processing group parameters to use. When
   *        {@code null}, the default value is governed by the
   *        associated scheduler (a new object is created when the default
   *        value is not {@code null}).  (See {@link
   *        PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made.  False, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter values
   *         are not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and any of the
   *         specified parameter objects are located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold
   *         references to the specified parameter objects, or the
   *         parameter objects cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits this parameter change at this time due
   *         to the state of the schedulable.
   *
   * @since RTSJ 1.0.1 Promoted to the {@code Schedulable} interface.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate.
   */
  @Deprecated
  public boolean setIfFeasible(ReleaseParameters<?> release,
                               ProcessingGroupParameters pgp)
  {
    return false;
  }

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter object as replacement for the current parameter
   * of {@code this}.  When the resulting system is feasible, this method
   * replaces the current parameter of {@code this} with the proposed
   * one.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable.  For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param memory The memory parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made.  False, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter value is
   *         not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and the
   *         specified parameter object is located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold a
   *         reference to the specified parameter object, or the
   *         parameter object cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits the changing of the memory parameter at
   *         this time due to the state of the schedulable.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate
   */
  @Deprecated
  public boolean setMemoryParametersIfFeasible(MemoryParameters memory)
  {
    return true;
  }

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter object as replacement for the current parameter
   * of {@code this}.  When the resulting system is feasible, this method
   * replaces the current parameter of {@code this} with the proposed
   * one.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable.  For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param scheduling The scheduling parameters to use. When {@code null},
   * the default value is governed by the associated scheduler
   * (a new object is created when the default value is not {@code null}).
   * (See {@link PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and
   *         the changes are made.  False, when the resulting system
   *         is not feasible and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter value is
   *         not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and the
   *         specified parameter object is located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold a
   *         reference to the specified parameter object, or the
   *         parameter object cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits the changing of the scheduling
   *         parameter at this time due to the state of the schedulable
   *         object.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate.
   */
  @Deprecated
  public
    boolean setSchedulingParametersIfFeasible(SchedulingParameters scheduling)
  {
    return true;
  }

  /**
   * This method first performs a feasibility analysis using the
   * proposed parameter object as replacement for the current parameter
   * of {@code this}.  When the resulting system is feasible, this method
   * replaces the current parameter of {@code this} with the proposed
   * one.
   *
   * <p> This change becomes effective under conditions determined by
   * the scheduler controlling the schedulable.  For instance,
   * the change may be immediate or it may be delayed until the next
   * release of the schedulable.  See the documentation for the
   * scheduler for details.
   *
   * <p> This method does not require that the schedulable be in
   * the feasibility set before it is called. When it is not initially a
   * member of the feasibility set it will be added when the resulting
   * system is feasible.
   *
   * @param pgp The processing group parameters to use. When
   *        {@code null}, the default value is governed by the
   *        associated scheduler (a new object is created when the default
   *        value is not {@code null}).  (See {@link
   *        PriorityScheduler}.)
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made.  False, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the parameter value is
   *         not compatible with the schedulable's scheduler.
   *         Also when this schedulable may not use the heap and the
   *         specified parameter object is located in heap memory.
   *
   * @throws IllegalAssignmentError when {@code this} cannot hold a
   *         reference to the specified parameter object, or the
   *         parameter object cannot hold a reference to {@code this}.
   *
   * @throws IllegalThreadStateException when the schedulable's
   *         scheduler prohibits the changing of the processing group
   *         parameter at this time due to the state of the schedulable
   *         object.
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate.
   */
  @Deprecated
  public boolean
    setProcessingGroupParametersIfFeasible(ProcessingGroupParameters pgp)
  {
    return false;
  }
}
