/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import javax.realtime.control.AsynchronouslyInterruptedException;
import javax.realtime.memory.ScopedMemory;

/**
 * Class {@code RealtimeThread} extends {@code Thread} and adds access
 * to realtime services such as advanced scheduling, affinity
 * management, asynchronous transfer of control, and access to scope
 * memory.
 *
 *<p> As with {@code java.lang.Thread}, there are two ways to create a
 *  {@code RealtimeThread}.
 *  <ul>
 *  <li>Create a new class that extends
 *  {@code RealtimeThread} and override the {@code run()} method with
 *  the logic for the thread.</li>
 *  <li>Create an instance of {@code RealtimeThread} using one of
 *  the constructors with a {@code logic} parameter.  Pass a
 *  {@code Runnable} object whose {@code run()} method
 *  implements the logic of the thread.</li>
 *  </ul>
 *
 *  Every RealtimeThread is a member of a {@link RealtimeThreadGroup}, and it
 *  is not possible to add a RealtimeThread from within a regular ThreadGroup.
 *
 *  @see RealtimeThreadGroup
 */
public class RealtimeThread
  extends Thread
  implements BoundSchedulable
{
  /**
   * Gets a reference to the current instance of {@code RealtimeThread}.
   *
   * <p> Calling {@code currentRealtimeThread} is permissible when
   * control is in an {@link AsyncEventHandler}. The method will
   * return a reference to the {@code RealtimeThread} supporting that
   * release of the async event handler.
   *
   * @return a reference to the current instance of {@code RealtimeThread}.
   *
   * @throws ClassCastException when the current execution context
   *                            is not an instance of {@link Schedulable}.
   */
  public static RealtimeThread currentRealtimeThread()
    throws ClassCastException
  {
    return new RealtimeThread();
  }

  /**
   * Gets a reference to the current instance of {@code Schedulable}.  It
   * behaves the same when the current thread is an instance of
   * {@code java.lang.Thread}, but otherwise it produces an instance of
   * {@link AsyncBaseEventHandler}.
   *
   * @return a reference to the current instance of {@code Schedulable}.
   *
   * @throws ClassCastException when the current execution context
   *                            is that of a conventional Java thread.
   */
  public static Schedulable currentSchedulable()
    throws ClassCastException
  {
    return null;
  }

  /**
   * Gets the absolute time of this thread's last release, whether
   * periodic or aperiodic.
   *
   * <p> The clock in the returned absolute time shall be the realtime
   * clock for aperiodic releases and the clock used for the periodic
   * release for periodic releases.
   *
   * @return the last release time in a new absolute time instance
   *         in the current memory area.
   *
   * @since RTSJ 2.0
   */
  public static AbsoluteTime getCurrentReleaseTime() { return null; }

  /**
   * Gets the absolute time of this thread's last release, whether
   * periodic or aperiodic.
   *
   * <p> The clock in the returned absolute time shall be the realtime
   * clock for aperiodic releases and the clock used for the periodic
   * release for periodic releases.
   *
   * @param dest, when not {@code null},
   *        contains the last release time
   *
   * @return the last release time in {@code dest}.  When
   *         {@code dest} is {@code null}, create a new
   *         absolute time instance in the current memory area.
   *
   * @since RTSJ 2.0
   */
  public static AbsoluteTime getCurrentReleaseTime(AbsoluteTime dest)
  {
    return null;
  }

  /*
   * Determines the CPU consumption for this release.
   *
   * @param dest, when not {@code null},
   *        contains the CPU consumption
   *
   * @return when {@code dest}
   *         is {@code null}, returns the CPU consumption in a
   *         {@link RelativeTime} instance created in the
   *         current execution context.
   *
   * @throws StaticIllegalStateException when the caller is not a
   *         {@link Schedulable}.
   *
   * @since RTSJ 2.0
   */
  /*static public RelativeTime getCurrentConsumption(RelativeTime dest)
  {
    return null;
    }*/

  /*
   * Determines the CPU consumption for this release.
   *
   * @return the CPU consumption in a {@link RelativeTime} instance
   *         created in the current execution context.
   *
   * @throws StaticIllegalStateException when the caller is not a
   *         {@link Schedulable}.
   *
   * @since RTSJ 2.1
   */
  //  public static RelativeTime getCurrentConsumption() { return null; }

  /**
   * Gets a reference to the {@link MemoryArea} object representing the
   * current allocation context.
   * For a task that is not an instance of {@link Schedulable}, the result
   * can only be heap or immortal memory.
   *
   * @return a reference to the {@link MemoryArea} object representing the
   * current allocation context.
   */
  public static MemoryArea getCurrentMemoryArea() { return null; }


  /**
   * A sleep method that is controlled by a generalized clock.  Since
   * the time is expressed as a {@link HighResolutionTime}, this method
   * is an accurate timer with nanosecond granularity.  The actual
   * resolution available for the clock and even the quantity it
   * measures depends on {@code clock}. The time base is the given
   * {@link Clock}.  The sleep time may be relative or absolute. When
   * relative, then the calling thread is blocked for the amount of time
   * given by {@code time}, and measured by {@code clock}.  When
   * absolute, then the calling thread is blocked until the indicated
   * value is reached by {@code clock}.  When the given absolute time
   * is less than or equal to the current value of {@code clock},
   * the call to sleep returns immediately.
   *
   * <p> Calling {@code sleep} is permissible when control is in
   * an {@link AsyncEventHandler}. The method causes the handler to
   * sleep.
   *
   * <p> This method must not throw {@code IllegalAssignmentError}.
   * It must tolerate {@code time} instances that may not be stored
   * in {@code this}.
   *
   * @param time The amount of time to sleep or the point in time at
   *             which to awaken.
   *
   * @throws InterruptedException when the thread is interrupted by
   *         {@link #interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   * @throws ClassCastException when the current execution context is
   *         not an instance of {@link Schedulable}.
   *
   * @throws StaticIllegalArgumentException when {@code time} is {@code null},
   *         when {@code time} is a relative time less than zero, or
   *         when the {@link Chronograph} of {@code time} is not a
   *         {@link Clock}.
   */
  public static void sleep(HighResolutionTime<?> time)
    throws InterruptedException,
           ClassCastException,
           StaticIllegalArgumentException
  {
  }


  /*
   * The same as {@link #sleep(HighResolutionTime)} except that it is not
   * interruptible.
   *
   * @param time An absolute or relative time until which to suspend.
   *
   * @throws ClassCastException when the current execution context is
   *         not an instance of {@link Schedulable}.
   *
   * @throws StaticIllegalArgumentException when {@code time} is {@code null},
   *         when {@code time} is a relative time less than zero, or
   *         when the {@link Chronograph} of {@code time} is not a
   *         {@link Clock}.
   *
   * @since RTSJ 2.0
   *//*
  public static void suspend(HighResolutionTime<?> time)
    throws ClassCastException, StaticIllegalArgumentException
  {
  }*/

  /*
   * Similar to {@link #sleep(HighResolutionTime)} except it performs a
   * busy wait by polling on the {@link Chronograph} associated with
   * {@code time} until {@code time} has been reached.  Note that
   * interaction with other tasks, scheduling considerations, and other
   * effects may reduce the frequency of polling for long delays, so an
   * application cannot assume that the associated {@code Chronograph}
   * will be polled as quickly as possible.
   *
   * @param time An absolute or relative time at which to stop spinning.
   *
   * @throws InterruptedException when the thread is interrupted by
   *         {@link #interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   * @throws ClassCastException when the current execution context is
   *         not an instance of {@link Schedulable}.
   *
   * @throws StaticIllegalArgumentException when {@code time} is {@code null},
   *         or when {@code time} is a relative time less than zero.
   *
   * @since RTSJ 2.x (save for later release)
   *//*
  public static void spin(HighResolutionTime<?> time)
    throws InterruptedException,
           ClassCastException,
           StaticIllegalArgumentException
  {
  }*/

  /*
   * The same as calling {@link #spin(HighResolutionTime)} with a relative
   * time to the default realtime clock, zero milliseconds, and {@code nanos}
   * nanoseconds, except no relative time object is necessary.
   *
   * @param nanos A relative number of nanoseconds to wait.
   *
   * @throws InterruptedException when the thread is interrupted by
   *         {@link #interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   * @throws ClassCastException when the current execution context is
   *         not an instance of {@link Schedulable}.
   *
   * @throws StaticIllegalArgumentException when {@code nanos} is less
   *         than zero.
   *
   * @since RTSJ 2.x (save for later release)
   *//*
  public static void spin(int nanos)
    throws InterruptedException,
           ClassCastException,
           StaticIllegalArgumentException
  {
  }*/

  /**
   * Causes the current realtime thread to delay until the next release.
   * (See {@link #release()}.)  Used by threads that have a reference to
   * either periodic or aperiodic {@link ReleaseParameters}. The first
   * release starts when {@code this} thread is released as a
   * consequence of the action of one of the {@link #start()} family of
   * methods.  Each time this method is called it will block until the
   * next release unless the thread is in a deadline miss condition.
   * In that case, the operation of {@code waitForNextRelease} is
   * controlled by this thread's scheduler.  (See {@link PriorityScheduler}.)
   *
   * @throws StaticIllegalStateException when
   *         {@code this} does not have a reference to a
   *         {@link ReleaseParameters} type of either
   *         {@link PeriodicParameters} or {@link AperiodicParameters}.
   *
   * @throws ClassCastException when the current thread is not an
   *         instance of {@code RealtimeThread}.
   *
   * @return either <code>false</code> when the thread is in a deadline miss
   *         condition or <code>true</code> otherwise.  When a deadline miss
   *         condition occurs is defined by its thread's scheduler.
   *
   * @since RTSJ 2.0
   */
  public static boolean waitForNextRelease()
    throws StaticIllegalStateException,
           ClassCastException
  {
    return true;
  }

  /**
   * Same as {@link #waitForNextRelease()} except it can throw an interrupted
   * exception
   *
   * @throws InterruptedException when the thread is interrupted
   *         by {@link #interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it and
   *         the {@link ReleaseParameters#isRousable()} on its release
   *         parameters returns {@code true}.
   *
   *         <p> An interrupt during
   *         {@code waitForNextPeriodInterruptible()} is treated as a
   *         release for purposes of scheduling. This is likely to
   *         disrupt proper operation of the periodic thread.  The
   *         timing behavior of the thread is unspecified until the
   *         state is reset by altering the thread's release
   *         parameters or the thread is no longer in a deadline miss state.
   *
   * @throws StaticIllegalStateException when
   *         {@code this} does not have a reference to a
   *         {@link ReleaseParameters} type of either
   *         {@link PeriodicParameters} or {@link AperiodicParameters}.
   *
   * @throws ClassCastException when the current thread is not an
   *         instance of {@code RealtimeThread}.
   *
   * @return either <code>false</code> when the thread is in a deadline miss
   *         condition or <code>true</code> otherwise.  When a deadline miss
   *         condition occurs is defined by its thread's scheduler.
   *
   * @since RTSJ 2.0
   */
  public static boolean waitForNextReleaseInterruptible()
    throws InterruptedException,
           StaticIllegalStateException,
           ClassCastException
  {
    return true;
  }


  @Override
  public boolean subsumes(Schedulable other) { return false; }


  /**
   * Gets the position of the initial memory area for the current
   * {@link Schedulable} in the memory area stack. Memory area stacks may
   * include inherited stacks from parent threads. The initial memory area of
   * a {@link RealtimeThread} or an {@link AsyncBaseEventHandler} is the
   * memory area specified in its constructor. The index of the initial memory
   * area in the initial memory area stack is a fixed property of a
   * {@code Schedulable}.
   *
   * @return the index into the initial memory area stack of
   * the initial memory area of the current {@code Schedulable}.
   *
   * @throws StaticIllegalStateException when the memory area stack of the
   * current {@code Schedulable} has changed from its initial
   * configuration and the memory area at the originally specified
   * initial memory area index is not the initial memory area, thus the
   * index is invalid.
   *
   * <p>This can only happen when the application uses the alternate memory
   * module and the initial memory area is a scoped memory area.  The following
   * is an example of an event handler that will throw this exception when its
   * initial memory area is a scoped memory area.
   *
   * <pre>
   * public void handleAsyncEvent()
   * {
   *   MemoryArea current = RealtimeThread.getCurrentMemoryArea();
   *   if (current instanceof ScopedMemory)
   *     {
   *       MemoryArea parent =
   *         ((ScopedMemory) current).getParent();
   *       parent.executeInArea(() -&gt;
   *       {
   *         ScopedMemory scope = new LTMemory(1000);
   *         scope.enter(() -&gt;
   *         {
   *           System.out.println("Initial Memory Area Index = " +
   *           RealtimeThread.getInitialMemoryAreaIndex());
   *          });
   *       });
   *     }
   * }
   * </pre>
   *
   * @throws ClassCastException when the current execution context is
   * not an instance of {@link Schedulable}.
   * An exception will be thrown on line 12, where the first opening bracket
   * is line one, of the handler above.
   *
   * @deprecated as of RTSJ 2.0.
   */
  @Deprecated
  public static int getInitialMemoryAreaIndex()
    throws StaticIllegalStateException, ClassCastException
  {
    return 0;
  }

  /**
   * Gets the size of the memory area stack of {@link MemoryArea} instances
   * to which the current schedulable has access.  For a realtime thread
   * started with immortal or heap as its explicit initial memory area,
   * the initial size is one.  The current memory area
   * ({@link #getCurrentMemoryArea()}) is found at memory area stack index of
   * {@code getMemoryAreaStackDepth() - 1}.
   *
   * @return the size of the stack of {@link MemoryArea} instances.
   *
   * @throws ClassCastException when the current execution context is
   *         not an instance of {@link Schedulable}.
   *
   * @deprecated as of RTSJ 2.0
   */
  @Deprecated
  public static int getMemoryAreaStackDepth()
    throws ClassCastException
  {
    return 0;
  }

  /**
   * Gets the instance of {@link MemoryArea} in the memory area stack
   * at the index given. When the given index does not exist in the
   * memory area stack, then {@code null} is returned.  For a thread
   * started with immortal or heap as its explicit initial memory area, the
   * index of that area is zero.  The current memory area
   * ({@link #getCurrentMemoryArea()}) is found at memory area stack index
   * {@code getMemoryAreaStackDepth() - 1}, so
   * {@code getCurrentMemoryArea() == getOutMemoryArea(getMemoryAreaStackDepth()
   * - 1)}.
   *
   * <p>Note that accessing the stack should have a maximum complexity of
   * {@code O(n}, where {@code n} is the stack depth.  This means the memory
   * stack need not be backed by an array.
   *
   * @param index The offset into the memory area stack.
   *
   * @return the instance of {@link MemoryArea} at index or {@code null}
   *         when the given index does not correspond to a position
   *         in the stack.
   *
   * @throws ClassCastException when the current execution context is
   *         not an instance of {@link Schedulable}.
   *
   * @throws MemoryAccessError when the memory area is allocated in heap
   *        memory and the caller is a schedulable that may not use the heap.
   */
  @Deprecated
  public static MemoryArea getOuterMemoryArea(int index)
    throws ClassCastException, MemoryAccessError
  {
    return null;
  }

  /**
   *  A sleep method that is controlled by a generalized clock.  Since
   * the time is expressed as a {@link HighResolutionTime}, this method
   * is an accurate timer with nanosecond granularity.  The actual
   * resolution available for the clock and even the quantity it
   * measures depends on {@code clock} associated with {@code time}. The
   * time base is the given {@link Clock} associated with {@code time}.
   * The sleep time may be relative or absolute. When relative, then the
   * calling thread is blocked for the amount of time given by {@code
   * time}, and measured by {@code clock}.  When absolute, then the
   * calling thread is blocked until the indicated value is reached by
   * {@code clock}.  When the given absolute time is less than or equal
   * to the current value of {@code clock}, the call to sleep returns
   * immediately.
   *
   * <p> Calling {@code sleep} is permissible when control is in
   * an {@link AsyncEventHandler}. The method causes the handler to
   * sleep.
   *
   * <p> This method must not throw {@code IllegalAssignmentError}.
   * It must tolerate {@code time} instances that may not be stored
   * in {@code this}.
   *
   * @param clock The instance of {@link Clock} used as the base. When
   *   {@code clock} is {@code null} the realtime clock (see
   *   {@link Clock#getRealtimeClock}) is used.  When {@code time}
   *   uses a time-base other than {@code clock}, {@code time}
   *   is reassociated with {@code clock} for purposes of this
   *   method.
   *
   * @param time The amount of time to sleep or the point in time at

   * which to awaken.
   *
   * @throws InterruptedException when the thread is interrupted by
   *         {@link #interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   * @throws ClassCastException when the current execution context is
   *         not an instance of {@link Schedulable}.
   *
   * @throws StaticUnsupportedOperationException when the
   *                 sleep operation is not supported
   *                 by {@code clock}.
   *
   * @throws StaticIllegalArgumentException when {@code time} is
   *                 {@code null}, or when {@code time} is a
   *                 relative time less than zero.
   *
   * @deprecated in RTSJ 2.0
   */
  @Deprecated
  public static void sleep(Clock clock, HighResolutionTime<?> time)
    throws InterruptedException, ClassCastException,
           StaticUnsupportedOperationException,
           StaticIllegalArgumentException
  {
  }

  /**
   * Causes the current realtime thread to delay until the beginning of
   * the next period.  Used by threads that have a reference to a {@link
   * ReleaseParameters} type of {@link PeriodicParameters} to block
   * until the start of each period.  The first period starts when
   * {@code this} thread is first released. Each time it is called
   * this method will block until the start of the next period unless
   * the thread is in a deadline miss condition.  In that case the
   * operation of {@code waitForNextPeriod()} is controlled by this
   * thread's scheduler.  (See {@link PriorityScheduler}.)
   *
   * @throws IllegalThreadStateException when {@code this} does not
   *         have a reference to a {@link ReleaseParameters} type of
   *         {@link PeriodicParameters}.
   *
   * @throws ClassCastException when the current thread is not an
   *         instance of {@code RealtimeThread}.
   *
   * @return either <code>false</code> when the thread is in a deadline miss
   *         condition or <code>true</code> otherwise.  When a deadline miss
   *         condition occurs is defined by its thread's scheduler.
   *
   * @since RTSJ 1.0.1 Changed from an instance method to a static method.
   *
   * @deprecated RTSJ 2.0 Replaced by {@link #waitForNextRelease()}
   */
  @Deprecated
  public static boolean waitForNextPeriod()
    throws ClassCastException, IllegalThreadStateException
  {
    return true;
  }

  /**
   * The {@code waitForNextPeriodInterruptible()} method is a
   * duplicate of {@link #waitForNextPeriod()} except that
   * {@code waitForNextPeriodInterruptible()} is able to throw
   * {@code InterruptedException}.
   *
   * <p> Used by threads that have a reference to a
   * {@link ReleaseParameters} type of {@link PeriodicParameters} to
   * block until the start of each period.  The first period starts when
   * {@code this} thread is first released. Each time it is called,
   * this method will block until the start of the next period unless
   * the thread is in a deadline miss condition.  In that case the
   * operation of {@code waitForNextPeriodInterruptible()} is
   * controlled by this thread's scheduler.  (See {@link PriorityScheduler})
   *
   * @throws InterruptedException when the thread is interrupted by
   *         {@link #interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   *         <p> An interrupt during
   *         {@code waitForNextPeriodInterruptible()} is treated as a
   *         release for purposes of scheduling. This is likely to
   *         disrupt proper operation of the periodic thread.  The
   *         periodic behavior of the thread is unspecified until the
   *         state is reset by altering the thread's periodic
   *         parameters.
   *
   * @throws ClassCastException when the current thread is not an
   *         instance of {@code RealtimeThread}.
   *
   * @throws IllegalThreadStateException when {@code this} does not
   *         have a reference to a {@link ReleaseParameters} type of
   *         {@link PeriodicParameters}.
   *
   * @return either <code>false</code>, when the thread is in a
   *         deadline miss condition, or <code>true</code>, otherwise.
   *         The time at which a deadline miss condition occurs is
   *         defined by its thread's scheduler.
   *
   * @since RTSJ 1.0.1
   *
   * @deprecated RTSJ 2.0 Replaced by {@link #waitForNextReleaseInterruptible}
   */
  @Deprecated
  public static boolean waitForNextPeriodInterruptible()
    throws InterruptedException, ClassCastException, IllegalThreadStateException
  {
    return true;
  }


  /**
   * Creates a realtime thread with the given characteristics and a
   * specified {@code Runnable}.  The realtime thread group of the new
   * thread is inherited from its parent task unless {@code group} is set.
   * The newly-created realtime thread is associated with the
   * scheduler in effect during execution of the constructor.
   *
   * @param scheduling The {@link SchedulingParameters} associated with
   *        {@code this} (And possibly other instances of {@link
   *        Schedulable}).  When {@code scheduling} is
   *        {@code null} and the creator is a schedulable,
   *        {@link SchedulingParameters} is a clone of the creator's
   *        value created in the same memory area as {@code this}.
   *        When {@code scheduling} is {@code null} and the
   *        creator is a Java thread, the contents and type of the new
   *        {@code SchedulingParameters} object is governed by the
   *        associated scheduler.
   *
   * @param release The {@link ReleaseParameters} associated with
   *        {@code this} (and possibly other instances of
   *        {@link Schedulable}).  When {@code release} is {@code null}
   *        the new {@code RealtimeThread} will use a clone of the
   *        default {@code ReleaseParameters} for the associated
   *        scheduler created in the memory area that contains the
   *        {@code RealtimeThread} object.
   *
   * @param memory The {@link MemoryParameters} associated with
   *               {@code this} (and possibly other instances of
   *               {@link Schedulable}).  When {@code memory} is
   *               {@code null}, the new
   *               {@code RealtimeThread} receives
   *               {@code null} value for its memory parameters,
   *               and the amount or rate of memory allocation for the
   *               new thread is unrestricted, and it may access the heap.
   *
   * @param area The initial {@link MemoryArea} of this handler.
   *
   * @param config The {@link ConfigurationParameters} associated
   *               with {@code this} (and possibly other instances
   *               of {@link Schedulable}).  When {@code config} is
   *               {@code null}, this {@code RealtimeThread}
   *               will reserve no space for preallocated exceptions and
   *               implementation-specific values will be set to their
   *               implementation-defined defaults.
   *
   * @param group The {@link RealtimeThreadGroup} of the newly created
   *              realtime thread or the parent's realtime thread group
   *              when null.
   *
   * @param logic The {@code Runnable} object whose
   *        {@code run()} method will serve as the logic for the
   *        new {@code RealtimeThread}.  When {@code logic} is
   *        {@code null}, the {@code run()} method in the new
   *        object will serve as its logic.
   *
   * @throws StaticIllegalArgumentException when the parameters are not
   *         compatible with the associated scheduler.
   *
   * @throws IllegalAssignmentError when the new
   *         {@code RealtimeThread} instance cannot hold a
   *         reference to any of the values of {@code scheduling},
   *         {@code release}, {@code memory}, or {@code group},
   *         when those parameters cannot hold a
   *         reference to the new {@code RealtimeThread},
   *         when the new {@code RealtimeThread} instance cannot
   *         hold a reference to the values of {@code area} or
   *         {@code logic}, when the initial memory area is not specified
   *         and the new {@code RealtimeThread}
   *         instance cannot hold a reference to the default initial
   *         memory area, and
   *         when the thread may not use the heap, as specified by its
   *         memory parameters, and any of the following is true:
   *         <ul>
   *         <li>the initial memory area is not specified,</li>
   *         <li>the initial memory is heap memory,</lI>
   *         <li>the initial memory area, scheduling, release, memory,
   *         or group is allocated in heap memory.</lI>
   *         <li>when this is in heap memory, or</lI>
   *         <li>logic is in heap memory.</lI>
   *         </ul>
   *
   * @throws ScopedCycleException when {@code memory} is a scoped memory area
   *         that has already been entered from a memory area other than the
   *         current scope.
   *
   * @throws StaticIllegalStateException when the {@code ThreadGroup} of
   *         the calling thread is not an instance of
   *         {@link RealtimeThreadGroup} and the argument is {@code null}.
   *
   * @since RTSJ 2.0
   */
  public RealtimeThread(SchedulingParameters scheduling,
                        ReleaseParameters<?> release,
                        MemoryParameters memory,
                        MemoryArea area,
                        ConfigurationParameters config,
                        RealtimeThreadGroup group,
                        Runnable logic)
  {
  }


  /**
   * Creates a realtime thread with the given {@link SchedulingParameters},
   * {@link ReleaseParameters}, {@link MemoryArea}, and
   * {@link ConfigurationParameters} with default values
   * for all other parameters.
   *<p>
   * This constructor is equivalent to {@code RealtimeThread(scheduling,
   * release, null, area, config, null, null, null)}.
   *
   * @since RTSJ 2.0
   */
  public RealtimeThread(SchedulingParameters scheduling,
                        ReleaseParameters<?> release,
                        MemoryArea area,
                        ConfigurationParameters config)
  {
    this(scheduling, release, null, area, config, null, null);
  }


  /**
   * Creates a realtime thread with the given {@link SchedulingParameters},
   * {@link ReleaseParameters}, {@link MemoryArea} and a specified
   * {@code Runnable} and default values for all other parameters.
   * <p>
   * This constructor is equivalent to
   * {@link #RealtimeThread(SchedulingParameters, ReleaseParameters,
   * MemoryParameters, MemoryArea, ConfigurationParameters,
   * RealtimeThreadGroup, Runnable)}
   * with values {@code scheduling, release, null, null, config, null,
   * null, logic}.
   *
   * @since RTSJ 2.0
   */
  public RealtimeThread(SchedulingParameters scheduling,
                        ReleaseParameters<?> release,
                        ConfigurationParameters config,
                        Runnable logic)
  {
    this(scheduling, release, null, null, config, null, logic);
  }

  /**
   * Creates a realtime thread with the given {@link SchedulingParameters},
   * {@link ReleaseParameters} and {@link MemoryArea}
   * and default values for all other parameters.
   *<p>
   * This constructor is equivalent to
   * {@code RealtimeThread(scheduling, release, null, null, config, null, null)}.
   *
   * @since RTSJ 2.0
   */
  public RealtimeThread(SchedulingParameters scheduling,
                        ReleaseParameters<?> release,
                        ConfigurationParameters config)
  {
    this(scheduling, release, null, null, config, null, null);
  }

  /**
   * Creates a realtime thread with the given {@link SchedulingParameters},
   * {@link ReleaseParameters} and a specified {@code Runnable}
   * and default values for all other parameters.
   *<p>
   * This constructor is equivalent to
   * {@code RealtimeThread(scheduling, release, null, null, null, null, logic)}.
   *
   * @since RTSJ 2.0
   */
  public RealtimeThread(SchedulingParameters scheduling,
                        ReleaseParameters<?> release,
                        Runnable logic)
  {
    this(scheduling, release, null, null, null, null, logic);
  }


  /**
   * Creates a realtime thread with the given {@link SchedulingParameters}
   * and {@link ReleaseParameters}
   * and default values for all other parameters.
   *<p>
   * This constructor is equivalent to
   * {@code RealtimeThread(scheduling, release, null, null, null, null, null)}.
   */
  public RealtimeThread(SchedulingParameters scheduling,
                        ReleaseParameters<?> release)
  {
    this(scheduling, release, null, null, null, null, null);
  }

  /**
   * Creates a realtime thread with the given {@link SchedulingParameters}
   * and default values for all other parameters.
   * <p>This constructor is equivalent to
   * {@code RealtimeThread(scheduling, null, null, null, null, null, null)}.
   */
  public RealtimeThread(SchedulingParameters scheduling)
  {
    this(scheduling, null, null, null, null, null, null);
  }

  /**
   * Creates a realtime thread with default values for all parameters.
   * This constructor is equivalent to
   * {@code RealtimeThread(null, null, null, null, null, null, null)}.
   */
  public RealtimeThread()
  {
    this(null, null, null, null, null, null, null);
  }

  /**
   * Obtains the initial memory area for this {@code RealtimeThread}.
   * When not specified through the constructor, the default is a
   * <em>reference</em> to the current allocation context when {@code this}
   * was constructed.
   *
   * @return a reference to the initial memory area for this thread.
   *
   * @since RTSJ 1.0.1
   */
  public MemoryArea getMemoryArea()  { return null; }


  @Override
  public MemoryParameters getMemoryParameters()  { return null; }

  /*
  @Override
  public RealtimeThreadGroup getRealtimeThreadGroup()
  {
    return null;
  }
  */

  @Override
  public ConfigurationParameters getConfigurationParameters()
  {
    return null;
  }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   */
  @Override
  public ReleaseParameters<?> getReleaseParameters()  { return null; }


  @Override
  public Scheduler getScheduler()  { return null; }

  @Override
  public SchedulingParameters getSchedulingParameters() { return null; }

  /**
   * Generates a release for this {@code RealtimeThread}.  The
   * action of this release is governed by the scheduler.  It may, for
   * instance, act immediately, or be queued, delayed, or discarded.
   * This method does not suspend itself and has a runtime complexity
   * of {@code O(1)}.
   *
   * @throws StaticIllegalStateException when {@code this} does not
   *         have a reference to a {@link ReleaseParameters} type of
   *         {@link AperiodicParameters}.
   *
   * @since RTSJ 2.0
   */
  public void release() {}

  @Override
  public void interrupt() {}

  @Override
  public boolean isInterrupted() { return false; }

  /**
   * Performs any <em>deschedule</em> actions specified by this thread's
   * scheduler, either immediately when in {@link #waitForNextRelease()}
   * or the next time the thread enters {@code waitForNextRelease()}.
   *
   * @since RTSJ 2.0
   */
  public void deschedule() {}

  /**
   * Gets the thread to the blocked-for-next-release state.  This causes
   * the next event to release the thread and {@link #waitForNextRelease} to
   * return.  Deadline miss and cost enforcement are re-enabled.
   *
   * <p>The details of the interaction of this method with
   * {@link #deschedule}, {@link #waitForNextRelease} and {@link #release}
   * are dictated by this thread's scheduler.
   *
   * @throws IllegalTaskStateException when the configured
   *         {@code Scheduler} and {@code SchedulingParameters} for this
   *         {@code RealtimeThread} are not compatible.
   *
   * @since RTSJ 2.0
   */
  public void reschedule() throws IllegalTaskStateException
  {
  }


  /**
   * Starts the periodic thread with the specified phasing policy.
   *
   * @param phasingPolicy The phasing policy to be applied when the start
   *        time given in the realtime thread's associated
   *        {@link PeriodicParameters} is in the past.
   *
   * @throws javax.realtime.LateStartException when the actual start
   *        time is after the assigned start time and the phasing policy
   *        is {@link PhasingPolicy#STRICT_PHASING}.
   *
   * @throws IllegalTaskStateException when the configured {@code Scheduler}
   *         and {@code SchedulingParameters} for this
   *         {@code RealtimeThread} are not compatible or the thread is
   *         does not have periodic parameters with an absolute start time.
   *
   * @since RTSJ 2.0
   */
  public void startPeriodic(PhasingPolicy phasingPolicy)
    throws LateStartException, IllegalTaskStateException
  {
  }

  /**
   * Sets up the realtime thread's environment and starts it.  The set up
   * might include delaying it until the assigned start time and initializing
   * the thread's memory area stack. (See {@link ScopedMemory}.)
   *
   * It is never legal to start a thread more than once.  In
   * particular, a thread may not be restarted once it has completed
   * execution.
   *
   * @throws StaticIllegalStateException when the configured {@code Scheduler}
   *         and {@code SchedulingParameters} for this
   *         {@code RealtimeThread} are not compatible.
   *
   * @throws IllegalTaskStateException when the affinity of this
   *         RealtimeThread is not compatible with the affinity of the
   *         {@link RealtimeThreadGroup} it belongs.
   *
   * @throws IllegalThreadStateException when the thread is already started.
   *
   * @since RTSJ 2.0 adds new exceptions
   */
  @Override
  public void start() throws StaticIllegalStateException {}

  /**
   * Equivalent to {@code getEffectiveStartTime(null)}.
   *
   * @since RTSJ 2.0
   */
  public AbsoluteTime getEffectiveStartTime() { return null; }

  /**
   * Determines the effective start time of this realtime thread.  This is
   * not necessarily the same as the start time in the release
   * parameters.
   *
   * <ul>
   * <li>
   * When the release parameters' start time is relative, the effective
   * start time is the time of the first release.</li>
   * <li>
   * When the release parameters' start time is an absolute time after
   * start() is invoked, the effective start time is the same as the
   * release parameters' start time.</li>
   * <li>
   * When the release parameters' start time is an absolute time before
   * start() is invoked, the effective start time depends on the phasing
   * policy.</li>
   * </ul>
   * The default is to set the effective start time equal to the time start()
   * is invoked.
   *
   * @return the effective start time in {@code dest}.  When
   *         {@code dest} is {@code null}, returns the
   *         effective start time in an {@link AbsoluteTime} instance
   *         created in the current memory area.
   *
   * @since RTSJ 2.0
   */
  public AbsoluteTime getEffectiveStartTime(AbsoluteTime dest) { return null; }

  /*
   * Gets the minimum CPU consumption measured for any completed release
   * of this schedulable.
   *
   * @return the minimum CPU consumption in {@code dest}. When
   *         {@code dest} is {@code null}, it returns the minimum
   *         CPU consumption in a {@link RelativeTime} instance created
   *         in the current memory area.
   *
   * @throws StaticIllegalStateException when the caller is not a
   *         {@link Schedulable}.
   *
   * @since RTSJ 2.0
   */
  //@Override
  //public RelativeTime getMinConsumption(RelativeTime dest) { return null; }

  /*
   * Equivalent to {@code getMinConsumption(null)}.
   *
   * @since RTSJ 2.1
   */
  //@Override
  //public RelativeTime getMinConsumption() { return null; }

  /*
   * Gets the maximum CPU consumption measured for any completed release
   * of this schedulable.
   *
   * @return the maximum CPU consumption in {@code dest}. When
   *         {@code dest} is {@code null}, it returns the maximum
   *         CPU consumption in a {@link RelativeTime} instance created
   *         in the current memory area.
   *
   * @throws StaticIllegalStateException when the caller is not a
   *         {@link Schedulable}.
   *
   * @since RTSJ 2.1
   */
  //@Override
  //public RelativeTime getMaxConsumption(RelativeTime dest) { return dest; }

  /*
   * Equivalent to {@code getMaxConsumption(null)}.
   *
   * @since RTSJ 2.1
   */
  //@Override
  //public RelativeTime getMaxConsumption() { return null; }

  /**
   * Determines whether or not this {@code schedulable} may use the heap.
   *
   * @return {@code true} only when this {@code Schedulable} may allocate
   *         on the heap and may enter {@code HeapMemory}.
   *
   * @since RTSJ 2.0
   */
  public boolean mayUseHeap()
  {
    return true;
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
   * <p>For an instance of {@code RealtimeThread}, the {@code Schedulable}
   * is <em>running</em> when {@link RealtimeThread#start()}
   * has been called on it and {@code RealtimeThread.join()} would
   * block.
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
  public Schedulable setScheduler(Scheduler scheduler) { return this; }


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
  public synchronized
    Schedulable setSchedulingParameters(SchedulingParameters scheduling)
  {
    return this;
  }

  /**
   * Creates a realtime thread with the given characteristics and a Runnable.
   * This is equivalent to <code>RealtimeThread(scheduling, release, memory,
   * area, null, group, null, null, logic)</code>.
   *
   * @throws StaticIllegalStateException when the {@code ThreadGroup} of
   *         the calling thread is not an instance of
   *         {@link RealtimeThreadGroup}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public RealtimeThread(SchedulingParameters scheduling,
                        ReleaseParameters<?> release,
                        MemoryParameters memory,
                        MemoryArea area,
                        ProcessingGroupParameters group,
                        Runnable logic)
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
  public boolean addIfFeasible()  { return true; }

  /**
   * {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
  public boolean addToFeasibility()  { return true; }

  /**
   * When the {@link ReleaseParameters} object associated with
   * {@code this} {@code RealtimeThread} is an instance of
   * {@link PeriodicParameters}, performs any {@code deschedulePeriodic}
   * actions specified by this thread's scheduler.
   *
   * When the type of the associated instance of {@link ReleaseParameters}
   * is not {@link PeriodicParameters} nothing happens.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public void deschedulePeriodic() {}

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
   * @return {@inheritDoc}
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
    @Override
  public boolean removeFromFeasibility()  { return true; }

  /**
   * Begins unblocking {@link RealtimeThread#waitForNextPeriod()} for a
   * periodic thread.  When deadline miss detection is disabled, enables
   * it.  Typically used when a periodic schedulable is in a
   * deadline miss condition.  <p>The details of the interaction of this
   * method with {@link #deschedulePeriodic()} and {@link #waitForNextPeriod()}
   * are dictated by this thread's scheduler.
   *
   * <p> When this {@code RealtimeThread} does not have a type of
   * {@link PeriodicParameters} as its {@link ReleaseParameters} nothing
   * happens.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public void schedulePeriodic() {}

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
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   *             is inadequate.
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
   * @param group The processing group parameters to use. When
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
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is
   *             inadequate.
   */
  @Deprecated
  public boolean setIfFeasible(ReleaseParameters<?> release,
                               MemoryParameters memory,
                               ProcessingGroupParameters group)
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
   * @param group The processing group parameters to use. When
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
                               ProcessingGroupParameters group)
  {
    return  true;
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
   * @param group The processing group parameters to use. When
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
                               ProcessingGroupParameters group)
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
   * {@inheritDoc}
   *
   * @param group {@inheritDoc}
   *
   * @throws StaticIllegalArgumentException {@inheritDoc}
   *
   * @throws IllegalAssignmentError {@inheritDoc}
   *
   * @throws IllegalThreadStateException {@inheritDoc}
   *
   * @deprecated
   */
  @Deprecated
  @Override
  public void setProcessingGroupParameters(ProcessingGroupParameters group) {}

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
   * @param group The processing group parameters to use. When
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
  setProcessingGroupParametersIfFeasible(ProcessingGroupParameters group)
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
    return true;
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
   *        default value is not {@code null}. See {@link PriorityScheduler}.
   *
   * @param release A reference to the {@link ReleaseParameters} which
   *        will be associated with {@code this}.  When
   *        {@code null}, the default value is governed by
   *        {@code scheduler}; a new object is created when the
   *        default value is not {@code null}.  See {@link PriorityScheduler}.
   *
   * @param memoryParameters A reference to the {@link MemoryParameters}
   *      which will be associated with {@code this}.  When
   *      {@code null}, the default value is governed by
   *      {@code scheduler}; a new object is created when the default
   *      value is not {@code null}.  (See {@link PriorityScheduler}.)
   *
   * @param group A reference to the {@link ProcessingGroupParameters}
   *        which will be associated with {@code this}.  When
   *        {@code null}, the default value is governed by
   *        {@code scheduler}; a new object is created when the
   *        default value is not {@code null}.  (See
   *        {@link PriorityScheduler}.)
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
   * @throws IllegalThreadStateException when {@code scheduler}
   *          prohibits the changing of the scheduler or a parameter at
   *          this time due to the state of the schedulable.
   *
   * @throws StaticSecurityException when the caller is not permitted to set
   *         the scheduler for this schedulable.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public void setScheduler(Scheduler scheduler,
                           SchedulingParameters scheduling,
                           ReleaseParameters<?> release,
                           MemoryParameters memoryParameters,
                           ProcessingGroupParameters group)
  {
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
}
