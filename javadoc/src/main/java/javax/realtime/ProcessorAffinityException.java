/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Exception used to report processor affinity-related errors.
 *
 * @since RTSJ 2.0
 */
public class ProcessorAffinityException
  extends IllegalTaskStateException
{
  private static final long serialVersionUID = -2510970591132149468L;

  private static final ProcessorAffinityException _singleton_ =
    new ProcessorAffinityException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static ProcessorAffinityException get()
  {
    return _singleton_;
  }

  ProcessorAffinityException()
  {
    super();
  }

  @Override
  public ProcessorAffinityException getSingleton() { return _singleton_; }
}
