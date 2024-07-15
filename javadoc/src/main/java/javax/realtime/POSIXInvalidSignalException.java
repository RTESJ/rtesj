/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An invalid POSIX signal number has been specified.
 *
 * @since RTSJ 2.0
 */
public class POSIXInvalidSignalException
  extends StaticRuntimeException
  implements StaticThrowable<POSIXInvalidSignalException>
{
  private static final long serialVersionUID = 3420153664977672369L;

  private static final POSIXInvalidSignalException _singleton_ =
    new POSIXInvalidSignalException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static POSIXInvalidSignalException get()
  {
    return _singleton_;
  }

  private POSIXInvalidSignalException()
  {
    super();
  }

  @Override
  public POSIXInvalidSignalException getSingleton() { return _singleton_; }
}
