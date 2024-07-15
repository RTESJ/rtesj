/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.device;

import javax.realtime.ActiveEvent;
import javax.realtime.AsyncBaseEventHandler;
import javax.realtime.AsyncEvent;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticIllegalStateException;

/**
 * This class provides second level handling for external events such as
 * interrupts.  A happening can be triggered by an
 * interrupt service routine or from native code.  Application-defined
 * {@code Happenings} can be identified by an application-provided name
 * or a system-provided {@code id}, both of which must be unique.  A
 * system {@code Happening} has a name provided by the system which
 * is a string beginning with {@code @}.
 *
 * @since RTSJ 2.0
 */
public class Happening
  extends AsyncEvent
  implements ActiveEvent<Happening, HappeningDispatcher>
{
  /**
   * Finds an active happening by its name.
   *
   * @param name Identifies the happening to get.
   *
   * @return a reference to the happening with the given {@code name},
   *         or {@code null}, if no happening with the given name is found.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code name} is {@code null}.
   */
  static public Happening getHappening(final String name)
  {
    return null;
  }

  /**
   * Determines whether or not there is an active happening with {@code name}
   * given as parameter.
   *
   * @param name A string that might name an active happening.
   *
   * @return {@code true} only when there is a registered happening with the
   * given {@code name}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code name} is {@code null}.
   */
  public static boolean isHappening(String name)
  {
    return false;
  }

  /**
   * Sets up a mapping between a {@code name} and a system-dependent ID.
   * This can be called either in native code that
   * sets up an interrupt service routine to link it with a
   * {@code happening}.  Once created, it cannot be removed.
   *
   * <p>This must take no more than linear time in the number of ID
   * ({@code n}) registered, but should be {@code O(log2(n))}.
   *
   * @param name A string to name a happening.
   *
   * @return an ID assigned by the system.
   *
   * @throws javax.realtime.StaticIllegalStateException when
   *         {@code name} is already registered.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code name} is {@code null}.
   */
  static public int createId(final String name)
    throws StaticIllegalStateException
  {
    return -1;
  }

  /**
   * Obtains the ID of {@code name}, when one exists or
   * -1, when {@code name} is not registered.
   *
   * <p>This must take no more than linear time in the number of ID
   * ({@code n}) registered, but should be {@code O(log2(n))}.
   *
   * @param name A happening name string.
   *
   * @return The ID, or -1 when no happening is found with that name.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code name} is {@code null}.
   */
  static public int getId(final String name)
  {
    return -1;
  }

  /**
   * Gets the external event corresponding to a given {@code id}.
   *
   * @param id The identifier of a registered signal.
   * @return the signal corresponding to {@code id}.
   */
  public static Happening get(int id)
  {
    return null;
  }

  /**
   * Gets the external event corresponding to a given name.
   *
   * @param name The name of a registered signal.
   *
   * @return the signal corresponding to {@code name}.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code name} is {@code null}.
   */
  public static Happening get(String name)
  {
    return null;
  }

  /**
   * Causes the event dispatcher corresponding to {@code happeningId}
   * to be scheduled for execution. The implementation should be simple enough
   * so that it can be done in the context of an
   * interrupt service routine.
   *
   * <p> {@code trigger()} and any native code analog to it interact
   * with other {@link javax.realtime.ActiveEvent} code effectively as
   * if {@code trigger()} signals a POSIX counting semaphore that the
   * happening is waiting on.
   *
   * <p>
   * The implementation is encouraged to create (and document) a native code
   * analog to this method that can be used without a Java context.
   *
   * <p> This method must execute in constant time.
   *
   * @param id Identifies which happening to trigger.
   *
   * @return {@code true} when a happening with ID {@code happeningId}
   *         was found, {@code false} otherwise.
   */
  public static boolean trigger(int id)
  {
    Happening happening = get(id);
    if (happening == null)
      {
        return false;
      }
    else
      {
        happening.trigger();
        return true;
      }
  }


  /**
   * Creates a happening with the given name.
   *
   * @param name A string to name the happening.
   *
   * @param dispatcher To use when being triggered.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code name} is {@code null} or does not match the pattern
   *         full identifier naming convention, i.e., package plus
   *         {@code name}.  An implementation may throw this exception for
   *         all names starting with java. and javax.
   */
  public Happening(String name, HappeningDispatcher dispatcher)
    throws StaticIllegalArgumentException
  {
    super();
  }

  /**
   * Creates a happening with the given {@code name} and the default dispatcher.
   *
   * @param name A string to name the happening.
   *
   * @throws javax.realtime.StaticIllegalArgumentException when
   *         {@code name} is {@code null} or does not match the pattern full
   *         type naming convention, i.e., package plus {@code name}.
   *         An implementation may throw this exception for all names
   *         starting with java. and javax.
   */
  public Happening(String name) throws StaticIllegalArgumentException
  {
    super();
  }


  /**
   * Gets the number of this happening.
   *
   * @return the happening number or -1, when not registered.
   */
  public final int getId()
  {
    return -1;
  }

  /**
   * Gets the name of this happening.
   *
   * @return the name of this happening.
   */
  public String getName()
  {
    return null;
  }

  /**
   * Determines the activation state of this happening,
   * i.e., if it has been started.
   *
   * @return {@code true} when active; {@code false} otherwise.
   */
  @Override
  public boolean isActive() { return false; }

  /**
   * Determines whether or not this {@code happening} is both active an enabled.
   *
   * @return {@code true} when this {@code happening} is both active and
   *         enabled; {@code false} otherwise.
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
   * Starts this {@code happening}, i.e., changes its state to the active and enabled.
   * Once a happening is started for the first time, when it is in a scoped
   * memory it increments the scope count of that scope; otherwise, it becomes
   * a member of the root set.  An active and enabled happening dispatches its
   * handlers when fired.
   *
   * @see #stop()
   *
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@code happening} has already been started or its
   *         {@code name} is already in use by another happening that has
   *         been started.
   */
  @Override
  public void start()
    throws StaticIllegalStateException
  {
  }

  /**
   * Starts this {@code happening}, but leaves it in the disabled state.
   * When fired before being enabled, it does not dispatch its handlers.
   *
   * @param disabled true for starting in a disabled state.
   *
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@code happening} has already been started.
   *
   * @see #stop()
   */
  @Override
  public void start(boolean disabled)
    throws StaticIllegalStateException
  {
  }

  /**
   * Stops this {@code happening} from responding to the {@code fire}
   * and {@code trigger} methods.
   *
   * @return {@code true} when {@code this} is in the
   *         <em>enabled</em> state; {@code false} otherwise.
   *
   * @throws javax.realtime.StaticIllegalStateException when this
   *         {@code happening} is not active.
   */
  @Override
  public boolean stop()
    throws StaticIllegalStateException
  {
    return false;
  }


  /**
   * Causes the event dispatcher associated with {@code this}
   * to be scheduled for execution. The implementation should be simple
   * enough so that it can be done in the context of an
   * interrupt service routine.
   *
   * <p> This method must execute in constant time.
   */
  public void trigger()
  {
  }

  @Override
  public HappeningDispatcher getDispatcher()
  {
    return null;
  }

  @Override
  public HappeningDispatcher setDispatcher(HappeningDispatcher dispatcher)
  {
    return null;
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
