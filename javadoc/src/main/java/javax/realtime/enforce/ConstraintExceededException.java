/*------------------------------------------------------------------------*
 * Copyright 2002-2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime.enforce;

import javax.realtime.StaticRuntimeException;
import javax.realtime.StaticThrowable;
import javax.realtime.StaticThrowableStorage;

/*---------------------------------------------------------------------*/

/**
 * A runtime exception to throw when the limits of a ResourceConstraint
 * would be exceeded.
 *
 * @since RTSJ 2.0
 */
public class ConstraintExceededException
  extends StaticRuntimeException
  implements StaticThrowable<ConstraintExceededException>
{
  private static final long serialVersionUID = 5679509359767311167L;

  private static final ConstraintExceededException _singleton_ =
    new ConstraintExceededException();

  /**
   * Gets the preallocated version of this  {@code Throwable}.  Allocation is
   * done in memory that acts like {@link javax.realtime.ImmortalMemory}.
   * The message and cause are cleared and the stack trace is filled out.
   *
   * @return the preallocated exception.
   */
  public static ConstraintExceededException get()
  {
    StaticThrowableStorage.initCurrent(_singleton_);
    return _singleton_;
  }


/*--------------------------  constructors  ---------------------------*/

  /**
   * Constructor of this exception without detail message.
   */
 /*@ public behavior
   @ requires true;
   @ assignable \everything;
   @ ensures getMessage() == null;
   @*/
  public ConstraintExceededException() { }

  @Override
  public ConstraintExceededException getSingleton() { return _singleton_; }

  /**
   * Constructor of this exception with detail message.
   *
   * @param description The detail message or null if none.
   */
 /*@ public behavior
   @ requires true;
   @ assignable \everything;
   @ ensures getMessage() == description;
   @*/
  public ConstraintExceededException(String description)
  {
    super(description);
  }
}
