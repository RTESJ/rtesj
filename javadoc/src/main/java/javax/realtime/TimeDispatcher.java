/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.util.Stack;

/**
 * A dispatcher for time events: {@link Timer} and {@link RealtimeThread#sleep}.
 *
 * @since RTSJ 2.0
 */
public final class TimeDispatcher
  extends ActiveEventDispatcher<TimeDispatcher, Timer>
{
  /**
   * Sets the system default time dispatcher.
   *
   * @param dispatcher To be used when no dispatcher is provided.  When
   *        {@code null}, the default time dispatcher is set to the
   *        original system default.
   */
  public static void setDefaultDispatcher(TimeDispatcher dispatcher)
  {
  }

  /**
   * Obtain the default dispatcher for timers
   *
   * @return the current default dispatcher
   */
  public static synchronized TimeDispatcher getDefaultDispatcher()
  {
    return null;
  }

  /**
   * Creates a new dispatcher, whose dispatching thread runs with the given
   * scheduling parameters.
   *
   * @param schedule It gives the parameters for scheduling this dispatcher
   *
   * @throws StaticIllegalStateException when the intersection of affinity
   *         in {@code schedule} and the affinity of {@code group} does not
   *         correspond to a valid affinity.
   */
  public TimeDispatcher(SchedulingParameters schedule,
                        RealtimeThreadGroup group)
    throws StaticIllegalStateException
  {
    super(new RealtimeThread(schedule));
  }

  /**
   * Creates a new dispatcher, whose dispatching thread runs with the given
   * scheduling parameters.
   *
   * @param schedule It gives the parameters for scheduling this dispatcher
   *
   * @throws StaticIllegalStateException when the intersection of affinity
   *         in {@code schedule} and the affinity of the current rhread groupo
   *         does not correspond to a valid affinity.
   */
  public TimeDispatcher(SchedulingParameters schedule)
    throws StaticIllegalStateException
  {
    this(schedule, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isRegistered(Timer target)
  {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void register(Timer target)
    throws RegistrationException, StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void activate(Timer target) throws StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deregister(Timer target)
    throws DeregistrationException, StaticIllegalStateException
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void deactivate(Timer target) throws StaticIllegalStateException
  {
  }

  /**
   * Releases all resources thereby making the dispatcher unusable.
   *
   * @throws StaticIllegalStateException when called on a dispatcher that has
   *         one or more registered {@link Timer} objects.
   */
  @Override
  public void destroy()
    throws StaticIllegalStateException
  {
  }
}
