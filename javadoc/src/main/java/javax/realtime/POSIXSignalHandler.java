/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import javax.realtime.posix.Signal;

/**
 * This class enables the use of an AsyncEventHandler to react on the
 * occurrence of POSIX signals.
 *
 * <p> On systems that support POSIX signals fully, the 13 signals
 * required by POSIX will be supported. Any further signals defined in
 * this class may be supported by the system. On systems that do not
 * support POSIX signals, even the 13 standard signals may never be
 * fired.
 *
 * @deprecated since RTSJ 2.0
 */
@Deprecated
public final class POSIXSignalHandler
{
  /*----------------------------  constants  ----------------------------*/

  /**
   * true value that cannot be optimized by the compiler.
   */
  private static final boolean TRUE = (Thread.currentThread() != null);


  /* from the POSIX Standard: (see http://www.unix.org/version3/online.html):

                      Signal  Default Action  Description

                      SIGABRT   A Process abort signal.
                      SIGALRM   T Alarm clock.
                      SIGBUS    A Access to an undefined portion of a memory object.
                      SIGCHLD   I Child process terminated, stopped,
[XSI] [Option Start]  or continued. [Option End]
                      SIGCONT   C Continue executing, when stopped.
                      SIGFPE    A Erroneous arithmetic operation.
                      SIGHUP    T Hangup.
                      SIGILL    A Illegal instruction.
                      SIGINT    T Terminal interrupt signal.
                      SIGKILL   T Kill (cannot be caught or ignored).
                      SIGPIPE   T Write on a pipe with no one to read it.
                      SIGQUIT   A Terminal quit signal.
                      SIGSEGV   A Invalid memory reference.
                      SIGSTOP   S Stop executing (cannot be caught or ignored).
                      SIGTERM   T Termination signal.
                      SIGTSTP   S Terminal stop signal.
                      SIGTTIN   S Background process attempting read.
                      SIGTTOU   S Background process attempting write.
                      SIGUSR1   T User-defined signal 1.
                      SIGUSR2   T User-defined signal 2.
[XSI] [Option Start]  SIGPOLL   T Pollable event.
                      SIGPROF   T Profiling timer expired.
                      SIGSYS    A Bad system call.
                      SIGTRAP   A Trace/breakpoint trap. [Option End]
                      SIGURG    I High bandwidth data is available at a socket.
[XSI] [Option Start]  SIGVTALRM T Virtual timer expired.
                      SIGXCPU   A CPU time limit exceeded.
                      SIGXFSZ   A File size limit exceeded. [Option End]

  Strangely enough, there is no one-to-one mapping from the POSIX Standard to the signals defined here:
  * standard signals: *
      case SIGHUP      :   X
      case SIGINT      :   X
      case SIGQUIT     :   X
      case SIGILL      :   X
      case SIGABRT     :   X
      case SIGFPE      :   X
      case SIGKILL     :   X
      case SIGUSR1     :   X
      case SIGSEGV     :   X
      case SIGUSR2     :   X
      case SIGPIPE     :   X
      case SIGALRM     :   X
      case SIGTERM     :   X
      case SIGCHLD     :   X

      * optional signals: *
      case SIGBUS      :  X
      case SIGCLD      :
      case SIGCONT     :  X
      case SIGEMT      :
      case SIGIOT      :
      case SIGSTOP     :  X
      case SIGSYS      :  X
      case SIGTRAP     :  X
      case SIGTSTP     :  X
      case SIGTTIN     :  X
      case SIGTTOU     :  X

      * deprecated signals: *
      case SIGURG      :  X
      case SIGXCPU     :  X
      case SIGXFSZ     :  X
      case SIGVTALRM   : [X]
      case SIGPROF     :  X
      case SIGWINCH    :
      case SIGIO       :
      case SIGPWR      :
      case SIGPOLL     : [X]
      case SIGCANCEL   :
      case SIGFREEZE   :
      case SIGLOST     :
      case SIGLWP      :
      case SIGTHAW     :
      case SIGWAITING  :

*/


  /**
   * Hangup (POSIX).
   */
  public static final int SIGHUP      = Signal.getId("SIGHUP");

  /**
   * interrupt (ANSI)
   */
  public static final int SIGINT      = Signal.getId("SIGINT");

  /**
   * quit (POSIX)
   */
  public static final int SIGQUIT     = Signal.getId("SIGQUIT");

  /**
   * illegal instruction (ANSI)
   */
  public static final int SIGILL      = Signal.getId("SIGILL");

  /**
   * trace trap (POSIX), optional signal.
   */
  public static final int SIGTRAP     = Signal.getId("SIGTRAP");

  /**
   * Abort (ANSI).
   */
  public static final int SIGABRT     = Signal.getId("SIGABRT");

  /**
   * BUS error (4.2 BSD), optional signal.
   */
  public static final int SIGBUS      = Signal.getId("SIGBUS");

  /**
   * floating point exception
   */
  public static final int SIGFPE      = Signal.getId("SIGFPE");

  /**
   * Kill, unblockable (POSIX).
   */
  public static final int SIGKILL     = Signal.getId("SIGKILL");

  /**
   * User-defined signal 1 (POSIX).
   */
  public static final int SIGUSR1     = Signal.getId("SIGUSR1");

  /**
   * Segmentation violation (ANSI).
   */
  public static final int SIGSEGV     = Signal.getId("SIGSEGV");

  /**
   * User-defined signal 2 (POSIX).
   */
  public static final int SIGUSR2     = Signal.getId("SIGUSR2");

  /**
   * Broken pipe (POSIX).
   */
  public static final int SIGPIPE     = Signal.getId("SIGPIPE");

  /**
   * Alarm clock (POSIX).
   */
  public static final int SIGALRM     = Signal.getId("SIGALRM");

  /**
   * Termination (ANSI).
   */
  public static final int SIGTERM     = Signal.getId("SIGTERM");

  /**
   * Termination (Linux Stack Fault).
   */
  public static final int SIGSTKFLT   = Signal.getId("SIGSTKFLT");

  /**
   * Child status has changed (POSIX).
   */
  public static final int SIGCHLD     = Signal.getId("SIGCHLD");

  /**
   * Continue (POSIX), optional signal.
   */
  public static final int SIGCONT     = Signal.getId("SIGCONT");

  /**
   * Stop, unblockable (POSIX), optional signal.
   */
  public static final int SIGSTOP     = Signal.getId("SIGSTOP");

  /**
   * Keyboard stop (POSIX), optional signal.
   */
  public static final int SIGTSTP     = Signal.getId("SIGTSTP");

  /**
   * Background read from tty (POSIX), optional signal.
   */
  public static final int SIGTTIN     = Signal.getId("SIGTTIN");

  /**
   * Background write to tty (POSIX), optional signal.
   */
  public static final int SIGTTOU     = Signal.getId("SIGTTOU");

  /*
   * Urgent condition on socket (4.2 BSD).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGURG      = Signal.getId("SIGURG");

  /*
   * CPU limit exceeded (4.2 BSD).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGXCPU     = Signal.getId("SIGXCPU");

  /*
   * File size limit exceeded (4.2 BSD).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGXFSZ     = Signal.getId("SIGXFSZ");

  /*
   * Virtual alarm clock (4.2 BSD).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGVTALRM   = Signal.getId("SIGVTALRM");

  /*
   * Profiling alarm clock (4.2 BSD).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGPROF     = Signal.getId("SIGPROF");

  /*
   * Window size change (4.3 BSD, Sun).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGWINCH    = Signal.getId("SIGWINCH");

  /*
   * I/O now possible (4.2 BSD).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGIO       = Signal.getId("SIGIO");

  /*
   * Power failure restart (System V).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGPWR      = Signal.getId("SIGPWR");

  /**
   * Bad system call, optional signal.
   */
  public static final int SIGSYS      = Signal.getId("SIGSYS");

  /**
   * IOT instruction  (4.2 BSD), optional signal.
   */
  public static final int SIGIOT      = Signal.getId("SIGIOT");  //same as SIGABRT

  /*
   * Pollable event occurred (System V).
   *
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGPOLL     = Signal.getId("SIGPOLL"); //same as SIGIO

  /**
   * Same as SIGCHLD (System V), optional signal.
   */
  public static final int SIGCLD      = Signal.getId("SIGCLD"); //same as SIGCHLD

  /**
   * EMT instruction, optional signal.
   */
  public static final int SIGEMT      = Signal.getId("SIGEMT");

  /*
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGLOST     = Signal.getId("SIGLOST");

  /*
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGCANCEL   = Signal.getId("SIGCANCEL");

  /*
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGFREEZE   = Signal.getId("SIGFREEZE");

  /*
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGLWP      = Signal.getId("SIGLWP");

  /*
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGTHAW     = Signal.getId("SIGTHAW");

  /*
   * @deprecated as of RTSJ 1.0.1 not part of POSIX 9945-1-1996 standard
   */
  @Deprecated
  public static final int SIGWAITING  = Signal.getId("SIGWAITING");


  /*----------------------------  variables  ----------------------------*/

  /**
   * Check whether or not a given signal is defined in this class. The fact that
   * it is defined does, however, not imply that it will be supported
   * by the underlying platform.
   *
   * @param signal the signal number.
   *
   * @return true iff the given system is defined by one of the
   * constants in this class.
   */
 /*@ public behavior
   @ requires true;
   @ assignable \nothing;
   @ ensures
   @   \result <==>
   @     signal == SIGHUP      ||
   @     signal == SIGINT      ||
   @     signal == SIGQUIT     ||
   @     signal == SIGILL      ||
   @     signal == SIGABRT     ||
   @     signal == SIGFPE      ||
   @     signal == SIGKILL     ||
   @     signal == SIGUSR1     ||
   @     signal == SIGSEGV     ||
   @     signal == SIGUSR2     ||
   @     signal == SIGPIPE     ||
   @     signal == SIGALRM     ||
   @     signal == SIGTERM     ||
   @     signal == SIGCHLD     ||
   @     signal == SIGBUS      ||
   @     signal == SIGCLD      ||
   @     signal == SIGCONT     ||
   @     signal == SIGEMT      ||
   @     signal == SIGIOT      ||
   @     signal == SIGSTOP     ||
   @     signal == SIGSYS      ||
   @     signal == SIGTRAP     ||
   @     signal == SIGTSTP     ||
   @     signal == SIGTTIN     ||
   @     signal == SIGTTOU     ||
   @     signal == SIGURG      ||
   @     signal == SIGXCPU     ||
   @     signal == SIGXFSZ     ||
   @     signal == SIGVTALRM   ||
   @     signal == SIGPROF     ||
   @     signal == SIGWINCH    ||
   @     signal == SIGIO       ||
   @     signal == SIGPWR      ||
   @     signal == SIGPOLL     ||
   @     signal == SIGCANCEL   ||
   @     signal == SIGFREEZE   ||
   @     signal == SIGLOST     ||
   @     signal == SIGLWP      ||
   @     signal == SIGTHAW     ||
   @     signal == SIGWAITING  ;
   @
   @ spec_public
   @*/
   static
 /*@
   @ pure
   @*/
   boolean isSignalDefined(int signal) {
   return
      /* standard signals: */
      signal == SIGHUP      ||
      signal == SIGINT      ||
      signal == SIGQUIT     ||
      signal == SIGILL      ||
      signal == SIGABRT     ||
      signal == SIGFPE      ||
      signal == SIGKILL     ||
      signal == SIGUSR1     ||
      signal == SIGSEGV     ||
      signal == SIGUSR2     ||
      signal == SIGPIPE     ||
      signal == SIGALRM     ||
      signal == SIGTERM     ||
      signal == SIGCHLD     ||

      /* optional signals: */
      signal == SIGBUS      ||
      signal == SIGCLD      ||
      signal == SIGCONT     ||
      signal == SIGEMT      ||
      signal == SIGIOT      ||
      signal == SIGSTOP     ||
      signal == SIGSYS      ||
      signal == SIGTRAP     ||
      signal == SIGTSTP     ||
      signal == SIGTTIN     ||
      signal == SIGTTOU     ;

      /* deprecated signals: *//*
      signal == SIGURG      ||
      signal == SIGXCPU     ||
      signal == SIGXFSZ     ||
      signal == SIGVTALRM   ||
      signal == SIGPROF     ||
      signal == SIGWINCH    ||
      signal == SIGIO       ||
      signal == SIGPWR      ||
      signal == SIGPOLL     ||
      signal == SIGCANCEL   ||
      signal == SIGFREEZE   ||
      signal == SIGLOST     ||
      signal == SIGLWP      ||
      signal == SIGTHAW     ||
      signal == SIGWAITING  ;*/
  }


  /**
   * Adds the handler provided to the set of handlers that
   * will be released on the provided signal.
   *
   * @param signal The POSIX signal as defined in the constants SIG*.
   *
   * @param handler The handler to be released on the given signal.
   *
   * @throws IllegalArgumentException when signal is not defined by any
   * of the constants in this class or handler is {@code null}.
   */
 /*@ public behavior
   @ requires true;
   @ assignable \everything;
   @ ensures true;
   @ signals (IllegalArgumentException e)
   @   (handler == null) || (Signal(id) == null);
   @*/
  public static void addHandler(int signal, AsyncEventHandler handler)
  {
  }


  /**
   * Removes a handler that was added for a given signal.
   *
   * @param signal The POSIX signal as defined in the constants SIG*.
   *
   * @param handler The handler to be removed from the given
   * signal. When this handler is {@code null} or has not been added to the
   * signal, nothing will happen.
   *
   * @throws IllegalArgumentException when signal is not defined by any
   * of the constants in this class.
   */
 /*@ public behavior
   @ requires true;
   @ assignable \everything;
   @ ensures true;
   @ signals (IllegalArgumentException e)
   @   (Signal(id) == null);
   @*/
  public static void removeHandler(int signal, AsyncEventHandler handler)
  {
  }


  /**
   * Sets the set of handlers that will be released on the provided
   * signal to the set with the provided handler being the single
   * element.
   *
   * @param signal The POSIX signal as defined in the constants SIG*.
   *
   * @param handler The handler to be released on the given signal,
   * {@code null} to remove all handlers for the given signal.
   *
   * @throws IllegalArgumentException when signal is not defined by any
   * of the constants in this class.
   */
 /*@ public behavior
   @ requires true;
   @ assignable \everything;
   @ ensures true;
   @ signals (IllegalArgumentException e)
   @   (Signal.get(id) == null);
   @*/
  public static void setHandler(int signal, AsyncEventHandler handler)
  {
  }

  /**
   * Make sure a default constructor does not show up in the documentation.
   */
  private POSIXSignalHandler()
  {
  }
}
