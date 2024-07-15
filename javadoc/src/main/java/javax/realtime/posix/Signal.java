/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.posix;

import javax.realtime.ActiveEvent;
import javax.realtime.AsyncBaseEventHandler;
import javax.realtime.AsyncEvent;
import javax.realtime.StaticIllegalStateException;
import javax.realtime.POSIXInvalidSignalException;
import javax.realtime.POSIXInvalidTargetException;
import javax.realtime.POSIXSignalPermissionException;

/**
 * An {@link javax.realtime.ActiveEvent} subclass for defining a POSIX realtime
 * signal.
 * Signal is capable of handling a signal that come from other process as
 * well as sending it to another process described by its process id.
 * The name of all the supported signal begins with {@code SIG}, such as
 * {@code SIGTERM}, {@code SIGABRT}, etc.  The signal that is possible
 * to send or handle are the one available on the system.
 *
 * <p>Here is an example of using of the class:
 * <pre>
 * class SendSignalToSelf
 * {
 *   public static void main(String[] args)
 *     throws POSIXSignalPermissionException,
 *       POSIXInvalidTargetException,
 *       POSIXInvalidSignalException
 *   {
 *     Signal signal = Signal.get("SIGFPE");
 *     Runnable run = () -&gt; { System.out.println("Signal handled!"); };
 *     signal.setHandler(new AsyncEventHandler(run));
 *     signal.start(); // register the signal
 *     signal.send(Signal.getProcessId()); // send the signal to this process
 *     signal.disable(); //disable the signal, to no longer executed the handler
 *     signal.send(Signal.getProcessId());
 *     // the signal to this process, it will be handle, but the handlers
 *     // will not be executed
 *     signal.stop(); // deregister the signal
 *   }
 * }</pre>
 *
 * In the following, ID is used as the system numeric value of the
 * realtime signal, for example, in most POSIX systems, {@code SIGHUP}
 * has the value of 1.
 *
 * <p>Here is the list of all handled signals, but depending of the
 * running system, this set of usable signal may be smaller.
 *
 * <table border=1 cellpadding=4 width=95% summary="Supported signals.">
 * <tr>
 * <th>Signal Name</th>
 * <th>Description</th>
 * </tr>
 *
 * <tr>
 *   <td>SIGHUP</td>
 *   <td>Hangup (POSIX).</td>
 * </tr>
 * <tr>
 *   <td>SIGINT</td>
 *   <td>Interrupt (ANSI).</td>
 * </tr>
 * <tr>
 *   <td>SIGQUIT</td>
 *   <td>Quit (POSIX).</td>
 * </tr>
 * <tr>
 *   <td>SIGILL</td>
 *   <td>Illegal instruction (ANSI).</td>
 * </tr>
 * <tr>
 *   <td>SIGTRAP</td>
 *   <td>Trace trap (POSIX), optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGABRT</td>
 *   <td>Abort (ANSI).</td>
 * </tr>
 * <tr>
 *   <td>SIGBUS</td>
 *   <td>BUS error (4.2 BSD), optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGFPE</td>
 *   <td>Floating point exception.</td>
 * </tr>
 * <tr>
 *   <td>SIGKILL</td>
 *   <td>Kill, unblockable (POSIX).</td>
 * </tr>
 * <tr>
 *   <td>SIGUSR1</td>
 *   <td>User-defined signal 1 (POSIX).</td>
 * </tr>
 * <tr>
 *   <td>SIGSEGV</td>
 *   <td>Segmentation violation (ANSI).</td>
 * </tr>
 * <tr>
 *   <td>SIGUSR2</td>
 *   <td>User-defined signal 2 (POSIX).</td>
 * </tr>
 * <tr>
 *   <td>SIGPIPE</td>
 *   <td>Broken pipe (POSIX)</td>
 * </tr>
 * <tr>
 *   <td>SIGALRM</td>
 *   <td>Alarm clock (POSIX).</td>
 * </tr>
 * <tr>
 *   <td>SIGTERM</td>
 *   <td>Termination (ANSI).</td>
 * </tr>
 * <tr>
 *   <td>SIGSTKFLT</td>
 *   <td>Stack fault.</td>
 * </tr>
 * <tr>
 *   <td>SIGCHLD</td>
 *   <td>Child status has changed (POSIX).</td>
 * </tr>
 * <tr>
 *   <td>SIGCONT</td>
 *   <td>Continue (POSIX), optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGSTOP</td>
 *   <td>Stop, unblockable (POSIX), optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGTSTP</td>
 *   <td>Keyboard stop (POSIX), optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGTTIN</td>
 *   <td>Background read from tty (POSIX), optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGTTOU</td>
 *   <td>Background write to tty (POSIX), optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGURG</td>
 *   <td>Urgent condition on socket (4.2 BSD). Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGXCPU</td>
 *   <td>CPU limit exceeded (4.2 BSD). Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGXFSZ</td>
 *   <td>File size limit exceeded (4.2 BSD). Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGVTALRM</td>
 *   <td>Virtual alarm clock (4.2 BSD). Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGPROF</td>
 *   <td>Profiling alarm clock (4.2 BSD). Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGWINCH</td>
 *   <td>Window size change (4.3 BSD, Sun). Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGIO</td>
 *   <td>I/O now possible (4.2 BSD). Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGPWR</td>
 *   <td>Power failure restart (System V). Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGSYS</td>
 *   <td>Bad system call, optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGIOT</td>
 *   <td>IOT instruction  (4.2 BSD), optional signal, same as SIGABRT.</td>
 * </tr>
 * <tr>
 *   <td>SIGPOLL</td>
 *   <td>Pollable event occurred (System V), same as SIGIO. Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGCLD</td>
 *   <td>Same as SIGCHLD (System V), optional signal.</td>
 * </tr>
 * <tr>
 *   <td>SIGEMT</td>
 *   <td>Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGLOST</td>
 *   <td>Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGCANCEL</td>
 *   <td>Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGFREEZE</td>
 *   <td>Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGLWP</td>
 *   <td>Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGTHAW</td>
 *   <td>Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGWAITING</td>
 *   <td>Not part of POSIX 9945-1-1996 standard.</td>
 * </tr>
 * <tr>
 *   <td>SIGUNUSED</td>
 *   <td>Since glibc 2.26, not defined, same as SIGSYS.</td>
 * </tr>
 * <tr>
 *   <td>SIGINFO</td>
 *   <td>A synonym for SIGPWR.</td>
 * </tr>
 * </table>
 * <p>
 * Since it is possible that several signal name have the same numerical
 * id, the notion of preferred signal is provided.  When several
 * available signal names refers to the same number, the system chooses
 * the name to give to the number as the name that comes first in the
 * list above among all the synonyms.  Thus, the method {@link #get(int)}
 * will returned the preferred name for the signal, even if several
 * signals are available with the same number. A call to the method
 * {@link #get(String)} could also return a Signal whose name is not the
 * name given as parameter, but the preferred name instead.
 * <p>
 * This class requires the following permissions:
 *
 * <table border=1 cellpadding=4 width=95%
 *        summary="POSIXPermissions required by Signal.">
 * <tr>
 * <th>Method</th>
 * <th>POSIXPermission Action</th>
 * </tr>
 *
 * <tr>
 *   <td>{@link Signal#addHandler}</td>
 *   <td>handle</td>
 * </tr>
 * <tr>
 *   <td>{@link Signal#addHandler}</td>
 *   <td>handle, override</td>
 * </tr>
 * <tr>
 *   <td>{@link Signal#removeHandler}</td>
 *   <td>override</td>
 * </tr>
 * <tr>
 *   <td>{@link Signal#send}</td>
 *   <td>send</td>
 * </tr>
 * <tr>
 *   <td>{@link Signal#start}</td>
 *   <td>control</td>
 * </tr>
 * <tr>
 *   <td>{@link Signal#stop}</td>
 *   <td>control</td>
 * </tr>
 * </table>
 *
 * @see RealtimeSignal
 *
 * @see SignalDispatcher
 *
 * @see POSIXPermission
 *
 * @since RTSJ 2.0
 */
public final class Signal
  extends AsyncEvent
  implements ActiveEvent<Signal, SignalDispatcher>
{
  /**
   * Determines if the given name represents a POSIX signal.
   *
   * @param name The string passed as the name of the signal.
   * @return {@code true} when a signal with the given name is defined, but
   *         in all other cases {@code false}.
   */
  public static boolean isSignal(String name)
  {
    return false;
  }

  /**
   * Determines if the given name represents a POSIX signal.
   *
   * @param id The int passed as the numerical identifier of the signal.
   * @return {@code true} when a signal with the given id is defined, but
   *         in all other cases {@code false}.
   */
  public static boolean isSignal(int id)
  {
    return false;
  }


  /**
   * Gets the ID of a supported signal by its name.
   *
   * @param name The {@code name} of the signal for which to search.
   * @return the ID of the signal named by {@code name}.
   *
   * @throws POSIXInvalidSignalException when no signal with the given
   *         name exists or {@code name} is {@code null}.
   */
  public static int getId(String name) throws POSIXInvalidSignalException
  {
    return -1;
  }


  /**
   * Gets a supported signal by its name.
   *
   * @param name The {@code name} identifying the signal to get.
   * @return the signal associated with {@code name}.
   *
   * @throws POSIXInvalidSignalException when no signal with the given
   *         name exists or {@code name} is {@code null}.
   */
  public static Signal get(String name) throws POSIXInvalidSignalException
  {
    return null;
  }


  /**
   * Gets a supported signal by its ID.
   *
   * @param id The identifier of a registered signal.
   * @return the signal corresponding to {@code id} or {@code null} when
   *         no signal with the given {@code id} exists.
   *
   * @throws POSIXInvalidSignalException when no signal with the given
   *         identifier {@code id} exists.
   */
  public static Signal get(int id) throws POSIXInvalidSignalException
  {
    return null;
  }


  /**
   * Obtains the OS Id of the JVM process.  When running in kernel space, the
   * result is VM dependent and must be documented.  This number returned is
   * only usable with the {@link Signal#send} and {@link RealtimeSignal#send}
   * methods.
   *
   * @return the OS process {@code ID}.
   */
  public static long getProcessId()
  {
    return 0L;
  }


  /**
   * Creates an new POSIX signal instance.
   *
   * @param name To be used as name for the realtime signal.
   * @param id To be used as identifier for the realtime signal.
   */
  Signal(String name, int id) { }

  /**
   * Gets the number of this signal.
   *
   * @return the signal number.
   */
  public int getId()
  {
    return -1;
  }


  /**
   * Gets the name of this signal.
   *
   * @return the name of this signal.
   */
  public String getName()
  {
    return null;
  }


  @Override
  public SignalDispatcher getDispatcher()
  {
    return null;
  }

  @Override
  public SignalDispatcher setDispatcher(SignalDispatcher dispatcher)
  {
    return null;
  }


  /**
   * Determines the activation state of this signal,
   * i.e., whether or not it is registered with a dispatcher.
   *
   * @return {@code true} when active; {@code false} otherwise.
   */
  @Override
  public boolean isActive()
  {
    return false;
  }


  /**
   * Determines the firing state, releasing or skipping, of this signal,
   * i.e., whether or not it is active and enabled.
   *
   * @return {@code true} when releasing, {@code false} when skipping.
   */
  @Override
  public boolean isRunning()
  {
    return false;
  }

  @Override
  public void enable() {}

  @Override
  public void disable() {}

  /**
   * Starts this {@link Signal} in the enabled state, i.e., changes its state
   * to running: active and enabled.   An active signal is a source of
   * activation for its memory area and is a member of the root set when in
   * the heap.  A running signal can be triggered.  Entering the active state
   * causes it to be registered with its dispatcher.
   *
   * @see #stop()
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@link Signal} is active.
   */
  @Override
  public void start()
    throws StaticIllegalStateException
  {
  }


  /**
   * Starts this {@link Signal}, i.e., changes to an active state.
   * An active signal is a source of activation when in a scoped memory
   * and is a member of the root set when in the heap. When called with
   * {@code disabled} equal to {@code false}, it will also be running.
   * An active signal can be triggered, but it must be running to dispatch its
   * handlers.
   *
   * @param disabled {@code true} for starting in a disabled state.
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@link Signal} is active.
   * @see #stop()
   */
  @Override
  public void start(boolean disabled)
    throws StaticIllegalStateException
  {
  }


  /**
   * Stops this {@link Signal}. A stopped signal, i.e., inactive signal,
   * ceases to be a source of activation and no longer causes any
   * {@code ActiveEvent} attached to it to be a source of activation.
   * This causes it to be deregistered from its dispatcher.
   *
   * @return {@code true} when {@code this} was <em>enabled</em> and
   *         {@code false} otherwise.
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@link Signal} is inactive.
   */
  @Override
  public boolean stop()
    throws StaticIllegalStateException
  {
    return false;
  }


  /**
   * Sends this signal to another process or process group.
   *
   * <p>
   * On POSIX systems running in user space, the following holds:
   * <ul>
   * <li>when pid is positive, the signal is sent to pid;</li>
   * <li>when pid equals 0, the signal is sent to every process in the
   *     process group of the current process;</li>
   * <li>when pid equals -1, the signal is sent to every process for which
   *     the calling process has permission to send signals, except for
   *     possibly OS-defined system processes; otherwise</li>
   * <li>when pid is less than -1, the signal is sent to every process in
   *     the process group -pid.</li>
   * </ul>
   *
   * <p>
   * POSIX.1-2001 requires the underlying mechanism of {@code signal.send(-1)}
   * to send {@link Signal} to all processes for which the current process may
   * signal, except possibly for some OS-defined system processes.
   *
   * <p> For an RTVM running in kernel space, the meaning of the {@code pid}
   * is implementation dependent, though it should be as closed to the standard
   * definition as possible.
   *
   * @param pid ID of the process to which to send the signal.
   *
   * @throws POSIXSignalPermissionException when the process does not
   *         have permission to send the target.
   *
   * @throws POSIXInvalidTargetException when the target does not exist.
   */
  public void send(long pid)
    throws POSIXSignalPermissionException,
           POSIXInvalidTargetException
  {
  }

  /**
   * {@inheritDoc}
   */
  public void addHandler(AsyncBaseEventHandler handler)
  {
  }

  /**
   * {@inheritDoc}
   */
  public void setHandler(AsyncBaseEventHandler handler)
  {
  }

  /**
   * {@inheritDoc}
   */
  public void removeHandler(AsyncBaseEventHandler handler)
  {
  }
}
