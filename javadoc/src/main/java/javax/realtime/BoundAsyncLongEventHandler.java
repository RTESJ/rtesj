/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.util.function.LongConsumer;

/**
 * A bound asynchronous event handler is an instance of
 * {@link AsyncLongEventHandler} that is permanently bound to a dedicated
 * {@link RealtimeThread}.  Bound asynchronous long event handlers are for use in
 * situations where the added timeliness is worth the overhead of
 * dedicating an individual realtime thread to the handler. Individual
 * server realtime threads can only be dedicated to a single bound event
 * handler.
 *
 * @since RTSJ 2.0
 */
public class BoundAsyncLongEventHandler
  extends AsyncLongEventHandler
  implements BoundSchedulable
{
  /**
   * Creates an instance of {@code BoundAsyncEventHandler} (BAEH) with the
   * specified parameters.
   *
   * @param scheduling A {@link SchedulingParameters} object which will
   *        be associated with the constructed instance.  When
   *        {@code null}, and the creator is not an instance of
   *        {@link Schedulable}, a {@code SchedulingParameters} object is
   *        created which has the default {@code SchedulingParameters}
   *        for the scheduler associated with the current thread.  When
   *        {@code null}, and the creator is an instance of
   *        {@code Schedulable}, the {@code SchedulingParameters} are
   *        inherited from the current schedulable (a new
   *        {@code SchedulingParameters} object is cloned).
   *        The Affinity of the newly-created handler will be
   *          set as follow:
   *          <ul>
   *            <li>When defined, from SchedulingParameters.</li>
   *            <li>When the creating task is in the RealtimeThreadGroup
   *                in parameters, or when no group are defined, the Affinity
   *                will be inherited from the creating Thread</li>
   *            <li>Otherwise, the Affinity will be inherited from the
   *                RealtimeThreadGroup in parameters. When it is not set,
   *                it will take the affinity of the group of the creating
   *                thread.</li>
   *          </ul>
   *          In all the cases where the affinity is not explicitly set using
   *          {@link AsyncBaseEventHandler#setSchedulingParameters(SchedulingParameters)},
   *          the default affinity assigned to this Schedulable will not appear
   *          in the {@link SchedulingParameters} returned by
   *          {@link AsyncBaseEventHandler#getSchedulingParameters()}.
   * @param release A {@link ReleaseParameters} object which will be
   *          associated with the constructed instance. When
   *          {@code null}, {@code this} will have default
   *          {@code ReleaseParameters} for the BAEH's scheduler.
   * @param memory A {@link MemoryParameters} object which will be
   *          associated with the constructed instance. When
   *          {@code null}, {@code this} will have no
   *          {@code MemoryParameters} and the handler can access the heap.
   * @param area The {@link MemoryArea} for {@code this}. When
   *          {@code null}, the memory area will be that of the
   *          current thread/schedulable.
   * @param group A {@link RealtimeThreadGroup} object which will
   *          be associated with the constructed instance. When
   *          {@code null}, {@code this} will be
   *          associated with the creating thread's realtime thread group.
   * @param config The {@link ConfigurationParameters} associated with
   *        {@code this}, and possibly other instances of {@link Schedulable}.
   *        When {@code config} is {@code null}, this
   *        {@code BoundAsyncEventHandler} will reserve no space for
   *        preallocated exceptions and implementation-specific values will
   *        be set to their implementation-defined defaults.
   * @param logic The {@code LongConsumer} object whose {@code accept()}
   *        method is executed by
   *        {@link AsyncLongEventHandler#handleAsyncEvent(long)}.
   *        When {@code null}, the default {@code handleAsyncEvent(long)}
   *        method invokes nothing.
   * @throws ProcessorAffinityException when the Affinity in SchedulingParameters
   *         is invalid or not a subset of the groups {@code this} is
   *         associated to.
   * @throws StaticIllegalArgumentException when {@code config} is of type
   *         {@link javax.realtime.memory.ScopedConfigurationParameters}
   *         and {@code logic}, any parameter object, or {@code this} is
   *         in heap memory.
   * @throws IllegalAssignmentError when the new {@code AsyncEventHandler}
   *         instance cannot hold a reference to any value assigned to
   *         one of the {@code scheduling}, {@code release}, {@code memory},
   *         or {@code group} parameters, or when those parameters
   *         cannot hold a reference to the new {@code AsyncEventHandler}.
   *         Also when the new {@code AsyncEventHandler} instance cannot
   *         hold a reference to values assigned to {@code area} or
   *         {@code logic}.
   */
  public BoundAsyncLongEventHandler(SchedulingParameters scheduling,
                                    ReleaseParameters<?> release,
                                    MemoryParameters memory,
                                    MemoryArea area,
                                    RealtimeThreadGroup group,
                                    ConfigurationParameters config,
                                    LongConsumer logic)
  {
    super(scheduling, release, memory, area, null, null, logic);
  }

  /**
   * Creates an instance of {@code BoundAsyncLongEventHandler}.
   * This constructor is equivalent to
   *  {@code BoundAsyncLongEventHandler(scheduling, release, null, null, null, null, logic)}
   */
  public BoundAsyncLongEventHandler(SchedulingParameters scheduling,
                                    ReleaseParameters<?> release,
                                    LongConsumer logic)
  {
    this(scheduling, release, null, null, null, null, logic);
  }

  /**
   * Creates an instance of {@code BoundAsyncLongEventHandler}.
   * Calling this constructor is equivalent to calling
   *  {@code BoundAsyncLongEventHandler(scheduling, release, null, null, null, null, null)}
   */
  public BoundAsyncLongEventHandler(SchedulingParameters scheduling,
                                    ReleaseParameters<?> release)
  {
    this(scheduling, release, null, null, null, null, null);
  }

  /**
   * Creates an instance of {@code BoundAsyncLongEventHandler}.
   * Calling this constructor is equivalent to calling
   *  {@code BoundAsyncLongEventHandler(null, null, null, null, null, null, logic)}
   */
  public BoundAsyncLongEventHandler(LongConsumer logic)
  {
    this(null, null, null, null, null, null, logic);
  }

  /**
   * Creates an instance of {@code BoundAsyncLongEventHandler} using
   *  default values.  Calling this constructor is equivalent to calling
   *  {@code BoundAsyncLongEventHandler(null, null, null, null, null, null, null)}
   */
  public BoundAsyncLongEventHandler()
  {
    this(null, null, null, null, null, null, null);
  }
}
