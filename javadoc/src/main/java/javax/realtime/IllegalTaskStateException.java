/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * The exception thrown when a {@link Schedulable} instance attempts an
 * operation which is illegal in its current state.  For instance,
 * changing parameters on such instances are only allowed when the
 * scheduler is not active or the new parameters are consistent with the
 * current scheduler.
 *
 * @since RTSJ 2.0
 */
public class IllegalTaskStateException
  extends IllegalThreadStateException
  implements StaticThrowable<IllegalTaskStateException>
{
  private static final long serialVersionUID = -8261804660879684547L;

  private static final IllegalTaskStateException _singleton_ =
    new IllegalTaskStateException();

  /**
   * Gets the preallocated version of this  {@code Throwable}.  Allocation is
   * done in memory that acts like {@link ImmortalMemory}.  The message,
   * cause, and the stack trace are cleared.
   *
   * @return the preallocated exception.
   */
  public static IllegalTaskStateException get()
  {
    return _singleton_;
  }

  IllegalTaskStateException()
  {
    super();
  }

  @Override
  public String getMessage()
  {
    return StaticThrowableStorage.getCurrent().getMessage();
  }

  @Override
  public String getLocalizedMessage()
  {
    return StaticThrowableStorage.getCurrent().getLocalizedMessage();
  }

  @Override
  public Throwable initCause(Throwable causingThrowable)
  {
    StaticThrowableStorage.getCurrent().initCause(causingThrowable);
    return this;
  }

  @Override
  public Throwable getCause()
  {
    return StaticThrowableStorage.getCurrent().getCause();
  }

  @Override
  public Throwable fillInStackTrace()
  {
    StaticThrowableStorage.getCurrent().fillInStackTrace();
    return this;
  }

  @Override
  public void setStackTrace(StackTraceElement[] new_stackTrace)
    throws NullPointerException
  {
    StaticThrowableStorage.getCurrent().setStackTrace(new_stackTrace);
  }

  @Override
  public StackTraceElement[] getStackTrace()
  {
    return StaticThrowableStorage.getCurrent().getStackTrace();
  }

  @Override
  public void printStackTrace()
  {
    StaticThrowableStorage.getCurrent().printStackTrace();
  }

  @Override
  public void printStackTrace(PrintStream stream)
  {
    StaticThrowableStorage.getCurrent().printStackTrace(stream);
  }

  @Override
  public void printStackTrace(PrintWriter writer)
  {
    StaticThrowableStorage.getCurrent().printStackTrace(writer);
  }

  @Override
  public IllegalTaskStateException getSingleton()
  {
    return _singleton_;
  }
}
