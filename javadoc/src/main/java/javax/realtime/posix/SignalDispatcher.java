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
 * Provides a means of dispatching a set of {@link Signal}s.
 * An application can provide its own dispatcher, providing the priority for
 * the internal dispatching thread.  This dispatching thread calls the
 * {@link javax.realtime.AsyncEvent#fire} method on the instance of
 * {@link Signal} associated with the signal each time its signal is
 * triggered.
 * <p>
 * This class requires the following permissions:
 * <table border=1 cellpadding=4 width=95%
 *        summary="POSIXPermissions required by SignalDispatcher.">
 * <tr>
 * <th>Method</th>
 * <th>Required Action for POSIXPermission</th>
 * </tr>
 *
 * <tr>
 *   <td>{@link SignalDispatcher#setDefaultDispatcher}</td>
 *   <td>system</td>
 * </tr>
 * </table>
 *
 * @see Signal
 *
 * @see POSIXPermission
 *
 * @since RTSJ 2.0
 */
public final class SignalDispatcher
  extends ActiveEventDispatcher<SignalDispatcher, Signal>
{
  /**
   * Sets the system default signal dispatcher.
   *
   * @param dispatcher An instance to be used when the next signal is started.
   *        When {@code null}, the signal dispatcher is set
   *        to the original system default.
   */
  public static void setDefaultDispatcher(SignalDispatcher dispatcher)
  {
  }

  /**
   * Obtain the default dispatcher for signals.
   *
   * @return the current default dispatcher
   */
  public static synchronized SignalDispatcher getDefaultDispatcher()
  {
    return null;
  }

  /**
   * Creates a new dispatcher, whose dispatching thread runs with the given
   * {@link javax.realtime.SchedulingParameters}.
   *
   * @param scheduling Parameters for scheduling this dispatcher.
   *
   * @param group Container for this dispatcher.
   *
   * @throws StaticIllegalStateException when the intersection of affinity
   *         in {@code schedule} and the affinity of {@code group} does not
   *         correspond to a valid affinity.
   */
  public SignalDispatcher(SchedulingParameters scheduling,
                          RealtimeThreadGroup group)
    throws StaticIllegalStateException
  {
    super(null);
  }

  /**
   * Creates a new dispatcher, whose dispatching thread runs with the given
   * {@link javax.realtime.SchedulingParameters}.
   *
   * @param scheduling For scheduling this dispatcher.
   *
   * @throws StaticIllegalStateException when the intersection of affinity
   *         in {@code scheduling} and the affinity of {@code group}
   *         does not correspond to a valid affinity.
   */
  public SignalDispatcher(SchedulingParameters scheduling)
    throws StaticIllegalStateException
  {
    super(null);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRegistered(Signal target)
  {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void register(Signal target)
    throws RegistrationException, StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void activate(Signal target) throws StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deregister(Signal target)
    throws DeregistrationException, StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deactivate(Signal target) throws StaticIllegalStateException
  {
  }

  /**
   * Releases all resources thereby making the dispatcher unusable.
   *
   * @throws javax.realtime.StaticIllegalStateException when called on
   *         a dispatcher that has one or more registered {@link Signal}
   *         objects.
   *
   */
  @Override
  public void destroy() throws StaticIllegalStateException
  {
  }
}
