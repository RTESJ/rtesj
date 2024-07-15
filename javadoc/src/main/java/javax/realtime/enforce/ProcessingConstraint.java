/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.enforce;

import javax.realtime.AbsoluteTime;
import javax.realtime.AsyncEventHandler;
import javax.realtime.HighResolutionTime;
import javax.realtime.IllegalAssignmentError;
import javax.realtime.RealtimeThreadGroup;
import javax.realtime.RelativeTime;
import javax.realtime.ReturnsThis;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticUnsupportedOperationException;

/**
 * A class for handling contraining CPU used by tasks as a group via their
 * {@link javax.realtime.RealtimeThreadGroup} instance.  As with
 * {@code ThreadGroup} and
 * {@code RealtimeThreadGroup}, instances of {@code ProcessingConstraint}
 * can be nested. The cost of the group, including all tasks in its subgroups
 * not subject to another {@code ProcessingConstraint} instance, can be both
 * tracked and limited over a given period, by bounding the execution demands
 * of those tasks.
 *
 *<p> A processing constraint has an associated affinity. The precision of cost
 * monitoring is dependent on the number of processors in the affinity.
 * In the worst case, it is the base precision times the number of processors
 * in the processing group.  The default affinity is that which was inherited
 * from the parent {@code ProcessingConstraint}.
 *
 * <p> For all tasks with a reference to an instance of
 * {@code ProcessingConstraint} {@code p}, no more than {@code p.cost} will
 * be allocated to the execution of these tasks on the processors
 * associated with its processing group in each interval of time given
 * by {@code p.period} after the time indicated by {@code p.start}.  No
 * execution of the tasks will be allowed on any processor other than
 * these processors.
 *
 * <p>For each running task subject to a processing group, there must always
 * be at least one processor in the intersection between a task object's
 * affinity and its processing group's affinity regardless of the group's
 * monitoring state.
 *
 * <p>Logically, a {@code ProcessingConstraint} represents a virtual
 * server.  This server has a start time, a period, a cost (budget), and
 * a deadline equal to {@code period}.  The server can only logically
 * execute when
 * <ul>
 * <li> (a) it has not consumed more execution time in its current
 * release than the cost (budget) parameter,</li>
 * <li> (b) one of its associated tasks is executable and
 * is the most eligible of the executable tasks.</li>
 * </ul>
 * When the server is logically executable, the associated tasks are
 * executed.
 *
 * <p>When the cost has been consumed, any {@code overrunHandler} is
 * released, and the server is not eligible for logical execution until
 * the period is finished. At this point, its allocated cost (budget) is
 * replenished. When the server is logically executable when its deadline
 * expires, any associated {@code missHandler} is released.
 * When the server is logically executable when its next release time occurs,
 * any associated {@code underrunHandler} is released.
 *
 * <p>The deadline and cost parameters of all the associated schedulable
 * objects have the same impact as they would if the objects were not
 * bound to a processing group.
 *
 * <p> Processing group parameters use
 * {@link javax.realtime.HighResolutionTime} values for cost
 * period, and start time. Since those times are expressed as a
 * {@link javax.realtime.HighResolutionTime}, the values use accurate
 * timers with nanosecond granularity.  The actual resolution available
 * and even the quantity it measures depends on the clock associated
 * with each time value.
 *
 * <p> The implementation must use copy semantics for each
 * {@link javax.realtime.HighResolutionTime} parameter value.  The value
 * of each time object should be copied at the time it is passed to the
 * parameter object, and the object reference must not be retained.
 *
 * Only changes to a {@code ProcessingConstraint} object
 * caused by methods on that object are immediately visible to the
 * scheduler.  For instance, invoking {@code setPeriod()} on a
 * {@code ProcessingConstraint} object will make the change,
 * then notify the scheduler that the parameter object has changed.
 * At that point the scheduler's view of the processing group parameters
 * object is updated.  Invoking a method on the
 * {@code RelativeTime} object that affects the period for this object
 * may change the period but it does not pass the change to the
 * scheduler at that time.  That new value for period must not change
 * the behavior of the SOs that use the parameter object until a setter
 * method on the {@code ProcessingConstraint} object is
 * invoked or the object is used in a constructor for an SO.
 *
 * <p> The following table gives the default parameter values for the
 * constructors.
 *
 *  <table width="95%" border="1">
 *   <caption>ProcessingConstraint Default Values</caption>
 *  <tr>
 *    <th align="center"><div><strong>Attribute</strong></div></th>
 *    <th align="center"><div><strong>Default Value</strong></div></th>
 *  </tr>
 *  <tr>
 *    <td>period</td>
 *    <td>No default. A value must be supplied</td>
 *  </tr>
 *  <tr>
 *    <td>cost</td>
 *    <td>No default. A value must be supplied</td>
 *  </tr>
 *  <tr>
 *    <td>minimum</td>
 *    <td><code>null</code>, no minimum</td>
 *  </tr>
 *  <tr>
 *    <td>overrunHandler</td>
 *    <td>None</td>
 *  </tr>
 *  <tr>
 *    <td>missHandler</td>
 *    <td>None</td>
 *  </tr>
 *  <tr>
 *    <td>underrunHandler</td>
 *    <td>None</td>
 *  </tr>
 * </table>
 *
 * <p><b>Caution:</b> This class is explicitly unsafe in multithreaded
 * situations when it is being changed.  No synchronization is done.  It
 * is assumed that users of this class who are mutating instances will be
 * doing their own synchronization at a higher level.
 *
 * <p><b>Caution:</b> The {@code cost} parameter time should be
 * considered to be measured against the target platform.
 *
 * @since RTSJ 2.0
 */
public class ProcessingConstraint
  extends ResourceConstraint<ProcessingConstraint>
{
  /**
   * Get the root instance for this constraint type.
   *
   * @return the root constraint.
   */
  public static ProcessingConstraint getRootConstraint() { return null; }

  /**
   * Determine the processing constraint for the current execution context.
   *
   * @return the constraint for this context.
   */
  public static ProcessingConstraint currentConstraint() { return null; }

  /**
   * Determine the processing constraint for the give execution context.
   *
   * @param thread The given execution context.
   * @return the constraint for this context.
   */
  public static ProcessingConstraint currentConstraint(Thread thread)
  {
    return null;
  }

  /**
   * Determine the processing constraint for the give execution context.
   *
   * @param group The given execution context.
   * @return the constraint for this context.
   */
  public static ProcessingConstraint currentConstraint(ThreadGroup group)
  {
    return null;
  }

  /**
   * Gets the value of {@code period} in the provided
   * {@link javax.realtime.RelativeTime} object.
   *
   * @param dest An instance of {@code RelativeTime} which will be set
   * to the currently configured {@code period}.  If {@code dest} is
   * null, a new {@code RelativeTime} will be created in the current
   * allocation context.
   *
   * @return a reference to {@code dest}, or a newly created object if
   * {@code dest} is null.
   */
  public static RelativeTime getPeriod(RelativeTime dest) { return null; }

  /**
   * Gets the value of {@code period}.
   * <p>
   * Equivalent to {@code getPeriod(null)}.
   *
   * @return a reference to a newly allocated instance of
   *         {@link javax.realtime.RelativeTime} that represents the
   *         value of {@code period}.
   */
  public static RelativeTime getPeriod()
  {
    return new RelativeTime();
  }

  /**
   * Sets the value of {@code period}.
   *
   * @param period The new value for {@code period}.  There is no
   *        default value.  When {@code period} is {@code null}
   *        an exception is thrown.
   *
   * @throws StaticIllegalArgumentException when {@code period} is
   *      {@code null}, or its time value is not greater than zero.
   */
  public static void setPeriod(RelativeTime period)
    throws StaticIllegalArgumentException, IllegalAssignmentError
  {
  }

  /**
   * Determines the measurement granularity of cost monitoring and cost
   * enforcement.
   *
   * @return the current granularity.
   *
   * @see #setGranularity
   */
  public static RelativeTime getGranularity()
  {
    return null;
  }

  /**
   * Determines the measurement granularity of cost monitoring and cost
   * enforcement.
   *
   * @param dest An instance of {@code RelativeTime} which will be set
   * to the currently configured granularity.  If {@code dest} is
   * null, a new {@code RelativeTime} will be created in the current
   * allocation context.
   *
   * @return the current granularity
   *
   * @see #setGranularity
   */
  public static RelativeTime getGranularity(RelativeTime dest)
  {
    return null;
  }

  /**
   * Sets the measurement granularity of cost monitoring and cost
   * enforcement.  The system provides a lower bound for this.  When
   * {@code nanos} is below this lower bound, granularity silently is
   * set to the lower bound.  In general, the lower bound is the
   * precision of the realtime clock.
   *
   * <p> Note that the ganularity applies to a single processor.  When a
   * processing group spans more than one processor, the precision of cost
   * monitoring or enforcement is this ganularity times the number of active
   * processors.  This is because more than one task could be running at
   * the same time and cost can be measured at most once per the elapse
   * of this ganularity.
   *
   * @param time the new granularity
   *
   * @throws StaticIllegalArgumentException when {@code nanos} is less
   *         than one.
   */
  public static void setGranularity(RelativeTime time)
    throws StaticIllegalArgumentException
  {
  }

  /**
   * Creates a {@code ProcessingConstraint} to govern a group of threads.
   *
   * @param group The {@code RealtimeThreadGroup} to govern.
   *
   * @param cost The maximum total execution time of all tasks in the
   *        group as a ratio, where one is a complete CPU core.
   *
   * @param overrun It is called when the the total execution of all tasks in
   *        the group exceeds {@code cost} for a given {@code period}.
   */
  public ProcessingConstraint(RealtimeThreadGroup group,
                              float cost,
                              AsyncEventHandler overrun)
  {
    super(group);
  }

  /**
   * Equivalent to {@link #ProcessingConstraint(RealtimeThreadGroup,
   * float, AsyncEventHandler)} with the argument list
   * {@code (group, cost, null)}.
   */
  public ProcessingConstraint(RealtimeThreadGroup group, float cost)
  {
    this(group, cost, null);
  }

  /**
   * Determine whether or not enforcing is in effect.
   *
   * @return {@code true} when yes and {@code false} when not.
   */
  @Override
  public boolean isEnforcing() { return false; }

  /**
   * Start applying this constraint to is tasks.
   */
  @Override
  public void start() {}

  /**
   * Stop applying this constraint to is tasks.
   */
  @Override
  public void stop() {}

  /**
   * Obtains the actual time of the group's start as recorded by the system.
   * When the start time is absolute, that is the effective start time;
   * otherwise, the effective start is computed relative to the time that
   * the processing group is constructed.
   *
   * @param dest A time value to fill.
   * @return either, a new instance of {@code AbsoluteTime}, when
   *        {@code dest} is {@code null}, or {@code dest} otherwise.
   *        In either case, its value is the time at which this group
   *        actually started.
   */
  public AbsoluteTime getEffectiveStart(AbsoluteTime dest)
  {
    return dest;
  }

  /**
   * Obtains the actual time of the group's start as recorded by the system.
   * <p>
   * Equivalent to {@link #getEffectiveStart(AbsoluteTime)} where {@code dest}
   * is set to {@code null}.
   *
   * @return a reference to a new instance of {@code AbsoluteTime}
   *         that represents the time at which this group started.
   */
  public AbsoluteTime getEffectiveStart()
  {
    return getEffectiveStart(null);
  }

  /**
   * Obtain when the current period started.
   *
   * @param dest an time object for holding the result of the call.
   * @return {@code dest} or an new object, when {@code dest} is {@code null}.
   */
  public AbsoluteTime getLastRelease(AbsoluteTime dest)
  {
    return dest;
  }

  /**
   * Obtain when the current period started.
   *
   * @return the start of the latest period in a new time object.
   */
  public AbsoluteTime getLastRelease()
  {
    return getLastRelease(null);
  }

  /**
   * Gets the value of {@code cost}.
   * <p>
   * Equivalent to {@code getMaximumCost(null)}.
   *
   * @return a reference to a newly allocated object containing
   * the value of {@code cost}.
   */
  public RelativeTime getBudget() { return null; }

  /**
   * Gets the value of {@code cost} in the provided
   * {@link javax.realtime.RelativeTime} object.
   *
   * @param dest An instance of {@code RelativeTime} which will be set
   * to the currently configured {@code cost}.  If {@code dest} is
   * null, a new {@code RelativeTime} will be created in the current
   * allocation context.
   *
   * @return a reference to {@code dest}, or a newly created object if
   * {@code dest} is null.
   */
  public RelativeTime getBudget(RelativeTime dest)
  {
    return null;
  }

  /**
   * Gets the cost used in the current period so far.
   *
   * @return an new object containing the cost used in the current period.
   */
  public RelativeTime used()
  {
    return null;
  }

  /**
   * Gets the cost used in the current period so far.
   *
   * @param dest The instance to use for returning the time.  If
   *        {@code dest} is null, the result will be returned in a newly
   *        allocated object.
   *
   * @return {@code dest} containing the cost of the current period
   */
  public RelativeTime used(RelativeTime dest)
  {
    return dest;
  }

  /**
   * Gets the time still available to this constraint.
   *
   * @return the cost available in an new object.
   */
  public RelativeTime available()
  {
    return null;
  }

  /**
   * Gets the time still available to this constraint.
   *
   * @param dest It is the instance to use for returning the time.  If
   *        {@code dest} is null, the result will be returned in a newly
   *        allocated object.
   *
   * @return {@code dest} containing the available cost.
   */
  public RelativeTime available(RelativeTime dest)
  {
    return dest;
  }

  /**
   * Gets the total cost used in the last period.
   *
   * @return A new object containing the cost of the last period
   */
  public RelativeTime lastUsed()
  {
    return null;
  }

  /**
   * Gets the total cost used in the last period.
   *
   * @param dest It is the instance to use for returning the time.  If
   *        {@code dest} is null, the result will be returned in a newly
   *        allocated object.
   *
   * @return {@code dest} containing the cost of the last period
   */
  public RelativeTime lastUsed(RelativeTime dest)
  {
    return dest;
  }

  /*
   * Gets the value of {@code minimum} and returns it in the provided
   * {@link javax.realtime.RelativeTime} object.
   *
   * @param dest An instance of {@code RelativeTime} which will be set
   * to the currently configured {@code minimum}.  If {@code dest} is
   * null, a new {@code RelativeTime} will be created in the current
   * allocation context.
   *
   * @return a reference to {@code dest}, or a newly created object if
   * {@code dest} is null.
   */
  /*
  public RelativeTime getMinimumCost(RelativeTime dest)
  {
    return null;
   }
  */
  /*
   * Gets the value of {@code minimum} and returns it in a newly
   * allocated object.
   * <p>
   * Equivalent to {@code getMinimumCost(null)}.
   *
   * @return a reference to the value of {@code minimum}.
   */
  /*
  public RelativeTime getMinimumCost() { return null; }
  */
  /*
   * Sets the value of {@code minimum}.
   *
   * @param cost The new value for {@code minimum}. When
   *        {@code null}, an exception is thrown.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code minimum} is
   *          {@code null} or its time value is less than zero.
   */
  /*
  @ReturnsThis
  public ProcessingConstraint setMinimumCost(RelativeTime cost)
    throws StaticIllegalArgumentException, IllegalAssignmentError
  {
    return this;
  }
  */
  /*
   * Gets the cost underrun handler.
   *
   * @return a reference to an instance of {@link javax.realtime.AsyncEventHandler} that
   * is cost overrun handler of {@code this}.
   */
  /*
  public AsyncEventHandler getCostUnderrunHandler()
  {
    return new AsyncEventHandler();
  }
  */

  /**
   * Sets the cost underrun handler.
   *
   * @param handler This handler is invoked when the {@code run()}
   *        method of the schedulables attempts to
   *        execute for more than {@code cost} time units in any
   *        period.  When {@code null}, no handler is attached, and
   *        any previous handler is removed.
   *
   * @return {@code this}
   *
   * @throws IllegalAssignmentError when {@code handler}
   *         cannot be stored in {@code this}.
   */
  /*
  @ReturnsThis
  public ProcessingConstraint setCostUnderrunHandler(AsyncEventHandler handler)
    throws IllegalAssignmentError
  {
    return this;
  }
  */
  /**
   * Gets the cost overrun handler.
   *
   * @return a reference to an instance of {@link javax.realtime.AsyncEventHandler} that
   * is cost overrun handler of {@code this}.
   */
  public AsyncEventHandler getCostOverrunHandler()
  {
    return new AsyncEventHandler();
  }

  /**
   * Sets the cost overrun handler.
   *
   * @param handler This handler is invoked when the {@code run()}
   *        method of the schedulables attempts to
   *        execute for more than {@code cost} time units in any
   *        period.  When {@code null}, no handler is attached, and
   *        any previous handler is removed.
   *
   * @return {@code this}
   *
   * @throws IllegalAssignmentError when {@code handler}
   *         cannot be stored in {@code this}.
   */
  @ReturnsThis
  public ProcessingConstraint setCostOverrunHandler(AsyncEventHandler handler)
    throws IllegalAssignmentError
  {
    return this;
  }
}
