/*------------------------------------------------------------------------*
 * Copyright 2020-2021, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime.memory;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An annotation to mark the memory area to use for class allocation
 * and initialization for a package.
 */
@Retention(RUNTIME)
@Target({PACKAGE})
public @interface ClassAllocation
{
  MemoryAreaType value() default MemoryAreaType.IMMORTAL;
}
