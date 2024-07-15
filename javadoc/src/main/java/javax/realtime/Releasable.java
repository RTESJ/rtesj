/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * A base interface for everything that can be dispatched and hence has a
 * asynchronous release cycle.  This unifies the concept behind active events
 * and {@link RealtimeThread#waitForNextRelease}.  Thus a realtime thread can
 * handle events which do not have a payload too.
 *
 * @param <T> is subtype of {@code this}.
 * @param <D> is the dispatcher type
 *
 * @since RTSJ 2.0
 */
public interface Releasable<T extends Releasable<T,D>,
                            D extends ActiveEventDispatcher<D, T>>
{
  /**
   * Obtains the dispatcher for {@code this}.
   *
   * @return that dispatcher.
   */
  public D getDispatcher();
}
