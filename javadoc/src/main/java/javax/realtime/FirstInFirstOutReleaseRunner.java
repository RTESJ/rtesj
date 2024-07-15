/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.util.function.IntBinaryOperator;

/**
 * The default {@link ReleaseRunner} that uses a pool of FIFO scheduled
 * realtime threads to run handlers.  This reduces the amount of threads
 * required for handling events compared with bounding a thread to each
 * handler.  It also supports handlers that suspend themselves, e.g., by
 * calling the {@code Object.wait()} method.  The size of the pool of threads
 * is based on the number of handlers, the number of priorities in use, and
 * the number of cpus available.  For systems with many
 * {@link AsyncBaseEventHandler} instances, there can be significantly fewer
 * threads to run releases of those handlers in the system.
 *
 * @since RTSJ 2.0
 */
public class FirstInFirstOutReleaseRunner extends ReleaseRunner
{
  private final static RealtimeThreadGroup _default_group_ =
    new RealtimeThreadGroup("Default Release Runner");

  /**
   * Create a release runner which maintains a pool of threads to run releases
   * of {@link AsyncBaseEventHandler} instances.  The threads in the pool all
   * run in a given {@link RealtimeThreadGroup} instance.  The thread pool size
   * is determined by the binary function {@code sizer}.  When {@code sizer}
   * is {@code null}, a reasonable default is provided.
   *
   * @param config the ConfigurationParameters object to use for all handler
   *        run from this pool, which means for each thread in the pool.
   *
   * @param group for the pool threads.
   *
   * @param sizer A binary function from the number of handlers and the
   *        number of priorities of those handles to the
   *        number of threads in the pool.  It may use global
   *        information, such as the number of available CPUs.  It may
   *        also ignore its arguments.
   */
  public FirstInFirstOutReleaseRunner(ConfigurationParameters config,
                                      RealtimeThreadGroup group,
                                      IntBinaryOperator sizer)
  {
    super(group == null ? _default_group_ : group);
  }


  /**
   * Same as
   * {@link #FirstInFirstOutReleaseRunner(ConfigurationParameters, RealtimeThreadGroup, IntBinaryOperator)}
   * with arguments {(config, null, null)}.
   */
  public FirstInFirstOutReleaseRunner(ConfigurationParameters config)
  {
    this(config, null, null);
  }

  @Override
  protected RealtimeThreadGroup getRealtimeThreadGroup() { return null; }

  @Override
  public ConfigurationParameters getConfigurationParameters() { return null; }

  @Override
  protected void release(Proxy handler) {}

  /**
   * Attach a handler from this runner, so it will be released.
   * Adjusts the number of threads for running handlers accordingly.
   *
   * @param handler to be removed.
   *
   * @throws StaticIllegalArgumentException When {@code handler} is null
   */
  @Override
  protected void attach(Proxy handler)
    throws StaticIllegalStateException
  {
  }

  /**
   * Detach a handler from this runner, so it will no longer be released.
   * Adjusts the number of threads for running handlers accordingly.
   *
   * @param handler to be detached.
   *
   * @throws StaticIllegalArgumentException When {@code handler} is null
   */
  @Override
  protected void detach(Proxy handler)
    throws StaticIllegalStateException
  {
  }

  /**
   * A type of realtime thread for executing the code of a release.
   */
  private final class HandlerThread extends RealtimeThread
  {
  }
}
