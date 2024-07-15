/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A {@code NoHeapRealtimeThread} is a specialized form of
 * {@link RealtimeThread}.  Because an instance of
 * {@code NoHeapRealtimeThread} may immediately preempt any
 * implemented garbage collector, logic contained in its
 * {@code run()} is never allowed to allocate or reference any
 * object allocated in the heap.  At the byte-code level, it is illegal
 * for a reference to an object allocated in heap to appear on a
 * this realtime thread's operand stack.
 *
 * <p>Thus, it is always safe for a {@code NoHeapRealtimeThread} to
 * interrupt the garbage collector at any time, without waiting for the
 * end of the garbage collection cycle or a defined preemption
 * point. Due to these restrictions, a {@code NoHeapRealtimeThread}
 * object must be placed in a memory area such that thread logic may
 * unexceptionally access instance variables and such that Java methods
 * on {@code Thread}, e.g., enumerate and join, complete normally, except
 * where execution would cause access violations.  The constructors of
 * {@code NoHeapRealtimeThread} require a reference to
 * {@link ScopedMemory} or {@link ImmortalMemory}.
 *
 * <p>When the thread is started, all execution occurs in the scope of
 * the given memory area.  Thus, all memory allocation performed with
 * the <em>new</em> operator is taken from this given area.
 *
 * @deprecated since RTSJ 2.0
 */
@Deprecated
public class NoHeapRealtimeThread extends RealtimeThread
{
  /**
   * Creates a realtime thread with the given characteristics and a Runnable.
   * The thread group of the new thread is (effectively) null.
   * The newly-created realtime thread which may not use the heap is
   * associated with the scheduler in effect during execution of the
   * constructor.
   *
   * @param scheduling the SchedulingParameters associated with this (and
   *                    possibly other instances of Schedulable).  When
   *                    scheduling is {@code null}, the default is a copy of
   *                    the creator's scheduling parameters created in the
   *                    same memory area as the new NoHeapRealtimeThread.
   *
   * @param release the ReleaseParameters associated with this (and
   *                possibly other instances of Schedulable). When release
   *                is null, the default is a copy of the creator's
   *                ReleaseParameters created in the same memory area as
   *                the new NoHeapRealtimeThread.
   *
   * @param memory the MemoryParameters associated with this (and possibly
   *               other instances of Schedulable).  When memory is null, the
   *               new NoHeapRealtimeThread will have a null value for its
   *               MemoryParameters, and the amount or rate of memory
   *               allocation is unrestricted.
   *
   * @param area the MemoryArea associated with this. When area is null, an
   *             IllegalArgumentException is thrown.
   *
   * @param group the ProcessingGroupParameters associated with this (and
   *              possibly other instances of Schedulable).  When null, the
   *              new NoHeapRealtimeThread will not be associated with any
   *              processing group.
   *
   * @param logic the Runnable object whose run() method will serve as the
   *              logic for the new NoHeapRealtimeThread.  When logic is null,
   *              the run() method in the new object will serve as its logic.
   *
   * @throws IllegalArgumentException when the parameters are not compatible
   *                                  with the associated scheduler,
   *                                  when area is null,
   *                                  when area is heap memory,
   *                                  when area, scheduling release, memory
   *                                  or group is allocated in heap memory.
   *                                  when {@code this} is in heap memory,
   *                                  or when logic is in heap memory.
   *
   * @throws IllegalAssignmentError when the new NoHeapRealtimeThread instance
   *                                cannot hold references to non-null values
   *                                of the scheduling release, memory and group,
   *                                or when those parameters cannot hold a
   *                                reference to the new NoHeapRealtimeThread.
   */
  public NoHeapRealtimeThread(SchedulingParameters scheduling,
                              ReleaseParameters<?> release,
                              MemoryParameters memory,
                              MemoryArea area,
                              ProcessingGroupParameters group,
                              Runnable logic)
  {
  }

  /**
   * Creates a realtime thread which may not use the heap with the given
   * {@link SchedulingParameters}, {@link ReleaseParameters} and
   * {@link MemoryArea}, and default values for all other parameters.  This
   * constructor is equivalent to <code>NoHeapRealtimeThread(scheduling,
   * release, null, area, null, null, null)</code>.
   */
  public NoHeapRealtimeThread(SchedulingParameters scheduling,
                              ReleaseParameters<?> release,
                              MemoryArea area)
  {
  }


  /**
   * Creates a realtime thread with the given {@link SchedulingParameters}
   * and {@link MemoryArea} and default values for all other parameters.
   *
   * <p> This constructor is equivalent to
   * <code>NoHeapRealtimeThread(scheduling, null, null, area, null,
   * null, null)</code>.
   */
  public NoHeapRealtimeThread(SchedulingParameters scheduling, MemoryArea area)
  {
  }


  @Override
  public void start()
  {
  }

  /**
   * {@inheritDoc}
   *
   * @since RTSJ 2.0
   */
  @Override
  public void startPeriodic(PhasingPolicy phasingPolicy)
    throws LateStartException
  {
  }
}
