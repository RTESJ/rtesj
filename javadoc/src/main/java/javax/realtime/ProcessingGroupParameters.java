/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.Serializable;

/**
 * This is associated with one or more schedulables for which the
 * system guarantees that the associated objects will not be given more
 * time per period than indicated by {@code cost} (budget).  The
 * motivation for this class is to allow the execution demands of one or
 * more aperiodic schedulables to be bound.  However, periodic or sporadic
 * schedulables can also be associated with a processing group.
 *
 *<p> Processing groups have an associated affinity set that must
 * contain only a single processor.
 *
 * <p> For all schedulables with a reference to an instance of
 * {@code ProcessingGroupParameters} {@code p} no more than
 * {@code p.cost} will be allocated to the execution of these
 * schedulables on the processor associated with its processing
 * group in each interval of time given by {@code p.period} after
 * the time indicated by {@code p.start}. No execution of the
 * schedulables will be allowed on any processor other than this
 * processor.  When there is no intersection between the a schedulable
 * object's affinity set and its processing group's affinity set, then
 * the schedulable execution is constrained by the default
 * processing group's affinit set. <p>
 *
 * Logically a virtual server is associated with each instance of
 * {@code ProcessingGroupParameters}. This server has a start time,
 * a period, a cost (budget) and a deadline.  The server can only
 * logically execute when (a) it has not consumed more execution time in
 * its current release than the cost parameter, and (b) one of its
 * associated schedulables is executable and is the most eligible
 * of the executable schedulables. When the server is logically
 * executable, the associated schedulable is executed.
 *
 * When the cost has been consumed, any {@code overrunHandler} is
 * released, and the server is not eligible for logical execution until
 * its next period is due. At this point, its allocated cost is
 * replenished. When the server is logically executing when its deadline
 * expires, any associated {@code missHandler} is released.
 *
 * The deadline and cost parameters of all the associated schedulable
 * objects have the same impact as they would if the objects were not
 * bound to a processing group.
 *
 * <p> Processing group parameters use {@link HighResolutionTime} values
 * for cost, deadline, period and start time. Since those times are
 * expressed as a {@link HighResolutionTime}, the values use accurate
 * timers with nanosecond granularity.  The actual resolution available
 * and even the quantity it measures depends on the clock associated
 * with each time value.
 *
 * <p> When a reference to a {@code ProcessingGroupParameters}
 * object is given as a parameter to a schedulable's constructor
 * or passed as an argument to one of the schedulable's setter
 * methods, the {@code ProcessingGroupParameters} object becomes
 * the processing group parameters object bound to that schedulable
 * object. Changes to the values in the
 * {@code ProcessingGroupParameters} object affect that schedulable
 * object. When bound to more than one schedulable then changes to
 * the values in the {@code ProcessingGroupParameters} object
 * affect <em>all</em> of the associated objects. Note that this is a
 * one-to-many relationship and <em>not</em> a many-to-many.
 *
 * <p> The implementation must use modified copy semantics for each
 * {@link HighResolutionTime} parameter value.  The value of each time
 * object should be treated as if it were copied at the time it is
 * passed to the parameter object, but the object reference must also be
 * retained.
 * <p>
 * Only changes to a {@code ProcessingGroupParameters} object
 * caused by methods on that object are immediately visible to the
 * scheduler.  For instance, invoking {@code setPeriod()} on a
 * {@code ProcessingGroupParameters} object will make the change,
 * then notify the scheduler that the parameter object has changed.
 * At that point the scheduler's view of the processing group parameters
 * object is updated.  Invoking a method on the
 * {@code RelativeTime} object that is the period for this object
 * may change the period but it does not pass the change to the
 * scheduler at that time.  That new value for period must not change
 * the behavior of the SOs that use the parameter object until a setter
 * method on the {@code ProcessingGroupParameters} object is
 * invoked, or the parameter object is used in
 * {@code setProcessingGroupParameters()} or a constructor for an
 * SO.
 *
 * <p> The implementation may use copy semantics for each
 * {@code HighResolutionTime} parameter value.  For instance the
 * value returned by {@code getCost()} must be {@code equal}
 * to the value passed in by {@code setCost}, but it need not be
 * the same object.
 *
 * <p> The following table gives the default parameter values for the constructors.
 *
 *
 *  <table width="95%" border="1">
 *   <caption>ProcessingGroupParameter Default Values</caption>
 *  <tr>
 *    <th align="center"><div><strong>Attribute</strong></div></th>
 *    <th align="center"><div><strong>Default Value</strong></div></th>
 *  </tr>
 *  <tr>
 *    <td>start</td>
 *    <td>new RelativeTime(0,0)</td>
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
 *    <td>deadline</td>
 *    <td>new RelativeTime(period)</td>
 *  </tr>
 *  <tr>
 *    <td>overrunHandler</td>
 *    <td>None</td>
 *  </tr>
 *  <tr>
 *    <td>missHandler</td>
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
 * @deprecated as of RTSJ 2.0; replaced by
 * {@link javax.realtime.enforce.ProcessingConstraint}.
 */
@Deprecated
public class ProcessingGroupParameters  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -208751427006776600L;

  /**
   * Creates a {@code ProcessingGroupParameters} object.
   *
   * @param start Time at which the first period begins.  When a
   *        {@code RelativeTime}, this time is relative to the
   *        creation of {@code this}. When an {@code AbsoluteTime}, then the
   *        first release of the logical server is at the start time (or
   *        immediately when the absolute time is in the past).  When
   *        {@code null}, the default value is a new instance of
   *        {@code RelativeTime(0,0)}.
   *
   * @param period The period is the interval between successive
   *        replenishment of the logical server's associated cost
   *        budget.  There is no default value.  When {@code period}
   *        is {@code null} an exception is thrown.
   *
   * @param cost Processing time per period. The budget CPU time that
   *        the logical server can consume each period.  When
   *        {@code null}, an exception is thrown.
   *
   * @param deadline The latest permissible completion time measured
   *        from the start of the current period.  Changing the deadline
   *        might not take effect after the expiration of the current
   *        deadline.  Specifying a deadline less than the period
   *        constrains execution of all the members of the group to the
   *        beginning of each period.  When {@code null}, the default
   *        value is new instance of {@code RelativeTime(period)}.
   *
   * @param overrunHandler This handler is invoked when any schedulable
   *        object member of this processing group attempts to use
   *        processor time beyond the group's budget.  When
   *        {@code null}, no application async event handler is
   *        fired on the overrun condition.
   *
   * @param missHandler This handler is invoked when the logical server is
   *        still executing after the deadline has passed.  When
   *        {@code null}, no application async event handler is
   *        fired on the deadline miss condition.
   *
   * @throws IllegalArgumentException when the {@code period}
   *         is {@code null} or its time value is not greater than
   *         zero, when {@code cost} is {@code null}, or when the
   *         time value of {@code cost} is less than zero, when
   *         {@code start} is an instance of
   *         {@code RelativeTime} and its value is negative, or when
   *         the time value of {@code deadline} is not greater than
   *         zero and less than or equal to the {@code period}.  When
   *         the implementation does not support processing group
   *         deadline less than period, {@code deadline} less than
   *         {@code period} will cause
   *         {@code IllegalArgumentException} to be thrown.
   *
   * @throws IllegalAssignmentError when
   *            {@code start},
   *            {@code period},
   *            {@code cost},
   *            {@code deadline},
   *            {@code overrunHandler} or
   *            {@code missHandler}
   * cannot be stored in {@code this}.
   */
  public ProcessingGroupParameters(HighResolutionTime<?> start,
                                   RelativeTime period,
                                   RelativeTime cost,
                                   RelativeTime deadline,
                                   AsyncEventHandler overrunHandler,
                                   AsyncEventHandler missHandler)
    throws IllegalArgumentException, IllegalAssignmentError
  {
  }

  /**
   * Creates a clone of {@code this}.  This method should behave
   * effectively as if it constructed a new object with clones of the
   * high-resolution time values of {@code this}.
   *
   * <ul>
   * <li>The new object is in the current allocation context.</li>
   * <li> {@code clone} does not copy any associations from
   * {@code this} and it does not implicitly bind the new object to a SO.
   * </li>
   * <li> The new object has clones of all high-resolution time values
   * (deep copy).</li>
   * <li> References to event handlers are copied (shallow copy.)
   * </ul>
   *
   * @return the clone of {@code this}
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public Object clone()
  {
    return null;
  }

  /**
   * Gets the value of {@code cost}.
   *
   * @return a reference to the value of {@code cost}.
   */
  public RelativeTime getCost() { return null; }

  /**
   * Gets the cost overrun handler.
   *
   * @return a reference to an instance of {@link AsyncEventHandler} that
   * is cost overrun handler of {@code this}.
   */
  public AsyncEventHandler getCostOverrunHandler()
  {
    return new AsyncEventHandler();
  }

  /**
   * Gets the value of {@code deadline}.
   *
   * @return a reference to an instance of {@link RelativeTime} that is
   * the deadline of {@code this}.
   */
  public RelativeTime getDeadline()
  {
    return new RelativeTime();
  }

  /**
   * Gets the deadline miss handler.
   *
   * @return a reference to an instance of {@link AsyncEventHandler}that
   * is deadline miss handler of {@code this}.
   */
  public AsyncEventHandler getDeadlineMissHandler()
  {
    return new AsyncEventHandler();
  }

  /**
   * Gets the value of {@code period}.
   *
   * @return a reference to an instance of {@link RelativeTime} that
   * represents the value of {@code period}.
   */
  public RelativeTime getPeriod()
  {
    return new RelativeTime();
  }

  /**
   * Gets the value of {@code start}.  This is the value that
   * was specified in the constructor or by {@code setStart()},
   * not the actual absolute time the corresponding to the start of the
   * processing group.
   *
   * @return a reference to an instance of {@link HighResolutionTime}
   * that represents the value of {@code start}.
   */
  public HighResolutionTime<?> getStart()
  {
    return null;
  }

  /**
   * Sets the value of {@code cost}.
   *
   * @param cost The new value for {@code cost}. When
   *        {@code null}, an exception is thrown.
   *
   * @throws IllegalArgumentException when {@code cost} is
   *          {@code null} or its time value is less than zero.
   *
   * @throws IllegalAssignmentError when {@code cost}
   *         cannot be stored in {@code this}.
   */
  public void setCost(RelativeTime cost)
    throws IllegalArgumentException, IllegalAssignmentError
  {
  }

  /**
   * Sets the cost overrun handler.
   *
   * @param handler This handler is invoked when the {@code run()}
   *        method of and of the the schedulables attempt to
   *        execute for more than {@code cost} time units in any
   *        period.  When {@code null}, no handler is attached, and
   *        any previous handler is removed.
   *
   * @throws IllegalAssignmentError when {@code handler}
   *         cannot be stored in {@code this}.
   */
  public void setCostOverrunHandler(AsyncEventHandler handler)
    throws IllegalAssignmentError
  {
  }

  /**
   * Sets the value of {@code deadline}.
   *
   * @param deadline The new value for {@code deadline}.  When
   *        {@code null}, the default value is new instance of
   *        {@code RelativeTime(period)}.
   *
   * @throws IllegalArgumentException when {@code deadline}
   *         has a value less than zero or greater than the period.
   *         Unless the implementation supports deadline less than
   *         period in processing groups,
   *         {@code IllegalArgumentException} is also when
   *         {@code deadline} is less than the period.
   *
   * @throws IllegalAssignmentError when {@code deadline}
   *         cannot be stored in {@code this}.
   */
  public void setDeadline(RelativeTime deadline)
    throws IllegalArgumentException, IllegalAssignmentError
  {
  }

  /**
   * Sets the deadline miss handler.
   *
   * @param handler This handler is invoked when the {@code run()}
   *        method of any of the schedulables still expect to
   *        execute after the deadline has passed.  When
   *        {@code null}, no handler is attached, and any previous
   *        handler is removed.
   *
   * @throws IllegalAssignmentError when {@code handler}
   *         cannot be stored in {@code this}.
   */
  public void setDeadlineMissHandler(AsyncEventHandler handler)
    throws IllegalAssignmentError
  {
  }

  /**
   * This method first performs a feasibility analysis using the period,
   * cost and deadline attributes as replacements for the matching
   * attributes {@code this}. When the resulting system is feasible
   * the method replaces the current attributes of this with the new
   * attributes.
   *
   * @param period The proposed period.  There is no default value.  When
   *        {@code period} is {@code null} an exception is
   *        thrown.
   *
   * @param cost The proposed cost. When {@code null}, an exception
   *        is thrown.
   *
   * @param deadline The proposed deadline.  When {@code null}, the
   *        default value is new instance of {@code RelativeTime(period)}.
   *
   * @return {@code true}, when the resulting system is feasible and
   *         the changes are made. {@code False}, when the resulting system is
   *         not feasible and no changes are made.
   *
   * @throws IllegalArgumentException when the {@code period}
   *         is {@code null} or its time value is not greater than
   *         zero, or when the time value of {@code cost} is less
   *         than zero, or when the time value of {@code deadline} is
   *         not greater than zero.
   *
   * @throws IllegalAssignmentError when
   *            {@code period},
   *            {@code cost}, or
   *            {@code deadline}
   * cannot be stored in {@code this}.
   */
  public boolean setIfFeasible(RelativeTime period,
                               RelativeTime cost,
                               RelativeTime deadline)
    throws IllegalArgumentException, IllegalAssignmentError
  {
    return true;
  }

  /**
   * Sets the value of {@code period}.
   *
   * @param period The new value for {@code period}.  There is no
   *        default value.  When {@code period} is {@code null}
   *        an exception is thrown.
   *
   *  @throws IllegalArgumentException when {@code period} is
   *      {@code null}, or its time value is not greater than zero.
   *      When the implementation does not support processing group
   *      deadline less than period, and {@code period} is not
   *      equal to the current value of the processing group's deadline,
   *      the deadline is set to a clone of {@code period} created
   *      in the same memory area as {@code period}.
   *
   * @throws IllegalAssignmentError when {@code period}
   *         cannot be stored in {@code this}.
   */
  public void setPeriod(RelativeTime period)
    throws IllegalArgumentException, IllegalAssignmentError
  {
  }

  /**
   * Sets the value of {@code start}.  When the processing group is
   * already started this method alters the value of this object's start
   * time property, but has no other effect.
   *
   * @param start The new value for {@code start}.  When
   *        {@code null}, the default value is a new instance of
   *        {@code RelativeTime(0,0)}.
   *
   * @throws IllegalAssignmentError when {@code start}
   *         cannot be stored in {@code this}.
   *
   * @throws IllegalArgumentException when {@code start} is a
   *         relative time value and less than zero.
   */
  public void setStart(HighResolutionTime<?> start)
    throws IllegalArgumentException, IllegalAssignmentError
  {
  }
}
