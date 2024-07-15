/*------------------------------------------------------------------------*
 * Copyright 2018, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime;

class StaticThrowableProxy extends Throwable
{
  private static final long serialVersionUID = -6191724778827120943L;

  private final Class<StaticThrowable<?>> type_;

  public StaticThrowableProxy(StaticThrowable<?> throwable)
  {
    super(throwable.getMessage(), throwable.getCause());
    setStackTrace(throwable.getStackTrace());
    type_ = (Class<StaticThrowable<?>>)throwable.getClass();
  }

  Object readResolve()
  {
    StaticThrowableStorage throwable = StaticThrowableStorage.getCurrent();
    throwable.setLastThrown(type_).
              initMessage(getMessage()).
              initCause(getCause()).
              setStackTrace(getStackTrace());
    return throwable.getLastThrown();
  }

  @Override
  public Throwable fillInStackTrace() { return this; }
}
