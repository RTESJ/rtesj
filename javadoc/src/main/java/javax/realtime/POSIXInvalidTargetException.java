/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * The target of the signal does not exist.
 *
 * @since RTSJ 2.0
 */
public class POSIXInvalidTargetException
  extends StaticCheckedException
  implements StaticThrowable<POSIXInvalidTargetException>
{
  private static final long serialVersionUID = 5997207951382861179L;

  private static final POSIXInvalidTargetException _singleton_ =
    new POSIXInvalidTargetException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static POSIXInvalidTargetException get()
  {
    return _singleton_;
  }

  private POSIXInvalidTargetException()
  {
    super();
  }

  @Override
  public POSIXInvalidTargetException getSingleton() { return _singleton_; }
}
