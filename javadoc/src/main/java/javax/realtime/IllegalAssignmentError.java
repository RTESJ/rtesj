/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * The exception thrown on an attempt to make an illegal assignment.
 * For example, this will be thrown on any attempt to assign a reference
 * to an object in scoped memory, an area of memory identified to be an instance
 * of {@link javax.realtime.memory.ScopedMemory}, to a field of an object in
 * immortal memory.
 *
 * @since RTSJ 2.0 extends StaticError
 */
public class IllegalAssignmentError
  extends StaticError implements StaticThrowable<IllegalAssignmentError>
{
  /**
   *
   */
  private static final long serialVersionUID = -6803556508273380303L;

  private static final IllegalAssignmentError _singleton_ =
    new IllegalAssignmentError();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static IllegalAssignmentError get()
  {
    return _singleton_;
  }

  /**
   * A constructor for {@code IllegalAssignmentError}, but the application
   * should use {@link #get()} instead, e.g., {@code IllegalAssignmentError.get().init()}.
   */
  public IllegalAssignmentError()
  {
    super();
  }

  /**
   * A descriptive constructor for {@code IllegalAssignmentError}.
   *
   * @param description The reason for throwing the error.
   * @deprecated since RTSJ 2.0; application code should use {@link #get()}
   *             instead, e.g., {@code IllegalAssignmentError.get().init(description)}.
   */
  @Deprecated
  public IllegalAssignmentError(String description)
  {
    super();
    init(description);
  }

  @Override
  public IllegalAssignmentError getSingleton() { return _singleton_; }
}
