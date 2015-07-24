package uk.co.techblue.rabbitmq.core.exceptions;

/**
 * This exception indicates an error processing a {@link MessagingCoreImpl} or a {@link RabbitMqPublish}.
 */
public class RabbitMqClientException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3025916422765570405L;

    /**
     * Constructs an empty instance.
     */
    public RabbitMqClientException() {
        super();
    }

    /**
     * Constructs an instance from the given argument.
     *
     * @param message exception message
     * @param cause exception cause
     */
    public RabbitMqClientException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an instance from the given argument.
     *
     * @param message exception message
     */
    public RabbitMqClientException(String message) {
        super(message);
    }

    /**
     * Constructs an instance from the given argument.
     *
     * @param cause exception cause
     */
    public RabbitMqClientException(Throwable cause) {
        super(cause);
    }
}