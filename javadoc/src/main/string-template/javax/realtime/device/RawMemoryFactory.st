RawMemoryFactory(package, names, SCJ) ::= <<
/* copyright (c) 2012-2024 aicas GmbH; all rights reserved */
/* This java file is automatically generated, so do not edit it directly. */

package <package>;

import javax.realtime.DeregistrationException;
import javax.realtime.MemoryTypeConflictException;
import javax.realtime.OffsetOutOfBoundsException;
import javax.realtime.RegistrationException;
import javax.realtime.SizeOutOfBoundsException;
import javax.realtime.UnsupportedRawMemoryRegionException;

<if(SCJ)>
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJMayAllocate;
import javax.safetycritical.annotate.SCJMaySelfSuspend;
import javax.safetycritical.annotate.SCJPhase;

import static javax.safetycritical.annotate.AllocatePermission.CurrentContext;
import static javax.safetycritical.annotate.Phase.STARTUP;
import static javax.safetycritical.annotate.Phase.INITIALIZATION;
import static javax.safetycritical.annotate.Phase.RUN;
import static javax.safetycritical.annotate.Phase.CLEANUP;
<endif>

/**
 * This class is the hub of a system that constructs special purpose
 * objects to access particular types and ranges of raw memory.
 * This facility is supported by the {@link #register(RawMemoryRegionFactory)}
 * methods.  An application developer can use this method to add support
 * for additional memory regions.
 *
 * \<p\> Each \<i\>create method\</i\> returns an object of the corresponding type,
 * e.g., the {@link #createRawByte(RawMemoryRegion, long, int, int)}
 * method returns a reference to an object that implements the
 * {@link RawByte} interface and supports access to the
 * requested type of memory and address range.  Each \<i\>create method\</i\>
 * is permitted to optimize error checking and access based on the
 * requested memory type and address range.
 *
 * \<p\>
 * The usage pattern for raw memory, assuming the necessary factory has
 * been registered, is illustrated by this example.
 *
 * \<p\>\<pre\>
<if(SCJ)>
\begin{Java}

<endif>
  // Get an accessor object that can access memory starting at
  // baseAddress, for size bytes.
  RawInt memory =
    RawMemoryFactory.getDefaultFactory().
      createRawInt(RawMemoryFactory.MEMORY_MAPPED_REGION,
                   address, count, stride, false);
  // Use the accessor to load from and store to raw memory.
  int loadedData = memory.getInt(someOffset);
  memory.setInt(otherOffset, intVal);
<if(SCJ)>
\end{Java}

<endif>
 * \</pre\>
 *
 * \<p\> When an application needs to access a class of memory that is not
 * already supported by a registered factory, the developer must implement
 * and register a factory that implements the {@link RawMemoryRegionFactory})
 * which can create objects to access memory in that region.
 *
 * \<p\> A raw memory region factory is identified by a
 * {@link RawMemoryRegion} that is used by each \<i\>create method\</i\>,
 * e.g., {@link #createRawByte(RawMemoryRegion, long, int, int)}, to locate
 * the appropriate factory.  The name is
 * provided to {@link #register(RawMemoryRegionFactory)} through
 * the factory's {@link RawMemoryRegionFactory#getName} method.
 *
 * \<p\> The {@link #register(RawMemoryRegionFactory)} method is only
 * used by the application code when it needs to add support for a new
 * type of raw memory.
 *
 * \<p\> Whether a given {@code offset} addresses a high-order or low-order
 * byte of an aligned {@code short} in memory is determined by the value
 * of the {@link javax.realtime.RealtimeSystem#BYTE_ORDER} static byte variable
 * in class {@link javax.realtime.RealtimeSystem}, by the start address of the
 * object, and by the {@code offset} given the {@code stride} of the
 * object.  Regardless of the byte ordering, accessor methods
 * continue to select bytes starting at {@code offset} from the
 * base address and continuing toward greater addresses.
 *
 * \<p\> A raw memory region cannot contain references to Java objects.
 * Such a capability would be unsafe (since it could be used to defeat
 * Java's type checking) and error prone (since it is sensitive to the
 * specific representational choices made by the Java compiler).
 *
 * \<p\> Atomic loads and stores on raw memory are defined in terms of
 * physical memory.  This memory may be accessible to threads outside
 * the JVM and to non-programmed access (e.g., DMA).  Consequently,
 * atomic access must be supported by hardware.  This specification is
 * written with the assumption that all suitable hardware platforms
 * support atomic loads from raw memory for aligned bytes, shorts, and
 * ints.  Atomic access beyond the specified minimum may be supported
 * by the implementation.
 *
 * \<p\> Storing values into raw memory is more hardware dependent than
 * loading values.  Many processor architectures do not support atomic
 * stores of variables except for aligned stores of the processor's
 * word size.  For instance, storing a byte into memory might require
 * reading a 32-bit quantity into a processor register, updating the
 * register to reflect the new byte value, then restoring the whole
 * 32-bit quantity.  Changes to other bytes in the 32-bit quantity that
 * take place between the load and the store are lost.
 *
 * \<p\> Some processors have mechanisms that can be used to implement an
 * atomic store of a byte, but those mechanisms are often slow and not
 * universally supported.
 *
 * \<p\> This class need not support unaligned access to data; but if it
 * does, it is not required of the implementation to make such access
 * atomic.  Accesses to data aligned on its natural boundary will be
 * atomic if the processor implements atomic loads and stores of that
 * data size.
 *
 * \<p\> Except where noted, accesses to raw memory are not atomic with
 * respect to the memory or with respect to tasks.  A raw memory region
 * could be updated by another task, or even unmapped in the middle of
 * an access method, or even \<em\>removed\</em\>
 * mid method.
 *
<if(SCJ)>
 *  \<p\> \<pre\>
 *  The characteristics of raw-memory access are necessarily platform
 *  dependent.  This specification provides a minimum requirement for
 *  the \scj platform, but it also supports optional system properties
 *  that identify a platform's level of support for atomic raw
 *  {@code put} and {@code get}.  The properties represent a
 *  four-dimensional sparse array of access type, data type, alignment,
 *  and atomicity with boolean values indicating whether that
 *  combination of access attributes is atomic. The default value for
 *  array entries is false. The permissable values of these array entries are:
 *  \begin{itemize}
 *    \item Access type - possible values are \textit{read} and \textit{write}.
 *    \item Data type - possible values are \textit{byte}, \textit{short},
 *    \textit{int}, \textit{long}, \textit{float}, and  \textit{double}.
 *    \item Alignment - possible values are 0 through 7, inclusive.  For each
 *      data type, the possible alignments range from:
 *    \begin{itemize}
 *      \item 0 means aligned
 *      \item 1 to (data size-1) means only the first byte of the data is
 *        alignment bytes away from natural alignment
 *    \end{itemize}
 *    \item Atomicity - possible values are \textit{processor}, \textit{smp},
 *      and \textit{memory}.
 *    \begin{itemize}
 *      \item \textit{processor} means that access is atomic with respect to
 *        other schedulable objects on that processor.
 *      \item \textit{smp} means that access is processor atomic, and atomic
 *        across the processors in an SMP.
 *      \item \textit{memory} means that access is SMP atomic, and atomic
 *        with respect to all access to the memory, including DMA hardware.
 *    \end{itemize}
 *  \end{itemize}
 *  \</pre\>
<else>
 * \<p\> The characteristics of raw-memory access are necessarily
 * platform dependent.  This specification provides a minimum
 * requirement for the RTSJ platform, but it also supports optional
 * system properties that identify a platform's level of support for
 * atomic raw put and get.  The properties can be represented by a
 * four-dimensional sparse array of access type, data type, alignment, and
 * atomicity  with boolean values indicating whether that combination
 * of access attributes is atomic.  The default value for array entries
 * is false.
 *
 * The array is described in the following table.
 * \<table width="95%" border="1"\>
 *   \<caption\>Properties Array\</caption\>
 * \<tr\>
 *   \<td width="25%" align="center"\>\<div\>\<strong\>Attribute\</strong\>\</div\>\</td\>
 *   \<td width="25%" align="center"\>\<div\>\<strong\>Values\</strong\>\</div\>\</td\>
 *   \<td width="50%" align="center"\>\<div\>\<strong\>Comment\</strong\>\</div\>\</td\>
 * \</tr\>
 * \<tr\>
 *   \<td\>Access type\</td\>
 *   \<td\>read, write\</td\>
 *   \<td\>&nbsp;\</td\>
 * \</tr\>
 * \<tr\>\<td\>Data type\</td\>
 *     \<td\>byte, short, int, long, float, double\</td\>
 *     \<td\>&nbsp;\</td\>
 * \</tr\>
 * \<tr\>
 *   \<td rowspan="2"\>Alignment\</td\>
 *   \<td\>0\</td\>
 *   \<td\>aligned\</td\>
 * \</tr\>
 * \<tr\>
 *   \<td\>1 to one less than data type size\</td\>
 *   \<td\>the first byte of the data is the value of \<em\>alignment\</em\>
 *     bytes away from natural alignment.
 *   \</td\>
 * \</tr\>
 * \<tr\>
 *   \<td rowspan="3"\>Atomicity\</td\>
 *   \<td\>processor\</td\>
 *   \<td\>means access is atomic with respect to other tasks on processor.\</td\>
 * \</tr\>
 * \<tr\>
 *   \<td\>smp\</td\>
 *   \<td\>means access is \<em\>processor\</em\> atomic, and atomic
 *         with respect to all processors in an SMP.\</td\>
 * \</tr\>
 * \<tr\>
 *   \<td\>memory\</td\>
 *   \<td\>means that access is \<em\>smp\</em\> atomic, and atomic with respect
 *       to all access to the memory including DMA.\</td\>
 * \</tr\>
 * \</table\>
<endif>

 *
 * The true values in the table are represented by properties of the
 * following form.
 * javax.realtime.atomicaccess_&lt;access&gt;_&lt;type&gt;_&lt;alignment&gt;_atomicity=true
 * for example,
 *
 * \<pre\>
<if(SCJ)>
\begin{Java}

<endif>
   javax.realtime.atomicaccess_read_byte_0_memory=true
<if(SCJ)>
\end{Java}

<endif>
 * \</pre\>
 * Table entries with a value of false may be explicitly represented,
 *  but since false is the default value, such properties are redundant.
 *
 * \<p\> All raw memory access is treated as volatile, and
 * \<em\>serialized\</em\>.  The infrastructure must be forced to read memory
 * or write to memory on each call to a raw memory objects's getter or
 * setter method, and to complete the reads and writes in the order they
 * appear in the program order.
 *
 * @since RTSJ 2.0
 */
<if(SCJ)>
@SCJAllowed

<endif>
public class RawMemoryFactory
{
  /**
   * This raw memory region is predefined for requesting access to
   * memory mapped I/O devices.
   */
  <if(SCJ)>
  @SCJAllowed
  <endif>
  public static final RawMemoryRegion MEMORY_MAPPED_REGION =
    RawMemoryRegion.create("MEMORY_MAPPED_REGION");

 /**
  * This raw memory region is predefined for access to I/O
  * device space implemented by processor instructions, such as the x86
  * {@code in} and {@code out} instructions.
  */
  <if(SCJ)>
  @SCJAllowed
  <endif>
  public static final RawMemoryRegion IO_PORT_MAPPED_REGION =
    RawMemoryRegion.create("IO_PORT_MAPPED_REGION");

  /**
   * Gets the factory with support for the platform defined regions.
   * Create when it has not already been created.  Ensure that at least
   * the RawMemoryRegionFactory is registered in the default factory.
   *
   * @return the platform-defined factory.
   */
  <if(SCJ)>
  @SCJAllowed
  @SCJMaySelfSuspend(false)
  @SCJPhase({STARTUP, INITIALIZATION, RUN, CLEANUP})
  @SCJMayAllocate({})
  <endif>
  public static RawMemoryFactory getDefaultFactory()
  {
    return null;
  }

  /**
   * Creates an empty factory.  For a factory with support for the
   * platform defined regions, use {@link #getDefaultFactory} instead.
   * This is only useful after a {@link RawMemoryRegionFactory} has been
   * added with {@link #register(RawMemoryRegionFactory)}.
   */
  <if(SCJ)>
  @SCJAllowed
  @SCJMaySelfSuspend(false)
  @SCJPhase({STARTUP, INITIALIZATION})
  <endif>
  public RawMemoryFactory()
  {
  }

  /**
   * Adds support for a new memory region.
   *
   * @param factory The {@link RawMemoryRegionFactory} instance to use for
   *        creating {@link RawMemory} objects for the
   *        {@link RawMemoryRegion} instances it makes available.
   *
   * @throws RegistrationException when the
   *         {@code factory} already is already registered.
   *
   * @throws NullPointerException when {@code factory} is null.
   */
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({})
  @SCJMaySelfSuspend(false)
  <endif>
  public void register(RawMemoryRegionFactory factory)
    throws RegistrationException, NullPointerException
  {
  }

  /**
   * Removes support for a new memory region.
   *
   * @param factory The {@link RawMemoryRegionFactory} to be made unavailable.
   *
   * @throws DeregistrationException when {@code factory} is not
   *         registered.
   *
   * @throws NullPointerException when {@code factory} is null.
   */
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({})
  @SCJMaySelfSuspend(false)
  <endif>
  public void deregister(RawMemoryRegionFactory factory)
    throws DeregistrationException, NullPointerException
  {
  }
<names:{name|


  /**
   * Creates an instance of a class that implements {@link <name>\} and
   * accesses memory of {@code region\} in the address range described by
   * {@code base\}, {@code stride\}, and {@code count\}.
   * The actual extent of the memory addressed by the object is
   * {@code stride\} &times; \<i\>size of <name>\</i\> &times;
   * {@code count\}.  The object is allocated in the current memory area
   * of the calling thread.
   *
   * @param region The address space from which the new instance
   *        should be taken.
   *
   * @param base The starting physical address accessible through the
   *        returned instance.
   *
   * @param count The number of memory elements accessible through the
   *        returned instance.
   *
   * @param stride The distance to the next element as a multiple of element
   *        size, where {@code 1\} means the elements are adjacent in
   *        memory.
   *
   * @return an object that implements {@link <name>\} and supports access to
   *         the specified range in the memory region.
   *
   * @throws SecurityException when the caller does not have permissions
   *         to access the given memory region or the specified range of
   *         addresses.
   *
   * @throws IllegalArgumentException when {@code base\} is negative,
   *         {@code count\} is not greater than zero, or
   *         {@code stride\} is less than one.
   *
   * @throws OffsetOutOfBoundsException when {@code base\} is not in
   *         a invalid range for raw memory.
   *
   * @throws SizeOutOfBoundsException when the memory addressed by the object
   *         would extend into an invalid range for raw memory.
   *
   * @throws UnsupportedRawMemoryRegionException when {@code region\} is
   *         not registered.
   */
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({CurrentContext\})
  @SCJMaySelfSuspend(false)
  <endif>
  public <name> create<name>(RawMemoryRegion region, long base,
                             int count, int stride)
    throws SecurityException, IllegalArgumentException,
           OffsetOutOfBoundsException, SizeOutOfBoundsException,
           UnsupportedRawMemoryRegionException
  {
    return null;
  \}
}>}
>>
