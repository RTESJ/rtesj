/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
/*
 * -----------------------------------------------------------------
 * PhysicalMemAccess.java : RawIoAccess models physical memory space
 * as a fixed sequence of bytes.
 *
 * Copyright (C) 2007 TimeSys Corporation, All Rights Reserved.
 *
 * This software is subject to the terms and conditions of the
 * accompanying Common Public License in file CPLicense.  Your use
 * of this software indicates your acceptance of these terms.
 * -----------------------------------------------------------------
 */

package javax.realtime;
import java.io.IOException;

/**
 * @deprecated not part of API
 */
@Deprecated
class PhysicalMemAccess
{
  // TODO: Public just for testing.

  private static int SIZEOF_BYTE = 1;
  private static int SIZEOF_SHORT = 2;
  private static int SIZEOF_INT = 4;
  private static int SIZEOF_LONG = 8;
  private int base;
  private int size;
  boolean memOpen = false;
  private int fd;

  /**
   * Construct an I/O access instance with the specified base and
   * size in the I/O space.
   *
   * @param base
   * @param size
   * @deprecated since RTSJ 2.0
   */
  @Deprecated
  public PhysicalMemAccess(int base, int size)
    throws SecurityException,
      OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    // How to throw security exception?
    if (base < 0) { throw new IllegalArgumentException("Negative base"); }
    if (size <= 0) { throw new IllegalArgumentException("Non-positive size"); }

    fd = PhysicalMemAccess.openMemory0();
    if (fd < 0)
      {
        throw new SecurityException("Cannot open memory file");
      }
    try
    {
      seek0(fd, base);
    }
    catch (Throwable th)
    {
      close0(fd);
      throw new IllegalArgumentException("Cannot access base address");
    }
    try
    {
      seek0(fd, base + size - 1);
    }
    catch (Throwable th)
    {
      close0(fd);
      throw new IllegalArgumentException("Cannot access end address");
    }

    this.base = base;
    this.size = size;
    memOpen = true;
  }

  private static native int openMemory0();

  /**
   * Get the {@code byte} at the given offset.
   *
   * @param offset
   *          The offset at which to read the byte.
   * @return The byte read.
   */
  public byte getByte(long offset)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    standardChecks(offset, SIZEOF_BYTE);
    PhysicalMemAccess.seek0(fd, base + offset);
    return PhysicalMemAccess.readByte0(fd);
  }

  private static native void seek0(int fd, long seekTo);

  private static native byte readByte0(int fd);

  /**
   * Get {@code number} bytes starting at the given offset and assign
   * them to the byte array passed starting at position {@code low}.
   */
  public void getBytes(long offset, byte[] bytes, int low, int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    arrayChecks(offset, SIZEOF_BYTE, bytes.length, low, number);
    for (int i = 0; i < number; ++i)
    {
      bytes[i + low] = getByte(offset + i);
    }
  }

  /**
   * Get the {@code int} at the given offset.
   *
   * @param offset
   *          The offset at which to read the integer.
   * @return The int read.
   */
  public int getInt(long offset)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    standardChecks(offset, SIZEOF_INT);
    PhysicalMemAccess.seek0(fd, base + offset);
    return PhysicalMemAccess.readInt0(fd);
  }

  private static native int readInt0(int fd);

  /**
   * Get {@code number} ints starting at the given offset and assign
   * them to the int array passed starting at position {@code low}.
   */
  public void getInts(long offset, int[] ints, int low, int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    arrayChecks(offset, SIZEOF_INT, ints.length, low, number);
    for (int i = 0; i < number; ++i)
    {
      ints[i + low] = getInt(offset + (i * PhysicalMemAccess.SIZEOF_INT));
    }
  }

  /**
   * Get the {@code long} at the given offset.
   *
   * @param offset
   *          The offset at which to read the long.
   * @return The long read.
   */
  public long getLong(long offset)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    standardChecks(offset, SIZEOF_LONG);
    PhysicalMemAccess.seek0(fd, base + offset);
    return PhysicalMemAccess.readLong0(fd);
  }

  private static native long readLong0(int fd);

  /**
   * Get {@code number} longs starting at the given offset and assign
   * them to the long array passed starting at position {@code low}.
   */
  public void getLongs(long offset, long[] longs, int low, int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    arrayChecks(offset, SIZEOF_LONG, longs.length, low, number);

    for (int i = 0; i < number; ++i)
    {
      longs[i + low] = getLong(offset + (i * PhysicalMemAccess.SIZEOF_LONG));
    }
  }

  /**
   * Get the {@code short} at the given offset.
   *
   * @param offset
   *          The offset at which to read the short.
   * @return The short read.
   */
  public short getShort(long offset)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    standardChecks(offset, SIZEOF_SHORT);
    PhysicalMemAccess.seek0(fd, base + offset);
    return PhysicalMemAccess.readShort0(fd);
  }

  private static native short readShort0(int fd);

  /**
   * Get {@code number} shorts starting at the given offset and assign
   * them to the short array passed starting at position {@code low}.
   */
  public void getShorts(long offset, short[] shorts, int low, int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    arrayChecks(offset, SIZEOF_SHORT, shorts.length, low, number);
    for (int i = 0; i < number; ++i)
    {
      shorts[i + low] = getShort(offset + (i * PhysicalMemAccess.SIZEOF_SHORT));
    }
  }

  /**
   * Set the {@code byte} at the given offset.
   *
   * @param offset
   *          The offset at which to write the byte.
   * @param value
   *          The byte to write.
   */
  public void setByte(long offset, byte value)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    standardChecks(offset, SIZEOF_BYTE);
    PhysicalMemAccess.seek0(fd, base + offset);
    PhysicalMemAccess.writeByte0(fd, value);
  }

  private static native void writeByte0(int fd, byte value);

  /**
   * Set {@code number} bytes starting at the given offset from the
   * byte array passed starting at position {@code low}.
   */
  public void setBytes(long offset, byte[] bytes, int low, int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    arrayChecks(offset, SIZEOF_BYTE, bytes.length, low, number);
    for (int i = 0; i < number; ++i)
    {
      setByte(offset + i, bytes[i + low]);
    }
  }

  /**
   * Set the {@code int} at the given offset.
   *
   * @param offset
   *          The offset at which to write the int.
   * @param value
   *          The integer to write.
   */
  public void setInt(long offset, int value)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    standardChecks(offset, SIZEOF_INT);
    PhysicalMemAccess.seek0(fd, base + offset);
    PhysicalMemAccess.writeInt0(fd, value);
  }

  private static native void writeInt0(int fd, int value);

  /**
   * Set {@code number} ints starting at the given offset from the
   * int array passed starting at position {@code low}.
   */
  public void setInts(long offset, int[] ints, int low, int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    arrayChecks(offset, SIZEOF_INT, ints.length, low, number);
    for (int i = 0; i < number; ++i)
    {
      setInt(offset + (i * PhysicalMemAccess.SIZEOF_INT), ints[i + low]);
    }
  }

  /**
   * Set the {@code long} at the given offset.
   *
   * @param offset
   *          The offset at which to write the long.
   * @param value
   *          The long to write.
   */
  public void setLong(long offset, long value)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    standardChecks(offset, SIZEOF_LONG);
    PhysicalMemAccess.seek0(fd, base + offset);
    PhysicalMemAccess.writeLong0(fd, value);
  }

  private static native void writeLong0(int fd, long value);

  /**
   * Set {@code number} longs starting at the given offset from the
   * long array passed starting at position {@code low}.
   */
  public void setLongs(long offset, long[] longs, int low, int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    arrayChecks(offset, SIZEOF_LONG, longs.length, low, number);
    for (int i = 0; i < number; ++i)
    {
      setLong(offset + (i * PhysicalMemAccess.SIZEOF_LONG), longs[i + low]);
    }
  }

  /**
   * Set the {@code short} at the given offset.
   *
   * @param offset
   *          The offset at which to write the short.
   * @param value
   *          The short to write.
   */
  public void setShort(long offset, short value)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    standardChecks(offset, SIZEOF_SHORT);
    PhysicalMemAccess.seek0(fd, base + offset);
    PhysicalMemAccess.writeShort0(fd, value);
  }

  private static native void writeShort0(int fd, short value);

  /**
   * Set {@code number} shorts starting at the given offset from the
   * short array passed starting at position {@code low}.
   */
  public void setShorts(long offset, short[] shorts, int low, int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    arrayChecks(offset, SIZEOF_SHORT, shorts.length, low, number);

    for (int i = 0; i < number; ++i)
    {
      setShort(offset + (i * PhysicalMemAccess.SIZEOF_SHORT), shorts[i + low]);
    }
  }

  void standardChecks(long offset, int datatypeSize)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    if (memOpen == false)
    {
      int rVal = openMemory0();
      if (rVal < 0) { throw new SecurityException("Cannot open memory file"); }
      memOpen = true;
    }
    RealtimeSystem.getSecurityManager().checkAccessPhysical();
    if (offset >= size || offset < 0)
      {
        throw new OffsetOutOfBoundsException("Offset passed is less than 0 or greater than size");
      }
    if (offset + datatypeSize > size)
     {
      throw new SizeOutOfBoundsException();
      // Unaligned access is fine for physical memory.
      // if (offset % datatypeSize != 0) {
      // throw new
      // IllegalArgumentException("Offset must be aligned for I/O space operations");
      // }
    }
  }

  void arrayChecks(long offset, int datatypeSize, int arrayLength, int low,
                   int number)
    throws OffsetOutOfBoundsException,
      SizeOutOfBoundsException
  {
    if (number + low > arrayLength)
      {
        throw new ArrayIndexOutOfBoundsException("Array passed is not big enough");
      }
    else if (low < 0)
      {
        throw new ArrayIndexOutOfBoundsException("Negative starting index");
      }
    else if (offset < 0)
      {
        throw new OffsetOutOfBoundsException("offset is negative");
      }
    else if (number < 0)
      {
        throw new OffsetOutOfBoundsException("'number' is negative");
      }
    else
      {
        standardChecks(offset, datatypeSize * number);
      }
  }

  @Override
  protected void finalize()
    throws Throwable
  {
    super.finalize();
    close0(fd);
    memOpen = false;
  }

  private static native void close0(int fd);

  /**
   * Called from native code.
   *
   * @throws IOException
   */
  private static void generateIoException()
    throws IOException
  {
    throw new IOException();
  }

  /**
   * Called from native code
   */
  private static void generateSizeException()
  {
    throw new SizeOutOfBoundsException();
  }
}
