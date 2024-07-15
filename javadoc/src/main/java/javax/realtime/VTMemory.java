/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * {@code VTMemory} is similar to {@link LTMemory} except that
 * the execution time of an allocation from a {@code VTMemory} area
 * need not complete in linear time.
 * <p>
 *  Methods from {@code VTMemory} should be overridden only by methods that
 *  use {@code super}.
 *
 * @deprecated as of RTSJ 2.0
 */
@Deprecated
public class VTMemory extends ScopedMemory
{
  /**
   * Creates a {@code VTMemory} with the given parameters.
   *
   * @param initial The size in bytes of the memory to initially
   *        allocate for this area.
   *
   * @param maximum The maximum size in bytes to
   *        which this memory area's size may grow.
   *
   * @param logic An instance of {@code Runnable} whose
   *        {@code run()} method will use {@code this} as its
   *        initial memory area.  When {@code logic} is
   *        {@code null}, this constructor is equivalent to {@link
   *        #VTMemory(long initial, long maximum)}.
   *
   * @throws IllegalArgumentException when {@code initial} is
   *         greater than {@code maximum}, or when
   *         {@code initial} or {@code maximum} is less than
   *         zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code VTMemory} object or for its allocation area in its
   *      backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic}
   *            in {@code this} would violate the assignment rules.
   */
  public VTMemory(long initial,
                  long maximum,
                  Runnable logic)
  {
    super(initial, logic);
  }

  /**
   * Equivalent to {@link #VTMemory(long, long, Runnable)} with the argument
   * list {@code (initial.getEstimate(), maximum.getEstimate(), logic)}.
   *
   * @param initial The size in bytes of the memory to initially
   *        allocate for this area estimated by an instance of {@link SizeEstimator}.
   *
   * @param maximum The maximum size in bytes to which this memory area's size
   *        may grow estimated by an instance of {@link SizeEstimator}.
   *
   * @param logic An instance of {@code Runnable} whose
   *        {@code run()} method will use {@code this} as its
   *        initial memory area.  When {@code logic} is
   *        {@code null}, this constructor is equivalent to {@link
   *        #VTMemory(SizeEstimator initial, SizeEstimator maximum)}.
   *
   * @throws IllegalArgumentException when {@code initial} is
   *          {@code null}, {@code maximum} is
   *          {@code null}, {@code initial.getEstimate()} is
   *          greater than {@code maximum.getEstimate()}, or when
   *          {@code initial.getEstimate()} is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code VTMemory} object or for its allocation area in its
   *      backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic}
   *         in {@code this} would violate the assignment rules.
   */
  public VTMemory(SizeEstimator initial,
                  SizeEstimator maximum,
                  Runnable logic)
  {
    this(initial.getEstimate(), maximum.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #VTMemory(long, long, Runnable)} with the argument
   * list {@code (initial, maximum, null)}.
   *
   * @param initial The size in bytes of the memory to initially
   *        allocate for this area.
   *
   * @param maximum The maximum size in bytes to which this memory area's size
   *        may grow.
   *
   * @throws IllegalArgumentException when {@code initial}
   *         is greater than {@code maximum} or when {@code initial}
   *         or {@code maximum} is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code VTMemory} object or for its allocation area in its
   *      backing store.
   */
  public VTMemory(long initial, long maximum)
  {
    this(initial, maximum, null);
  }

  /**
   * Equivalent to {@link #VTMemory(long, long, Runnable)} with the argument
   * list {@code (initial.getEstimate(), maximum.getEstimate(), null)}.
   *
   * @param initial The size in bytes of the memory to initially
   *        allocate for this area estimated by an instance of
   *        {@link SizeEstimator}.
   *
   * @param maximum The maximum size in bytes to which this memory area's size
   *        may grow estimated by an instance of {@link SizeEstimator}.
   *
   * @throws IllegalArgumentException when {@code initial} is
   *          {@code null}, {@code maximum} is
   *          {@code null}, {@code initial.getEstimate()} is
   *          greater than {@code maximum.getEstimate()}, or when
   *          {@code initial.getEstimate()} is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *          {@code VTMemory} object or for its allocation area in its
   *          backing store.
   */
  public VTMemory(SizeEstimator initial, SizeEstimator maximum)
  {
    this(initial.getEstimate(), null);
  }

  /**
   * Equivalent to {@link #VTMemory(long, long, Runnable)} with the argument
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
   *        equivalent to {@link #VTMemory(long size)}.
   *
   * @throws IllegalArgumentException when {@code size}
   *          is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code VTMemory} object or for its allocation area in its
   *      backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic} in
   *         {@code this} would violate the assignment rules.
   *
   * @since RTSJ 1.0.1
   */
  public VTMemory(long size, Runnable logic)
  {
    this(size, size, logic);
  }

  /**
   * Equivalent to {@link #VTMemory(long, long, Runnable)} with the argument
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
   *        equivalent to {@link #VTMemory(SizeEstimator size)}.
   *
   *
   * @throws IllegalArgumentException when {@code size} is
   *          {@code null}, or {@code size.getEstimate()} is
   *          less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code VTMemory} object or for its allocation area in its
   *      backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic} in
   *         {@code this} would violate the assignment rules.
   *
   * @since RTSJ 1.0.1
   */
  public VTMemory(SizeEstimator size, Runnable logic)
  {
    this(size.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #VTMemory(long, long, Runnable)} with the argument
   * list {@code (size, size, null)}.
   *
   * @param size The size in bytes of the memory to
   *        allocate for this area. This memory must be committed
   *        before the completion of the constructor.
   *
   * @throws IllegalArgumentException when {@code size}
   *         is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code VTMemory} object or for its allocation area in its
   *      backing store.
   *
   * @since RTSJ 1.0.1
   */
  public VTMemory(long size)
  {
    this(size, size, null);
  }

  /**
   * Equivalent to {@link #VTMemory(long, long, Runnable)} with the argument
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
   *         {@code VTMemory} object or for its allocation area in its
   *         backing store.
   *
   * @since RTSJ 1.0.1
   */
  public VTMemory(SizeEstimator size)
  {
    this(size.getEstimate(), null);
  }

  /**
   * Creates a string representing this object.
   * The string is of the form
   * <pre>
   * {@code (VTMemory) ScopedMemory#<num>}
   * </pre>
   * where {@code <num>}
   *  uniquely identifies the {@code VTMemory} area.
   *
   * @return a string representing the value of {@code this}.
   */
  @Override
  public String toString()
  {
    return new String("(VTMemory) " + super.toString());
  }
}
