/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Security policy object for realtime specific issues.  Primarily used
 * to control access to physical memory.
 *
 * <p> Security requirements are generally application-specific.  Every
 * implementation shall have a default {@code RealtimeSecurity}
 * instance, and a way to install a replacement at run-time,
 * {@link RealtimeSystem#setSecurityManager}.  The default security is
 * minimal.  All security managers should prevent access to JVM internal
 * data and  the Java heap; additional protection is
 * implementation-specific and must be documented.
 *
 * @deprecated since RTSJ 2.0
 */
@Deprecated
public class RealtimeSecurity
{
  /**
   * Create an {@code RealtimeSecurity} object.
   */
  public RealtimeSecurity() {}

  /**
   * Check whether the application is allowed to access physical memory.
   *
   * @throws StaticSecurityException The application doesn't have
   * permission to access physical memory.
   */
  public void checkAccessPhysical() throws StaticSecurityException {}

  /**
   * Checks whether the application is allowed to access physical memory
   * within the specified range.
   *
   * @param base The beginning of the address range.
   *
   * @param size The size of the address range.
   *
   * @throws StaticSecurityException The application doesn't have
   *             permission to access the memory in the given range.
   */
  public void checkAccessPhysicalRange(long base,
                                       long size)
    throws StaticSecurityException
  {
  }

  /**
   * Checks whether the application is allowed to register
   * {@link PhysicalMemoryTypeFilter} objects with the
   * {@link PhysicalMemoryManager}.
   *
   * @throws StaticSecurityException The application doesn't have
   * permission to register filter objects.
   *
   */
  public void checkSetFilter() throws StaticSecurityException
  {
  }

  /**
   * Checks whether the application is allowed to set the default
   * monitor control policy.
   * @param policy The new policy
   * @throws StaticSecurityException when the application doesn't have
   *            permission to change the default monitor control policy
   *            to {@code policy}.
   *
   * @since RTSJ 1.0.1
   */
  public void checkSetMonitorControl(MonitorControl policy)
    throws StaticSecurityException
  {
  }

  /**
   * Checks whether the application is allowed to set the daemon status
   * of an AEH.
   *
   * @throws StaticSecurityException  when the application is not
   *            permitted to alter the daemon status.
   *
   * @since RTSJ 1.0.1
   */
  public void checkAEHSetDaemon() throws StaticSecurityException
  {
  }

  /**
   * Checks whether the application is allowed to set the scheduler.
   *
   * @throws StaticSecurityException The application doesn't have
   * permission to set the scheduler.
   */
  public void checkSetScheduler() throws StaticSecurityException
  {
  }
}
