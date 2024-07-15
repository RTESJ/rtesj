/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * When {@code true}, which is the default, indicates that a method returns
 * the object upon which it has been called; hence, the return value may be
 * safely ignored.
 *
 * @rtsj.issue Should this be in a separate package.
 *
 * @since RTSJ 2.0
 */
@Retention(CLASS)
@Target({METHOD})
public @interface ReturnsThis
{
  boolean value = true;
}
