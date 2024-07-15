/*------------------------------------------------------------------------*
 * Copyright 2017-2018, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/

package javax.realtime;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A version of {@code SecurityException} to be thrown by RTSJ methods
 * that does not require allocation.
 *
 * @since RTSJ 2.0
 */
public class StaticSecurityException
  extends SecurityException
  implements StaticThrowable<StaticSecurityException>
{

  private static final long serialVersionUID = 1L;

  private static final StaticSecurityException _singleton_ =
    new StaticSecurityException();

  /**
   * Gets the preallocated version of this  {@code Throwable}.  Allocation is
   * done in memory that acts like {@link ImmortalMemory}.  The message,
   * cause, and the stack trace are cleared.  It should be initialized
   * before throwing.
   *
   * @return the preallocated exception.
   */
  public static StaticSecurityException get()
  {
    return _singleton_;
  }

  StaticSecurityException()
  {
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
  public StaticSecurityException getSingleton() { return _singleton_; }
}
