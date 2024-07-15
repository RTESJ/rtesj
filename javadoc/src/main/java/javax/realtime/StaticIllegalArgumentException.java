/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A version of {@code IllegalArgumentException} to be thrown by RTSJ methods
 * that does not require allocation.
 *
 * @since RTSJ 2.0
 */
public class StaticIllegalArgumentException
  extends IllegalArgumentException
  implements StaticThrowable<StaticIllegalArgumentException>
{
  private static final long serialVersionUID = 4241813139693470083L;

  private static final StaticIllegalArgumentException _singleton_ =
    new StaticIllegalArgumentException();

  /**
   * Gets the preallocated version of this  {@code Throwable}.  Allocation is
   * done in memory that acts like {@link ImmortalMemory}.  The message,
   * cause, and the stack are cleared.  It should be initialized
   * before throwing.
   *
   * @return the preallocated exception.
   */
  public static StaticIllegalArgumentException get()
  {
    return _singleton_;
  }

  StaticIllegalArgumentException() {}

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
    StaticThrowableStorage.getCurrent().getCause();
    return this;
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
  public void printStackTrace(PrintWriter writer)
  {
    StaticThrowableStorage.getCurrent().printStackTrace(writer);
  }

  @Override
  public StaticIllegalArgumentException getSingleton() { return _singleton_; }
}
