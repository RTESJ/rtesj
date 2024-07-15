/*-----------------------------------------------------------------------*\
 * Copyright 2012-2021, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.memory;

import javax.realtime.FirstInFirstOutReleaseRunner;
import javax.realtime.ImmortalMemory;
import javax.realtime.MemoryArea;
import javax.realtime.ConfigurationParameters;
import javax.realtime.StaticIllegalArgumentException;
import javax.realtime.StaticIllegalStateException;

import java.util.function.Supplier;

import javax.realtime.ReleaseRunner;

/**
 * This is the same as {@link javax.realtime.ConfigurationParameters}
 * except an instance of {@link javax.realtime.BoundSchedulable} that
 * uses this parameters object may not access the heap and one that uses
 * the super type may.  A VM need only fully enforce this in
 * interpreted mode.
 */
public class ScopedConfigurationParameters extends ConfigurationParameters
{
  private static final long serialVersionUID = -2356305640190685646L;
  private static final int _DEFAULT_MESSAGE_LENGTH_ = 80;
  private static final int _DEFAULT_FRAME_COUNT_ = 10;
  private static final int _DEFAULT_CLASS_NAME_LENGTH_ = 52;
  private static final int _DEFAULT_METHOD_NAME_LENGTH_ = 32;
  private static final int _DEFAULT_FILE_NAME_LENGTH_ = 24;

  /**
   * The current default configuration parameters object.
   */
  private static ScopedConfigurationParameters _default_ = null;

  /**
   * The current default release runner object.
   */
  private static ReleaseRunner _runner_ = null;

  private static final
    Supplier<ScopedConfigurationParameters> newDefaultConfig =
    new Supplier<ScopedConfigurationParameters>()
  {
    public ScopedConfigurationParameters get()
    {
      return new ScopedConfigurationParameters(_DEFAULT_MESSAGE_LENGTH_,
                                               _DEFAULT_FRAME_COUNT_,
                                               _DEFAULT_CLASS_NAME_LENGTH_,
                                               _DEFAULT_METHOD_NAME_LENGTH_,
                                               _DEFAULT_FILE_NAME_LENGTH_,
                                               null);
    }
  };

  private static final
    Supplier<FirstInFirstOutReleaseRunner> newDefaultRunner =
    new Supplier<FirstInFirstOutReleaseRunner>()
  {
    public FirstInFirstOutReleaseRunner get()
    {
      return new FirstInFirstOutReleaseRunner(getDefault());
    }
  };

  /**
   * Set the parameters object to be used when none is provided for an
   * instance of {@link javax.realtime.Schedulable}.  Setting to
   * {@code null} restores the default values.
   *
   * @param config the new default parameter object.
   *
   * @throws StaticIllegalArgumentException when {@code config} is not
   *         allocated in immortal memory or its configuration
   *         parameters are not {@code ScopedConfigurationParameters}.
   */
  public static synchronized
    void setDefault(ScopedConfigurationParameters config)
  {
    if (MemoryArea.getMemoryArea(config) != ImmortalMemory.instance())
      {
        throw StaticIllegalArgumentException.get().
          init("Argument is not in immortal memory.");
      }
    else
      {
        _default_ = config;
      }
  }

  /**
   * Set the parameters object to be used when none is provided for an
   * instance of {@link javax.realtime.Schedulable}.
   *
   * @return the default parameter object.
   */
  public static synchronized ScopedConfigurationParameters getDefault()
  {
    if (_default_ == null)
      {
        _default_ = ImmortalMemory.instance().executeInArea(newDefaultConfig);
      }
    return _default_;
  }

  /**
   * Sets the system default release runner.
   *
   * @param runner The runner to be used when none is set.  When
   *        {@code null}, the default release runner is set to the
   *        original system default.
   *
   * @throws StaticIllegalArgumentException when {@code runner} is not
   *         allocated in immortal memory or its configuration
   *         parameters are not {@code ScopedConfigurationParameters}.
   */
  public synchronized static void setDefaultRunner(ReleaseRunner runner)
    throws StaticIllegalArgumentException
  {
    ConfigurationParameters config = runner.getConfigurationParameters();
    if (MemoryArea.getMemoryArea(runner) != ImmortalMemory.instance())
      {
        throw StaticIllegalArgumentException.get().
          init("Argument is not in immortal memory.");
      }
    else if (!(config instanceof ScopedConfigurationParameters))
      {
        throw StaticIllegalArgumentException.get().
          init("Argument does not have matching configuration parameters.");
      }
    else
      {
        _runner_ = runner;
      }
  }

  /**
   * Gets the system default release runner.
   *
   * @return a general runner to be used when none is set.
   */
  @Override
  public synchronized ReleaseRunner getDefaultRunner()
  {
    if (_runner_ == null)
      {
        _runner_ = ImmortalMemory.instance().executeInArea(newDefaultRunner);
      }
    return _runner_;
  }

  /**
   * Similar to {@link
   * javax.realtime.ConfigurationParameters#ConfigurationParameters(int,
   * int, int, int, int, long[])}, except the receiver may not use the heap.
   *
   * @throws StaticIllegalStateException when the current memory context is
   *         a heap memory.
   */
  public ScopedConfigurationParameters(int messageLength,
                                       int stackTraceDepth,
                                       int classNameLength,
                                       int methodNameLength,
                                       int fileNameLength,
                                       long[] sizes)
    throws StaticIllegalStateException
  {
    super(messageLength, stackTraceDepth,
          classNameLength, methodNameLength,
          fileNameLength, sizes);
  }

  @Override
  public boolean mayUseHeap() { return false; }
}
