/*------------------------------------------------------------------------*
 * Copyright 2012-2021, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *------------------------------------------------------------------------*/
package javax.realtime;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Configuration parameters provide a way to specify various
 * implementation-dependent parameters such as the Java stack and native
 * stack sizes, and to configure the statically allocated
 * {@link ThrowBoundaryError} associated with a {@link Schedulable}.
 *
 * <p> Note that these parameters are immutable.
 *
 * @since RTSJ 2.0
 */
public class ConfigurationParameters implements Cloneable, Serializable
{
  private static final long serialVersionUID = -1928226204703184541L;

  private static final int _DEFAULT_MESSAGE_LENGTH_ = 80;
  private static final int _DEFAULT_FRAME_COUNT_ = 10;
  private static final int _DEFAULT_CLASS_NAME_LENGTH_ = 52;
  private static final int _DEFAULT_METHOD_NAME_LENGTH_ = 32;
  private static final int _DEFAULT_FILE_NAME_LENGTH_ = 24;

  /**
   * The current default configuration parameters object.
   */
  private static ConfigurationParameters _default_ = null;

  /**
   * The current default release runner object.
   */
  private static ReleaseRunner _runner_ = null;

  private static final Runnable restoreDefaultConfig = new Runnable()
  {
    public void run()
    {
      setDefault(new ConfigurationParameters(_DEFAULT_MESSAGE_LENGTH_,
                                             _DEFAULT_FRAME_COUNT_,
                                             _DEFAULT_CLASS_NAME_LENGTH_,
                                             _DEFAULT_METHOD_NAME_LENGTH_,
                                             _DEFAULT_FILE_NAME_LENGTH_,
                                             null));
    }
  };

  /**
   * Set the parameters object to be used when none is provided for an
   * instance of {@link Schedulable}.
   *
   * @param config the new default parameter object.  Setting to
   *        {@code null} restores the default values.
   */
  public static synchronized void setDefault(ConfigurationParameters config)
  {
    _default_ = config;
  }

  /**
   * Set the parameters object to be used when none is provided for an
   * instance of {@link Schedulable}.
   *
   * @return the default parameter object.
   */
  public static synchronized ConfigurationParameters getDefault()
  {
    if (_default_ == null)
      {
        HeapMemory.instance().executeInArea(restoreDefaultConfig);
      }
    return _default_;
  }

  /**
   * Sets the system default heap release runner.
   *
   * @param runner The runner to be used when none is set.  When
   *        {@code null}, the default release runner is set to the
   *        original system default.
   */
  public static synchronized void setDefaultRunner(ReleaseRunner runner)
    throws StaticIllegalArgumentException
  {
    _runner_ = runner;
  }

  /** Maximum number of characters stored for an exception message. */
  private final int messageLength_;

  /** Maximum number of stack trace entries stored for an exception. */
  private final int stackTraceDepth_;

  /** Maximum number of character stored for class name in each frame. */
  private final int classNameLength_;

  /** Maximum number of character stored for method signature in each frame. */
  private final int methodNameLength_;

  /** Maximum number of character stored for file name in each frame. */
  private final int fileNameLength_;

  /** Maximum stack sizes (system dependent). */
  private final long[] sizes_;

  /**
   * Creates a parameter object for initializing the state of a
   * {@link Schedulable}.  The parameters provide the data for this
   * initialization.  For {@link RealtimeThread} and bound versions of
   * {@link AsyncBaseEventHandler}, the stack and message buffers
   * can be set exactly, but for the unbound event handlers, the system
   * cannot give any guarentees to allow thread sharing.
   *
   * @param messageLength The size of the buffer, in units of {@code char},
   *        for storing an exception message used by preallocated exceptions
   *        and errors thrown in the context
   *        of an instance of {@link Schedulable} which was created with
   *        {@code this} as its configuration parameters.  The value {@code 0}
   *        indicates that no message should be stored. The value of {@code -1}
   *        uses the system default and is the default when an instance of
   *        this class is not provided.
   *
   * @param stackTraceDepth The number of stack trace elements,
   *        reserved for use by preallocated exceptions and errors
   *        thrown in the execution context of the {@link Schedulable} object
   *        created with these parameters.  The
   *        amount of space this requires is implementation-specific.
   *        The value {@code 0} indicates that no stack trace
   *        should be stored.  The value of <code>-1</code> uses
   *        the system default and is the default when an instance of this
   *        class is not provided.
   *
   * @param classNameLength The number of characters reserved in each frame
   *        for saving the full class name in a given stack trace frame.
   *
   * @param methodNameLength The number of characters reserved in each frame
   *        for saving the method signature in a given stack trace frame.
   *
   * @param fileNameLength The number of characters reserved in each frame
   *        for saving the file name in a given stack trace frame.
   *
   * @param sizes An array of implementation-specific values dictating
   *        memory parameters for {@code Schedulable} objects
   *        created with these parameters, such as maximum Java and
   *        native stack sizes.  The {@code sizes} array will not
   *        be stored in the constructed object.  The default is system
   *        dependent, and indicated by setting this parameter to {@code null}
   *        or by not providing an instance of this class.
   *        <p>JamaicaVM: not yet used.
   */
  public ConfigurationParameters(int messageLength,
                                 int stackTraceDepth,
                                 int classNameLength,
                                 int methodNameLength,
                                 int fileNameLength,
                                 long[] sizes)
    throws StaticIllegalStateException
  {
    messageLength_ = (messageLength >= 0) ?
      messageLength : _DEFAULT_MESSAGE_LENGTH_;
    stackTraceDepth_ = (stackTraceDepth >= 0) ?
      stackTraceDepth : _DEFAULT_FRAME_COUNT_;
    classNameLength_ = (classNameLength >= 0) ?
      classNameLength : _DEFAULT_CLASS_NAME_LENGTH_;
    methodNameLength_ = (methodNameLength >= 0) ?
      methodNameLength : _DEFAULT_METHOD_NAME_LENGTH_;
    fileNameLength_ = (fileNameLength >= 0) ?
      fileNameLength : _DEFAULT_FILE_NAME_LENGTH_;
    sizes_ = sizes;
  }


  /**
   * Same as {@link #ConfigurationParameters(int,int,int,int,int,long[])}
   * with arguments {@code (0, 0, 0, 0, 0, sizes)}.
   */
  public ConfigurationParameters(long[] sizes)
  {
    this(0, 0, 0, 0, 0, sizes);
  }

  /**
   * Gets the system default release runner.
   *
   * @return a general runner to be used when none is set.
   */
  public synchronized ReleaseRunner getDefaultRunner()
  {
    if (_runner_ == null)
      {
        _runner_ = HeapMemory.instance().executeInArea(newDefaultRunner);
      }
    return _runner_;
  }

  private static final NewDefaultRunner newDefaultRunner =
    new NewDefaultRunner();
  private static class NewDefaultRunner
    implements Supplier<FirstInFirstOutReleaseRunner>
  {
    @Override
    public FirstInFirstOutReleaseRunner get()
    {
      return new FirstInFirstOutReleaseRunner(getDefault());
    }
  }

  /**
   * Determines whether or not this {@code schedulable} may use the heap.
   *
   * @return {@code true} only when this configuration may allocate
   *         on the heap and may enter the {@code Heap}.
   */
  public boolean mayUseHeap() { return true; }


  /**
   * Obtain the size of the buffer dedicated to storing the message of the
   * last thrown throwable in the context of instances of {@link Schedulable}
   * created with these parameters.
   *
   * The value {@code 0} indicates that no message will be stored.
   *
   * @return reserved memory in units of {@code char}.
   */
  public int getMessageLength()
  {
    return messageLength_;
  }

  /**
   * Obtain the number of frames available for storing the stack trace of the
   * last thrown throwable in the context of instances of {@link Schedulable}
   * created with these parameters.
   *
   * The value {@code 0} indicates that no stack trace will be stored.
   *
   * @return reserved memory as number of frames to save.
   */
  public int getStackTraceDepth()
  {
    return stackTraceDepth_;
  }

  /**
   * Obtain the maximum number of character available for storing class names
   * in each stack trace frame.
   *
   * @return reserved memory in units of {@code char}.
   */
  public int getClassNameLength()
  {
    return classNameLength_;
  }

  /**
   * Obtain the maximum number of character available for storing method
   * signatures in each stack trace frame.
   *
   * @return reserved memory in units of {@code char}.
   */
  public int getMethodNameLength()
  {
    return methodNameLength_;
  }

  /**
   * Obtain the maximum number of character available for storing file names
   * in each stack trace frame.
   *
   * @return reserved memory in units of {@code char}.
   */
  public int getFileNameLength()
  {
    return fileNameLength_;
  }

 /**
   * Gets the array of implementation-specific sizes associated with
   * {@link Schedulable} objects created with these parameters.
   * <em>This method may allocate memory.</em>
   *
   * @return a copy of the array of implementation-specific sizes or
   *         {@code null} when none are set.
   */
  public long[] getSizes()
  {
    if (sizes_ == null)
      {
        return null;
      }
    else
      {
        return sizes_.clone();
      }
  }
}
