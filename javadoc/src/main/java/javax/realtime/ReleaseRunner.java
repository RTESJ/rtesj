/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Manages a pool of threads to execute asynchronous event handler releases.
 * The implementer is responsible for maintaining the pool of threads and
 * ensuring they all have at least the desired {@link ConfigurationParameters},
 * {@link RealtimeThreadGroup}, and {@link Affinity}.
 * <p>
 * The other parameters for instances of {@link Schedulable} can either be
 * set for each release or be configurable for the pool.  In the latter case,
 * one should not be able to associate a handler with the runner that has an
 * incompatible parameter set.  These other parameters are
 * {@link SchedulingParameters}, {@link ReleaseParameters}, and
 * {@link MemoryParameters}, as well as the {@link MemoryArea} in which the
 * release should take place.
 * <p>
 * The default release runner, {@link FirstInFirstOutReleaseRunner},
 * sets these other parameters on the releasing thread at each
 * release.  Since there may be a performance penalty for doing this,
 * an application can define its own release runners for commonly
 * occurring cases of these parameters.  It is then up to the
 * application to ensure that handlers are matched to the correct
 * release runner.
 *
 * @since RTSJ 2.0
 */
public abstract class ReleaseRunner
{
  /**
   * An interface to encapsulate the execution contexts for running
   * an instance of {@link AsyncBaseEventHandler}.  It is used for
   * implementing proxys for running handlers.  This is necessary for
   * supporting execution in memory areas other than {@link PerennialMemory}.
   */
  public static interface Proxy extends Runnable, Comparable<Proxy>
  {
    public SchedulingParameters getSchedulingParameters();
    public AbsoluteTime getReleaseTime();
    public AsyncBaseEventHandler getHandler();
    /** Compare the release parameters of this Proxy. */
    @Override
    public default int compareTo(ReleaseRunner.Proxy r2) { return 0; }
  }

  /**
   * Enables creating a subclass of this class.
   */
  protected ReleaseRunner(RealtimeThreadGroup group)
  {
  }

  /**
   * Determine the {@code RealtimeThreadGroup} instance used.
   *
   * @return the {@code RealtimeThreadGroup} instance used by all threads
   *         used for running releases.
   */
  protected abstract RealtimeThreadGroup getRealtimeThreadGroup();

  /**
   * Get the {@code ConfigurationParameters} object used for all threads
   * provided by this release runner.
   *
   * @return those parameters.
   */
  public abstract ConfigurationParameters getConfigurationParameters();

  /**
   * Finds a thread and has it call the {@link Proxy#run()}
   * method.  Care should be exercised when implementing this method, since
   * it adds to both the latency and jitter of releasing events.  The caller
   * must guarantee that releases of any given handler are always executed in
   * order.
   * <p>
   * This method should only be called from the infrastructure.
   *
   * @param handler The handler to be released.
   */
  protected abstract void release(Proxy handler);

  /**
   * Notifies this runner that the handler is now associated with it.  Any
   * compatibility check should be done here.
   * <p>
   * This method should only be called from the infrastructure.
   *
   * @param handler The handler to be attached
   *
   * @throws StaticIllegalStateException when {@code handler} is
   *         already attached.
    *
   * @throws ProcessorAffinityException when {@code handler} is
   *         has an illegal affinity.
  */
  protected abstract void attach(Proxy handler)
    throws StaticIllegalStateException,
           ProcessorAffinityException;

  /**
   * Notifies this runner that the handler is no longer associated with it.
   * <p>
   * This method should only be called from the infrastructure.
   *
   * @param handler The handler to be removed
   *
   * @throws StaticIllegalStateException when {@code handler} is not attached.
   */
  protected abstract void detach(Proxy handler)
    throws StaticIllegalStateException;
}
