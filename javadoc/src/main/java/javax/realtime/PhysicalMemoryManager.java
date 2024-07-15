/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;


/**
 * <p>The {@code PhysicalMemoryManager} is not ordinarily used by
 * applications, except that the implementation may require the
 * application to use the {@link #registerFilter} method to make the
 * physical memory manager aware of the memory types on their platform.
 * The {@code PhysicalMemoryManager} class is primarily intended
 * for use by the various physical memory accessor objects
 * ({@link VTPhysicalMemory}, {@link LTPhysicalMemory}, and
 * {@link ImmortalPhysicalMemory}) to create objects of the types
 * requested by the application.  The physical memory manager is
 * responsible for finding areas of physical memory with the appropriate
 * characteristics and access rights, and moderating any required
 * combination of physical and virtual memory characteristics.
 *
 * <p>The Physical Memory Manager assumes that the physical adresss
 * space is linear but not necessarily contiguous.  That is, addresses
 * range from 0 .. {@code MAX_LONG}, but there may be gaps in the
 * memory space.  Some intervals in the range may be filled with removable
 * memory as well.
 *
 * <p>The physical memory is partitioned into chunks (pages, segments,
 * etc.). Each chunk of memory has a base address and a length.
 *
 * <p>Each chunk of memory has certain properties. Some of these
 * properties may require actions to be performed by the Physical Memory
 * Manager when the memory is accessed.  For example, access to
 * {@code IO_PAGE} may require the use of special instructions to
 * even reach the devices, or it may require special code sequences to
 * ensure proper handling of processor write queues and caches.
 *
 * <p> Filters tell the Physical Memory Manager about the properties of
 * the memory that are available on the machine by registering with the
 * Physical Memory Manager.
 *
 * <p> When the program requests a physical memory area with particular
 * properties, the constructor communicates with the Physical Memory
 * Manager through a private interface. The Physical Memory Manager asks
 * the filter whether or not the address specified has the required properties
 * and whether it is free, or asks for a chunk of memory with the
 * requested size.
 *
 * <p> The Physical Memory Manager then maps the physical memory chunk
 * into virtual memory and locks the virtual memory to the memory chunk,
 * on systems that support virtual memory.
 *
 * <p> Examples of characteristics that might be specified are DMA
 * memory, hardware byte swapping, and non-cached access to memory.
 * Standard "names" for some memory characteristics are included in this
 * class: DMA, SHARED, ALIGNED, BYTESWAP, and IO_PAGE. Support for
 * these characteristics is optional, but when they are
 * supported they must use these names.  Additional characteristics may
 * be supported, but only names defined in this specification may be
 * visible in the {@code PhysicalMemoryManager} API.
 *
 * <p> The base implementation will provide a
 * {@code PhysicalMemoryManager}.
 *
 * <p> Original Equipment Manufacturers (OEMs) or other interested parties may
 * provide {@link PhysicalMemoryTypeFilter} classes that allow
 * additional characteristics of memory devices to be specified.
 *
 * @deprecated as of RTSJ 2.0
 */
@Deprecated
public final class PhysicalMemoryManager
{
  /**
   * When aligned memory is supported by the implementation,
   * specify {@code ALIGNED} to identify aligned memory.
   * This type of memory ignores low-order bits in load and store
   * accesses to force accesses to fall on natural boundaries for the
   * access type even when the processor uses a poorly aligned address.
   *
   * @see javax.realtime.device.RawMemory
   */
  public static final Object ALIGNED =  null;

  /**
   * When automatic byte swapping is supported by the implementation,
   * specify {@code BYTESWAP} when byte swapping should be used.
   * Byte-swapping memory re-orders the bytes in accesses for 16 bits or
   * more such that little-endian data in memory is accessed as
   * big-endian, and vice-versa.  Such memory would typically be
   * available in swapped mode in one physical address range and in
   * un-swapped mode in another address range.
   *
   * @see javax.realtime.device.RawMemory
   */
  public static final Object BYTESWAP =  null;

  /**
   * When DMA (Direct Memory Access) memory is supported by the
   * implementation, specify {@code DMA} to identify DMA memory.
   * This memory is visible to devices that use DMA.  In some systems,
   * only a portion of the physical address space is available to DMA
   * devices.  On such systems, memory that will be used for DMA must be
   * allocated from the range of addresses that DMA can reach.
   *
   * @see javax.realtime.device.RawMemory
   */
  public static final Object DMA =  null;

  /**
   * When access to the system I/O space is supported by the
   * implementation, specify {@code IO_PAGE} when I/O space should be
   * used.  Addresses tagged with the name {@code IO_PAGE} are used
   * for memory mapped I/O devices.  Such addresses are almost certainly
   * not suitable for physical memory, but only for raw memory access.
   *
   * @since RTSJ 1.0.1
   */
  public static final Object IO_PAGE =  null;

  /**
   * When shared memory is supported by the implementation, specify
   * {@code SHARED} to identify shared memory.  In a NUMA
   * (Non-Uniform Memory Access) architecture, processors may make some
   * part of their local memory available to other processors.  This
   * memory would be tagged with {@code SHARED}, as would memory
   * that is shared and non-local.
   *
   * <p> A fully built-out NUMA system might well need
   * sub-classifications of {@code SHARED} to reflect different
   * paths to memory.  Note that, as with other physical memory names, a
   * single byte of memory may be visible at several physical addresses
   * with different access properties at each address.  For instance, a
   * byte of shared memory accesses at address <em>x</em> might be
   * shared with high-performance access, but without the support of
   * coherent caches.  The same byte accessed at address <em>y</em>
   * might be shared with coherent cache support, but substantially
   * longer access times.
   */
  public static final Object SHARED =  null;

  /**
   * Queries the system about the removability of the specified range of memory.
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @return {@code true}, when any part of the specified range can be removed.
   *
   * @throws IllegalArgumentException when  {@code size} is
   *         less than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical addressing
   *         range of the processor.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is
   *         less than zero.
   */
  public static boolean isRemovable(long base, long size) { return false; }

  /**
   * Queries the system about the removed state of the specified range
   * of memory.  This method is used for devices that lie in the memory
   * address space and can be removed while the system is running. (Such
   * as PC cards).
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @return {@code true}, when any part of the specified range is
   *         currently not usable.
   *
   * @throws IllegalArgumentException when {@code size} is
   *         less than zero.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is
   *         less than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical addressing
   *         range of the processor.
   */
  public static boolean isRemoved(long base, long size) { return false; }

  /**
   * Registers the specified {@link AsyncEvent} to fire when any memory
   * in the range is added to the system.  When the specified range of
   * physical memory contains multiple different types of removable
   * memory, the AE will be registered with each of them.
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @param ae  The async event to fire.
   *
   * @throws IllegalArgumentException when {@code ae} is
   *          {@code null}, or when the specified range contains no removable
   *          memory, or when {@code size} is less than zero.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is
   *         less than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical addressing
   *         range of the processor.
   *
   *  @since RTSJ 1.0.1
   */
  public static void onInsertion(long base, long size, AsyncEvent ae) {}

  /**
   * Registers the specified {@link AsyncEventHandler} to run when any
   * memory in the range is added to the system.
   * When the specified range of physical memory contains multiple different
   * types of removable memory, the AEH will be registered with each of them.
   * When the size or the base is less than 0, unregisters all
   * "onInsertion" references to the handler.
   * <p>
   * Note that this method only removes handlers that were registered with
   * the same method.  It has no effect on handlers that were registered
   * using an associated async event.
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @param aeh  The handler to register.
   *
   * @throws IllegalArgumentException when {@code aeh} is
   *          {@code null}, or when the specified range contains no removable
   *          memory, or when {@code aeh} is {@code null} and
   *          {@code size} and {@code base} are both greater
   *          than or equal to zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical addressing
   *         range of the processor.
   *
   * @deprecated as of RTSJ 1.0.1 Replace with onInsertion(long, long, AsyncEvent)
   */
  @Deprecated
  public static void onInsertion(long base, long size, AsyncEventHandler aeh) {}

  /**
   * Registers the specified AE to fire when any memory in the range is
   * removed from the system.  When the specified range of physical memory
   * contains multiple different types of removable memory, the AE will
   * be registered with each of them.
   *
   * @param base The starting address in physical memory.
   *
   * @param size The size of the memory area.
   *
   * @param ae  The async event to register.
   *
   * @throws IllegalArgumentException when the specified
   *          range contains no removable memory, when {@code ae} is
   *          {@code null},  or when {@code size} is less than zero.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is
   *         less than zero.
   *
   * @throws SizeOutOfBoundsException when {@code base} plus
   *         {@code size} would be greater than the physical addressing
   *          range of the processor.
   */
  public static void onRemoval(long base, long size, AsyncEvent ae) {}

  /**
   * Registers the specified AEH to run when any memory in the range is
   * removed from the system.  When the specified range of physical memory
   * contains multiple different types of removable memory, the AEH will
   * be registered with each of them.  When {@code size} or
   * {@code base} is less than 0, unregisters all "onRemoval"
   * references to the handler parameter.
   *
   * <p> Note that this method only removes handlers that were registered
   * with the same method.  It has no effect on handlers that were
   * registered using an associated async event.
   *
   *  @param base The starting address in physical memory.
   *
   *  @param size The size of the memory area.
   *
   *  @param aeh  The handler to register.
   *
   *  @throws IllegalArgumentException when the specified range
   *          contains no removable memory, or when {@code aeh} is
   *          {@code null} and {@code size} and {@code base} are both
   *          greater than or equal to zero.
   *
   *  @throws SizeOutOfBoundsException when {@code base} plus
   *  {@code size} would be greater than the physical addressing
   *  range of the processor.
   *
   * @deprecated as of RTSJ 1.0.1
   */
  @Deprecated
  public static void onRemoval(long base, long size, AsyncEventHandler aeh)
  {
  }


  /**
   * Registers a memory type filter with the physical memory manager.
   * <p>
   * Values of  {@code name} are compared using reference equality
   * (==) not value equality ({@code equals()}).
   *
   *  @param name The type of memory handled by this filter.
   *
   *  @param filter The filter object.
   *
   *  @throws DuplicateFilterException when a filter for this type
   *          of memory already exists.
   *
   *  @throws ResourceLimitError when the system is configured for
   *          a bounded number of filters.  This filter exceeds the bound.
   *
   *  @throws IllegalArgumentException when the name parameter is
   *          an array of objects, when the name and filter are not both in
   *          immortal memory, or when either {@code name} or
   *          {@code filter} is {@code null}.
   * @throws StaticSecurityException when this operation is not permitted.
   *
   */
  public static final void registerFilter(Object name,
                                          PhysicalMemoryTypeFilter filter)
    throws  DuplicateFilterException
  {
  }


  /** Removes the identified filter from the set of registered filters.
   *  When the filter is not registered, silently does nothing.
   * <p>
   * Values of  {@code name} are compared using reference equality
   * (==) not value equality ({@code equals()}).
   *
   *  @param name The identifying object for this memory attribute.
   *
   *  @throws IllegalArgumentException when {@code name} is {@code null}.
   *
   *  @throws StaticSecurityException when this operation is not permitted.
   */
  public static final void removeFilter(Object name) {}

  /** Unregisters the specified insertion event.  The event is only
   * unregistered when all three arguments match the arguments used to
   * register the event, except that {@code ae} of {@code null} matches all
   * values of {@code ae} and will unregister every {@code ae}
   * that matches the address range.
   * <p>
   * Note that this method has no effect on handlers registered directly as
   * async event handlers.
   *
   * @param base The starting address in physical memory associated with
   * {@code ae}.
   * @param size The size of the memory area associated with {@code ae}.
   * @param ae The event to unregister.
   * @return {@code true}, when at least one event matched the pattern,
   *         {@code false}, when no such event was found.
   *
   *  @throws IllegalArgumentException when
   *      {@code size} is less than 0.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is
   * less than zero.
   *
   *  @throws SizeOutOfBoundsException when {@code base} plus
   *  {@code size} would be greater than the physical addressing
   *  range of the processor.
   *
   * @since RTSJ 1.0.1
   */
  public static boolean unregisterInsertionEvent(long base,
                                                 long size,
                                                 AsyncEvent ae)
  {
    return true;
  }

  /** Unregisters the specified removal event.  The async event is only
   * unregistered when all three arguments match the arguments used to
   * register the event, except that {@code ae} of {@code null} matches all
   * values of {@code ae} and will unregister every {@code ae}
   * that matches the address range.
   *
   * <p>
   * Note that this method has no effect on handlers registered directly as
   * async event handlers.
   *
   * @param base The starting address in physical memory associated with
   * {@code ae}.
   * @param size The size of the memory area associated with {@code ae}.
   * @param ae The async event to unregister.
   * @return {@code true}, when at least one event matched the pattern,
   *         {@code false}, when no such event was found.
   *
   * @throws IllegalArgumentException when {@code size} is less than 0.
   *
   * @throws OffsetOutOfBoundsException when {@code base} is less than zero.
   *
   *  @throws SizeOutOfBoundsException when {@code base} plus
   *  {@code size} would be greater than the physical addressing
   *  range of the processor.
   *
   * @since RTSJ 1.0.1
   */
  public static boolean unregisterRemovalEvent(long base,
                                               long size,
                                               AsyncEvent ae)
  {
    return false;
  }

  /**
   * Private constructor to prevent a default constructor from appearing.
   * This class should not be instantiated except possibly by internal
   * logic.
   */
  private PhysicalMemoryManager() {}
}
