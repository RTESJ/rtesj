/*------------------------------------------------------------------------*
 * Copyright 2017-2018, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime;

/**
 * A partial ordering relationship.  One object subsumes another if and only
 * if the set represented by other is a subset of {@code this} object}.
 * Objects which represent disjoint set or sets whose intersection is less
 * than either of the objects sets are mutually not subsumable.
 *
 * @param <T> The type domain of this a use of this relationship.
 *
 * @since RTSJ 2.0
 */
public interface Subsumable<T>
{
  /**
   * Indicates that some set represented by {@code other} is subsumed by the
   * set represented by {@code this} object.
   *
   * @param other The object to be compared with.
   *
   * @return true when and only when the set represented by {@code other} is
   *         subsumed by the set represented by {@code this} object.
   */
  public boolean subsumes(T other);
}
