/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A marker interface to indicate that a {@code Throwable} is intended to
 * be created once and reused.  {@code Throwables} that implement this
 * interface keep their state in a {@code ThreadLocal}
 * data structure instead of the object itself.  This means that data is
 * only valid until the next {@code StaticThrowable} is thrown in the
 * context of the current thread.  Instances of {@link AsyncBaseEventHandler}
 * always have some instance of {@code Thread} when executing.
 * Having a marker interface makes it easier to provide checking tools to
 * ensure the proper throw sequence for all {@code Throwables} thrown from
 * application code.
 * <p>
 * {@code Throwables} which implement this interface should define a
 * {@code get()} method that returns the singleton {@code Throwable}
 * of that class.  It should also fill the stack backtrace and
 * clear the message and cause.
 * <p>
 * An application which throws a static exception should use the following
 * paradigm:
 * <p>
 * {@code throw LateStartException.get().init(message, cause);}
 * <p>
 * The message must be initialized before the cause, because initMessage is
 * defined on {@code StaticThrowable} but not {@code Throwable}. Setting the
 * message and the cause are both optional.
 *
 * <p> Applications which define a static throwable by implementing
 * {@code StaticThrowable} should extend one of predefined classes:
 * {@link StaticError}, {@link StaticCheckedException}, or
 * {@link StaticRuntimeException}.  When this is not possible because
 * one needs to extend an existing conventional Java exception, the new
 * throwable must override its methods and redirect them to the
 * local instance of {@link StaticThrowableStorage}.  For example,
 * the following code snippet reimplements {@code initMessage} using
 * {@code StaticThrowableStorage}:
 * <pre>
 * public Throwable initMessage(String message)
 * {
 *   StaticThrowableStorage.getCurrent().initMessage(message);
 *   return this;
 * }
 * </pre>
 * Stack trace, message, and cause must be stored in and retrieved
 * from this thread's local storage structure.
 *
 * @see ConfigurationParameters
 *
 * @since RTSJ 2.0
 */
public interface StaticThrowable<T extends StaticThrowable<T>>
{
  /**
   * Store the message of the calling exception in the instance of
   * {@code StaticThrowableStorage} associated with this task.
   * This is the only method not defined in {@code java.lang.Throwable}.
   *
   * @return {@code this} object.
   *
   * @since RTSJ 2.0 implemented by all static throwables
   */
  @Hidden
  public default T init()
  {
    StaticThrowableStorage.initCurrent(this).initMessage("");
    return (T)this;
  }

  /**
   * Store the message of the calling exception in the instance of
   * {@code StaticThrowableStorage} associated with this task.
   * This is the only method not defined in {@code java.lang.Throwable}.
   *
   * @param message Text to be saved describing the exception's cause.
   *
   * @return {@code this} object.
   *
   * @since RTSJ 2.0 implemented by all static throwables
   */
  @Hidden
  public default T init(String message)
  {
    StaticThrowableStorage.initCurrent(this).initMessage(message);
    return (T)this;
  }

 /**
   * Store the message of the calling exception in the instance of
   * {@code StaticThrowableStorage} associated with this task.
   * This is the only method not defined in {@code java.lang.Throwable}.
   *
   * @param message Text to be saved describing the exception's cause.
   *
   * @param cause Another throwable that lead to this one being throwm.
   *
   * @return {@code this} object.
   *
   * @since RTSJ 2.0 implemented by all static throwables
   */
  @Hidden
  public default T init(String message, Throwable cause)
  {
    StaticThrowableStorage.initCurrent(this).initMessage(message).initCause(cause);
    return (T)this;
  }


  /**
   * Store the message of the calling exception in the instance of
   * {@code StaticThrowableStorage} associated with this task.
   * This is the only method not defined in {@code java.lang.Throwable}.
   *
   * @param cause Another throwable that lead to this one being throwm.
   *
   * @return {@code this} object.
   *
   * @since RTSJ 2.0 implemented by all static throwables
   */
  @Hidden
  public default T init(Throwable cause)
  {
    StaticThrowableStorage.initCurrent(this).initMessage(null).initCause(cause);
    return (T)this;
  }

  /**
   * Replace this objected with a special transport object when
   * serializing.
   *
   * @return the proxy object.
   */
  public default Object writeReplace()
  {
    return null;
  }

  /**
   * For the case of legacy code that creates an RTSJ exception
   * explicity, this provides a means of obtaining its singleton version.
   *
   * @return the singleton version of this exception.
   *
   * @since RTSJ 2.0 implemented by all static throwables
   */
  public StaticThrowable<?> getSingleton();

  /**
   * Determine whether or not this is the static instance of this
   * {@code Throwable}.
   *
   * @return {@code true} when it is the singleton instance and {@code false}
   *          otherwise.
   *
   * @since RTSJ 2.0 implemented by all static throwables
   */
  public default boolean isStatic() { return this == getSingleton(); }

  /**
   * Gets the message describing the exception's cause from thread
   * local memory.
   *
   * @return the message when this was the last method thrown and
   * message was set for it or {@code null}.
   */
  public String getMessage();


  /**
   * Subclasses may override this message to get an error message that
   * is localized to the default locale.
   * <p>
   * By default it returns {@code getMessage()}.
   *
   * @return the value of {@code getMessage()}.
   */
  public String getLocalizedMessage();


  /**
   * Store the cause of calling exception in the instance of
   * {@code StaticThrowableStorage} associated with this task.
   *
   * @param causingThrowable The reason why this {@code Throwable}
   *        gets thrown.
   *
   * @return the reference to this {@code Throwable}.
   *
   * @throws StaticIllegalArgumentException when the cause is this
   *         {@code Throwable} itself.
   */
  public Throwable initCause(Throwable causingThrowable);


  /**
   * Gets the cause from thread local memory of the calling exception or
   * {@code null} when no cause was set.  The cause is another
   * exception that was caught before the current exception is to be thrown.
   *
   * @return the cause when this was the last thrown exception and
   * cause was set or {@code null}.
   */
  public Throwable getCause();


  /**
   * Calls into the virtual machine to capture the current stack trace in
   * the instance of {@code StaticThrowableStorage} associated with this task.
   *
   * @return a reference to this {@code Throwable}.
   */
  public Throwable fillInStackTrace();


  /**
   * This method enables overriding the stack trace that was filled
   * during construction of this object. It is intended to be used in
   * a serialization context when the stack trace of a remote
   * exception should be treated like a local.
   *
   * @param new_stackTrace the stack trace to be used as replace.
   *
   * @throws NullPointerException when new_stackTrace or any element of
   * new_stackTrace is {@code null}.
   */
  public void setStackTrace(StackTraceElement[] new_stackTrace)
    throws NullPointerException;


  /**
   * Gets the stack trace created by fillInStackTrace for this
   * {@code Throwable} from the current thread local storage as an
   * array of StackTraceElements.
   * <p>
   * The stack trace does not need to contain entries for all methods
   * that are actually on the call stack, the virtual machine may
   * decide to skip some stack trace entries.  Even an empty array is
   * a valid result of this function.
   * <p>
   * Repeated calls of this function without intervening calls to
   * fillInStackTrace will return the same result.
   *
   * <p> When memory areas of the RTSJ are used (see {@link MemoryArea}),
   * and this {@code Throwable} was allocated in a
   * different memory area than the current allocation context, the
   * resulting stack trace will be allocated in either the same memory
   * area {@code this} was allocated in or the current memory area,
   * depending on which is the least deeply nested, thereby creating
   * objects that are assignment compatible with both areas.
   *
   * @return An array representing the stack trace when this was the
   * last message thrown or an empty trace array, but never {@code null}.
   */
  public StackTraceElement[] getStackTrace();


  /**
   * Prints stack trace of this {@code Throwable} to System.err.
   * <p>
   * The printed stack trace contains the result of {@code toString()} as the
   * first line followed by one line for each stack trace element that
   * contains the name of the method or constructor, optionally
   * followed by the source file name and source file line number when
   * available.
   */
  public void printStackTrace();


  /**
   * Prints the stack trace of this {@code Throwable} to the given stream.
   * <p>
   * The printed stack trace contains the result of {@code toString()} as the
   * first line followed by one line for each stack trace element that
   * contains the name of the method or constructor, optionally
   * followed by the source file name and source file line number when
   * available.
   *
   * @param stream The stream to print to.
   */
  public void printStackTrace(PrintStream stream);


  /**
   * Prints the stack trace of this {@code Throwable} to the given PrintWriter.
   * <p>
   * The printed stack trace contains the result of {@code toString()} as the
   * first line followed by one line for each stack trace element that
   * contains the name of the method or constructor, optionally
   * followed by the source file name and source file line number when
   * available.
   *
   * @param s The PrintWriter to write to.
   */
  public void printStackTrace(PrintWriter s);

  /**
   * A marker for static throwable support methods that should not be seen
   * in a stack trace.
   */
  @Retention(RUNTIME)
  @Target(METHOD)
  static @interface Hidden {}
}
