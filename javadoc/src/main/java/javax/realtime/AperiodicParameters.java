/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * <p> When a reference to an {@code AperiodicParameters} object is
 * given as a parameter to a schedulable's constructor or passed
 * as an argument to one of the schedulable's setter methods, the
 * {@code AperiodicParameters} object becomes the release
 * parameters object bound to that schedulable. Changes to the
 * values in the {@code AperiodicParameters} object affect that
 * schedulable.  When bound to more than one schedulable,
 * changes to the values in the {@code AperiodicParameters} object
 * affect <em>all</em> of the associated objects. Note that this is a
 * one-to-many relationship and <em>not</em> a many-to-many.
 *
 * <p> Only changes to an {@code AperiodicParameters} object caused
 * by methods on that object cause the change to propagate to all
 * schedulables using the object.  For instance, calling
 * {@code setCost} on an {@code AperiodicParameters} object
 * will make the change, then notify the scheduler that the
 * parameter object has changed.  At that point the object is
 * reconsidered for every schedulable that uses it.  Invoking a
 * method on the {@code RelativeTime} object that is the cost for
 * this object may change the cost but it does not pass the change to
 * the scheduler at that time.  That change must not change the behavior
 * of the schedulables that use the parameter object until a
 * setter method on the {@code AperiodicParameters} object is
 * invoked, the parameter object is used in
 * {@code setReleaseParameters()}, or it is used in a constructor for a
 * schedulable.
 *
 * <p> The implementation must use modified copy semantics for each
 * {@link HighResolutionTime} parameter value.  The value of each time
 * object should be treated as if it were copied at the time it is
 * passed to the parameter object, but the object reference must also be
 * retained.  For instance, the value returned by {@code getCost()}
 * must be the same object passed in by setCost(), but any changes made
 * to the time value of the cost must not take effect in the associated
 * {@code AperiodicParameters} instance unless they are passed to
 * the parameter object again, e.g. with a new invocation of
 * {@code setCost}.
 *
 * <p>Correct initiation of the deadline miss and cost overrun handlers
 * requires that the underlying system know the arrival time of each
 * aperiodic task. For an instance of {@link RealtimeThread} the
 * arrival time is the time at which the {@code start()} is invoked.
 * For other instances of {@link Schedulable}, the required behavior may
 * force the implementation to act effectively as if it maintained
 * a queue of arrival times.
 *
 * <p>When the release parameters for a {@code RealtimeThread} are set to an
 * instance of this class or one of its subclasses, the thread does not start
 * executing code until the {@link RealtimeThread#release()} method is called.
 *
 * <p> The following table gives the default values for the constructors
 * parameters.
 *
 * <table border="1">
 *   <caption>AperiodicParameters Default Values</caption>
 *   <tr>
 *     <th align="center"><div><strong>Attribute</strong></div></th>
 *     <th align="center"><div><strong>Value</strong></div></th>
 *   </tr>
 *   <tr>
 *     <td>cost</td>
 *     <td>{@code new RelativeTime(0,0)}</td>
 *   </tr>
 *   <tr>
 *     <td>deadline</td>
 *     <td>{@code new RelativeTime(Long.MAX_VALUE, 999999)}</td>
 *   </tr>
 *   <tr>
 *     <td>overrunHandler</td>
 *     <td>None</td>
 *   </tr>
 *   <tr>
 *     <td>missHandler</td>
 *     <td>None</td>
 *   </tr>
 *   <tr>
 *     <td>rousable</td>
 *     <td>false</td>
 *   </tr>
 *   <tr>
 *     <td>Arrival time queue size</td>
 *     <td>0</td>
 *   </tr>
 *   <tr>
 *     <td>Queue overflow policy</td>
 *     <td>SAVE</td>
 *   </tr>
 * </table>
 *
 * @rtsj.warning.sync
 */
public class AperiodicParameters extends ReleaseParameters<AperiodicParameters>
{
  /**
   * The standard serialization id for this class.
   */
  private static final long serialVersionUID = -277282014893485707L;

  /**
   * Represents the ``EXCEPT'' policy for dealing with arrival time queue
   * overflow. Under this policy, when an arrival occurs and its time
   * should be queued but the queue already holds a number of times
   * equal to the initial queue length defined by {@code this} then
   * the {@code fire()} method shall throw a
   * {@link ArrivalTimeQueueOverflowException}. Any other associated
   * semantics are governed by the schedulers for the schedulables using
   * these aperiodic parameters.  When the arrival is a result of a
   * happening to which the instance of {@link AsyncEventHandler} is
   * bound then the arrival time is ignored.
   *
   * @since RTSJ 1.0.1 Moved here from {@code SporadicParameters}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static final String arrivalTimeQueueOverflowExcept =
    QueueOverflowPolicy.EXCEPT.name();

  /**
   * Represents the ``IGNORE'' policy for dealing with arrival time queue
   * overflow. Under this policy, when an arrival occurs and its time
   * should be queued, but the queue already holds a number of times
   * equal to the initial queue length defined by {@code this} then
   * the arrival is ignored.  Any other associated semantics are
   * governed by the schedulers for the schedulables using these
   * aperiodic parameters.
   *
   * @since RTSJ 1.0.1 Moved here from {@code SporadicParameters}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static final String arrivalTimeQueueOverflowIgnore =
    QueueOverflowPolicy.IGNORE.name();

  /**
   * Represents the ``REPLACE'' policy for dealing with arrival time queue
   * overflow. Under this policy, when an arrival occurs and should be
   * queued but the queue already holds a number of times equal to the
   * initial queue length defined by {@code this} then the
   * information for this arrival replaces a previous arrival.  Any
   * other associated semantics are governed by the schedulers for the
   * schedulables using these aperiodic parameters.
   *
   * @since RTSJ 1.0.1 Moved here from {@code SporadicParameters}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static final String arrivalTimeQueueOverflowReplace =
    QueueOverflowPolicy.REPLACE.name();

  /**
   * Represents the ``SAVE'' policy for dealing with arrival time queue
   * overflow. Under this policy, when an arrival occurs and should be
   * queued but the queue is full, then the queue is lengthened and the
   * arrival time is saved.  Any other associated semantics are governed
   * by the schedulers for the schedulables using these aperiodic
   * parameters.
   *
   * <p>This policy does not update the ``initial queue
   * length;'' it alters the actual queue length.  Since the
   * {@code SAVE} policy grows the arrival time queue as necessary
   * for the {@code SAVE} policy, the initial queue length is only
   * an optimization.
   *
   *  @since RTSJ 1.0.1 Moved here from {@code SporadicParameters}.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static final String arrivalTimeQueueOverflowSave =
    QueueOverflowPolicy.SAVE.name();

  /** Creates an {@code AperiodicParameters} object.
   *
   * @param cost Processing time per invocation.  On implementations
   *              which can measure the amount of time a schedulable
   *              object is executed, this value is the maximum amount
   *              of time a schedulable receives.  On
   *              implementations which cannot measure execution time,
   *              it is not possible to
   *              determine when any particular object exceeds cost.  When
   *              {@code null}, the default value is a new instance of
   *              {@code RelativeTime(0,0)}.
   *
   * @param deadline The latest permissible completion time measured
   *                   from the release time of the associated
   *                   invocation of the schedulable.  When
   *                   {@code null}, the default value is a new
   *                   instance of
   *                   {@code RelativeTime(Long.MAX_VALUE, 999999)}.
   *
   *
   * @param overrunHandler This handler is invoked when an invocation of
   *                        the schedulable exceeds cost.  Not
   *                        required for minimum implementation.  When
   *                        {@code null}, the default value is no overrun
   *                        handler.
   *
   * @param missHandler This handler is invoked when the
   *                     {@code run()} method of the schedulable
   *                     object is still executing after the deadline
   *                     has passed.  When {@code null}, the default
   *                     value is no miss handler.
   *
   * @param rousable determines whether or not an instance of
   *        {@code Schedulable} can be prematurely released by a thread
   *        interrupt.
   *
   * @throws StaticIllegalArgumentException when the time value of
   *         {@code cost} is less than zero,  or the time
   *         value of {@code deadline} is less than or equal to
   *         zero.
   *
   * @throws IllegalAssignmentError when {@code cost},
   *      {@code deadline}, {@code overrunHandler} or
   *      {@code missHandler} cannot be stored in
   *      {@code this}.
   *
   *  @since RTSJ 2.0
   */
  public AperiodicParameters(RelativeTime cost,
                             RelativeTime deadline,
                             AsyncEventHandler overrunHandler,
                             AsyncEventHandler missHandler,
                             boolean rousable)
  {
    super(cost, deadline, overrunHandler, missHandler);
    setRousable(rousable);
  }


  /**
   * Equivalent to {@link #AperiodicParameters(RelativeTime, RelativeTime,
   * AsyncEventHandler, AsyncEventHandler, boolean)} with the argument list
   * {@code (cost, deadline, overrunHandler, missHandler, false)}.
   *
   * @param cost Processing time per invocation.  On implementations
   *              that support cost enforcement, this value is the maximum amount
   *              of time a schedulable receives.  On
   *              implementations which do not support cost enforcement,
   *              it is not possible to
   *              determine when any particular object exceeds cost.  When
   *              {@code null}, the default value is a new instance of
   *              {@code RelativeTime(0,0)}.
   *
   * @param deadline The latest permissible completion time measured
   *                   from the release time of the associated
   *                   invocation of the schedulable.  When
   *                   {@code null}, the default value is a new
   *                   instance of
   *                   {@code RelativeTime(Long.MAX_VALUE, 999999)}.
   *
   *
   * @param overrunHandler This handler is invoked when an invocation of
   *                        the schedulable exceeds cost.  Not
   *                        required for minimum implementation.  When
   *                        {@code null}, the default value is no overrun
   *                        handler.
   *
   * @param missHandler This handler is invoked when the
   *                     {@code run()} method of the schedulable
   *                     object is still executing after the deadline
   *                     has passed.  When {@code null}, the default
   *                     value is no miss handler.
   *
   * @throws StaticIllegalArgumentException when the time value of
   *         {@code cost} is less than zero,  or the time
   *         value of {@code deadline} is less than or equal to
   *         zero.
   *
   * @throws IllegalAssignmentError when {@code cost},
   *      {@code deadline}, {@code overrunHandler} or
   *      {@code missHandler} cannot be stored in
   *      {@code this}.
   */
  public AperiodicParameters(RelativeTime cost,
                             RelativeTime deadline,
                             AsyncEventHandler overrunHandler,
                             AsyncEventHandler missHandler)
  {
    super(cost, deadline, overrunHandler, missHandler);
  }


  /**
   * Equivalent to {@link #AperiodicParameters(RelativeTime, RelativeTime,
   * AsyncEventHandler, AsyncEventHandler, boolean)} with the argument list
   * {@code (null, deadline, null, missHandler, rousable)}.
   *
   * @since RTSJ 2.0
   */
  public AperiodicParameters(RelativeTime deadline,
                             AsyncEventHandler missHandler,
                             boolean rousable)
  {
    this(null, deadline, null, missHandler, rousable);
  }


  /**
   * Equivalent to {@link #AperiodicParameters(RelativeTime, RelativeTime,
   * AsyncEventHandler, AsyncEventHandler, boolean)} with the argument list
   * {@code (null, deadline, null, null, false)}.
   *
   * @since RTSJ 2.0
   */
  public AperiodicParameters(RelativeTime deadline)
  {
    this(null, deadline, null, null, false);
  }


  /**
   * Equivalent to {@link #AperiodicParameters(RelativeTime, RelativeTime,
   * AsyncEventHandler, AsyncEventHandler, boolean)} with the argument list
   * {@code (null, null, null, null, false)}.
   *
   * @since RTSJ 1.0.1
   */
  public AperiodicParameters()
  {
    this(null, null, null, null, false);
  }


  /**
   * Gets the initial number of elements the arrival time queue can hold.
   * This returns the initial queue length currently associated
   * with this parameter object.  When the overflow policy is
   * {@code SAVE} the initial queue length may not be related to the
   * current queue lengths of schedulables associated
   * with this parameter object.
   *
   * @return The initial length of the queue.
   *
   * @since RTSJ 1.0.1 Moved here from {@code SporadicParameters}.
   *
   * @deprecated since RTSJ 2.0 replaced by
   *  {@link ReleaseParameters#getInitialQueueLength()}.
   */
  @Deprecated
  public int getInitialArrivalTimeQueueLength() { return 0; }


  /**
   * Sets the initial number of elements the arrival time queue can hold
   * without lengthening the queue.  The initial length of an arrival
   * queue is set when the schedulable using the queue is
   * constructed, after that time changes in the initial queue length
   * are ignored.
   *
   * @param initial The initial length of the queue.
   *
   * @throws StaticIllegalArgumentException when {@code initial} is
   *         less than zero.
   *
   * @since RTSJ 1.0.1 Moved here from {@code SporadicParameters}.
   *
   * @deprecated since RTSJ 2.0 replaced by
   *  {@link ReleaseParameters#setInitialQueueLength(int initial)}.
   */
  @Deprecated
  public void setInitialArrivalTimeQueueLength(int initial) {}

  /**
   * Gets the behavior of the arrival time queue in the event of
   * an overflow.
   *
   * @return The behavior of the arrival time queue as a string.
   *
   * @since RTSJ 1.0.1 Moved from {@code SporadicParameters}
   *
   * @deprecated since RTSJ 2.0 and replaced by
   *             {@link ReleaseParameters#getEventQueueOverflowPolicy}
   */
  @Deprecated
  public String getArrivalTimeQueueOverflowBehavior() { return new String(); }


  /**
   * Sets the behavior of the arrival time queue for the case where the
   * insertion of a new element makes the queue size greater than
   * the initial size given when {@code this} object was constructed.
   *
   * <p>
   * Values of  {@code behavior} are compared using reference equality
   * (==) not value equality ({@code equals()}).
   *
   * @param behavior A string representing the behavior.
   *
   * @throws StaticIllegalArgumentException when {@code behavior} is
   *  not one of the {@code final} queue overflow behavior values
   *  defined in this class.
   *
   * @since RTSJ 1.0.1 Moved here from {@code SporadicParameters}.
   *
   * @deprecated Since RTSJ 2.0
   */
  @Deprecated
  public void setArrivalTimeQueueOverflowBehavior(String behavior) {}


  /**
   * This method first performs a feasibility analysis using the new
   * cost and deadline as replacements for the matching attributes of
   * this.  When the resulting system is feasible, the method replaces the
   * current scheduling characteristics of {@code this} with the
   * new scheduling characteristics.
   *
   * @param cost The proposed cost used to determine when any particular
   *        object exceeds cost.  When {@code null}, the default
   *        value is a new instance of {@code RelativeTime(0,0)}.
   *
   * @param deadline The proposed deadline. When {@code null}, the
   *                 default value is a new instance of
   *                 {@code RelativeTime(Long.MAX_VALUE, 999999)}.
   *
   * @return {@code false}. Aperiodic parameters never yield a feasible
   *         system. (Subclasses of {@code AperiodicParameters},
   *         such as {@link SporadicParameters}, need not return false.)
   *
   * @throws StaticIllegalArgumentException when the time value of
   *          {@code cost} is less than zero, or the time value of
   *          {@code deadline} is less than or equal to zero, or
   *          the values are incompatible with the scheduler for any of
   *          the schedulables which are presently using this
   *          parameter object.
   *
   * @throws IllegalAssignmentError when {@code cost} or
   *         {@code deadline} cannot be stored in
   *         {@code this}.
   * @deprecated as of RTSJ 2.0
   */
  @Deprecated
  @Override
  public boolean setIfFeasible(RelativeTime cost, RelativeTime deadline)
  {
    return false;
  }
}
