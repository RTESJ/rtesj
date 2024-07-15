/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import javax.realtime.control.AsynchronouslyInterruptedException;

/**
 * A queue that can be non-blocking for producers.  The
 * {@code WaitFreeWriteQueue} class is intended for single-writer
 * multiple-reader communication, although it may also be used (with
 * care) for multiple writers.  A <em>writer</em> is generally an
 * instance {@code Schedulable} which may not use the heap, and the
 * <em>readers</em> are generally conventional Java threads or instances
 * of {@code Schedulable} which use the heap.  Communication is through
 * a bounded buffer of {@code Objects} that is managed
 * first-in-first-out.  The principal methods for this class are write
 * and read.
 *
 * <p>
 * <UL>
 * <LI> The {@code write} method appends a new element onto the queue.
 * It is not synchronized, and does not block when the queue is full (it
 * returns {@code false} instead).  Multiple writer threads or
 * schedulables are permitted, but when two or more threads intend
 * to write to the same {@code WaitFreeWriteQueue} they will need
 * to arrange explicit synchronization.
 * </li>
 *
 * <LI> The {@code read} method removes the oldest element from the queue.
 * It is synchronized, and will block when the queue is empty.
 * It may be called by more than one reader, in which case the different
 * callers will read different elements from the queue.
 * </UL>
 *
 * <p> {@code WaitFreeWriteQueue} is one of the classes enabling
 * schedulables which may not use the heap and regular Java threads to
 * synchronize on an object without the risk of the schedulable
 * incurring Garbage Collector latency due to priority inversion
 * avoidance management.
 *
 * <p>
 * <I>Incompatibility with V1.0:</I>
 * Three exceptions previously thrown by the constructor have been
 * deleted from the {@code throws} clause.  These are
 *
 * <UL>
 * <LI><code>java.lang.IllegalAccessException</code>,
 * <LI><code>java.lang.ClassNotFoundException</code>, and
 * <LI><code>java.lang.InstantiationException</code>.
 * </UL>
 *
 * <p>
 * Including these exceptions on the {@code throws} clause was an error.
 * Their deletion may cause compile-time errors in code using the
 * previous constructor.  The repair is to remove the exceptions from
 * the {@code catch} clause around the constructor invocation.
 *
 * @param <T> is the type of the object in this queue
 */
public class WaitFreeWriteQueue<T>
{
  /**
   * Constructs a queue in {@code memory} with an unsynchronized and
   * nonblocking {@code write()} method and a
   * synchronized and blocking {@code read()} method.
   * <p>
   * The {@code writer} and {@code reader} parameters, when non-null,
   * are checked to insure that they are compatible with the
   * {@code MemoryArea} specified by {@code memory} (when
   * non-null.)  When {@code memory} is {@code null} and both
   * Runnables are non-null, the constructor will select the nearest
   * common scoped parent memory area, or when there is no such scope it
   * will use immortal memory.  When all three parameters are
   * {@code null}, the queue will be allocated in immortal memory.
   * <p>
   * {@code reader} and {@code writer}
   *  are not necessarily the only threads or
   *  schedulables that will access the queues; moreover,
   * there is no check that they actually access the queue at all.
   * <p>
   * <em>Note,</em> the wait free queue's internal queue is allocated in
   * {@code memory}, but the memory area of the wait free queue
   * instance itself is determined by the current allocation context.
   *
   * @param writer An instance of {@code Schedulable} or {@code null}.
   *
   * @param reader An instance of {@code Schedulable} or {@code null}.
   *
   * @param maximum The maximum number of elements in the queue.
   *
   * @param memory The {@link MemoryArea} in which {@code this} and
   *        internal elements are allocated.
   *
   * @throws StaticIllegalArgumentException when an argument holds an
   *          invalid value.  The {@code writer} argument must be
   *          {@code null}, a reference to a {@code Thread},
   *          or a reference to a schedulable (a
   *          {@code RealtimeThread}, or an
   *          {@code AsyncEventHandler}.)  The {@code reader}
   *          argument must  be {@code null}, a reference to
   *          a {@code Thread}, or a reference to a schedulable.
   *          The {@code maximum} argument must be greater than zero.
   *
   * @throws MemoryScopeException when
   *          either {@code reader} or {@code writer}
   *          is non-null and the {@code memory} argument is not
   *          compatible with {@code reader} and
   *          {@code writer} with respect to the assignment
   *          and access rules for memory areas.
   *
   * @throws    InaccessibleAreaException when {@code memory}
   *                    is a scoped memory that is not on the caller's
   *                    scope stack.
   */
  public WaitFreeWriteQueue(Runnable writer,
                            Runnable reader,
                            int maximum,
                            MemoryArea memory)
    throws StaticIllegalArgumentException,
           MemoryScopeException,
           InaccessibleAreaException
  {
  }

  /**
   * Constructs a queue containing up to {@code maximum}
   * elements in {@code memory}.  The queue has an unsynchronized and
   * nonblocking {@code write()} method and a
   * synchronized and blocking {@code read()} method.
   * <p>
   * Equivalent to
   * {@code WaitFreeWriteQueue(null,null,maximum, memory)}
   *
   * @since RTSJ 1.0.1
   */
  public WaitFreeWriteQueue(int maximum, MemoryArea memory)
    throws StaticIllegalArgumentException, InaccessibleAreaException
  {
  }

  /**
   * Constructs a queue containing up to {@code maximum}
   * elements in immortal memory.  The queue has an unsynchronized and
   * nonblocking {@code write()} method and a
   * synchronized and blocking {@code read()} method.
   * <p>
   * Equivalent to
   * {@code WaitFreeWriteQueue(null,null,mximum, null)}
   *
   * @since RTSJ 1.0.1
   */
  public WaitFreeWriteQueue(int maximum)
    throws StaticIllegalArgumentException
  {
  }

  /**
   * Sets {@code this} to empty.
   */
  public void clear()
  {
  }

  /**
   * Queries the system to determine if {@code this} is empty.
   * <p>
   * <em>Note,</em> this method needs to be used with care since the state of the queue
   * may change while the method is in progress or after it has returned.
   *
   * @return {@code true}, when {@code this} is empty;
   *         {@code false}, when {@code this} is not empty.
   */
  public boolean isEmpty()
  {
    return true;
  }

  /**
   * Queries the system to determine if {@code this} is full.
   * <p>
   * <em>Note,</em> this method needs to be used with care since the state of the queue
   * may change while the method is in progress or after it has returned.
   *
   * @return {@code true}, when {@code this} is full;
   *         {@code false}, when {@code this} is not full.
   */
  public boolean isFull()
  {
    return true;
  }

  /**
   * A synchronized and possibly blocking operation on the queue.
   *
   * @return the {@code T} least recently written to the queue.
   * When {@code this} is empty, the calling schedulable blocks until
   * an element is inserted; when it is resumed, {@code read}
   * removes and returns the element.
   *
   * @throws InterruptedException when the thread is interrupted
   *         by {@code interrupt()} or {@link
   *         AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   * @since RTSJ 1.0.1 Throws {@code InterruptedException}
   *
   */
  public synchronized T read() throws InterruptedException
  {
    return null;
  }


  /**
   * Queries the queue to determine the number of elements in {@code this}.
   * <p>
   * <em>Note,</em> this method needs to be used with care since the
   * state of the queue may change while the method is in progress or
   * after it has returned.
   *
   * @return the number of positions in {@code this} occupied by
   *  elements that have been written but not yet read.
   */
  public int size()
  {
    return 0;
  }

  /**
   * Unconditionally inserts {@code value} into {@code this},
   * either in a vacant position or else overwriting the most recently
   * inserted element. The {@code boolean} result reflects whether,
   * at the time that {@code force()} returns, the position at
   * which {@code value} was inserted was vacant
   * ({@code false}) or occupied ({@code true}).
   *
   * @param value An instance of {@code T} to insert.
   *
   * @return {@code true} when {@code value} has overwritten
   * an element that was occupied when the function returns;
   * {@code false} otherwise (it has been inserted
   * into a position that was vacant when the function returns)
   *
   * @throws MemoryScopeException when a memory access error
   *            or illegal assignment error would occur
   *            while storing {@code value} in the queue.
   *
   * @throws StaticIllegalArgumentException when {@code value} is {@code null}.
   */
  public boolean force(T value)
    throws MemoryScopeException, StaticIllegalArgumentException
  {
    return true;
  }

  /**
   * Inserts {@code value} into {@code this} when
   * {@code this} is non-full and otherwise has no effect on
   * {@code this}; the {@code boolean} result reflects whether
   * {@code value} has been inserted. When the queue was empty and
   * one or more threads or schedulables were waiting to read,
   * then one will be awakened after the write. The choice of which to
   * awaken depends on the involved scheduler(s).
   *
   * @param value An instance of {@code T} to insert.
   *
   * @return {@code true} when the queue was non-full;
   *         {@code false} otherwise.
   *
   * @throws MemoryScopeException when a memory access error
   *            or illegal assignment error would occur
   *            while storing {@code value} in the queue.
   *
   * @throws StaticIllegalArgumentException when {@code value} is
   *         {@code null}.
   */
  public boolean write(T value)
    throws MemoryScopeException, StaticIllegalArgumentException
  {
    return true;
  }
}
