/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An instance of {@code LTPhysicalMemory} allows objects to be
 * allocated from a range of physical memory with particular attributes,
 * determined by their memory type. This memory area has the same
 * semantics as {@link ScopedMemory} memory areas, and the same
 * performance restrictions as {@link LTMemory}.
 *
 * <p> No provision is made for sharing object in
 * {@code LTPhysicalMemory} with entities outside the JVM that
 * creates them, and, while the memory backing an instance of
 * {@code LTPhysicalMemory} could be shared by multiple JVMs, the
 * class does not support such sharing.
 *
 * <p> Methods from {@code LTPhysicalMemory} should be overridden
 * only by methods that use {@code super}.
 *
 * @deprecated since RTSJ 2.0
 */
@Deprecated
public class LTPhysicalMemory extends ScopedMemory
{
  private long base;


  /**
   * Creates an instance of {@code LTPhysicalMemory} with the given
   * parameters.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          used to define the base address and control the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @see PhysicalMemoryManager
   * @param base
   *          The physical memory address of the area.
   * @param size
   *          The size of the area in bytes.
   * @param logic The {@code run()} method of this object will be
   *          called whenever {@link MemoryArea#enter()} is called. When
   *          {@code logic} is {@code null},
   *          {@code logic} must be supplied when the memory area
   *          is entered.
   * @throws SizeOutOfBoundsException
   *           when the implementation detects that {@code base} plus
   *           {@code size} extends
   *           beyond physically addressable memory.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory or the given type of
   *              memory.
   * @throws IllegalArgumentException
   *           when {@code size} is less than zero.
   * @throws OffsetOutOfBoundsException
   *           when the address is invalid.
   * @throws UnsupportedPhysicalMemoryException
   *              when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException
   *              when the specified base does not point to
   *              memory that matches the requested type, or when
   *              {@code type} specifies
   *              incompatible memory attributes.
   * @throws MemoryInUseException
   *           when the specified memory is already in use.
   * @throws IllegalAssignmentError
   *           when storing {@code logic} in {@code this} would
   *           violate the assignment rules.
   */
  public LTPhysicalMemory(Object type, long base, long size, Runnable logic)
  {
    super(size);
  }

  /**
   * Equivalent to {@link #LTPhysicalMemory(Object, long, long, Runnable)} with
   * the argument list {@code (type, base, size.getEstimate(), logic)}.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          used to define the base address and control the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @param base
   *          The physical memory address of the area.
   * @param size
   *          A size estimator for this memory area.
   * @param logic The {@code run()} method of this object will be
   *          called whenever {@link MemoryArea#enter()} is called. When
   *          {@code logic} is {@code null},
   *          {@code logic} must be supplied when the memory area
   *          is entered.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory or the given type of
   *              memory.
   * @throws SizeOutOfBoundsException
   *           when the implementation detects that {@code base} plus
   *           the size estimate extends beyond physically addressable memory.
   * @throws OffsetOutOfBoundsException
   *           when the address is invalid.
   * @throws UnsupportedPhysicalMemoryException
   *              when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException
   *              when the specified base does not point to
   *              memory that matches the requested type, or when
   *              {@code type} specifies
   *              incompatible memory attributes.
   * @throws MemoryInUseException
   *           when the specified memory is already in use.
   * @throws IllegalArgumentException
   *           when {@code size} is {@code null},
   *           or {@code size.getEstimate()} is negative.
   * @throws IllegalAssignmentError
   *           when storing {@code logic} in {@code this} would
   *           violate the assignment rules.
   * @see PhysicalMemoryManager
   */
  public LTPhysicalMemory(Object type, long base, SizeEstimator size,
                          Runnable logic)
  {
    this(type, base, size.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #LTPhysicalMemory(Object, long, long, Runnable)} with the
   * the argument list {@code (type, base, size, null)}.
   *
   * @see PhysicalMemoryManager
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          used to define the base address and control the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @param base
   *          The physical memory address of the area.
   * @param size
   *          The size of the area in bytes.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory or the given type of
   *              memory.
   * @throws SizeOutOfBoundsException
   *           when the size is less than zero, or the
   *           implementation detects that {@code base} plus
   *           {@code size} extends beyond physically addressable memory.
   * @throws OffsetOutOfBoundsException
   *           when the address is invalid.
   * @throws UnsupportedPhysicalMemoryException
   *              when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException
   *              when the specified base does not point to
   *              memory that matches the requested type, or when
   *              {@code type} specifies
   *              incompatible memory attributes.
   * @throws IllegalArgumentException
   *           when {@code size} is less than zero.
   * @throws MemoryInUseException
   *           when the specified memory is already in use.
   */
  public LTPhysicalMemory(Object type, long base, long size)
  {
    this(type, base, size, null);
  }

  /**
   * Equivalent to {@link #LTPhysicalMemory(Object, long, long, Runnable)} with
   * the argument list {@code (type, base, size.getEstimate(), null)}.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          used to define the base address and control the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @see PhysicalMemoryManager
   * @param base
   *          The physical memory address of the area.
   * @param size
   *          A size estimator for this memory area.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory or the given type of
   *              memory.
   * @throws SizeOutOfBoundsException
   *           when the
   *           implementation detects that {@code base} plus
   *           the size estimate extends beyond physically addressable memory.
   * @throws OffsetOutOfBoundsException
   *           when the address is invalid.
   * @throws UnsupportedPhysicalMemoryException
   *              when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException
   *              when the specified base does not point to
   *              memory that matches the requested type, or when
   *              {@code type} specifies
   *              incompatible memory attributes.
   * @throws MemoryInUseException
   *           when the specified memory is already in use.
   * @throws IllegalArgumentException
   *           when {@code size} is {@code null},
   *           or {@code size.getEstimate()} is negative.
   */
  public LTPhysicalMemory(Object type, long base, SizeEstimator size)
  {
    super(size);
  }

  /**
   * Equivalent to {@link #LTPhysicalMemory(Object, long, long, Runnable)}
   * with the argument list {@code (type, next, size, logic)}, where
   * {@code next} is the beginning of the next best fit in the physical
   * memory range.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          used to define the base address and control the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @param size The size of the area in bytes.
   * @param logic The {@code run()} method of this object will be
   *          called whenever {@link MemoryArea#enter()} is called. When
   *          {@code logic} is {@code null},
   *          {@code logic} must be supplied when the memory area
   *          is entered.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory or the given type of
   *              memory.
   * @throws IllegalArgumentException when {@code size} is less than zero.
   * @throws SizeOutOfBoundsException when the
   *           implementation detects that {@code size} extends beyond
   *           physically addressable memory.
   * @throws UnsupportedPhysicalMemoryException
   *              when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException
   *              when the specified base does not point to
   *              memory that matches the requested type, or when
   *              {@code type} specifies
   *              incompatible memory attributes.
   * @throws IllegalAssignmentError
   *           when storing {@code logic} in {@code this} would
   *           violate the assignment rules.
   * @see PhysicalMemoryManager
   */
  public LTPhysicalMemory(Object type, long size, Runnable logic)
  {
    this(type, 0, size, logic);
  }

  /**
   * Equivalent to {@link #LTPhysicalMemory(Object, long, long, Runnable)}
   * with the argument list {@code (type, next, size.getEstimate(), logic)},
   * where {@code next} is the beginning of the next best fit in the physical
   * memory range.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          used to define the base address and control the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @see PhysicalMemoryManager
   * @param size
   *          A size estimator for this area.
   * @param logic The {@code run()} method of this object will be
   *          called whenever {@link MemoryArea#enter()} is called. When
   *          {@code logic} is {@code null},
   *          {@code logic} must be supplied when the memory area
   *          is entered.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory or the given type of
   *              memory.
   * @throws SizeOutOfBoundsException
   *           when the
   *           implementation detects that {@code base} plus
   *           the size estimate extends beyond physically addressable memory.
   * @throws UnsupportedPhysicalMemoryException
   *              when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException
   *           when the specified base does not point to
   *           memory that matches the request type, or when {@code type}
   *           specifies attributes with a conflict.
   * @throws IllegalArgumentException when {@code size} is {@code null},
   *           or {@code size.getEstimate()} is negative.
   * @throws IllegalAssignmentError when storing {@code logic} in
   *         {@code this} violates the assignment rules.
   */
  public LTPhysicalMemory(Object type, SizeEstimator size, Runnable logic)
  {
    this(type, 0, size.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #LTPhysicalMemory(Object, long, long, Runnable)} with
   * the argument list {@code (type, next, size, null)}, where
   * {@code next} is the beginning of the next best fit in the physical
   * memory range.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          used to define the base address and control the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @see PhysicalMemoryManager
   * @param size The size of the area in bytes.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory or the given type of
   *              memory.
   * @throws IllegalArgumentException when {@code size} is less than zero.
   * @throws SizeOutOfBoundsException when
   *           the implementation detects {@code size} extends
   *           beyond physically addressable memory.
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException when {@code type} specifies
   *           incompatible memory attributes.
   */
  public LTPhysicalMemory(Object type, long size)
  {
    super(size);
  }

  /**
   * Equivalent to {@link #LTPhysicalMemory(Object, long, long, Runnable)} with
   * the argument list {@code (type, next, size.getEstimate(), null)}, where
   * {@code next} is the beginning of the next best fit in the physical
   * memory range.
   *
   * @param type An instance of {@code Object} representing the
   *          type of memory required, e.g., <em>dma, shared</em>,
   *          used to define the base address and control the
   *          mapping. When the required memory has more than one
   *          attribute, {@code type} may be an array of
   *          objects. When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable. Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   * @see PhysicalMemoryManager
   * @param size
   *          A size estimator for this area.
   * @throws StaticSecurityException when the application doesn't have
   *              permissions to access physical memory or the given type of
   *              memory.
   * @throws SizeOutOfBoundsException
   *           when the implementation
   *           detects that {@code size} extends beyond physically
   *           addressable memory.
   * @throws UnsupportedPhysicalMemoryException
   *              when the underlying
   *              hardware does not support the given type, or when no matching
   *              {@link PhysicalMemoryTypeFilter} has been registered with
   *              the {@link PhysicalMemoryManager}.
   * @throws MemoryTypeConflictException
   *           when {@code type} specifies
   *           incompatible memory attributes.
   * @throws IllegalArgumentException
   *           when {@code size} is {@code null},
   *           or {@code size.getEstimate()} is negative.
   */
  public LTPhysicalMemory(Object type, SizeEstimator size)
  {
    this(type, 0, size, null);
  }

  /**
   * Creates a string describing this object. The
   * string is of the form
   *
   * <pre>
   * <code>(LTPhysicalMemory) Scoped memory # num</code>
   * </pre>
   *
   * where {@code num} is a number that uniquely
   * identifies this {@code LTPhysicalMemory} memory area
   * representing the value of {@code this}.
   *
   * @return A string representing the value of {@code this}.
   */
  @Override
  public String toString()
  {
    return new String("(LTPhysicalMemory) " + super.toString());
  }
}
