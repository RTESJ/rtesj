/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;


/**
 * An instance of {@code RawMemoryAccess} models a range of
 * physical memory as a fixed sequence of bytes.  A complement of
 * accessor methods enable the contents of the physical area to be
 * accessed through offsets from the base, interpreted as byte, short,
 * int, or long data values or as arrays of these types.
 *
 * <p> Whether an offset addresses the high-order or low-order byte is
 * normally based on the value of the {@link RealtimeSystem#BYTE_ORDER}
 * static byte variable in class {@link RealtimeSystem}.  When the type of
 * memory used for this {@code RawMemoryAccess} region implements
 * non-standard byte ordering, accessor methods in this class continue
 * to select bytes starting at {@code offset} from the base address
 * and continuing toward greater addresses.  The memory type may control
 * the mapping of these bytes into the primitive data type.  The memory
 * type could even select bytes that are not contiguous.  In each case
 * the documentation for the {@link PhysicalMemoryTypeFilter} must
 * document any mapping other than the "normal" one specified above.
 *
 * <p> The {@code RawMemoryAccess} class allows a realtime program
 * to implement device drivers, memory-mapped I/O, flash memory,
 * battery-backed RAM, and similar low-level software.
 *
 * <p> A raw memory area cannot contain references to Java objects.
 * Such a capability would be unsafe (since it could be used to defeat
 * Java's type checking) and error-prone (since it is sensitive to the
 * specific representational choices made by the Java compiler).
 *
 * <p> Many of the constructors and methods in this class throw {@link
 * OffsetOutOfBoundsException}. This exception means that the value
 * given in the offset parameter is either negative or outside the
 * memory area.
 *
 * <p> Many of the constructors and methods in this class throw {@link
 * SizeOutOfBoundsException}. This exception means that the value given
 * in the size parameter is either negative, larger than an allowable
 * range, or would cause an accessor method to access an address outside
 * of the memory area.
 *
 * <p> Unlike other integral parameters in this chapter, negative values
 *  are valid for
 *  {@code byte, short, int,} and {@code long} values that are
 *  copied in and out of memory by the {@code set} and {@code get}
 *  methods of this class.
 *
 *  <p> All offset values used in this class are measured in bytes.
 *
 *  <p> Atomic loads and stores on raw memory are defined in terms of
 *  physical memory.  This memory may be accessible to threads outside the
 *  JVM and to non-programmed access (e.g., DMA), consequently atomic
 *  access must be supported by hardware.  This specification
 *  is written with the assumption that all suitable hardware platforms
 *  support atomic loads for aligned bytes, shorts, and ints.
 *  Atomic access beyond the specified minimum may be supported by the
 *  implementation.
 *
 *  <p> Storing values into raw memory is more hardware-dependent than
 *  loading values.  Many processor architectures do not support atomic
 *  stores of variables except for aligned stores of the processor's
 *  word size.  For instance, storing a byte into memory might require
 *  reading a 32-bit quantity into a processor register, updating the
 *  register to reflect the new byte value, then re-storing the whole
 *  32-bit quantity.  Changes to other bytes in the 32-bit quantity that
 *  take place between the load and the store will be lost.
 *
 * <p> Some processors have mechanisms that can be used to implement an
 * atomic store of a byte, but those mechanisms are often slow and not
 * universally supported.
 *
 * <p> This class supports unaligned access to data, but it does not
 * require the implementation to make such access atomic.  Accesses to
 * data aligned on its natural boundary will be atomic when the processor
 * implements atomic loads and stores of that data size.
 *
 * <p> Except where noted, accesses to raw memory are not atomic with
 * respect to the memory or with respect to schedulables.  A raw
 * memory area could be updated by another schedulable, or even
 * unmapped in the middle of a method.
 *
 * <p> The characteristics of raw-memory access are necessarily platform
 * dependent.  This specification provides a minimum requirement for the
 * RTSJ platform, but it also supports optional system properties that
 * identify a platform's level of support for atomic raw put and get.
 * The properties represent a four-dimensional sparse array with boolean
 * values indicating whether that combination of access attributes is
 * atomic.  The default value for array entries is false.
 *
 *  The dimension are
 * <table width="95%" border="1">
 *   <caption>Properties Array</caption>
 * <tr>
 *   <td width="25%" align="center"><div><strong>Attribute</strong></div></td>
 *   <td width="25%" align="center"><div><strong>Values</strong></div></td>
 *   <td width="50%" align="center"><div><strong>Comment</strong></div></td>
 * </tr>
 * <tr>
 *   <td>Access type</td>
 *   <td>read, write</td>
 *   <td>&nbsp;</td>
 * </tr>
 * <tr><td>Data type</td>
 *     <td>byte, short, int, long, float, double</td>
 *     <td>&nbsp;</td>
 * </tr>
 * <tr>
 *   <td rowspan="2">Alignment</td>
 *   <td>0</td>
 *   <td>aligned</td>
 * </tr>
 * <tr>
 *   <td>1 to one less than data type size</td>
 *   <td>the first byte of the data is <em>alignment</em> bytes away
 *    from natural alignment.
 *   </td>
 * </tr>
 * <tr>
 *   <td rowspan="3">Atomicity</td>
 *   <td>processor</td>
 *   <td>means access is atomic with respect to other taska on processor.</td>
 * </tr>
 * <tr>
 *   <td>smp</td>
 *   <td>means access is <em>processor</em> atomic, and atomic
 *         with respect to all processors in an SMP.</td>
 * </tr>
 * <tr>
 *   <td>memory</td>
 *   <td>means that access is <em>smp</em> atomic, and atomic with respect
 *       to all access to the memory including DMA.</td>
 * </tr>
 * </table>
 *
 *  The true values in the table are represented by properties of the
 *  following form.
 * javax.realtime.atomicaccess_&lt;access&gt;_&lt;type&gt;_&lt;alignment&gt;_atomicity=true
 *  for example:
 * <pre>
 *  javax.realtime.atomicaccess_read_byte_0_memory=true
 * </pre>
 *  Table entries with a value of false may be explicitly represented,
 *  but since false is the default value, such properties are redundant.
 *
 * <p> All raw memory access is treated as volatile, and
 * <em>serialized</em>.  The run-time must be forced to re-read memory
 * or write to memory on each call to a raw memory {@code get<type>} or {@code put<type>}
 * method, where {@code type} is defined in the table above, and to complete
 * the reads and writes in the order they appear in the program order.
 *
 * @deprecated as of RTSJ 2.0.
 *  Use {@link javax.realtime.device.RawMemoryFactory} to create the
 *  appropriate {@link javax.realtime.device.RawMemory} object.
 */
@Deprecated
public class RawMemoryAccess
{
  /**
   * Constructs an instance of {@code RawMemoryAccess} with the
   * given parameters, and sets the object to the mapped state.  When the
   * platform supports virtual memory, maps the raw memory into virtual
   * memory.
   *
   * <p> The run time environment is allowed to choose the virtual
   * address where the raw memory area corresponding to this object will
   * be mapped.  The attributes of the mapping operation are controlled
   * by the vMFlags and vMAttributes of the
   * {@code PhysicalMemoryTypeFilter} objects that matched this
   * object's {@code type} parameter.
   *
   * (See {@link PhysicalMemoryTypeFilter#getVMAttributes} and
   * {@link PhysicalMemoryTypeFilter#getVMFlags}.
   *
   * @param type An instance of {@code Object} representing the
   *        type of memory required (e.g., <em>dma, shared</em>) - used
   *        to define the base address and control the mapping.  When the
   *        required memory has more than one attribute,
   *        {@code type} may be an array of objects.  When
   *        {@code type} is {@code null} or a reference to an
   *        array with no entries, any type of memory is
   *        acceptable. Note that {@code type} values are compared
   *        by reference (==), not by value ({@code equals}).
   *
   * @param base The physical memory address of the region.
   *
   * @param size The size of the area in bytes.
   *
   * @throws StaticSecurityException when application doesn't have
   *            permissions to access physical memory, the
   *              specified range of addresses, or the given type of memory.
   *
   * @throws OffsetOutOfBoundsException when the address is invalid.
   *
   * @throws SizeOutOfBoundsException when the size is negative or
   *            extends into an invalid range of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws MemoryTypeConflictException when the specified base
   *            does not point to memory that matches the request type,
   *            or when {@code type} specifies incompatible memory
   *            attributes.
   *
   * @throws OutOfMemoryError when the requested type of memory
   *      exists, but there is not enough of it free to satisfy the
   *      request.
   */
  public RawMemoryAccess(Object type, long base, long size) {}

  /**
   * Constructs an instance of {@code RawMemoryAccess} with the
   * given parameters, and sets the object to the mapped state.  When the
   * platform supports virtual memory, maps the raw memory into virtual
   * memory.
   *
   * <p> The run time environment is allowed to choose the virtual
   * address where the raw memory area corresponding to this object will
   * be mapped.  The attributes of the mapping operation are controlled
   * by the vMFlags and vMAttributes of the
   * {@code PhysicalMemoryTypeFilter} objects that matched this
   * object's {@code type} parameter.  (See
   * {@link PhysicalMemoryTypeFilter#getVMAttributes} and
   * {@link PhysicalMemoryTypeFilter#getVMFlags}.
   *
   * @param type An instance of {@code Object} representing the
   *        type of memory required (e.g., <em>dma, shared</em>) - used
   *        to define the base address and control the mapping.  When the
   *        required memory has more than one attribute,
   *        {@code type} may be an array of objects.  When
   *        {@code type} is {@code null} or a reference to an
   *        array with no entries, any type of memory is
   *        acceptable. Note that {@code type} values are compared
   *        by reference (==), not by value ({@code equals}).
   *
   * @param size The size of the area in bytes.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory, the
   *              specified range of addresses, or the given type of memory.
   *
   * @throws SizeOutOfBoundsException when the size is negative or
   *            extends into an invalid range of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws MemoryTypeConflictException when the specified base
   *            does not point to memory that matches the request type,
   *            or when {@code type} specifies incompatible memory
   *            attributes.
   *
   * @throws OutOfMemoryError when the requested type of memory
   *      exists, but there is not enough of it free to satisfy the
   *      request.
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given range
   *            of memory.
   */
  public RawMemoryAccess(Object type, long size) {}

  /**
   * Gets the {@code byte} at the given offset in the memory area
   *  associated with this object.  The byte is always loaded from memory
   *  in a single atomic operation.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw memory
   *  from which to load the byte.
   *
   * @return the byte from raw memory.
   *
   * @throws SizeOutOfBoundsException when the object is not mapped,
   *      or when the byte falls in an invalid address range.
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   *
   * @see RawMemoryAccess#map(long,long)
   */
  public byte getByte(long offset) { return 0; }

  /**
   * Gets {@code number} bytes starting at the given offset in the
   * memory area associated with this object and assigns them to the
   * byte array passed starting at position {@code low}.  Each byte
   * is loaded from memory in a single atomic operation.  Groups of
   * bytes may be loaded together, but this is unspecified.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *        memory from which to start loading.
   *
   * @param bytes The array into which the loaded items are placed.
   *
   * @param low The offset which is the starting point in the given
   *            array for the loaded items to be placed.
   *
   * @param number The number of items to load.
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not
   *      mapped, or when a byte falls in an invalid address range.
   *      This is checked at every entry in the array to allow for the
   *      possibility that the memory area could be unmapped or
   *      remapped.  The {@code byte} array could, therefore, be
   *      partially updated when the raw memory is unmapped or remapped
   *      mid-method.
   *
   * @throws ArrayIndexOutOfBoundsException when {@code low}
   *            is less than 0 or greater than <code>bytes.length -
   *            1</code>, or when <code>low + number</code> is greater
   *            than or equal to {@code bytes.length}.
   *
   * @throws StaticSecurityException when this access is not permitted by
   *         the security manager.
   *
   * @see RawMemoryAccess#map(long,long)
   */
  public void getBytes(long offset, byte[] bytes, int low, int number) {}


  /**
   * Gets the {@code int} at the given offset in the memory area
   * associated with this object.  When the integer is aligned on a
   * "natural" boundary it is always loaded from memory in a single
   * atomic operation.  When it is not on a natural boundary it may not be
   * loaded atomically, and the number and order of the load operations
   * is unspecified.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *      memory area from which to load the integer.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException  when the object is not mapped,
   *      or when the integer falls in an invalid address range.
   *
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   *
   * @return the integer from raw memory.
   */
  public int getInt(long offset) { return 0; }

  /**
   * Gets {@code number} integers starting at the given offset in
   * the memory area associated with this object and assign them to the
   *  {@code int} array passed starting at position {@code low}.
   *
   * <p> When the integers are aligned on natural boundaries each integer
   * is loaded from memory in a single atomic operation.  Groups of
   * integers may be loaded together, but this is unspecified.  <p> When
   * the integers are not aligned on natural boundaries they may not be
   * loaded atomically and the number and order of load operations is
   * unspecified.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *      memory area at which to start loading.
   *
   * @param ints The array into which the integers read from the raw
   * memory are placed.
   *
   * @param low The offset which is the starting point in the given
   *            array for the loaded items to be placed.
   *
   * @param number The number of integers to load.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not
   *      mapped, or when an integer falls in an invalid address range.
   *      This is checked at every entry in the array to allow for the
   *      possibility that the memory area could be unmapped or
   *      remapped.  The {@code int} array could, therefore, be
   *      partially updated when the raw memory is unmapped or remapped
   *      mid-method.
   *
   * @throws ArrayIndexOutOfBoundsException when {@code low}
   *            is less than 0 or greater than <code>bytes.length -
   *            1</code>, or when <code>low + number</code> is greater
   *            than or equal to {@code bytes.length}.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void getInts(long offset, int[] ints, int low, int number) {}

  /**
   * Gets the {@code long} at the given offset in the memory area
   * associated with this object.
   *
   * <p> The load is not required to be atomic even it is located on a
   * natural boundary.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw memory area
   *      from which to load the long.
   *
   * @throws OffsetOutOfBoundsException when the offset is invalid.
   *
   * @throws SizeOutOfBoundsException  when the object is not mapped,
   *      or when the long falls in an invalid address range.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   *
   * @return the long from raw memory.
   */
  public long getLong(long offset) { return 0; }

  /**
   * Gets {@code number} longs starting at the given offset in the
   * memory area associated with this object and assign them to the {@code longs}
   * array passed starting at position {@code low}.
   *
   * <p> The loads are not required to be atomic even when they are
   * located on natural boundaries.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw memory area
   *      at which to start loading.
   *
   * @param longs The array into which the loaded items are placed.
   *
   * @param low The offset which is the starting point in the given
   *            array for the loaded items to be placed.
   *
   * @param number The number of longs to load.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not
   *      mapped, or when a long falls in an invalid address range.  This
   *      is checked at every entry in the array to allow for the
   *      possibility that the memory area could be unmapped or
   *      remapped.  The {@code long} array could, therefore, be
   *      partially updated when the raw memory is unmapped or remapped
   *      mid-method.
   *
   * @throws ArrayIndexOutOfBoundsException when {@code low}
   *            is less than 0 or greater than <code>bytes.length -
   *            1</code>, or when <code>low + number</code> is greater
   *            than or equal to {@code bytes.length}.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void getLongs(long offset, long[] longs, int low, int number) {}


  /**
   * Gets the virtual memory location at which the memory region is mapped.
   *
   * @return the virtual address to which {@code this} is mapped, for reference
   *         purposes.  When virtual memory is not supported, this is
   *         the same as the physical base address.
   *
   *  @throws IllegalStateException when the raw memory object is
   *      not in the mapped state.
   */
  public long getMappedAddress() { return 0; }

  /**
   * Gets the {@code short} at the given offset in the memory area
   * associated with this object. When the short is aligned on a natural
   * boundary it is always loaded from memory in a single atomic
   * operation.  When it is not on a natural boundary it may not be loaded
   * atomically, and the number and order of the load operations is
   * unspecified.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *      memory area from which to load the short.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException  when the object is not mapped,
   *      or when the short falls in an invalid address range.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   *
   * @return the short loaded from raw memory.
   */
  public short getShort(long offset) { return 0; }

  /**
   * Gets {@code number} shorts starting at the given offset in the
   * memory area associated with this object and assign them to the
   * {@code short} array passed starting at position {@code low}.
   *
   * <p> When the shorts are located on natural boundaries each short is
   * loaded from memory in a single atomic operation.  Groups of shorts
   * may be loaded together, but this is unspecified.
   *
   * <p> When the shorts are not located on natural boundaries the load
   * may not be atomic, and the number and order of load operations is
   * unspecified.
   * <p> Caching of the memory access is controlled by the
   * memory {@code type} requested when the
   * {@code RawMemoryAccess} instance was created.  When the memory
   * is not cached, this method guarantees serialized access (that is,
   * the memory access at the memory occurs in the same order as in the
   * program.  Multiple writes to the same location may not be
   * coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area from which to start loading.
   *
   * @param shorts The array into which the loaded items are placed.
   *
   * @param low The offset which is the starting point in the given
   *            array for the loaded shorts to be placed.
   *
   * @param number The number of shorts to load.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not
   *      mapped, or when a short falls in an invalid address range.  This
   *      is checked at every entry in the array to allow for the
   *      possibility that the memory area could be unmapped or
   *      remapped.  The {@code short} array could, therefore, be
   *      partially updated when the raw memory is unmapped or remapped
   *      mid-method.
   *
   * @throws ArrayIndexOutOfBoundsException when {@code low}
   *            is less than 0 or greater than <code>bytes.length -
   *            1</code>, or when <code>low + number</code> is greater
   *            than or equal to {@code bytes.length}.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void getShorts(long offset, short[] shorts, int low, int number) {}

  /**
   * Maps the physical memory range into virtual memory. No-op when the system
   * doesn't support virtual memory.
   *
   * <p> The run time environment is allowed to choose the virtual
   * address where the raw memory area corresponding to this object will
   * be mapped.  The attributes of the mapping operation are controlled
   * by the vMFlags and vMAttributes of the
   * {@code PhysicalMemoryTypeFilter} objects that matched this
   * object's {@code type} parameter.
   * (See {@link PhysicalMemoryTypeFilter#getVMAttributes} and
   * {@link PhysicalMemoryTypeFilter#getVMFlags}.
   *
   * <p> When the object is already mapped into virtual memory, this
   * method does not change anything.
   *
   * @return the starting point of the virtual memory range.
   *
   * @throws OutOfMemoryError when there is insufficient free
   *      virtual address space to map the object.
   */
  public long map() { return 0; }

  /**
   * Maps the physical memory range into virtual memory at the specified
   * location. No-op when the system doesn't support virtual memory.
   * <p>
   *  The attributes of the mapping operation are controlled by the vMFlags and
   *  vMAttributes of the {@code PhysicalMemoryTypeFilter} objects
   *  that matched this object's {@code type} parameter.  (See
   *  {@link PhysicalMemoryTypeFilter#getVMAttributes}
   *  and {@link PhysicalMemoryTypeFilter#getVMFlags}.
   * <p>
   *  When the object is already mapped into virtual memory at a different
   *  address, this method remaps it to {@code base}.
   * <p>
   *  When a remap is requested while another schedulable
   *  is accessing the raw memory, the
   *  map will block until one load or store completes.  It can interrupt
   *  an array operation between entries.
   *
   * @param base The location to map at the virtual memory space.
   *
   * @return the starting point of the virtual memory.
   *
   * @throws OutOfMemoryError when there is insufficient free virtual
   *      memory at the specified address.
   *
   * @throws IllegalArgumentException when {@code base} is
   *      not a legal value for a virtual address, or the memory-mapping
   *      hardware cannot place the physical memory at the designated
   *      address.
   */
 public long map(long base) { return 0; }

  /**
   * Maps the physical memory range into virtual memory. No-op when the system
   * doesn't support virtual memory.
   * <p>
   *  The attributes of the mapping operation are controlled by the vMFlags and
   *  vMAttributes of the {@code PhysicalMemoryTypeFilter} objects
   *  that matched this object's {@code type} parameter.
   *  (See {@link PhysicalMemoryTypeFilter#getVMAttributes}
   *  and {@link PhysicalMemoryTypeFilter#getVMFlags}.
   * <p>
   *  When the object is already mapped into virtual memory at a different
   *  address, this method remaps it to {@code base}.
   * <p>
   *  When a remap is requested while another schedulable is
   *  accessing the raw memory, the
   *  map will block until one load or store completes.  It can interrupt
   *  an array operation between entries.

   * @param base The location to map at the virtual memory space.
   *
   * @param size The size of the block to map in.  When the size of the
   *     raw memory area is greater than {@code size}, the object
   *     is unchanged but accesses beyond the mapped region will throw
   *     {@link SizeOutOfBoundsException}.  When the size of the raw
   *     memory area is smaller than the mapped region, access to the raw
   *     memory will behave as if the mapped region matched the raw
   *     memory area, but additional virtual address space will be
   *     consumed after the end of the raw memory area.
   *
   * @return the starting point of the virtual memory.
   *
   * @throws IllegalArgumentException when size is not greater than
   *      zero, {@code base} is not a legal value for a virtual
   *      address, or the memory-mapping hardware cannot place the
   *      physical memory at the designated address.
   */
  public long map(long base, long size) { return 0; }

  /**
   * Sets the {@code byte} at the given offset in the memory area
   * associated with this object.
   *
   * <p> This memory access may involve a load and a store, and it may
   * have unspecified effects on surrounding bytes in the presence of
   * concurrent access.
   *
   * <p> Caching of the memory access is controlled by the memory
   *  {@code type} requested when the {@code RawMemoryAccess}
   *  instance was created.  When the memory is not cached, this method
   *  guarantees serialized access (that is, the memory access at the
   *  memory occurs in the same order as in the program.  Multiple
   *  writes to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area to which to write the byte.
   *
   * @param value The byte to write.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not mapped,
   *      or when the byte falls in an invalid address range.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void setByte(long offset, byte value)  {}

  /**
   * Sets {@code number} bytes starting at the given offset in the
   * memory area associated with this object from the
   * {@code byte} array passed starting at position {@code low}.
   *
   * <p> This memory access may involve multiple load and a store
   * operations, and it may have unspecified effects on surrounding
   * bytes (even bytes in the range being stored) in the presence of
   * concurrent access.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area to which to start writing.
   *
   * @param bytes The array from which the items are obtained.
   *
   * @param low The offset which is the starting point in the given
   *            array for the items to be obtained.
   *
   * @param number The number of items to write.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not
   *      mapped, or when a byte falls in an invalid address range.
   *      This is checked at every entry in the array to allow for the
   *      possibility that the memory area could be unmapped or
   *      remapped.  The store of the array into memory could,
   *      therefore, be only partially complete when the raw memory is
   *      unmapped or remapped mid-method.
   *
   * @throws ArrayIndexOutOfBoundsException when {@code low}
   *            is less than 0 or greater than <code>bytes.length -
   *            1</code>, or when <code>low + number</code> is greater
   *            than or equal to {@code bytes.length}.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void setBytes(long offset, byte[] bytes, int low, int number) {}


  /**
   * Sets the {@code int} at the given offset in the memory area
   * associated with this object.  On most processor architectures an
   * aligned integer can be stored in an atomic operation, but this is
   * not required.
   *
   * <p> This memory access may involve multiple load and a store
   * operations, and it may have unspecified effects on surrounding
   * bytes (even bytes in the range being stored) in the presence of
   * concurrent access.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area at which to write the integer.
   *
   * @param value The integer to write.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not mapped,
   *      or when the integer falls in an invalid address range.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void setInt(long offset, int value)  {}

  /**
   * Sets {@code number} ints starting at the given offset in the
   * memory area associated with this object from the {@code int} array passed
   * starting at position {@code low}.  On most processor
   * architectures each aligned integer can be stored in an atomic
   * operation, but this is not required.
   *
   * <p> This memory access may involve multiple load and a store
   * operations, and it may have unspecified effects on surrounding
   * bytes (even bytes in the range being stored) in the presence of
   * concurrent access.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area at which to start writing.
   *
   * @param ints The array from which the items are obtained.
   *
   * @param low The offset which is the starting point in the given
   *            array for the items to be obtained.
   *
   * @param number The number of items to write.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not
   *      mapped, or when an integer falls in an invalid address range.  This
   *      is checked at every entry in the array to allow for the
   *      possibility that the memory area could be unmapped or
   *      remapped.  The store of the array into memory could,
   *      therefore, be only partially complete when the raw memory is
   *      unmapped or remapped mid-method.
   *
   * @throws ArrayIndexOutOfBoundsException when {@code low}
   *            is less than 0 or greater than <code>bytes.length -
   *            1</code>, or when <code>low + number</code> is greater
   *            than or equal to {@code bytes.length}.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   *
   */
  public void setInts(long offset, int[] ints, int low, int number) {}


  /**
   * Sets the {@code long} at the given offset in the memory area
   *  associated with this object.  Even when it is aligned, the long
   *  value may not be updated atomically.  It is unspecified how many
   *  load and store operations will be used or in what order.
   *
   * <p> This memory access may involve multiple load and a store
   * operations, and it may have unspecified effects on surrounding
   * bytes (even bytes in the range being stored) in the presence of
   * concurrent access.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area at which to write the long.
   *
   * @param value The long to write.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not mapped,
   *      or when the long falls in an invalid address range.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void setLong(long offset, long value) {}

  /**
   * Sets {@code number} longs starting at the given offset in the
   * memory area associated with this object from the {@code long} array passed
   * starting at position {@code low}.  Even when they are aligned,
   * the long values may not be updated atomically.  It is unspecified
   * how many load and store operations will be used or in what order.
   *
   * <p> This memory access may involve multiple load and a store
   * operations, and it may have unspecified effects on surrounding
   * bytes (even bytes in the range being stored) in the presence of
   * concurrent access.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area at which to start writing.
   *
   * @param longs The array from which the items are obtained.
   *
   * @param low The offset which is the starting point in the given
   *            array for the items to be obtained.
   *
   * @param number The number of items to write.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not
   *      mapped, or when a long falls in an invalid address range.
   *      This is checked at every entry in the array to allow for the
   *      possibility that the memory area could be unmapped or
   *      remapped.  The store of the array into memory could,
   *      therefore, be only partially complete when the raw memory is
   *      unmapped or remapped mid-method.
   *
   * @throws ArrayIndexOutOfBoundsException when {@code low}
   *            is less than 0 or greater than <code>bytes.length -
   *            1</code>, or when <code>low + number</code> is greater
   *            than or equal to {@code bytes.length}.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void setLongs(long offset, long[] longs, int low, int number) {}

  /**
   * Sets the {@code short} at the given offset in the memory area
   *  associated with this object.
   *
   * <p> This memory access may involve a load and a store, and it may
   * have unspecified effects on surrounding shorts in the presence of
   * concurrent access.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area at which to write the short.
   *
   * @param value The short to write.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException  when the object is not mapped,
   *      or when the short falls in an invalid address range.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void setShort(long offset, short value) {}

  /**
   * Sets {@code number} shorts starting at the given offset in the
   *  memory area associated with this object from the {@code short} array
   *  passed starting at position {@code low}.
   *
   * <p> Each write of a short value may involve a load and a store, and
   * it may have unspecified effects on surrounding shorts in the
   * presence of concurrent access - even on other shorts in the array.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created.  When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program.  Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *       memory area at which to start writing.
   *
   * @param shorts The array from which the items are obtained.
   *
   * @param low The offset which is the starting point in the given
   *            array for the items to be obtained.
   *
   * @param number The number of items to write.
   *
   * @see RawMemoryAccess#map(long,long)
   *
   * @throws OffsetOutOfBoundsException when the offset is negative
   *      or greater than the size of the raw memory area.  The role of
   *      the {@link SizeOutOfBoundsException} somewhat overlaps this
   *      exception since it is when the offset is within the
   *      object but outside the mapped area.
   *
   * @throws SizeOutOfBoundsException when the object is not
   *      mapped, or when a short falls in an invalid address range.
   *      This is checked at every entry in the array to allow for the
   *      possibility that the memory area could be unmapped or
   *      remapped.  The store of the array into memory could,
   *      therefore, be only partially complete when the raw memory is
   *      unmapped or remapped mid-method.
   *
   *
   * @throws ArrayIndexOutOfBoundsException when {@code low}
   *            is less than 0 or greater than <code>bytes.length -
   *            1</code>, or when <code>low + number</code> is greater
   *            than or equal to {@code bytes.length}.
   *
   * @throws StaticSecurityException when this access is not permitted by
   * the security manager.
   */
  public void setShorts(long offset, short[] shorts, int low, int number) {}

  /**
   * Unmaps the physical memory range from virtual memory. This changes
   * the raw memory from the mapped state to the unmapped state. When the
   * platform supports virtual memory, this operation frees the virtual
   * addresses used for the raw memory region.
   *
   * <p> When the object is already in the unmapped state, this method has
   *  no effect.
   *
   * <p> While a raw memory object is unmapped all attempts to set or
   * get values in the raw memory will throw {@link SizeOutOfBoundsException}.
   *
   * <p> An unmapped raw memory object can be returned to mapped state
   * with any of the object's {@code map} methods.
   *
   * <p> When an unmap is requested while another schedulable is
   * accessing the raw  memory, the {@code unmap} will throw an
   * {@code IllegalStateException}.  The {@code unmap} method
   * can interrupt an array operation between entries.
   */
  public void unmap() {}
}
