/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Implementation or device providers may include classes that implement
 * {@code PhysicalMemoryTypeFilter} which allow additional
 * characteristics of memory in devices to be specified.
 *
 * Implementations of {@code PhysicalMemoryTypeFilter} are intended
 * to be used by the {@link PhysicalMemoryManager}, not directly from
 * application code.
 *
 * @deprecated as of RTSJ 2.0
 */
@Deprecated
public interface PhysicalMemoryTypeFilter
{
  /**
   * Queries the system about whether the specified range of memory
   * contains any of this type.
   *
   * @see PhysicalMemoryManager#isRemovable
   *
   * @param base The physical address of the beginning of the memory region.
   *
   * @param size The size of the memory region.
   *
   * @return {@code true}, when the specified range contains ANY of this
   *         type of memory.
   *
   * @throws IllegalArgumentException when {@code base} or
   *         {@code size} is negative.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   *         than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   */
  public boolean contains(long base, long size);

  /**
   * Search for physical memory of the right type.
   *
   * @param base The physical address at which to start searching.
   *
   * @param size The amount of memory to be found.
   *
   * @return the address where memory was found or -1 when it was not found.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   *         than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @throws IllegalArgumentException when {@code base} or
   *         {@code size} is negative.
   */
  public long find(long base, long size);

  /**
   * Gets the virtual memory attributes of {@code this}.  The value
   * of this field is as defined for the POSIX {@code mmap}
   * function's {@code prot} parameter for the platform.  The
   * meaning of the bits is platform-dependent.  POSIX defines constants
   * for PROT_READ, PROT_WRITE, PROT_EXEC, and PROT_NONE.
   *
   * @return the virtual memory attributes as an integer.
   */
  public int getVMAttributes();

  /**
   * Gets the virtual memory flags of {@code this}.  The value of
   * this field is as defined for the POSIX {@code mmap} function's
   * {@code flags} parameter for the platform.  The meaning of the
   * bits is platform-dependent.  POSIX defines constants for
   * MAP_SHARED, MAP_PRIVATE, and MAP_FIXED.
   *
   * @return the virtual memory flags as an integer.
   */
  public int getVMFlags();

  /**
   * When configuration is required for memory to fit the attribute of
   * this object, do the configuration here.
   *
   * @param base The address of the beginning of the physical memory region.
   *
   * @param vBase The address of the beginning of the virtual memory region.
   *
   * @param size The size of the memory region.
   *
   * @throws IllegalArgumentException when {@code base} or
   *  {@code size} is negative.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   * than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor, or {@code vBase}
   *         plus {@code size} would exceed the virtual addressing
   *         range of the processor.
   *
   */
  public void initialize(long base, long vBase, long size);

  /**
   * Queries the system about the existence of the specified range of
   * physical memory.
   *
   * @see PhysicalMemoryManager#isRemoved
   *
   * @param base The address of the beginning of the memory region.
   *
   * @param size The size of the memory region.
   *
   * @throws IllegalArgumentException when the base and size do not fall into
   *             this type of memory.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   *         than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @return {@code true}, when all of the memory is present.
   *         {@code False}, when any of the memory has been removed.
   */
  public boolean isPresent(long base, long size);

  /**
   * Queries the system about the removability of this memory.
   *
   * @return  {@code true}, when this type of memory is removable.
   */
  public boolean isRemovable();

  /**
   * Register the specified {@link AsyncEvent} to fire when any memory
   * of this type in the range is added to the system.
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @param ae  The async event to fire.
   *
   *
   *  @throws IllegalArgumentException when {@code ae} is
   *          {@code null}, or when the specified range contains no
   *          removable memory of this type.
   *          {@code IllegalArgumentException} may also be thrown
   *          when {@code size} is less than zero.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   *         than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @since RTSJ 1.0.1
   */
  public void onInsertion(long base, long size, AsyncEvent ae);

  /**
   * Registers the specified {@link AsyncEventHandler} to run when any
   * memory of this type, and in the range is added to the system.  When
   * the size or the base is less than 0, unregister all "onInsertion"
   * references to the handler.
   *
   * <p> Note that this method only removes handlers that were registered
   * with the same method.  It has no effect on handlers that were
   * registered using an associated async event.
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @param aeh  The handler to register.
   *
   * @throws IllegalArgumentException when the specified range contains
   *          no removable memory, or when {@code aeh} is
   *          {@code null} and {@code size} and
   *          {@code base} are both greater than or equal to zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @deprecated as of RTSJ 1.0.1 Replace with onInsertion(long, long, AsyncEvent)
   */
  @Deprecated
  public void onInsertion(long base, long size, AsyncEventHandler aeh);


  /**
   * Registers the specified AE to fire when any memory in the range is
   * removed from the system.
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @param ae  The async event to register.
   *
   * @throws IllegalArgumentException when the specified range contains
   *         no removable memory of this type, when {@code ae} is
   *         {@code null}, or when {@code size} is less than
   *         zero.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   *         than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @since RTSJ 1.0.1
   */
  public void onRemoval(long base, long size, AsyncEvent ae);

  /**
   * Registers the specified AEH to run when any memory in the range is
   * removed from the system.  When {@code size} or {@code base}
   * is less than 0, unregister all "onRemoval" references to the
   * handler parameter.
   *
   * <p> Note that this method only removes handlers that were registered
   * with the same method.  It has no effect on handlers that were
   * registered using an associated async event.
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @param aeh  The handler to register.
   *
   * @throws IllegalArgumentException when the specified range contains
   *          no removable memory known to this filter, when
   *          {@code aeh} is {@code null} and
   *          {@code size} and {@code base} are both greater
   *          than or equal to zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @deprecated as of RTSJ 1.0.1
   */
  @Deprecated
  public void onRemoval(long base, long size, AsyncEventHandler aeh);

  /**
   * Unregisters the specified insertion event.  The event is only
   * unregistered when all three arguments match the arguments used to
   * register the event, except that {@code ae} of
   * {@code null} matches all values of {@code ae} and will
   * unregister every {@code ae} that matches the address range.
   *
   * <p> Note that this method has no effect on handlers registered directly
   * as async event handlers.
   *
   * @param base The starting address in physical memory associated with
   *        {@code ae}.
   * @param size The size of the memory area associated with {@code ae}.
   * @param ae The event to unregister.
   * @return  {@code true}, when at least one event matched the pattern,
   *         {@code false} when no such event was found.
   * @throws IllegalArgumentException when
   *         {@code size} is less than 0.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   *         than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @since RTSJ 1.0.1
   */
  public boolean unregisterInsertionEvent(long base, long size, AsyncEvent ae);

  /**
   * Unregisters the specified removal event.  The async event is only
   * unregistered when all three arguments match the arguments used to
   * register the event, except that {@code ae} of
   * {@code null} matches all values of {@code ae} and will
   * unregister every {@code ae} that matches the address range.
   * Note that this method has no effect on handlers registered directly as
   * async event handlers.
   *
   * @param base The starting address in physical memory associated with
   * {@code ae}.
   * @param size The size of the memory area associated with {@code ae}.
   * @param ae The async event to unregister.
   * @return {@code true}, when at least one event matched the pattern,
   *         {@code false} when no such event was found.
   *
   * @throws IllegalArgumentException when {@code size} is less
   *         than 0.
   *
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   *         than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @since RTSJ 1.0.1
   */
  public boolean unregisterRemovalEvent(long base, long size, AsyncEvent ae);

  /**
   * Searches for virtual memory of the right type.
   * This is important for systems where attributes are associated with
   * particular ranges of virtual memory.
   *
   * @param base The address at which to start searching.
   *
   * @param size The amount of memory to be found.
   *
   * @return the address where memory was found or -1 when it was not found.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less
   *         than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical
   *         addressing range of the processor.
   *
   * @throws IllegalArgumentException when {@code base} or
   *         {@code size} is negative.
   *         {@code IllegalArgumentException} may also be when
   *         {@code base} is an invalid virtual address.
   */
  public long vFind(long base, long size);
}
