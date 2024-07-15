/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
/*
 * Created on Aug 14, 2009
 *
 * Copyright (C) 2004, 2005 TimeSys Corporation, All Rights Reserved.
 */

package javax.realtime.memory;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import javax.realtime.Clock;
import javax.realtime.HighResolutionTime;

import javax.realtime.IllegalTaskStateException;
import javax.realtime.MemoryAccessError;
import javax.realtime.ScopedCycleException;
import javax.realtime.SizeEstimator;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticIllegalStateException;
import javax.realtime.StaticOutOfMemoryError;
import javax.realtime.StaticUnsupportedOperationException;

/**
 * This class is for passing information between different threads as in the
 * producer consumer pattern.  One thread can enter an empty
 * {@code PinnableMemory}, allocate some data structure, put a reference in the
 * portal, pin the scope, exit it, and then pass it to another thread for
 * further processing or consumption.  Once the last thread is done, the memory
 * can be unpinned, causing its contents to be freed.
 *
 * <p>Creation of a {@code PinnableMemory} shall fail with a
 * {@link javax.realtime.StaticOutOfMemoryError} when the current
 * {@link javax.realtime.Schedulable} has been configured with a
 * {@link ScopedMemoryParameters#getMaxGlobalBackingStore} that would be
 * exceeded by said creation.
 *
 * @since RTSJ 2.0
 */
public class PinnableMemory
  extends ScopedMemory
{
  /**
   * Creates a scoped memory of fixed size that can be held open when no
   * {@link javax.realtime.Schedulable} has it on its scoped memory stack.
   *
   * @param size The number of bytes in the memory area.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is less than zero.
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory for the {@code PinnalbeMemory} object
   *         or for its allocation area in its backing store, or when
   *         the current {@code Schedulable} would exceed its configured
   *         allowance of global backing store.
   */
  public PinnableMemory(long size)
    throws StaticIllegalArgumentException, StaticOutOfMemoryError
  {
    super(size);
  }

  /**
   * Creates a scoped memory of fixed size that can be held open when no
   * {@link javax.realtime.Schedulable} has it on its scoped memory stack.
   *
   * @param size The number of bytes in the memory area.
   *
   * @param logic The logic to execute when none is provide at enter.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is less than zero.
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory for the {@code PinnalbeMemory} object
   *         or for its allocation area in its backing store, or when
   *         the current {@code Schedulable} would exceed its configured
   *         allowance of global backing store.
   */
  public PinnableMemory(long size, Runnable logic)
  {
    super(size, logic);
  }

  /**
   * Equivalent to {@link #PinnableMemory(long)} with
   * {@code size.getEstimate()} as its argument.
   *
   * @param size An estimator for determining the number of bytes in the
   *        memory area.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is {@code null}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory for the {@code PinnalbeMemory} object
   *         or for its allocation area in its backing store, or when
   *         the current {@code Schedulable} would exceed its configured
   *         allowance of global backing store.
   */
  public PinnableMemory(SizeEstimator size)
    throws StaticIllegalArgumentException, StaticOutOfMemoryError
  {
    super(size.getEstimate());
  }

  /**
   * Equivalent to {@link #PinnableMemory(long, Runnable)} with
   * {@code size.getEstimate()} as its first argument.
   *
   * @param size An estimator for determining the number of bytes in the
   *        memory area.
   *
   * @param logic The logic to execute when none is provide at enter.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code size} is {@code null}.
   *
   * @throws javax.realtime.StaticOutOfMemoryError when there is
   *         insufficient memory for the {@code PinnalbeMemory} object
   *         or for its allocation area in its backing store, or when
   *         the current {@code Schedulable} would exceed its configured
   *         allowance of global backing store.
   */
  public PinnableMemory(SizeEstimator size, Runnable logic)
  {
    this(size.getEstimate(), logic);
  }


  /**
   * Prevents the contents from being freed.
   *
   * @throws StaticIllegalStateException when the current allocation
   *         context is not {@code this} allocation context.
   */
  public void pin() throws StaticIllegalStateException
  {
  }

  /**
   * Allows the contents to be freed once no {@link javax.realtime.Schedulable}
   * is active within the scope.  The {@code unpin} method must be
   * called as many times as {@link #pin} to take effect.  If there is
   * no task in the area when the call takes affect, then the object in
   * the area are reclaimed immediately, in the caller's context.
   *
   * @throws javax.realtime.StaticIllegalStateException when
   *         schedulable does not have {@code this} memory area as its
   *         current memory area.
   *
   */
  public void unpin() throws StaticIllegalStateException
  {
  }

  /**
   * Determines whether the scope may be cleared on last exit.
   *
   * @return true when yes, otherwise false.
   */
  public boolean isPinned()
  {
    return true;
  }

  /**
   * Finds out how many times the scope has been pinned, but not unpinned.
   *
   * @return the number of outstanding pins.
   */
  public int getPinCount()
  {
    return 0;
  }

  /**
   * Same as {@link ScopedMemory#join()} except that the area may be
   * pinned so the memory may not have been cleared.
   *
   * @throws java.lang.InterruptedException when this schedulable is
   *         interrupted by {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   */
  public void joinPinned() throws InterruptedException
  {
  }

  /**
   * Same as {@link ScopedMemory#join(HighResolutionTime)}
   * except that the area may be pinned so the memory may not have been cleared.
   *
   * @param limit The maximum time to wait.
   *
   * @throws java.lang.InterruptedException when this schedulable is
   *         interrupted by {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   */
  public void joinPinned(HighResolutionTime<?> limit)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException
  {
  }

  /**
   * Same as {@link ScopedMemory#joinAndEnter()}
   * except that the area may be pinned so the memory may not have been cleared.
   *
   * @throws ScopedCycleException when the caller is a schedulable and
   *         this invocation would break the single parent rule.
   *
   * @throws java.lang.InterruptedException when this schedulable is
   *         interrupted by {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   *
   * @throws IllegalTaskStateException when the caller is a Java thread,
   *         or when this method is invoked during finalization of
   *         objects in scoped memory and entering this scoped memory
   *         area would force deletion of the instance of {@code Schedulable}
   *         that triggered finalization.  This would include the scope
   *         containing the instance of {@code Schedulable}, and the
   *         scope (if any) containing the scope containing the instance
   *         of {@code Schedulable}.
   *
   * @throws MemoryAccessError when calling schedulable may not use the
   *         heap and this memory area's {@code logic} value is allocated in
   *         heap memory.
   */
  public void joinAndEnterPinned()
    throws InterruptedException,
           ScopedCycleException,
           IllegalTaskStateException,
           MemoryAccessError
  {
  }


  /**
   * Same as {@link ScopedMemory#joinAndEnter(Runnable)} except that the
   * area may be pinned so the memory may not have been cleared.
   *
   * @param logic the code to be executed in this memory area.
   *
   * @throws ScopedCycleException when the caller is a schedulable and
   *         this invocation would break the single parent rule.
   *
   * @throws StaticIllegalArgumentException when {@code logic} is
   *         {@code null}.
   *
   * @throws java.lang.InterruptedException when this schedulable is
   *         interrupted by {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   *
   * @throws IllegalTaskStateException when the caller is a Java thread,
   *         or when this method is invoked during finalization of
   *         objects in scoped memory and entering this scoped memory
   *         area would force deletion of the instance of {@code Schedulable}
   *         that triggered finalization.  This would include the scope
   *         containing the instance of {@code Schedulable}, and the
   *         scope (if any) containing the scope containing the instance
   *         of {@code Schedulable}.
   *
   * @throws MemoryAccessError when calling schedulable may not use the
   *         heap and this memory area's {@code logic} value is allocated in
   *         heap memory.
   */
  public void joinAndEnterPinned(Runnable logic)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException,
           MemoryAccessError
  {
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable)} except that the
   * executed method is called {@code get} and an object is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public <T> T joinAndEnterPinned(Supplier<T> logic)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException,
           MemoryAccessError
  {
    return null;
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable)} except that the
   * executed method is called {@code get} and a {@code boolean} is
   * returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public boolean joinAndEnterPinned(BooleanSupplier logic)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return false;
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable)} except that the
   * executed method is called {@code get} and an {@code int} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public int joinAndEnterPinned(IntSupplier logic)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return 0;
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable)} except that the
   * executed method is called {@code get} and a {@code long} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public long joinAndEnterPinned(LongSupplier logic)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return 0L;
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable)} except that the
   * executed method is called {@code get} and a {@code double} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public double joinAndEnterPinned(DoubleSupplier logic)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return 0.0;
  }


  /**
   * Same as {@link ScopedMemory#joinAndEnter(HighResolutionTime)}
   * except that pinning is ignored so the memory may not have been cleared.
   *
   * @param limit The maximum time to wait.
   *
   * @throws ScopedCycleException when the caller is a schedulable and
   *         this invocation would break the single parent rule.
   *
   * @throws java.lang.InterruptedException when this schedulable is
   *         interrupted by {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   *
   * @throws IllegalTaskStateException when the caller is a Java thread,
   *         or when this method is invoked during finalization of
   *         objects in scoped memory and entering this scoped memory
   *         area would force deletion of the instance of {@code Schedulable}
   *         that triggered finalization.  This would include the scope
   *         containing the instance of {@code Schedulable}, and the
   *         scope (if any) containing the scope containing the instance
   *         of {@code Schedulable}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *         caller is a schedulable, and {@code time} is {@code null}
   *         or no non-null {@code logic} value was supplied to the
   *         memory area's constructor.
   *
   * @throws MemoryAccessError when calling schedulable may not use the
   *         heap and this memory area's {@code logic} value is allocated in
   *         heap memory.
   *
   * @throws javax.realtime.StaticUnsupportedOperationException when the
   *         wait operation is not supported using the clock associated
   *         with {@code time}.
   */
  public void joinAndEnterPinned(HighResolutionTime<?> limit)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException,
           MemoryAccessError
  {
  }

  /**
   * Same as {@link ScopedMemory#joinAndEnter(Runnable, HighResolutionTime)}
   * except that pinning is ignored so the memory may not have been cleared.
   *
   * @param logic The logic to execute upon entry.
   *
   * @param limit The maximum time to wait.
   *
   * @throws java.lang.InterruptedException when this schedulable is
   *         interrupted by {@link javax.realtime.RealtimeThread#interrupt()} or
   *         {@link javax.realtime.control.AsynchronouslyInterruptedException#fire()}
   *         while waiting for the reference count to go to zero.
   *
   * @throws ScopedCycleException when the caller is a schedulable and
   *         this invocation would break the single parent rule.
   *
   * @throws IllegalTaskStateException when the caller is a Java thread,
   *         or when this method is invoked during finalization of
   *         objects in scoped memory and entering this scoped memory
   *         area would force deletion of the instance of {@code Schedulable}
   *         that triggered finalization.  This would include the scope
   *         containing the instance of {@code Schedulable}, and the
   *         scope (if any) containing the scope containing the instance
   *         of {@code Schedulable}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when the
   *         caller is a schedulable, and {@code time} is {@code null}
   *         or no non-null {@code logic} value was supplied to the
   *         memory area's constructor.
   *
   * @throws MemoryAccessError when calling schedulable may not use the
   *         heap and this memory area's {@code logic} value is allocated in
   *         heap memory.
   *
   * @throws javax.realtime.StaticUnsupportedOperationException when the
   *         wait operation is not supported using the clock associated
   *         with {@code time}.
   */
  public void joinAndEnterPinned(Runnable logic, HighResolutionTime<?> limit)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException,
           MemoryAccessError
  {
  }


  /**
   * Same as {@link #joinAndEnterPinned(Runnable, HighResolutionTime)} except
   * that the executed method is called {@code get} and an object
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public <P> P joinAndEnterPinned(Supplier<P> logic,
                                  HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return null;
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable, HighResolutionTime)} except
   * that the executed method is called {@code get} and a {@code boolean}
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public boolean joinAndEnterPinned(BooleanSupplier logic,
                                    HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return false;
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable, HighResolutionTime)} except
   * that the executed method is called {@code get} and an {@code int}
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public int joinAndEnterPinned(IntSupplier logic,
                                HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return 0;
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable, HighResolutionTime)} except
   * that the executed method is called {@code get} and a {@code long}
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public long joinAndEnterPinned(LongSupplier logic,
                                 HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return 0L;
  }

  /**
   * Same as {@link #joinAndEnterPinned(Runnable, HighResolutionTime)} except
   * that the executed method is called {@code get} and a {@code double}
   * is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   */
  public double joinAndEnterPinned(DoubleSupplier logic,
                                   HighResolutionTime<?> time)
    throws InterruptedException,
           IllegalTaskStateException,
           StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           ScopedCycleException, MemoryAccessError
  {
    return 0.0;
  }
}
