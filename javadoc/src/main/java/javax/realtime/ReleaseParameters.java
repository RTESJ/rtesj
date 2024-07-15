/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.Serializable;

/**
 * The top-level class for release characteristics used by {@link Schedulable}.
 * When a reference to a {@code ReleaseParameters} object is given as a
 * parameter to a constructor of a schedulable, the {@code ReleaseParameters}
 * object becomes bound to the object being created. Changes to the values in
 * the {@code ReleaseParameters} object affect the constructed object.  When
 * given to more than one constructor, then changes to the values in the
 * {@code ReleaseParameters} object affect <em>all</em> of the associated
 * objects.  Note that this is a one-to-many relationship and <em>not</em>
 * a many-to-many.
 *
 * <p>Only changes to an {@code ReleaseParameters} object caused by methods
 * on that object cause the change to propagate to all schedulables using the
 * object.  For instance, invoking {@code setDeadline} on a
 * {@code ReleaseParameters} instance will make the change, and then notify
 * the scheduler that the object has been changed.  At that point the
 * object is reconsidered for every SO that uses it.  Invoking a method on
 * the {@code RelativeTime} object that is the deadline for this object may
 * change the time value but it does not pass the new time value to the
 * scheduler at that time.  Even though the changed time value is referenced
 * by {@code ReleaseParameters} objects, it will not change the behavior of
 * the SOs that use the parameter object until a setter method on the
 * {@code ReleaseParameters} object is invoked, the parameter object is
 * used in {@code setReleaseParameters()}, or the object is used in a
 * constructor for a schedulable.
 *
 * <p> Release parameters use {@link HighResolutionTime} values for cost, and
 * deadline. Since the times are expressed as {@link HighResolutionTime}
 * values, these values use accurate timers with nanosecond granularity.  The
 * actual precision available and even the quantity the timers measure
 * depend on the clock associated with each time value.
 *
 * <p> The implementation must use modified copy semantics for each
 * {@link HighResolutionTime} parameter value.
 * The value of each time object should be treated as when it were
 * copied at the time it is passed to the parameter object, but the
 * object reference must also be retained.  For instance, the value
 * returned by {@code getCost()} must be the same object passed in by
 * setCost(), but any changes made to the time value of the cost must
 * not take effect in the associated {@code ReleaseParameters} instance
 * unless they are passed to the parameter object again, e.g. with a new
 * invocation of {@code setCost}.
 *
 * <p> The following table gives the default parameter values for the
 *  constructors.
 *  <table width="95%" border="1">
 *    <caption>ReleaseParameter Default Values</caption>
 *    <tr>
 *      <th align="center"><div><strong>Attribute</strong></div></th>
 *      <th align="center"><div><strong>Default Value</strong></div></th>
 *    </tr>
 *    <tr>
 *      <td>cost</td>
 *      <td>new RelativeTime(0,0)</td>
 *    </tr>
 *    <tr>
 *      <td>deadline</td>
 *      <td>no default</td>
 *    </tr>
 *    <tr>
 *      <td>overrunHandler</td>
 *      <td>None</td>
 *    </tr>
 *    <tr>
 *      <td>missHandler</td>
 *      <td>None</td>
 *    </tr>
 *    <tr>
 *      <td>rousable</td>
 *      <td>false</td>
 *    </tr>
 *    <tr>
 *      <td>initial event queue length</td>
 *      <td>0</td>
 *    </tr>
 *  </table>
 *
 * @rtsj.warning.sync
 *
 * <p><b>Caution:</b> The {@code cost} parameter time should be considered to be
 * measured against the target platform.
 *
 * <p><b>Note:</b> Cost measurement and enforcement is an optional facility for
 *  implementations of the RTSJ.
 */

public abstract class ReleaseParameters<T extends ReleaseParameters<T>>
  implements Cloneable, Serializable
{
  /**
   *
   */
  private static final long serialVersionUID = -8087944099435895783L;

  /**
   * A special value for cost for turning off cost monitoring.  This is just
   * a notification to the VM that the application does not require cost
   * monitoring for a give instance of {@code Schedulable}.  What the VM does
   * with it is system dependent; though, when a cost is so set, the application
   * cannot rely on any cost tracking that involves said instance.
   */
  public static final RelativeTime DISABLE_MONITORING = new RelativeTime(-1, 0);

  /**
   * Creates a new instance of {@code ReleaseParameters} with the
   * given parameter values.
   *
   * @param cost Processing time units per release.  On implementations
   *        which can measure the amount of time an instance of
   *        {@code schedulable} is executed, when null, the default
   *        value is a new instance of {@code RelativeTime(0, 0)}
   *        meaning that no cost enforcement will take place.  Setting
   *        it to {@link #DISABLE_MONITORING} disables cost monitoring as well.
   *
   * @param deadline The latest permissible completion time measured
   *        from the release time of the associated invocation of the
   *        schedulable.  There is no default for deadline in this
   *        class. The default must be determined by the subclasses.
   *
   * @param overrunHandler This handler is invoked when an invocation of
   *        the schedulable exceeds cost. In the minimum implementation
   *        overrunHandler is ignored.  When null, no application event
   *        handler is executed on cost overrun.
   *
   * @param missHandler This handler is invoked when the run() method of
   *        the schedulable is still executing after the deadline has
   *        passed.  When null, no application
   *        event handler is executed on the miss deadline condition.
   *
   * @param rousable When {@code true}, an interrupt will cause this
   *        schedulable fire immediately.
   *
   * @throws StaticIllegalArgumentException when the time value of cost is less
   *         than zero, or the time value of deadline is less than
   *         or equal to zero, or the chronograph associated with the
   *         {@code cost} or {@code deadline} parameters is not an instance
   *         of {@link Clock}.
   *
   * @throws IllegalAssignmentError when cost, deadline,
   *         overrunHandler, or missHandler cannot be stored in this.
   *
   * Since RTSJ 2.0
   */
  protected ReleaseParameters(RelativeTime cost,
                              RelativeTime deadline,
                              AsyncEventHandler overrunHandler,
                              AsyncEventHandler missHandler,
                              boolean rousable)
  {
  }


  /**
   * Creates a new instance of {@code ReleaseParameters} with the
   * given parameter values.
   *
   * @param cost Processing time units per release.  On implementations
   *        which can measure the amount of time an instance of
   *        {@code schedulable} is executed, when null, the default
   *        value is a new instance of {@code RelativeTime(0, 0)}
   *        meaning that no cost enforcement will take place.  Setting
   *        it to {@link #DISABLE_MONITORING} disables cost monitoring as well.
   *
   * @param deadline The latest permissible completion time measured
   *        from the release time of the associated invocation of the
   *        schedulable.  There is no default for deadline in this
   *        class. The default must be determined by the subclasses.
   *
   * @param overrunHandler This handler is invoked when an invocation of
   *        the schedulable exceeds cost. In the minimum implementation
   *        overrunHandler is ignored.  When null, no application event
   *        handler is executed on cost overrun.
   *
   * @param missHandler This handler is invoked when the run() method of
   *        the schedulable is still executing after the deadline has
   *        passed.  When null, no application
   *        event handler is executed on the miss deadline condition.
   *
   * @throws StaticIllegalArgumentException when the time value of cost is less
   *         than zero, or the time value of deadline is less than
   *         or equal to zero, or the chronograph associated with the
   *         {@code cost} or {@code deadline} parameters is not an instance
   *         of {@link Clock}.
   *
   * @throws IllegalAssignmentError when cost, deadline,
   *         overrunHandler, or missHandler cannot be stored in this.
   */
  protected ReleaseParameters(RelativeTime cost,
                              RelativeTime deadline,
                              AsyncEventHandler overrunHandler,
                              AsyncEventHandler missHandler)
  {
  }

  /**
   * Equivalent to {@link #ReleaseParameters(RelativeTime, RelativeTime,
   * AsyncEventHandler, AsyncEventHandler)} with the argument list
   * {@code (null, null, null, null)}.
   */
  protected ReleaseParameters()
  {
    this(null, null, null, null);
  }

  /**
   * Obtains a clone of {@code this}.  This method should behave
   * effectively as when it constructed a new object with clones of the
   * high-resolution time values of {@code this}.
   * <ul>
   * <li>The new object is in the current allocation context.</li>
   * <li> {@code clone} does not copy any associations from {@code this}
   *      and it does not implicitly bind the new object to a SO.</li>
   * <li> The new object has clones of all high-resolution time
   *      values (deep copy).</li>
   * <li> References to event handlers are copied (shallow copy.)</li>
   * </ul>
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public Object clone()
  {
    try
      {
        return super.clone();
      }
    catch (CloneNotSupportedException e)
      {
        throw new Error("Can't happen");
      }
  }

  /**
   * Determines the current value of cost.  A value of {@code RelativeTime(0,0}
   * meaning that no cost enforcement will take place; whereas a value of
   * {@link #DISABLE_MONITORING} means cost monitoring is disabled.
   *
   * @return the object last used to set the cost containing the current
   *         value of cost.
   */
  public RelativeTime getCost()
  {
    return null;
  }

  /**
   * Determines the current value of cost, where {@code Relative(0,0)} means
   * no cost enforcement in being done and {@link #DISABLE_MONITORING} means
   * cost monitoring is disabled as well.
   *
   * @param value The parameter in which to return the cost.
   *
   * @return {@code value} or, when {@code null}, the last object used
   *         to set the cost, set to the current value of cost.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime getCost(RelativeTime value)
  {
    return null;
  }

  /**
   * Gets a reference to the cost overrun handler.
   *
   * @return a reference to the associated cost overrun handler.
   */
  public AsyncEventHandler getCostOverrunHandler()
  {
    return new AsyncEventHandler();
  }

  /**
   * Determines the current value of deadline.
   *
   * @return the object last used to set the deadline containing the current
   *         value of deadline.
   */
  public RelativeTime getDeadline()
  {
    return null;
  }

  /**
   * Determines the current value of deadline.
   *
   * @param value The parameter in which to return the deadline.
   *
   * @return {@code value} or, when {@code null}, the last object used
   *         to set the deadline, set to the current value of deadline.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime getDeadline(RelativeTime value)
  {
    return null;
  }

  /**
   * Gets a reference to the deadline miss handler.
   *
   * @return a reference to the deadline miss handler.
   */
  public AsyncEventHandler getDeadlineMissHandler()
  {
    return new AsyncEventHandler();
  }


  /**
   * Sets the cost value.
   *
   * <p> When this parameter object is associated with any schedulable
   * object (by being passed through the schedulable's
   * constructor or set with a method such as
   * {@link RealtimeThread#setReleaseParameters(ReleaseParameters)}) the
   * cost of those schedulables takes effect immmediately.
   *
   *  @param cost Processing time units per release.  On implementations
   *         which can measure the amount of time a schedulable
   *         is executed, this value is the maximum amount of time a
   *         schedulable receives per release.  On
   *         implementations which cannot measure execution time,
   *         it is not possible to determine when any
   *         particular object exceeds cost.  When {@code null}, the
   *         default value is a new instance of
   *         {@code RelativeTime(0,0)}.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when the time
   *          value of {@code cost} is less than zero, or the clock
   *          associated with the {@code cost} parameters is not the
   *          realtime clock.
   *
   * @throws IllegalAssignmentError when {@code cost} cannot be
   * stored in {@code this}.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public T setCost(RelativeTime cost) { return (T)this; }

  /**
   * Sets the cost overrun handler.
   *
   * <p> When this parameter object is associated with any schedulable
   * object (by being passed through the schedulable's
   * constructor or set with a method such as {@link
   * RealtimeThread#setReleaseParameters(ReleaseParameters)}) the cost
   * overrun handler of those schedulables is effective immediately.
   *
   * @param handler This handler is invoked when an invocation of the
   *        schedulable attempts to exceed {@code cost} time
   *        units in a release.  A {@code null} value of
   *        {@code handler} signifies that no cost overrun handler
   *        should be used.
   *
   * @return {@code this}
   *
   * @throws IllegalAssignmentError when {@code handler} cannot be
   *         stored in {@code this}.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public T setCostOverrunHandler(AsyncEventHandler handler)
    throws IllegalAssignmentError
  {
    return (T)this;
  }

  /**
   * Sets the deadline value.
   *
   * <p> When this parameter object is associated with any schedulable
   * object (by being passed through the schedulable's
   * constructor or set with a method such as
   * {@link RealtimeThread#setReleaseParameters(ReleaseParameters)}) the
   * deadline of those schedulables take effect at completion.

   * @param deadline The latest permissible completion time measured
   *        from the release time of the associated invocation of the
   *        schedulable.  The default value of the deadline must
   *        be controlled by the classes that extend
   *        {@code ReleaseParameters}.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code deadline} is
   *          {@code null}, the time value of {@code deadline}
   *          is less than or equal to zero, or when the new value of this
   *          deadline is incompatible with the scheduler for any
   *          associated schedulable.
   *
   * @throws IllegalAssignmentError when {@code deadline} cannot be
   *         stored in {@code this}.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public T setDeadline(RelativeTime deadline)
  {
    return (T)this;
  }

  /**
   * Sets the deadline miss handler.
   *
   * <p> When this parameter object is associated with any schedulable
   * object (by being passed through the schedulable's
   * constructor or set with a method such as {@link
   * RealtimeThread#setReleaseParameters(ReleaseParameters)}) the
   * deadline miss handler of those schedulables take effect at completion.
   *
   *  @param handler This handler is invoked when any release of the
   *         schedulable fails to complete before the deadline
   *         passes.   A {@code null} value of
   *         {@code handler} signifies that no deadline miss
   *         handler should be used.
   *
   * @return {@code this}
   *
   * @throws IllegalAssignmentError when {@code handler} cannot be
   *         stored in {@code this}.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public T setDeadlineMissHandler(AsyncEventHandler handler)
  {
    return (T)this;
  }

  /**
   * Determines whether or not a thread interrupt will cause instances of
   * {@code Schedulable} associated with an instance of this class
   * to be prematurely released, i.e., released before the very first release
   * event happens.  It has no effect for periodic realtime threads, since
   * the first event of a timing is when start is called.  The default value,
   * i.e., before any call to {@link #setRousable(boolean)}, is {@code false}.
   *
   * <p> Note that the rousable state has no effect on instances of
   * {@code RealtimeThread} which have an instance of
   * {@code BackgroundParameters} for {@code ReleaseParameters} or
   * on ordinary event handlers, i.e., those which do not extend
   * {@link ActiveEvent}.  In the former case, there are no releases to
   * interrupt and, in the case, the handler does not have a
   * {@link ActiveEventDispatcher} to release it.
   *
   * @return {@code true} when rousable and {@code false} when not.
   *
   * @since RTSJ 2.0
   */
  public boolean isRousable()
  {
    return false;
  }

  /**
   * Dictates whether or not a thread interrupt will cause instances of
   * {@code Schedulable} associated with an instance of this class
   * to be prematurely released, i.e., released before the very first release
   * event happens.
   *
   * @param value When rousable, {@code true} and {@code false} when not.
   *
   * @return {@code this}
   *
   * @since RTSJ 2.0
   */
  @ReturnsThis
  public T setRousable(boolean value)
  {
    return (T)this;
  }

  /**
   * Gets the behavior of the arrival time queue in the event of
   * an overflow.
   *
   * @return the behavior of the arrival time queue.
   *
   * @since RTSJ 2.0
   */
  public QueueOverflowPolicy getEventQueueOverflowPolicy()
  {
    return null;
  }

  /**
   * Sets the policy for the arrival time queue for when the
   * insertion of a new element would make the queue size greater than
   * the initial size given in {@code this}.
   *
   * @param policy A queue overflow policy to use for handlers associated
   *        with {@code this}.
   *
   * @return {@code this}
   *
   * @since RTSJ 2.0
   */
  @ReturnsThis
  public T setEventQueueOverflowPolicy(QueueOverflowPolicy policy)
  {
    return (T)this;
  }

  /**
   * Gets the initial number of elements the event queue can hold.
   * This returns the initial queue length currently associated
   * with this parameter object.  When the overflow policy is
   * {@code SAVE} the initial queue length may not be related to the
   * current queue lengths of schedulables associated
   * with this parameter object.
   *
   * @return the initial length of the queue.
   *
   * @since RTSJ 2.0 replaces the subclasse method
   *  {@code AperiodicParameters.getInitialArrivalTimeQueueLength()}.
   */
  public int getInitialQueueLength() { return 0; }

  /**
   * Sets the initial number of elements the arrival time queue can hold
   * without lengthening the queue.  The initial length of an arrival
   * queue is set when the schedulable using the queue is
   * constructed, after that time changes in the initial queue length
   * are ignored.  The queue may have a length of zero, i.e., any event,
   * along with its arrival time, received during a previous release is lost.
   *
   * @param initial The initial length of the queue.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code initial} is
   *         less than zero.
   *
   * @since RTSJ 2.0 replaces the subclass method
   *  {@link AperiodicParameters#setInitialArrivalTimeQueueLength(int)}.
   */
  @ReturnsThis
  public T setInitialQueueLength(int initial) { return (T)this; }

  /**
   * This method first performs a feasibility analysis using the new
   * cost, and deadline as replacements for the matching attributes of
   * all schedulables associated with this release parameters
   * object.  When the resulting system is feasible, the method replaces
   * the current scheduling characteristics of {@code this} release
   * parameters object with the new scheduling characteristics.  The
   * change in the release characteristics, including the timing of the
   * change, of any associated schedulables will take place under
   * the control of their schedulers.
   *
   * @param cost The proposed cost.  Equivalent to
   *      {@code RelativeTime(0,0)} when {@code null}. (A new
   *      instance of {@link RelativeTime} is created in the memory area
   *      containing this {@code ReleaseParameters} instance).  When
   *      {@code null}, the default value is a new instance of
   *      {@code RelativeTime(0,0)}.
   *
   * @param deadline The proposed deadline.  There is no default for
   *        {@code deadline} in this class. The default must be
   *        determined by the subclasses.
   *
   * @return {@code true}, when the resulting system is feasible and
   *               the changes are made, and
   *         {@code false}, when the resulting system is
   *                not feasible and no changes are made.
   *
   * @throws StaticIllegalArgumentException when the time value of
   *          {@code cost} is less than zero, or
   *          the time value of {@code deadline} is less than or
   *          equal to zero.
   *
   * @throws IllegalAssignmentError when {@code cost}
   *         or {@code deadline} cannot be stored in {@code this}.
   * @deprecated as of RTSJ 2.0
   */
  @Deprecated
  public boolean setIfFeasible(RelativeTime cost,
                               RelativeTime deadline)
  {
    return true;
  }
}
