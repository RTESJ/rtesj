/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Thrown when the underlying hardware does not support the type of
 * physical memory requested.
 *
 * @since RTSJ 1.0.1 became unchecked
 *
 * @since RTSJ 2.0 extends StaticRuntimeException
 *
 * @deprecated as of RTSJ 2.0
 */
@Deprecated
public class UnsupportedPhysicalMemoryException
  extends RuntimeException
{
  private static final long serialVersionUID = 6392892352190694508L;

  /**
   * A constructor for {@code UnsupportedPhysicalMemoryException}.
   */
  public UnsupportedPhysicalMemoryException() {}

  /**
   * A descriptive constructor for
   * {@code UnsupportedPhysicalMemoryException}.
   *
   * @param description The reason for throwing the exception.
   */
  public UnsupportedPhysicalMemoryException(String description) {}
}
