/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Abstract superclass for all monitor control policies.
 */
public abstract class MonitorControl
{
  /**
   * Invoked from subclass constructors.
   */
  protected MonitorControl() {}

  /**
   * Gets the monitor control policy of the given instance of
   * {@code Object}.
   *
   * @param monitor The object being queried.
   *
   * @return the monitor control policy of the {@code monitor} parameter.
   *
   * @throws StaticIllegalArgumentException when {@code monitor} is
   * {@code null}.
   */
  public static MonitorControl getMonitorControl(Object monitor)
  {
    return null;
  }

  /**
   * Gets the current default monitor control policy.
   *
   * @return the default monitor control policy object.
   */
  public static MonitorControl getMonitorControl()
  {
    return null;
  }

  /**
   * Sets the <em>default monitor control policy</em>.
   * This policy does not affect the monitor control policy of any already
   * created object, it will, however, govern any object whose creation happens
   * after the method completes, until either
   * <ol>
   * <li> a new ``per-object'' policy is set for that object, thereby
   * altering the monitor control policy for a single object without changing
   * the default policy, or</li>
   * <li> a new default policy is set.</li>
   * </ol>
   * Like the per-object method
   * (see {@link #setMonitorControl(Object, MonitorControl)},
   * the setting of the default monitor control policy occurs immediately, but
   * may not be visible on all processors of a multicore system simultaneously.
   *
   * @param policy The new monitor control policy.  When {@code null},
   *        the default {@code MonitorControl} policy is not changed.
   *
   * @return the default {@code MonitorControl} policy in effect on completion.
   *
   * @throws StaticSecurityException when the caller is not permitted to alter
   *         the default monitor control policy.
   *
   * @throws StaticIllegalArgumentException when {@code policy} is
   *         not in immortal memory.
   *
   * @throws StaticUnsupportedOperationException when {@code policy} is not
   *            a supported monitor control policy.
   *
   * @since RTSJ 1.0.1 The return type is changed from void to
   * {@code MonitorControl}.
   */
  public static MonitorControl setMonitorControl(MonitorControl policy)
    throws StaticIllegalArgumentException,
           StaticUnsupportedOperationException,
           StaticIllegalStateException
  {
    return  null;
  }

  /**
   * Immediately sets {@code policy} as the monitor control policy for
   * {@code obj}.
   *
   * <p>Monitor control policy changes on a monitor that is actively
   * contended may lead to queued or enqueuing tasks following
   * either the old or new policy in an unpredictable fashion.  Tasks
   * enqueued after the monitor is released after a policy change will
   * follow the new policy.
   *
   * <p> A thread or schedulable that is queued for the lock
   * associated with {@code obj}, or is in {@code obj}'s wait
   * set, is not rechecked (e.g., for a
   * {@code CeilingViolationException}) under {@code policy},
   * either as part of the execution of {@code setMonitorControl}
   * or when it is awakened to (re)acquire the lock.
   *
   * <p> The thread or schedulable invoking
   * {@code setMonitorControl} must already hold the lock on
   * {@code obj}.
   *
   * @param obj The object that will be governed by the new policy.
   *
   * @param policy The new policy for the object. When {@code null}
   *        nothing will happen.
   *
   * @return the current {@code MonitorControl} policy for
   *         {@code obj}, which will be replaced.
   *
   * @throws StaticIllegalArgumentException when {@code obj} is
   *         {@code null} or {@code policy} is not in immortal
   *         memory.
   *
   * @throws StaticUnsupportedOperationException when {@code policy} is not
   *         a supported monitor control policy.
   *
   * @throws IllegalMonitorStateException when the caller does not hold a lock
   *         on {@code obj}.
   *
   * @since RTSJ 1.0.1 The return type has been changed from void to
   * {@code MonitorControl}.
   */
  public static MonitorControl setMonitorControl(Object obj,
                                                 MonitorControl policy)
  {
    return null;
  }
}
