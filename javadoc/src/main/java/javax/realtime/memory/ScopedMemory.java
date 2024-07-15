/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.memory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import javax.realtime.ForEachTerminationException;
import javax.realtime.HighResolutionTime;
import javax.realtime.IllegalAssignmentError;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.IllegalTaskStateException;
import javax.realtime.InaccessibleAreaException;
import javax.realtime.MemoryAccessError;
import javax.realtime.MemoryArea;
import javax.realtime.ScopedCycleException;
import javax.realtime.StaticOutOfMemoryError;
import javax.realtime.ThrowBoundaryError;
import javax.realtime.StaticUnsupportedOperationException;

/**
 * {@code ScopedMemory} is the abstract base class of all classes dealing
 * with representations of memory spaces which have a limited lifetime.
 * In general, objects allocated in scoped memory are freed when, and only
 * when, no schedulable has access to the objects in the scoped memory.
 *
 * <p>
 * A {@code ScopedMemory} area is a connection to a particular region of
 * memory and reflects the current status of that memory. The object does not
 * necessarily contain direct references to the region of memory. That is
 * implementation dependent.
 *
 * <p> When a {@code ScopedMemory} area is instantiated, the object
 * itself is allocated from the current memory allocation context, but
 * the memory space that object represents (its backing store) is
 * allocated from memory that is not otherwise directly visible to Java
 * code; e.g., it might be allocated with the C {@code malloc}
 * function. This backing store behaves effectively as if it were
 * allocated when the associated scoped memory object is constructed and
 * freed at that scoped memory object's finalization.
 *
 * <p> The {@link ScopedMemory#enter} method of
 * {@code ScopedMemory} is one mechanism used to make a memory area
 * the current allocation context. The other mechanism for activating a
 * memory area is making it the initial memory area for a realtime
 * thread or async event handler. Entry into the scope is accomplished,
 * for example, by calling the method:<p>
 *
 * <pre>
 *      <code>public void enter(Runnable logic)</code>
 * </pre>
 *
 * <p>where {@code logic} is an instance of {@code Runnable} whose
 * {@code run()} method represents the entry point of the code that will
 * run in the new scope. Exit from the scope occurs between the time the
 * {@code runnable.run()} method completes and the time control returns
 * from the {@code enter} method. By default, allocations of objects within
 * {@code runnable.run()} are taken from the backing store of the
 * {@code ScopedMemory}.
 *
 * <p>
 * {@code ScopedMemory} is an abstract class, but all specified methods
 * include implementations. The responsibilities of
 * {@code MemoryArea, ScopedMemory} and the classes that extend
 * {@code ScopedMemory} are not specified. Application code should not
 * extend {@code ScopedMemory} without detailed knowledge of its
 * implementation.
 *
 * since RTSJ 2.0, moved from {@code javax.realtime}.
 */
public abstract class ScopedMemory extends MemoryArea
{
  /**
   * Determines the total amount of memory in the global backing store.
   *
   * @return the total amount of global backing store in bytes.
   *
   * @since RTSJ 2.0
   */
  public static long globalBackingStoreSize() { return 0L; }

  /**
   * Determines the amount of memory remaining for allocation to new
   * scoped memories in the backing store of this scoped memory.
   *
   * @return the amount of global backing store remaing in bytes.
   *
   * @since RTSJ 2.0
   */
  public static long globalBackingStoreRemaining() { return 0L; }

  /**
   * Determines the amount of memory consumed by exisiting scoped memories
   * from the global backing store.
   *
   * @return the amount of backing store available in bytes.
   *
   * @since RTSJ 2.0
   */
  public static long globalBackingStoreConsumed() { return 0L; }

  /**
   * A means of accessing all live scoped memories whose parent is a
   * perennial memory area, even those to which no reference exists,
   * such a {@link javax.realtime.memory.PinnableMemory} that is pinned
   * or another {@code javax.realtime.memory.ScopedMemory} that contains a
   * {@code Schedulable}.  The set may be concurrently modified by other tasks,
   * but the view seen by the visitor may not be updated to reflect those
   * changes.
   *
   * The following is guaranteed even when the set is disturbed by other tasks:
   * <ul>
   * <li>the visitor shall visit no member more than once,</li>
   * <li>it shall visit only scopes that were a member of
   * the set at some time during the enumeration of the set, and</li>
   * <li>it shall visit all the scopes that are not deleted during the
   * execution of the visitor.</li>
   * </ul>
   * The visitor's {@code accept} method is called on all live roots scopes,
   * so long as the {@code visitor} does not throw
   * {@link javax.realtime.ForEachTerminationException}. When that is thrown,
   * the visit terminates.  A closure could be used to capture the last
   * element visited.
   *
   * <p> When execution of the visitor's {@code accept} method is
   * terminated abruptly by throwing an exception, then execution of
   * visitScopedChildren also terminates abruptly by throwing the same
   * exception.
   *
   * @param visitor Determines the action to be performed on each of the
   *        children scopes.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         visitor is {@code null}.
   *
   * @throws ForEachTerminationException when the traversal ends prematurely.
   *
   * @throws javax.realtime.StaticSecurityException when the
   *         application does not have permissions to access visit root
   *         scopes.
   *
   * @since RTSJ 2.0
   */
  public static void visitScopeRoots(Consumer<ScopedMemory> visitor)
    throws StaticIllegalArgumentException, ForEachTerminationException
  {
  }

  /**
   * Creates a new {@code ScopedMemory} area with the given parameters.
   *
   * @param size
   *          The size of the new {@code ScopedMemory} area
   *          in bytes.
   * @param logic
   *          The {@code Runnable} to execute when this
   *          {@code ScopedMemory} is entered. When {@code logic} is
   *          {@code null}, this constructor is equivalent to constructing
   *          the memory area without a logic value.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is less than zero.
   *
   * @throws javax.realtime.IllegalAssignmentError
   *           when storing {@code logic} in {@code this} would
   *           violate the assignment rules.
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *           insufficient memory for the {@code ScopedMemory} object
   *           or for its allocation area in its backing store.
   */
  ScopedMemory(long size, Runnable logic)
  {
    super(size, logic);
  }

  /**
   * Equivalent to {@link #ScopedMemory(long, Runnable)} with argument list
   * {@code (size, null)}.
   *
   * @param size The new {@code ScopedMemory} area in bytes.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is less than zero.
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *           insufficient memory for the {@code ScopedMemory} object
   *           or for its allocation area in its backing store.
   */
  ScopedMemory(long size)
  {
    super(size);
  }

  /**
   * Associates this memory area with the current schedulable for
   * the duration of the execution of the {@code run()} method of
   * the instance of {@code Runnable} given in the constructor.
   * During this period of execution, this memory area becomes the
   * default allocation context until another default allocation context
   * is selected (using {@code enter}, or {@link #executeInArea})
   * or the {@code enter} method exits.
   *
   * @throws ScopedCycleException when this invocation
   *         would break the single parent rule.
   * @throws ThrowBoundaryError Thrown when the JVM needs
   *         to propagate an exception allocated in {@code this} scope
   *         to (or through) the memory area of the caller. Storing a
   *         reference to that exception would cause an
   *         {@link javax.realtime.IllegalAssignmentError}, so the JVM cannot
   *         be permitted to deliver the exception.  The
   *         {@link javax.realtime.ThrowBoundaryError} is allocated in the
   *         current allocation context and contains information about
   *         the exception it replaces.
   * @throws IllegalTaskStateException when the execution context
   *         is not an instance of {@link javax.realtime.Schedulable} or
   *         when this method is invoked during finalization of objects in
   *         scoped memory and entering this scoped memory area would force
   *         deletion of the execution context that triggered
   *         finalization.  This would include the scope containing the
   *         execution context, and the scope (if any) containing the
   *         scope containing execution context.
   * @throws javax.realtime.StaticIllegalArgumentException {@inheritDoc}
   * @throws MemoryAccessError {@inheritDoc}
   */
  @Override
  public void enter()
    throws ScopedCycleException, ThrowBoundaryError,
           IllegalTaskStateException,
           StaticIllegalArgumentException, MemoryAccessError
  {
  }

  /**
   * Associates this memory area with the current schedulable for the
   * duration of the execution of the {@code run()} method of the given
   * {@code Runnable}.  During this period of execution, this memory
   * area becomes the default allocation context until another default
   * allocation context is selected (using {@code enter}, or
   * {@link #executeInArea}) or the {@code enter} method exits.
   *
   * @param logic
   *          {@inheritDoc}
   * @throws ScopedCycleException
   *           when this invocation would break the single
   *           parent rule.
   * @throws ThrowBoundaryError when the JVM needs
   *           to propagate
   *           an exception allocated in {@code this} scope to (or
   *           through) the memory area of the caller. Storing a
   *           reference to that exception would cause an
   *           {@link javax.realtime.IllegalAssignmentError}, so the JVM
   *           cannot be permitted to deliver the exception.  The
   *           {@link javax.realtime.ThrowBoundaryError} is allocated in the
   *           current allocation context and contains information about the
   *           exception it replaces.
   * @throws IllegalTaskStateException when the execution context
   *         is not an instance of {@link javax.realtime.Schedulable} or
   *         when this method is invoked during finalization of objects in
   *         scoped memory and entering this scoped memory area would force
   *         deletion of the task that triggered finalization.  This
   *         would include the scope containing the task, and the scope
   *         (if any) containing the scope containing task.
   * @throws javax.realtime.StaticIllegalArgumentException {@inheritDoc}
   */
  @Override
  public void enter(Runnable logic)
    throws ScopedCycleException,
           ThrowBoundaryError,
           IllegalTaskStateException,
           StaticIllegalArgumentException
  {
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and an object is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public <T> T enter(Supplier<T> logic)
    throws ScopedCycleException,
           ThrowBoundaryError,
           IllegalTaskStateException,
           StaticIllegalArgumentException
  {
    return null;
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and a {@code boolean} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public boolean enter(BooleanSupplier logic)
    throws ScopedCycleException, ThrowBoundaryError,
           IllegalTaskStateException, StaticIllegalArgumentException
  {
    return false;
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and an {@code int} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public int enter(IntSupplier logic)
    throws ScopedCycleException, ThrowBoundaryError,
           IllegalTaskStateException, StaticIllegalArgumentException
  {
    return 0;
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and a {@code long} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public long enter(LongSupplier logic)
    throws ScopedCycleException, ThrowBoundaryError,
           IllegalTaskStateException, StaticIllegalArgumentException
  {
    return 0L;
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and a {@code double} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public double enter(DoubleSupplier logic)
    throws ScopedCycleException, ThrowBoundaryError,
           IllegalTaskStateException, StaticIllegalArgumentException
  {
    return 0.0;
  }

  /**
   * Executes the run method from the {@code logic} parameter using
   * this memory area as the current allocation context. This method
   * behaves as if it moves the allocation context down the scope stack
   * to the occurrence of {@code this}.
   *
   * @param logic The runnable object whose {@code run()} method should
   *          be executed.
   * @throws IllegalTaskStateException when the execution context
   *           is not an instance of {@link javax.realtime.Schedulable}.
   * @throws InaccessibleAreaException when the memory area is not in
   *           the schedulable's scope stack.
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *         execution context is an instance of
   *         {@link javax.realtime.Schedulable} schedulable and
   *         {@code logic} is {@code null}.
   */
  @Override
  public void executeInArea(Runnable logic)
    throws IllegalTaskStateException,
           StaticIllegalArgumentException,
           InaccessibleAreaException
  {    // executeInArea0(logic);
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed
   * method is called {@code get} and an object is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public <T> T executeInArea(Supplier<T> logic)
    throws IllegalTaskStateException,
           StaticIllegalArgumentException,
           InaccessibleAreaException
  {
    return null;
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed
   * method is called {@code get} and a {@code boolean} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public boolean executeInArea(BooleanSupplier logic)
    throws IllegalTaskStateException,
           StaticIllegalArgumentException,
           InaccessibleAreaException
  {
    return false;
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed
   * method is called {@code get} and an {@code int} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public int executeInArea(IntSupplier logic)
    throws IllegalTaskStateException,
           StaticIllegalArgumentException,
           InaccessibleAreaException
  {
    return 0;
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed
   * method is called {@code get} and a {@code long} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public long executeInArea(LongSupplier logic)
    throws IllegalTaskStateException,
           StaticIllegalArgumentException,
           InaccessibleAreaException
  {
    return 0L;
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed
   * method is called {@code get} and a {@code double} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   */
  @Override
  public double executeInArea(DoubleSupplier logic)
    throws IllegalTaskStateException,
           StaticIllegalArgumentException,
           InaccessibleAreaException
  {
    return 0.0;
  }

  /**
   * Returns a reference to the portal object in this instance of
   * {@code ScopedMemory}.
   *
   * <p> Assignment rules are enforced on the value returned by
   * {@code getPortal} as if the return value were first stored in
   * an object allocated in the current allocation context, then moved
   * to its final destination.
   *
   * @return a reference to the portal object or {@code null} when
   *         there is no portal object.  The portal value is always set
   *         to {@code null} when the contents of the memory are
   *         deleted.
   * @throws javax.realtime.IllegalAssignmentError
   *         when a reference to the portal object cannot be stored in the
   *         caller's allocation context; that is, when the object is
   *         allocated in a more deeply nested scoped memory than the
   *         current allocation context or not on the caller's scope stack.
   * @throws IllegalTaskStateException when the execution context
   *           is not an instance of {@link javax.realtime.Schedulable}.
   */
  public Object getPortal()
    throws IllegalAssignmentError, IllegalTaskStateException
  {
    return new Object();
  }

  /**
   * Returns the reference count of this {@code ScopedMemory}.
   *
   * <p> Note that a reference count of zero reliably means
   * that the scope is not referenced, but other reference counts are
   * subject to artifacts of lazy/eager maintenance by the
   * implementation.
   *
   * @return the reference count of this {@code ScopedMemory}.
   */
  public final int getReferenceCount()
  {
    return 0;
  }

  /**
   * Waits until the reference count of this {@code ScopedMemory}
   * goes down to zero.  Returns immediately when the memory is
   * unreferenced.
   *
   * @throws java.lang.InterruptedException
   *         when this schedulable is interrupted by
   *         {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   * @throws IllegalTaskStateException when the execution context
   *         is not an instance of {@link javax.realtime.Schedulable}.
   */
  public void join()
    throws InterruptedException
  {
  }

  /**
   * Waits at most until the time designated by the {@code time}
   * parameter for the reference count of this {@code ScopedMemory}
   * to drop to zero.  Returns immediately when the memory area is
   * unreferenced.
   *
   * <p> Since the time is expressed as a {@link javax.realtime.HighResolutionTime},
   * this method is an accurate timer with nanosecond granularity. The
   * actual resolution of the timer and even the quantity it measures
   * depends on the clock associated with {@code time}. The delay
   * time may be relative or absolute. When relative, then the delay is
   * the amount of time given by {@code time}, and measured by its
   * associated clock. When absolute, then the delay is until the
   * indicated value is reached by the clock. When the given absolute time
   * is less than or equal to the current value of the clock, the call
   * to {@code join} returns immediately.
   *
   * @param time
   *          When this time is an absolute time, the wait is bounded by that
   *          point in time.
   *          When the time is a relative time (or a member of the
   *          {@code RationalTime} subclass of {@code RelativeTime})
   *          the wait is bounded by a the specified
   *          interval from some time between the time {@code join} is
   *          called and the
   *          time it starts waiting for the reference count to reach zero.
   * @throws java.lang.InterruptedException
   *         when this schedulable is interrupted by
   *         {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   * @throws IllegalTaskStateException when the execution context
   *           is not an instance of {@link javax.realtime.Schedulable}.
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *           execution context is a schedulable and {@code time} is
   *           {@code null}.
   * @throws StaticUnsupportedOperationException
   *           when the
   *           wait operation is not supported
   *           using the clock associated with {@code time}.
   */
  public void join(HighResolutionTime<?> time)
    throws InterruptedException
  {
  }

  /**
   * In the error-free case, {@code joinAndEnter} combines
   * <code>join();enter();</code> such that no {@code enter()} from
   * another schedulable can intervene between the two method
   * invocations.  The resulting method will wait for the reference
   * count on this {@code ScopedMemory} to reach zero, then enters
   * the {@code ScopedMemory} and executes the {@code run}
   * method from {@code logic} passed in the constructor. When no
   * instance of {@code Runnable} was passed to the memory area's
   * constructor, the method throws
   * {@code StaticIllegalArgumentException} immediately.
   *
   * <p> When multiple threads are waiting in {@code joinAndEnter}
   * family methods for a memory area, at most <em>one</em> of them will
   * be released each time the reference count goes to zero.
   *
   * <p> Note that although {@code joinAndEnter} guarantees that
   * the reference count is zero when the schedulable is released
   * for entry, it does not guarantee that the reference count will
   * remain one for any length of time. A subsequent {@code enter}
   * could raise the reference count to two.
   *
   * @throws java.lang.InterruptedException
   *         when this schedulable is interrupted by
   *         {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   * @throws IllegalTaskStateException when the execution context
   *         is not an instance of {@link javax.realtime.Schedulable} or
   *         when this method is invoked during finalization of objects in
   *         scoped memory and entering this scoped memory area would force
   *         deletion of the task that triggered finalization.  This
   *         would include the scope containing the task, and the scope
   *         (if any) containing the scope containing the task.
   * @throws ThrowBoundaryError when the JVM needs
   *         to propagate an exception allocated in {@code this} scope
   *         to (or through) the memory area of the caller. Storing a
   *         reference to that exception would cause an
   *         {@link javax.realtime.IllegalAssignmentError}, so the JVM
   *         cannot be permitted to deliver the exception.  The
   *         {@link javax.realtime.ThrowBoundaryError} is allocated in the
   *         current allocation context and contains information about
   *         the exception it replaces.
   * @throws javax.realtime.ScopedCycleException
   *           when this invocation would break the single
   *           parent rule.
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *           execution context is a schedulable and no non-null
   *           {@code logic} value was supplied to the memory area's
   *           constructor.
   * @throws MemoryAccessError
   *           when caller is a non-heap schedulable and this memory
   *           area's logic
   *           value is allocated in heap memory.
   */

  /*
   * Note that David Holmes observed that no joinAndEnter method without a
   * time-in value can
   * throw a scopedCycleException.
   * Here's the reasoning:
   * >> - if MA has a parent then MA must have been pushed on a scope stack
   * >> - if MA was pushed on a scope stack then its ref count must be > 0
   * >> - if ref count > 0 then join cannot proceed until ref count == 0
   * >> - if ref count == 0 then MA cannot be on scope stack and so has no
   * parent.
   *
   * This is not true because of MA given as an initial MA of a ASBEH might
   * not be entered, but it is still locked in its scope tree.
   */
  public void joinAndEnter()
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           MemoryAccessError
  {
  }

  /**
   * In the error-free case, {@code joinAndEnter} combines
   * <code>join();enter();</code> such that no {@code enter()} from
   * another schedulable can intervene between the two method
   * invocations. The resulting method will wait for the reference count
   * on this {@code ScopedMemory} to reach zero, or for the current
   * time to reach the designated time, then enter the
   * {@code ScopedMemory} and execute the {@code run} method
   * from {@code Runnable} object passed to the constructor. When no
   * instance of {@code Runnable} was passed to the memory area's
   * constructor, the method throws
   * {@code StaticIllegalArgumentException} immediately.
   *
   * <p> When multiple threads are waiting in {@code joinAndEnter}
   * family methods for a memory area, at most <em>one</em> of them will
   * be released each time the reference count goes to zero.
   *
   * <p> Since the time is expressed as a {@link javax.realtime.HighResolutionTime},
   * this method has an accurate timer with nanosecond granularity. The
   * actual resolution of the timer and even the quantity it measures
   * depends on the clock associated with {@code time}. The delay
   * time may be relative or absolute. When relative, then the calling
   * thread is blocked for at most the amount of time given by
   * {@code time}, and measured by its associated clock. When
   * absolute, then the time delay is until the indicated value is
   * reached by the clock. When the given absolute time is less than or
   * equal to the current value of the clock, the call to
   * {@code joinAndEnter} behaves effectively like {@link #enter}.
   *
   * <p> Note that expiration of {@code time} may cause control to
   * enter the memory area before its reference count has gone to zero.
   *
   * @param time
   *          The time that bounds the wait.
   * @throws javax.realtime.ThrowBoundaryError
   *           when the JVM needs to propagate an exception allocated in
   *           {@code this} scope
   *           to (or through) the memory area of the caller. Storing a
   *           reference to that exception would cause
   *           an {@link javax.realtime.IllegalAssignmentError}, so the JVM
   *           cannot be permitted to deliver the exception.
   *           The {@link javax.realtime.ThrowBoundaryError} is allocated in
   *           the current allocation context and contains information about
   *           the exception it replaces.
   * @throws java.lang.InterruptedException
   *         when this schedulable is interrupted by
   *         {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   * @throws javax.realtime.IllegalTaskStateException when the execution context
   *           is not an instance of {@link javax.realtime.Schedulable} or
   *           when this method is invoked during finalization of objects in
   *           scoped memory and entering this scoped memory area would
   *           force deletion of the task that triggered finalization.
   *           This would include the scope containing the task, and the
   *           scope (if any) containing the scope containing the task.
   * @throws javax.realtime.ScopedCycleException when the execution
   *           context is a schedulable and this invocation would break
   *           the single parent rule.
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *           execution context is a schedulable, and {@code time} is
   *           {@code null} or no non-null {@code logic} value was
   *           supplied to the memory area's constructor.
   * @throws javax.realtime.StaticUnsupportedOperationException when the wait
   *         operation is not supported using the clock associated with
   *         {@code time}.
   * @throws javax.realtime.MemoryAccessError when calling schedulable may not
   *         use the heap and this memory area's logic value is allocated in
   *         heap memory.
   */
  public void joinAndEnter(HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           MemoryAccessError
  {
  }

  /**
   * In the error-free case, {@code joinAndEnter} combines
   * {@code join();} and {@code enter();} such that no {@code enter()}
   * from another schedulable can intervene between the two method
   * invocations. The resulting method will wait for the reference count
   * on this {@code ScopedMemory} to reach zero,
   * then enter the {@code ScopedMemory} and execute the {@code run}
   * method from {@code logic}
   *
   * <p> When {@code logic} is {@code null}, the method throws
   * {@code StaticIllegalArgumentException} immediately.
   *
   * <p> When multiple threads are waiting in {@code joinAndEnter}
   * family methods for a memory area, at most <em>one</em> of them will
   * be released each time the reference count goes to zero.
   *
   * <p> Note that although {@code joinAndEnter} guarantees that
   * the reference count is zero when the schedulable is released
   * for entry, it does not guarantee that the reference count will
   * remain one for any length of time. A subsequent {@code enter}
   * could raise the reference count to two.
   *
   * @param logic The {@code Runnable} object which contains the
   *          code to execute.
   * @throws java.lang.InterruptedException
   *         when this schedulable is interrupted by
   *         {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   * @throws IllegalTaskStateException when the execution context
   *           is not an instance of {@link javax.realtime.Schedulable} or
   *           when this method is invoked during finalization of objects in
   *           scoped memory and entering this scoped memory area would
   *           force deletion of the task that triggered finalization.
   *           This would include the scope containing the task, and the
   *           scope (if any) containing the scope containing the task.
   * @throws ThrowBoundaryError thrown when the JVM needs
   *         to propagate an exception allocated in {@code this} scope
   *         to (or through) the memory area of the caller. Storing a
   *         reference to that exception would cause an
   *         {@link javax.realtime.IllegalAssignmentError}, so the JVM
   *         cannot be permitted to deliver the exception.  The
   *         {@link javax.realtime.ThrowBoundaryError} is allocated in
   *         the current allocation context and contains information about
   *         the exception it replaces.
   * @throws javax.realtime.ScopedCycleException when this invocation
   *           would break the single parent rule.
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *           execution context is a schedulable and {@code logic} is
   *           {@code null}.
   */
  public void joinAndEnter(Runnable logic)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           MemoryAccessError
  {
  }

  /**
   * Same as {@link #joinAndEnter(Runnable)} except that the executed
   * method is called {@code get} and an object is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public <T> T joinAndEnter(Supplier<T> logic)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           MemoryAccessError
  {
    return null;
  }

  /**
   * Same as {@link #joinAndEnter(Runnable)} except that the executed
   * method is called {@code get} and a {@code boolean} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public boolean joinAndEnter(BooleanSupplier logic)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           MemoryAccessError
  {
    return false;
  }

  /**
   * Same as {@link #joinAndEnter(Runnable)} except that the executed
   * method is called {@code get} and an {@code int} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public int joinAndEnter(IntSupplier logic)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           MemoryAccessError
  {
    return 0;
  }

  /**
   * Same as {@link #joinAndEnter(Runnable)} except that the executed
   * method is called {@code get} and a {@code long} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public long joinAndEnter(LongSupplier logic)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           MemoryAccessError
  {
    return 0L;
  }

  /**
   * Same as {@link #joinAndEnter(Runnable)} except that the executed
   * method is called {@code get} and a {@code double} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public double joinAndEnter(DoubleSupplier logic)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           MemoryAccessError
  {
    return 0.0;
  }

  /**
   * In the error-free case, {@code joinAndEnter} combines
   * <code>join();enter();</code> such that no {@code enter()} from
   * another schedulable can intervene between the two method
   * invocations. The resulting method will wait for the reference count
   * on this {@code ScopedMemory} to reach zero, or for the current
   * time to reach the designated time, then enter the
   * {@code ScopedMemory} and execute the {@code run} method
   * from {@code logic}.
   *
   * <p> Since the time is expressed as a {@link javax.realtime.HighResolutionTime},
   * this method is an accurate timer with nanosecond granularity. The
   * actual resolution of the timer and even the quantity it measures
   * depends on the clock associated with {@code time}. The delay
   * time may be relative or absolute. When relative, then the delay is
   * the amount of time given by {@code time}, and measured by its
   * associated clock. When absolute, then the delay is until the
   * indicated value is reached by the clock. When the given absolute time
   * is less than or equal to the current value of the clock, the call
   * to {@code joinAndEnter} behaves effectively like {@link
   * #enter(Runnable)}.
   *
   * <p> The method throws {@code StaticIllegalArgumentException} immediately when
   * {@code logic} is {@code null}.
   *
   * <p> When multiple threads are waiting in {@code joinAndEnter}
   * family methods for a memory area, at most <em>one</em> of them will
   * be released each time the reference count goes to zero.
   *
   * <p> Note that expiration of {@code time} may cause control to
   * enter the memory area before its reference count has gone to zero.
   *
   * @param logic The {@code Runnable} object which contains the
   *          code to execute.
   * @param time
   *          The time that bounds the wait.
   * @throws java.lang.InterruptedException
   *         when this schedulable is interrupted by
   *         {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   * @throws IllegalTaskStateException when the execution context
   *         is not an instance of {@link javax.realtime.Schedulable} or
   *         when this method is invoked during finalization of objects in
   *         scoped memory and entering this scoped memory area would force
   *         deletion of the task that triggered finalization.  This
   *         would include the scope containing the task, and the scope
   *         (if any) containing the scope containing the task.
   * @throws ThrowBoundaryError
   *           when the JVM needs to propagate an exception allocated in
   *           {@code this} scope
   *           to (or through) the memory area of the caller. Storing a
   *           reference to that exception would cause
   *           a {@link javax.realtime.IllegalAssignmentError}, so the JVM
   *           cannot be permitted to deliver the exception.
   *           The {@link javax.realtime.ThrowBoundaryError} is preallocated
   *           and saves information about the exception it replaces.
   * @throws javax.realtime.ScopedCycleException when the execution
   *           context is a schedulable and this invocation would break
   *           the single parent rule.
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *         execution context is a schedulable and {@code time} or
   *         {@code logic} is {@code null}.
   * @throws javax.realtime.StaticUnsupportedOperationException when the
   *          wait operation is not supported using the clock associated
   *          with {@code time}.
   */
  public void joinAndEnter(Runnable logic, HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           MemoryAccessError
  {
  }

  /**
   * Same as {@link #joinAndEnter(Runnable, HighResolutionTime)} except that
   * the executed method is called {@code get} and an object
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public <P> P joinAndEnter(Supplier<P> logic, HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           MemoryAccessError
  {
    return null;
  }

  /**
   * Same as {@link #joinAndEnter(Runnable, HighResolutionTime)} except that
   * the executed method is called {@code get} and a {@code boolean}
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public boolean joinAndEnter(BooleanSupplier logic, HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           MemoryAccessError
  {
    return false;
  }

  /**
   * Same as {@link #joinAndEnter(Runnable, HighResolutionTime)} except that
   * the executed method is called {@code get} and an {@code int}
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public int joinAndEnter(IntSupplier logic, HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           MemoryAccessError
  {
    return 0;
  }

  /**
   * Same as {@link #joinAndEnter(Runnable, HighResolutionTime)} except that
   * the executed method is called {@code get} and a {@code long}
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public long joinAndEnter(LongSupplier logic, HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           MemoryAccessError
  {
    return 0L;
  }

  /**
   * Same as {@link #joinAndEnter(Runnable, HighResolutionTime)} except that
   * the executed method is called {@code get} and a {@code double}
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public double joinAndEnter(DoubleSupplier logic, HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           ThrowBoundaryError,
           ScopedCycleException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           MemoryAccessError
  {
    return 0.0;
  }

  /**
   * Returns a reference to this scope's parent scope (e.g., its parent in the
   * single-parent-rule tree).
   *
   * @return a reference to the next outer scoped memory region on the caller's
   *         scope stack.
   *         <ul>
   *         <li>When there is no outer scoped memory and the primordial parent is
   *         heap memory, returns a reference to {@code this}.
   *         <li>When there is no outer scoped memory and the primordial parent is
   *         immortal, or when {@code this} is unreferenced and unpinned,
   *         returns {@code null}
   *         </ul>
   *         <p>
   *         <em>Problem.  The single-parent tree is RTT-independent except for
   * the primordial scope.  The type of the primordial scope is RTT-dependent.
   * What should we do about that?  When called from a RTT that has entered
   * {@code this}, the above rules make some sense, but what if the caller
   * has not even entered the scope, should we throw an exception?  Or just
   * return {@code null}? I think the right solution is to return
   * {@code this} whatever the type of the primordial scope.  The app can
   * then know that {@code null} means the scope is not pinned and not
   * referenced, and {@code this} means the parent is either heap or
   * immortal.  At that point, the app can learn what it wants to know by
   * just finding what memory area contains the scope object.</em>
   *
   * @since RTSJ 2.0
   */
  public MemoryArea getParent()
  {
    return null;
  }


  /**
   * A means of accessing all live nested scoped memories parented in this
   * scoped memory, even those to which no reference exists, such a
   * {@link javax.realtime.memory.PinnableMemory} that is pinned
   * or another {@code javax.realtime.memory.ScopedMemory} that contains a
   * {@code Schedulable}.  It has the same semantics as the method
   * {@link #visitScopeRoots}, except for the following:
   * <ul>
   * <li> what scoped memories are visited,</li>
   * <li> the memory area must be reachable from the current scope stack,
   *      and</li>
   * <li> there is not security manager check.</li>
   * </ul>
   *
   * @param visitor Determines the action to be performed on each of the
   *        children scopes.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         visitor is {@code null}.
   *
   * @throws ForEachTerminationException when the traversal ends prematurely.
   *
   * @since RTSJ 2.0
   */
  public void visitNestedScopes(Consumer<ScopedMemory> visitor)
    throws StaticIllegalArgumentException, ForEachTerminationException
  {
  }

  /**
   * Allocates an array of the given type in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param type {@inheritDoc}
   * @param number {@inheritDoc}
   * @return {@inheritDoc}
   * @throws javax.realtime.StaticIllegalArgumentException {@inheritDoc}
   * @throws javax.realtime.StaticOutOfMemoryError {@inheritDoc}
   * @throws IllegalTaskStateException when the execution context
   *           is not an instance of {@link javax.realtime.Schedulable}.
   * @throws InaccessibleAreaException
   *           when the memory area is not in the schedulable's
   *           scope stack.
   */
  @Override
  public Object newArray(Class<?> type, int number)
  {
    return null; // newArray0(type, number);
  }

  /**
   * Allocates an object in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param type {@inheritDoc}
   * @return {@inheritDoc}
   * @throws IllegalAccessException {@inheritDoc}
   * @throws javax.realtime.StaticIllegalArgumentException {@inheritDoc}
   * @throws ExceptionInInitializerError {@inheritDoc}
   * @throws javax.realtime.StaticOutOfMemoryError {@inheritDoc}
   * @throws InstantiationException {@inheritDoc}
   * @throws IllegalTaskStateException when the execution context
   *           is not an instance of {@link javax.realtime.Schedulable}.
   * @throws InaccessibleAreaException
   *           when the memory area is not in the schedulable's
   *           scope stack.
   */
  @Override
  public <T> T newInstance(Class<T> type)
    throws IllegalAccessException,
           StaticIllegalArgumentException,
           ExceptionInInitializerError,
           StaticOutOfMemoryError,
           InstantiationException,
           IllegalTaskStateException,
           InaccessibleAreaException
  {
    return null; // newInstance0(type);
  }

  /**
   * Allocates an object in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param c T {@inheritDoc}
   * @param args {@inheritDoc}
   * @return {@inheritDoc}
   * @throws IllegalAccessException {@inheritDoc}
   * @throws InstantiationException {@inheritDoc}
   * @throws javax.realtime.StaticOutOfMemoryError {@inheritDoc}
   * @throws javax.realtime.StaticIllegalArgumentException {@inheritDoc}
   * @throws IllegalTaskStateException when the execution context
   *           is not an instance of {@link javax.realtime.Schedulable}.
   * @throws InvocationTargetException {@inheritDoc}
   * @throws InaccessibleAreaException
   *           when the memory area is not in the schedulable's
   *           scope stack.
   */
  @Override
  public <T> T newInstance(Constructor<T> c, Object[] args)
    throws IllegalAccessException,
      InstantiationException,
      InvocationTargetException
  {
    return null; // newInstance1(c, args);
  }

  /**
   * Sets the <em>portal</em> object of the memory area represented by this
   * instance of {@code ScopedMemory} to the given object. The object must
   * have been
   * allocated in this {@code ScopedMemory} instance.
   *
   * @param object
   *          The object which will become the portal for this.
   *          When {@code null} the previous portal object remains the
   *          portal object for this or when there was no previous
   *          portal object then there is still no portal object
   *          for {@code this}.
   * @throws IllegalTaskStateException when the execution context
   *         is not an instance of {@link javax.realtime.Schedulable}.
   * @throws IllegalAssignmentError when the execution
   *         context is an instance of {@link javax.realtime.Schedulable},
   *         and {@code object} is not allocated in this scoped memory
   *         instance and not {@code null}.
   * @throws InaccessibleAreaException when the execution context is a
   *         schedulable, {@code this} memory area is not in the
   *         caller's scope stack and {@code object} is not {@code null}.
   */
  public void setPortal(Object object)
    throws IllegalTaskStateException,
           IllegalAssignmentError,
           InaccessibleAreaException
  {
  }

  /**
   * Since there is no specified way to release the memory of a memory
   * area explicitly, this is done by its finalize method.
   *
   * @throws InternalError when this is currently in use
   * (i.e., it has a non-zero enter count.)
   */
  protected final void finalize() throws Throwable
  {
  }

  /**
   * Returns a user-friendly representation of this
   * {@code ScopedMemory} of the form
   * {@code <class-name>@<num>} where {@code <class-name>}
   * is the name of the class, e.g. {@code javax.realtime.memory.ScopedMemory},
   * and {@code <num>} is a number that uniquely identifies this scoped memory
   * area.
   *
   * @return the string representation
   */
  @Override
  public String toString()
  {
    return super.toString();
  }
}
