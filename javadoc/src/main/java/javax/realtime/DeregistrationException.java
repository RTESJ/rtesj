/*------------------------------------------------------------------------*
 * Copyright 2013--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime;

/**
 * An exception to throw when trying to deregister an {@link ActiveEvent}
 * from an {@link ActiveEventDispatcher} to which it is not registered.
 *
 * @since RTSJ 2.0
 */
public class DeregistrationException
  extends StaticRuntimeException
  implements StaticThrowable<DeregistrationException>
{
  private static final long serialVersionUID = -5401470339761812118L;

  private static final DeregistrationException _singleton_ =
    new DeregistrationException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static DeregistrationException get()
  {
    return _singleton_;
  }

  /** Creates an exception with neither message nor cause */
  private DeregistrationException() {}

  @Override
  public DeregistrationException getSingleton() { return _singleton_; }
}
