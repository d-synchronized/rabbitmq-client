package uk.co.techblue.rabbitmq.dto.constant;

/**
 * Abstract class to hold RabbitMQ exchange types. Contains all available exchange types on RabbitMQ.
 */
public abstract class ExchangeType {

    /** The Constant TOPIC. */
    public static final String TOPIC = "topic";

    /** The Constant FANOUT. */
    public static final String FANOUT = "fanout";

    /** The Constant DIRECT. */
    public static final String DIRECT = "direct";

    /** The Constant HEADRES. */
    public static final String HEADERS = "headers";
}
