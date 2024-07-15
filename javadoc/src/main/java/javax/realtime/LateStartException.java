/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Exception thrown when a periodic realtime thread or timer is started
 * after its assigned, absolute, start time.
 *
 * @since RTSJ 2.0
 */
public class LateStartException
  extends StaticCheckedException
  implements StaticThrowable<LateStartException>
{
  /**
   *
   */
  private static final long serialVersionUID = -8645601023003347139L;

  private static final LateStartException _singleton_ =
    new LateStartException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static LateStartException get()
  {
    return _singleton_;
  }

  private LateStartException() {}

  @Override
  public LateStartException getSingleton() { return _singleton_; }
}
