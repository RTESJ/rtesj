/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.security.Permission;

/**
 * All permission classes in the RTSJ inherit from this class.  The following
 * table lists common risk classes that correspond to granting specific
 * permissions.
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="Permission risk classes.">
 * <tr>
 *   <th>Risk Class</th>
 *   <th>Description</th>
 * </tr>
 * <tr>
 *   <td>CPU Assignment Risk</td>
 *   <td>Interferes with critical tasks by assigning
 *   too many other tasks to the same CPU.</td>
 * </tr>
 * <tr>
 *   <td>Encapsulation Risk</td>
 *   <td>Could break out of encapsulation.</td>
 * </tr>
 * <tr>
 *   <td>External Risk</td>
 *   <td>Could adversely effect other processes on the system.</td>
 * </tr>
 * <tr>
 *   <td>Interference Risk</td>
 *   <td>Could interfere with the function of other parts of the system.</td>
 * </tr>
 * <tr>
 *   <td>Load Risk</td>
 *   <td>Could increase the load on the system.</td>
 * </tr>
 * <tr>
 *   <td>Lost Events Risk</td>
 *   <td>Another task could no longer receive the expected events.</td>
 * </tr>
 * <tr>
 *   <td>Memory Leak Risk</td>
 *   <td>Could cause memory to be lost to the system.</td>
 * </tr>
 * <tr>
 *   <td>Scheduling Risk</td>
 *   <td>Interferes with the timeliness of other parts of the system.</td>
 * </tr>
 * <tr>
 *   <td>Device Range Risk</td>
 *   <td>Could specify memory outside the desired Device range.</td>
 * </tr>
 * <tr>
 *   <td>Device Map Risk</td>
 *   <td>Could map too much or too little Device memory.</td>
 * </tr>
 * <tr>
 *   <td>DMA Range Risk</td>
 *   <td>Could specify memory outside the desired DMA range.</td>
 * </tr>
 * <tr>
 *   <td>DMA Map Risk</td>
 *   <td>Could map too much or too little DMA memory for DMA.</td>
 * </tr>
 * <tr>
 *   <td>Physical Range Risk</td>
 *   <td>Could specify memory outside the desired Physical range.</td>
 * </tr>
 * <tr>
 *   <td>Physical Map Risk</td>
 *   <td>Could take too much memory.</td>
 * </tr>
 * </table>
 *
 * @since RTSJ 2.0
 */
public abstract class RealtimePermission extends Permission
{
  /** An identifier for serialization.  */
  private static final long serialVersionUID = -6932708086006627888L;


  /**
   * Creates a new {@code RealtimePermission} object for a given set of actions,
   * i.e., the symbolic names of actions.  The {@code target} string
   * specifies additional limitations on the actions.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code actions} is {@code null}.
   * @throws StaticIllegalArgumentException when {@code actions} is empty.
   */
  protected RealtimePermission(String actions)
  {
    super(actions);
  }


  /**
   * Creates a new {@code RealtimePermission} object for a given set of actions,
   * i.e., the symbolic names of actions.  The {@code target} string
   * specifies additional limitations on the actions.
   *
   * @param target Specifies the domain for the actions, or {@code *}
   *        for no limit on the permission.
   *
   * @param actions The names of the actions to allow, or {@code *}
   *        for all actions.
   *
   * @throws NullPointerException when {@code actions} is {@code null}.
   *
   * @throws StaticIllegalArgumentException when {@code target} or
   *         {@code actions} is empty.
   */
  protected RealtimePermission(String target, String actions)
  {
    super(actions);
  }

  /**
   * Compare two Permission objects for equality.
   *
   * @param other is the object with which to compare.
   *
   * @return {@code true} when yes and {@code false} otherwise.
   */
  @Override
  public boolean equals(Object other) { return false; }

  /**
   * Obtain the actions as a String in canonical form.
   *
   * @return the actions represented as a string.
   */
  @Override
  public String getActions() { return null; }

  /**
   * Obtain the hash code value for this object.
   *
   * @return the hash code value.
   */
  @Override
  public int hashCode() { return 0; }

  /**
   * Checks if the given permission's actions are "implied by" this
   * object's actions.  This method is used by the AccessController to
   * determine whether or not a requested permission is implied by another
   * permission that is known to be valid in the current execution context.
   *
   * @param permission is the permission to check.
   *
   * @return {@code true} when yes and {@code false} otherwise.
   */
  @Override
  public boolean implies(Permission permission) { return false; }
}
