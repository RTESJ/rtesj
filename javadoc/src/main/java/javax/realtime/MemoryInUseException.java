/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * There has been attempt to allocate a range of physical or
 * virtual memory that is already in use.
 *
 * @since RTSJ 2.0 extends StaticRuntimeException
 */
public class MemoryInUseException
  extends StaticRuntimeException
  implements StaticThrowable<MemoryInUseException>
{
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  private static final MemoryInUseException _singleton_ =
    new MemoryInUseException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static MemoryInUseException get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code MemoryInUseException}, but application code
   * should use {@link #get()} instead.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public MemoryInUseException()
  {
    super();
  }

  /**
   * A descriptive constructor for {@code MemoryInUseException}.
   *
   * @param description
   *          Description of the error.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public MemoryInUseException(String description)
  {
    super();
    init(description);
  }

  @Override
  public MemoryInUseException getSingleton() { return _singleton_; }
}
