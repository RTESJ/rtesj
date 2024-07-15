/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.realtime.control.AsynchronouslyInterruptedException;

/**
 * Equivalent to and superseded by {@link javax.realtime.memory.ScopedMemory}.
 *
 * @deprecated in RTSJ 2.0; moved to package {@code javax.realtime.memory}
 */
@Deprecated
public abstract class ScopedMemory extends MemoryArea
{
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
   * @throws IllegalArgumentException
   *           when {@code size} is less than
   *           zero.
   * @throws IllegalAssignmentError
   *           when storing {@code logic} in {@code this} would
   *           violate the assignment rules.
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *           {@code ScopedMemory} object or for its allocation area in
   *           its backing store.
   */
  public ScopedMemory(long size, Runnable logic)
  {
    super(size, logic);
  }

  /**
   * Equivalent to {@link #ScopedMemory(long, Runnable)}
   * with the argument list {@code (size.getEstimate(), logic)}.
   *
   * @param size The size of the new {@code ScopedMemory} area
   *          estimated by an instance of {@link SizeEstimator}.
   * @param logic The logic which will use the memory represented by
   *          {@code this} as its initial memory area. When
   *          {@code logic} is {@code null}, this constructor
   *          is equivalent to constructing the memory area without a
   *          logic value.
   * @throws IllegalArgumentException when {@code size} is {@code null},
   *           or {@code size.getEstimate()} is negative.
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *           {@code ScopedMemory} object or for its allocation area in
   *           its backing store.
   * @throws IllegalAssignmentError when storing {@code logic} in {@code
   *           this} would violate the assignment rules.
   */
  public ScopedMemory(SizeEstimator size, Runnable logic)
  {
    this(size.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #ScopedMemory(long, Runnable)}
   * with the argument list {@code (size, null)}.
   *
   * @param size of the new {@code ScopedMemory} area in bytes.
   *
   * @throws IllegalArgumentException when {@code size} is less than
   *           zero.
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *           {@code ScopedMemory} object or for its allocation area in
   *           its backing store.
   */
  public ScopedMemory(long size)
  {
    this(size, null);
  }

  /**
   * Equivalent to {@link #ScopedMemory(long, Runnable)}
   * with the argument list {@code (size.getEstimate(), null)}.
   *
   * @param size The size of the new {@code ScopedMemory} area
   *          estimated by an instance of {@link SizeEstimator}.
   * @throws IllegalArgumentException
   *           when {@code size} is {@code null},
   *           or {@code size.getEstimate()} is negative.
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *           {@code ScopedMemory} object or for its allocation area in
   *           its backing store.
   */
  public ScopedMemory(SizeEstimator size)
  {
    this(size.getEstimate(), null);
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
   * @throws ScopedCycleException when this invocation would break the
   *           single parent rule.
   * @throws ThrowBoundaryError when the JVM needs to propagate
   *           an exception allocated in {@code this} scope to (or
   *           through) the memory area of the caller. Storing a
   *           reference to that exception would cause an {@link
   *           IllegalAssignmentError}, so the JVM cannot be permitted
   *           to deliver the exception.  The {@link ThrowBoundaryError}
   *           is allocated in the current allocation context and
   *           contains information about the exception it replaces.
   * @throws IllegalThreadStateException when the caller is a Java
   *           thread, or when this method is invoked during finalization
   *           of objects in scoped memory and entering this scoped
   *           memory area would force deletion of the SO that triggered
   *           finalization.  This would include the scope containing
   *           the SO, and the scope (if any) containing the scope
   *           containing the SO.
   * @throws IllegalArgumentException {@inheritDoc}
   * @throws MemoryAccessError {@inheritDoc}
   */
  @Override
  public void enter()
  {
  }

  /**
   * Associates this memory area with the current
   * schedulable for the duration of the execution of the
   * {@code run()} method of the given {@code Runnable}.
   * During this period of execution, this memory area
   * becomes the default allocation context until another
   * default allocation context is selected (using {@code enter},
   * or {@link #executeInArea}) or
   * the {@code enter} method exits.
   *
   * @param logic {@inheritDoc}
   * @throws ScopedCycleException
   *           when this invocation would break the single
   *           parent rule.
   * @throws ThrowBoundaryError when the JVM needs to propagate
   *           an exception allocated in {@code this} scope to (or
   *           through) the memory area of the caller. Storing a
   *           reference to that exception would cause an
   *           {@link IllegalAssignmentError}, so the JVM cannot be
   *           permitted to deliver the exception.  The
   *           {@link ThrowBoundaryError} is allocated in the current
   *           allocation context and contains information about the
   *           exception it replaces.
   * @throws IllegalThreadStateException when the caller is a Java
   *           thread, or when this method is invoked during finalization
   *           of objects in scoped memory and entering this scoped
   *           memory area would force deletion of the SO that triggered
   *           finalization.  This would include the scope containing
   *           the SO, and the scope (if any) containing the scope
   *           containing the SO.
   * @throws IllegalArgumentException {@inheritDoc}
   */
  @Override
  public void enter(Runnable logic)
  {
  }

  /**
   * Executes the run method from the {@code logic} parameter using
   * this memory area as the current allocation context. This method
   * behaves as if it moves the allocation context down the scope stack
   * to the occurrence of {@code this}.
   *
   * @param logic
   *          The runnable object whose {@code run()} method should
   *          be executed.
   * @throws IllegalThreadStateException
   *         when the caller context in not an instance of {@link Schedulable}.
   * @throws InaccessibleAreaException
   *           when the memory area is
   *           not in the schedulable's
   *           scope stack.
   * @throws IllegalArgumentException
   *           when the caller is a schedulable and
   *           {@code logic} is {@code null}.
   */
  @Override
  public void executeInArea(Runnable logic)
  {    // executeInArea0(logic);
  }


  /**
   * Gets the maximum size this memory area can attain. If this is
   * a fixed size memory area, the returned value will be equal
   * to the initial size.
   *
   * @return the maximum size attainable.
   */
  public long getMaximumSize()
  {
    return 0;
  }

  /**
   * Obtains a reference to the portal object in this instance of
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
   * @throws IllegalAssignmentError
   *           when a reference to the portal object cannot
   *           be stored in the caller's allocation context; that is, when
   *           {@code this} is "inner" relative to the current allocation
   *           context or not on the caller's scope stack.
   * @throws IllegalThreadStateException
   *         when the caller context in not an instance of {@link Schedulable}.
   */
  public Object getPortal()
  {
    return new Object();
  }

  /**
   * Returns the reference count of this {@code ScopedMemory}.
   *
   * <p> Note that a reference count of 0 reliably means
   * that the scope is not referenced, but other reference counts are
   * subject to artifacts of lazy/eager maintenance by the
   * implementation.
   *
   * @return the reference count of this {@code ScopedMemory}.
   */
  public int getReferenceCount()
  {
    return 0;
  }

  /**
   * Waits until the reference count of this {@code ScopedMemory}
   * goes down to zero.  Returns immediately when the memory is
   * unreferenced.
   *
   * @throws InterruptedException
   *              When this schedulable is interrupted by
   *              {@link RealtimeThread#interrupt()} or
   *              {@link AsynchronouslyInterruptedException#fire()} while
   *              waiting for the reference count to go to zero.
   * @throws IllegalThreadStateException
   *         when the caller context in not an instance of {@link Schedulable}.
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
   * <p> Since the time is expressed as a {@link HighResolutionTime},
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
   * @param time When this time is an absolute time, the wait is bounded
   *          by that point in time. When the time is a relative time,
   *          the wait is bounded by a the specified interval from some
   *          time between the time {@code join} is called and the time
   *          it starts waiting for the reference count to reach zero.
   * @throws InterruptedException When this schedulable is interrupted by
   *              {@link RealtimeThread#interrupt()} or
   *              {@link AsynchronouslyInterruptedException#fire()} while
   *              waiting for the reference count to go to zero.
   * @throws IllegalThreadStateException
   *         when the caller context in not an instance of {@link Schedulable}.
   * @throws IllegalArgumentException when the caller is a schedulable
   *           and {@code time} is {@code null}.
   * @throws UnsupportedOperationException when the wait operation is
   *           not supported using the clock associated with {@code time}.
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
   * count on this {@code ScopedMemory} to reach zero, then enter
   * the {@code ScopedMemory} and execute the {@code run}
   * method from {@code logic} passed in the constructor. When no
   * instance of {@code Runnable} was passed to the memory area's
   * constructor, the method throws
   * {@code IllegalArgumentException} immediately.
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
   * @throws InterruptedException
   *              When this schedulable is interrupted by
   *              {@link RealtimeThread#interrupt()} or
   *              {@link AsynchronouslyInterruptedException#fire()} while
   *              waiting for the reference count to go to zero.
   * @throws IllegalThreadStateException when the caller is a Java
   *           thread, or when this method is invoked during finalization
   *           of objects in scoped memory and entering this scoped
   *           memory area would force deletion of the SO that triggered
   *           finalization.  This would include the scope containing
   *           the SO, and the scope (if any) containing the scope
   *           containing the SO.
   * @throws ThrowBoundaryError when the JVM needs to propagate
   *           an exception allocated in {@code this} scope to (or
   *           through) the memory area of the caller. Storing a
   *           reference to that exception would cause an {@link
   *           IllegalAssignmentError}, so the JVM cannot be permitted
   *           to deliver the exception.  The {@link ThrowBoundaryError}
   *           is allocated in the current allocation context and
   *           contains information about the exception it replaces.
   * @throws ScopedCycleException
   *           when this invocation would break the single
   *           parent rule.
   * @throws IllegalArgumentException
   *           when the caller is a schedulable and no non-null
   *           {@code logic} value
   *           was supplied to the memory area's constructor.
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
   */

  public void joinAndEnter()
    throws InterruptedException
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
   * {@code IllegalArgumentException} immediately.  *
   *
   * <p> When multiple threads are waiting in {@code joinAndEnter}
   * family methods for a memory area, at most <em>one</em> of them will
   * be released each time the reference count goes to zero.
   *
   * <p> Since the time is expressed as a {@link HighResolutionTime},
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
   * @throws ThrowBoundaryError
   *           when the JVM needs to propagate an exception allocated in
   *           {@code this} scope
   *           to (or through) the memory area of the caller. Storing a
   *           reference to that exception would cause
   *           an {@link IllegalAssignmentError}, so the JVM cannot be permitted
   *           to deliver the exception.
   *           The {@link ThrowBoundaryError} is allocated in the current
   *           allocation context and contains information about the exception
   *           it replaces.
   * @throws InterruptedException
   *              When this schedulable is interrupted by
   *              {@link RealtimeThread#interrupt()} or
   *              {@link AsynchronouslyInterruptedException#fire()} while
   *              waiting for the reference count to go to zero.
   * @throws IllegalThreadStateException when the caller context is not
   *         an instance of {@link Schedulable}, or when this method is
   *         invoked during finalization of objects in scoped memory and
   *         entering this scoped memory area would force deletion of
   *         the instance that triggered finalization.  This would include the
   *         scope containing the instance, and the scope (if any) containing
   *         the scope containing the instance.
   * @throws ScopedCycleException
   *           when the caller is a schedulable and this invocation
   *           would break the single
   *           parent rule.
   * @throws IllegalArgumentException when the caller is a schedulable,
   *           and {@code time} is {@code null} or {@code null} was
   *           supplied as {@code logic} value to the memory area's
   *           constructor.
   * @throws UnsupportedOperationException when the wait operation is
   *           not supported using the clock associated with {@code time}.
   * @throws MemoryAccessError when the calling schedulable may not use
   *           the heap and this memory area's logic value is allocated
   *           in heap memory.
   */
  public void joinAndEnter(HighResolutionTime<?> time)
    throws InterruptedException
  {
  }

  /**
   * In the error-free case, {@code joinAndEnter} combines
   * <code>join();enter();</code> such that no {@code enter()} from
   * another schedulable can intervene between the two method
   * invocations. The resulting method
   * will wait for the reference count on this {@code ScopedMemory} to
   * reach zero,
   * then enter the {@code ScopedMemory} and execute the {@code run}
   * method from {@code logic}
   *
   * <p> When {@code logic} is {@code null}, throw
   * {@code IllegalArgumentException} immediately.
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
   * @throws InterruptedException
   *              When this schedulable is interrupted by
   *              {@link RealtimeThread#interrupt()} or
   *              {@link AsynchronouslyInterruptedException#fire()} while
   *              waiting for the reference count to go to zero.
   * @throws IllegalThreadStateException when the caller is a Java
   *           thread, or when this method is invoked during finalization
   *           of objects in scoped memory and entering this scoped
   *           memory area would force deletion of the SO that triggered
   *           finalization.  This would include the scope containing
   *           the SO, and the scope (if any) containing the scope
   *           containing the SO.
   * @throws ThrowBoundaryError when the JVM needs to propagate
   *           an exception allocated in {@code this} scope to (or
   *           through) the memory area of the caller. Storing a
   *           reference to that exception would cause an {@link
   *           IllegalAssignmentError}, so the JVM cannot be permitted
   *           to deliver the exception.  The {@link ThrowBoundaryError}
   *           is allocated in the current allocation context and
   *           contains information about the exception it replaces.
   * @throws ScopedCycleException
   *           when this invocation would break the single
   *           parent rule.
   * @throws IllegalArgumentException
   *           when the caller is a schedulable and
   *           {@code logic} is {@code null}.
   */
  public void joinAndEnter(Runnable logic)
    throws InterruptedException
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
   * from {@code logic}.
   *
   * <p> Since the time is expressed as a {@link HighResolutionTime},
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
   * <p> Throws {@code IllegalArgumentException} immediately when
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
   * @throws InterruptedException
   *              When this schedulable is interrupted by
   *              {@link RealtimeThread#interrupt()} or
   *              {@link AsynchronouslyInterruptedException#fire()} while
   *              waiting for the reference count to go to zero.
   * @throws IllegalThreadStateException when the execution context in
   *         not an instance of {@link Schedulable}, or when this method
   *         is invoked during finalization of objects in scoped memory
   *         and entering this scoped memory area would force deletion
   *         of the task that triggered finalization.  This would
   *         include the scope containing the task, and the scope (if
   *         any) containing the scope containing the task.
   * @throws ThrowBoundaryError
   *           when the JVM needs to propagate an exception allocated in
   *           {@code this} scope
   *           to (or through) the memory area of the caller. Storing a
   *           reference to that exception would cause
   *           an {@link IllegalAssignmentError}, so the JVM cannot be permitted
   *           to deliver the exception.
   *           The {@link ThrowBoundaryError} is allocated in the current
   *           allocation context and contains information about the exception
   *           it replaces.
   * @throws ScopedCycleException
   *           when the caller is a schedulable and this invocation
   *           would break the single
   *           parent rule.
   * @throws IllegalArgumentException
   *           when the caller is a schedulable and
   *           {@code time} or {@code logic} is {@code null}.
   * @throws UnsupportedOperationException
   *           when the wait operation is not supported
   *           using the clock associated with {@code time}.
   */
  public void joinAndEnter(Runnable logic, HighResolutionTime<?> time)
    throws InterruptedException
  {
  }


  /**
   * Allocates an array of the given type in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param type {@inheritDoc}
   * @param number {@inheritDoc}
   * @return {@inheritDoc}
   * @throws IllegalArgumentException {@inheritDoc}
   * @throws StaticOutOfMemoryError {@inheritDoc}
   * @throws IllegalThreadStateException
   *         when the caller context in not an instance of {@link Schedulable}.
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
   * @throws IllegalArgumentException {@inheritDoc}
   * @throws ExceptionInInitializerError {@inheritDoc}
   * @throws StaticOutOfMemoryError {@inheritDoc}
   * @throws InstantiationException {@inheritDoc}
   * @throws IllegalThreadStateException when the caller context in not
   *         an instance of {@link Schedulable}.
   * @throws InaccessibleAreaException when the memory area is not in
   *           the schedulable's scope stack.
   */
  @Override
  public <T> T newInstance(Class<T> type)
    throws IllegalAccessException,
      InstantiationException
  {
    return null; // newInstance0(type);
  }

  /**
   * Allocates an object in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param c T{@inheritDoc}
   * @param args {@inheritDoc}
   * @return {@inheritDoc}
   * @throws IllegalAccessException {@inheritDoc}
   * @throws InstantiationException {@inheritDoc}
   * @throws StaticOutOfMemoryError {@inheritDoc}
   * @throws IllegalArgumentException {@inheritDoc}
   * @throws IllegalThreadStateException
   *         when the caller context in not an instance of {@link Schedulable}.
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
   * have been allocated in this {@code ScopedMemory} instance.
   *
   * @param object
   *          The object which will become the portal for this.
   *          When {@code null} the previous portal object remains the
   *          portal object for this or when there was no previous
   *          portal object then there is still no portal object
   *          for {@code this}.
   * @throws IllegalThreadStateException
   *         when the caller context in not an instance of {@link Schedulable}.
   * @throws IllegalAssignmentError
   *           when the caller is a schedulable, and
   *           {@code object} is not allocated in this scoped memory
   *           instance and not {@code null}.
   * @throws InaccessibleAreaException when the caller is a schedulable,
   *           {@code this} memory area is not in the caller's
   *           scope stack and {@code object} is not {@code null}.
   */
  public void setPortal(Object object)
  {
  }

  /**
   * Returns a user-friendly representation of this
   * {@code ScopedMemory} of the form
   * {@code ScopedMemory#<num>} where {@code <num>} is a number
   * that uniquely identifies this scoped memory area.
   *
   * @return The string representation
   */
  @Override
  public String toString()
  {
    return "ScopedMemory#";
  }
}
