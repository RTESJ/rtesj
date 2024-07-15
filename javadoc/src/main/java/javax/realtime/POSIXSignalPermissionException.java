/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * The process does not have permission to send the given signal to
 * the given target.
 *
 * @since RTSJ 2.0
 */
public class POSIXSignalPermissionException
  extends StaticRuntimeException
  implements StaticThrowable<POSIXSignalPermissionException>
{
  private static final long serialVersionUID = -4279309871155183141L;

  private static final POSIXSignalPermissionException _singleton_ =
    new POSIXSignalPermissionException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static POSIXSignalPermissionException get()
  {
    return _singleton_;
  }

  private POSIXSignalPermissionException()
  {
    super();
  }

  @Override
  public POSIXSignalPermissionException getSingleton() { return _singleton_; }
}
