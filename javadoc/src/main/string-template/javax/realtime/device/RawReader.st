RawReader(package, type, name, SCJ) ::= <<
/* copyright © 2012-2024 aicas GmbH; all rights reserved */
/* This java file is automatically generated, so do not edit it directly. */

package <package>;

import javax.realtime.OffsetOutOfBoundsException;

<if(SCJ)>
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJMayAllocate;
import javax.safetycritical.annotate.SCJMaySelfSuspend;
<endif>

/**
 * A marker for a <type> accessor object encapsulating the protocol for
 * reading <type>s from raw memory.  A <type> accessor can always access
 * at least one <type>.  Each <type> is transferred in a single atomic
 * operation.  Groups of <type>s may be transferred together; however,
 * this is not required.
 *
 * \<p\>Objects of this type are created with the method
 * {@link RawMemoryFactory#createRaw<name>Reader} and
 * {@link RawMemoryFactory#createRaw<name>}.  Each object references
 * a range of elements in the {@link RawMemoryRegion} starting at the
 * \<i\>base address\</i\> provided to the factory method.  The size provided
 * to the factory method determines the number of accessible elements.
 *
 * \<p\> Caching of the memory access is controlled by the factory that
 * created this object.  If the memory is not cached, this method
 * guarantees serialized access.  In other words, the memory access at
 * the memory occurs in the same order as in the program.  Multiple
 * writes to the same location may not be coalesced.
 *
 * @since RTSJ 2.0
 */
<if(SCJ)>
@SCJAllowed

<endif>
public interface Raw<name>Reader extends RawMemory
{
  /**
   * Gets the value at the first position referenced by this
   * instance, i.e., the value at its start address.
   * This operation must be atomic with respect to all other
   * raw memory accesses to the address.
   *
   * @return the value at the \<i\>base address\</i\>.
   */
  /*@ public behavior
    @   assignable \nothing;
    @   ensures true;
    @*/
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({})
  @SCJMaySelfSuspend(false)
  <endif>
  public <type> get<name>();

  /**
   * Gets the value at the address represented by {@code offset} from the
   * base of this instance: \<i\>base address\</i\> +
   * ({@code offset} &times; {@code stride} &times;
   * \<i\>element size in bytes\</i\>).
   * When an exception is thrown, no data is transferred.
   *
   * @param offset of <type> in the memory region starting from the address
   *        specified in the associated factory method.
   *
   * @return the value at the address specified.
   *
   * @throws OffsetOutOfBoundsException when {@code offset} is
   *         negative or greater than or equal to the number of elements in
   *         the raw memory region.
   */
  /*@ public behavior
    @   requires
    @     offset \>= 0 && offset \< getSize();
    @   assignable \nothing;
    @   ensures true;
    @   signals (OffsetOutOfBoundsException)
    @     offset \< 0 || offset \>= getSize();
    @*/
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({})
  @SCJMaySelfSuspend(false)
  <endif>
  public <type> get<name>(int offset) throws OffsetOutOfBoundsException;

  /**
   * Fills {@code values} with elements from this instance,
   * where the n\<sup\>th\</sup\> element is at the address
   * \<i\>base address\</i\> + {@code (offset + n)}
   * &times; {@code stride} &times; \<i\>element size in bytes\</i\>.
   * Only the <type>s in the intersection of the start and end of
   * {@code values} and the \<i\>base address\</i\> and the
   * end of the memory region are transferred.  When an exception is
   * thrown, no data is transferred.
   *
   * @param offset of the first <type> in the memory region to transfer.
   *
   * @param values The array to receive the <type>s.
   *
   * @return the number of elements actually transferred to {@code values}.
   *
   * @throws OffsetOutOfBoundsException when {@code offset} is
   * negative or greater than or equal to the number of elements in the
   * raw memory region.
   *
   * @throws NullPointerException when {@code values} is null.
   */
  /*@ public behavior
    @   requires
    @     offset \>= 0 && offset \< getSize() &&
    @     values != null && count \>= 0;
    @   assignable values[0 .. \min(values.length, getSize() - offset) - 1];
    @   ensures
    @      \result = \min(values.length, getSize() - offset);
    @   signals (OffsetOutOfBoundsException)
    @     offset \< 0 || offset \>= getSize();
    @   signals (NullPointerException)
    @     values == null;
    @*/
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({})
  @SCJMaySelfSuspend(false)
  <endif>
  public int get(int offset, <type>[] values)
    throws OffsetOutOfBoundsException, NullPointerException;

  /**
   * Fills {@code values} from index {@code start} with
   * elements from this instance, where the n\<sup\>th\</sup\> element is at
   * the address \<i\>base address\</i\> + {@code (offset + n)} &times;
   * {@code stride} &times; \<i\>element size in bytes\</i\>.  The
   * number of bytes transferred is the minimum of {@code count},
   * the \<i\>size\</i\> of the memory region minus {@code offset}, and
   * length of {@code values} minus {@code start}.  When an
   * exception is thrown, no data is transferred.
   *
   * @param offset of the first <type> in the memory region to transfer.
   *
   * @param values The array to receive the <type>s.
   *
   * @param start The first index in array to fill.
   *
   * @param count The maximum number of <type>s to copy.
   *
   * @return the number of <type>s actually transferred.
   *
   * @throws OffsetOutOfBoundsException when {@code offset} is
   * negative or either {@code offset} or {@code offset} +
   * {@code count} is greater than or equal to the size of this
   * raw memory area.
   *
   * @throws ArrayIndexOutOfBoundsException when {@code start} is
   * negative or either {@code start} or {@code start} +
   * {@code count} is greater than or equal to the size of values.
   *
   * @throws NullPointerException when {@code values} is null or
   * {@code count} is negative.
   */
  /*@ public behavior
    @   requires
    @     values != null;
    @   assignable values[\max(0, start) ..
    @                     \max(0, \min(count, getSize(),
    @                          count - start, getSize() - offset));
    @   ensures
    @      \result =
    @        \max(0, \min(count, getSize(), count - start, getSize() - offset));
    @   signals (OffsetOutOfBoundsException)
    @     offset \< 0 || offset \>= getSize();
    @   signals (ArrayIndexOutOfBoundsException)
    @     start \< 0 || start + count \>= values.length;
    @   signals (IllegalArgumentException)
    @     count \< 0;
    @   signals (NullPointerException)
    @     values == null;
    @*/
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({})
  @SCJMaySelfSuspend(false)
  <endif>
  public int get(int offset, <type>[] values, int start, int count)
    throws OffsetOutOfBoundsException,
           ArrayIndexOutOfBoundsException,
           IllegalArgumentException,
           NullPointerException;
}
>>
