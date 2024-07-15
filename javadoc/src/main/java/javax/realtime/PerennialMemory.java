/*-----------------------------------------------------------------------*\
 * Copyright 2016-2017, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A base class for all memory areas whose contents can be unexceptionally
 * referenced.  In other words, any memory area can store a reference to an
 * object stored in one of these areas.  This includes all concrete
 * memory areas in the core package.  Only memory areas of this type can be
 * a root for a scoped memory.
 *
 * @since RTSJ 2.0
 */
public abstract class PerennialMemory extends MemoryArea
{
  PerennialMemory(long size, Runnable logic)
    throws StaticIllegalArgumentException,
      StaticOutOfMemoryError,
      IllegalAssignmentError
  {
    super(size, logic);
  }


  PerennialMemory(SizeEstimator size, Runnable logic)
    throws StaticIllegalArgumentException,
      StaticOutOfMemoryError,
      IllegalAssignmentError
  {
    super(size, logic);
  }


  PerennialMemory(long size)
    throws StaticIllegalArgumentException,
      StaticOutOfMemoryError
  {
    super(size);
  }


  PerennialMemory(SizeEstimator size)
    throws StaticIllegalArgumentException,
      StaticOutOfMemoryError
  {
    super(size);
  }
}
