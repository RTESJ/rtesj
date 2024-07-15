/*-----------------------------------------------------------------------*\
 * Copyright 2016-2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.enforce;

import java.security.Permission;

/**
 * Constraint permissions are for controlling limits on resource
 * usage. The target may be a constraint type name:
 * <ul>
 * <li>{@code ProcessingConstraint},
 * <li>{@code ThreadConstraint},
 * <li>{@code MemoryConstraint},
 * <li>{@code BackingStoreConstraint}.
 * </ul>
 * The following table describes the actions to check.
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="ConstraintPermission action names, descriptions, and risks.">
 * <tr>
 * <th>Action Name</th>
 * <th>Description</th>
 * <th>Risks of grant</th>
 * </tr>
 *
 * <tr>
 *   <td>control</td>
 *   <td>Enables controlling the activity of constraint.</td>
 *   <td>Scheduling Risk</td>
 * </tr>
 * <tr>
 *   <td>create</td>
 *   <td>Enables new contraint instances to be created.</td>
 *   <td>Scheduling Risk</td>
 * </tr>
 * <tr>
 *   <td>system</td>
 *   <td>Changes system wide Constraint behavior.</td>
 *   <td>Load and Scheduling Risk</td>
 * </tr>
 * </table>
 *
 * The risk classes are defined in {@link javax.realtime.RealtimePermission}.
 *
 * @since RTSJ 2.0
 */
public class ConstraintPermission extends javax.realtime.RealtimePermission
{
  private static final long serialVersionUID = -5133435674907629438L;

  /**
   * Creates a new {@code ConstraintPermission} object for a given action,
   * i.e., the symbolic name of an action.  The {@code target} string
   * specifies additional limitations on the action.
   *
   * @param target Specifies the domain for the action, or {@code *}
   *        for no limit on the permission.
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws javax.realtime.StaticIllegalArgumentException
   *         when {@code target} or {@code action} is empty.
   */
  public ConstraintPermission(String target, String actions)
  {
    super(target, actions);
  }

  /**
   * Creates a new {@code ConstraintPermission} object for a given action,
   * i.e., the symbolic name of an action.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code action} is {@code null}.
   * @throws javax.realtime.StaticIllegalArgumentException
   *         when {@code action} is empty.
   */
  public ConstraintPermission(String actions)
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
