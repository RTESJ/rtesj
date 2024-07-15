/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This is the base class for all asynchronous event handlers, where
 * asynchronous is in regards to running code, not external time.  This class
 * unifies the original {@link AsyncEventHandler} with
 * {@link AsyncLongEventHandler} and {@link AsyncObjectEventHandler}.  Each of
 * these subclasses has its own {@code handleAsyncEvent} method, whose only
 * difference is whether and what argument it has.
 *
 * @since RTSJ 2.0
 */
public abstract class AsyncBaseEventHandler
  implements Schedulable
{
  private ReleaseRunner runner_;

  /**
   * Gets the last release time of this handler.
   *
   * @return a reference to a newly-created {@link AbsoluteTime} object
   * representing this handler's last release time.  When the handler has not
   * been released since it was last started, throws an exception.
   *
   * @throws StaticIllegalStateException when this handler has not been released
   * since it was last started.
   *
   * @since RTSJ 2.1
   */
   public AbsoluteTime getCurrentReleaseTime() { return null; }

  /*
   * Gets the last release time of this handler in the object provided.
   *
   * @return When {@code dest} is {@code null}, returns a
   * reference to a newly-created {@link AbsoluteTime} object
   * representing this handler's last release time.  When {@code dest}
   * is not null, sets {@code dest} to this handler's last release
   * time.  When the handler has not been released, returns
   * {@code null}.
   *
   * @param dest An object used to return the results
   *
   * @since RTSJ 2.1
   */
  public AbsoluteTime getCurrentReleaseTime(AbsoluteTime dest)
  {
    return null;
  }

  /*
   * Determines the CPU consumption for this release.  When {@code dest}
   * is {@code null}, returns the CPU consumption in an otherwise unused
   * {@link RelativeTime} instance in the current execution context.
   * Otherwise, when {@code dest} is not {@code null}, returns the CPU
   * consumption in {@code dest}
   *
   * @param dest When not {@code null}, the object in which to return
   *        the result.
   *
   * @return the time consumed in the current release.
   *
   * @throws StaticIllegalStateException when the caller is not a
   *         {@link Schedulable}.
   *
   * @since RTSJ 2.0 Inherited by AyncEventHandler
   *//*
  public RelativeTime getCurrentConsumption(RelativeTime dest)
    throws StaticIllegalStateException
  {
    return null;
  }

  /*
   * Equivalent to {@code getCurrentConsumption(null)}.
   *
   * @return the time consumed in the current release.
   *
   * @since RTSJ 2.0 Inherited by AyncEventHandler
   *//*
  public RelativeTime getCurrentConsumption()
  {
    return null;
  }*/


  AsyncBaseEventHandler(SchedulingParameters scheduling,
                        ReleaseParameters<?> release,
                        MemoryParameters memory,
                        MemoryArea area,
                        ProcessingGroupParameters pgp,
                        ReleaseRunner runner,
                        boolean noheap,
                        Object logic)
    throws StaticIllegalArgumentException, IllegalAssignmentError
  {
  }


  AsyncBaseEventHandler(SchedulingParameters scheduling,
                        ReleaseParameters<?> release,
                        MemoryParameters memory,
                        MemoryArea area,
                        ConfigurationParameters config,
                        ReleaseRunner runner,
                        Object logic)
    throws StaticIllegalArgumentException, IllegalAssignmentError
  {
  }

  /**
   * This is an accessor method for {@code fireCount}.  The
   * {@code fireCount} field nominally holds the number of times
   * associated instances of {@link AsyncEvent} have occurred that have
   * not had the method {@code handleAsyncEvent()} invoked.  It is
   * incremented and decremented by the implementation of the RTSJ.  The
   * application logic may manipulate the value in this field for
   * application-specific reasons.
   *
   * @return the value held by {@code fireCount}.
   */
  protected int getPendingFireCount() { return 0; }

  /**
   * This is an accessor method for {@code fireCount}.
   * This method atomically sets the value of {@code fireCount} to zero
   * and returns the value from before it was set to zero.
   * This may be used by handlers for which the logic can accommodate multiple
   * releases in a single execution.
   *
   * <p>The general form for using this is
   *
   * <pre>
   * public void handleAsyncEvent()
   * {
   *   int numberOfReleases = getAndClearPendingFireCount();
   *   &lt;handle the events&gt;
   * }
   * </pre>
   *
   * The effect of a call to {@code getAndClearPendingFireCount} on
   * the scheduling of this AEH depends on the semantics of the scheduler
   * controlling this AEH.
   *
   * @return the value held by {@code fireCount} prior to setting
   * the value to zero.
   */
  protected int getAndClearPendingFireCount() { return 0; }

  /**
   * This is an accessor method for {@code fireCount}.  This method
   * atomically decrements, by one, the value of {@code fireCount}
   * (when it is greater than zero) and returns the value from before the
   * decrement. This method can be used in the
   * {@code handleAsyncEvent()} method to handle multiple releases:<p>
   *
   * <pre>
   * public void handleAsyncEvent()
   * {
   *   &lt;setup&gt;
   *   do
   *     {
   *       &lt;handle the event&gt;
   *     }
   *   while(getAndDecrementPendingFireCount() &gt; 0);
   * }
   * </pre>
   *
   * <p>This construction is necessary only in cases where a handler
   * wishes to avoid the setup costs, since the framework guarantees that
   * {@code handleAsyncEvent()} will be invoked whenever the
   * {@code fireCount} is greater than zero.  The effect of a call
   * to {@code getAndDecrementPendingFireCount} on the scheduling
   * of this AEH depends on the semantics of the scheduler controlling
   * this AEH.
   *
   * @return the value held by {@code fireCount} prior to
   * decrementing it by one.
   */
  protected int getAndDecrementPendingFireCount() { return 0; }

  /**
   * This is an accessor method for the initial instance of {@link
   * MemoryArea} associated with {@code this}.
   *
   * @return the instance of {@link MemoryArea} which was passed as the
   * {@code area}  parameter when {@code this} was created (or
   * the default value when {@code area} was allowed to default.
   */
  public MemoryArea getMemoryArea() { return null; }

  @Override
  public MemoryParameters getMemoryParameters()
  {
    return null;
  }

  @Override
  public ReleaseParameters<?> getReleaseParameters()
  {
    return null;
  }

  @Override
  public Scheduler getScheduler()
  {
    return null;
  }

  @Override
  public SchedulingParameters getSchedulingParameters()
  {
    return null;
  }
  /*
  @Override
  public RealtimeThreadGroup getRealtimeThreadGroup()
  {
    return null;
  }
  */

  /**
   * Obtain the configuration parameters.
   *
   * @return the current configuration parameters.
   *
   * @since RTSJ 2.0
   */
  public ReleaseRunner getReleaseRunner() { return null; }

  /**
   * {@inheritDoc}
   *
   * @return the {@code ConfigurationParameters} instance of its
   *         {@link ReleaseRunner} instance, i.e.,
   *         {@code getReleaseRunner().getConfigurationParameters()}
   */
  @Override
  public ConfigurationParameters getConfigurationParameters()
  {
    return null;
  }

  @Override
  @ReturnsThis
  public Schedulable setMemoryParameters(MemoryParameters memory)
  {
    return this;
  }

  @Override
  @ReturnsThis
  public Schedulable setReleaseParameters(ReleaseParameters<?> release)
  {
    return this;
  }

  /**
   * {@inheritDoc}
   *
   * <p>For an instance of {@code AsyncBaseEventHandler}, the
   * {@code Schedulable} is <em>running</em> for the purpose of setting
   * the scheduler when it is attached to an {@code AsyncEvent}, even when
   * {@link AsyncBaseEvent#isRunning()} would return {@code false}
   * for that event.
   *
   * @param scheduler {@inheritDoc}
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException {@inheritDoc}
   *
   * @throws IllegalAssignmentError {@inheritDoc}
   *
   * @throws StaticSecurityException {@inheritDoc}
   *
   * @throws IllegalTaskStateException {@inheritDoc}
   *
   * @since RTSJ 2.0 returns itself
   */
  @Override
  @ReturnsThis
  public Schedulable setScheduler(Scheduler scheduler)
  {
    return this;
  }


  /**
   * {@inheritDoc}
   *
   * @param scheduler {@inheritDoc}
   * @param scheduling {@inheritDoc}
   * @param release {@inheritDoc}
   * @param memoryParameters {@inheritDoc}
   * @throws StaticIllegalArgumentException {@inheritDoc}
   * @throws IllegalAssignmentError {@inheritDoc}
   * @throws StaticSecurityException {@inheritDoc}
   * @since RTSJ 2.0
   */
  @Override
  @ReturnsThis
  public Schedulable setScheduler(Scheduler scheduler,
                                  SchedulingParameters scheduling,
                                  ReleaseParameters<?> release,
                                  MemoryParameters memoryParameters)
  {
    return this;
  }

  @Override
  @ReturnsThis
  public Schedulable setSchedulingParameters(SchedulingParameters scheduling)
  {
    return this;
  }

  @Override
  public final void setDaemon(boolean on) {}

  @Override
  public final boolean isDaemon() { return false; }

  /**
   * Determine whether or not this handler is attached to an event.
   *
   * @return {@code true} when it is attached to at least one event;
   *         {@code false} otherwise.
   */
  public boolean isArmed() { return false; }

  /**
   * Finds the current length of the event queue.  The event queue holds
   * the time and payload of all released events that are still outstanding.
   * The queue may have a length of zero.
   *
   * @return the queue length.
   *
   * @since RTSJ 2.0 Inherited by AyncEventHandler
   */
  public int getQueueLength() { return 0; }

  /*
   * Determines the minimum CPU consumption of all completed releases.
   * When {@code dest} is {@code null}, returns the CPU consumption in an
   * otherwise unused  {@link RelativeTime} instance in the current
   * execution context.  Otherwise, when {@code dest} is not {@code
   * null}, returns the CPU consumption in {@code dest}
   *
   * @param dest When not {@code null}, the object in which to return
   *        the result.
   *
   * @return the minimum time consumed in any release.
   *
   * @since RTSJ 2.1
   */
  /* @Override
  public RelativeTime getMinConsumption(RelativeTime dest)
  {
    return null;
    }*/

  /*
   * Same as {@link #getMinConsumption(RelativeTime)} with a null
   * argument.
   *
   * @return the minimum time consumed in any release.
   *
   * @since RTSJ 2.1
   */
  //@Override
  //public RelativeTime getMinConsumption() { return null; }


  /*
   * Determines the maximum CPU consumption of all completed releases.
   * When {@code dest} is {@code null}, returns the CPU consumption in an
   * otherwise unused  {@link RelativeTime} instance in the current
   * execution context.  Otherwise, when {@code dest} is not {@code null},
   * returns the CPU consumption in {@code dest}.
   *
   * @param dest When not {@code null}, the object in which to return
   *        the result.
   *
   * @return the maximum time consumed in any release.
   *
   * @since RTSJ 2.1
   */
  //@Override
  //public RelativeTime getMaxConsumption(RelativeTime dest) { return null; }

  /*
   * Same as {@link #getMaxConsumption(RelativeTime)} with a null
   * argument.
   *
   * @return the maximum time consumed in any release.
   *
   * @since RTSJ 2.1
   */
  // @Override
  //public RelativeTime getMaxConsumption() { return null;  }

  /**
   * Determines whether or not this {@code schedulable} may use the heap.
   *
   * @return {@code true} only when this {@code Schedulable} may allocate
   *         on the heap and may enter the {@code Heap}.
   */
  public boolean mayUseHeap()
  {
    return true;
  }

   @Override
   public boolean subsumes(Schedulable other) { return false; }


  /**
   * Calls {@code handleAsyncEvent} repeatedly until the fire count
   * reaches zero.  The method is only to be used by the infrastructure,
   * and should not be called by the application.  The {@code handleAsyncEvent}
   * method should be overridden instead.
   * <p>
   * The {@code handleAsyncEvent()} family of methods provides the
   * equivalent functionality to {@code Runnable.run()} for asynchronous
   * event handlers, including execution of the {@code logic} argument
   * passed to this object's constructor.  Applications should override
   * the  {@code handleAsyncEvent()} method instead of overwriting this method.
   */
  @Override
  public final void run() {}

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  @Override
  public ProcessingGroupParameters getProcessingGroupParameters()
  {
    return null;
  }

  /**
   * {@inheritDoc}
   *
   * @param pgp {@inheritDoc}
   *
   * @throws StaticIllegalArgumentException {@inheritDoc}
   *
   * @throws IllegalAssignmentError {@inheritDoc}
   *
   * @throws IllegalThreadStateException {@inheritDoc}
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  @Override
  public void setProcessingGroupParameters(ProcessingGroupParameters pgp)
  {
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
  @Override
  public boolean addToFeasibility() { return true; }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate
   */
  @Deprecated
  @Override
  public boolean removeFromFeasibility()
  {
    return true;
  }
}
