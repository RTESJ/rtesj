/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime;

/**
 * This class defines a set of constants that specify the supported policies
 * for starting a periodic thread or periodic timer, when it is started
 * later than the assigned absolute time.  The following table specifies the
 * effective start time, that is, the first release time of a periodic realtime
 * thread.  The effective start time of a periodic timer is similar;
 * where the first firing is equivalent to the first release, and a call to
 * the constructor is equivalent to a call to {@code RealtimeThread.start()}.
 *
 * <table width="100%" border="1">
 *   <caption>PhasingPolicy Effect on First Release of
 *            a RealtimeThread with PeriodicParameters</caption>
 *   <tr>
 *     <th align="left"></th>
 *     <th align="left">ADJUST IMMEDIATE</th>
 *     <th align="left">ADJUST FORWARD</th>
 *     <th align="left">ADJUST BACKWARD</th>
 *     <th align="left">STRICT PHASING</th>
 *   </tr>
 *   <tr>
 *     <td>RelativeTime</td>
 *     <td>The time of start method invocation plus {@code start} time.</td>
 *     <td>The time of start method invocation plus {@code start} time.</td>
 *     <td>The time of start method invocation plus {@code start} time.</td>
 *     <td>The time of start method invocation plus {@code start} time.</td>
 *   </tr>
 *   <tr>
 *     <td>AbsoluteTime, earlier than call to {@code start}</td>
 *     <td>Release immediately and set next release time to be at the
 *         time the start method was invoked plus {@code period}.</td>
 *     <td>All releases before the time {@code start} is called are
 *         ignored. The first release is at the start time plus the smallest
 *         multiple of {@code period} whose time is after the time
 *         {@code start} was called.</td>
 *     <td>The first release occurs immediately and the next release is at the
 *         start time plus the smallest multiple of {@code period} whose
 *         time is after the time {@code start} was called.</td>
 *     <td>The {@code start} method throws an exception.</td>
 *   </tr>
 *   <tr>
 *     <td>AbsoluteTime, later than call to {@code start}</td>
 *     <td>First release is at time passed to {@code start}.</td>
 *     <td>First release is at time passed to {@code start}.</td>
 *     <td>First release is at time passed to {@code start}.</td>
 *     <td>First release is at time passed to {@code start}.</td>
 *   </tr>
 *   <tr>
 *     <td>Without Time</td>
 *     <td>First release is at time of start method invocation </td>
 *     <td>First release is at time of start method invocation </td>
 *     <td>First release is at time of start method invocation </td>
 *     <td>First release is at time of start method invocation </td>
 *   </tr>
 * </table>
 *
 * @since RTSJ 2.0
 */
public enum PhasingPolicy
{
  /**
   * Indicates that a periodic thread started after the absolute
   * time given for its start time should be released immediately with
   * the next release one period later.
   */
  ADJUST_IMMEDIATE,
  /**
   * Indicates that a periodic thread started after the absolute
   * time given for its start time should be released at the next multiple
   * of its period from its start time.
   */
  ADJUST_FORWARD,
  /**
   * Indicates that a periodic thread started after the absolute
   * time given for its start time should be released immediately with
   * the next release at the next multiple of its period from its start
   * time.
   */
  ADJUST_BACKWARD,
  /**
   * Indicates that a periodic thread started after the absolute
   * time given for its start time should throw the {@link LateStartException} exception instead of being
   * released.
   */
  STRICT_PHASING;
  private PhasingPolicy() {}
}
