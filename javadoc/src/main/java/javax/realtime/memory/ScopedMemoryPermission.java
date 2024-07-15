/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.memory;

import java.security.Permission;

import javax.realtime.RealtimePermission;

/**
 * The alternate memory management module provides two permissions for the
 * security manager to use.  The following table describes the actions for
 * checking the use of scoped memory.  A signal name can be given as the
 * target.  The name of a scoped memory area type can be given for enter
 * and a maximum amount of backing store can be used for global backing store.
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="DirectMemoryPermission action names, descriptions, and risks.">
 * <tr>
 * <th>Action Name</th>
 * <th>Description</th>
 * <th>Risks of grant</th>
 * </tr>
 * <tr>
 *   <td>map</td>
 *   <td>Uses a given amount of the global backing store.</td>
 *   <td>Physical Map Risk</td>
 * </tr>
 * <tr>
 *   <td>enter</td>
 *   <td>Enters a scoped memory</td>
 *   <td>Memory Risk</td>
 * </tr>
 * <tr>
 *   <td>monitor</td>
 *   <td>Visit root scoped memories</td>
 *   <td>Encapsulation Risk</td>
 * </tr>
 * </table>
 *
 * The wildcard {@code *} or no target allows access to any scoped
 * memory area or any amount of backing store.
 *
 * The risk classes are defined in {@link javax.realtime.RealtimePermission}.
 *
 * @since RTSJ 2.0
 */
public class ScopedMemoryPermission extends RealtimePermission
{
  /**
   *
   */
  private static final long serialVersionUID = -1887345641505893160L;

  /**
   * Creates a new {@code DirectMemoryPermission} object for a given action,
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
  public ScopedMemoryPermission(String target, String actions)
  {
    super(target, actions);
  }

  /**
   * Creates a new {@code ImmortalMemoryPermission} object for a given action,
   * i.e., the symbolic name of an action.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code action} is empty.
   */
  public ScopedMemoryPermission(String actions)
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
