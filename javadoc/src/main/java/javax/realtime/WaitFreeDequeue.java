/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import javax.realtime.control.AsynchronouslyInterruptedException;

/**
 * A {@code WaitFreeDequeue} encapsulates a
 * {@code WaitFreeWriteQueue} and a {@code WaitFreeReadQueue}.
 * Each method on a {@code WaitFreeDequeue} corresponds to an
 * equivalent operation on the underlying
 * {@code WaitFreeWriteQueue} or {@code WaitFreeReadQueue}.
 *
 * <P>
 * <I>Incompatibility with V1.0:</I>
 * Three exceptions previously thrown by the constructor have been deleted from
 * the {@code throws} clause.  These are
 *
 * <UL>
 * <LI><code>java.lang.IllegalAccessException</code>,
 * <LI><code>java.lang.ClassNotFoundException</code>, and
 * <LI><code>java.lang.InstantiationException</code>.
 * </UL>
 *
 * <P> Including these exceptions on the {@code throws} clause was
 * an error.  Their deletion may cause compile-time errors in code using
 * the previous constructor.  The repair is to remove the exceptions
 * from the {@code catch} clause around the constructor invocation.
 *
 * <p> {@code WaitFreeDequeue} is one of the classes allowing
 * {@code NoHeapRealtimeThread}s and regular Java threads to
 * synchronize on an object without the risk of a
 * {@code NoHeapRealtimeThread} incurring Garbage Collector latency
 * due to priority inversion avoidance management.
 *
 * @deprecated as of RTSJ 1.0.1
 */
@Deprecated
public class WaitFreeDequeue
{
  /**
   * Constructs a queue, in {@code memory}, with an underlying
   * {@link WaitFreeWriteQueue} and {@link WaitFreeReadQueue}, each of
   * size {@code maximum}.
   *
   * <p> The {@code writer} and {@code reader} parameters, when
   * non-null, are checked to insure that they are compatible with the
   * {@code MemoryArea} specified by {@code memory} (when
   * non-null.)  When {@code memory} is {@code null} and both
   * Runnables are non-null, the constructor will select the nearest
   * common scoped parent memory area, or when there is no such scope it
   * will use immortal memory.  When all three parameters are
   * {@code null}, the queue will be allocated in immortal memory.
   *
   * <p> {@code reader} and {@code writer} are not necessarily
   * the only threads or schedulables that will access the queue;
   * moreover, there is no check that they actually access the queue at
   * all.
   *
   * <p> <em>Note</em> that the wait free queues' internal queues are
   * allocated in {@code memory}, but the memory area of the wait
   * free dequeue instance itself is determined by the current
   * allocation context.
   *
   * @param writer An instance of {@code Runnable} or {@code null}.
   *
   * @param reader An instance of {@code Runnable} or {@code null}.
   *
   * @param maximum Then maximum number of elements in the both the
   *        {@link WaitFreeReadQueue} and the {@link WaitFreeWriteQueue}.
   *
   * @param memory The {@link MemoryArea} in which internal elements are
   *       allocated.
   *
   * @throws MemoryScopeException when
   *          either {@code reader} or {@code writer}
   *          is non-null and the {@code memory} argument is not
   *          compatible with {@code reader} and
   *          {@code writer} with respect to the assignment
   *          and access rules for memory areas.
   *
   * @throws IllegalArgumentException When an argument holds an invalid
   *          value.  The {@code writer} argument must be
   *          {@code null}, a reference to a {@code Thread},
   *          or a reference to a schedulable (a
   *          {@code RealtimeThread}, or an
   *          {@code AsyncEventHandler}.)  The {@code reader}
   *          argument must be {@code null}, a reference to a
   *          {@code Thread}, or a reference to a schedulable
   *          object.  The {@code maximum} argument must be greater
   *          than zero.
   *
   * @throws InaccessibleAreaException when {@code memory} is a
   *                    scoped memory that is not on the caller's scope
   *                    stack.
   */
  public WaitFreeDequeue (Runnable writer,
                          Runnable reader,
                          int maximum,
                          MemoryArea memory)
  {
  }


  /**
   * An unsynchronized call of the {@code read()}
   * method of the underlying {@link WaitFreeReadQueue}.
   *
   * @return a {@code java.lang.Object}
   *          object read from {@code this}.
   *          When there are no elements
   *          in {@code this} then {@code null} is returned.
   */
  public Object nonBlockingRead()
  {
    return new Object();
  }

  /**
   * A synchronized call of the {@code write()} method of the
   * underlying {@link WaitFreeReadQueue}.  This call blocks on queue
   * full and waits until there is space in {@code this}.
   *
   * @param object The {@code java.lang.Object} to place in
   *        {@code this}.
   *
   * @throws MemoryScopeException when a memory access error
   *            or illegal assignment error would occur
   *            while storing {@code object} in the queue.
   *
   * @throws InterruptedException when the thread is interrupted by
   *         {@code interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   *
   * @since RTSJ 1.0.1 Return type changed from boolean to void because
   *      this method <em>always</em> returned {@code true}, and
   *      added InterruptedException.
   */
  public void blockingWrite(Object object) throws InterruptedException{
  }

  /**
   * An unsynchronized call of the {@code write()} method of the
   * underlying {@link WaitFreeWriteQueue}.
   * This call does not block on queue full.
   *
   * @param object The {@code Object} to attempt to place in
   *        {@code this}.
   *
   * @return {@code true} when {@code object} was inserted
   *       (i.e., the queue was not full), {@code false} otherwise.
   *
   * @throws MemoryScopeException when a memory access error
   *            or illegal assignment error would occur
   *            while storing {@code object} in the queue.
   */
  public boolean nonBlockingWrite(Object object)
  {
    return true;
  }

  /**
   * A synchronized call of the {@code read()} method of the
   * underlying {@link WaitFreeWriteQueue}.  This call blocks on queue
   * empty and will wait until there is an element in the queue to
   * return.
   *
   * @return the {@code java.lang.Object} read.
   *
   * @throws InterruptedException when the thread is interrupted by
   *         {@code interrupt()} or {@link
   *         AsynchronouslyInterruptedException#fire()} during the time
   *         between calling this method and returning from it.
   *
   * @since RTSJ 1.0.1 Added throws {@code InterruptedException}.
   */
  public Object blockingRead() throws InterruptedException
  {
    return new Object();
  }

  /**
   * When {@code this}'s underlying {@link WaitFreeWriteQueue} is
   * full, then overwrite with {@code object} the most recently
   * inserted element.  Otherwise this call is equivalent to
   * {@code nonBlockingWrite()}.
   *
   * @param object The object to be written.
   *
   * @return {@code true} when an element was overwritten;
   *      {@code false} when there as an empty element
   *      into which the write occurred.
   *
   * @throws MemoryScopeException when a memory access error
   *            or illegal assignment error would occur
   *            while storing {@code object} in the queue.
   */
  public boolean force(Object object)
  {
    return true;
  }
}
