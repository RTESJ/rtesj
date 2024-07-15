/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.Serializable;

/**
 * Subclasses of {@code SchedulingParameters} ({@link PriorityParameters},
 * {@link ImportanceParameters}, and any others parameters defined for particular
 * schedulers) provide the parameters to be used by the {@link Scheduler}.
 *
 * Changes to the values in a parameters object
 * affects the scheduling behavior of all the {@link Schedulable} objects
 * to which it is bound.
 *
 * @rtsj.warning.sync
 */
public class SchedulingParameters
  implements Cloneable, Serializable, Subsumable<SchedulingParameters>
{
  /**
   *
   */
  private static final long serialVersionUID = -1798586554157746704L;

  /**
   * Creates a new instance of {@code SchedulingParameters}.
   *
   * @param affinity Sets the affinity for these parameters.
   *
   * @since RTSJ 2.0
   */
  protected SchedulingParameters(Affinity affinity) {}

  /**
   * Creates a new instance of {@code SchedulingParameters} with
   * the default Affinity.
   *
   * @since RTSJ 1.0.1
   */
  protected SchedulingParameters() {}

  /**
   * Creates a clone of {@code this}.
   * <ul>
   * <li>The new object is in the current allocation context.
   * <li>
   * {@code clone} does not copy any associations from
   * {@code this} and it does not implicitly bind the new object to a schedulable.
   * </ul>
   *
   * @since RTSJ 1.0.1
   */
  @Override
  public Object clone()
  {
    try { return super.clone(); }
    catch (CloneNotSupportedException e) { throw new Error("Can't happen"); }
  }

  /**
   * Determines whether {@code this} scheduling parameters can be used by
   * tasks scheduled by {@code scheduler}.
   * @param scheduler The scheduler to check against
   * @return {@code true} when and only when {@code this} can be used
   *         with {@code scheduler} as the scheduler.
   * @since RTSJ 2.0
   */
  public boolean isCompatible(Scheduler scheduler)
  {
    return true;
  }

  /**
   * Determines whether this parameters is more eligible than another.
   *
   * @param other The other parameters object to be compared with.
   *
   * @return true when and only when this parameters is more eligible than
   *              the other parameters.
   */
  @Override
  public boolean subsumes(SchedulingParameters other)
  {
    return true;
  }

  /**
   * Determines the affinity set instance associated of these parameters.
   *
   * @return The associated affinity.
   *
   * @since RTSJ 2.0
   */
  public Affinity getAffinity() { return null; }
}
