/*------------------------------------------------------------------------*
 * Copyright 2020-2021, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime.memory;

import javax.realtime.StaticIllegalArgumentException;

public enum MemoryAreaType
{
  UNSPECIFIED(0),              /* no annotation present */
  HEAP(1),                     /* allocation only on the heap */
  IMMORTAL(2),                 /* allocation only in immortal */
  PERENNIAL(3),                /* allocation in any perrenial memory */
  SCOPED(4),                   /* for later use. */
  ANY(255);                    /* for later use. */

  private final int key_;

  private MemoryAreaType(int key)
  {
    key_ = key;
  }

  public MemoryAreaType get(int key)
  {
    for (MemoryAreaType area : MemoryAreaType.values())
      {
        if (area.key_ == key) { return area; }
      }
    throw StaticIllegalArgumentException.get().init("Unassigned value " + key);
  }
}
