/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * {@code ImmortalMemory} is a memory resource that is
 *  unexceptionally available to all schedulables and Java
 *  threads for use and allocation.
 *
 * <p> An immortal object may not contain references to any form of
 *  scoped memory, e.g., {@link javax.realtime.memory.LTMemory},
 *  {@link javax.realtime.memory.StackedMemory}, or
 *  {@link javax.realtime.memory.PinnableMemory}.
 *
 *  <p> Objects in immortal memory have the same states with respect to
 *  finalization as objects in the standard Java heap, but there is no
 *  assurance that immortal objects will be finalized even when the JVM
 *  is terminated.
 *
 * <p> Methods from {@code ImmortalMemory} should
 *  be overridden only by methods that use {@code super}.
 *
 */
public final class ImmortalMemory extends PerennialMemory
{

  private static final int MEM_SIZE = 2*1024*1024; //size of immortal memory

  /**
   * Returns a pointer to the singleton {@link ImmortalMemory}
   * object.
   *
   * @return The singleton {@link ImmortalMemory} object.
   */
  public static ImmortalMemory instance() { return immortal; }

  ImmortalMemory() { super(MEM_SIZE); }
  /**
   * Executes the run method from the {@code logic} parameter using this
   * memory area as the current allocation context.
   * For a schedulable, this saves the current scope
   *    stack and replaces it with one consisting only of
   *    the {@code ImmortalMemory} instance;
   *  restoring the original scope stack upon completion.
   *
   * @param logic The runnable object whose {@code run()} method should
   * be executed.
   *
   * @throws StaticIllegalArgumentException when {@code logic} is {@code null}.
   */
  @Override
  public void executeInArea(Runnable logic)
  {
    // executeInArea0(logic);
  }

  private static ImmortalMemory immortal = null;
}
