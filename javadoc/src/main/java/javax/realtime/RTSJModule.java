/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Modules an RTSJ implementation may provide.
 *
 * @since RTSJ 2.0
 */
public enum RTSJModule
{
  /** Indicates the presence of the core module. */
  CORE(1),
  /** Indicates the presence of the CONTROL module. */
  CONTROL(2),
  /** Indicates the presence of the device access module. */
  DEVICE(4),
  /** Indicates the presence of the alternative memory areas module. */
  MEMORY(8),
  /** Indicates the presence of the POSIX module. */
  POSIX(16),
  /** Indicates the presence of the all APIs needed to implement the
   * SCJ.  This is not actually a module, since these APIs are covered
   * in the other modules.  Thus either this is set alone for a pure
   * implement of SCJ or this is set and at least all other packages that
   * contain an API needed by the SCJ are set.  Other configurations
   * are forbidden. */
  SCJ(Integer.MIN_VALUE);

  private final int value_;

  RTSJModule(int value) { value_ = value; }

  /**
   * Determines the numeric value of an element of this enumeration.  This
   * value can be used in bit sets to determine the presence of the given
   * element.
   *
   * @return a number with a single bit set representing this element.
   */
  public int value() { return value_; }

  /**
   * Given an int representing a set of enumeration elements via bit value,
   * sees whether or not this element is contained within that set.
   *
   * @param value The set to test against.
   * @return {@code true} when and only when {@code value} has the bit set
   *         that represents {@code this}.
   */
  public boolean in(int value) { return (value_ & value) != 0; }
}
