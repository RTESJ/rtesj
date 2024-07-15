/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This error is thrown on an attempt to refer to an object in
 * an inaccessible {@link MemoryArea}.  For example, when logic in a
 * {@link RealtimeThread} or {@link AsyncBaseEventHandler} configured with
 * a {@link javax.realtime.memory.ScopedConfigurationParameters}
 * object, attempts to refer to an object in a {@link HeapMemory} area.
 *
 * @since RTSJ 2.0 extends StaticError
 */
public class MemoryAccessError
  extends StaticError implements StaticThrowable<MemoryAccessError>
{
  /**
   *
   */
  private static final long serialVersionUID = 5877868839696757284L;

  private static final MemoryAccessError _singleton_ =
    new MemoryAccessError();

  /**
   * Obtains the singleton of this static {@code Throwable}.
   * It should be initialized before throwing.
   *
   * @return the single instance of this {@code Throwable}.
   *
   * @since RTSJ 2.0
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   * instead.
   */
  @MemoryArea.Hidden
  public static MemoryAccessError get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code MemoryAccessError}, but application code should
   * use {@link #get()} instead.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   * instead.
   */
  @Deprecated
  public MemoryAccessError() { super(); }

  /**
   * A descriptive constructor for {@code MemoryAccessError}.
   *
   * @param description The reason for throwing this error.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   * instead.
   */
  @Deprecated
  public MemoryAccessError(String description)
  {
    super();
    init(description);
  }

  @Override
  public MemoryAccessError getSingleton() { return _singleton_; }
}
