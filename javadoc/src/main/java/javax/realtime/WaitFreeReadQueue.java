/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import javax.realtime.control.AsynchronouslyInterruptedException;

/**
 * A queue that can be non-blocking for consumers.  The
 * {@code WaitFreeReadQueue} class is intended for single-reader
 * multiple-writer communication, although it may also be used (with
 * care) for multiple readers.  A <em>reader</em> is generally an
 * instance of {@code Schedulable} which may not use the heap, and the
 * <em>writers</em> are generally regular Java threads or heap-using
 * instances of {@code Schedulable}.  Communication is through a bounded
 * buffer of Objects that is managed first-in-first-out.  The principal
 * methods for this class are write and read.
 *
 * <UL>
 * <LI>
 * The {@code write} method appends a new element onto the queue.
 * It is synchronized, and blocks when the queue is full.
 * It may be called by more than one writer,
 * in which case, the different callers will write to different
 * elements of the queue.
 * <LI>
 * The {@code read} method removes the oldest element from the queue.
 * It is not synchronized and does not block;
 * it will return {@code null} when the queue is empty. Multiple reader
 * threads or schedulables are permitted, but when two or more intend to
 * read from the same {@code WaitFreeWriteQueue} they will need to
 * arrange explicit synchronization.
 * </UL>
 *
 * For convenience, and to avoid requiring a reader to poll until the
 * queue is non-empty, this class also supports instances
 * that can be accessed
 * by a reader that blocks on queue empty.  To obtain this behavior,
 * the reader needs to invoke the {@code waitForData()} method on a
 * queue that has been constructed with a {@code notify} parameter
 * set to {@code true}.
 *
 * <P> {@code WaitFreeReadQueue} is one of the classes enabling
 * instances of {@code Schedulable} that may not use the heap and
 * conventional Java threads to synchronize on an object without the risk of
 * that Schedulable instance incurring Garbage Collector latency due to
 * priority inversion avoidance management.
 *
 * <p><I>Incompatibility with V1.0:</I>
 * Three exceptions previously thrown by the constructor have been
 * deleted. These are
 *
 * <UL>
 * <LI><code>java.lang.IllegalAccessException</code>,
 * <LI><code>java.lang.ClassNotFoundException</code>, and
 * <LI><code>java.lang.InstantiationException</code>.
 * </UL>
 *
 * These exceptions were in error. Their deletion may cause compile-time
 * errors in code using the previous constructor.  The repair is to
 * remove the exceptions from the catch clause around the constructor
 * invocation.
 *
 * @param <T> the type of the object in this queue
 */
public class WaitFreeReadQueue<T>
{
  /**
   * Constructs a queue containing up to {@code maximum}
   * elements in {@code memory}.  The queue has an unsynchronized and
   * nonblocking {@code read()} method and a
   * synchronized and blocking {@code write()} method.
   * <p>
   * The {@code writer} and {@code reader} parameters, when non-null,
   * are checked to insure that they are compatible with the
   * {@code MemoryArea} specified by {@code memory} (when
   * non-null.)  When {@code memory} is {@code null} and both
   * Runnables are non-null, the constructor will select the
   * nearest common scoped parent memory area, or
   * when there is no such scope it will use immortal memory.
   * When all three parameters are {@code null}, the queue will be
   * allocated in immortal memory.
   *
   * <p>
   * {@code reader} and {@code writer} are not necessarily the only instances
   * of {@code Schedule} that will access the queue; moreover,
   * there is no check that they actually access the queue at all.
   *
   * <p>
   * Note that the wait free queue's internal queue is allocated in
   * {@code memory}, but the memory area of the wait free queue
   * instance itself is determined by the current allocation context.
   *
   * @param writer An instance of {@code Runnable} or {@code null}.
   *
   * @param reader An instance of {@code Runnable} or {@code null}.
   *
   * @param maximum The maximum number of elements in the queue.
   *
   * @param memory The {@link MemoryArea} in which internal elements are
   *          allocated.
   * @param notify A flag that establishes
   *          whether a reader is notified when the queue becomes non-empty.
   *
   * @throws StaticIllegalArgumentException when an argument holds an invalid value.
   *          The {@code writer} argument must be {@code null}, a reference to
   *          a {@code Thread}, or a reference to a schedulable
   *          (a {@code RealtimeThread}, or an {@code AsyncEventHandler}.)
   *          The {@code reader} argument must  be {@code null}, a reference to
   *          a {@code Thread}, or a reference to a schedulable.
   *          The {@code maximum} argument must be greater than zero.
   *
   * @throws InaccessibleAreaException when {@code memory} is a scoped
   *         memory that is not on the caller's scope stack.
   *
   * @throws MemoryScopeException when
   *          either {@code reader} or {@code writer}
   *          is non-null and the {@code memory} argument is not
   *          compatible with {@code reader} and
   *          {@code writer} with respect to the assignment
   *          and access rules for memory areas.
   */
  public WaitFreeReadQueue(Runnable writer,
                           Runnable reader,
                           int maximum,
                           MemoryArea memory,
                           boolean notify)
    throws StaticIllegalArgumentException,
           MemoryScopeException,
           InaccessibleAreaException
  {
  }

  /**
   * Constructs a queue containing up to {@code maximum}
   * elements in {@code memory}.  The queue has an unsynchronized and
   * nonblocking {@code read()} method and a
   * synchronized and blocking {@code write()} method.
   * <p>
   * Equivalent to
   * {@code WaitFreeReadQueue(writer, reader, maximum, memory, false)}
   */
  public WaitFreeReadQueue(Runnable writer,
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
   * nonblocking {@code read()} method and a
   * synchronized and blocking {@code write()} method.
   *
   * <p>
   * Equivalent to
   * {@code WaitFreeReadQueue(null, null, maximum, memory, notify)}
   * @since RTSJ 1.0.1
   */
  public WaitFreeReadQueue(int maximum, MemoryArea memory, boolean notify)
    throws StaticIllegalArgumentException, InaccessibleAreaException
  {
  }


  /**
   * Constructs a queue containing up to {@code maximum}
   * elements in immortal memory.  The queue has an unsynchronized and
   * nonblocking {@code read()} method and a
   * synchronized and blocking {@code write()} method.
   *
   * <p>
   * Equivalent to
   * {@code WaitFreeReadQueue(null, null, maximum, null, notify)}
   *
   * @since RTSJ 1.0.1
   */
  public WaitFreeReadQueue(int maximum, boolean notify)
    throws StaticIllegalArgumentException
  {
  }

  /**
   * Sets {@code this} to empty.
   * <p>
   * <em>Note,</em> this method needs to be used with care.
   *      Invoking {@code clear} concurrently with {@code read}
   *      or {@code write} can lead to unexpected results.
   */
  public void clear() {}

  /**
   * Queries the queue to determine if {@code this} is empty.
   *
   * <p>
   * <em>Note:</em> This method needs to be used with care since the
   * state of the queue may change while the method is in progress or
   * after it has returned.
   *
   * @return {@code true} when {@code this} is empty;
   *         {@code false} when {@code this} is not empty.
   */
  public boolean isEmpty()
  {
    return true;
  }

  /**
   * Queries the system to determine if {@code this} is full.
   *
   * <p>
   * <em>Note:</em> This method needs to be used with care since the state of the queue
   * may change while the method is in progress or after it has returned.
   *
   * @return {@code true} when {@code this} is full;
   *         {@code false} when {@code this} is not full.
   */
  public boolean isFull()
  {
    return true;
  }

  /**
   * Reads the least recently inserted element from the queue and returns
   * it as the result, unless the queue is empty. When the
   * queue is empty, {@code null} is returned.

   *
   * @return the instance of {@code T} read, or
   *         else {@code null} when {@code this} is empty.
   */
  public T read()
  {
    return null;
  }

  /**
   * Queries the queue to determine the number of elements in {@code this}.
   * <p>
   * <em>Note:</em> This method needs to be used with care since the state of the queue
   * may change while the method is in progress or after it has returned.
   *
   * @return the number of positions in {@code this} occupied by
   *          elements that have been written but not yet read.
   */
  public int size()
  {
    return 0;
  }

  /**
   * When {@code this} is empty block until a writer
   * inserts an element.
   * <p>
   * <em>Note:</em> When there is a single reader and no asynchronous invocation
   *      of  {@code clear}, then it is safe to invoke {@code read}
   *      after {@code waitForData} and know that {@code read}
   *      will find the queue non-empty.
   * <p>
   * <em>Implementation note,</em> to avoid reader and writer synchronizing on
   *  the same object, the reader should not be notified directly by
   *  a writer.  (This is the issue that the non-wait queue classes are
   *  intended to solve).
   *
   * @throws StaticUnsupportedOperationException when {@code this} has not
   *          been constructed with {@code notify} set to {@code true}.
   *
   * @throws InterruptedException when the thread is interrupted
   *         by {@code interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   *
   * @since RTSJ 1.0.1 {@code InterruptedException} was
   *         added to the throws
   *         clause.
   */
  public void waitForData()
    throws StaticUnsupportedOperationException, InterruptedException
  {
  }

  /**
   * A synchronized and blocking write.  This call blocks on queue full and
   * will wait until there is space in the queue.
   *
   * @param value The {@code java.lang.Object} that is placed in the queue.
   *
   * @throws InterruptedException when the thread is interrupted
   *         by {@code interrupt()} or
   *         {@link AsynchronouslyInterruptedException#fire()} during
   *         the time between calling this method and returning from it.
   *
   * @throws MemoryScopeException when a memory access error or
   *            illegal assignment error would occur
   *            while storing {@code object} in the queue.
   *
   * @throws StaticIllegalArgumentException when {@code value} is
   *         {@code null}.
   *
   * @since RTSJ 1.0.1 The return type is changed to void since it
   *        <em>always</em> returned` {@code true}, and
   *        {@code InterruptedException} was added to the throws clause.
   */
  public synchronized void write(T value)
    throws MemoryScopeException, InterruptedException
  {
  }
}
