/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * The {@code HeapMemory} class is a singleton object that allows
 * logic with a non-heap allocation context to allocate objects in the Java
 * heap.
 */
public final class HeapMemory extends PerennialMemory
{
  private static HeapMemory heap = null;

  private HeapMemory()
  {
    super(4 * 1024 * 1024);
  }

  /**
   * Associates this memory area with the current
   * schedulable for the duration of the execution of the
   * {@code run()} method of the instance of {@code Runnable} given in
   * the constructor.
   * During this period of execution, this memory area
   * becomes the default allocation context until another
   * default allocation context is selected (using {@code enter},
   * or {@link #executeInArea}) or the {@code enter} method exits.
   *
   * @throws IllegalTaskStateException when the caller context in
   *         not an instance of {@link Schedulable}.
   * @throws StaticIllegalArgumentException {@inheritDoc}
   * @throws MemoryAccessError when caller is a schedulable which may
   *         not use the heap.
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
   * or {@link #executeInArea}) or the {@code enter} method exits.
   *
   * @param logic The Runnable object whose {@code run()} method
   *        should be invoked.
   * @throws MemoryAccessError when caller is a schedulable which may
   *         not use the heap.
   * @throws IllegalTaskStateException {@inheritDoc}
   * @throws StaticIllegalArgumentException {@inheritDoc}
   */
  @Override
  public void enter(Runnable logic)
  {
  }

  /**
   * Returns a reference to the singleton instance of {@link HeapMemory}
   * representing the Java heap.
   * The singleton instance of this class shall be allocated in the
   * {@link ImmortalMemory} area.
   *
   * @return the singleton {@link HeapMemory} object.
   */
  public static HeapMemory instance()
  {
    return heap;
  }

  /**
   * Executes the run method from the {@code logic} parameter using heap
   * as the current allocation context.
   * For a schedulable, this saves the current scope
   * stack and replaces it with one consisting only of the
   * {@code HeapMemory} instance;
   * restoring the original scope stack upon completion.
   *
   * @param logic The runnable object whose {@code run()} method should
   *         be executed.
   * @throws StaticIllegalArgumentException when {@code logic} is {@code null}.
   * @throws MemoryAccessError when caller is a schedulable which may
   *         not use the heap.
   */
  @Override
  public void executeInArea(Runnable logic)
  {
  }

  /**
   * Allocates an array of the given type in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param type {@inheritDoc}
   * @param number {@inheritDoc}
   * @return {@inheritDoc}
   * @throws MemoryAccessError when caller is a schedulable which may
   *         not use the heap.
   * @throws StaticIllegalArgumentException {@inheritDoc}
   * @throws StaticOutOfMemoryError {@inheritDoc}
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
   * @throws MemoryAccessError when caller is a schedulable which may
   *         not use the heap.
   * @throws IllegalAccessException {@inheritDoc}
   * @throws StaticIllegalArgumentException {@inheritDoc}
   * @throws ExceptionInInitializerError {@inheritDoc}
   * @throws StaticOutOfMemoryError {@inheritDoc}
   * @throws InstantiationException {@inheritDoc}
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
   * @param c {@inheritDoc}
   * @param args {@inheritDoc}
   * @return {@inheritDoc}
   * @throws MemoryAccessError when caller is a schedulable which may
   *         not use the heap.
   * @throws IllegalAccessException {@inheritDoc}
   * @throws InstantiationException {@inheritDoc}
   * @throws StaticOutOfMemoryError {@inheritDoc}
   * @throws StaticIllegalArgumentException {@inheritDoc}
   * @throws InvocationTargetException {@inheritDoc}
   */
  @Override
  public <T> T newInstance(Constructor<T> c, Object[] args)
    throws IllegalAccessException,
      InstantiationException,
      InvocationTargetException
  {
    return null; // newInstance1(c, args);
  }
}
