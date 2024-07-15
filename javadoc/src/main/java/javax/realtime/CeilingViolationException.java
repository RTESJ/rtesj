/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;


/**
 * This exception is thrown when a schedulable or
 * {@code java.lang.Thread} attempts to lock an object
 * governed by an instance of {@link PriorityCeilingEmulation} and the thread or
 * SO's base priority exceeds the policy's ceiling.
 *
 * @since RTSJ 2.0 implements StaticThrowable
 */
public class CeilingViolationException
  extends IllegalTaskStateException
{
  /**
   *
   */
  private static final long serialVersionUID = 8928735806620020607L;

  private static final CeilingViolationException _singleton_ =
    new CeilingViolationException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static CeilingViolationException get()
  {
    return _singleton_;
  }

  /**
   * Constructs a {@code CeilingViolationException} instance with a
   * message consisting of a zero-length string
   * and default values for the {@code callerPriority} and
   * {@code ceiling}.
   */
  private CeilingViolationException() {}

  /**
   * Gets the ceiling of the {@code PriorityCeilingEmulation} policy which
   * was
   * exceeded by the base priority of an SO or thread that attempted to
   * synchronize
   * on an object governed by the policy, which resulted in throwing of
   * {@code this}.
   *
   * @return the ceiling of the {@code PriorityCeilingEmulation} policy
   *         which caused this exception to be thrown.
   */
  public int getCeiling() { return 0; }

  /**
   * Gets the base priority of the SO or thread whose
   * attempt to synchronize resulted in the throwing of this.
   *
   * @return the synchronizing thread's base priority.
   */
  public int getCallerPriority() { return 0; }

  @Override
  public CeilingViolationException getSingleton() { return _singleton_; }
}
