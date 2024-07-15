/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Indicates an invalid raw memory region.
 *
 * @since RTSJ 2.0
 */
public class UnsupportedRawMemoryRegionException
  extends StaticRuntimeException
  implements StaticThrowable<UnsupportedRawMemoryRegionException>
{
  private static final long serialVersionUID = -4498345322215979707L;

  private static final UnsupportedRawMemoryRegionException _singleton_ =
    new UnsupportedRawMemoryRegionException();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   */
  public static UnsupportedRawMemoryRegionException get()
  {
    return _singleton_;
  }

  private UnsupportedRawMemoryRegionException()
  {
  }

  @Override
  public UnsupportedRawMemoryRegionException getSingleton()
  {
    return _singleton_;
  }
}
