/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.posix;

import java.security.Permission;

import javax.realtime.RealtimePermission;

/**
 * The POSIX module provides one permission class for the security manager to
 * use.  This permission applies to both {@link Signal} and
 * {@link RealtimeSignal}. The following table describes the actions for
 * checking the use of signals. A signal name (like {@code SIGHUP}) or {@code *}
 * can be given as target.
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="POSIXPermission action names, descriptions, and risks.">
 * <tr>
 * <th>Action Name</th>
 * <th>Description</th>
 * <th>Risks of grant</th>
 * </tr>
 *
 * <tr>
 *   <td>handle</td>
 *   <td>Adds a handle to the given signal.</td>
 *   <td>Load Risk</td>
 * </tr>
 * <tr>
 *   <td>override</td>
 *   <td>Removes a handler that belongs to another realtime thread group.</td>
 *   <td>Interference Risk</td>
 * </tr>
 * <tr>
 *   <td>send</td>
 *   <td>Sends a given signal.</td>
 *   <td>External Risk</td>
 * </tr>
 * <tr>
 *   <td>control</td>
 *   <td>Starts or stops this signal.</td>
 *   <td>Scheduling Risk</td>
 * </tr>
 * <tr>
 *   <td>system</td>
 *   <td>Changes system's wide signaling behavior.</td>
 *   <td>Scheduling and Load Risk</td>
 * </tr>
 * </table>
 * <p>
 * The wildcard {@code *} is allowed for both signal and action.
 *
 * The risk classes are defined in {@link javax.realtime.RealtimePermission}.
 *
 * @see Signal
 *
 * @see RealtimeSignal
 *
 * @see SignalDispatcher
 *
 * @see RealtimeSignalDispatcher
 *
 * @since RTSJ 2.0
 */
public class POSIXPermission extends RealtimePermission
{
  /**
   *
   */
  private static final long serialVersionUID = 2875588082555335334L;

  /**
   * Creates a new {@code POSIXPermission} object for a given action,
   * i.e., the symbolic name of an action.  The {@code target} string
   * specifies for which POSIX signal the action applies.
   *
   * @param target Specifies the domain for the action, or {@code *}
   *        for no limit on the permission.
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code target} or {@code action} is empty.
   */
  public POSIXPermission(String target, String actions)
  {
    super(target, actions);
  }

  /**
   * Creates a new {@code POSIXPermission} object for a given action,
   * i.e., the symbolic name of an action.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code action} is empty.
   */
  public POSIXPermission(String actions)
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
