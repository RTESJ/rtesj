/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This exception is used to indicate a situation where an instance of
 * {@link AsyncEvent} attempts to bind to a happening that does not
 * exist.
 *
 * @deprecated since RTSJ 2.0
 */
@Deprecated
public class UnknownHappeningException extends RuntimeException
{
  private static final long serialVersionUID = -5036579201003623567L;

  /**
   * A constructor for {@code UnknownHappeningException}.
   */
  public UnknownHappeningException() {}

  /**
   * A descriptive constructor for {@code UnknownHappeningException}.
   *
   * @param description The reason for throwing the exception.
   */
  public UnknownHappeningException(String description) {}
}
