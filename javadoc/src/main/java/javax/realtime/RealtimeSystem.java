/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;


/**
 * {@code RealtimeSystem} provides a means for tuning the behavior
 * of the implementation by specifying parameters such as the maximum
 * number of locks that can be in use concurrently, and the monitor
 * control policy.  In addition, {@code RealtimeSystem} provides a
 * mechanism for obtaining access to the security manager, garbage
 * collector, and scheduler, to query or set parameters.
 */
public final class RealtimeSystem
{
  /**
   * Private no argument constructor to keep javadoc from creating a public one.
   *
   */
  private RealtimeSystem() {}

  /**
   * Value indicating that the highest order byte of a bit word is stored
   * at the lowest byte address: the int 0x0A0B0C0D is stored in the byte
   * sequence 0x0A, 0x0B, 0x0C, 0x0D. and the long 0x0102030405060708 is stored
   * in the sequence 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08.
   *
   * @deprecated RTSJ 2.0
   */
  @Deprecated
  public static final byte BIG_ENDIAN = ByteOrder.BIG_ENDIAN.value();

  /**
   * Value indicating that the lowest order byte of a word is stored
   * at the lowest byte address: the int 0x0A0B0C0D is stored in the byte
   * sequence 0x0D, 0x0C, 0x0B, 0x0A and the long 0x0102030405060708 is stored
   * in the sequence 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01.
   *
   * @deprecated RTSJ 2.0
   */
  @Deprecated
  public static final byte LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN.value();

  /**
   * The byte ordering of the underlying hardware.
   *
   * @deprecated RTSJ 2.0
   */
  @Deprecated
  public static final byte BYTE_ORDER = ByteOrder.systemByteOrder().value();

  /**
   * Returns a reference to the currently active garbage collector
   * for the heap.
   *
   * @return a {@link GarbageCollector} object which is the current
   *         collector collecting objects on the conventional Java heap.
  */
  public static GarbageCollector currentGC()
  {
    return null;
  }

  /**
   * Gets the maximum number of locks that have been used concurrently.
   * This value can be used for tuning the concurrent locks parameter,
   * which is used as a hint by systems that use a monitor cache.
   *
   * @return an integer, whose value is the maximum number of locks that
   *          have been used concurrently.  When the number of concurrent
   *          locks is not tracked by the implementation, returns -1.
   *          Note that when the number of concurrent locks is not
   *          tracked, the number of available concurrent locks is
   *          effectively unlimited.
   */
  public static int getConcurrentLocksUsed()
  {
    return -1;
  }

  /**
   * Gets the maximum number of locks that can be used concurrently
   * without incurring an execution time increase as set by the
   * {@code setMaximumConcurrentLocks()} methods.
   *
   * <p> Note that any relationship between this method and
   * {@code setMaximumConcurrentLocks} is implementation-specific.
   * This method returns the actual maximum number of concurrent locks
   * the platform can currently support, or
   * {@code Integer.MAX_VALUE} when there is no maximum.  The
   * {@code setMaximumConcurrentLocks} method gives the
   * implementation a hint as to the maximum number of concurrent locks
   * it should expect.
   *
   * @return an integer, whose value is the maximum number of locks that
   *         can be in simultaneous use.
   */
  public static int getMaximumConcurrentLocks()
  {
    return 0;
  }

  /**
   * Gets a reference to the security manager used to control
   * access to realtime system features such as access to
   * physical memory.
   *
   * @return a {@link RealtimeSecurity} object representing the default
   * realtime security manager.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static RealtimeSecurity getSecurityManager()
  {
    return new RealtimeSecurity();
  }

  /**
   * Sets the anticipated maximum number of locks that may be held
   * or waited on concurrently.  Provides a hint to systems that
   * use a monitor cache as to how much space to dedicate to the cache.
   *
   * @param numLocks An integer, whose value becomes the number of locks
   *        that can be in simultaneous use without incurring an
   *        execution time increase.  When {@code number} is less
   *        than or equal to zero nothing happens. When the system does
   *        not use this hint this method has no effect other than on
   *        the value returned by {@link #getMaximumConcurrentLocks()}.
   */
  public static void setMaximumConcurrentLocks(int numLocks) {}

  /**
   * Sets the anticipated maximum number of locks that may be held
   * or waited on concurrently.  Provides a limit for the size of
   * the monitor cache on systems that provide one when hard is true.
   *
   * @param number The maximum number of locks that can be in
   *        simultaneous use without incurring an execution time
   *        increase.  When {@code number} is less than or equal to
   *        zero nothing happens.  When the system does not use this hint
   *        this method has no effect other than on the value returned
   *        by {@link #getMaximumConcurrentLocks()}.
   *
   * @param hard When true, {@code number} sets a limit.  When a lock
   *        is attempted which would cause the number of locks to exceed
   *        {@code number} then a {@link ResourceLimitError} is
   *        thrown.  When the system does not limit use of concurrent
   *        locks, this parameter is silently ignored.
   */
  public static void setMaximumConcurrentLocks(int number, boolean hard) {}

  /**
   * Sets a new realtime security manager.
   *
   * @param manager A {@link RealtimeSecurity} object which will become
   * the new security manager.
   *
   * @throws StaticSecurityException when security manager has already
   *         been set.
   */
  public static void setSecurityManager(RealtimeSecurity manager)
  {
  }

  /**  Returns the monitor control object that represents the initial
   * monitor control policy.
   * @return the initial monitor control policy.
   * @since RTSJ 1.0.1
   */
  public static MonitorControl getInitialMonitorControl()
  {
    return null;
  }

  /**
   * Determines whether or not a particular module is supported.
   *
   * @param module The identifier of the module to be checked for support.
   *
   * @return {@code true} when {@code module} is supported; otherwise
   *         {@code false}.
   *
   * @since RTSJ 2.0
   */
  public static boolean supports(RTSJModule module) { return false; }

  /**
   * The set of modules supported.
   *
   * @return an integer representing all the modules supported.
   *
   * @since RTSJ 2.0
   */
  public static int modules() { return RTSJModule.CORE.value(); }

  /**
   * Determines whether or not this system supports a universal time clock.
   *
   * @return {@code true} when the system can provide a universal time clock.
   */
  public static boolean hasUniversalClock()
  {
    return false;
  }

  /**
   * Determines whether or not hard cost enforcement is supported.
   *
   * @return {@code true} when cost enforcement is supported;
   *         otherwise {@code false}.
   *
   * @since RTSJ 2.0
   */
  public static boolean canEnforceCost() { return false; }


  /**
   * Determines whether or not allocation rate enforcement is supported.
   *
   * @return {@code true} when allocation rate enforcement is supported,
   *         otherwise {@code false}.
   *
   * @since RTSJ 2.0
   */
  public static boolean canEnforceAllocationRate() { return false; }
}
