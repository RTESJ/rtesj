/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/

package javax.realtime.memory;

import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.MemoryParameters;

/**
 * Extends memory parameters to provide limits for scoped memory.
 *
 * @see javax.realtime.MemoryParameters
 *
 * @since RTSJ 2.0
 */
public class ScopedMemoryParameters extends MemoryParameters
{
  /**
   *
   */
  private static final long serialVersionUID = 3327152212877292017L;

  private final long maxContainingArea_;

  private final long maxInitialBackingStore_;

  private final long maxGlobalBackingStore_;

  /**
   * Creates a {@code ScopedMemoryParameters} instance with the given values that
   * can allow access to any {@code ScopedMemory}
   * @param maxInitialArea A limit on the amount of memory the
   *        schedulable may allocate in its initial scoped memory
   *        area. Units are in bytes. When zero, no allocation is
   *        allowed in the memory area. When the initial memory area is
   *        not a {@code ScopedMemory}, this parameter has no effect.
   *        To specify no limit, use {@code UNLIMITED}.
   * @param maxImmortal  A limit on the amount of memory the schedulable
   *        may allocate in the immortal area. Units are in bytes. When
   *        zero, no allocation allowed in immortal. To specify no limit,
   *        use {@code UNLIMITED}.
   * @param allocationRate  A limit on the rate of allocation in the
   *        heap. Units are in bytes per second of wall clock time.
   *        When {@code allocationRate} is zero, no allocation
   *        is allowed in the heap. To specify no limit, use {@code UNLIMITED}.
   *        Measurement starts when the schedulable is first released
   *        for execution; not when it is constructed.  Enforcement of
   *        the allocation rate is an implementation option.  When the
   *        implementation does not enforce allocation rate limits, it
   *        treats all positive allocation rate limits as {@code UNLIMITED}.
   * @param maxContainingArea A limit on the amount of memory the
   *        schedulable may allocate in memory area where it was created.
   *        Units are in bytes. When zero, no allocation is
   *        allowed in the memory area. When the containing memory area is
   *        not a {@code ScopedMemory}, this parameter has no effect.
   *        To specify no limit, use {@code UNLIMITED}.
   * @param maxInitialBackingStore A limit on the amount of backing
   *        store the schedulable may allocate from backing store of its
   *        initial memory area when that memory area is an instance of
   *        {@code StackedMemory}, in bytes. When zero, no allocation is
   *        allowed in that backing store.  Backing store that is returned
   *        to the area backing store is subtracted from the limit.
   *        To specify no limit, use {@code UNLIMITED}.
   * @param maxGlobalBackingStore A limit on the amount of backing store the
   *        schedulable may allocate from the global backing store to scoped memory
   *        areas in bytes. When zero, no allocation is
   *        allowed in the memory area.
   *        To specify no limit, use {@code UNLIMITED}.
   * @throws javax.realtime.StaticIllegalArgumentException when any
   *         value other less than zero is passed as
   *         the value of {@code maxInitialArea}, {@code maxImmortal},
   *         {@code allocationRate}, {@code maxBackingStore}, or
   *         {@code maxContainingArea}.
   */
  public ScopedMemoryParameters(long maxInitialArea,
                                long maxImmortal,
                                long allocationRate,
                                long maxContainingArea,
                                long maxInitialBackingStore,
                                long maxGlobalBackingStore)
    throws StaticIllegalArgumentException
  {
    super(maxInitialArea, maxImmortal, allocationRate);
    maxContainingArea_ = maxContainingArea;
    maxInitialBackingStore_ = maxInitialBackingStore;
    maxGlobalBackingStore_ = maxGlobalBackingStore;
  }

  /**
   * Same as {@code ScopedMemoryParameters(maxInitialArea, maxImmortal,
   * 0, maxContainingArea, maxInitialBackingStore, 0)}.
   * This constructor disallows root {@code StackedMemory},
   * {@code LTMemory}, and {@code Heap} allocation.
   */
  public ScopedMemoryParameters(long maxInitialArea,
                         long maxImmortal,
                         long maxContainingArea,
                         long maxInitialBackingStore)
    throws StaticIllegalArgumentException
  {
    this(maxInitialArea, maxImmortal, 0,
         maxContainingArea, maxInitialBackingStore, 0);
  }

  /**
   * Same as {@code ScopedMemoryParameters(maxInitialArea, maxImmortal,
   * MemoryParameters.UNLIMITED, maxGlobalBackingStore, 0, 0)}.
   * This constructor disallows host {@code StackedMemory} and
   * {@code LTMemory} allocation.
   */
  public ScopedMemoryParameters(long maxInitialArea,
                                long maxImmortal,
                                long maxContainingArea)
    throws StaticIllegalArgumentException
  {
    this(maxInitialArea, maxImmortal, MemoryParameters.UNLIMITED,
         maxContainingArea, 0, 0);
  }

  /**
   * Determines the limit on backing store for this task from the global pool.
   *
   * @return the limit on backing store.
   */
  public long getMaxGlobalBackingStore() { return maxGlobalBackingStore_; }

  /**
   * Determines the limit on backing store for this task from its parent
   * {@code StackedMemory}.
   *
   * @return the limit on backing store.
   */
  public long getMaxInitialBackingStore() { return maxInitialBackingStore_; }

  /**
   * Determines the limit on allocation in the area where the task was created.
   *
   * @return the limit on allocation in the area where the task was created.
   */
  public long getMaxContainingArea() { return maxContainingArea_; }
}
