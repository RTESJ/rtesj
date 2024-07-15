/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.memory;

import javax.realtime.HighResolutionTime;
import javax.realtime.IllegalTaskStateException;
import javax.realtime.MemoryAccessError;
import javax.realtime.SizeEstimator;
import javax.realtime.ThrowBoundaryError;

/**
 * {@code StackedMemory} implements a scoped memory allocation area
 * and backing store management system.  It is designed to allow for
 * safe, fragmentation-free management of scoped allocation with certain
 * strong guarantees provided by the virtual machine and runtime
 * libraries.
 *
 * <p>
 * Each {@code StackedMemory} instance represents a single object
 * allocation area and additional memory associated with it in the form
 * of a <em>backing store</em>.  The backing store associated with a
 * {@code StackedMemory} is a fixed-size memory area allocated at
 * or before instantiation of the {@code StackedMemory}.  The
 * object allocation area is taken from the associated backing store,
 * and the backing store may be further subdivided into additional
 * {@code StackedMemory} allocation areas or backing stores by
 * instantiating additional {@code StackedMemory} objects.
 *
 * <p>
 * When a {@code StackedMemory} is created with a backing store, the
 * backing store may be taken from a notional global backing store, in
 * which case it is effectively immortal, or it may be taken from the
 * enclosing {@code StackedMemory}'s backing store when the scope in
 * which it is created is also a {@code StackedMemory}. In this
 * case it is returned to its enclosing scope's backing store when the
 * object is finalized.  Implementations should return the
 * space occupied by backing stores taken from the global backing store
 * when their associated {@code StackedMemory} object is finalized.
 *
 * <p>
 * These backing store semantics divide instances of
 * {@code StackedMemory} into two categories:
 * <ul>
 *     <li><em>host</em> &mdash; this denotes a
 *         {@code StackedMemory} with an object allocation area
 *         created in a new backing store, allocated either from the
 *         global store or from a parent {@code StackedMemory}'s
 *         backing store, and</li>
 *     <li><em>guest</em> &mdash; this in turn indicates a
 *         {@code StackedMemory} with an object allocation area
 *         taken directly from a parent {@code StackedMemory}'s
 *         backing store without creating a sub-store.</li>
 * </ul>
 * In addition, there is one distinguished status for
 * {@code StackedMemory} object: <em>root</em>.  A root
 * {@code StackedMemory} is a host {@code StackedMemory}
 * created with a backing store drawn directly from the global backing
 * store, created in an allocation context of some type other than
 * {@code StackedMemory}.
 *
 * <p>
 * Creation of a {@code StackedMemory} shall fail with a
 * {@link javax.realtime.StaticOutOfMemoryError} when the current
 * {@link javax.realtime.Schedulable} is configured with a limit on
 * {@code ScopedMemoryParameters.maxGlobalBackingStore} and creation of the
 * root {@code StackedMemory} would exceed that limit.
 *
 * <p>
 * Creation of a {@code StackedMemory} is subject to additional
 * restrictions when the current {@code Schedulable} is configured with an
 * explicit initial memory area of type {@code StackedMemory}.  In this
 * case, the following rules apply.
 * <ul>
 *   <li>Construction of a root {@code StackedMemory} will fail and
 *       throw a {@code StaticOutOfMemoryError} <em>regardless</em> of
 *       the value of the {@code Schedulable}'s
 *       {@code ScopedMemoryParameters.maxGlobalBackingStore}.</li>
 *   <li>Construction of a {@code StackedMemory} from a current
 *       allocation context that is not the {@code Schedulable}'s
 *       explicit initial memory area or one of its descendants in the
 *       scope stack will fail and throw {@code StaticOutOfMemoryError}.</li>
 *   <li>A maximum of {@code ScopedMemoryParameters.maxInitialBackingStore}
 *       bytes may be allocated directly from the backing store of the
 *       {@code Schedulable}'s explicit initial memory area over the
 *       lifetime of the {@code Schedulable}.  Any operation that would
 *       exceed this limit (whether by resizing the allocation area of
 *       the explicit initial memory area or a guest area in the same
 *       backing store, or by allocating a new {@code StackedMemory}
 *       with the explicit initial memory area as the current allocation
 *       context) will fail and throw a {@code StaticOutOfMemoryError}.</li>
 * </ul>
 *
 * <p>
 * Allocations from a {@code StackedMemory} object allocation area
 * are guaranteed to run in time linear in the size of the allocation.
 * All memory for the backing store must be reserved at object
 * construction time.
 *
 * <p>
 * {@code StackedMemory} memory areas have two additional
 * stacking constraints in addition to the single parent rule, designed
 * to enable fragmentation-free manipulation:
 * <ul>
 *     <li>a {@code StackedMemory} that is created when another
 *         {@code StackedMemory} is the current allocation context
 *         can only be entered from the same allocation context in which
 *         it was created, and </li>
 *     <li>a guest {@code StackedMemory} may not be created from
 *         a {@code StackedMemory} that currently has another child
 *         area that is also a guest {@code StackedMemory}, i.e.,
 *         a {@code StackedMemory} can have at most one
 *         direct child that is a guest {@code StackedMemory}.</li>
 * </ul>
 *
 *<p>
 * The {@code StackedMemory} constructor semantics also enforce the
 * property that a {@code StackedMemory} may not be created from
 * another {@code StackedMemory} allocation context unless it is
 * allocated from that context's backing store as either a host or guest
 * area.
 *
 * <p>
 * The backing store of a {@code StackedMemory} behaves as if any
 * {@code StackedMemory} object allocation areas are at the
 * &ldquo;bottom&rdquo; of the backing store, while the backing stores
 * for enclosed {@code StackedMemory} areas are taken from the
 * &ldquo;top&rdquo; of the backing store.
 *
 * <p>
 * There may be an implementation-specific memory overhead for creating
 * a backing store of a given size.  This means that creating a
 * {@code StackedMemory} with a backing store of exactly the
 * remaining available backing store of the current
 * {@code StackedMemory} may fail with an
 * {@code javax.realtime.StaticOutOfMemoryError}. This overhead must be
 * bounded by a constant.
 *
 * @since RTSJ 2.0
 */
public class StackedMemory extends ScopedMemory
{
  /**
   * Creates a host {@code StackedMemory} with an object
   * allocation area and backing store of the specified sizes, bound to
   * the specified {@code Runnable}.  The backing store is
   * allocated from the currently active memory area when it is also a
   * {@code StackedMemory}, and the global backing store otherwise.
   * The object allocation area is allocated from the backing store.
   *
   * @param scopeSize  Size of the allocation area within the backing store.
   *
   * @param backingSize  Size of the total backing store.
   *
   * @param logic {@code Runnable} to be entered using {@code this} as its
   *        current memory area when {@link #enter()} is called.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when either
   *         {@code scopeSize} or {@code backingSize} is less than zero,
   *         or when {@code scopeSize} is too large to be allocated from
   *         a backing store of size {@code backingSize}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is insufficient
   *         memory available to reserve the requested backing store.
   *
   * @throws javax.realtime.IllegalTaskStateException when the current
   *         {@code Schedulable} has a {@code StackedMemory} as its explicit
   *         initial scoped memory area and that area is not on the scope stack.
   */
  public StackedMemory(long scopeSize, long backingSize, Runnable logic)
  {
    super(scopeSize, logic);
  }

  /**
   * Equivalent to {@link #StackedMemory(long, long, Runnable)} with argument
   * list {@code (scopeSize.getEstimate(), backingSize.getEstimate(), runnable)}.
   *
   * @param scopeSize {@code SizeEstimator} indicating the size of the object
   *        allocation area within the backing store.
   *
   * @param backingSize {@code SizeEstimator} indicating the size of the total
   *        backing store.
   *
   * @param logic {@code Runnable} to be entered using {@code this} as its
   *        current memory area when {@link #enter()} is called.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when either
   *         {@code scopeSize} or {@code backingSize} is {@code null},
   *         or when {@code scopeSize.getEstimate()} is too large to be
   *         allocated from a backing store of size
   *         {@code backingSize.getEstimate()}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is insufficient
   *         memory available to reserve the requested backing store.
   *
   * @throws javax.realtime.IllegalTaskStateException when the current
   *         {@code Schedulable} has a {@code StackedMemory} as its explicit
   *         initial scoped memory area and that area is not on the scope stack.
   */
  public StackedMemory(SizeEstimator scopeSize,
                       SizeEstimator backingSize,
                       Runnable logic)
  {
    this(scopeSize.getEstimate(), backingSize.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #StackedMemory(long, long, Runnable)} with argument
   * list {@code (scopeSize, backingSize, null)}.
   *
   * @param scopeSize  Size of the allocation area within the backing store.
   *
   * @param backingSize  Size of the total backing store.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when either
   *         {@code scopeSize} or {@code backingSize} is less than zero,
   *         or when {@code scopeSize} is too large to be allocated from
   *         a backing store of size {@code backingSize}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is insufficient
   *         memory available to reserve the requested backing store.
   *
   * @throws javax.realtime.IllegalTaskStateException when the current
   *         {@code Schedulable} has a {@code StackedMemory} as its explicit
   *         initial scoped memory area and that area is not on the scope stack.
   */
  public StackedMemory(long scopeSize, long backingSize)
  {
    this(scopeSize, backingSize, null);
  }

  /**
   * Equivalent to {@link #StackedMemory(long, long, Runnable)} with argument
   * list {@code (scopeSize.getEstimate(), backingSize.getEstimate(), null)}.
   *
   * @param scopeSize {@code SizeEstimator} indicating the size of the object
   *        allocation area within the backing store.
   *
   * @param backingSize {@code SizeEstimator} indicating the size of the total
   *        backing store.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when either
   *         {@code scopeSize} or {@code backingSize} is {@code null},
   *         or when {@code scopeSize.getEstimate()} is too large to be
   *         allocated from a backing store of size
   *         {@code backingSize.getEstimate()}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is insufficient
   *         memory available to reserve the requested backing store.
   *
   * @throws javax.realtime.IllegalTaskStateException when the current
   *         {@code Schedulable} has a {@code StackedMemory} as its explicit
   *         initial scoped memory area and that area is not on the scope stack.
   */
  public StackedMemory(SizeEstimator scopeSize, SizeEstimator backingSize)
  {
    this(scopeSize.getEstimate(), backingSize.getEstimate(), null);
  }

  /**
   * Create a guest {@code StackedMemory} with an object
   * allocation area of the specified size, bound to the specified
   * {@code Runnable}.  The object allocation area is drawn
   * from the same backing store as the parent scope's object allocation
   * area.  The parent scope must be a {@code StackedMemory}.
   *
   * @param scopeSize  Size of the allocation area within the backing store.
   *
   * @param logic {@code Runnable} to be entered using {@code this} as its
   *        current memory area when {@link #enter()} is called.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *         parent memory area is not a {@code StackedMemory}.
   *
   * @throws javax.realtime.MemoryInUseException when the parent
   *         {@code StackedMemory} already has a child that is
   *         also a guest {@code StackedMemory}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code scopeSize} is less than zero.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is insufficient
   *         memory available in the backing store of the parent
   *         {@code StackedMemory}'s object allocation area to reserve
   *         the requested object allocation area.
   *
   * @throws javax.realtime.IllegalTaskStateException when the current
   *         {@code Schedulable} has a {@code StackedMemory} as its explicit
   *         initial scoped memory area and that area is not on the scope stack.
   */
  public StackedMemory(long scopeSize, Runnable logic)
  {
    super(scopeSize, logic);
  }

  /**
   * Equivalent to {@link #StackedMemory(long, Runnable)} with argument
   * list {@code (scopeSize.getEstimate(), runnable)}.
   *
   * @param scopeSize  {@code SizeEstimator} indicating the size of
   *                   the object allocation area within the backing store.
   * @param logic  {@code Runnable} to be entered using
   *               {@code this} as its current memory area when
   *               {@link #enter()} is called.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *         parent memory area is not a {@code StackedMemory}.
   *
   * @throws javax.realtime.MemoryInUseException when the parent
   *         {@code StackedMemory} already has a child that is
   *         also a guest {@code StackedMemory}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code scopeSize} is {@code null}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory available in the backing store of the
   *         parent {@code StackedMemory}'s object allocation area to
   *         reserve the requested object allocation area.
   *
   * @throws javax.realtime.IllegalTaskStateException when the current
   *         {@code Schedulable} has a {@code StackedMemory} as its explicit
   *         initial scoped memory area and that area is not on the scope stack.
   */
  public StackedMemory(SizeEstimator scopeSize, Runnable logic)
  {
    this(scopeSize.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #StackedMemory(long, Runnable)} with argument
   * list {@code (scopeSize, null)}.
   *
   * @param scopeSize  Size of the allocation area within the backing store.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *         parent memory area is not a {@code StackedMemory}.
   *
   * @throws javax.realtime.MemoryInUseException when the parent
   *         {@code StackedMemory} already has a child that is
   *         also a guest {@code StackedMemory}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code scopeSize} is less than zero.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory available in the backing store of the
   *         parent {@code StackedMemory}'s object allocation area to
   *         reserve the requested object allocation area.
   *
   * @throws javax.realtime.IllegalTaskStateException when the current
   *         {@code Schedulable} has a {@code StackedMemory} as its explicit
   *         initial scoped memory area and that area is not on the scope stack.
   */
  public StackedMemory(long scopeSize)
  {
    this(scopeSize, null);
  }

  /**
   * Equivalent to {@link #StackedMemory(long, Runnable)} with argument
   * list {@code (scopeSize.getEstimate(), null)}.
   *
   * @param scopeSize {@code SizeEstimator} indicating the size of the
   *        object allocation area within the backing store.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *         parent memory area is not a {@code StackedMemory}.
   *
   * @throws javax.realtime.MemoryInUseException when the parent
   *         {@code StackedMemory} already has a child that is
   *         also a guest {@code StackedMemory}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code scopeSize} is {@code null}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory available in the backing store of the
   *         parent {@code StackedMemory}'s object allocation area to
   *         reserve the requested object allocation area.
   *
   * @throws javax.realtime.IllegalTaskStateException when the current
   *         {@code Schedulable} has a {@code StackedMemory} as its explicit
   *         initial scoped memory area and that area is not on the scope stack.
   */
  public StackedMemory(SizeEstimator scopeSize)
  {
    this(scopeSize.getEstimate(), null);
  }

  /**
   * Changes the size of the object allocation area for this scope.
   * This method may be used to either grow or shrink the allocation
   * area when there are no objects allocated in the scope and no
   * {@code Schedulable} object has this area as its current allocation
   * context.  It may be used to grow the allocation area, or to shrink
   * the allocation area no smaller than the size of its current usage,
   * when the calling {@code Schedulable} object is the only object that
   * has this area on its scope stack and there are no guest
   * {@code StackedMemory} object allocation areas created after this
   * area in the same backing store but not yet finalized.
   *
   * @param scopeSize  The new allocation area size for this scope.
   *
   * @throws javax.realtime.StaticSecurityException when the caller is
   *         not permitted to perform the requested adjustment.
   * @throws javax.realtime.StaticIllegalArgumentException there
   *         are additional guest {@code StackedMemory}
   *         allocation areas after this one in the backing store.
   * @throws javax.realtime.StaticOutOfMemoryError when the remaining
   *         backing store is insufficient for the requested adjustment,
   *         or when the current {@code Schedulable} has a {@code
   *         StackedMemory} as its explicit initial scoped memory area
   *         and that area is not on the scope stack.
   */
  public void resize(long scopeSize) { }

  /**
   * Gets the maximum size this memory area can attain.  The value
   * returned by this function is the maximum size that can currently
   * be passed to {@link #resize(long)} without triggering an
   * {@code StaticOutOfMemoryError}.
   *
   * @return the maximum size attainable.
   */
  public long getMaximumSize()
  {
    return 0;
  }

  /**
   * Determines the total amount of memory in the backing store of this
   * stacked memory.  For a guest stacked memory, this is always zero.
   *
   * @return the total amount of backing store in bytes.
   */
  public long hostBackingStoreSize() { return 0L; }

  /**
   * Determines the amount of memory remaining for allocation to new
   * stacked memories in the backing store of this stacked memory.  For
   * a guest stacked memory, this is always zero.
   *
   * @return the amount of backing store remaining in bytes.
   */
  public long hostBackingStoreRemaining() { return 0L; }

  /**
   * Determines the amount of memory consumed by exisiting stacked memories
   * from the backing store of this stacked memory.  For a guest stacked
   * memory, this is always zero.
   *
   * @return the amount of backing store consumed in bytes.
   */
  public long hostBackingStoreConsumed() { return 0L; }

  /**
   * Associates this memory area with the current
   * {@code Schedulable} object for the duration of the
   * {@code run()} method of the instance of
   * {@code Runnable} given in this object's constructor.  During
   * this period of execution, this memory area becomes the default
   * allocation context until another default allocation context is
   * selected.
   *
   * <p>
   * This method may only be called from the memory area in which this
   * scope was created.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when the currently
   *         active memory area is a {@code StackedMemory} and is not the
   *         area in which this scope was created, or the current memory
   *         area is not a {@code StackedMemory} and this
   *         {@code StackedMemory} is not a root area.
   * @throws ThrowBoundaryError {@inheritDoc}
   * @throws IllegalTaskStateException {@inheritDoc}
   * @throws MemoryAccessError {@inheritDoc}
   *
   * @see ScopedMemory#enter()
   */
  @Override
  public void enter() { }

  /**
   * Associates this memory area with the current
   * {@code Schedulable} object for the duration of the
   * {@code run()} method of the given {@code Runnable}.
   * During this period of execution, this memory area becomes the
   * default allocation context until another default allocation
   * context is selected.
   * <p>
   * This method may only be called from the memory area in which this
   * scope was created.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when the currently
   *         active memory area is a {@code StackedMemory} and is not the
   *         area in which this scope was created, or the current memory
   *         area is not a {@code StackedMemory} and this
   *         {@code StackedMemory} is not a root area.
   *
   * @throws javax.realtime.ThrowBoundaryError {@inheritDoc}
   *
   * @throws javax.realtime.IllegalTaskStateException {@inheritDoc}
   *
   * @throws javax.realtime.MemoryAccessError {@inheritDoc}
   *
   * @see ScopedMemory#enter(Runnable)
   */
  @Override
  public void enter(Runnable logic) { }


  @Override
  public void joinAndEnter() throws InterruptedException { }


  @Override
  public void joinAndEnter(HighResolutionTime<?> time)
    throws InterruptedException
  {
  }


  @Override
  public void joinAndEnter(Runnable logic)
    throws InterruptedException
  {
  }


  @Override
  public void joinAndEnter(Runnable logic, HighResolutionTime<?> time)
    throws InterruptedException
  {
  }
}
