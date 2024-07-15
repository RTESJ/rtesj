/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import java.io.Serializable;
import java.util.BitSet;

/**
 * This is class for specifying processor affinity.  It includes a
 * factory that generates {@code Affinity} objects.  With it, the
 * affinity of every task in a JVM can be controlled.
 *
 * <p> An affinity is a set of processors that can be associated
 * with certain types of tasks.  Each task can be associated with an
 * affinity via its {@link SchedulingParameters}.  Groups of these can be
 * assigned an affinity through their {@link RealtimeThreadGroup}.
 *
 * <p> The processor membership of an affinity is immutable.  The tasks
 * associations of an affinity are mutable.  The internal representation
 * of a set of processors in an {@code Affinity} instance is not
 * specified, but the representation that is used to communicate with
 * this class is a {@code BitSet} where each bit corresponds to a
 * logical processor ID.  The relationship between logical and physical
 * processors is beyond the scope of this specification, and may change.
 *
 * <p> The set of affinities created at startup (the predefined set) is
 * visible through the {@link #getPredefinedAffinities(Affinity[])} method.
 * Only the Affinities made available at startup and the Affinities generated
 * using {@link #generate(BitSet)} but with a
 * cardinality of one may be used as parameters for schedulables.
 * These are referred to as \emph{valid} affinities.
 * However, it is still possible to create Affinity instances that are
 * not equals to the ones defined at startup and
 * with a cardinality more than one using {@link #generate(BitSet)}.
 * These affinities are not considered to be valid
 * as they can not be used as parameters for schedulables.
 * The purpose of these invalid affinities is to be used as
 * parameter if a RealtimeThreadGroup instance to limit the processors
 * available to its members.
 *
 * <p>
 * There is no public constructor for this class. All instances must be
 * created by the factory method ({@code generate}).
 *
 * @since RTSJ 2.0
 */
public final class Affinity implements Cloneable, Serializable
{
  /**
   * Determines the minimum array size required to store references to all
   * the predefined processor affinities.
   *
   * @return the minimum array size required to store references to all
   * the predefined affinities.
   */
  public final static int getPredefinedAffinitiesCount() { return 0; }

  /**
   * Equivalent to invoking {@code getPredefinedAffinitySets(null)}.
   * @return an array of the predefined affinities.
   */
  public static final Affinity[] getPredefinedAffinities() { return null; }

  /**
   * Determines what affinities are predefined by the Java runtime.
   *
   * @param dest The destination array, or {@code null}.
   *
   * @return {@code dest} or a newly created array when
   * {@code dest} is {@code null}, populated with references
   * to the predefined affinities.  When {@code dest} has excess
   * entries, those entries are filled with {@code null}.
   *
   *
   * @throws StaticIllegalArgumentException when {@code dest} is not
   *         large enough.
   */
  public static final Affinity[] getPredefinedAffinities(Affinity[] dest)
  {
    return dest;
  }

  /**
   * Determines whether or not affinity control is supported.
   *
   * @return {@code true} when more than one affinity set is available.
   */
  public static final boolean isSetAffinitySupported() { return true; }

  /**
   * Determines the Affinity corresponding to a {@code BitSet}, where each
   * bit in {@code set} represents a CPU.  When {@code BitSet} does not
   * correspond to a predefined affinity or an affinity with a cardinality of
   * one, the resulting {@code Affinity} instance is not a valid affinity and
   * can only be used for limiting the CPUs that can be used by a
   * {@link RealtimeThreadGroup}. The method {@link #isValid} can be used to
   * determine whether or not the result is a valid affinity.
   *
   * <p>
   * Platforms that support specific affinities will register those
   * {@code Affinity} instances with {@link Affinity}.  They
   * appear in the arrays returned by {@link #getPredefinedAffinities()}
   * and {@link #getPredefinedAffinities(Affinity[])}.
   *
   * @param set The {@code BitSet} to convert into an {@code Affinity}.
   *
   * @return the resulting {@code Affinity}.
   *
   * @throws StaticIllegalArgumentException when {@code set} is {@code null}
   * or when {@code set} is empty.
   */
  public static final Affinity generate(BitSet set) { return null; }

  /**
   * Gets the root Affinity: the Affinity that can be used to allow
   * a schedulable to run on all the processing units available to the VM.
   *
   * @return the root {@code Affinity}.
   */
  public static final Affinity getRootAffinity() { return null; }

  /**
   * Obtain the affinity of the current thread.
   *
   * @return the affinity of this thread context.
   *
   * @throws StaticIllegalArgumentException when the affinity is not valid.
   */
  public static Affinity getAffinity(Thread thread)
  {
    return null;
  }

  /**
   * Obtain the affinity of the current thread.
   *
   * @return the affinity of this thread context.
   */
  public static Affinity getCurrentAffinity()
  {
    Thread thread = Thread.currentThread();
    return getAffinity(thread);
  }

  /**
   * This method is equivalent to {@link #getAvailableProcessors(BitSet)}
   * with a {@code null} argument.
   *
   * In systems where the set of processors available to a process is
   * dynamic, e.g., system management operations or fault tolerance
   * capabilities can add or remove processors, the set of available
   * processors shall reflect the processors that are allocated to the RTSJ
   * runtime and are currently available to execute tasks.
   *
   * @return a {@code BitSet} representing the
   * set of processors currently valid for use in the
   * {@code BitSet} argument to {@link #generate(BitSet)}.
   */
  public static final BitSet getAvailableProcessors() { return null; }

  /**
   * In systems where the set of processors available to a process is
   * dynamic (e.g., because of system management operations or because
   * of fault tolerance capabilities), the set of available processors
   * shall reflect the processsors that are allocated to the RTSJ
   * runtime and are currently available to execute tasks.
   *
   * @param dest When {@code dest} is non-null, use {@code dest}
   * as the returned value.  When it is {@code null}, create a new
   * {@code BitSet}.
   *
   * @return a {@code BitSet} representing the
   * set of processors currently valid for use in the
   * {@code bitset} argument to {@link #generate(BitSet)}.
   *
   */
  public static final BitSet getAvailableProcessors(BitSet dest)
  {
    return null;
  }

  /**
   * Determines whether or not the system can trigger an event for notifying
   * the application when the set of available CPUs changes.
   *
   * @return {@code true} when change notification is supported. (See
   *         {@link #setProcessorAddedEvent(AsyncEvent)} and
   *         {@link #setProcessorRemovedEvent(AsyncEvent)}.)
   */
  public static final boolean isAffinityChangeNotificationSupported()
  {
    return false;
  }

  /**
   * Gets the event used for CPU addition notification.
   *
   * @return the async event that will be fired when a processor is
   *         added to the set available to the JVM.  Returns
   *         {@code null} when change notification is not supported,
   *         or when no async event has been designated.
   */
  public static AsyncEvent getProcessorAddedEvent() { return null; }

  /**
   * Sets the AsyncEvent that will be fired when a processor is added to
   * the set available to the JVM.
   *
   * @param event The async event to fire in case an added processor is
   *      detected, or {@code null} to cause no async event to be called in
   *      case an added processor is detected.
   *
   * @throws StaticUnsupportedOperationException when change notification
   * is not supported.
   *
   * @throws StaticIllegalArgumentException when {@code event} is
   *         not in immortal memory.
   *
   */
  public static void setProcessorAddedEvent(AsyncEvent event)
    throws StaticUnsupportedOperationException,
           StaticIllegalArgumentException
  {
  }

  /**
   * Gets the event used for CPU removal notification.
   *
   * @return the async event that will be fired when a processor is
   *         removed from the set available to the JVM.  Returns
   *         {@code null} when change notification is not supported,
   *         or when no async event has been designated.
   */
  public static AsyncEvent getProcessorRemovedEvent() { return null; }

  /**
   * Sets the {@link AsyncEvent} that will be fired when a processor is
   * removed from the set available to the JVM.
   *
   * @param event Called when a processor is removed.
   *
   * @throws StaticUnsupportedOperationException when change notification
   * is not supported.
   *
   * @throws StaticIllegalArgumentException when {@code event} is
   * not {@code null} or in immortal memory.
   *
   */
  public static void setProcessorRemovedEvent(AsyncEvent event) {}

  /**
   * Package-protected default constructor.
   */
  Affinity() {}

  /**
   * Obtains a {@code BitSet} representing the processor affinity
   * set for this {@code Affinity}.
   * @return a newly created {@code BitSet} representing this
   * {@code Affinity}.
   */
  public final BitSet getProcessors() { return getProcessors(null); }

  /**
   * Determines the set of CPUs representing the processor affinity
   * of this {@code Affinity}.
   * @param dest  Set {@code dest} to the {@code BitSet} value.
   *  When {@code dest} is {@code null}, create a new
   *  {@code BitSet} in the current allocation context.
   * @return a {@code BitSet} representing the processor affinity
   * set of this {@code Affinity}.
   */
  public final BitSet getProcessors(BitSet dest) { return dest; }

  /**
   * Asks whether a processor is included in this affinity set.
   * @param processorId A number identifying a single CPU in a
   *        multiprocessor system.
   * @return {@code true} when and only when {@code processorNumber} is
   *         represented in this affinity set.
   */
  public final boolean isProcessorInSet(final int processorId)
  {
    return true;
  }

  /**
   * Determines the number of CPUs in this affinity
   *
   * @return the number of CPUs.
   */
  public int getProcessorCount()
  {
    return 0;
  }

  /**
   * Determine whether or not the affinity can be used for scheduling or
   * just for limiting the processors available to members of
   * {@link RealtimeThreadGroup}.
   *
   * @return {@code true} when valid for scheduling and {@code false} otherwise.
   */
  public boolean isValid() { return true; }

  /**
   * Determines whether or not {@code other} is equal to or a proper subset
   * of this affinity.
   * @param other The other affinity with which to compare
   * @return {@code true} Only when the affinity in parameter is a equal to
   *         or a proper subset of this affinity.
   */
  public boolean subsumes(Affinity other) { return true; }

  public Object clone() { return null; }
}
