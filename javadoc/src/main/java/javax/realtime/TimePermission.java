/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.security.Permission;

/**
 * Time permissions are for controlling clocks and timers. The following
 * table describes the actions to check.  For all but {@code create}, which
 * takes no target, either the permission is limited to the current
 * {@code ThreadGroup} by specifying the target {@code group}, or it can
 * apply to all with no target or the target {@code *}.
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="TimePermission action names, descriptions, and risks">
 * <tr>
 * <th>Action Name</th>
 * <th>Description</th>
 * <th>Risks of grant</th>
 * </tr>
 *
 * <tr>
 *   <td>control</td>
 *   <td>Enables controlling the activity of a timer</td>
 *   <td>Scheduling Risk</td>
 * </tr>
 * <tr>
 *   <td>create</td>
 *   <td>Enables new timers to be created</td>
 *   <td>Scheduling Risk</td>
 * </tr>
 * <tr>
 *   <td>handle</td>
 *   <td>Adds handler to a timer</td>
 *   <td>Load Risk</td>
 * </tr>
 * <tr>
 *   <td>override</td>
 *   <td>Change existing handlers</td>
 *   <td>Interference Risk</td>
 * </tr>
 * <tr>
 *   <td>system</td>
 *   <td>Changes system wide timer and clock behavior</td>
 *   <td>Load and Scheduling Risk</td>
 * </tr>
 * </table>
 *
 * The risk classes are defined in {@link RealtimePermission}.
 *
 * @since RTSJ 2.0
 */
public class TimePermission extends RealtimePermission
{
  /**
   *
   */
  private static final long serialVersionUID = -1887345641505893160L;

  /**
   * Creates a new {@code TimePermission} object for a given action,
   * i.e., the symbolic name of an action.  The {@code target} string
   * specifies additional limitations on the action.
   *
   * @param target Specifies the domain for the action, or {@code *}
   *        for no limit on the permission.
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws StaticIllegalArgumentException when {@code target} or {@code action}
   *         is empty.
   */
  public TimePermission(String target, String actions)
  {
    super(target, actions);
  }

  /**
   * Creates a new {@code TimePermission} object for a given action,
   * i.e., the symbolic name of an action.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws StaticIllegalArgumentException when {@code action} is empty.
   */
  public TimePermission(String actions)
  {
    super(actions);
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
