/*-----------------------------------------------------------------------*\
 * Copyright 2012--2024, aicas GmbH; all rights reserved.
 * This header, including copyright notice, may not be altered or removed.
 *-----------------------------------------------------------------------*/
package javax.realtime.control;

/**
 * {@code Interruptible} is an interface implemented by classes
 * that will be used as arguments on the methods{@code doInterruptible()} of
 * {@link AsynchronouslyInterruptedException} and its subclasses.
 * {@code doInterruptible()} invokes the implementations of the
 * methods in this interface.
 */
public interface Interruptible
{
    /**
     * The main piece of code that is executed when an implementation
     * is given to {@code doInterruptible()}.
     * When a class is created that implements this
     * interface, for example through an anonymous inner class,
     * it must include the {@code throws} clause
     * to make the method interruptible.
     *
     * @param exception The AIE object whose doInterruptible method is
     *        calling the {@code run} method. Used to invoke methods on
     *        {@link AsynchronouslyInterruptedException} from
     *        within the {@code run()} method.
     *
     */
    public void run(AsynchronouslyInterruptedException exception)
        throws AsynchronouslyInterruptedException;

    /**
     * This method is called by the system when the {@code run()} method
     * is interrupted.  By using this, the program logic can determine
     * when the {@code run()} method completed normally or had its control
     * asynchronously transferred to its caller.
     *
     * @param exception The currently pending AIE. Used to invoke methods on
     *        {@link AsynchronouslyInterruptedException} from
     *        within the {@code interruptAction()} method.
     */
    public void interruptAction(AsynchronouslyInterruptedException exception);
}
