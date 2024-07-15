/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.device;

/**
 * A marker for all raw memory accessor objects.
 *
 * @since RTSJ 2.0
 */
public interface RawMemory
{
  /**
   * Gets the base physical address of this object.
   *
   * @return the first physical address this raw memory object can access.
   */
  /*@ public behavior
    @   assignable false;
    @   ensures true;
    @*/
  public long getAddress();

  /**
   * Gets the number of bytes that this object spans.
   *
   * @return the size of this raw memory.
   */
  /*@ public behavior
    @   assignable false;
    @   ensures \return > 0;
    @*/
  public int getSize();

  /**
   * Gets the distance between elements in multiples of element size.
   *
   * @return the span between elements of this raw memory.
   */
  /*@ public behavior
    @   assignable \nothing;
    @   ensures \return > 0;
    @*/
  public int getStride();
}
