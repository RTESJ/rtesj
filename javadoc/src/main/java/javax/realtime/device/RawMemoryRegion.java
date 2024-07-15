/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.device;

import java.util.HashMap;

import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticIllegalStateException;

/**
 * RawMemoryRegion is a class for typing raw memory regions.  It is returned
 * by the {@link RawMemoryRegionFactory#getRegion} methods of the raw memory
 * region factory classes, and it is used with methods such as
 * {@link RawMemoryFactory#createRawByte(RawMemoryRegion, long, int, int)} and
 * {@link RawMemoryFactory#createRawDouble(RawMemoryRegion, long, int, int)}
 * methods to identify the region from which the application wants to
 * get an accessor instance.
 *
 * @since RTSJ 2.0
 */
public class RawMemoryRegion
{
  private static final
    HashMap<String, RawMemoryRegion> _regions_ = new HashMap<>();

  private final String name_;

  /**
   * Get a region type when it already exists or creates a new one.
   *
   * @param name of the region
   *
   * @return the region type object.
   *
   * @throws StaticIllegalArgumentException when {@code name} is {@code null}.
   *
   * @throws StaticIllegalStateException when a region with {@code name}
   *         already exists.
   */
  static RawMemoryRegion create(String name)
    throws StaticIllegalArgumentException,
           StaticIllegalStateException
  {
    if (name == null)
      {
        throw (StaticIllegalArgumentException)
               StaticIllegalArgumentException.get().
               init("RawMemoryRegion name may not be null!");
      }
    else if (_regions_.get(name) == null)
      {
        RawMemoryRegion result = new RawMemoryRegion(name);
        _regions_.put(name, result);
        return result;
      }
    else
      {
        throw (StaticIllegalStateException)
               StaticIllegalStateException.get().
               init("RawMemoryRegion name alread exists!");
      }
  }

  /**
   * Get a memory region type by name.
   *
   * @param name of the region
   *
   * @return the region type object or {@code null}, when none with
   *         {@code name} exists.
   *
   * @throws StaticIllegalArgumentException when {@code name} is {@code null}.
   */
  public static RawMemoryRegion get(String name)
    throws StaticIllegalArgumentException
  {
    if (name == null)
      {
        throw (StaticIllegalArgumentException)
               StaticIllegalArgumentException.get().
               init("RawMemoryRegion name may not be null!");
      }
    else
     {
       return _regions_.get(name);
     }
  }

  /**
   * Ask whether or not there is a memory region type of a given name.
   *
   * @param name for which to search
   *
   * @return {@code true} when there is one and {@code false} otherwise.
   */
  public static boolean isRawMemoryRegion(String name)
  {
    return _regions_.get(name) != null;
  }

  private RawMemoryRegion(String name)
  {
    name_ = name;
  }

  /**
   * Obtains the name of this region type.
   *
   * @return the region types name
   */
  public final String getName()
  {
    return name_;
  }

  /**
   * Gets a printable representation for a Region.
   *
   * @return the name of this memory region type.
   */
  public final String toString()
  {
    return name_;
  }

}
