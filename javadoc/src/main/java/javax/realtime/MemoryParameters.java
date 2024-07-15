/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.Serializable;


/**
 * Memory parameters can be given on the constructor of any
 * {@link Schedulable}.  They provide limits on allocation.  For
 * garbage-collected objects, they provide the rate of allocation, and
 * for Immortal, the overall amount of allocation.
 *
 * <p>
 * The limits in a {@code MemoryParameters} instance are enforced when a
 * schedulable creates a new object, e.g., uses the {@code new}
 * operation.  When a schedulable exceeds its allocation or allocation
 * rate limit, the error is handled as if the allocation failed because
 * of insufficient memory.  The failed object allocation throws an
 * {@code OutOfMemoryError}.
 *
 * <p>
 * A {@code MemoryParameters} object may be bound to more than one
 * schedulable, but that does not cause the memory budgets reflected by
 * the parameter to be shared among the schedulables that are associated
 * with the parameter object.
 * <p>
 * As of RTSJ 2.0, instances of {@code MemoryParameters} are immutable.
 *
 * @rtsj.warning.sync
 */
public class MemoryParameters implements Cloneable, Serializable
{
  /**
   *
   */
  private static final long serialVersionUID = -4040157033159132745L;

  /**
   * Specifies no maximum limit.
   *
   * @since RTSJ 2.0
   */
  public static final long UNLIMITED = Long.MAX_VALUE;

  /**
   * Specifies no maximum limit.
   *
   * @deprecated since RTSJ 2.0.
   */
  @Deprecated
  public static final long NO_MAX = UNLIMITED;

  private long max_initial_area_ = UNLIMITED;

  private long max_immortal_ = UNLIMITED;

  private long allocation_rate_ = UNLIMITED;

  /**
   * Creates a {@code MemoryParameters} object with the given values.
   *
   * @param maxInitialArea A limit on the amount of memory the
   *        schedulable may allocate in its initial scoped memory
   *        area. Units are in bytes. When zero, no allocation is
   *        allowed in the memory area. When the initial memory area is
   *        not a {@code ScopedMemory}, this parameter has no effect.
   *        To specify no limit, use {@code UNLIMITED}.
   *
   * @param maxImmortal  A limit on the amount of memory the schedulable
   *        may allocate in the immortal area. Units are in bytes. When
   *        zero, no allocation is allowed in immortal. To specify no limit,
   *        use {@code UNLIMITED}.
   *
   * @param allocationRate A limit on the rate of allocation in the
   *        heap. Units are in bytes per second of wall clock time.
   *        When {@code allocationRate} is zero, no allocation is
   *        allowed in the heap. To specify no limit, use {@code UNLIMITED}.
   *        Measurement starts when the schedulable is
   *        first released for execution; not when it is constructed.
   *        Enforcement of the allocation rate is an implementation
   *        option.  When the implementation does not enforce allocation
   *        rate limits, it treats all positive allocation rate limits
   *        as {@code UNLIMITED}.
   *
   * @throws StaticIllegalArgumentException when any value less than zero
   *         is passed as the value of {@code maxInitialArea},
   *         {@code maxImmortal}, or {@code allocationRate}.
   */
  public MemoryParameters(long maxInitialArea,
                          long maxImmortal,
                          long allocationRate)
    throws StaticIllegalArgumentException
  {
  }

  /**
   * Creates a {@code MemoryParameters} object with the given values and
   * {@code allocationRate} set to {@code UNLIMITED}.  It has the same
   * effect as
   * {@code MemoryParameters(maxInitialArea, maxImmortal, UNLIMITED)}
   */
  public MemoryParameters(long maxInitialArea, long maxImmortal)
  {
    this(maxInitialArea, maxImmortal, UNLIMITED);
  }

  /**
   * Creates a {@code MemoryParameters} object with the given values and
   * {@code allocationRate} set to {@code allocationRate}.  It has the same
   * effect as {@code MemoryParameters(UNLIMITED, UNLIMITED, allocationRate)}
   *
   * @since RTSJ 2.0
   */
  public MemoryParameters(long allocationRate)
  {
    this(UNLIMITED, UNLIMITED, allocationRate);
  }

  /**
   * Associates the passed schedulable with
   * this {@code MemoryParameters} object. This is to implement
   * the many-to-one functionality of {@code MemoryParameters},
   * specifically this is to facilitate the requirements of
   * setmaxInitialArea and setMaxImmortal.
   *
   * @param t The {@code RealtimeThread} or {@code AsyncEventHandler}
   *          that will use this {@code MemoryParameters} object for budgeted
   *          allocation.
   *
   * @throws StaticUnsupportedOperationException when the parameter does not
   *         reference a schedulable.
   *
   * @since RTSJ 1.0.1 Changed the parameter type to Schedulable and added
   *        the throw StaticUnsupportedOperationException.
   */
  void associateThread(Schedulable t)
    throws StaticUnsupportedOperationException {}

  /**
   * Returns a clone of {@code this}.  This method should behave
   * effectively as if it constructed a new object with the visible
   * values of {@code this}.
   * <ul>
   * <li>The new object is in the current allocation context.</li>
   * <li>
   * {@code clone} does not copy any associations from {@code this} and
   * it does not implicitly bind the new object to a SO.</li>
   * </ul>
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public Object clone()
  {
    try { return super.clone(); }
    catch (CloneNotSupportedException e) { throw new Error("Can't happen"); }
  }

  /**
   * Disassociates or removes the {@code RealtimeThread} object
   * passed with this {@code MemoryParameters} object. This is
   * to implement the many-to-one functionality of
   * {@code MemoryParameters}, specifically this is to facilitate
   * the requirements of setmaxInitialArea and setMaxImmortal.
   *
   * @param t {@code RealtimeThread} object that will no longer
   *          be using this {@code MemoryParameters} object for
   *          budgeted allocation.
   */

  void disassociateThread(RealtimeThread t) {}

  /**
   * Determines the limit on the rate of allocation in the heap.
   * Units are in bytes per second.
   *
   * @return the allocation rate in bytes per second.
   *  When zero, no allocation is allowed in the heap.  When the
   *  returned value is {@link #UNLIMITED} then the allocation rate on the
   *  heap is uncontrolled.  Enforcement of allocation rates between zero and
   *  {@link #UNLIMITED} is implementation dependent.
   */
  public long getAllocationRate() { return 0; }

  /**
   * Gets the limit on the amount of memory the schedulable may allocate in
   * the immortal area. Units are in bytes.
   *
   * @return the limit on immortal memory allocation.
   *  When zero, no allocation is allowed in immortal memory.  When the
   *  returned value is {@link #UNLIMITED} then there is no limit for
   *  allocation in immortal memory.
   *
   */
  public long getMaxImmortal() { return 0; }

  /**
   * Gets the limit on the amount of memory the schedulable may allocate in
   * its initial memory area, when initial is a scoped memory. Units are
   * in bytes.
   *
   * @return the allocation limit in the schedulable's initial memory
   *  area.  When zero, no allocation is allowed in the initial memory
   *  area.  When the returned value is {@link #UNLIMITED} then there is
   *  no limit for allocation in the initial memory area.
   *
   * @since RTSJ 2.0
   */
  public long  getMaxInitialMemoryArea() { return 0; }

  /**
   * Gets the limit on the amount of memory the schedulable may allocate in
   * its initial memory area. Units are in bytes.
   *
   * @return the allocation limit in the schedulable's initial memory area.
   *  When zero, no allocation is allowed in the initial memory area.  When the
   *  returned value is {@link #NO_MAX} then there is no limit for
   *  allocation in the initial memory area.
   *
   * @deprecated since RTSJ 2.0, repleace by getMaxInitialMemoryArea.
   */
  @Deprecated
  public long getMaxMemoryArea() { return 0; }

  /**
   * Sets the limit on the rate of allocation in the heap.
   * <p>
   * Changes to this parameter take place at the next object allocation
   * for each associated schedulable, on an individual basis.
   * Schedulables which are in current violation of the newly configured
   * value will simply receive an {@code StaticOutOfMemoryError} on violating
   * allocations.  Because this {@code MemoryParameters} may be
   * associated with more than one schedulable, on a multiprocessor
   * system there may be some implementation-defined delay before
   * executing schedulables detect the parameter changes.
   *
   * @param allocationRate Units are in bytes per second of wall-clock
   *        time. When {@code allocationRate} is zero, no allocation is
   *        allowed in the heap. To specify no limit, use
   *        {@code NO_MAX}. Measurement starts when the schedulable starts;
   *        not when it is constructed.  Enforcement of the allocation rate
   *        is an implementation option.  When the implementation does
   *        not enforce allocation rate limits, it treats all non-zero
   *        allocation rate limits as {@code NO_MAX}.
   *
   * @throws StaticIllegalArgumentException when any value other than
   *         positive, zero, or {@code NO_MAX} is passed as the value of
   *         {@code allocationRate}.
   *
   * @deprecated RTSJ 2.0
   */
  @Deprecated
  public void setAllocationRate(long allocationRate)
  {
  }

  /**
   * Sets the limit on the rate of allocation in the heap. When this
   * {@code MemoryParameters} object is currently
   * associated with one or more schedulables that have been passed
   * admission control, this change in allocation rate will be submitted
   * to admission control. The scheduler (in conjunction with the garbage
   * collector) will either admit all the effected threads with the
   * new allocation rate, or leave the allocation rate unchanged
   * and cause {@code setAllocationRateIfFeasible} to return {@code false}.
   * <p>
   * Changes to this parameter take place at the next object allocation
   * for each associated schedulable, on an individual basis.
   * Schedulables which are in current violation of the newly configured
   * value will simply receive an {@code StaticOutOfMemoryError} on violating
   * allocations.  Because this {@code MemoryParameters} may be
   * associated with more than one schedulable, on a multiprocessor
   * system there may be some implementation-defined delay before
   * executing schedulables detect the parameter changes.
   *
   * @param allocationRate Units in bytes per second of wall-clock time.
   *         When {@code allocationRate} is zero, no
   *        allocation is allowed in the heap. To specify no limit,
   *        use {@link #NO_MAX}.  Enforcement of the allocation rate is
   *         an implementation option.  When the implementation does not
   *         enforce allocation rate limits, it treats all non-zero
   *         allocation rate limits as {@code NO_MAX}.
   *
   * @return {@code true} when the request was fulfilled.
   *
   * @throws StaticIllegalArgumentException when any value other than
   *       positive, zero, or {@code NO_MAX} is passed as the value of
   *       {@code allocationRate}.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   * is inadequate.
   */
  @Deprecated
  public boolean setAllocationRateIfFeasible(long allocationRate)
  {
    return true;
  }

  /**
   * Sets the limit on the amount of memory the schedulable may allocate in the
   * immortal area.
   * <p>
   * Changes to this parameter take place at the next object allocation
   * for each associated schedulable, on an individual basis.
   * Schedulables which are in current violation of the newly configured
   * value will simply receive an {@code StaticOutOfMemoryError} on violating
   * allocations.  Because this {@code MemoryParameters} may be
   * associated with more than one schedulable, on a multiprocessor
   * system there may be some implementation-defined delay before
   * executing schedulables detect the parameter changes.
   *
   * @param  maximum Units in bytes. When zero, no allocation
   *          allowed in immortal. To specify no limit, use {@code NO_MAX}.
   *
   * @return {@code true} when the value is set, false when any of the
   *         schedulables have already allocated more
   *         than the given value. In this case the call has no effect.
   *
   * @throws StaticIllegalArgumentException when any value other than
   *       positive, zero, or {@code NO_MAX} is passed as the value of
   *        {@code maximum}.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis
   * is inadequate
   */
  @Deprecated
  public boolean setMaxImmortalIfFeasible(long maximum) { return true; }

  /**
   * Sets the limit on the amount of memory the schedulable may allocate in
   * its initial memory area.
   * <p>
   * Changes to this parameter take place at the next object allocation
   * for each associated schedulable, on an individual basis.
   * Schedulables which are in current violation of the newly configured
   * value will simply receive an {@code StaticOutOfMemoryError} on violating
   * allocations.  Because this {@code MemoryParameters} may be
   * associated with more than one schedulable, on a multiprocessor
   * system there may be some implementation-defined delay before
   * executing schedulables detect the parameter changes.
   *
   * @param maximum Units in bytes. When zero, no allocation allowed
   *        in the initial memory area. To specify no limit, use
   *        {@code UNLIMITED}.
   *
   * @return {@code true} when the value is set, false when any of the
   *         schedulables have already allocated more
   *         than the given value. In this case the call has no effect.
   *
   *   @throws StaticIllegalArgumentException when any value other
   *       than positive, zero, or {@code NO_MAX} is passed as the value
   *       of {@code maximum}.
   *
   * @deprecated as of RTSJ 2.0, since the framework for feasibility analysis
   *             is inadequate.
   */
  @Deprecated
  public boolean setMaxMemoryAreaIfFeasible(long maximum) { return true; }
}
