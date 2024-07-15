/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This release parameter indicates that the schedulable is
 * released on a regular basis. For an {@link AsyncEventHandler}, this
 * means the handler is either released by a periodic timer or the
 * associated event occurs periodically.  For a {@link RealtimeThread},
 * this means the {@link RealtimeThread#waitForNextRelease}
 * method will unblock the associated realtime thread at the start of
 * each period.
 *
 * <p>
 * When a reference to a {@code PeriodicParameters} object is given
 * as a parameter to a schedulable's constructor or passed as an
 * argument to one of the schedulable's setter methods, the
 * {@code PeriodicParameters} object becomes the release parameters
 * object bound to that schedulable. Changes to the values in the
 * {@code PeriodicParameters} object affect that schedulable
 * object. When bound to more than one schedulable then changes to
 * the values in the {@code PeriodicParameters} object affect
 * <em>all</em> of the associated objects. Note that this is a
 * one-to-many relationship and <em>not</em> a many-to-many.
 *
 * <p> Only a change to a {@code PeriodicParameters} object caused by
 * methods on that object cause the change to be propagate to all
 * instances of {@code Schedulable} using that parameter object.  For instance,
 * calling {@code setCost} on a {@code PeriodicParameters} object will
 * make the change, then notify the scheduler that the parameter
 * object has changed.  At that point the object is reconsidered for
 * every SO that uses it.  Invoking a method on a {@code RelativeTime}
 * object that is the cost for this object changes the cost value but
 * does not pass the change to the scheduler at that time.  That change
 * must not change the behavior of the SOs that use the parameter object
 * until a setter method on the {@code PeriodicParameters} object is
 * invoked, the parameter object is used in {@code setReleaseParameters()},
 * or it is used in a constructor for an SO.
 *
 * <p>
 * Periodic parameters use {@link HighResolutionTime} values for
 * period and start time. Since these
 * times are expressed as a {@link HighResolutionTime} values, these
 * values use accurate timers with nanosecond granularity.  The actual
 * resolution available and even the quantity the timers measure
 * depend on the clock associated with each time value.
 *
 * <p>
 * The implementation must use modified copy semantics for each
 * {@link HighResolutionTime} parameter value.
 * The value of each time object should be treated as if it were copied
 * at the time it is passed to the parameter object, but the object
 * reference must also be retained.  For instance, the value returned by
 * {@code getCost()} must be the same object passed in by
 * setCost(), but any changes made to the time value of the cost must
 * not take effect in the associated {@code PeriodicParameters}
 * instance unless they are passed to the parameter object again,
 * e.g. with a new invocation of {@code setCost}.
 *
 * <p> The following table gives the default parameter values for the
 * constructors.
 *
 * <table width="95%" border="1">
 *   <caption>PeriodicParameter Default Values</caption>
 *   <tr>
 *     <th align="center"><div><strong>Attribute</strong></div></th>
 *     <th align="center"><div><strong>Default Value</strong></div></th>
 *   </tr>
 *   <tr>
 *     <td>start</td>
 *     <td>new RelativeTime(0,0)</td>
 *   </tr>
 *   <tr>
 *     <td>period</td>
 *     <td>No default. A value must be supplied</td>
 *   </tr>
 *   <tr>
 *     <td>cost</td>
 *     <td>new RelativeTime(0,0)</td>
 *   </tr>
 *   <tr>
 *     <td>deadline</td>
 *     <td>new RelativeTime(period)</td>
 *   </tr>
 *   <tr>
 *     <td>overrunHandler</td>
 *     <td>None</td>
 *   </tr>
 *   <tr>
 *      <td>missHandler</td>
 *      <td>None</td>
 *   </tr>
 *   <tr>
 *      <td>EventQueueOverflowPolicy</td>
 *      <td>QueueOverflowPolicy.DISABLE</td>
 *    </tr>
 * </table>
 *
 * <p>
 * Periodic release parameters are strictly informational when they are
 * applied to async event handlers.  They must be used for any
 * feasibility analysis, but release of the async event handler is not
 * entirely controlled by the scheduler.
 *
 * @rtsj.warning.sync
 */
public class PeriodicParameters extends ReleaseParameters<PeriodicParameters>
{
  /**
   *
   */
  private static final long serialVersionUID = -7460570596769412084L;

  /**
   * Creates a {@code PeriodicParameters} object with attributes set to
   * the specified values.
   *
   * @param start Time at which the first release begins (i.e. the
   *         realtime thread becomes eligible for execution.) When a
   *         {@code RelativeTime}, this time is relative to the
   *         first time the thread becomes activated (that is, when
   *         {@code start()} is called).  When an
   *         {@code AbsoluteTime}, then the first release is the
   *         maximum of the start parameter and the time of the call to
   *         the associated RealtimeThread.start() method (modified
   *         according to any phasing policy).  When null, the default
   *         value is a new instance of {@code RelativeTime(0,0)}.
   *
   * @param period The period is the interval between successive
   *        releases.  There is no default value.  When {@code period}
   *        is null an exception is thrown.
   *
   * @param cost Processing time per release.  On implementations which
   *        can measure the amount of time a schedulable is
   *        executed, this value is the maximum amount of time a
   *        schedulable receives per release.  When null, the
   *        default value is a new instance of
   *        {@code RelativeTime(0,0)}.
   *
   * @param deadline The latest permissible completion time measured
   *        from the release time of the associated invocation of the
   *        schedulable.  When null, the default value is new
   *        instance of {@code RelativeTime(period)}.
   *
   * @param overrunHandler This handler is invoked when an invocation of
   *        the schedulable exceeds cost in the given release.
   *        Implementations may ignore this parameter.  When null, the
   *        default value is no overrun handler.
   *
   * @param missHandler This handler is invoked when the
   *        {@code run()} method of the schedulable is still
   *        executing after the deadline has passed.  When null, the
   *        default value is no deadline miss handler.
   *
   * @param rousable When {@code true}, an interrupt will cause an early
   *        release, otherwise not.
   *
   * @throws StaticIllegalArgumentException when the {@code period} is
   *         {@code null} or its time value is not greater than zero, or
   *         when the time value of {@code cost} is less than zero, or when
   *         the time value of {@code deadline} is not greater than
   *         zero, or when the clock associated with the {@code cost}
   *         is not the realtime clock, or when the clocks associated with
   *         the {@code deadline} and {@code period} parameters are not
   *         the same.
   *
   * @throws IllegalAssignmentError when {@code start}
   *         {@code period}, {@code cost},
   *         {@code deadline}, {@code overrunHandler} or
   *         {@code missHandler} cannot be stored in
   *         {@code this}.
   *
   * @since RTSJ 2.0
   */
  public PeriodicParameters(HighResolutionTime<?> start,
                            RelativeTime period,
                            RelativeTime cost,
                            RelativeTime deadline,
                            AsyncEventHandler overrunHandler,
                            AsyncEventHandler missHandler,
                            boolean rousable)
  {
    super(cost, deadline, overrunHandler, missHandler);
  }

  /**
   * Equivalent to {@link #PeriodicParameters(HighResolutionTime,
   * RelativeTime, RelativeTime, RelativeTime, AsyncEventHandler,
   * AsyncEventHandler, boolean)} with the argument list
   * {@code (start, period, cost, deadline, overrunHandler, missHandler, false)};
   */
  public PeriodicParameters(HighResolutionTime<?> start,
                            RelativeTime period,
                            RelativeTime cost,
                            RelativeTime deadline,
                            AsyncEventHandler overrunHandler,
                            AsyncEventHandler missHandler)
  {
    this(start, period, cost, deadline, overrunHandler, missHandler, false);
  }

  /**
   * Equivalent to {@link #PeriodicParameters(HighResolutionTime,
   * RelativeTime, RelativeTime, RelativeTime, AsyncEventHandler,
   * AsyncEventHandler, boolean)} with the argument list
   * {@code (start, period, deadline, null, null, missHandler, rousable)};
   *
   * @since RTSJ 2.0
   */
  public PeriodicParameters(HighResolutionTime<?> start,
                            RelativeTime period,
                            RelativeTime deadline,
                            AsyncEventHandler missHandler,
                            boolean rousable)
  {
    this(start, period, null, deadline, null, missHandler, rousable);
  }

  /**
   * Equivalent to {@link #PeriodicParameters(HighResolutionTime,
   * RelativeTime, RelativeTime, RelativeTime, AsyncEventHandler,
   * AsyncEventHandler, boolean)} with the argument list
   * {@code (start, period, null, null, null, null, false)};
   *
   * @since RTSJ 1.0.1
   */
  public PeriodicParameters(HighResolutionTime<?> start, RelativeTime period)
  {
    this(start, period, null, null, null, null, false);
  }

  /**
   * Creates a {@code PeriodicParameters} object with the
   *  specified period and all other attributes set to their default values.
   * This constructor has the same effect as invoking
   * {@code PeriodicParameters(null, period, null, null, null, null, false)}
   *
   * @since RTSJ 1.0.1
   */
  public PeriodicParameters(RelativeTime period)
  {
    this(null, period, null, null, null, null, false);
  }

  /**
   * Determines the current value of period.
   *
   * @return the object last used to set the period containing the current
   *         value of period.
   */
  public RelativeTime getPeriod()
  {
    return null;
  }

  /**
   * Determines the current value of period.
   *
   * @return {@code value} or, when {@code null}, the last object used
   *         to set the period, set to the current value of period.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime getPeriod(RelativeTime value)
  {
    return null;
  }

  /**
   * Determines the time used to start an instance of {@code Schedulable},
   * which is not necessarily the time at which it actually started.
   *
   * @return the object last used to set the start containing the current
   *         value of start.
   */
  public HighResolutionTime<?> getStart()
  {
    return null;
  }

  /**
   * Sets the period.
   *
   * @param period The value to which {@code period} is set.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when the given period is
   *          {@code null} or its time value is not greater than
   *          zero.   Also when {@code period} is incompatible
   *          with the scheduler for any associated schedulable
   *          or when an associated {@link AsyncBaseEventHandler} is
   *          associated with a {@link Timer} whose period does not
   *          match {@code period}.
   *
   * @throws IllegalAssignmentError when {@code period} cannot be
   *         stored in {@code this}.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public PeriodicParameters setPeriod(RelativeTime period) { return this; }

  /**
   * Sets the start time.
   *
   * <p>
   * Changes to the start time in a realtime thread’s
   * PeriodicParameters object only have an effect on its initial release time.
   *
   * <p>
   * Note that an instance of {@code PeriodicParameters} may
   * be shared by several schedulables.  A change to the start
   * time may take effect on a subset of these schedulables.
   * That leaves the start time returned by
   * {@code getStart} unreliable as a way to determine the start time of a
   * schedulable.
   *
   * @param start The new start time.  When {@code null}, the default
   *        value is a new instance of {@code RelativeTime(0,0)}.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when the given start time is
   *         incompatible with the scheduler for any of the schedulable
   *         objects which are presently using this parameter object.
   *
   * @throws IllegalAssignmentError when {@code start}
   *         cannot be stored in {@code this}.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public PeriodicParameters setStart(HighResolutionTime<?> start)
  {
    return this;
  }

  /**
   * This method first performs a feasibility analysis using the new period,
   * cost and deadline attributes as replacements for the matching attributes
   * of {@code this}. When the resulting system is feasible the method
   * replaces the current attributes of {@code this}.  When {@code this} parameter object
   * is associated with any schedulable, either by being passed through
   * the schedulable's constructor or set with a method such as
   * {@link RealtimeThread#setReleaseParameters(ReleaseParameters)}, the
   * parameters of those schedulables are altered as specified by
   * each schedulable's respective scheduler.
   *
   * @param period The proposed period.  There is no default value.  When
   *      {@code period} is {@code null} an exception is thrown.
   *
   * @param cost The proposed cost.  When {@code null}, the default
   *        value is a new instance of {@code RelativeTime(0,0)}.
   *
   * @param deadline The proposed deadline.  When {@code null}, the
   *        default value is new instance of
   *        {@code RelativeTime(period)}.
   *
   * @return {@code true}, when the resulting system is feasible and the changes
   *         are made.  {@code False}, when the resulting system is not feasible
   *         and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the {@code period}
   *          is {@code null} or its time value is not greater
   *          than zero, or when the time value of {@code cost}
   *          is less than zero, or when the time value of
   *          {@code deadline} is not greater than zero.
   *          Also when the values are incompatible with the
   *          scheduler for any of the schedulables which are
   *          presently using this parameter object.
   *
   * @throws IllegalAssignmentError when {@code period}, {@code cost},
   *         or {@code deadline} cannot be stored in {@code this}.
   *
   * @deprecated as of RTSJ 2.0; the framework for feasibility analysis
   *             is inadequate
   */
  @Deprecated
  public boolean setIfFeasible(RelativeTime period,
                               RelativeTime cost,
                               RelativeTime deadline)
  {
    return true;
  }
}
