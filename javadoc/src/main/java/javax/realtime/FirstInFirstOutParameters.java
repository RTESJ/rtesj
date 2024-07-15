package javax.realtime;

/**
 * Same as {@link PriorityParameters} except that it is only valid with
 * the {@link FirstInFirstOutScheduler}.
 *
 * @since RTSJ 2.0
 */
public class FirstInFirstOutParameters extends PriorityParameters
{
  private static final long serialVersionUID = 7419519828492266734L;

  /**
   * Create scheduling parameters restricted to the FIFO scheduler.
   *
   * @param priority The priority assigned to schedulables that use this
   *        parameter instance.
   *
   * @param affinity The affinity assigned to schedulables that use this
   *        parameter instance.
   */
  public FirstInFirstOutParameters(int priority, Affinity affinity)
  {
    super(priority, affinity);
  }

  /**
   * Create scheduling parameters restricted to the FIFO scheduler.
   *
   * @param priority The priority assigned to schedulables that use this
   *        parameter instance.
   */
  public FirstInFirstOutParameters(int priority)
  {
    super(priority);
  }

  @Override
  public boolean isCompatible(Scheduler scheduler)
  {
    return scheduler instanceof FirstInFirstOutScheduler;
  }

  @Override
  public boolean subsumes(SchedulingParameters other)
  {
    return (other instanceof FirstInFirstOutParameters) &&
           super.subsumes(other);
  }
}
