Raw(package, type, name, SCJ) ::= <<
/* copyright © 2012-2024 aicas GmbH; all rights reserved */
/* This java file is automatically generated, so do not edit it directly. */

package <package>;

<if(SCJ)>
import javax.safetycritical.annotate.SCJAllowed;
<endif>

/**
 * A marker for an object that can be used to access a single <type>.
 * Read and write access to that <type> is checked by the factory that
 * creates the instance; therefore, no access checking is provided by this
 * interface, only bounds checking.
 *
 * @since RTSJ 2.0
 */
<if(SCJ)>
@SCJAllowed

<endif>
public interface Raw<name> extends Raw<name>Reader, Raw<name>Writer
{
}
>>
