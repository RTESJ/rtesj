/*------------------------------------------------------------------------*
 * Copyright 2013-2017, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime;

/**
 * An exception to throw when trying to register an {@link ActiveEvent} with
 * an {@link ActiveEventDispatcher} to which it is already registered.
 *
 * @since RTSJ 2.0
 */
public class RegistrationException
  extends StaticRuntimeException
  implements StaticThrowable<RegistrationException>
{
  private static final long serialVersionUID = 6026162450161854577L;

  private static final RegistrationException _singleton_ =
    new RegistrationException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static RegistrationException get()
  {
    return _singleton_;
  }

  /** Creates an exception with neither message nor cause */
  private RegistrationException() {}

  @Override
  public RegistrationException getSingleton() { return _singleton_; }
}
