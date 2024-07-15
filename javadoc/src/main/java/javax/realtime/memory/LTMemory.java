/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.memory;

import javax.realtime.SizeEstimator;

/**
 * {@code LTMemory} represents a memory area guaranteed by the
 * system to have linear time allocation when memory consumption from
 * the memory area is less than the memory area's <em>size</em>.
 *
 * <p> The memory area described by a {@code LTMemory} instance
 * does not exist in the Java heap, and is not subject to garbage
 * collection. Thus, it is safe to use a {@code LTMemory} object as
 * the initial memory area for a {@link javax.realtime.Schedulable}
 * instance which may not use the {@link javax.realtime.HeapMemory}
 * or to enter the memory area using the {@link ScopedMemory#enter}
 * method within such an instance.
 *
 * <p> Enough memory must be committed by the completion of the
 * constructor to satisfy the memory requirement given in the constructor.
 * Committed means that this memory must always be available for allocation.
 * The memory allocation must behave, with respect to successful
 * allocation, as if it were contiguous; i.e., a correct implementation
 * must guarantee that any sequence of object allocations that could
 * ever succeed without exceeding a specified initial memory size will
 * always succeed without exceeding that initial memory size and succeed
 * for any instance of {@code LTMemory} with that initial memory
 * size.
 *
 * <p>Creation of an {@code LTMemory} shall fail with a
 * {@link javax.realtime.StaticOutOfMemoryError} when the current
 * {@link javax.realtime.Schedulable} has been configured with a
 * {@link ScopedMemoryParameters#getMaxGlobalBackingStore} that would be
 * exceeded by said creation.
 *
 * <p> Methods from {@code LTMemory} should be overridden only by methods
 * that use {@code super}.
 *
 * @see javax.realtime.MemoryArea
 * @see ScopedMemory
 * @see javax.realtime.Schedulable
 *
 * @since RTSJ 2.0 moved to this package.
 */
public class LTMemory extends ScopedMemory
{
  long initialSize;
  long maximumSize;

  /**
   * Create a scoped memory of the given size and with the give logic to
   * run upon entry when no other logic is given.
   *
   * @param size The size in bytes of the memory to
   *        allocate for this area. This memory must be committed
   *        before the completion of the constructor.
   *
   * @param logic The {@code run()} of the given
   *        {@code Runnable} will be executed using
   *        {@code this} as its initial memory area.  When
   *        {@code logic} is {@code null}, this constructor is
   *        equivalent to {@link #LTMemory(long)}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when {@code size}
   *          is less than zero.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory for the {@code LTMemory} object or for
   *         its allocation area in its backing store, or when the
   *         current {@code Schedulable} would exceed its configured
   *         allowance of global backing store.
   *
   * @throws javax.realtime.IllegalAssignmentError when storing
   *         {@code logic} in {@code this} would violate the assignment
   *         rules.
   *
   * @since RTSJ 1.0.1
   */
  public LTMemory(long size, Runnable logic)
  {
    super(size, logic);
  }

  /**
   * Equivalent to {@link #LTMemory(long, Runnable)} with argument list
   * {@code (size.getEstimate(), runnable)}.
   *
   * @param size An instance of {@link javax.realtime.SizeEstimator}
   *        used to give an
   *        estimate of the initial size.
   *        This memory must be committed
   *        before the completion of the constructor.
   *
   * @param logic The {@code run()} of the given
   *        {@code Runnable} will be executed using
   *        {@code this} as its initial memory area.  When
   *        {@code logic} is {@code null}, this constructor is
   *        equivalent to {@link #LTMemory(SizeEstimator)}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is {@code null}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory for the {@code LTMemory} object or for
   *         its allocation area in its backing store, or when the
   *         current {@code Schedulable} would exceed its configured
   *         allowance of global backing store.
   *
   * @throws javax.realtime.IllegalAssignmentError when storing
   *         {@code logic} in {@code this} would violate the assignment
   *         rules.
   *
   * @since RTSJ 1.0.1
   */
  public LTMemory(SizeEstimator size, Runnable logic)
  {
    this(size.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #LTMemory(long, Runnable)} with the argument list
   * ({@code (size, null)}.
   *
   * @param size The size in bytes of the memory to
   *        allocate for this area. This memory must be committed
   *        before the completion of the constructor.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is less than zero.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *      insufficient memory for the {@code LTMemory} object or for its
   *      allocation area in its backing store, or when the current
   *      {@code Schedulable} would exceed its configured allowance of
   *      global backing store.
   *
   * @since RTSJ 1.0.1
   */
  public LTMemory(long size)
  {
    this(size, null);
  }

  /**
   * Equivalent to {@link #LTMemory(long, Runnable)} with argument list
   * {@code (size.getEstimate(), null)}.
   *
   * @param size An instance of {@link javax.realtime.SizeEstimator}
   *        used to give an estimate of the initial size.
   *        This memory must be committed
   *        before the completion of the constructor.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is {@code null}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *      insufficient memory for the {@code LTMemory} object, or when
   *      the current {@code Schedulable} would exceed its configured
   *      allowance of global backing store.
   *
   * @since RTSJ 1.0.1
   */
  public LTMemory(SizeEstimator size)
  {
    this(size.getEstimate(), null);
  }
}
