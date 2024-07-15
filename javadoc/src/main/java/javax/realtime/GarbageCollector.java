/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * The system shall provide dynamic and static information
 * characterizing the temporal behavior and imposed overhead of any
 * garbage collection algorithm provided by the implementation.  This
 * information shall be made available to applications via methods on
 * subclasses of {@code GarbageCollector}. Implementations are
 * allowed to provide any set of methods in subclasses as long as the
 * temporal behavior and overhead are sufficiently categorized.  The
 * implementations are also required to fully document the subclasses.
 *
 * <p>
 *  A reference to the garbage collector responsible for heap memory is
 *  available from {@link RealtimeSystem#currentGC()}.
 */

public abstract class GarbageCollector
{
  /*
   * Creates an instance of {@code this}.
   *
   * @deprecated as of RTSJ 1.0.1  This class and any subclasses should be
   * singletons.
   */
  GarbageCollector() {}

  /**
   * Preemption latency is a measure of the maximum time a schedulable
   * object may have to wait for the collector to reach a safe point.
   *
   * <p> Schedulables which may not use the heap preempt garbage
   * collection immediately, but other schedulables must wait until the
   * collector reaches a safe point.  For many garbage
   * collectors the only safe point is at the end of garbage
   * collection, but an implementation of the garbage collector could
   * permit a schedulable to preempt garbage collection before it
   * completes.  The {@code getPreemptionLatency} method gives such
   * a garbage collector a way to report the worst-case interval between
   * the release of a schedulable during garbage collection, and the
   * time the schedulable starts execution or gains full access
   * to heap memory, whichever comes later.
   *
   * @return the worst-case preemption latency of the garbage collection
   *            algorithm represented by {@code this}.  The
   *            returned object is allocated in the current allocation
   *            context.  When there is no constant that bounds garbage
   *            collector preemption latency, this method shall return a
   *            relative time with {@code Long.MAX_VALUE}
   *            milliseconds.  The number of nanoseconds in this special
   *            value is unspecified.
   */
  public abstract RelativeTime getPreemptionLatency();
}
