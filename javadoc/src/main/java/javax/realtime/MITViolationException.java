/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Thrown by the {@link AsyncEvent#fire()} on a minimum
 * interarrival time violation.
 * More specifically, it is thrown under the semantics of
 * the base priority scheduler's sporadic parameters'
 * {@code mitViolationExcept} policy when an attempt
 * is made to introduce a release that would violate the MIT
 * constraint.
 *
 * @since RTSJ 1.0.1 became unchecked
 *
 * @since RTSJ 2.0 extends StaticRuntimeException
 */
public class MITViolationException
  extends StaticRuntimeException
  implements StaticThrowable<MITViolationException>
{
  /**
   *
   */
  private static final long serialVersionUID = 7239451477969970767L;

  private static final MITViolationException _singleton_ =
    new MITViolationException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static MITViolationException get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code MITViolationException}.
   */
  public MITViolationException()
  {
    super();
  }

  /**
   * A descriptive constructor for {@code MITViolationException}.
   *
   * @param description
   *          Description of the error.
   */
  public MITViolationException(String description)
  {
    super();
    init(description);
  }

  @Override
  public MITViolationException getSingleton() { return _singleton_; }
}
