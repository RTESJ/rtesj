/*------------------------------------------------------------------------*
 * Copyright 2017-2018, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Provides the methods for managing the thread local memory used
 * for storing the data needed by preallocated throwables, i.e., exceptions
 * and errors which implement {@link StaticThrowable}.  This call is visible
 * so that an application can extend an existing conventional Java throwable
 * and still implement {@code StaticThrowable}; its methods can be implemented
 * using the methods defined in this class.  An application defined throwable
 * that does not need to extend an existing conventional Java throwable should
 * extend one of {@link StaticCheckedException}, {@link StaticRuntimeException},
 * or {@link StaticError} instead.
 *
 * @since RTSJ 2.0
 */
public class StaticThrowableStorage
  extends Throwable
{
  private static final long serialVersionUID = -1204639469315923424L;

  private static final ThreadLocal<StaticThrowableStorage> _storage_ =
    new ThreadLocal<StaticThrowableStorage>()
    {
      @Override
      protected StaticThrowableStorage initialValue()
      {
        Thread thread = RealtimeThread.currentThread();
        if (!(thread instanceof RealtimeThread)||
            RealtimeThread.getCurrentMemoryArea() ==
                 MemoryArea.getMemoryArea(thread))
          {
            ConfigurationParameters config =
              ConfigurationParameters.getDefault();
            return new StaticThrowableStorage(config);
          }
        else
          {
            RealtimeThread rtt = (RealtimeThread)thread;
            // NYI: UNDER DEVELOPMENT: Once configuration parameters are added
            // to realtime threads, parameters should come from the thread.
            // ConfigurationParameters config = rtt.getConfigurationParameters();
            ConfigurationParameters config =
                ConfigurationParameters.getDefault();
            MemoryArea area = MemoryArea.getMemoryArea(rtt);
            Class types[] = new Class[] { ConfigurationParameters.class };
            Constructor<StaticThrowableStorage> constructor;
            try
              {
                constructor = StaticThrowableStorage.class.getConstructor(types);
                Object[] args = new Object[] { config };
                return area.newInstance(constructor, args);
              }
            catch (NoSuchMethodException |
                   SecurityException |
                   IllegalAccessException |
                   InstantiationException |
                   InvocationTargetException e)
              {
                // This will cause a IllegalAssignmentError to be thrown.
                return new StaticThrowableStorage(config);
              }
          }
      }
    };

  /**
   * A means of obtaining the storage object for the current task and throwable.
   *
   * @return the storage object for the current task.
   */
  public static StaticThrowableStorage getCurrent()
  {
    return _storage_.get();
  }

  /**
   * Obtaining the storage object for the current task and initialize it.
   *
   * @return the storage object for the current task.
   */
  public static StaticThrowableStorage initCurrent(StaticThrowable<?> throwable)
  {
    return _storage_.get().reset(throwable).initMessage(null).clearCause();
  }

  StaticThrowableStorage clearCause()
  {
    cause_ = null;
    return this;
  }

  private StaticThrowableStorage(ConfigurationParameters config)
  {
    context_ = null;
    cause_ = null;
    messageLength_ = 0;
    message_ = new char[config.getMessageLength()];
    traceLength_ = 0;
    trace_ = new StackTraceCondensedElement[config.getStackTraceDepth()];
    for (int index = 0; index < trace_.length; index++)
      {
        trace_[index] = new StackTraceCondensedElement(config);
      }
  }

  private StaticThrowable<?> context_;

  private Class<Throwable> cause_;

  private int messageLength_;

  private final char[] message_;

  private int traceLength_;

  private final StackTraceCondensedElement[] trace_;

  /**
   * Clear the message and cause and fill context and stacktrace.
   *
   * @return the storage object for the current task.
   */
  private StaticThrowableStorage reset(StaticThrowable<?> throwable)
  {
    context_ = throwable;
    cause_ = null;
    messageLength_ = 0;
    traceLength_ = 0;
    fillInStackTrace();
    return this;
  }

  /**
   * Capture the exception to be thrown, so as to provide a context for
   * the data stored in this object.
   *
   * @return the storage object for the current task.
   */
  StaticThrowableStorage setLastThrown(StaticThrowable<?> value)
  {
    context_ = value;
    return this;
  }

  /**
   * Capture the exception to be thrown, so as to provide a context for
   * the data stored in this object.
   *
   * @return the storage object for the current task.
   */
  StaticThrowableStorage setLastThrown(Class<StaticThrowable<?>> value)
  {
    try
      {
        Method get = cause_.getMethod("get");
        context_ = (StaticThrowable<?>)get.invoke(null);
      }
    catch (NoSuchMethodException | SecurityException |
           IllegalAccessException | StaticIllegalArgumentException |
           InvocationTargetException e)
      {
      }
    return this;
  }

  /**
   * Determine for what throwable the data is valid;
   *
   * @return the current context for the throwable data stored in this object.
   */
  public StaticThrowable<?> getLastThrown()
  {
    return context_;
  }


  /**
   * Captures the current thread's stack trace and saves it in thread
   * local storage.  Only the part of the stack trace that fits in the
   * preallocated buffer is stored.  This method should be called by a
   * preallocated exception to implement its method of the same name.
   *
   * @return {@code this}
   *
   * NYI: UNDER DEVELOPMENT: JAM-5543 Implementation Under Construction
   * --- this should be done on the native side to avoid allocation.
   */
  @Override
  public Throwable fillInStackTrace()
  {
    // JAM-5543: this will replace code below.
    //traceLength_ = fillStackTrace(trace_);
    if (trace_ != null) // initialization is finished.
      {
        Exception dummy = new Exception();
        // Should skip first stack frame.
        setStackTrace(dummy.getStackTrace());
      }
    return this;
  }

  /**
   * Gets the message from thread local storage that was saved by the last
   * preallocated exception thrown.  This method should be called by a
   * preallocated exception to implement its method of the same name.
   *
   * @return the message.
   */
  @Override
  public String getMessage()
  {
    return String.valueOf(message_, 0, messageLength_);
  }

  /**
   * Saves the message in thread local storage for later retrieval.  Only the
   * part of the message that fits in the preallocated buffer is stored.
   * This method should be called by a preallocated exception to implement
   * its method of the same name.
   *
   * @param message The description to save.
   * @return {@code this}
   */
  public StaticThrowableStorage initMessage(String message)
  {
    if (message == null)
      {
        messageLength_ = 0;
      }
    else
      {
        messageLength_ = Math.min(message_.length, message.length());
        message.getChars(0, messageLength_, message_, 0);
      }
    return this;
  }

  /**
   * Gets the cause from thread local storage that was saved by the last
   * preallocated exception thrown.  The actual exception of cause
   * is not saved, but just a reference to its type.  This returns a newly
   * allocated exception without any valid content, i.e., no valid stack
   * trace.  This method should be called by a preallocated exception to
   * implement its method of the same name.
   *
   * @return the throwable that caused the condition or {@code null}
   *         when none was set.
   */
  @Override
  public Throwable getCause()
  {
    if (cause_ == null)
      {
        return null;
      }
    else if (StaticThrowable.class.isAssignableFrom(cause_))
      {
        try
          {
            Method get = cause_.getMethod("get");
            return (Throwable)get.invoke(null);
          }
        catch (NoSuchMethodException | SecurityException |
               IllegalAccessException | StaticIllegalArgumentException |
               InvocationTargetException e)
          {
            return null;
          }
      }
    else
      {
        try
          {
            Constructor<Throwable> constructor = cause_.getConstructor();
            return (Throwable)constructor.newInstance();
          }
        catch (InstantiationException | NoSuchMethodException |
               SecurityException | IllegalAccessException |
               StaticIllegalArgumentException | InvocationTargetException e)
          {
            return null;
          }
      }
  }

  /**
   * Saves the message in thread local storage for later retrieval.  Only
   * a reference to the exception class is stored.  The rest of its information
   * is lost.  This method should be called by a preallocated exception to
   * implement its method of the same name.
   *
   * @param cause In the case of cascading throwables, the
   *        exception or error that was the original cause.
   * @return {@code this}
   */
  @Override
  public Throwable initCause(Throwable cause)
  {
    if (cause == null) { cause_ = null; }
    else { cause_ = (Class<Throwable>)cause.getClass(); }
    return this;
  }

  /**
   * Gets the stack trace from thread local storage that was saved by the last
   * preallocated exception thrown.  This method should be called by a
   * preallocated exception to implement its method of the same name.
   *
   * @return an array of the elements of the stack trace.
   */
  @Override
  public StackTraceElement[] getStackTrace()
  {
    StackTraceElement[] trace = new StackTraceElement[traceLength_];
    for (int index = 0; index < traceLength_; index++)
      {
        StackTraceCondensedElement element = trace_[index];
        trace[index] = new StackTraceElement(element.getClassName(),
                                             element.getMethodName(),
                                             element.getFileName(),
                                             element.line_number);
      }
    return trace;
  }

  @Override
  public String getLocalizedMessage()
  {
    return getMessage();
  }

  /**
   * Transfer the contents of a StaticThrowableStorage to the current
   * thread context.
   *
   * @return the throwable from the old context.
   */
  public StaticThrowable transfer() { return null; }

  @Override
  public void setStackTrace(StackTraceElement[] stackTrace)
  {
    if (stackTrace == null)
      {
        traceLength_ = 0;
      }
    else
      {
        int count = Math.min(trace_.length, stackTrace.length);
        for (int index = 0; index < count; index++)
          {
            StackTraceCondensedElement element = trace_[index];
            StackTraceElement source = stackTrace[index];
            element.setClassName(source.getClassName());
            element.setMethodName(source.getMethodName());
            element.setFileName(source.getFileName());
            element.line_number = source.getLineNumber();
          }
        traceLength_ = count;
      }
  }

  @Override
  public void printStackTrace()
  {
    printStackTrace(System.err);
  }

  @Override
  public void printStackTrace(PrintStream stream)
  {
    stream.format("%s: %s\n",
                  context_ == null ?
                    "<null> " : context_.getClass().getCanonicalName(),
                  getMessage());
    for (int index = 0; index < traceLength_; index++)
      {
        StackTraceCondensedElement element = trace_[index];
        stream.format("\tat %s.%s(%s:%d)\n",
                      element.getClassName(),
                      element.getMethodName(),
                      element.getFileName(),
                      element.line_number);
      }
    // Print cause, if any
    Throwable cause = getCause();
    if (cause != null) { stream.format("Caused by %s\n", cause); }
  }

  @Override
  public void printStackTrace(PrintWriter writer)
  {
    for (int index = 0; index < traceLength_; index++)
    {
      writer.format("%s: %s\n",
                     context_ == null ?
                       "<null> " : context_.getClass().getCanonicalName(),
                     getMessage());
      StackTraceCondensedElement element = trace_[index];
      writer.format("\tat %s.%s(%s:%d)\n",
                    element.getClassName(),
                    element.getMethodName(),
                    element.getFileName(),
                    element.line_number);
    }
    // Print cause, if any
    Throwable cause = getCause();
    if (cause != null) { writer.format("Caused by %s\n", cause); }
  }

  static private class StackTraceCondensedElement
  {
    public StackTraceCondensedElement(ConfigurationParameters config)
    {
      class_name_ = new char[config.getClassNameLength()];
      method_Name_ = new char[config.getMethodNameLength()];
      file_name_ = new char[config.getFileNameLength()];
    }
    final char[] class_name_;
    int class_name_length_;
    final char[] method_Name_;
    int method_Name_length_;
    final char[] file_name_;
    int file_name_length_;
    int line_number;

    public final String getClassName()
    {
      return String.copyValueOf(class_name_, 0, class_name_length_);
    }

    public final String getMethodName()
    {
      return String.copyValueOf(method_Name_, 0, method_Name_length_);
    }

    public final String getFileName()
    {
      return String.copyValueOf(file_name_, 0, file_name_length_);
    }

    public void setClassName(String name)
    {
      class_name_length_ = fillArray(class_name_, name);
    }

    public void setMethodName(String name)
    {
      method_Name_length_ = fillArray(method_Name_, name);
    }

    public void setFileName(String name)
    {
      file_name_length_ = fillArray(file_name_, name);
    }

    private int fillArray(char[] dest, String source)
    {
      int offset = dest.length < source.length() ?
                   source.length() - dest.length : 0;
      int count = Math.min(dest.length, source.length());
      for (int index = 0; index < count; index++)
        {
          dest[index] = source.charAt(index + offset);
        }
      return count;
    }
 }
}
