/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
/*
 * Created on Oct 22, 2009
 *
 * Copyright (C) 2004, 2005 TimeSys Corporation, All Rights Reserved.
 */
package javax.realtime;

/**
 * Provides a means of dispatching a set of {@link Releasable} instances.
 * It acts as if it contains a daemon {@code RealtimeThread} to perform this
 * task. The priority of this thread can be specified when a dispatcher object
 * is created.  The default dispatcher runs at the highest realtime
 * priority on the base scheduler.  Dispatchers do not maintain a queue of
 * pending event.
 *
 * <p> Application code cannot extend this class.
 *
 * @param <D> The type of dispatcher this class is.
 *
 * @param <T> The type of event to be dispatched.
 *
 * @since RTSJ 2.0
 */
public abstract
  class ActiveEventDispatcher<D extends ActiveEventDispatcher<D, T>,
                              T extends Releasable<T, D>>
{
  /**
   * Creates a new dispatcher. The thread is provided by the caller
   * and is specific to the type of dispatcher.  The scheduling of the
   * thread and its thread group are also provided by the caller.
   *
   * @param thread a realtime thread set up to run the dispatcher.
   *
   * @throws StaticIllegalArgumentException thread is {@code null}.
   */
  protected ActiveEventDispatcher(RealtimeThread thread)
    throws StaticIllegalStateException
  {
  }

  /**
   * Gets a reference to the {@link Scheduler} object for this
   * schedulable.
   *
   * @return a reference to the associated {@link Scheduler} object.
   */
  public Scheduler getScheduler() { return null; }

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
   */
  @ReturnsThis
  public ActiveEventDispatcher<D,T> setScheduler(Scheduler scheduler)
    throws StaticSecurityException, IllegalTaskStateException
  {
    return this;
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
   */
  @ReturnsThis
  public
    ActiveEventDispatcher<D,T> setScheduler(Scheduler scheduler,
                                            SchedulingParameters scheduling)
  {
    return this;
  }

  /**
   * Determines how the thread associated with this dispatcher is scheduled.
   *
   * @return the scheduling parameters of the dispatcher thread.
   */
  public SchedulingParameters getSchedulingParameters()
  {
    return null;
  }

  /**
   * Sets the scheduling parameters associated with this instance of
   * {@code Schedulable}.
   *
   * <p>
   * This change becomes effective under conditions determined by the
   * scheduler controlling the schedulable.  For instance, the
   * change may be immediate or it may be delayed until the next release
   * of the schedulable.  See the documentation for the scheduler
   * for details.
   *
   * @param scheduling A reference to the {@link SchedulingParameters}
   *        object.  When {@code null}, the default value is governed
   *        by the associated scheduler; a new object is created when the
   *        default value is not {@code null}.  (See
   *        {@link PriorityScheduler}.)
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
   *         scheduler or the intersection of affinity in {@code scheduling}
   *         and the affinity of {@code this} object's realtime thread group
   *         is empty, or when the affinity of {@code scheduling} is not contained
   *         in the affinity of the current {@code this} object's realtime thread group
   *         or when the affinity in {@code scheduling} is invalid.
   */
  @ReturnsThis
  public ActiveEventDispatcher<D,T> setSchedulingParameters(SchedulingParameters scheduling)
    throws IllegalTaskStateException,
           IllegalAssignmentError,
           StaticIllegalArgumentException
  {
    return this;
  }

  /**
   * Determines in which group the thread associated with this dispatcher is.
   *
   * @return the realtime thread group of the dispatcher thread.
   */
  public RealtimeThreadGroup getRealtimeThreadGroup()
  {
    return null;
  }

  /**
   * Optain the thread behind the dispatcher.
   *
   * @return the realtime thread behind the dispatcher.
   */
  protected RealtimeThread getThread()
  {
    return null;
  }

  /**
   * Test wether or not a given event is registered with this
   * dispatcher.
   *
   * @param event The event to test
   *
   * @return {@code true} when {@code event} is registered with this
   *         dispatcher.
   */
  public abstract boolean isRegistered(T event);

  /**
   * Registers an active event with this dispatcher.  Registering an
   * event prevents the event from being programmatically destroyed,
   * but it may not hold the dispatcher from being collected when the
   * dispatcher is in a more deeply nested scope.
   *
   * @param event The event to register
   *
   * @throws RegistrationException when {@code event} is already
   *         registered.
   *
   * @throws StaticIllegalStateException when this object has been destroyed.
   */
  protected abstract void register(T event)
    throws RegistrationException, StaticIllegalStateException;

  /**
   * Activate an active event registered with this
   * dispatcher.
   *
   * @param event The event to register
   *
   * @throws RegistrationException when {@code event} is not already
   * registered.
   *
   * @throws StaticIllegalStateException when {@code event} is
   *         stopped.
   */
  protected void activate(T event)
    throws RegistrationException, StaticIllegalArgumentException
  {
  }

  /**
   * Deregisters an active event from this dispatcher, breaking its
   * association with this dispatcher.  This should only happen when
   * an event is assoicated with another dispather.
   *
   * @param event The event to deregister
   *
   * @throws StaticIllegalStateException when this object has been
   * destroyed
   *
   * @throws StaticIllegalArgumentException when {@code event} is not
   *         stopped or is {@code null}.
   */
  protected abstract void deregister(T event)
    throws RegistrationException, StaticIllegalStateException;

  /**
   * Deactivate an active event registered with this dispatcher.
   *
   * @param event The event to deregister
   *
   * @throws DeregistrationException when {@code event} is not already
   *         registered.
   *
   * @throws StaticIllegalStateException when this object has been destroyed.
   *
   * @throws StaticIllegalArgumentException when {@code event} is not
   *         stopped or is {@code null}.
   */
  protected void deactivate(T event)
    throws DeregistrationException, StaticIllegalStateException,
           StaticIllegalArgumentException
  {
  }

  /**
   * Makes the dispatcher unusable.
   *
   * @throws StaticIllegalStateException when called on a dispatcher that has
   *         one or more registered objects.
   */
  public abstract void destroy() throws StaticIllegalStateException;
}
