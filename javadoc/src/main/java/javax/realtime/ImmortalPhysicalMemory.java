/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An instance of {@code ImmortalPhysicalMemory} allows objects to
 * be allocated from a range of physical memory with particular
 * attributes, determined by their memory type. This memory area has the
 * same restrictive set of assignment rules as {@link ImmortalMemory}
 * memory areas, and may be used in any execution context where
 * {@code ImmortalMemory} is appropriate.
 *
 * <p> No provision is made for sharing object in
 * {@code ImmortalPhysicalMemory} with entities outside the JVM
 * that creates them, and, while the memory backing an instance of
 * {@code ImmortalPhysicalMemory} could be shared by multiple JVMs,
 * the class does not support such sharing.
 *
 * <p> Methods from {@code ImmortalPhysicalMemory} should be
 *  overridden only by methods that use {@code super}.
 *
 * @since RTSJ 2.0 extends {@code PerennialMemory} instead of
 *        {@code MemoryArea} directly.
 *
 * @deprecated since RTSJ 2.0
 */
@Deprecated
public class ImmortalPhysicalMemory extends PerennialMemory
{
  /**
   * Creates an instance with the given parameters.
   *
   * @param type An instance of {@code Object} or an array of
   *          objects representing the type of memory required (e.g.,
   *          <em>dma, shared</em>) - used to define the base address
   *          and control the mapping.  When the required memory has more
   *          than one attribute {@code type} may be an array of
   *          objects.  When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable.  Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   *
   * @param base The physical memory address of the area.
   *
   * @param size The size of the area in bytes.
   *
   * @param logic The {@code run()} method of this object will be
   *        called whenever {@link MemoryArea#enter()} is called.  When
   *        {@code logic} is {@code null}, {@code logic}
   *        must be supplied when the memory area is entered.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given type
   *            of memory.
   *
   * @throws OffsetOutOfBoundsException when the {@code base}
   *         address is invalid.
   *
   * @throws SizeOutOfBoundsException when {@code size}
   *            extends into an invalid range of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws MemoryTypeConflictException when the specified base does
   *            not point to memory that matches the requested type, or
   *            when {@code type} specifies incompatible memory
   *            attributes.
   *
   * @throws IllegalArgumentException when {@code size} is
   *         negative.  {@code IllegalArgumentException} may also
   *         be when {@code base} plus {@code size} would be
   *         greater than the maximum physical address supported by the
   *         processor.
   *
   * @throws MemoryInUseException when the specified memory is already in use.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *         {@code ImmortalPhysicalMemory} object or for its allocation
   *         area in its backing store.
   *
   *    @throws IllegalAssignmentError when storing {@code logic}
   *            in {@code this} would violate the assignment rules.
   *
   */
  public ImmortalPhysicalMemory(Object type,
                                long base,
                                long size,
                                Runnable logic)
  {
    super(size, logic);
  }

  /**
   * Creates an instance with the given parameters.
   *
   * @param type An instance of {@code Object} or an array of
   *          objects representing the type of memory required (e.g.,
   *          <em>dma, shared</em>) - used to define the base address
   *          and control the mapping.  When the required memory has more
   *          than one attribute {@code type} may be an array of
   *          objects.  When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable.  Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   *
   * @param base The physical memory address of the area.
   *
   * @param size A size estimator for this memory area.
   *
   * @param logic The {@code run()} method of this object will be
   *        called whenever {@link MemoryArea#enter()} is called.  When
   *        {@code logic} is {@code null}, {@code logic}
   *        must be supplied when the memory area is entered.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given type
   *            of memory.
   *
   * @throws OffsetOutOfBoundsException when the {@code base}
   *         address is invalid.
   *
   * @throws SizeOutOfBoundsException when the size
   *          estimate from {@code size}
   *            extends into an invalid range of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws MemoryTypeConflictException when the specified base does
   *            not point to memory that matches the requested type, or
   *            when {@code type} specifies incompatible memory
   *            attributes.
   *
   *  @throws IllegalArgumentException when {@code size} is
   *              {@code null}, or {@code size.getEstimate()}
   *              is negative.  {@code IllegalArgumentException}
   *              may also be when {@code base} plus the size
   *              indicated by {@code size} would be greater than
   *              the maximum physical address supported by the
   *              processor.
   *
   * @throws MemoryInUseException when the specified memory is already in use.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code ImmortalPhysicalMemory} object or for the backing
   *      memory.
   *
   * @throws IllegalAssignmentError when storing {@code logic} in
   *            {@code this} would violate the assignment rules.
   */
  public ImmortalPhysicalMemory(Object type,
                                long base,
                                SizeEstimator size,
                                Runnable logic)
  {
    super(size.getEstimate(), logic);
  }

  /**
   * Creates an instance with the given parameters.
   *
   * @param type An instance of {@code Object} or an array of
   *          objects representing the type of memory required (e.g.,
   *          <em>dma, shared</em>) - used to define the base address
   *          and control the mapping.  When the required memory has more
   *          than one attribute {@code type} may be an array of
   *          objects.  When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable.  Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   *
   * @param size The size of the area in bytes.
   *
   * @param logic The {@code run()} method of this object will be
   *        called whenever {@link MemoryArea#enter()} is called.  When
   *        {@code logic} is {@code null}, {@code logic}
   *        must be supplied when the memory area is entered.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given type
   *            of memory.
   *
   * @throws SizeOutOfBoundsException when {@code size}
   *            extends into an invalid range of memory.
   *
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws IllegalArgumentException when {@code size} is negative.
   *
   * @throws MemoryTypeConflictException when the specified base does
   *            not point to memory that matches the requested type, or
   *            when {@code type} specifies incompatible memory
   *            attributes.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code ImmortalPhysicalMemory} object or for its allocation
   *      area in its backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic} in
   *            {@code this} would violate the assignment rules.
   *
   */
  public ImmortalPhysicalMemory(Object type,
                                long size,
                                Runnable logic)
  {
    super(size, logic);
  }

  /**
   * Creates an instance with the given parameters.
   *
   * @param type An instance of {@code Object} or an array of
   *          objects representing the type of memory required (e.g.,
   *          <em>dma, shared</em>) - used to define the base address
   *          and control the mapping.  When the required memory has more
   *          than one attribute {@code type} may be an array of
   *          objects.  When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable.  Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   *
   * @param size A size estimator for this area.
   *
   * @param logic The {@code run()} method of this object will be
   *        called whenever {@link MemoryArea#enter()} is called.  When
   *        {@code logic} is {@code null}, {@code logic}
   *        must be supplied when the memory area is entered.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given type
   *            of memory.
   *
   * @throws SizeOutOfBoundsException when the {@code size}
   *            extends into an invalid range of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   *  @throws IllegalArgumentException when {@code size} is
   *              {@code null}, or {@code size.getEstimate()}
   *              is negative.
   *
   * @throws MemoryTypeConflictException when {@code type} specifies
   *            incompatible memory attributes.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code ImmortalPhysicalMemory} object or for its allocation
   *      area in its backing store.
   *
   * @throws IllegalAssignmentError when storing {@code logic} in
   *            {@code this} would violate the assignment rules.
   */
  public ImmortalPhysicalMemory(Object type,
                                SizeEstimator size,
                                Runnable logic)
  {
    super(size.getEstimate(), logic);
  }

  /**
   * Creates an instance with the given parameters.
   *
   * @param type An instance of {@code Object} or an array of
   *          objects representing the type of memory required (e.g.,
   *          <em>dma, shared</em>) - used to define the base address
   *          and control the mapping.  When the required memory has more
   *          than one attribute {@code type} may be an array of
   *          objects.  When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable.  Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   *
   * @param base The physical memory address of the area.
   *
   * @param size The size of the area in bytes.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given range
   *            of memory.
   *
   * @throws OffsetOutOfBoundsException when the {@code base}
   *         address is invalid.
   *
   * @throws SizeOutOfBoundsException when the {@code size}
   *            extends into an invalid range of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws MemoryTypeConflictException when the specified base does
   *            not point to memory that matches the requested type, or
   *            when {@code type} specifies incompatible memory
   *            attributes.
   *
   * @throws IllegalArgumentException when {@code size} is less
   *         than zero.  {@code IllegalArgumentException} may also
   *         be when {@code base} plus {@code size} would be
   *         greater than the maximum physical address supported by the
   *         processor.
   *
   * @throws MemoryInUseException when the specified memory is already in use.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code ImmortalPhysicalMemory} object or or for its allocation
   *      area in its backing store.
   */
  public ImmortalPhysicalMemory(Object type,
                                long base,
                                long size)
  {
    super(size);
  }

  /**
   * Creates an instance with the given parameters.
   *
   * @param type An instance of {@code Object} or an array of
   *          objects representing the type of memory required (e.g.,
   *          <em>dma, shared</em>) - used to define the base address
   *          and control the mapping.  When the required memory has more
   *          than one attribute {@code type} may be an array of
   *          objects.  When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable.  Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   *
   * @param base The physical memory address of the area.
   *
   * @param size A size estimator for this memory area.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given type
   *            of memory.
   *
   * @throws OffsetOutOfBoundsException when the {@code base}
   *         address is invalid.
   *
   * @throws SizeOutOfBoundsException when the
   *          size estimate from {@code size}
   *            extends into an invalid range of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws MemoryTypeConflictException when the specified base does
   *            not point to memory that matches the requested type, or
   *            when {@code type} specifies incompatible memory
   *            attributes.
   *
   *  @throws IllegalArgumentException when {@code size} is
   *              {@code null}, or {@code size.getEstimate()}
   *              is negative.  {@code IllegalArgumentException}
   *              may also be when {@code base} plus the size
   *              indicated by {@code size} would be greater than
   *              the maximum physical address supported by the
   *              processor.
   *
   * @throws MemoryInUseException when the specified memory is already in use.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code ImmortalPhysicalMemory} object or for its allocation
   *      area in its backing store.
   */
  public ImmortalPhysicalMemory(Object type,
                                long base,
                                SizeEstimator size)
  {
    super(size);
  }

  /**
   * Creates an instance with the given parameters.
   *
   * @param type An instance of {@code Object} representing the
   *        type of memory required (e.g., <em>dma, shared</em>) - used
   *        to define the base address and control the mapping.  When the
   *        required memory has more than one attribute
   *        {@code type} may be an array of objects.  When
   *        {@code type} is {@code null} or a reference to an
   *        array with no entries, any type of memory is acceptable.
   *        Note that {@code type} values are compared by reference
   *        (==), not by value ({@code equals}).
   *
   * @param size The size of the area in bytes.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given type
   *            of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws MemoryTypeConflictException when {@code type} specifies
   *            incompatible memory attributes.
   *
   *  @throws IllegalArgumentException when {@code size} is less than zero.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code ImmortalPhysicalMemory} object or for its allocation
   *      area in its backing store.
   *
   * @throws SizeOutOfBoundsException when the {@code size}
   *            extends into an invalid range of memory.
   */
  public ImmortalPhysicalMemory(Object type, long size)
  {
    super(size);
  }

  /**
   * Creates an instance with the given parameters.
   *
   * @param type An instance of {@code Object} or an array of
   *          objects representing the type of memory required (e.g.,
   *          <em>dma, shared</em>) - used to define the base address
   *          and control the mapping.  When the required memory has more
   *          than one attribute {@code type} may be an array of
   *          objects.  When {@code type} is {@code null} or a
   *          reference to an array with no entries, any type of memory
   *          is acceptable.  Note that {@code type} values are
   *          compared by reference (==), not by value
   *          ({@code equals}).
   *
   * @param size A size estimator for this area.
   *
   * @throws StaticSecurityException when the application doesn't have
   *            permissions to access physical memory or the given type
   *            of memory.
   *
   * @throws SizeOutOfBoundsException when the size estimate
   *          from {@code size}
   *            extends into an invalid range of memory.
   *
   * @throws UnsupportedPhysicalMemoryException when the underlying
   *            hardware does not support the given type, or when no matching
   *          {@link PhysicalMemoryTypeFilter} has been registered with
   *          the {@link PhysicalMemoryManager}.
   *
   * @throws MemoryTypeConflictException when {@code type} specifies
   *            incompatible memory attributes.
   *
   *  @throws IllegalArgumentException when {@code size} is
   *              {@code null}, or {@code size.getEstimate()}
   *              is negative.
   *
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code ImmortalPhysicalMemory} object or for its allocation
   *      area in its backing store.
   *
   */
  public ImmortalPhysicalMemory(Object type,
                                SizeEstimator size)
  {
    super(size);
  }
}
