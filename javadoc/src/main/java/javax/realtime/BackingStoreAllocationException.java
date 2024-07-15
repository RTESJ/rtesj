/*-----------------------------------------------------------------------*\
 * Copyright 2020--2024, aicas GmbH; all rights reserved.
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
 * @since RTSJ 2.0
 */
public class BackingStoreAllocationException
  extends StaticRuntimeException
  implements StaticThrowable<BackingStoreAllocationException>
{
  private static final BackingStoreAllocationException _singleton_ =
    new BackingStoreAllocationException();

  /**
   * Obtains the singleton of this static {@code Throwable}.
   * It is prepared for immediate throwing.
   *
   * @return the single instance of this {@code Throwable}.
   *
   * @since RTSJ 2.0
   */
  public static BackingStoreAllocationException get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code BackingStoreAllocationException}, but
   * application code should use {@link #get()} instead.
   */
  BackingStoreAllocationException() { super(); }

  @Override
  public BackingStoreAllocationException getSingleton() { return _singleton_; }
}
