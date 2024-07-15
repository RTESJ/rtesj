/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.control;

import javax.realtime.HighResolutionTime;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticUnsupportedOperationException;

/**
 * Creates a scope in a {@link javax.realtime.Schedulable} object which will
 * be asynchronously interrupted at the expiration of a timer.  This timer
 * will begin measuring time at some point between the time
 * {@code doInterruptible} is invoked and the time when the
 * {@code run()} method of the {@code Interruptible} object is
 * invoked.  Each call of {@code doInterruptible} on an instance
 * of {@code Timed} will restart the timer for the amount of time
 * given in the constructor or the most recent invocation of
 * {@code resetTime()}. The timer is cancelled when it has not
 * expired before the {@code doInterruptible} method has
 * finished.
 *
 * <p>All memory use of an instance of {@code Timed} occurs during
 * construction or the first invocation of
 * {@code doInterruptible}.  Subsequent invocations of
 * {@code doInterruptible} do not allocate memory.
 *
 * <p>When the timer fires, the resulting AIE will be generated for the
 * schedulable within a bounded execution time of the targeted
 * schedulable.
 *
 * <p>Typical usage: {@code new Timed(T).doInterruptible(interruptible);}
 */
public class Timed extends AsynchronouslyInterruptedException
{
  /**
   *
   */
  private static final long serialVersionUID = -4530191000795901577L;

  /**
   * Creates an instance of {@code Timed} with a timer set to
   * {@code time}.  When the {@code time} is in the past the
   * {@link AsynchronouslyInterruptedException} mechanism is activated
   * immediately after or when the {@code doInterruptible} method
   * is called.
   * @param time When {@code time} is a {@link javax.realtime.RelativeTime}
   * value, it is the interval of time between the invocation of
   * {@code doInterruptible} and the time when the schedulable is
   * asynchronously interrupted.  When {@code time} is an
   * {@link javax.realtime.AbsoluteTime} value, the timer asynchronously
   * interrupts at this time (assuming the timer has not been cancelled).
   *
   * @throws StaticIllegalArgumentException when {@code time} is
   *            {@code null}.
   *
   * @throws StaticUnsupportedOperationException when {@code time} is not
   *         based on a {@link javax.realtime.Clock}.
   */
  public Timed(HighResolutionTime<?> time)
    throws StaticIllegalArgumentException,
           StaticUnsupportedOperationException
  {
  }

  /**
   * Executes a timeout method by
   * starting the timer and executing the {@code run()} method of the
   * given {@link Interruptible} object.
   *
   * @param logic {@inheritDoc}
   *
   * @return {@inheritDoc}
   *
   * @throws StaticIllegalArgumentException {@inheritDoc}
   * @throws IllegalThreadStateException {@inheritDoc}
   *
   */
  @Override
  public boolean doInterruptible(Interruptible logic) { return true; }

  /**
   * Sets the timeout for the next invocation of {@code doInterruptible}.
   *
   * @param time This can be an absolute time or a relative time.  When
   *        {@code null} or not based on a {@link javax.realtime.Clock}, the
   *        timeout is not changed.
   */
  public void resetTime(HighResolutionTime<?> time) {}

  /*
   * Resets the timeout.  When this {@link Timed} instance is executing,
   * it adjusts the timeout to {@code time} and restarts the timer.  When
   * the instance is not executing, it adjusts the timeout for the next
   * invocation.
   *
   * @param time The new timeout.
   *
   * @throws StaticIllegalArgumentException when {@code time} is
   *         {@code null} or a relative time less than zero.
   *
   * @throws StaticUnsupportedOperationException when {@code time} is not
   *         based on a {@link javax.realtime.Clock}
   *
   * @since RTSJ 2.0
   */
  /*
  public void restart(HighResolutionTime<?> time) {}
  */

  @Override
  protected synchronized void finalize() {}
}
