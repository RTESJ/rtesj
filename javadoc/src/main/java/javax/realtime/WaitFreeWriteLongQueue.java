/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import javax.realtime.control.AsynchronouslyInterruptedException;

class WaitFreeWriteLongQueue
{
  private long[] elements_;
  private int size_ = 0;

  /**
   * Constructs a queue containing up to {@code maximum}
   * elements in {@code memory}.  The queue has an unsynchronized and
   * nonblocking {@code write()} method and a
   * synchronized and blocking {@code read()} method.
   *
   * @param maximum the maximum capacity of the queue.
   */
  public WaitFreeWriteLongQueue(int maximum)
    throws StaticIllegalArgumentException, InaccessibleAreaException
  {
    elements_ = new long[maximum];
  }

  /**
   * Sets {@code this} to empty.
   */
  public void clear()
  {
    size_ = 0;
  }

  /**
   * Queries the system to determine if {@code this} is empty.
   * <p>
   * <em>Note,</em> this method needs to be used with care since the state of the queue
   * may change while the method is in progress or after it has returned.
   *
   * @return True, when {@code this} is empty.
   *         False, when {@code this} is not empty.
   */
  public boolean isEmpty()
  {
    return size_ == 0;
  }

  /**
   * Queries the system to determine if {@code this} is full.
   * <p>
   * <em>Note,</em> this method needs to be used with care since the state of the queue
   * may change while the method is in progress or after it has returned.
   *
   * @return True, when {@code this} is full.
   *         False, when {@code this} is not full.
   */
  public boolean isFull()
  {
    return size_ == elements_.length;
  }

  /**
   * A synchronized and possibly blocking operation on the queue.
   *
   * @return The {@code T} least recently written to the queue.
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
  public synchronized long read() throws InterruptedException
  {
    while (isEmpty())
      {
        synchronized (this) { if (isEmpty()) { wait(); }}
      }

    long result = elements_[0];
    for (int i = 1; i < size_; i++) { elements_[i - 1] = elements_[i]; }
    size_--;
    return result;
  }


  /**
   * Queries the queue to determine the number of elements in {@code this}.
   * <p>
   * <em>Note,</em> this method needs to be used with care since the
   * state of the queue may change while the method is in progress or
   * after it has returned.
   *
   * @return The number of positions in {@code this} occupied by
   *  elements that have been written but not yet read.
   */
  public int size()
  {
    return size_;
  }

  /**
   * Unconditionally insert {@code value} into {@code this},
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
   */
  public synchronized boolean force(long value)
  {
    int length = elements_.length;
    if (size_ == length)
      {
        elements_[length - 1] = value;
        return false;
      }
    else
      {
        elements_[size_] = value;
        size_++;
        return true;
      }
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
   */
  public boolean write(long value)
    throws MemoryScopeException, StaticIllegalArgumentException
  {
    int length = elements_.length;
    if (size_ == length)
      {
        return false;
      }
    else
      {
        elements_[size_] = value;
        size_++;
        return true;
      }
  }

}
