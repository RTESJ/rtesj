/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.util.function.Consumer;

/**
 * An enhanced {@code ThreadGroup} in which a {@link RealtimeThread}
 * instance may be started, as well as a convention {@code Thread}.
 * Limits for what realtime scheduler and scheduling parameters can be
 * enforced on all tasks in this group.  A normal {@code ThreadGroup}
 * may not contain an instance of {@link Schedulable} or instances of
 * {@code RealtimeThreadGroup}.  Every thread is in some instance of
 * {@code ThreadGroup} and every instance of {@code RealtimeThread} is
 * in some instance of {@code RealtimeThreadGroup}.  This means that the
 * {@code main} thread of a realtime Java implementation must be in an
 * instance of this class, not a normal {@code ThreadGroup}.
 *
 * @rtsj.warning.sync
 *
 * @since RTSJ 2.0
 */
public class RealtimeThreadGroup extends ThreadGroup
{
  /**
   * Creates a new realtime thread group with its scheduler type inherited from
   * {@code parent}.
   *
   * @param parent The parent group of the new group
   *
   * @param name The name of the new group
   *
   * @param scheduler a scheduler class limiting the schedulers allowed for
   *        scheduling group members.  When {@code null} inherits from parent.
   *        Instances of {@code java.lang.ThreadGroup} do not have a scheduler
   *        and may not contain instances of {@code RealtimeSchedulerGroup}.
   *
   * @throws StaticIllegalStateException when the parent {@code ThreadGroup}
   * instance is not an instance of {@code RealtimeThreadGroup}.
   *
   * @throws IllegalAssignmentError when the parent
   * {@code ThreadGroup} instance is not assignable to this.
   */
  public RealtimeThreadGroup(RealtimeThreadGroup parent,
                             String name,
                             Class<? extends Scheduler> scheduler)
  {
    super(parent, name);
  }

  /**
   * Creates a new realtime thread group with its scheduler type inherited from
   * {@code parent}.
   *
   * @param parent The parent group of the new group
   *
   * @param name The name of the new group
   *
   * @throws StaticIllegalStateException when the parent {@code ThreadGroup}
   * instance is not an instance of {@code RealtimeThreadGroup}.
   *
   * @throws IllegalAssignmentError when the parent
   * {@code ThreadGroup} instance is not assignable to this.
   */
  public RealtimeThreadGroup(RealtimeThreadGroup parent, String name)
  {
    super(parent, name);
  }


  /**
   * Creates a new group with the current {@code ThreadGroup} instance
   * as its parent and that parent's scheduler type for its scheduler type.
   * That parent must be an instance of {@code RealtimeThreadGroup}.
   * The primordial realtime thread group has {@code Scheduler.class} as its
   * scheduler type.
   *
   * @param name The name of the new group
   *
   * @throws StaticIllegalStateException when the parent {@code ThreadGroup}
   * instance is not an instance of {@code RealtimeThreadGroup}.
   *
   * @throws IllegalAssignmentError when the parent
   * {@code ThreadGroup} instance is not assignable to this.
   */
  public RealtimeThreadGroup(String name)
    throws StaticIllegalStateException, IllegalAssignmentError
  {
    super(name);
  }

  /**
   * Finds the type of scheduler tasks in this group may use.  The scheduler
   * of each thread must be an instance of the type returned.  The default is
   * {@code class<Scheduler>}, but it may be set to any subtype.
   *
   * @return the scheduler type
   */
  public Class<? extends Scheduler> getScheduler()
  {
    return null;
  }

  /**
   * Finds the upper bound on scheduling eligibility that tasks in this
   * group may have.  For example, when it is an instance of
   * {@code PriorityParameters}, it gives the maximum base priority any
   * task in this group.
   *
   * @return the scheduling parameter instance denoting the upper bound
   *         on the scheduling eligibility of threads in this group,
   *         The maximal possible eligibility is represented by an
   *         instance of {@code SchedulingParamters}, not one of its
   *         subclasses, with an affinity that contains all processors
   *         available to the process.  This may not be {@code null}.
   */
  public SchedulingParameters getMaxEligibility()
  {
    return null;
  }

  /**
   * Sets the upper bound on scheduling eligibility that tasks in this
   * group may have.  For example, when it is an instance of
   * {@code PriorityParameters}, it sets the maximum base priority any
   * task in this group may have.  When a task in the group has a higher
   * eligibility than specified in {@code parameters}, the task's
   * eligibility is silently set to the max specified in {@code parameters}.
   *
   * <p>
   * When the new eligibility is higher than that of any parent's eligibility,
   * then eligibility is set to the minimum of those priorities.
   *
   * When a child of this
   * {@code RealtimeThreadGroup} has a higher max eligibility than specified in
   * {@code parameters}, its max eligibility is silently set to the max
   * specified in {@code parameters} as if {@code setMaxEligibility} were
   * invoked on it recursively.
   * <p>
   * When a task in this {@code RealtimeThreadGroup} or a child of this
   * {@code RealtimeThreadGroup} has previously had its maximum eligibility
   * reduced by a call to this method, setting a higher maximum
   * eligibility via this method will not automatically reraise its
   * eligibility.
   *
   * Please note that this method is not thread safe, as it uses methods
   * from ThreadGroup that are not thread safe.
   *
   * @param parameters The SchedulingParameter instance denoting the new
   *        upper bound on the scheduling eligibility of threads in this group.
   *
   * @return {@code this}
   *
   * @throws StaticIllegalArgumentException when {@code parameters} are not
   *         consistent with the scheduler type.  The scheduler specified
   *         must be specific enough that only mutually compatible
   *         {@code SchedulingParameters} could be set.  For example,
   *         {@link Scheduler} is not sufficient to restrict the scheduling
   *         parameters to compatible types, but {@link PriorityScheduler} does
   *         since all {@code PriorityScheduler} instances require
   *         {@link PriorityParameters}.
   *
   * @throws StaticIllegalStateException when {@code parameters} is a
   *         higher eligibility than the max eligibility enforced by a
   *         {@code SchedulingParameters} above {@code this} in the
   *         hierarchy.
   */
  @ReturnsThis
  public RealtimeThreadGroup setMaxEligibility(SchedulingParameters parameters)
    throws StaticIllegalStateException
  {
    return this;
  }

  /**
   * Visit all {@code java.lang.Thread} instances contained by {@code this}
   * group and optionally all {@code ThreadGroup} instances contained within
   * recursively.
   *
   * @param visitor A consumer of each schedulable instance.
   *
   * @param recurse A boolean to indicated that the visit should be recursive.
   *
   * @throws ForEachTerminationException
   */
  public void visitThreads(Consumer<Thread> visitor, boolean recurse)
    throws ForEachTerminationException
  {
  }

  /**
   * Visit all {@code java.lang.Thread} instances contained by {@code this}
   * group. It is equivalent to calling {@link #visitThreads(Consumer, boolean)}
   * with {@code recurse} set to {@code false}.
   *
   * @param visitor A consumer of each thread instance
   *
   * @throws ForEachTerminationException when the visitor is prematurely ended.
   */
  public void visitThreads(Consumer<Thread> visitor)
    throws ForEachTerminationException
  {
    visitThreads(visitor, false);
  }

  /**
   * Performs some operation on all the groups in the current group.  The
   * traversal of these children continues as long as {@code visitor} does not
   * throw a {@link ForEachTerminationException}.  Thus the traversal can be
   * prematurely ended by {@code visitor} throwing this exception, e.g., when
   * a particular element is found. It is equivalent to a call to
   * {@link #visitThreadGroups(Consumer, boolean)} with {@code recurse}
   * set to {@code false}.
   *
   * @param visitor The function to be called on each child thread group.
   *
   * @throws ForEachTerminationException when the traversal ends prematurely.
   */
  public void visitThreadGroups(Consumer<ThreadGroup> visitor)
    throws ForEachTerminationException
  {
  }

  /**
   * Performs some operation on all th groups in the current group.  The
   * traversal of these children continues as long as {@code visitor} does not
   * throw a {@link ForEachTerminationException}.  Thus the traversal can be
   * prematurely ended by {@code visitor} throwing this exception, e.g., when
   * a particular element is found.
   *
   * @param visitor The function to be called on each child thread group.
   *
   * @param recursive A boolean to determine whether or not all subgroups
   *        are included, where {@code true} means yes and {@code false}
   *        means no.
   *
   * @throws ForEachTerminationException when the traversal ends prematurely.
   */
  public void visitThreadGroups(Consumer<ThreadGroup> visitor,
                                boolean recursive)
  {
  }
}
