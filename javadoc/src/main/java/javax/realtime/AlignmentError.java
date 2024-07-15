/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
/*
 * Created on Nov 20, 2010
 *
 * Copyright (C) 2004, 2005 TimeSys Corporation, All Rights Reserved.
 */
package javax.realtime;

/**
 * The exception thrown on a request for a raw memory factory to
 * return memory for a base address that is aligned such that the
 * factory cannot guarantee that loads and stores based on that address
 * will meet the factory's specifications. For instance, on many
 * processors, odd addresses are unsuitable for anything but byte
 * access.
 *
 * @since RTSJ 2.0
 */
public class AlignmentError
  extends StaticError implements StaticThrowable<AlignmentError>
{
  private static final long serialVersionUID = 1L;

  private static final AlignmentError _singleton_ = new AlignmentError();

  /**
   * Obtains the singleton of this static throwable.
   * It should be initialized before throwing.
   *
   * @return the single instance of this throwable.
   *
   * @since RTSJ 2.0
   */
  public static AlignmentError get()
  {
    return _singleton_;
  }

  private AlignmentError() {}

  @Override
  public AlignmentError getSingleton() { return _singleton_; }
}
