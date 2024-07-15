/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A notice to the scheduler that the associated schedulable
 * will be released aperiodically but with a
 * minimum time between releases.
 *
 * <p>
 * When a reference to a {@code SporadicParameters} object is
 * given as a parameter to a schedulable's constructor or passed
 * as an argument to one of the schedulable's setter methods, the
 * {@code SporadicParameters} object becomes the release parameters
 * object bound to that schedulable. Changes to the values in the
 * {@code SporadicParameters} object affect that schedulable
 * object. When bound to more than one schedulable then changes to
 * the values in the {@code SporadicParameters} object affect
 * <em>all</em> of the associated objects. Note that this is a
 * one-to-many relationship and <em>not</em> a many-to-many.
 *
 * <p>
 * The implementation must use modified copy semantics for each
 * {@link HighResolutionTime} parameter value.  The value of each time
 * object should be treated as when it were copied at the time it is
 * passed to the parameter object, but the object reference must also be
 * retained.  Only changes to a {@code SporadicParameters} object
 * caused by methods on that object cause the change to propagate to all
 * schedulables using the parameter object.  For instance,
 * calling {@code setCost} on a {@code SporadicParameters}
 * object will make the change, then notify the scheduler that the
 * parameter object has changed.  At that point the object is
 * reconsidered for every SO that uses it.  Invoking a method on the
 * {@code RelativeTime} object that is the cost for this object may
 * change the cost but it does not pass the change to the scheduler at
 * that time.  That change must not change the behavior of the SOs that
 * use the parameter object until a setter method on the
 * {@code SporadicParameters} object is invoked, the parameter
 * object is used in {@code setReleaseParameters()}, or the object is used in a
 * constructor for an SO.
 *
 * <p> The following table gives the default parameter values for the
 * constructors.
 *
 *  <table width="95%" border="1">
 *    <caption>SporadicParameters Default Values</caption>
 *    <tr>
 *      <th align="center"><div><strong>Attribute</strong></div></th>
 *      <th align="center"><div><strong>Value</strong></div></th>
 *    </tr>
 *    <tr>
 *      <td>minInterarrival time</td>
 *      <td>No default. A value must be supplied</td>
 *    </tr>
 *    <tr>
 *      <td>cost</td>
 *      <td>new RelativeTime(0,0)</td>
 *    </tr>
 *    <tr>
 *      <td>deadline</td>
 *      <td>new RelativeTime(mit)</td>
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
 *      <td>MIT violation policy</td>
 *      <td>SAVE</td>
 *    </tr>
 *    <tr>
 *      <td>Arrival queue overflow policy</td>
 *      <td>SAVE</td>
 *    </tr>
 *    <tr>
 *      <td>Initial arrival queue length</td>
 *      <td>0</td>
 *    </tr>
 *  </table>
 *
 * <p>This class enables the application to specify one of four
 * arrival behaviors defined by {@link MinimumInterarrivalPolicy}.
 * Each behavior indicates what to do when an arrival occurs that is
 * closer in time to the previous arrival than the value given in this
 * class as minimum interarrival time.  They also specify what to do
 * when, for any reason, the queue overflows, and what the initial
 * size of the queue should be.
 *
 * @rtsj.warning.sync
 */
public class SporadicParameters extends AperiodicParameters
{
  /**
   *
   */
  private static final long serialVersionUID = 6232498541987773679L;

  /**
   * Represents the "EXCEPT" policy for dealing with minimum
   * interarrival time violations. Under this policy, when an arrival time
   * for any instance of {@link Schedulable} which has {@code this}
   * as its instance of {@link ReleaseParameters} occurs at a time less
   * then the minimum interarrival time defined here then the
   * {@code fire()} method shall throw
   * {@link MITViolationException}. Any other associated semantics are
   * governed by the schedulers for the schedulables using these
   * sporadic parameters.  When the arrival time is a result of a happening to
   * which the instance of {@link AsyncEventHandler} is bound then the
   * arrival time is ignored.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static final String mitViolationExcept =
    MinimumInterarrivalPolicy.EXCEPT.name();

  /**
   * Represents the "IGNORE" policy for dealing with minimum
   * interarrival time violations. Under this policy, when an arrival time
   * for any instance of {@link Schedulable} which has {@code this}
   * as its instance of {@link ReleaseParameters} occurs at a time less
   * then the minimum interarrival time defined here then the new
   * arrival time is ignored.  Any other associated semantics are
   * governed by the schedulers for the schedulables using these
   * sporadic parameters.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static final String mitViolationIgnore =
    MinimumInterarrivalPolicy.IGNORE.name();

  /**
   * Represents the "SAVE" policy for dealing with minimum
   * interarrival time violations. Under this policy the arrival time
   * for any instance of {@link Schedulable} which has {@code this}
   * as its instance of {@link ReleaseParameters} is not compared to the
   * specified minimum interarrival time.  Any other associated
   * semantics are governed by the schedulers for the schedulable
   * objects using these sporadic parameters.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static final String mitViolationSave =
    MinimumInterarrivalPolicy.SAVE.name();

  /**
   * Represents the "REPLACE" policy for dealing with minimum
   * interarrival time violations. Under this policy when an arrival time
   * for any instance of {@link Schedulable} which has {@code this}
   * as its instance of {@link ReleaseParameters} occurs at a time less
   * then the minimum interarrival time defined here then the
   * information for this arrival replaces a previous arrival.  Any
   * other associated semantics are governed by the schedulers for the
   * schedulables using these sporadic parameters.
   *
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public static final String mitViolationReplace =
    MinimumInterarrivalPolicy.REPLACE.name();


  /**
   * Creates a {@code SporadicParameters} object.
   *
   * @param minInterarrival The release times of the schedulable
   *        will occur no closer than this interval.  This time object
   *        is treated as if it were copied.  Changes to
   *        {@code minInterarrival} will not affect the
   *        {@code SporadicParameters} object.  There is no default
   *        value.  When {@code minInterarrival} is {@code null}
   *        an illegal argument exception is thrown.
   *
   * @param cost Processing time per release.  On implementations which
   *        can measure the amount of time a schedulable is
   *        executed, this value is the maximum amount of time a
   *        schedulable receives per release.  When
   *        {@code null}, the default value is a new instance of
   *        {@code RelativeTime(0,0)}.
   *
   * @param deadline The latest permissible completion time measured
   *         from the release time of the associated invocation of the
   *         schedulable.  When {@code null}, the default value is a new
   *         instance of {@code minInterarrival}:
   *         {@code new RelativeTime(minInterarrival)}.
   *
   * @param overrunHandler This handler is invoked when an invocation of
   *         the schedulable exceeds cost.  Not required for
   *         minimum implementation.  When {@code null} no overrun
   *         handler will be used.
   *
   * @param missHandler This handler is invoked when the
   *         {@code run()} method of the schedulable is
   *         still executing after the deadline has passed.   When
   *         {@code null}, no deadline miss handler will be used.
   *
   * @param rousable Determines whether or not an instance of
   *        {@code Schedulable} can be prematurely released by a thread
   *        interrupt.
   *
   * @throws StaticIllegalArgumentException when {@code minInterarrival}
   *     is {@code null} or its time value is not greater than
   *     zero, or the time value of {@code cost} is less than zero,
   *     or the time value of {@code deadline} is not greater than
   *     zero, or when the chronograph associated with {@code deadline}
   *     and {@code minInterarrival} parameters are not identical or not
   *     an instance of {@link Clock}.
   *
   * @throws IllegalAssignmentError when {@code minInterarrival},
   *      {@code cost}, {@code deadline}, {@code  overrunHandler} or
   *      {@code missHandler} cannot be stored in {@code this}.
   *
   * @since RTSJ 2.0
   */
  public SporadicParameters(RelativeTime minInterarrival,
                            RelativeTime cost,
                            RelativeTime deadline,
                            AsyncEventHandler overrunHandler,
                            AsyncEventHandler missHandler,
                            boolean rousable)
  {
    super(cost, deadline, overrunHandler, missHandler, rousable);
  }

  /**
   * Equivalent to {@link #SporadicParameters(RelativeTime, RelativeTime,
   * RelativeTime, AsyncEventHandler, AsyncEventHandler, boolean)} with an
   * argument list of {@code (minInterarrival, cost, deadline, overrunHandler,
   * missHandler, false)}.
   */
  public SporadicParameters(RelativeTime minInterarrival,
                            RelativeTime cost,
                            RelativeTime deadline,
                            AsyncEventHandler overrunHandler,
                            AsyncEventHandler missHandler)
  {
    this(minInterarrival, cost, deadline, overrunHandler, missHandler, false);
  }


  /**
   * Equivalent to {@link #SporadicParameters(RelativeTime, RelativeTime,
   * RelativeTime, AsyncEventHandler, AsyncEventHandler, boolean)} with an
   * argument list of {@code (minInterarrival, null, deadline, null,
   * missHandler, rousable)}.
   *
   *
   * @since RTSJ 2.0
   */
  public SporadicParameters(RelativeTime minInterarrival,
                            RelativeTime deadline,
                            AsyncEventHandler missHandler,
                            boolean rousable)
  {
    this(minInterarrival, deadline, null, missHandler, null, rousable);
  }


  /**
   * Equivalent to {@link #SporadicParameters(RelativeTime, RelativeTime,
   * RelativeTime, AsyncEventHandler, AsyncEventHandler, boolean)} with an
   * argument list of {@code (minInterarrival, null, null, null, null, false)}.
   *
   * @since RTSJ 1.0.1
   */
  public SporadicParameters(RelativeTime minInterarrival)
  {
    this(minInterarrival, null, null, null, null, false);
  }


  /**
   * Determines the current value of minimal interarrival.
   *
   * @return the object last used to set the minimal interarrival
   *         containing the current value of minimal interarrival.
   */
  public RelativeTime getMinimumInterarrival()
  {
    return null;
  }

  /**
   * Determines the current value of minimum interarrival.
   *
   * @param value A relative time object to fill and return.
   *
   * @return {@code value} or, when {@code null}, the last object used
   *         to set the minimal interarrival, set to the current value
   *         of minimal interarrival.
   *
   * @since RTSJ 2.0
   */
  public RelativeTime getMinimumInterarrival(RelativeTime value)
  {
    return null;
  }

  /**
   * Sets the minimum interarrival time.
   *
   * @param minimum The release times of the schedulable will
   *        occur no closer than this interval.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException
   *           when {@code minimum} is {@code null} or
   *          its time value is not greater than zero.
   *
   * @throws IllegalAssignmentError when {@code minimum}
   *    cannot be stored in {@code this}.
   *
   * @since RTSJ 2.0 returns itself
   */
  @ReturnsThis
  public SporadicParameters setMinimumInterarrival(RelativeTime minimum)
  {
    return this;
  }

  /**
   * Sets the policy for handling the arrival time queue when the
   * new arrival time is closer to the previous arrival time than the
   * minimum interarrival time given in {@code this}.
   *
   * @param policy The current policy for MIT violations.
   *
   * @since RTSJ 2.0
   */
  @ReturnsThis
  public SporadicParameters
    setMinimumInterarrivalPolicy(MinimumInterarrivalPolicy policy)
  {
    return this;
  }


  /**
   * Gets the arrival time queue policy for handling minimal interarrival
   * time underflow.
   *
   * @return the minimum interarrival time violation behavior as a string.
   *
   * @since RTSJ 2.0
   */
  public MinimumInterarrivalPolicy getMinimumInterarrivalPolicy()
  {
    return null;
  }

  /**
   * Sets the behavior of the arrival time queue for the case where the
   * new arrival time is closer to the previous arrival time than the
   * minimum interarrival time given in this.
   *
   * <p> Values of {@code behavior} are compared using reference
   * equality (==) not value equality ({@code equals()}).
   *
   * @param behavior A string representing the behavior.
   *
   * @throws StaticIllegalArgumentException when {@code behavior} is not
   *         one of the {@code final} MIT violation behavior values
   *         defined in this class.
   *
   * @deprecated since RTSJ 2.0 and replaced by
   *             {@link #setMinimumInterarrivalPolicy}.
   */
  @Deprecated
  public void setMitViolationBehavior(String behavior)
  {
  }


  /**
   * Gets the arrival time queue behavior in the event of a minimum
   * interarrival time violation.
   *
   * @return the minimum interarrival time violation behavior as a string.
   *
   * @deprecated since RTSJ 2.0 and replaced by
   *             {@link #getMinimumInterarrivalPolicy}.
   */
  @Deprecated
  public String getMitViolationBehavior()
  {
    return new String();
  }


  /**
   *  This method first performs a feasibility analysis using the new cost,
   *  and deadline as replacements for the matching attributes of this.
   *  When the resulting system is feasible, the method replaces the current
   *  scheduling characteristics, of {@code this} with the new
   *  scheduling characteristics.
   *
   * @param cost {@inheritDoc}
   *
   * @param deadline The proposed deadline. When {@code null},
   * the default value is a new instance of {@code RelativeTime(mit)}.
   *
   * @return {@code true}, when the resulting system is feasible and
   *               the changes are made;
   *         {@code false}, when the resulting system is
   *                not feasible and no changes are made.
   *
   * @throws StaticIllegalArgumentException {@inheritDoc}
   *
   * @throws IllegalAssignmentError {@inheritDoc}
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
  @Override
  public boolean setIfFeasible(RelativeTime cost, RelativeTime deadline)
  {
    return super.setIfFeasible(cost, deadline);
  }

  /**
   * This method first performs a feasibility analysis using the new
   * interarrival, cost and deadline attributes as replacements for the
   * matching attributes of this. When the resulting system is feasible
   * the method replaces the current attributes with the new ones.
   *
   * <p> Changes to a {@code SporadicParameters} instance effect
   * subsequent arrivals.
   *
   * @param interarrival The proposed interarrival time.  There is no
   *        default value.  When {@code minInterarrival} is
   *        {@code null} an illegal argument exception is thrown.
   *
   * @param cost The proposed cost.  When {@code null}, the default
   *        value is a new instance of {@code RelativeTime(0,0)}.
   *
   *
   * @param deadline The proposed deadline.  When {@code null}, the
   *        default value is a new instance of
   *        {@code RelativeTime(mit)}.
   *
   * @return {@code true}, when the resulting system is feasible and
   *         the changes are made;
   *         {@code false}, when the resulting system is
   *         not feasible and no changes are made.
   *
   * @throws StaticIllegalArgumentException when {@code minInterarrival}
   *         is {@code null} or its time value is not greater than
   *         zero, or the time value of {@code cost} is less than
   *         zero, or the time value of {@code deadline} is not
   *         greater than zero.
   *
   * @throws IllegalAssignmentError when {@code interarrival},
   *         {@code cost} or {@code deadline} cannot be stored
   *         in {@code this}.
   *
   * @deprecated as of RTSJ 2.0 The framework for feasibility analysis is inadequate
   */
  @Deprecated
  public boolean setIfFeasible(RelativeTime interarrival,
                               RelativeTime cost,
                               RelativeTime deadline)
  {
    return true;
  }
}
