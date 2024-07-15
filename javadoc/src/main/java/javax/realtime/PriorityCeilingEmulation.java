/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Monitor control class specifying the use of the priority ceiling
 * emulation protocol (also known as the "highest lockers" protocol).
 * Each {@code PriorityCeilingEmulation} instance is immutable; it has
 * an associated <em>ceiling,</em> initialized at construction and
 * queryable but not updatable thereafter.
 *
 * <p> When a thread or schedulable synchronizes on a target object
 * governed by a {@code PriorityCeilingEmulation} policy, then the
 * target object becomes a priority source for the thread or schedulable
 * object. When the object is unlocked, it ceases serving as a priority
 * source for the thread or schedulable. The practical effect of this
 * rule is that the thread or schedulable's active priority is boosted
 * to the policy's ceiling when the object is locked, and is reset when
 * the object is unlocked.  The value that it is reset to may or may not
 * be the same as the active priority it held when the object was
 * locked; this depends on other factors (e.g. whether the thread or
 * schedulable's base priority was changed in the interim).
 *
 * <p> The implementation must perform the following checks when a
 * thread or schedulable {@code t} attempts to synchronize on a target
 * object governed by a {@code PriorityCeilingEmulation} policy with
 * ceiling <code>ceil:</code>
 *
 * <ul>
 * <li> {@code t}'s base priority does not exceed {@code ceil}</li>
 * <li> {@code t}'s ceiling priority (when {@code t} is holding any other
 * {@code PriorityCeilingEmulation} locks) does not exceed {@code ceil}.</li>
 * </ul>
 *
 * Thus for any object {@code targetObj} that will be governed by
 * priority ceiling emulation, the programmer needs to provide (via
 * {@link MonitorControl#setMonitorControl(Object, MonitorControl)}) a
 * {@code PriorityCeilingEmulation} policy whose ceiling is at least as
 * high as the maximum of the following values:
 *
 * <ul>
 * <li> the highest base priority of any thread or schedulable that
 *  could synchronize on {@code targetObj}</li>
 * <li> the maximum ceiling priority value that any task may have when
 * it attempts to synchronize on {@code targetObj}.</li>
 * </ul>
 *
 * <p> More formally,
 * <ul>
 * <li> when a thread or schedulable {@code t}, whose base priority
 * is {@code p1}, attempts to synchronize on an object governed by a
 * {@code PriorityCeilingEmulation} policy with ceiling {@code p2},
 * where <code>p1 &gt; p2,</code> then a {@code CeilingViolationException}
 * is thrown in {@code t}; likewise, a {@code CeilingViolationException} is
 * thrown in {@code t} when {@code t} is holding a
 * {@code PriorityCeilingEmulation} lock and has a ceiling priority exceeding
 * {@code p2}.</li>
 * </ul>
 *
 * The values of {@code p1} and {@code p2} are passed to the constructor
 * for the exception and may be queried by an exception handler.
 *
 * <p> A consequence of the above rule is that a thread or schedulable
 * may nest synchronizations on {@code PriorityCeilingEmulation}-governed
 * objects as long as the ceiling for the inner lock is not less than the
 * ceiling for the outer lock.
 *
 * <p> The possibility of nested synchronizations on objects governed by
 * a mix of {@code PriorityInheritance} and {@code PriorityCeilingEmulation}
 * policies requires one other piece of behavior in order to avoid
 * unbounded priority inversions.  When a thread or schedulable holds a
 * {@code PriorityInheritance} lock, then any {@code PriorityCeilingEmulation}
 * lock that it either holds or attempts to acquire will exhibit
 * priority inheritance characteristics.  This rule is captured above in
 * the definition of priority sources (4.d).
 *
 * <p> When a thread or schedulable {@code t} attempts to synchronize on
 * a {@code PriorityCeilingEmulation}-governed object with ceiling
 * {@code ceil}, then {@code ceil} must be within the priority range
 * allowed by {@code t}'s scheduler; otherwise, an
 * {@code IllegalTaskStateException} is thrown.  Note that this does not
 * prevent a regular Java thread from synchronizing on an object
 * governed by a {@code PriorityCeilingEmulation} policy with a ceiling
 * higher than 10.
 *
 * <p> The priority ceiling for an object {@code obj} can be modified by
 * invoking {@code MonitorControl.setMonitorControl(obj, newPCE)} where
 * {@code newPCE}'s ceiling has the desired value.
 *
 * <p> See also {@link MonitorControl} {@link PriorityInheritance}, and
 * {@link CeilingViolationException}.
 */
public class PriorityCeilingEmulation extends MonitorControl
{
  private PriorityCeilingEmulation() {}

  /*
   * Creates a {@code PriorityCeilingEmulation} object with a given ceiling.
   *
   * @param ceiling Priority ceiling value.
   *
   * @throws StaticIllegalArgumentException when {@code ceiling} is out
   *  of the range of permitted priority values (i.e., less than
   * {@code PriorityScheduler.instance().getMinPriority()} or greater than
   * {@code PriorityScheduler.instance().getMaxPriority()}).

   public PriorityCeilingEmulation(int ceiling) {}
  */

  /**
   * Creates a {@code PriorityCeilingEmulation} object with the
   * specified ceiling. This object is in {@code ImmortalMemory}.
   * All invocations with the same ceiling value
   * return a reference to the same object.
   *
   * @param ceiling Priority ceiling value.
   *
   * @throws StaticIllegalArgumentException when {@code ceiling} is out
   *  of the range of permitted priority values (e.g., less than
   * {@code PriorityScheduler.instance().getMinPriority()} or greater than
   * {@code PriorityScheduler.instance().getMaxPriority()} for the base scheduler).
   *
   * @since RTSJ 1.0.1
   */
  public static PriorityCeilingEmulation instance(int ceiling)
  {
    return null;
  }


  /**
   * Gets the priority ceiling for this {@code PriorityCeilingEmulation} object.
   *
   * @return the priority ceiling.
   *
   * @since RTSJ 1.0.1
   */
  public int getCeiling()
  {
    return 0;
  }

  /**
   * Gets a {@code PriorityCeilingEmulation} object whose ceiling is
   * {@code PriorityScheduler.instance().getMaxPriority()}.
   *  This method returns a reference to a {@code PriorityCeilingEmulation}
   *  object allocated in immortal memory.
   *  All invocations of this method
   *  return a reference to the same object.
   *
   * @return a  {@code PriorityCeilingEmulation} object whose ceiling is
   * {@code PriorityScheduler.instance().getMaxPriority()}.
   *
   * @since RTSJ 1.0.1
   */
  static public PriorityCeilingEmulation getMaxCeiling()
  {
    return null;
  }
}
