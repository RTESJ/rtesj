/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.device;

import java.security.Permission;

import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.RealtimePermission;

/**
 * The device management module provides a permission for the security
 * manager to manage raw memory.  The following table describes the
 * actions to check.  An address range can be given as the {@code target} or
 * {@code *} for any.
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="RawMemoryPermission action names, descriptions, and risks.">
 * <tr>
 * <th>Action Name</th>
 * <th>Description</th>
 * <th>Risks of grant</th>
 * </tr>
 * <tr>
 *   <td>define</td>
 *   <td>Defines a device address range for use by raw memory.</td>
 *   <td>Device Range Risk</td>
 * </tr>
 * <tr>
 *   <td>map</td>
 *   <td>Maps a given amount of raw memory into a raw memory object.</td>
 *   <td>Device Map Risk</td>
 * </tr>
 * </table>
 *
 * The risk classes are defined in {@link javax.realtime.RealtimePermission}.
 *
 * @since RTSJ 2.0
 */
public class RawMemoryPermission extends RealtimePermission
{
  /**
   *
   */
  private static final long serialVersionUID = -1887345641505893160L;

  /**
   * Creates a new {@code RawMemoryPermission} object for a given action,
   * i.e., the symbolic name of an action.  The {@code target} string
   * specifies additional limitations on the action.
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
  public RawMemoryPermission(String target, String actions)
    throws NullPointerException, StaticIllegalArgumentException
  {
    super(target, actions);
  }

  /**
   * Creates a new {@code RawMemoryPermission} object for a given action,
   * i.e., the symbolic name of an action.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code action} is empty.
   */
  public RawMemoryPermission(String actions)
    throws NullPointerException, StaticIllegalArgumentException
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
