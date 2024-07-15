/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Thrown when a resource is not yet initialized, such as a Clock which
 * cannot be created yet because its data source is not yet available.  This
 * can happen when a Java process starts early in the system startup process.
 *
 * @since RTSJ 2.0
 */
public class UninitializedStateException
  extends StaticRuntimeException
  implements StaticThrowable<UninitializedStateException>
{
  private static final long serialVersionUID = -7545045130636858689L;

  private static final UninitializedStateException _singleton_ =
    new UninitializedStateException();

  /**
   * Gets the static instance of this class and initializes its stack trace.
   * It should be initialized before throwing.
   *
   * @return the static singleton of this class.
   */
  public static UninitializedStateException get()
  {
    return _singleton_;
  }

  private UninitializedStateException() { }

  @Override
  public UninitializedStateException getSingleton() { return _singleton_; }
}
