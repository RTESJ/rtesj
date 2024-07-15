/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A marker interface to provide a type safe reference to all
 * schedulables that are bound to a single underlying thread.
 * A {@link RealtimeThread} is by definition bound.
 *
 * @since RTSJ 2.0
 */
public interface BoundSchedulable extends Schedulable
{
}
