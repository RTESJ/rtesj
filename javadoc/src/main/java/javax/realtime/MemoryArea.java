/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * {@code MemoryArea} is the abstract base class of all classes
 * dealing with the representations of allocatable memory areas,
 * including the immortal memory area, physical memory and scoped memory
 * areas.
 * This is an abstract class, but no method in this class is abstract.
 * An application should not subclass {@code MemoryArea} without
 * complete knowledge of its implementation details.
 */
public abstract class MemoryArea
{
  /**
   * Creates an instance of {@code MemoryArea}.
   *
   * @param size
   *          The size of {@code MemoryArea} to allocate,
   *          in bytes.
   * @param logic
   *          A runnable, whose  {@code run()} method will be
   *          called whenever {@link #enter()} is called.
   *          When {@code logic} is {@code null}, this constructor is equivalent
   *          to {@code MemoryArea(long size)}.
   * @throws StaticIllegalArgumentException
   *           when the {@code size} parameter is
   *           less than zero.
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *           {@code MemoryArea} object or for its allocation
   *           area in its backing store.
   * @throws IllegalAssignmentError
   *           when storing {@code logic} in {@code this} would
   *           violate the assignment rules.
   */
  protected MemoryArea(long size, Runnable logic)
    throws StaticIllegalArgumentException, StaticOutOfMemoryError,
           IllegalAssignmentError
  {
  }

  /**
   * Equivalent to {@link #MemoryArea(long, Runnable)} with the argument
   * list {@code (size.getEstimate(), logic)}.
   *
   * @param size
   *          A {@code SizeEstimator} object which indicates the
   *          amount of memory required by this {@code MemoryArea}.
   * @param logic
   *          A runnable, whose  {@code run()} method will be
   *          called whenever {@link #enter()} is called.
   *          When {@code logic} is {@code null}, this constructor is equivalent
   *          to {@code MemoryArea(SizeEstimator size)}.
   * @throws StaticIllegalArgumentException
   *           when {@code size} is {@code null} or
   *           {@code size.getEstimate()} is negative.
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *           {@code MemoryArea} object or for its allocation area in
   *           its backing store.
   * @throws IllegalAssignmentError
   *           when storing {@code logic} in {@code this} would
   *           violate the assignment rules.
   */
  protected MemoryArea(SizeEstimator size, Runnable logic)
    throws StaticIllegalArgumentException, StaticOutOfMemoryError,
           IllegalAssignmentError
  {
    this(size.getEstimate(), logic);
  }

  /**
   * Equivalent to {@link #MemoryArea(long, Runnable)} with the argument
   * list {@code (size, null)}.
   *
   * @param size
   *          The size of {@code MemoryArea} to allocate,
   *          in bytes.
   * @throws StaticIllegalArgumentException
   *           when {@code size} is less
   *           than zero.
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *      {@code MemoryArea} object or for its allocation area in its
   *      backing store.
   */
  protected MemoryArea(long size)
    throws StaticIllegalArgumentException, StaticOutOfMemoryError
  {
    this(size, null);
  }

  /**
   * Equivalent to {@link #MemoryArea(long, Runnable)} with the argument
   * list {@code (size.getEstimate(), null)}.
   *
   * @param size
   *          A {@link SizeEstimator} object which indicates the
   *          amount of memory required by this {@code MemoryArea}.
   * @throws StaticIllegalArgumentException
   *           when the {@code size} parameter is
   *           {@code null}, or {@code size.getEstimate()} is negative.
   * @throws StaticOutOfMemoryError when there is insufficient memory for the
   *           {@code MemoryArea} object or for its allocation area in
   *           its backing store.
   */
  protected MemoryArea(SizeEstimator size)
    throws StaticIllegalArgumentException, StaticOutOfMemoryError
  {
    this(size.getEstimate(), null);
  }

  /**
   * Associates this memory area with the current
   * schedulable for the duration of the execution of the
   * {@code run()} method of the instance of {@code Runnable} given in
   * the constructor.
   * During this period of execution, this memory area
   * becomes the default allocation context until another
   * default allocation context is selected (using {@code enter},
   * or {@link #executeInArea}) or
   * the {@code enter} method exits.
   *
   * @throws IllegalTaskStateException when the caller context is not an
   *           instance of {@link Schedulable}.
   * @throws StaticIllegalArgumentException
   *           when the caller is a schedulable and a {@code null} value
   *           for {@code logic} was supplied when the memory area was
   *           constructed.
   * @throws ThrowBoundaryError
   *           Thrown when the JVM needs to propagate an exception allocated
   *           in {@code this} scope to (or through) the memory area of the
   *           caller.  Storing a reference to that exception would cause
   *           an {@link IllegalAssignmentError}, so the JVM cannot be
   *           permitted to deliver the exception.
   *           The {@link ThrowBoundaryError} instance is preallocated by the
   *           VM to avoid cascading creation of {@link ThrowBoundaryError}.
   * @throws MemoryAccessError
   *           when caller is a schedulable that may not use the heap and
   *           this memory area's logic value is allocated in heap memory.
   */
  public void enter()
    throws IllegalTaskStateException, StaticIllegalArgumentException,
           ThrowBoundaryError, MemoryAccessError
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
   * @param logic
   *          The Runnable object whose {@code run()} method should be invoked.
   * @throws IllegalTaskStateException
   *         when the caller context is not an instance of {@link Schedulable}.
   * @throws StaticIllegalArgumentException
   *           when the caller is a schedulable and {@code logic} is
   *           {@code null}.
   * @throws ThrowBoundaryError
   *           Thrown when the JVM needs to propagate an exception allocated in
   *           {@code this} scope to (or through) the memory area of the
   *           caller.  Storing a reference to that exception would cause
   *           an {@link IllegalAssignmentError}, so the JVM cannot be
   *           permitted to deliver the exception. The
   *           {@link ThrowBoundaryError} instance is preallocated by the
   *           VM to avoid cascading creation of {@code ThrowBoundaryError}.
   */
  public void enter(Runnable logic)
  {
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and an object is returned.  The {@code Supplier.get()} method
   * must ensure that the returned object is allocated outside the area, when
   * the area is not a {@link PerennialMemory}.
   *
   * @param <T> The type of the object returned.
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   * @throws IllegalAssignmentError when the return value allocated in area
   *         and area is not a {@link PerennialMemory}.
   * @since RTSJ 2.0
   */
  public <T> T enter(Supplier<T> logic)
  {
    return null;
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and a {@code boolean} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   * @since RTSJ 2.0
   */
  public boolean enter(BooleanSupplier logic)
  {
    return false;
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and an {@code int} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   * @since RTSJ 2.0
   */
  public int enter(IntSupplier logic)
  {
    return 0;
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and a {@code long} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   * @since RTSJ 2.0
   */
  public long enter(LongSupplier logic)
  {
    return 0L;
  }

  /**
   * Same as {@link #enter(Runnable)} except that the executed method is called
   * {@code get} and a {@code double} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   * @since RTSJ 2.0
   */
  public double enter(DoubleSupplier logic)
  {
    return 0.0;
  }

  /**
   * Gets the {@code MemoryArea} in which the given
   * object is located.
   *
   * @return the instance of {@code MemoryArea} from which
   *         {@code object} was allocated.
   * @throws StaticIllegalArgumentException
   *           when the value of {@code object} is {@code null}.
   */
  public static MemoryArea getMemoryArea(Object object)
  {
    return null; // getMemoryAreaForObject(object);
  }

  /**
   * For memory areas where memory is freed under program control this
   * returns an exact count, in bytes, of the memory currently
   * used by the system for the allocated objects.
   * For memory areas (such as heap) where the definition of "used" is
   * imprecise, this returns the best value it can generate in constant
   * time.
   *
   * @return the amount of memory consumed in bytes.
   */
  public long memoryConsumed()
  {
    return 0;
  }

  /**
   * An approximation of the total amount of memory currently
   * available for future allocated objects, measured in bytes.
   *
   * @return the amount of remaining memory in bytes.
   */
  public long memoryRemaining()
  {
    return 0;
  }

  /**
   * Allocates an array of the given type in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param type
   *          The class of the elements of the new array. To create an array
   *          of a primitive type use a {@code type} such as
   *          {@code Integer.TYPE} (which
   *          would call for an array of the primitive int type.)
   *
   * @param number The number of elements in the new array.
   *
   * @return a new array of class type, of number elements.
   *
   * @throws StaticIllegalArgumentException
   *           when {@code number} is less than zero,
   *           {@code type} is {@code null},
   *           or {@code type} is {@code java.lang.Void.TYPE}.
   *
   * @throws StaticOutOfMemoryError when space in the memory area is exhausted.
   * @throws StaticSecurityException when the caller does not have
   *           permission to create a new instance.
   */
  public Object newArray(Class<?> type, int number)
    throws StaticIllegalArgumentException,
           StaticOutOfMemoryError,
           StaticSecurityException
  {
    if ((type == null) || (type == Void.TYPE))
      {
        throw StaticIllegalArgumentException.get().
          init("Invalid type " + type);
      }
    else if (number < 0)
      {
        throw StaticIllegalArgumentException.get().
          init("Invalid number " + number);
      }
    else
      {
        return enter(() -> { return Array.newInstance(type, number); });
      }
  }

  /**
   * A helper method to create an array of type {@code type} in the memory
   * area containing {@code object}.
   *
   * @param object is the reference for determining the area in which to
   * allocate the array.
   *
   * @param type is the type of the array element for the returned
   * array.
   *
   * @param size is the size of the array to return.
   *
   * @return a new array of element {@code type} with {@code size} elements.
   *
   * @since RTSJ 2.0
   */
  public static Object newArrayInArea(Object object, Class<?> type, int size)
  {
    return getMemoryArea(object).newArray(type, size);
  }

  /**
   * Allocates an object in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param <T> The type of the created object
   *
   * @param type The class of which to create a new instance.
   *
   * @return a new instance of class {@code type}.
   *
   * @throws IllegalAccessException The class or initializer is inaccessible.
   *
   * @throws StaticIllegalArgumentException when {@code type} is {@code null}.
   *
   * @throws InstantiationException when the specified class object could not
   *         be instantiated. Possible causes are it is an interface, it is
   *         abstract, or it is an array.
   *
   * @throws ConstructorCheckedException a checked exception was thrown by
   *         the constructor.
   *
   * @throws StaticOutOfMemoryError when space in the memory area is exhausted.
   *
   * @throws ExceptionInInitializerError when an unexpected exception has
   *         occurred in a static initializer.
   *
   * @throws StaticSecurityException when the caller does not have
   *         permission to create a new instance.
   */
  public <T> T newInstance(Class<T> type)
    throws IllegalAccessException, StaticIllegalArgumentException,
           InstantiationException, StaticOutOfMemoryError,
           ExceptionInInitializerError, StaticSecurityException
  {
    try
      {
        return enter(() ->
        {
          try
            {
              return type.newInstance();
            }
          catch (RuntimeException e) // Pass through runtime exceptions
            {
              throw e;
            }
          catch (Exception e) // Wrap checked exception in runtime exception
            {
              throw new RuntimeException(e);
            }
        });
      }
    catch (RuntimeException e)
      {
        Throwable cause = e.getCause();
        if ((cause == null) || (e.getClass() != RuntimeException.class))
          {
            throw e;
          }
        else if (cause instanceof IllegalAccessException)
          {
            throw (IllegalAccessException)cause;
          }
        else if (cause instanceof InstantiationException)
          {
            throw (InstantiationException)cause;
          }
        else
          {
            throw ConstructorCheckedException.get().init(cause);
          }
      }
  }

  /**
   * Allocates an object in this memory area.
   * This method may be concurrently used by multiple threads.
   *
   * @param <T> The type of the created object
   *
   * @param c
   *          The constructor for the new instance.
   * @param args
   *          An array of arguments to pass to the constructor.
   * @return a new instance of the object constructed by {@code c}.
   * @throws ExceptionInInitializerError
   *           when an unexpected exception has occurred in a static
   *           initializer
   * @throws IllegalAccessException
   *           when the class or initializer is inaccessible under Java
   *           access control.
   * @throws StaticIllegalArgumentException
   *           when {@code c} is
   *           {@code null}, or the {@code args} array does
   *           not contain the number of arguments required by
   *           {@code c}. A {@code null} value of
   *           {@code args} is treated like an array of length 0.
   * @throws InstantiationException
   *           when the specified class object could not be
   *           instantiated. Possible causes are it is an interface, it is
   *           abstract,
   *           it is an array.
   * @throws InvocationTargetException
   *           when the underlying constructor throws an exception.
   * @throws StaticOutOfMemoryError
   *           when space in the memory area is exhausted.
   * @throws StaticSecurityException when the caller does not have
   *           permission to create a new instance.
   */
  public <T> T newInstance(Constructor<T> c, Object[] args)
    throws ExceptionInInitializerError, IllegalAccessException,
           StaticIllegalArgumentException, InstantiationException,
           InvocationTargetException, StaticOutOfMemoryError,
           StaticSecurityException
  {
    return null; // newInstance1(c, args);
  }

  /**
   * Queries the size of the memory area. The returned value is the
   * current size.  Current size may be larger than initial size for
   * those areas that are allowed to grow.
   *
   * @return the size of the memory area in bytes.
   */
  public long size()
  {
    return 0;
  }

  /**
   * Executes the {@code run()} method from the {@code logic} parameter using
   * this memory area as the current allocation context.
   * The effect of {@code executeInArea} on the scope stack
   * is specified in the subclasses of {@code MemoryArea}.
   *
   * @param logic
   *          The runnable object whose {@code run()} method should
   *          be executed.
   * @throws StaticIllegalArgumentException
   *           when {@code logic} is {@code null}.
   */
  public void executeInArea(Runnable logic)
    throws StaticIllegalArgumentException
  {
    // executeInArea0(logic);
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed method
   * is called {@code get} and an object is returned.  For a memory are that
   * is not a {@link PerennialMemory}, care must be taken that the returned
   * value is assignable to an object allocated in the current area.
   *
   * @param <T> the type of the returned object.
   *
   * @param logic The object whose get method will be executed.
   *
   * @return a result from the computation.
   *
   * @throws IllegalAssignmentError when the return value is not assignable to
   *         an object allocated in the current area.
   *
   * @since RTSJ 2.0
   */
  public <T> T executeInArea(Supplier<T> logic)
  {
    return null;
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed method
   * is called {@code get} and a {@code boolean} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public boolean executeInArea(BooleanSupplier logic)
  {
    return false;
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed method
   * is called {@code get} and an {@code int} is returned.
   *
   * @param logic the object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public int executeInArea(IntSupplier logic)
  {
    return 0;
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed method
   * is called {@code get} and a {@code long} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public long executeInArea(LongSupplier logic)
  {
    return 0L;
  }

  /**
   * Same as {@link #executeInArea(Runnable)} except that the executed method
   * is called {@code get} and a {@code double} is returned.
   *
   * @param logic The object whose get method will be executed.
   * @return a result from the computation.
   *
   * @since RTSJ 2.0
   */
  public double executeInArea(DoubleSupplier logic)
  {
    return 0.0;
  }

  /**
   * Determines whether an object {@code A} allocated in the memory area
   * represented by {@code this} can hold a reference to an object
   * {@code B} allocated in the current memory area.
   *
   * @return {@code true} when {@code B} can be assigned to a field
   *         of {@code A}, otherwise {@code false}.
   *
   * @since RTSJ 2.0
   */
  public boolean mayHoldReferenceTo()
  {
    return false;
  }


  /**
   * Determines whether an object {@code A} allocated in the memory area
   * represented by {@code this} can hold a reference to the object
   * {@code value}.
   *
   * @param value The object to test.
   *
   * @return {@code true} when {@code value} can be assigned
   * to a field of {@code A}, otherwise {@code false}.
   *
   * @since RTSJ 2.0
   */
  public boolean mayHoldReferenceTo(Object value)
  {
    return false;
  }

  /**
   * An annotation for hidding methods from security checks.  Used by
   * {@link #newInstance} and {@link #newArrayInArea} so that the
   * access checks work correctly.
   */
  @Retention(RUNTIME)
  @Target(METHOD)
  @interface Hidden {}
}
