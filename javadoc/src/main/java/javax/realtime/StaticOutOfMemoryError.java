/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A version of {@code OutOfMemoryError} that does not require
 * allocation.  It should be thrown from all RTSJ memory subclasses
 * except {@link HeapMemory}.  It is up to the implementation as to
 * whether {@code HeapMemory} throws this exception or its parent.
 *
 * @since RTSJ 2.0
 */
public class StaticOutOfMemoryError
  extends OutOfMemoryError
  implements StaticThrowable<StaticOutOfMemoryError>
{
  private static final StaticOutOfMemoryError _singleton_ =
    new StaticOutOfMemoryError();

  /**
   * Gets the preallocated version of this  {@code Throwable}.
   * Allocation is done in memory that acts like {@link ImmortalMemory}.
   * The message, cause, and the stack trace are cleared.
   * It should be initialized before throwing.
   *
   * @return the preallocated exception.
   */
  public static StaticOutOfMemoryError get()
  {
    return _singleton_;
  }

  /**
   *
   */
  private static final long serialVersionUID = -5495210474787471992L;

  private StaticOutOfMemoryError() {}

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
  public Throwable initCause(Throwable cause)
  {
    StaticThrowableStorage.getCurrent().initCause(cause);
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
  public void setStackTrace(StackTraceElement[] trace)
    throws NullPointerException
  {
    StaticThrowableStorage.getCurrent().setStackTrace(trace);
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
  public void printStackTrace(PrintWriter s)
  {
    StaticThrowableStorage.getCurrent().printStackTrace(s);
  }

  @Override
  public StaticOutOfMemoryError getSingleton() { return _singleton_; }
}
