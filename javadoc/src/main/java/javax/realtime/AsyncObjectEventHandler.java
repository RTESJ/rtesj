/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
/*
 * Created on May 23, 2010
 *
 * Copyright (C) 2004, 2005 TimeSys Corporation, All Rights Reserved.
 */
package javax.realtime;

import java.util.function.Consumer;

/**
 * A version of {@link AsyncBaseEventHandler} that carries an
 * {@code Object} value as payload.
 *
 * @param <P> is the type of Object received.
 *
 * @since RTSJ 2.0
 */
public class AsyncObjectEventHandler<P>
  extends AsyncBaseEventHandler
{
  private final WaitFreeWriteQueue<P> pending_releases_;

  /**
   * Creates an asynchronous event handler that receives a {@code P}
   * payload with each fire.
   *
   * @param scheduling Parameter for scheduling the new handler
   *        (and possibly other instances of {@link Schedulable}).  When
   *        {@code scheduling} is {@code null} and the
   *        creator is an instance of {@link Schedulable},
   *        {@link SchedulingParameters} is a clone of the creator's value
   *        created in the same memory area as {@code this}.  When
   *        {@code scheduling} is {@code null} and the creator is
   *        a task that is not an instance of {@code Schedulable}, the
   *        contents and type of the new {@code SchedulingParameters}
   *        object are governed by the associated scheduler.
   *
   * @param release Parameter for scheduling the new handler
   *        (and possibly other instances of {@link Schedulable}).  When
   *        {@code release} is {@code null} the new
   *        {@code AsyncEventHandler} will use a clone of the
   *        default {@link ReleaseParameters} for the associated
   *        scheduler created in the memory area that contains the
   *        {@code AsyncEventHandler} object.
   *
   * @param memory Parameter for scheduling the new handler
   *        (and possibly other instances of {@link Schedulable}).  When
   *        {@code memory} is {@code null}, the new
   *        {@code AsyncEventHandler} receives {@code null} value
   *        for its memory parameters, and the amount or rate of memory
   *        allocation for the new handler is unrestricted.
   *
   * @param area The initial memory area of this handler.
   *
   * @param runner Logic to be executed by {@link #handleAsyncEvent}
   *
   * @param logic The logic to run for each fire.  When {@code logic}
   *        is {@code null}, the {@code handleAsyncEvent}
   *        method in the new object will serve as its logic.
   *
   * @throws StaticIllegalArgumentException when the event queue
   *         overflow policy is {@link QueueOverflowPolicy#DISABLE}.
   */
  public AsyncObjectEventHandler(SchedulingParameters scheduling,
                                 ReleaseParameters<?> release,
                                 MemoryParameters memory,
                                 MemoryArea area,
                                 ConfigurationParameters config,
                                 ReleaseRunner runner,
                                 Consumer<P> logic)
    throws StaticIllegalArgumentException
  {
    super(scheduling, release, memory, area, config, runner, logic);
    pending_releases_ =
      new WaitFreeWriteQueue<P>(release.getInitialQueueLength());
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncObjectEventHandler(SchedulingParameters,
   *                                 ReleaseParameters,
   *                                 MemoryParameters,
   *                                 MemoryArea,
   *                                 ConfigurationParameters,
   *                                 ReleaseRunner,
   *                                 Consumer)}
   * with arguments {@code (scheduling, release, null, null, null, logic)}.
   */
  public AsyncObjectEventHandler(SchedulingParameters scheduling,
                                 ReleaseParameters<?> release,
                                 Consumer<P> logic)
    throws StaticIllegalArgumentException
  {
    this(scheduling, release, null, null, null, null, logic);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncObjectEventHandler(SchedulingParameters,
   *                                 ReleaseParameters,
   *                                 MemoryParameters,
   *                                 MemoryArea,
   *                                 ConfigurationParameters,
   *                                 ReleaseRunner,
   *                                 Consumer)}
   * with arguments {@code (scheduling, release, null, null, null, null, null)}
   */
  public AsyncObjectEventHandler(SchedulingParameters scheduling,
                                 ReleaseParameters<?> release)
    throws StaticIllegalArgumentException
  {
    this(scheduling, release, null, null, null, null, null);
  }

  /**
   * Calling this constructor is equivalent to calling
   * {@link #AsyncObjectEventHandler(SchedulingParameters,
   *                                 ReleaseParameters,
   *                                 MemoryParameters,
   *                                 MemoryArea,
   *                                 ConfigurationParameters,
   *                                 ReleaseRunner,
   *                                 Consumer)}
   * with arguments {@code (null, null, null, null, null, null, logic)}.
   *
   * @param logic It is the function to call on the object received.
   */
  public AsyncObjectEventHandler(Consumer<P> logic)
  {
    this(null, null, null, null, null, null, logic);
  }

  /**
   * Creates an instance of {@code AsyncObjectEventHandler} (AOEH) with default
   * values for all parameters.
   *
   * @see #AsyncObjectEventHandler(SchedulingParameters,
   *                               ReleaseParameters,
   *                               MemoryParameters,
   *                               MemoryArea,
   *                               ConfigurationParameters,
   *                               ReleaseRunner,
   *                               Consumer)
   */
  public AsyncObjectEventHandler()
  {
    this(null, null, null, null, null, null, null);

  }

  /**
   * This method holds the logic which is to be executed when any
   * {@link AsyncEvent} with which this handler is associated is fired.
   * This method will be invoked repeatedly while {@code fireCount} is
   * greater than zero.
   *
   * <p> The default implementation of this method invokes the
   * {@code run} method of any non-null {@code logic} instance
   * passed to the constructor of this handler.
   *
   * <p> This AOEH is a source of reference for its initial memory area
   * while this AOEH is released.
   *
   * <p> All throwables from (or propagated through)
   * {@code handleAsyncEvent(P)} are caught, a stack trace is printed
   * and execution continues as if {@code handleAsyncEvent(P)} had
   * returned normally.
   */
  public void handleAsyncEvent(P value) {}

  /**
   * Determines the next value queued for handling.
   *
   * @return the object reference at the head of the queue of object references
   *         to be passed to {@link #handleAsyncEvent}}.
   *
   * @throws StaticIllegalStateException when the fire count is zero.
   */
  public P peekPending() throws StaticIllegalStateException
  {
    return null;
  }

  /**
   * Release this handler directly.
   *
   * @param payload The Object to be passed to the handler.
   */
  public void release(P payload)
  {
  }
}
