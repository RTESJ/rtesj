/*-----------------------------------------------------------------------*\
 * Copyright 2016-2023, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * Represents type size classes for deciding how large a lambda is.  This
 * size is dependent on what variables the lambda expression contains in
 * its closure, i.e., it encloses.  It is used by the {@code reserveLambda}
 * methods in {@link SizeEstimator}.
 *
 * @since RTSJ 2.0
 */
public enum EnclosedType
{
  /** Represents a Java boolean. */
  BOOLEAN,
  /** Represents a Java byte. */
  BYTE,
  /** Represents a Java char. */
  CHAR,
  /** Represents a Java short. */
  SHORT,
  /** Represents a Java int. */
  INT,
  /** Represents a Java float. */
  FLOAT,
  /** Represents a Java long. */
  LONG,
  /** Represents a Java double. */
  DOUBLE,
  /** Represents a reference to any object. */
  REFERENCE;
}
