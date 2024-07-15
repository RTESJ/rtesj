/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Thrown when the memory region overlaps with another region in use or memory
 * that may not be used.
 *
 * @since RTSJ 2.0
 */
public class RangeOutOfBoundsException
  extends StaticCheckedException
  implements StaticThrowable<RangeOutOfBoundsException>
{
  /**
   *
   */
  private static final long serialVersionUID = 712227963556424477L;

  private static final RangeOutOfBoundsException _singleton_ =
    new RangeOutOfBoundsException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static RangeOutOfBoundsException get()
  {
    return _singleton_;
  }

  private RangeOutOfBoundsException() {}

  @Override
  public RangeOutOfBoundsException getSingleton() { return _singleton_; }
}
