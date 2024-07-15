/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import javax.realtime.ScopedMemory;

/**
 * Equivalent to and superseded by {@link javax.realtime.memory.LTMemory}.
 *
 * @deprecated since RTSJ 2.0; moved to package {@code javax.realtime.memory}.
 */
@Deprecated
public class LTMemory extends ScopedMemory
{
  long initialSize;
  long maximumSize;

  /**
   * Creates an {@code LTMemory} of the given size.
   *
   * @param initial The size in bytes of the memory to
   *        allocate for this area. This memory must be committed
   *        before the completion of the constructor.
   *
   * @param maximum The size in bytes of the memory to
   *        allocate for this area.
   *
   * @param logic The {@code run()} of the given
   *        {@code Runnable} will be executed using
   *        {@code this} as its initial memory area.  When
   *        {@code logic} is {@code null}, this constructor is
   *        equivalent to {@link #LTMemory(long initial, long maximum)}.
   *
   * @throws IllegalArgumentException when {@code initial}
   *         is greater than {@code maximum}, or when {@code initial}
   *          or {@code maximum} is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code LTMemory} object or for its allocation
   *      area in its backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic} in
   *          {@code this} would violate the assignment rules.
   */
  public LTMemory(long initial,
                  long maximum,
                  Runnable logic)
  {
    super(initial);
  }

  /**
   * Equivalent to {@link #LTMemory(long, long, Runnable)} with the argument
   * list {@code (initial.getEstimate(), maximum.getEstimate(), logic)}.
   *
   * @param initial An instance of {@link SizeEstimator} used to give an
   *        estimate of the initial size.
   *        This memory must be committed
   *        before the completion of the constructor.
   *
   * @param maximum An instance of {@link SizeEstimator} used to give an
   *        estimate for the maximum bytes to allocate for this area.
   *
   * @param logic The {@code run()} of the given
   *        {@code Runnable} will be executed using
   *        {@code this} as its initial memory area.  When
   *        {@code logic} is {@code null}, this constructor is
   *        equivalent to {@link #LTMemory(SizeEstimator initial, SizeEstimator maximum)}.
   *
   *
   *  @throws IllegalArgumentException when {@code initial} is
   *          {@code null}, {@code maximum} is
   *          {@code null}, {@code initial.getEstimate()} is
   *          greater than {@code maximum.getEstimate()}, or when
   *          {@code initial.getEstimate()} is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code LTMemory} object or for its allocation
   *      area in its backing store.
   *
   *  @throws IllegalAssignmentError when storing {@code logic} in
   *          {@code this} would violate the assignment rules.
   */
  public LTMemory(SizeEstimator initial,
                  SizeEstimator maximum,
                  Runnable logic)
  {
    this(initial.getEstimate(), maximum.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #LTMemory(long, long, Runnable)} with
   * the argument list {@code (initial, maximum, null)}.
   *
   * @param initial The size in bytes of the memory to
   *        allocate for this area. This memory must be committed
   *        before the completion of the constructor.
   *
   * @param maximum The size in bytes of the memory to
   *        allocate for this area.
   *
   *  @throws IllegalArgumentException when {@code initial}
   *          is greater than {@code maximum}, or when {@code initial}
   *          or {@code maximum} is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code LTMemory} object or for its allocation
   *      area in its backing store.
   */
  public LTMemory(long initial, long maximum)
  {
    this(initial, maximum, null);
  }

  /**
   * Equivalent to {@link #LTMemory(long, long, Runnable)} with the argument
   * list {@code (initial.getEstimate(), maximum.getEstimate(), null)}.
   *
   * @param initial An instance of {@link SizeEstimator} used to give an
   *        estimate of the initial size.
   *        This memory must be committed
   *        before the completion of the constructor.
   *
   * @param maximum An instance of {@link SizeEstimator} used to give an
   *        estimate for the maximum bytes to allocate for this area.
   *
   *
   * @throws IllegalArgumentException when {@code initial} is
   *         {@code null}, {@code maximum} is
   *         {@code null}, {@code initial.getEstimate()} is
   *         greater than {@code maximum.getEstimate()}, or when
   *         {@code initial.getEstimate()} is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code LTMemory} object or for its allocation
   *      area in its backing store.
   */
  public LTMemory(SizeEstimator initial, SizeEstimator maximum)
  {
    this(initial.getEstimate(), maximum.getEstimate(), null);
  }

  /**
   * Equivalent to {@link #LTMemory(long, long, Runnable)} with the argument
   * list {@code (size, size, logic)}.
   *
   * @param size The size in bytes of the memory to
   *        allocate for this area. This memory must be committed
   *        before the completion of the constructor.
   *
   * @param logic The {@code run()} of the given
   *        {@code Runnable} will be executed using
   *        {@code this} as its initial memory area.  When
   *        {@code logic} is {@code null}, this constructor is
   *        equivalent to {@link #LTMemory(long size)}.
   *
   * @throws IllegalArgumentException when {@code size}
   *          is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code LTMemory} object or for its allocation
   *      area in its backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic} in
   *          {@code this} would violate the assignment rules.
   *
   * @since RTSJ 1.0.1
   */
  public LTMemory(long size, Runnable logic)
  {
    this(size, size, logic);
  }

  /**
   * Equivalent to {@link #LTMemory(long, long, Runnable)} with the argument
   * list {@code (size.getEstimate(), size.getEstimate(), logic)}.
   *
   * @param size An instance of {@link SizeEstimator} used to give an
   *        estimate of the initial size.
   *        This memory must be committed
   *        before the completion of the constructor.
   *
   * @param logic The {@code run()} of the given
   *        {@code Runnable} will be executed using
   *        {@code this} as its initial memory area.  When
   *        {@code logic} is {@code null}, this constructor is
   *        equivalent to {@link #LTMemory(SizeEstimator size)}.
   *
   *
   * @throws IllegalArgumentException when {@code size} is
   *          {@code null}, or {@code size.getEstimate()} is
   *          less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code LTMemory} object or for its allocation
   *      area in its backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic} in
   *          {@code this} would violate the assignment rules.
   *
   * @since RTSJ 1.0.1
   */
  public LTMemory(SizeEstimator size, Runnable logic)
  {
    this(size.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #LTMemory(long, long, Runnable)} with the argument
   * list {@code (size, size, null)}.
   *
   * @param size The size in bytes of the memory to
   *        allocate for this area. This memory must be committed
   *        before the completion of the constructor.
   *
   *  @throws IllegalArgumentException when {@code size}
   *          is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code LTMemory} object or for its allocation
   *      area in its backing store.
   *
   * @since RTSJ 1.0.1
   */
  public LTMemory(long size)
  {
    this(size, size, null);
  }

  /**
   * Equivalent to {@link #LTMemory(long, long, Runnable)} with the argument
   * list {@code (size.getEstimate(), size.getEstimate(), null)}.
   *
   * @param size An instance of {@link SizeEstimator} used to give an
   *        estimate of the initial size.
   *        This memory must be committed
   *        before the completion of the constructor.
   *
   * @throws IllegalArgumentException when {@code size} is
   *           {@code null}, or {@code size.getEstimate()} is
   *           less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code LTMemory} object or for its allocation
   *      area in its backing store.
   *
   * @since RTSJ 1.0.1
   */
  public LTMemory(SizeEstimator size)
  {
    this(size.getEstimate());
  }

  /**
   * Creates a string representation of this object. The string
   * is of the form
   * <pre>
   * {@code (LTMemory) ScopedMemory#<num>}
   * </pre>
   * <p>where {@code <num>} uniquely identifies the {@code LTMemory} area.
   *
   * @return a string representing the value of {@code this}.
   */
  @Override
  public String toString()
  {
    return new String("(LTMemory) " + super.toString());
  }
}
