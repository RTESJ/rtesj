/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * To throw when {@link MemoryArea#newInstance} causes the constructor of
 * the new instance to throw a checked exception.
 *
 * @since RTSJ 2.0
 */
public class ConstructorCheckedException
  extends InstantiationException
  implements StaticThrowable<ConstructorCheckedException>
{
  private static final long serialVersionUID = 495208948313847338L;

  private static final ConstructorCheckedException _singleton_ =
    new ConstructorCheckedException();

  /**
   * Gets the preallocated version of this  {@code Throwable}.  Allocation is
   * done in memory that acts like {@link ImmortalMemory}.  The message,
   * cause, and stack trace are cleared.  It should be initialized
   * before throwing.
   *
   * @return the preallocated exception.
   */
  public static ConstructorCheckedException get()
  {
    return _singleton_;
  }

  private ConstructorCheckedException() {}

  @Override
  public ConstructorCheckedException getSingleton() { return _singleton_; }

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
}
