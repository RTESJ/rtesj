/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This class holds the accessor methods for accessing a raw memory area
 * by float and double types. Implementations are required to implement
 * this class when and only when the underlying Java Virtual Machine supports
 * floating point data types.
 *
 * <p> <em> See {@link RawMemoryAccess} for commentary on changes in the
 * preferred use of this class following RTSJ 2.0.  </em>
 *
 * <p> By default, the byte addressed by {@code offset} is the byte
 * at the lowest address of the floating point processor's floating
 * point representation. When the type of memory used for this
 * {@code RawMemoryFloatAccess} region implements a non-standard
 * floating point format, accessor methods in this class continue to
 * select bytes starting at {@code offset} from the base address
 * and continuing toward greater addresses. The memory type may control
 * the mapping of these bytes into the primitive data type. The memory
 * type could even select bytes that are not contiguous. In each case
 * the documentation for the {@link PhysicalMemoryTypeFilter} must
 * document any mapping other than the "normal" one specified above.
 *
 * <p> All offset values used in this class are measured in bytes.
 *
 * <p> Atomic loads and stores on raw memory are defined in terms of
 * physical memory. This memory may be accessible to threads outside the
 * JVM and to non-programmed access, e.g., DMA, consequently atomic
 * access must be supported by hardware. This specification is written
 * with the assumption that all suitable hardware platforms support
 * atomic loads for aligned floats.  Atomic access beyond the specified
 * minimum may be supported by the implementation.
 *
 * <p> Storing values into raw memory is more hardware-dependent than
 * loading values. Many processor architectures do not support atomic
 * stores of variables except for aligned stores of the processor's word
 * size.
 *
 * <p> This class supports unaligned access to data, but it does not
 * require the implementation to make such access atomic. Accesses to
 * data aligned on its natural boundary will be atomic when the processor
 * implements atomic loads and stores of that data size.
 *
 * <p> Except where noted, accesses to raw memory are not atomic with
 * respect to the memory or with respect to threads. A raw memory area
 * could be updated by another thread, or even unmapped in the middle of
 * a method.
 *
 * <p> The characteristics of raw-memory access are necessarily platform
 * dependent.  This specification provides a minimum requirement for the
 * RTSJ platform, but it also supports optional system properties that
 * identify a platform's level of support for atomic raw put and
 * get. (See {@link RawMemoryAccess}.)  The properties represent a
 * four-dimensional sparse array with boolean values, indicating whether that
 * combination of access attributes is atomic. The default value for
 * array entries is false.
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
 * @deprecated as of RTSJ 2.0. Use {@link javax.realtime.device.RawMemory}.
 */
@Deprecated
public class RawMemoryFloatAccess extends RawMemoryAccess
{
  /**
   * Constructs an instance of {@code RawMemoryFloatAccess} with the
   * given parameters, and sets the object to the mapped state.  When the
   * platform supports virtual memory, maps the raw memory into virtual
   * memory.
   *
   * <p> The run time environment is allowed to choose the virtual
   * address where the raw memory area corresponding to this object will
   * be mapped. The attributes of the mapping operation are controlled
   * by the vMFlags and vMAttributes of the
   * {@code PhysicalMemoryTypeFilter} objects that matched this
   * object's {@code type} parameter. (See
   * {@link PhysicalMemoryTypeFilter#getVMAttributes} and
   * {@link PhysicalMemoryTypeFilter#getVMFlags}.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          for defining the base address and controlling the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @param base
   *          The physical memory address of the region.
   * @param size
   *          The size of the area in bytes.
   * @throws StaticSecurityException
   *              when the application doesn't have
   *              permissions to access physical memory, the
   *              specified range of addresses, or the given type of memory.
   * @throws OffsetOutOfBoundsException
   *              when the address is invalid.
   * @throws SizeOutOfBoundsException
   *              when the size is negative or
   *              extends into an invalid range of memory.
   * @throws UnsupportedPhysicalMemoryException
   *              when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException
   *              when the specified base does not point to
   *              memory that matches the request type, or when {@code type}
   *              specifies incompatible memory attributes.
   * @throws OutOfMemoryError
   *              when the requested type of memory exists, but there is
   *              not enough of it free to satisfy the request.
   */
  public RawMemoryFloatAccess(Object type, long base, long size)
  {
    super(type, base, size);
  }

  /**
   * Constructs an instance of {@code RawMemoryFloatAccess} with the given
   * parameters, and sets the object to the mapped state.
   * When the platform supports virtual memory, maps
   * the raw memory into virtual memory.
   *
   * <p> The run time environment is allowed to choose the virtual
   * address where the raw memory area corresponding to this object will
   * be mapped. The attributes of the mapping operation are controlled
   * by the vMFlags and vMAttributes of the
   * {@code PhysicalMemoryTypeFilter} objects that matched this
   * object's {@code type} parameter. (See
   * {@link PhysicalMemoryTypeFilter#getVMAttributes} and
   * {@link PhysicalMemoryTypeFilter#getVMFlags}.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>),
   *          for defining the base address and controlling the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @param size The size of the area in bytes.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory, the specified
   *              range of addresses, or the given type of memory.
   * @throws SizeOutOfBoundsException when the size is negative or
   *              extends into an invalid range of memory.
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *              hardware does not support the given type, or when no
   *              matching {@link PhysicalMemoryTypeFilter} has been
   *              registered with the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException when the specified base does
   *              not point to memory that matches the request type, or
   *              when {@code type} specifies incompatible memory
   *              attributes.
   * @throws OutOfMemoryError when the requested type of memory exists,
   *              but there is not enough of it free to satisfy the
   *              request.
   */
  public RawMemoryFloatAccess(Object type, long size)
  {
    super(type, size);
  }

  /**
   * Gets the {@code double} at the given offset in the memory area
   * associated with this object.
   *
   * <p> The load is not required to be atomic even it is located on a
   * natural boundary.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created. When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program. Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset
   *          The offset in bytes from the beginning of the raw memory area
   *          from which to load the double.
   * @throws OffsetOutOfBoundsException
   *           when the offset is invalid.
   * @throws SizeOutOfBoundsException
   *           when the object is not mapped,
   *           or when the double falls in an invalid address range.
   * @throws StaticSecurityException
   *           when this access is not permitted by the security manager.
   * @return the double from raw memory.
   */
  public double getDouble(long offset) { return 0; }

  /**
   * Gets {@code number} doubles starting at the given offset in
   * the memory area associated with this object and assigns them to the
   * {@code double} array passed starting at position {@code low}.
   *
   * <p> The loads are not required to be atomic even when they are
   * located on natural boundaries.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created. When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program. Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset
   *          The offset in bytes from the beginning of the raw memory area
   *          at which to start loading.
   * @param doubles
   *          The array into which the loaded items are placed.
   * @param low The offset which is the starting point in the given
   *          array for the loaded items to be placed.
   * @param number
   *          The number of doubles to load.
   * @throws OffsetOutOfBoundsException
   *           when the offset is negative or greater than the size of the
   *           raw memory area. The role of the
   *           {@code SizeOutOfBoundsException} somewhat overlaps
   *           this exception since it is when the offset is within the
   *           object but outside the
   *           mapped area. (See {@link RawMemoryAccess#map(long,long)}
   *           ).
   * @throws SizeOutOfBoundsException when the object is not mapped, or
   *           when a double falls in an invalid address range. This is
   *           checked at every entry in the array to allow for the
   *           possibility that the memory area could be unmapped or
   *           remapped. The {@code double} array could,
   *           therefore, be partially updated when the raw memory is
   *           unmapped or remapped mid-method.
   * @throws ArrayIndexOutOfBoundsException
   *           when {@code low} is less than 0 or greater
   *           than <code>bytes.length - 1</code>, or when
   *           <code>low + number</code> is greater than or
   *           equal to {@code bytes.length}.
   * @throws StaticSecurityException
   *           when this access is not permitted by the security manager.
   */
  public void getDoubles(long offset, double[] doubles, int low, int number) {}

  /**
   * Gets the {@code float} at the given offset in the memory area
   * associated with this object. When the float is aligned on a "natural"
   * boundary it is always loaded from memory in a single atomic
   * operation. When it is not on a natural boundary it may not be loaded
   * atomically, and the number and order of the load operations is
   * unspecified.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created. When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program. Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *          memory area from which to load the float.
   * @throws OffsetOutOfBoundsException when the offset is negative or
   *           greater than the size of the raw memory area. The role of
   *           the {@code SizeOutOfBoundsException} somewhat
   *           overlaps this exception since it is when the offset is
   *           within the object but outside the mapped area. (See
   *           {@link RawMemoryAccess#map(long,long)} ).
   * @throws SizeOutOfBoundsException when the object is not mapped, or
   *           when the float falls in an invalid address range.
   * @throws StaticSecurityException when this access is not permitted by the
   *           security manager.
   * @return the float from raw memory.
   */
  public float getFloat(long offset) { return 0; }

  /**
   * Gets {@code number} floats starting at the given offset in the
   * memory area associated with this object and assign them to the
   * {@code floats} array passed starting at position {@code low}.
   *
   * <p> When the floats are aligned on natural boundaries each float is
   * loaded from memory in a single atomic operation. Groups of floats
   * may be loaded together, but this is unspecified.
   *
   * <p> When the floats are not aligned on natural boundaries they may
   * not be loaded atomically and the number and order of load
   * operations is unspecified.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created. When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program. Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *          memory area at which to start loading.
   * @param floats The array into which the floats loaded from the raw
   *          memory are placed.
   * @param low The offset which is the starting point in the given
   *          array for the loaded items to be placed.
   * @param number The number of floats to load.
   * @throws OffsetOutOfBoundsException
   *           when the offset is negative or greater than the size of the
   *           raw memory area. The role of the
   *           {@code SizeOutOfBoundsException} somewhat overlaps
   *           this exception since it is when the offset is within the
   *           object but outside the
   *           mapped area. (See {@link RawMemoryAccess#map(long,long)}
   *           ).
   * @throws SizeOutOfBoundsException when the object is not mapped, or
   *           when a float falls in an invalid address range. This is
   *           checked at every entry in the array to allow for the
   *           possibility that the memory area could be unmapped or
   *           remapped. The {@code float} array could, therefore,
   *           be partially updated when the raw memory is unmapped or
   *           remapped mid-method.
   * @throws ArrayIndexOutOfBoundsException when {@code low} is
   *           less than 0 or greater than <code>bytes.length - 1</code>,
   *           or when <code>low + number</code> is greater than
   *           or equal to {@code bytes.length}.
   * @throws StaticSecurityException
   *           when this access is not permitted by the security manager.
   */
  public void getFloats(long offset, float[] floats, int low, int number) {}

  /**
   * Sets the {@code double} at the given offset in the memory area
   * associated with this object.  Even when it is aligned, the double
   * value may not be updated atomically. It is unspecified how many
   * load and store operations will be used or in what order.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created. When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program. Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset
   *          The offset in bytes from the beginning of the raw memory area
   *          at which to write the double.
   * @param value
   *          The double to write.
   * @throws OffsetOutOfBoundsException
   *           when the offset is negative or greater than the size of the
   *           raw memory area. The role of the
   *           {@code SizeOutOfBoundsException} somewhat overlaps
   *           this exception since it is when the offset is within the
   *           object but outside the
   *           mapped area. (See {@link RawMemoryAccess#map(long,long)}
   *           ).
   * @throws SizeOutOfBoundsException
   *           when the object is not mapped,
   *           or when the double falls in an invalid address range.
   * @throws StaticSecurityException
   *           when this access is not permitted by the security manager.
   */
  public void setDouble(long offset, double value) {}

  /**
   * Sets {@code number} doubles starting at the given offset in
   * the memory area associated with this object from the {@code doubles} array
   * passed starting at position {@code low}.  Even when they are
   * aligned, the double values may not be updated atomically.  It is
   * unspecified how many load and store operations will be used or in
   * what order.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created. When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program. Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *          memory area at which to start writing.
   * @param doubles The array from which the items are obtained.
   * @param low The offset which is the starting point in the given
   *          array for the items to be obtained.
   * @param number The number of items to write.
   * @throws OffsetOutOfBoundsException when the offset is negative or
   *           greater than the size of the raw memory area. The role of
   *           the {@code SizeOutOfBoundsException} somewhat
   *           overlaps this exception since it is when the offset is
   *           within the object but outside the mapped area. (See
   *           {@link RawMemoryAccess#map(long,long)} ).
   * @throws SizeOutOfBoundsException when the object is not mapped, or
   *           when a double falls in an invalid address range. This is
   *           checked at every entry in the array to allow for the
   *           possibility that the memory area could be unmapped or
   *           remapped. The {@code doubles} array could,
   *           therefore, be partially updated when the raw memory is
   *           unmapped or remapped mid-method.
   * @throws ArrayIndexOutOfBoundsException
   *           when {@code low} is less than 0 or greater
   *           than <code>bytes.length - 1</code>, or when
   *           <code>low + number</code> is greater than or
   *           equal to {@code bytes.length}.
   * @throws StaticSecurityException
   *           when this access is not permitted by the security manager.
   */
  public void setDoubles(long offset, double[] doubles, int low, int number) {}

  /**
   * Sets the {@code float} at the given offset in the memory area
   * associated with this object.  On most processor architectures an
   * aligned float can be stored in an atomic operation, but this is not
   * required.
   *
   * <p> Caching of the memory access is controlled by the memory
   * {@code type} requested when the {@code RawMemoryAccess}
   * instance was created. When the memory is not cached, this method
   * guarantees serialized access (that is, the memory access at the
   * memory occurs in the same order as in the program. Multiple writes
   * to the same location may not be coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *          memory area at which to write the float.
   * @param value The float to write.
   * @throws OffsetOutOfBoundsException when the offset is negative or
   *           greater than the size of the raw memory area. The role of
   *           the {@code SizeOutOfBoundsException} somewhat
   *           overlaps this exception since it is when the offset is
   *           within the object but outside the mapped area. (See
   *           {@link RawMemoryAccess#map(long,long)} ).
   * @throws SizeOutOfBoundsException when the object is not mapped, or
   *           when the float falls in an invalid address range.
   * @throws StaticSecurityException when this access is not permitted by the
   *           security manager.
   */
  public void setFloat(long offset, float value) {}

  /**
   * Sets {@code number} floats starting at the given offset in the
   * memory area associated with this object from the {@code float} array passed
   * starting at position {@code low}.  On most processor
   * architectures each aligned float can be stored in an atomic
   * operation, but this is not required.  <p> Caching of the memory
   * access is controlled by the memory {@code type} requested when
   * the {@code RawMemoryAccess} instance was created. When the
   * memory is not cached, this method guarantees serialized access
   * (that is, the memory access at the memory occurs in the same order
   * as in the program. Multiple writes to the same location may not be
   * coalesced.)
   *
   * @param offset The offset in bytes from the beginning of the raw
   *          memory area at which to start writing.
   * @param floats The array from which the items are obtained.
   * @param low The offset which is the starting point in the given
   *          array for the items to be obtained.
   * @param number The number of floats to write.
   * @throws OffsetOutOfBoundsException when the offset is negative or
   *           greater than the size of the raw memory area. The role of
   *           the {@code SizeOutOfBoundsException} somewhat
   *           overlaps this exception since it is when the offset is
   *           within the object but outside the mapped area. (See
   *           {@link RawMemoryAccess#map(long, long)} ).
   * @throws SizeOutOfBoundsException when the object is not mapped, or
   *           when a float falls in an invalid address range. This is
   *           checked at every entry in the array to allow for the
   *           possibility that the memory area could be unmapped or
   *           remapped. The store of the array into memory could,
   *           therefore, be only partially complete when the raw memory
   *           is unmapped or remapped mid-method.
   * @throws ArrayIndexOutOfBoundsException when {@code low} is
   *           less than 0 or greater than <code>bytes.length -
   *           1</code>, or when <code>low + number</code> is greater than
   *           or equal to {@code bytes.length}.
   * @throws StaticSecurityException when this access is not permitted by the
   *           security manager.
   */
  public void setFloats(long offset, float[] floats, int low, int number) {}
}
