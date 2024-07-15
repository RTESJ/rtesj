/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * The specified memory area is not on the current thread's scope stack.
 *
 * @since RTSJ 1.0.1 Becomes unchecked
 *
 * @since RTSJ 2.0 extends StaticRuntimeException
 */
public class InaccessibleAreaException
  extends StaticRuntimeException
  implements StaticThrowable<InaccessibleAreaException>
{
  /**
   *
   */
  private static final long serialVersionUID = 7888927248288410876L;

  private static final InaccessibleAreaException _singleton_ =
    new InaccessibleAreaException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static InaccessibleAreaException get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code InaccessibleAreaException}, but application code
   * should use {@link #get()} instead.
   */
  public InaccessibleAreaException()
  {
    super();
  }

  /**
   * A descriptive constructor for {@code InaccessibleAreaException}.
   *
   * @param description
   *          Description of the error.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public InaccessibleAreaException(String description)
  {
    super();
    init(description);
  }

  @Override
  public InaccessibleAreaException getSingleton() { return _singleton_; }
}
