/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * {@link PhysicalMemoryManager} can only accommodate one filter object for
 * each type of memory. It throws this exception when an attempt is made
 * to register more than one filter for a type of memory.
 *
 * @deprecated since RTSJ 2.0
 */
@Deprecated
public class DuplicateFilterException extends Exception
{
  /**
   *
   */
  private static final long serialVersionUID = 3559902244065118950L;

  /**
   * A descriptive constructor for {@code DuplicateFilterException}.
   *
   * @param description Description of the error.
   */
  public DuplicateFilterException(String description)
  {
    super(description);
  }

  /**
   * A constructor for {@code DuplicateFilterException}.
   */
  public DuplicateFilterException()
  {
    super();
  }
}
