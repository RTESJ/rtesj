/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This class maintains an estimate of the amount of memory required to
 * store a set of objects.
 *
 * <p>{@code SizeEstimator} is a floor on the amount of memory
 * that should be allocated.  Many objects allocate other objects when
 * they are constructed.  {@code SizeEstimator} only estimates the
 * memory requirement of the object itself, it does not include memory
 * required for any objects allocated at construction time.  When the
 * instance itself is allocated in several parts (when for instance the
 * object and its monitor are separate), the size estimate shall
 * include the sum of the sizes of all the parts that are allocated
 * from the same memory area as the instance.  <p> Alignment
 * considerations, and possibly other order-dependent issues may cause
 * the allocator to leave a small amount of unusable space,
 * consequently the size estimate cannot be seen as more than a close
 * estimate.
 *
 * @see MemoryArea#MemoryArea(SizeEstimator)
 */
public final class SizeEstimator
{
  /**
   * Create an empty size estimator.
   */
  public SizeEstimator() {}

  /**
   * Takes into account additional {@code number} instances of
   * Class {@code c} when estimating the size of the
   * {@link MemoryArea}.
   *
   * @param c The class to take into account.
   *
   * @param number The number of instances of {@code c} to estimate.
   *
   * @throws StaticIllegalArgumentException when {@code c} is {@code null} or
   *         {@code number} is negative.
   *
   * @since RTSJ 2.0 throws {@code StaticIllegalArgumentException} also when
   *        {@code number} is less than zero.
   */
  public void reserve(Class<?> c, int number) {}

  /**
   * Takes into account additional {@code number} of the estimations from
   * instances of SizeEstimator {@code size} when estimating the size
   * of the {@link MemoryArea}.
   *
   * @param estimator The given instance of {@link SizeEstimator}.
   *
   * @param number The number of times to reserve the size denoted by
   *  {@code estimator}.
   *
   * @throws StaticIllegalArgumentException when {@code estimator} is
   * {@code null} or {@code number} is less than zero.
   *
   * @since RTSJ 2.0 throws {@code StaticIllegalArgumentException} also when
   *        {@code number} is less than zero.
   */
  public void reserve(SizeEstimator estimator, int number) {}

  /**
   * Takes into account an additional estimation from the instance of
   * SizeEstimator {@code size} when estimating the size of the
   * {@link MemoryArea}.
   *
   * @param size The given instance of {@code SizeEstimator}.
   *
   * @throws StaticIllegalArgumentException when {@code size} is {@code null}.
   */
  public void reserve(SizeEstimator size) {}

  /**
   * Takes into account an additional instance of an array of {@code length}
   * reference values when estimating the size of the {@link MemoryArea}.
   *
   * @param length The number of entries in the array.
   *
   * @throws StaticIllegalArgumentException when {@code length} is negative.
   *
   * @since RTSJ 1.0.1
   */
  public void reserveArray(int length) {}


  /**
   * Takes into account an additional instance of an array of {@code length}
   * primitive values when estimating the size of the {@link MemoryArea}.
   * <p>
   * Class values for the primitive types are available from the
   *   corresponding class types; e.g.,
   * Byte.TYPE, Integer.TYPE, and Short.TYPE.
   *
   *
   * @param length The number of entries in the array.
   *
   * @param type The class representing a primitive type.  The
   *  reservation will leave room for an array of {@code length} of
   *  the primitive type corresponding to {@code type}.
   *
   * @throws StaticIllegalArgumentException when {@code length} is negative,
   *         or {@code type} does not represent a primitive type.
   *
   *  @since RTSJ 1.0.1
   */
  public void reserveArray(int length, Class<?> type) {}


  /**
   * Determines the size of a lambda with more than two variables in its
   * closure and add it to this size estimator.
   *
   * @param first Type of first variable in closure.
   * @param second Type of second variable in closure.
   * @param others Types of additional variables in closure.
   *
   * @since RTSJ 2.0
   */
  public void reserveLambda(EnclosedType first,
                            EnclosedType second,
                            EnclosedType... others)
  {
  }

  /**
   * Determines the size of a lambda with two variables in its
   * closure and add it to this size estimator.
   *
   * @param first Type of first variable in closure.
   * @param second Type of second variable in closure.
   *
   * @since RTSJ 2.0
   */
  public void reserveLambda(EnclosedType first, EnclosedType second)
  {
    reserveLambda(first, second, (EnclosedType[])null);
  }

  /**
   * Determines the size of a lambda with one variable in its
   * closure and add it to this size estimator.
   *
   * @param first Type of first variable in closure.
   *
   * @since RTSJ 2.0
   */
  public void reserveLambda(EnclosedType first)
  {
    reserveLambda(first, null, (EnclosedType[])null);
  }

  /**
   * Determines the size of a lambda with no
   * closure and add it to this size estimator.
   *
   * @since RTSJ 2.0
   */
  public void reserveLambda()
  {
    reserveLambda(null, null, (EnclosedType[])null);
  }

  /**
   * Gets an estimate of the number of bytes needed to store
   * all the objects reserved.
   *
   * @return the estimated size in bytes.
   */
  public long getEstimate() { return 0; }

  /**
   * Restores the estimate value to zero for reuse.
   *
   * @since rtsj 2.0
   */
  public void clear() {}
}
