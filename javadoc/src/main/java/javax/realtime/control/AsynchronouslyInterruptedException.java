/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.control;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.realtime.IllegalTaskStateException;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticThrowable;

/**
 * A special exception that is thrown in response to an attempt to
 * asynchronously transfer the locus of control of a schedulable.
 *
 * <p> An instance of {@link javax.realtime.Schedulable} executing a method
 * or constructor, which lists {@link AsynchronouslyInterruptedException}
 * in its {@code throws} clause, can be asynchronously interrupted
 * except when it is executing in the lexical scope of a synchronized
 * statement within that method/constructor. As soon as the  {@code Schedulable}
 * object leaves the lexical scope of the method by calling another
 * method/constructor it may be asynchronously interrupted when the called
 * method/constructor is asynchronously interruptible. (See this
 * chapter's introduction section for the detailed semantics).
 *
 * <p> The asynchronous interrupt is generated for a {@code Schedulable},
 * {@code s}, when the {@code s.interrupt()} method is called
 * or the {@link #fire} method is called of an AIE for which {@code s}
 * has a {@code doInterruptible} method call in progress.
 *
 * <p> When an asynchronous interrupt is generated when the target
 * {@code Schedulable} is executing within an
 * ATC-deferred section, the asynchronous interrupt becomes pending. A
 * pending asynchronous interrupt is delivered when the target
 * {@code Schedulable} next attempts to enter asynchronously interruptible code.
 *
 * <p> Asynchronous transfers of control (ATCs) are intended to allow
 * long-running computations to be terminated without the overhead or
 * latency of polling with {@code java.lang.Thread.interrupted()}.
 *
 * <p> When {@link javax.realtime.RealtimeThread#interrupt}, or
 * {@code AsynchronouslyInterruptedException.fire()} is called, the
 * {@code AsynchronouslyInterruptedException} is compared against
 * any currently pending {@code AsynchronouslyInterruptedException}
 * on the  {@code Schedulable}. When there is none, or when the depth of the
 * {@code AsynchronouslyInterruptedException} is less than the
 * currently pending {@code AsynchronouslyInterruptedException};
 * (i.e., it is targeted at a less deeply nested method call), the new
 * {@code AsynchronouslyInterruptedException} becomes the currently
 * pending {@code AsynchronouslyInterruptedException} and the
 * previously pending {@code AsynchronouslyInterruptedException} is
 * discarded. Otherwise, the new
 * {@code AsynchronouslyInterruptedException} is discarded.
 *
 * <p> When an {@code AsynchronouslyInterruptedException} is
 * caught, the catch clause may invoke the {@code clear()} method
 * on the {@code AsynchronouslyInterruptedException} in which it is
 * interested to see if the exception matches the pending
 * {@code AsynchronouslyInterruptedException}. When so, the pending
 * {@code AsynchronouslyInterruptedException} is cleared for the
 * {@code Schedulable} and {@code clear} returns true. Otherwise,
 * the current AIE remains pending and {@code clear} returns {@code false}.
 *
 * <p> {@code Schedulable.interrupt()} generates the
 * generic {@code AsynchronouslyInterruptedException} which will
 * always propagate outward through interruptible methods until the
 * generic {@code AsynchronouslyInterruptedException} is identified
 * and handled.  The pending state of the generic AIE is per-instance of
 * {@code Schedulable}.
 *
 * <p> Other sources (e.g.,
 * {@code AsynchronouslyInterruptedException.fire()} and
 * {@link Timed}) will generate specific instances of
 * {@code AsynchronouslyInterruptedException} which applications
 * can identify and thus limit propagation.
 */
public class AsynchronouslyInterruptedException
  extends InterruptedException
  implements StaticThrowable<AsynchronouslyInterruptedException>
{
  /**
   *
   */
  private static final long serialVersionUID = 92110783453526785L;

  static class Singleton
  {
    private static final
      javax.realtime.AsynchronouslyInterruptedException _singleton_ =
      new javax.realtime.AsynchronouslyInterruptedException();
  }

  /**
   * Gets the preallocated version of this  {@code Throwable}.  Allocation is
   * done in memory that acts like {@link javax.realtime.ImmortalMemory}.
   * The message and cause are cleared and the stack trace is filled out.
   *
   * @return the preallocated exception.
   */
  public static AsynchronouslyInterruptedException get()
  {
    return null;
  }

  /**
   * Gets the singleton system generic
   * {@code AsynchronouslyInterruptedException} that is generated when
   * {@link javax.realtime.RealtimeThread#interrupt()} is invoked.
   *
   * @return the generic {@code AsynchronouslyInterruptedException}.
   *
   * @throws IllegalTaskStateException when the current thread context
   *         is not an instance of {@link javax.realtime.Schedulable}.
   */
  public static AsynchronouslyInterruptedException getGeneric()
    throws IllegalTaskStateException
  {
    return Singleton._singleton_;
  }

  /**
   * Creates an instance of {@code AsynchronouslyInterruptedException}.
   */
  public AsynchronouslyInterruptedException()
  {
  }

  /**
   * Creates an instance of {@code AsynchronouslyInterruptedException}.
   *
   * @param message A message to identify this instance.
   */
  public AsynchronouslyInterruptedException(String message)
  {
  }

  @Override
  public AsynchronouslyInterruptedException getSingleton() { return null; }

  /**
   * Enables the throwing of this exception. This method is valid only
   * when the caller has a call to {@code doInterruptible} in
   * progress. When invoked when no call to {@code doInterruptible}
   * is in progress, {@code enable} returns {@code false} and does nothing.
   *
   * @return {@code true}, when {@code this} was disabled before the method
   *         was called and the call was invoked whilst the associated
   *         {@code doInterruptible} was in progress, and {@code false}
   *         otherwise.
   */
  public boolean enable()
  {
    return true;
  }

  /**
   * Disables the throwing of this exception. When the {@link #fire}
   * method is called on {@code this} AIE whilst it is disabled,
   * the fire is held pending and delivered as soon as the AIE is
   * enabled and the interruptible code is within an AI-method.  When an
   * AIE is pending when the associated disable method is called, the
   * AIE remains pending, and is delivered as soon as the AIE is enabled
   * and the interruptible code is within an AI-method.
   *
   * <p> This method is valid only when the caller has a call to
   * {@code doInterruptible} in progress. If invoked when no call
   * to {@code doInterruptible} is in progress,
   * {@code disable} returns {@code false} and does nothing.
   *
   * @return {@code true}, when {@code this} was enabled before the method was
   *         called and the call was invoked with the associated
   *         {@code doInterruptible} in progress, and
   *         {@code false} otherwise.
   */
  public synchronized boolean disable()
  {
    return true;
  }

  /**
   * Queries the enabled status of this exception.
   * <p>
   * This method is valid only when the caller has a call to
   * {@code doInterruptible} in progress. If invoked when no call to
   * {@code doInterruptible} is in progress, {@code enable} returns
   * {@code false} and does nothing.
   *
   * @return {@code true}, when this is enabled and the method call was invoked
   *         in the context of the associated {@code doInterruptible}, and
   *         {@code false} otherwise.
   */
  public boolean isEnabled()
  {
    return true;
  }

  /**
   * Generates this exception when its {@code doInterruptible} has been
   * invoked and not completed.
   * When {@code this} is the only outstanding AIE on the {@code schedulable}
   * object that invoked this AIE's {@link #doInterruptible(Interruptible)}
   * method, this AIE becomes that {@code schedulable}'s current AIE.
   * Otherwise, it only becomes the current AIE when it is at a less
   * deep level of nesting compared with the current outstanding AIE.
   * <p>
   * Behaves as if {@code Thread.interrupt()} were called on the task
   * currently operating within this exception's {@code doInterruptible}.
   *
   * @return {@code true}, when {@code this} is not disabled and it has
   *         an invocation
   *         of a {@code doInterruptible} in progress and there is no
   *         outstanding fire request, and {@code false} otherwise.
   */
  public boolean fire()
  {
    return false;
  }

  /**
   * Executes the {@code run()} method of the given {@link Interruptible}.
   * This method may be on the stack in exactly one {@link javax.realtime.Schedulable}
   * object.  An attempt to invoke this method in a {@code schedulable} while it
   * is on the stack of another or the same {@code schedulable} will cause an
   * immediate return with a value of {@code false}.
   *
   * <p> The {@code run()} method of the given {@code Interruptible} is always
   * entered with the exception in the enabled state, but that state can be
   * modified with {@link #enable()} and {@link #disable()}, and the
   * state can be observed with {@link #isEnabled()}.
   *
   * <p> This AIE is cleared on return from {@code doInterruptible}.
   *
   * @param logic An instance of an {@link Interruptible} whose {@code run()}
   *          method will be called.
   *
   * @return {@code true}, when the method call completed normally, and
   *         {@code false}, when another call to {@code doInterruptible}
   *         has not completed.
   *
   * @throws IllegalTaskStateException when called on the generic
   *           {@code AsynchronouslyInterruptedException}.
   *
   * @throws StaticIllegalArgumentException when {@code logic} is
   *         {@code null}.
   *
   * @since RTSJ 2.0 no longer throws an exception when called from a
   *        Java thread.
   */
  public boolean doInterruptible(Interruptible logic) { return true; }

  /**
   * Determines whether or not the {@link #doInterruptible(Interruptible)}
   * method is currently running.
   *
   * @return {@code true} when it is running and {@code false} otherwise.
   */
  boolean isDoInterruptibleInProcess() { return false; }

  /**
   * Atomically checks whether or not {@code this} is pending on the
   * currently executing {@code schedulable}, and when so, makes it
   * non-pending.
   * <p>
   * This method may be called at any time, and in particular need not
   * be called in a {@code try} or {@code catch} block.
   *
   * @return {@code true}, when {@code this} was pending, and
   *         {@code false}, when {@code this} was not pending.
   * @since RTSJ 1.0.1
   * @since RTSJ 2.0 no longer throws an exception when called from a
   *        task that is not an instance of {@link javax.realtime.Schedulable}.
   */
  public boolean clear() { return true; }

  /**
   * Causes a pending {@code AsynchronouslyInterruptedException} to be
   * thrown as a synchronous exception in an ATC-deferred region if one
   * exists.
   *
   * @throws AsynchronouslyInterruptedException if an AIE is pending.
   *
   * @since RTSJ 2.0
   */
  public static void throwPending()
  throws AsynchronouslyInterruptedException { }


  /**
   * Does nothing, since no stacktrace is kept.
   *
   * @return {@code this} instance.
   */
  @Override
  public Throwable fillInStackTrace()
  {
    return this;
  }

  /**
   * Does nothing, since no stacktrace is kept.
   */
  @Override
  public void setStackTrace(StackTraceElement[] new_stackTrace)
    throws NullPointerException
  {
  }

  /**
   * No stacktrace is kept, so none can be returned.
   *
   * @return an empty array.
   */
  @Override
  public StackTraceElement[] getStackTrace()
  {
    return new StackTraceElement[0];
  }

  /**
   * No stacktrace is kept, so a message to that effect is printed.
   */
  @Override
  public void printStackTrace()
  {
    printStackTrace(System.out);
  }

  /**
   * No stacktrace is kept, so a message to that effect is printed.
   *
   * @param stream A {@code PrintStream} for printing
   */
  @Override
  public void printStackTrace(PrintStream stream)
  {
    stream.println("AsyncrhonouslyInterruptedException does no keep a stack trace!");
  }

  /**
   * No stacktrace is kept, so a message to that effect is printed.
   *
   * @param writer A {@code PrintWriter} for printing
   */
  @Override
  public void printStackTrace(PrintWriter writer)
  {
    writer.println("AsyncrhonouslyInterruptedException does no keep a stack trace!");
  }
}
