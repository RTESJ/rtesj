RawMemoryRegionFactory(package, names, SCJ) ::= <<
/* copyright (c) 2012-2024 aicas GmbH; all rights reserved */
/* This java file is automatically generated, so do not edit it directly. */

package <package>;

import javax.realtime.OffsetOutOfBoundsException;
import javax.realtime.SizeOutOfBoundsException;
import javax.realtime.MemoryTypeConflictException;
import javax.realtime.UnsupportedRawMemoryRegionException;

<if(SCJ)>
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJMayAllocate;
import javax.safetycritical.annotate.SCJMaySelfSuspend;
import javax.safetycritical.annotate.SCJPhase;

import static javax.safetycritical.annotate.AllocatePermission.CurrentContext;
import static javax.safetycritical.annotate.Phase.STARTUP;
import static javax.safetycritical.annotate.Phase.INITIALIZATION;
<endif>

/**
 * A means of giving an application the ability to provide support for a
 * {@link RawMemoryRegion} that is not already provided by the standard.
 * An instance of this interface can be registered with a
 * {@link RawMemoryFactory} and provide the object that that factory
 * should return for with a given {@code RawMemoryRegion}.  It is
 * responsible for checking all requests and throwing the proper
 * exception when a request is invalid or the requester is not
 * authorized to make the request.
 *
 * @since RTSJ 2.0
 */
<if(SCJ)>
@SCJAllowed

<endif>
public interface RawMemoryRegionFactory
{
  /**
   * Determines for what region this factory creates raw memory objects.
   *
   * @return the region of this factory.
   */
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({})
  @SCJMaySelfSuspend(false)
  <endif>
  public RawMemoryRegion getRegion();

  /**
   * Determines the name of the region for which this factory creates raw
   * memory objects.
   *
   * @return the name of the region of this factory.
   */
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({})
  @SCJMaySelfSuspend(false)
  <endif>
  public String getName();
<names:{name|


 /**
   * Creates an instance of a class that implements {@link <name>\} and
   * accesses memory of {@link #getRegion\} in the address range described by
   * {@code base\}, {@code stride\}, and {@code count\}.
   * The actual extent of the memory addressed by the object is
   * {@code stride\} &times; \<i\>size of <name>\</i\> &times;
   * {@code count\}.  The object is allocated in the current memory area
   * of the calling thread.
   *
   * @param base The starting physical address accessible through the
   *             returned instance.
   *
   * @param count The number of memory elements accessible through the
   *              returned instance.
   *
   * @param stride The distance to the next element in mulitple of element
   *               count, where a value of {@code 1\} means the elements
   *               are adjacent in memory.
   *
   * @return an object that implements {@link <name>\} and supports access to
   *         the specified range in the memory region.
   *
   * @throws SecurityException when the caller does not have
   *            permissions to access the given memory region or the
   *            specified range of addresses.
   *
   * @throws IllegalArgumentException when {@code base\} is negative,
   *         {@code count\} is not greater than zero, or
   *         {@code stride\} is not greater than zero.
   *
   * @throws OffsetOutOfBoundsException when {@code base\} is invalid.
   *
   * @throws SizeOutOfBoundsException when the memory addressed by the object
   *            would extend into an invalid range of memory.
   *
   * @since RTSJ 2.0
   */
  <if(SCJ)>
  @SCJAllowed
  @SCJMayAllocate({CurrentContext\})
  @SCJMaySelfSuspend(false)
  <endif>
  public <name> create<name>(long base, int count, int stride)
    throws SecurityException, IllegalArgumentException,
      OffsetOutOfBoundsException, SizeOutOfBoundsException;
}>}
>>
