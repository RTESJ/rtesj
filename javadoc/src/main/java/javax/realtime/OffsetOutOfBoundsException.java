/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * When the constructor of an {@link ImmortalPhysicalMemory},
 * {@link LTPhysicalMemory}, {@link VTPhysicalMemory}, {@link RawMemoryAccess},
 * or {@link RawMemoryFloatAccess} is given an invalid address.
 *
 * @since RTSJ 1.0.1 became unchecked
 *
 * @since RTSJ 2.0 extends StaticRuntimeException
 */
public class OffsetOutOfBoundsException
  extends StaticRuntimeException
  implements StaticThrowable<OffsetOutOfBoundsException>
{
  /**
   *
   */
  private static final long serialVersionUID = -2851633802781781569L;

  private static final OffsetOutOfBoundsException _singleton_ =
    new OffsetOutOfBoundsException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static OffsetOutOfBoundsException get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code OffsetOutOfBoundsException}, application
   * code should use {@link #get()} instead.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public OffsetOutOfBoundsException()
  {
  }

  /**
   * A descriptive constructor for {@code OffsetOutOfBoundsException}.
   *
   * @param description The reason for throwing the exception.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public OffsetOutOfBoundsException(String description)
  {
    super();
    init(description);
  }

  @Override
  public OffsetOutOfBoundsException getSingleton() { return _singleton_; }
}
