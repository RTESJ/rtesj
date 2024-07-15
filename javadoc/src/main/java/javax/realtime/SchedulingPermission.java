/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.security.Permission;

/**
 * Scheduling has its own security permission that covers APIs in
 * {@link Scheduler}, {@link RealtimeThreadGroup}, and
 * {@link javax.realtime.enforce.ProcessingConstraint}.
 * The following table describes the actions to check.  Either the
 * permission is limited to the current {@code ThreadGroup} by
 * specifying the target {@code group} or it can apply to all, either with no
 * target or the target {@code *}.
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="SchedulingPermission action names, descriptions, and risks.">
 * <tr>
 * <th>Action Name</th>
 * <th>Description</th>
 * <th>Risks of grant</th>
 * </tr>
 * <tr>
 *   <td>system</td>
 *   <td>Changes system wide behavior, such as how scheduling is done.</td>
 *   <td>Scheduling Risk</td>
 * </tr>
 * <tr>
 *   <td>control</td>
 *   <td>Changes another context's scheduling limits, including
 *        affinity, or raises your own limits.</td>
 *   <td>Scheduling Risk</td>
 * </tr>
 * <tr>
 *   <td>monitor</td>
 *   <td>Adds overrun and underrun handlers to someone else's group or
 *       change the event used to monitor adding or removing a processor</td>
 *   <td>Load Risk</td>
 * </tr>
 * <tr>
 *   <td>tune</td>
 *   <td>Changes task scheduling.</td>
 *   <td>Scheduling Risk</td>
 * </tr>
 *
 * </table>
 *
 * The wildcard {@code *} is allowed for both signal and action.
 * The risk classes are defined in {@link RealtimePermission}.
 *
 * @since RTSJ 2.0
 */
public class SchedulingPermission extends RealtimePermission
{
  /**
   *
   */
  private static final long serialVersionUID = -1887345641505893160L;

  /**
   * Creates a new {@code SchedulingPermission} object for a given action,
   * i.e., the symbolic name of an action.  The {@code target} string
   * specifies additional limitations on the action.
   *
   * @param target Specifies the domain for the action, or {@code *}
   *        for no limit on the permission.
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws StaticIllegalArgumentException when {@code target} or
   *         {@code action} is empty.
   */
  public SchedulingPermission(String target, String actions)
  {
    super(target, actions);
  }

  /**
   * Creates a new {@code SchedulingPermission} object for a given action,
   * i.e., the symbolic name of an action.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws StaticIllegalArgumentException when {@code action} is empty.
   */
  public SchedulingPermission(String actions)
  {
    super(actions);
  }

  /**
   * Creates a new {@code SchedulingPermission} object for a given action,
   * i.e., the symbolic name of an action.  The {@code group} parameter
   * specifies the thread group that will be compared to the current
   * thread group when {@link #implies(Permission) implies} is called.
   *
   * @param group Specifies the thread group that will be compared
   *              to the current thread group when
   *              {@link #implies(Permission) implies} is called.
   * @param actions The names of the actions to allow, or {@code *}
   *                for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws javax.realtime.StaticIllegalArgumentException
   *         when {@code group} is null or {@code action} is empty.
   */
  public SchedulingPermission(ThreadGroup group, String actions)
  {
    super("", actions);
  }

  @Override
  public boolean equals(Object other) { return false; }

  @Override
  public String getActions() { return null; }

  @Override
  public int hashCode() { return 0; }

  @Override
  public boolean implies(Permission permission) { return false; }
}
