/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This exception is thrown when the {@link PhysicalMemoryManager} is given
 * conflicting specifications for memory.
 * The conflict can be between types in an array of memory type specifiers,
 * or between the specifiers and a specified base address.
 *
 * @since RTSJ 1.0.1 Changed to an unchecked exception.
 *
 * @since RTSJ 2.0 extends StaticRuntimeException
 */
public class MemoryTypeConflictException
  extends StaticRuntimeException
  implements StaticThrowable<MemoryTypeConflictException>
{
  /**
   *
   */
  private static final long serialVersionUID = -6392040767727278331L;

  private static final MemoryTypeConflictException _singleton_ =
    new MemoryTypeConflictException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static MemoryTypeConflictException get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code MemoryTypeConflictException}, but application
   * code should use {@link #get()} instead.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public MemoryTypeConflictException()
  {
    super();
  }

  /**
   * A descriptive constructor for {@code MemoryTypeConflictException}.
   *
   * @param description A description of the exception.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public MemoryTypeConflictException(String description)
  {
    super();
    init(description);
  }

  @Override
  public MemoryTypeConflictException getSingleton() { return _singleton_; }
}
