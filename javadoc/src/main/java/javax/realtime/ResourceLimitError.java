/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This error is thrown when an attempt is made to exceed a system resource limit,
 * such as the maximum number of locks.
 *
 * @since RTSJ 2.0 extends StaticError
 */
public class ResourceLimitError
  extends StaticError implements StaticThrowable<ResourceLimitError>
{
  private static final long serialVersionUID = 5679509359767311167L;

  private static final ResourceLimitError _singleton_ =
    new ResourceLimitError();

  /**
   * Obtains the singleton of this static  {@code throwable}.
   * It should be initialized before throwing.
   *
   * @return the single instance of this  {@code throwable}.
   *
   * @since RTSJ 2.0
   */
  public static ResourceLimitError get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code ResourceLimitError}, but application code should
   * use {@link #get()} instead.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public ResourceLimitError() {}

  /**
   * A descriptive constructor for {@code ResourceLimitError}.
   *
   * @param description The reason for throwing this error.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public ResourceLimitError(String description) {}

  @Override
  public ResourceLimitError getSingleton() { return _singleton_; }
}
