package javax.realtime.control;

import javax.realtime.IllegalAssignmentError;
import javax.realtime.StaticIllegalStateException;
import javax.realtime.RealtimeThreadGroup;

/**
 * An enhanced {@code RealtimeThreadGroup} in which asynchronous task
 * termination can be performed.  It defines a set of tasks, both instances
 * of {@link javax.realtime.Schedulable} and {@code java.lang.Thread},
 * that can be terminated together.  By combining this with a class loader,
 * one can build a virtual process within a Java runtime environment.
 */
public class AsynchronousControlGroup extends RealtimeThreadGroup
{
  /**
   * Creates a new asynchronous control group with its scheduler type
   * inherited from {@code parent}.
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
  public AsynchronousControlGroup(RealtimeThreadGroup parent, String name)
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
  public AsynchronousControlGroup(String name)
    throws StaticIllegalStateException,
      IllegalAssignmentError
  {
    super(name);
  }

  /**
   * Terminate all tasks running in this thread group.
   */
  public void abort() {}
}
