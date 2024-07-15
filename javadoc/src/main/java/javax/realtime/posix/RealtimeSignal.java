/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.posix;

import java.util.ArrayList;
import java.util.HashMap;

import javax.realtime.ActiveEvent;
import javax.realtime.AsyncBaseEventHandler;
import javax.realtime.AsyncLongEvent;
import javax.realtime.StaticIllegalStateException;
import javax.realtime.POSIXInvalidSignalException;
import javax.realtime.POSIXInvalidTargetException;
import javax.realtime.POSIXSignalPermissionException;

/*---------------------------------------------------------------------*/

/**
 * An {@link javax.realtime.ActiveEvent} subclass for defining a POSIX realtime
 * signal.  A realtime signal, as defined in POSIX 1003.1b, is a signal
 * that may carry a numerical value with it and that have a special
 * queuing system to handle it.  It is capable of handling a realtime
 * signal with its associated payload, as well as sending it to another
 * process described by its process id.  The name formatting is {@code SIGRT}
 * followed by the number of the realtime signal to be used.  For
 * example {@code SIGRT0}, or {@code SIGRTMIN}, for the first realtime
 * signal, {@code SIGRT1} for the second and {@code SIGRTMAX} for the
 * last one.
 *
 * <p>Here is an example of using of the class:
 * <pre>
 * class SendRealtimeSignalToSelf
 * {
 *   public static void main(String[] args) throws POSIXInvalidSignalException
 *   {
 *     RealtimeSignal rs = RealtimeSignal.get("SIGRTMIN");
 *     LongConsumer lc = (l) -&gt; { System.out.println("The payload is " + l); };
 *     rs.setHandler(new AsyncLongEventHandler(lc));
 *     rs.start(); // register the signal
 *     rs.send(Signal.getProcessId(), 420); // send the signal this process
 *     rs.disable(); //disable the signal, so the handler no longer executes
 *     rs.send(Signal.getProcessId(), 1997);
 *     // the signal is sent to this process, it will be handled, but
 *     // the handlers will not be executed
 *     rs.stop(); // deregister the signal
 *   }
 * }</pre>
 *
 * The current state of the implementation of the POSIX mechanism on most
 * system does not enable guaranteeing that one can send or receive
 * realtime signal with payloads whose size is above 16 bits.
 *
 * In the following, the system numeric value of the realtime signal is
 * referred to as ID.  For example, in most POSIX systems,
 * {@code SIGRTMIN}'s value is 34.
 * <p>
 * This class requires the following permissions:
 * <table border=1 cellpadding=4 width=95%
 *        summary="POSIXPermissions required by RealtimeSignal.">
 * <tr>
 * <th>Method</th>
 * <th>POSIXPermission Action</th>
 * </tr>
 *
 * <tr>
 *   <td>{@link RealtimeSignal#addHandler}</td>
 *   <td>handle</td>
 * </tr>
 * <tr>
 *   <td>{@link RealtimeSignal#setHandler}</td>
 *   <td>handle, override</td>
 * </tr>
 * <tr>
 *   <td>{@link RealtimeSignal#removeHandler}</td>
 *   <td>override</td>
 * </tr>
 * <tr>
 *   <td>{@link RealtimeSignal#send}</td>
 *   <td>send</td>
 * </tr>
 * <tr>
 *   <td>{@link RealtimeSignal#start}</td>
 *   <td>control</td>
 * </tr>
 * <tr>
 *   <td>{@link RealtimeSignal#stop}</td>
 *   <td>control</td>
 * </tr>
 * </table>
 *
 * @see Signal
 *
 * @see RealtimeSignalDispatcher
 *
 * @since RTSJ 2.0
 */
public final class RealtimeSignal
  extends AsyncLongEvent
  implements ActiveEvent<RealtimeSignal, RealtimeSignalDispatcher>
{
  /** A lookup table for realtime signals based on their name. */
  private static HashMap<String, RealtimeSignal> _signals_ =
    new HashMap<String, RealtimeSignal>();

  /** A lookup table for realtime signals based on their ID. */
  private static ArrayList<RealtimeSignal> _signal_by_id_ =
    new ArrayList<RealtimeSignal>();

  /**
   * Determines whether or not the given name represents a realtime
   * signal.
   *
   * @param name The name of the signal check.
   * @return {@code true} when a signal with the given name is defined, but
   *         {@code false} in all other cases.
   */
  public static boolean isRealtimeSignal(String name)
  {
    synchronized (_signals_) { return _signals_.containsKey(name); }
  }

  /**
   * Determines whether or not the given id represents a realtime
   * signal.
   *
   * @param id The numerical signal identifier to check.
   * @return {@code true} when a signal with the given id is defined, but
   *         {@code false} in all other cases.
   */
  public static boolean isRealtimeSignal(int id)
  {
    return false;
  }

  /**
   * Gets the ID of a supported POSIX realtime signal by its name.
   *
   * @param name The name of the signal whose ID should be determined.
   * @return the ID of the signal named by {@code name}.
   *
   * @throws POSIXInvalidSignalException when no signal with the given
   *         name exists or {@code name} is {@code null}.
   */
  public static int getId(String name) throws POSIXInvalidSignalException
  {
    RealtimeSignal signal = get(name);
    if (signal == null) { throw POSIXInvalidSignalException.get(); }
    else                { return signal.getId(); }
  }

  /**
   * Gets a POSIX realtime signal by its name.
   *
   * @param name The name of the signal to get.
   * @return the realtime signal with name.
   *
   * @throws POSIXInvalidSignalException when no signal with the given
   *         name exists or {@code name} is {@code null}.
   */
  public static RealtimeSignal get(String name)
    throws POSIXInvalidSignalException
  {
    synchronized (_signals_) { return _signals_.get(name); }
  }

  /**
   * Gets a POSIX realtime signal by its ID.
   *
   * @param id The identifier of a POSIX realtime signal.
   * @return the realtime signal corresponding to ID or {@code null} when
   *         no signal with that ID exists.
   *
   * @throws POSIXInvalidSignalException when no signal with the given
   *         identifier {@code id} exists.
   */
  public static RealtimeSignal get(int id) throws POSIXInvalidSignalException
  {
    RealtimeSignal result = _signal_by_id_.get(id);
    if (result == null) { throw POSIXInvalidSignalException.get(); }
    else                { return result; }
  }

  /**
   * Determines the lowest system id for realtime signals.
   *
   * @return the equivalent of {@link #getId(String)} called with the string
   *         {@code "SIGRTMIN"} or {@code "SIGRT0"}.
   */
  public static int getMinId() { return -1; }

  /**
   * Determines the highest system id for realtime signals.
   *
   * @return the equivalent of {@link #getId(String)} called with the string
   *         {@code "SIGRTMAX"}.
   */
  public static int getMaxId() { return -1; }

  /**
   * Creates an new {@link RealtimeSignal} instance.
   *
   * @param name The string to name the realtime signal.
   * @param id The identifier of the realtime signal.
   */
  RealtimeSignal(String name, int id) { }

  /**
   * Gets the name of this realtime signal.
   *
   * @return the ID of this signal.
   */
  public int getId() { return -1; }

  /**
   * Gets the name of this signal.
   *
   * @return the name of this signal.
   */
  public final String getName() { return null; }

  @Override
  public RealtimeSignalDispatcher getDispatcher()
  {
    return null;
  }

  @Override
  public
    RealtimeSignalDispatcher setDispatcher(RealtimeSignalDispatcher dispatcher)
  {
    return null;
  }

  /**
   * Determines the activation state of this signal,
   * i.e., whether or not it has been started.
   *
   * @return {@code true} when active; {@code false} otherwise.
   */
  @Override
  public boolean isActive() { return false; }

  /**
   * Determines the firing state (releasing or skipping) of this signal,
   * i.e., whether or not is active and enabled.
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
   * Starts this {@link RealtimeSignal}, i.e., changes to a running state:
   * active and enabled.  An active realtime signal is a source of activation
   * for its memory area and is a member of the root set when in the heap.
   * An active signal can be triggered, but it must be running to dispatch its
   * handlers.
   *
   * @see #stop()
   *
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@link RealtimeSignal} is active.
   */
  @Override
  public final synchronized void start()
    throws StaticIllegalStateException
  {
  }


  /**
   * Starts this {@link RealtimeSignal}, i.e., changes to an active state.
   * An active realtime signal is a source of activation for its memory area
   * and is a member of the root set when in the heap.  When called with
   * {@code disabled} equal to {@code false}, it will also be running.
   * An active signal can be triggered, but it must be running to dispatch
   * its handlers.
   *
   * @param disabled {@code true} for starting in a disabled state.
   *
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@link RealtimeSignal} is active.
   *
   * @see #stop()
   */
  @Override
  public final synchronized void start(boolean disabled)
    throws StaticIllegalStateException
  {
  }


  /**
   * Stops this {@code RealtimeSignal}, i.e., change its state to inactive.
   * A stopped realtime signal ceases to be a source of activation and no
   * longer causes any {@link javax.realtime.ActiveEvent} attached to
   * it to be a source of activation.
   *
   * @return {@code true} when {@code this} was
   *         <em>enabled</em>; {@code false} otherwise.
   *
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@link RealtimeSignal} is inactive.
   */
  @Override
  public final boolean stop()
    throws StaticIllegalStateException
  {
    return false;
  }


  /**
   * Sends this signal to another process.
   *
   * @param pid The identifier of the process to which to send the signal.
   * @param payload The long value associated with a fire.
   *
   * @return {@code true} when successful and {@code false} when not
   *         due to a buffer overflow.
   *
   * @throws POSIXSignalPermissionException when the process does not
   *         have permission to send the target.
   *
   * @throws POSIXInvalidTargetException when the target does not exist.
   */
  public boolean send(long pid, long payload)
    throws POSIXSignalPermissionException, POSIXInvalidTargetException
  {
    return false;
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
