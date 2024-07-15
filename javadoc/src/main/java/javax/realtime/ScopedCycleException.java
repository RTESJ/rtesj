/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Thrown when a schedulable attempts to enter an instance of
 * {@link javax.realtime.memory.ScopedMemory} where that operation would
 * cause a violation of the single parent rule.
 *
 * @since RTSJ 2.0 extends StaticRuntimeException
 */
public class ScopedCycleException
  extends StaticRuntimeException
  implements StaticThrowable<ScopedCycleException>
{
  private static final long serialVersionUID = 5014040770533988735L;

  private static final ScopedCycleException _singleton_ =
    new ScopedCycleException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static ScopedCycleException get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code ScopedCycleException}, but application code
   * should use {@link #get()} instead.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   * instead.
   */
  @Deprecated
  public ScopedCycleException() {}

  /**
   * A descriptive constructor for {@code ScopedCycleException}.
   *
   * @param description Description of the error.
   *
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead.
   */
  @Deprecated
  public ScopedCycleException(String description) {}

  @Override
  public ScopedCycleException getSingleton() { return _singleton_; }
}
