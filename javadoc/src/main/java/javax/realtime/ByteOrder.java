/*------------------------------------------------------------------------*
 * Copyright 2023-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime;

/**
 * Defines the possible mappings between bytes and words in memory.
 *
 * @since RTSJ 2.0
 */
public enum ByteOrder
{
  /**
   * Value indicating that the highest order byte of a bit word is stored
   * at the lowest byte address: the int 0x0A0B0C0D is stored in the byte
   * sequence 0x0A, 0x0B, 0x0C, 0x0D. and the long 0x0102030405060708
   * is stored in the sequence 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08.
   */
  BIG_ENDIAN(0xe4),

  /**
   * Value indicating that the lowest order byte of a word is stored
   * at the lowest byte address: the int 0x0A0B0C0D is stored in the byte
   * sequence 0x0D, 0x0C, 0x0B, 0x0A and the long 0x0102030405060708
   * is stored in the sequence 0x08, 0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01.
  */
  LITTLE_ENDIAN(0x1b),

  /**
   * Value indicating a mixed endian mode used by among others the PDP-11:
   * the int 0x0A0B0C0D is stored in the byte sequence 0x0B, 0x0A, 0x0D, 0x0C,
   * and the long 0x0102030405060708 is stored in the sequence 0x02, 0x01,
   * 0x04, 0x03, 0x06, 0x05, 0x08, 0x07.
   */
  PDP_ENDIAN(0xb1),

  /**
   * Value indicating a mixed endian mode:
   * the int 0x0A0B0C0D is stored in the byte sequence 0x0D, 0x0C, 0x0B, 0x0A,
   * and the long 0x0102030405060708 is stored in the sequence 0x05, 0x06,
   * 0x07, 0x08, 0x01, 0x02, 0x03, 0x04.
   */
  CROSS_ENDIAN(0x4e);

  private final byte value_;

  ByteOrder(int value)
  {
    value_ = (byte)value;
  }

  /**
   * Determines the value of this enum.
   *
   * @return the corresponding integer.
   */
  public byte value() { return value_; }

  /**
   * Converts a string into a policy type.
   *
   * @param value is the string to convert.
   *
   * @return the corresponding endian enum.
   */
  public static ByteOrder value(String value)
  {
    switch (value)
      {
      case "BIG_ENDIAN":    return BIG_ENDIAN;
      case "LITTLE_ENDIAN": return LITTLE_ENDIAN;
      case "PDP_ENDIAN":    return PDP_ENDIAN;
      case "CROSS_ENDIAN":  return CROSS_ENDIAN;
      default:              return null;
      }
  }

  /**
   * Converts an int into a policy type.
   *
   * @param value the int to convert.
   *
   * @return the corresponding endian enum.
   */
  public static ByteOrder value(int value)
  {
    switch (value)
      {
      case (byte)0xe4: return BIG_ENDIAN;
      case (byte)0x1b: return LITTLE_ENDIAN;
      case (byte)0xb1: return PDP_ENDIAN;
      case (byte)0x4e: return CROSS_ENDIAN;
      default:         return null;
      }
  }

  private static ByteOrder initByteOrder()
  {
    long test = bytes2long(1, 2, 3, 4, 5, 6, 7, 8);
    if      (test == 0x0807060504030201L) { return LITTLE_ENDIAN; }
    else if (test == 0x0102030405060708L) { return BIG_ENDIAN; }
    else if (test == 0x0201040306050807L) { return PDP_ENDIAN; }
    else if (test == 0x0506060801020304L) { return CROSS_ENDIAN; }
    else
    {
      throw new InternalError("Strange byte order: " + test);
    }
  }

  private static native long bytes2long(int b0, int b1, int b2, int b3,
                                        int b4, int b5, int b6, int b7);

  private static final ByteOrder BYTE_ORDER = initByteOrder();

  /**
   * Obtains the byte order of the byte order of the system.
   *
   * @return one of the defined byte order constants.
   */
  public static ByteOrder systemByteOrder() { return BYTE_ORDER; }
}
