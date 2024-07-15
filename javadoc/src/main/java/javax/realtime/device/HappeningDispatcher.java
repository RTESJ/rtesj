/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.device;

import javax.realtime.ActiveEventDispatcher;
import javax.realtime.DeregistrationException;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticIllegalStateException;
import javax.realtime.RegistrationException;
import javax.realtime.RealtimeThreadGroup;
import javax.realtime.SchedulingParameters;

/**
 * This class provides a means of dispatching a set of {@link Happening}.
 *
 * @since RTSJ 2.0
 */
public final class HappeningDispatcher
  extends ActiveEventDispatcher<HappeningDispatcher, Happening>
{
  /**
   * Sets the system default happening dispatcher.
   *
   * @param dispatcher The default to use when no dispatcher is provided.  When
   *        {@code null}, the happening dispatcher is set to the
   *        original system default.
   */
  public static void setDefaultDispatcher(HappeningDispatcher dispatcher)
  {
  }

  /**
   * Obtain the default dispatcher for happenings
   *
   * @return the current default dispatcher
   */
  public static synchronized HappeningDispatcher getDefaultDispatcher()
  {
    return null;
  }

  /**
   * Creates a new dispatcher, whose dispatching thread runs with the given
   * scheduling parameters.
   *
   * @param schedule The parameters to use for scheduling this dispatcher.
   * @param group The realtime thread group to use for the dispatcher.
   *
   * @throws StaticIllegalStateException when the intersection of affinity
   *         in {@code schedule} and the affinity of {@code group} does not
   *         correspond to a valid affinity.
   */
  public HappeningDispatcher(SchedulingParameters schedule,
                             RealtimeThreadGroup group)
    throws StaticIllegalStateException
  {
    super(null);
  }

  /**
   * Creates a new dispatcher, whose dispatching thread runs with the given
   * scheduling parameters.
   *
   * @param schedule The parameters to use for scheduling this dispatcher.
   *
   * @throws StaticIllegalStateException when the intersection of affinity
   *         in {@code schedule} and the affinity of the current thread grouo
   *         does not correspond to a valid affinity.
   */
  public HappeningDispatcher(SchedulingParameters schedule)
    throws StaticIllegalStateException
  {
    super(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRegistered(Happening target)
  {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void register(Happening target)
    throws RegistrationException, StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void activate(Happening target) throws StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deregister(Happening target)
    throws DeregistrationException, StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deactivate(Happening target) throws StaticIllegalStateException
  {
  }

  /**
   * Releases all resources thereby making the dispatcher unusable.
   *
   * @throws javax.realtime.StaticIllegalStateException when called on
   *         a dispatcher that has one or more registered {@link Happening}
   *         objects.
   */
  @Override
  public void destroy() throws StaticIllegalStateException
  {
  }

  /**
   * Queues the happening for dispatching by this dispatcher.
   * This should only be called from {@link Happening#trigger()}.
   *
   * @param happening The happening that needs to be dispatched.
   */
  void trigger(Happening happening)
  {
  }
}
