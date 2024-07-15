/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.posix;

import javax.realtime.ActiveEventDispatcher;
import javax.realtime.DeregistrationException;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticIllegalStateException;
import javax.realtime.RegistrationException;
import javax.realtime.RealtimeThreadGroup;
import javax.realtime.SchedulingParameters;

/**
 * Provides a means of dispatching a set of {@link RealtimeSignal} instances,
 * each when its respective signal is triggered.  An application can provide
 * its own dispatcher, providing the priority for the internal dispatching
 * thread.  This dispatching thread calls the
 * {@link javax.realtime.AsyncLongEvent#fire} method on the instance of
 * {@link RealtimeSignal} associated with the signal each time its
 * signal is triggered.
 *
 * This class requires the following permissions:
 * <table border=1 cellpadding=4 width=95%
 *        summary="POSIXPermissions required by RealtimeSignalDispatcher.">
 * <tr>
 * <th>Method</th>
 * <th>Required Action for POSIXPermission</th>
 * </tr>
 *
 * <tr>
 *   <td>{@link RealtimeSignalDispatcher#setDefaultDispatcher}</td>
 *   <td>system</td>
 * </tr>
 * </table>
 *
 * @see RealtimeSignal
 *
 * @see POSIXPermission
 *
 * @since RTSJ 2.0
 */
public final class RealtimeSignalDispatcher
  extends ActiveEventDispatcher<RealtimeSignalDispatcher, RealtimeSignal>
{
  /**
   * Sets the system default realtime signal dispatcher.
   *
   * @param dispatcher The instance to be used when the next realtime signal
   *        is started.  When {@code null}, the realtime signal dispatcher
   *        is set to the original system default.
   */
  public static void setDefaultDispatcher(RealtimeSignalDispatcher dispatcher)
  {
  }

  /**
   * Obtain the default dispatcher for realtime signals
   *
   * @return the current default dispatcher
   */
  public static synchronized RealtimeSignalDispatcher getDefaultDispatcher()
  {
    return null;
  }

  /**
   * Creates a new dispatcher, whose dispatching thread runs with the given
   * {@link javax.realtime.SchedulingParameters}.
   *
   * @param schedule Parameters for scheduling this dispatcher.
   *
   * @param group Container for this dispatcher.
   *
   * @throws StaticIllegalStateException when the intersection of affinity
   *         in {@code schedule} and the affinity of {@code group} does not
   *         correspond to a valid affinity.
   */
  public RealtimeSignalDispatcher(SchedulingParameters schedule,
                                  RealtimeThreadGroup group)
    throws StaticIllegalStateException
  {
    super(null);
  }

  /**
   * Creates a new dispatcher, whose dispatching thread runs with the given
   * {@link javax.realtime.SchedulingParameters}.
   *
   * @param schedule Parameters for scheduling this dispatcher.
   *
   * @throws StaticIllegalStateException when the intersection of affinity
   *         in {@code schedule} and the affinity of the current thread group
   *         does not correspond to a valid affinity.
   */
  public RealtimeSignalDispatcher(SchedulingParameters schedule)
    throws StaticIllegalStateException
  {
    super(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRegistered(RealtimeSignal target)
  {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void register(RealtimeSignal target)
    throws RegistrationException, StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void activate(RealtimeSignal target)
     throws StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deregister(RealtimeSignal target)
    throws DeregistrationException, StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deactivate(RealtimeSignal target)
     throws StaticIllegalStateException
  {
  }

  /**
   * Releases all reasources thereby making the dispatcher unusable.
   *
   * @throws javax.realtime.StaticIllegalStateException when called on
   *         a dispatcher that has one or more registered
   *         {@link RealtimeSignal} objects.
   */
  @Override
  public void destroy() throws StaticIllegalStateException
  {
  }
}
