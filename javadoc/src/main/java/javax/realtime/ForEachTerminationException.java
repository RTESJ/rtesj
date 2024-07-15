/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * An exception to throw when a visitor should terminate early.  It is
 * for use with the forEach method in collection classes.  Since it is a
 * StaticThrowable, it can be used without creating garbage.
 */
public class ForEachTerminationException
  extends StaticRuntimeException
  implements StaticThrowable<ForEachTerminationException>
{
  private static final long serialVersionUID = 82349403597040726L;

  private static final ForEachTerminationException _singleton_ =
    new ForEachTerminationException();

  /**
   * Gets the static instance of this class and initializes its stack trace.
   * It should be initialized before throwing.
   *
   * @return the static singleton of this class.
   */
  public static ForEachTerminationException get()
  {
    return _singleton_;
  }

  private ForEachTerminationException() {}

  @Override
  public ForEachTerminationException getSingleton() { return _singleton_; }
}
