/*-----------------------------------------------------------------------* \
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;


/**
 * The error thrown by {@link MemoryArea#enter(Runnable logic)}
 * when a {@code Throwable} allocated from memory that
 * is not usable in the surrounding scope tries to propagate out of the
 * scope of the {@code enter}.
 *
 * @since RTSJ 2.0 extends StaticError
 */
public class ThrowBoundaryError
  extends StaticError implements StaticThrowable<ThrowBoundaryError>
{
  /**
   *
   */
  private static final long serialVersionUID = 993529698943269849L;

  private static final ThrowBoundaryError _singleton_ =
    new ThrowBoundaryError();

  /**
   * Gets the preallocated instance of this exception.
   * It should be initialized before throwing.
   *
   * @return the preallocated instance of this exception.
   */
  public static ThrowBoundaryError get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code ThrowBoundaryError}, but application code should
   * use {@link #get()} instead.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   * instead.
   */
  @Deprecated
  public ThrowBoundaryError() {}

  /**
   * A descriptive constructor for {@code ThrowBoundaryError}.
   *
   * @param description The reason for throwing this error.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public ThrowBoundaryError(String description) {}

  @Override
  public ThrowBoundaryError getSingleton() { return _singleton_; }
}
