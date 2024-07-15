/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.enforce;

import java.util.function.Consumer;

import javax.realtime.RealtimeThread;
import javax.realtime.RealtimeThreadGroup;
import javax.realtime.Schedulable;
import javax.realtime.Scheduler;

/**
 * The base class for all measurable constraints.  There are several types
 * of resource constraints typified by the value of {@code <T>} in the each
 * concrete subclass.  Each type is responsible for constraining on resource
 * for a group of tasks (instances of {@link javax.realtime.Schedulable} and
 * other instances of {@code java.lang.Thread}).  A task is only ever
 * associated with one instance of a given type of {@code ResourceConstraint}
 * via its {@code RealtimeThreadGroup} nearest instance, i.e., the first
 * {@code RealtimeThreadGroup} found that has an associated instance of
 * {@code <T>} when traversing up the thread group  hierarchy from its
 * immediate thread group.
 *
 * @param <T>
 *
 * @since RTSJ 2.0
 */
public abstract class ResourceConstraint<T extends ResourceConstraint<T>>
{
 /**
  * Create a new contraint with the given parent.
  * @param group The realtime thread group this constraint should govern.
  */
  public ResourceConstraint(RealtimeThreadGroup group)
  {
  }

  public T getParent() { return null; }

  /**
   * Determine whether or not enforcing is in effect.
   *
   * @return {@code true} when yes and {@code false} when not.
   */
  public boolean isEnforcing() { return false; }

  /**
   * Start applying this constraint to is tasks.
   */
  public abstract void start();

  /**
   * Stop applying this constraint to is tasks.
   */
  public abstract void stop();

  /**
   * Determine the associated realtime thread group.
   * @return the associated group
   */
  public RealtimeThreadGroup getRealtimeThreadGroup() { return null; }

  /**
   * See whether or not this task is governed, i.e., limited by this
   * constraint.
   *
   * @return {@code true} when the current task in governed by this
   *         constraint; otherwise {@code false}.
   */
  public boolean governs()
  {
    Thread current = Thread.currentThread();
    if (current instanceof RealtimeThread)
      {
        return governs(Scheduler.currentSchedulable());
      }
    else
      {
        return governs(current);
      }
  }

  /**
   * See whether or not {@code thread} is governed, i.e., limited by
   * this constraint.
   *
   * @param thread The thread in question
   *
   * @return {@code true} when the current task in governed by this
   *         constraint; otherwise {@code false}.
   */
  public boolean governs(Thread thread)
  {
    return false;
  }

  /**
   * See whether or not {@code schedulable} is governed, i.e., limited by
   * this constraint.
   *
   * @param schedulable The schedulable in question
   *
   * @return {@code true} when the current task in governed by this
   *         constraint; otherwise {@code false}.
   */
  public boolean governs(Schedulable schedulable)
  {
    return false;
  }

  /**
   * Do something on each task subject to this constraint.
   *
   * @param visitor the code to execute on each task.
   */
  public final void visitGoverned(Consumer<Thread> visitor) {}

  /**
   * Do something on each child constraint.
   *
   * @param visitor the code to execute on each child.
   */
  public final void visitBorrowers(Consumer<T> visitor) {}

  /**
   * Register a handler that should be called when this constraint is
   * violated.
   *
   * @param handler A consumer that accepts the constraint object.
   * {@code Thread.currentThread()} can be used to identify the thread
   * that caused the overrun.
   */
  public void setNotificationHandler(Consumer<T> handler) {}

  /**
   * Get the handler that should be called when this constraint is
   * violated.
   *
   * @return the registered handler.
   */
  public Consumer<T> getNotificationHandler() { return null; }
}
