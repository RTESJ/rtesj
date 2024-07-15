/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.security.Permission;

/**
 * Memory permission are divided into those for the core module and
 * those for the memory module.  The following table describes the
 * actions to check for the core module.  The name of a primordial
 * memory area type can be given.
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="ImmortalMemoryPermission action names, descriptions, and risks.">
 * <tr>
 * <th>Action Name</th>
 * <th>Description</th>
 * <th>Risks of grant</th>
 * </tr>
 *
 * <tr>
 *   <td>allocate</td>
 *   <td>Allows the creation of an object in Immortal without
 *   entering it.</td>
 *   <td>Can cause a Memory Leak Risk</td>
 * </tr>
 *
 * </table>
 *
 * The wildcard {@code *}, or no target, allows access to primordial
 * memory area.
 *
 * The risk classes are defined in {@link RealtimePermission}.
 *
 * @since RTSJ 2.0
 */
public class ImmortalMemoryPermission extends RealtimePermission
{
  /**
   *
   */
  private static final long serialVersionUID = -1887345641505893160L;

  /**
   * Creates a new {@code ImmortalMemoryPermission} object for a given action,
   * i.e., the symbolic name of an action.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws StaticIllegalArgumentException when {@code action} is empty.
   */
  public ImmortalMemoryPermission(String actions)
  {
    super(actions);
  }

  /**
   * Creates a new {@code ImmortalMemoryPermission} object for a given action,
   * i.e., the symbolic name of an action.
   *
   * @param target The names of the memory area class for the action, or
   *        {@code *} for all memory areas.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws StaticIllegalArgumentException when {@code action} is empty.
   */
  public ImmortalMemoryPermission(String target, String actions)
  {
    super(target, actions);
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
