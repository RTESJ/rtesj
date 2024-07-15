/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A base class for all unchecked exceptions defined in the specification
 * which do not extend a conventional Java exception.
 *
 * @since RTSJ 2.0
 */
public abstract class StaticRuntimeException
  extends RuntimeException
{
  private static final long serialVersionUID = -987579557586600637L;

  /**
   * Enable this class to be extended.
   */
  protected StaticRuntimeException()
  {
  }

 /**
   * Enable this class to be extended.
   *
   * @param message Text to descript the exception.
   */
  protected StaticRuntimeException(String message)
  {
    super(message);
  }

  public abstract <T extends StaticThrowable<T>> T getSingleton();

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
}
