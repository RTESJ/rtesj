/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * When construction of any of the wait-free queues is attempted with
 * the ends of the queue in incompatible memory areas. Also thrown by
 * wait-free queue methods when such an incompatibility is detected
 * after the queue is constructed.
 *
 * @since RTSJ 2.0 extends StaticRuntimeException
 */
public class MemoryScopeException
  extends StaticRuntimeException
  implements StaticThrowable<MemoryScopeException>
{
  /**
   *
   */
  private static final long serialVersionUID = -3802008159818447015L;

  private static final MemoryScopeException _singleton_ =
    new MemoryScopeException();
  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static MemoryScopeException get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code MemoryScopeException}, but application code
   * should use {@link #get()} instead.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   * instead.
   */
  @Deprecated
  public MemoryScopeException()
  {
    super();
  }

  /**
   * A descriptive constructor for {@code MemoryScopeException}.
   *
   * @param description The reason for throwing this exception.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public MemoryScopeException(String description)
  {
    super();
    init(description);
  }

  @Override
  public MemoryScopeException getSingleton() { return _singleton_; }
}
