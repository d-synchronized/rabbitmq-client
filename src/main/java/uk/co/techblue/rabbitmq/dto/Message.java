package uk.co.techblue.rabbitmq.dto;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * The Class Message.
 */
public final class Message {

    /** The Constant DEFAULT_MESSAGE_CHARSET. */
    public static final Charset DEFAULT_MESSAGE_CHARSET = StandardCharsets.UTF_8;

    /** The _body content. */
    private byte[] _bodyContent = new byte[0];

    /** The _basic properties. */
    private BasicProperties _basicProperties;

    /** The _routing key. */
    private String _routingKey;

    /** The _exchange. */
    private String _exchange;

    /** The _delivery tag. */
    private long _deliveryTag;

    private String _exchangeType;

    /** The _json object mapper. */
    private static ObjectMapper _jsonObjectMapper = new ObjectMapper();

    /**
     * Construct new Message with the given values.
     *
     * @param basicProperties {@link BasicProperties} basicproperties of the message containing values like e.g headers,
     *        deliverymode, correlationId, contentEncoding etc.
     * @param body body of the message
     * @param exchange exchange which the message is published to
     * @param routingKey routing key used to bound messages to queue
     * @param deliveryTag identifier for a message on the broker
     */
    public Message(BasicProperties basicProperties, byte[] body, String exchange, String routingKey,
            final String exchangeType, long deliveryTag) {
        _basicProperties = basicProperties;
        _bodyContent = body;
        _exchange = exchange;
        _routingKey = routingKey;
        _deliveryTag = deliveryTag;
        setExchangeType(exchangeType);
    }

    /**
     * Instantiates a new message.
     *
     * @param basicProperties the basic properties
     * @param body the body
     * @param exchange the exchange
     * @param routingKey the routing key
     * @param deliveryTag the delivery tag
     */
    public Message(BasicProperties basicProperties, byte[] body, String exchange, String routingKey, long deliveryTag) {
        _basicProperties = basicProperties;
        _bodyContent = body;
        _exchange = exchange;
        _routingKey = routingKey;
        _deliveryTag = deliveryTag;
    }

    /**
     * Gets the basic message properties.
     *
     * @return The message properties
     */
    public BasicProperties getBasicProperties() {
        return _basicProperties;
    }

    /**
     * Gets the body content in bytes.
     *
     * @return The body content as bytes
     */
    public byte[] getBodyContent() {
        return _bodyContent;
    }

    /**
     * Gets the exchange to which the message is published to.
     *
     * @return The exchange
     */
    public String getExchange() {
        return _exchange;
    }

    /**
     * Gets the routing key used to bound messages to queue.
     *
     * @return The routing key
     */
    public String getRoutingKey() {
        return _routingKey;
    }

    /**
     * Gets the message delivery tag.
     *
     * @return The delivery tag
     */
    public long getDeliveryTag() {
        return _deliveryTag;
    }

    /**
     * Gets the message body as the given type <code>klass</code>.
     *
     * @param <T> the generic type
     * @param klass the generic type of the entity
     * @return an instance of <code>klass</code> representing the message body
     * @throws IOException possible cause is that the message body can't be converted to the given <code>klass</code>.
     */
    @SuppressWarnings("unchecked")
    public <T> T getBodyAsJavaType(Class<T> klass) throws IOException {

        if (String.class.isAssignableFrom(klass)) {
            return (T) getBodyContentAsString();
        } else if (Boolean.class.isAssignableFrom(klass)) {
            return (T) getBodyContentAsBoolean();
        } else if (Double.class.isAssignableFrom(klass)) {
            return (T) getBodyContentAsDouble();
        } else if (Long.class.isAssignableFrom(klass)) {
            return (T) getBodyContentAsLong();
        } else {
            return getBodyContentAsObject(klass);
        }
    }

    /**
     * Gets the message body as String.
     *
     * @return message body as String
     */
    private String getBodyContentAsString() {
        return new String(getBodyContent(), DEFAULT_MESSAGE_CHARSET);
    }

    /**
     * Gets the message body as Long.
     *
     * @return message body as Long
     */
    private Long getBodyContentAsLong() {
        return Long.valueOf(getBodyContentAsString()).longValue();
    }

    /**
     * Get the message body as Boolean.
     *
     * @return message body as Boolean
     */
    private Boolean getBodyContentAsBoolean() {
        return Boolean.valueOf(getBodyContentAsString());
    }

    /**
     * Get the message body as Double.
     *
     * @return message body as Double
     */
    private Double getBodyContentAsDouble() {
        return Double.valueOf(getBodyContentAsString());
    }

    /**
     * Get the message body as the given entity type <code>klass</code>.
     *
     * @param <T> the generic type
     * @param klass the generic type of the entity
     * @return an instance of <code>klass</code> representing the message body
     * @throws IOException possible cause is that the message body can't be converted to the given <code>klass</code>.
     */
    private <T> T getBodyContentAsObject(Class<T> klass) throws IOException {
        String contentAsString = getBodyContentAsString();
        return _jsonObjectMapper.readValue(contentAsString, klass);
    }

    public String getExchangeType() {
        return _exchangeType;
    }

    public void setExchangeType(String _exchangeType) {
        this._exchangeType = _exchangeType;
    }

}