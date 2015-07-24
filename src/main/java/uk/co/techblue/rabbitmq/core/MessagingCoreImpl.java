package uk.co.techblue.rabbitmq.core;

import java.io.IOException;

import uk.co.techblue.rabbitmq.core.exceptions.RabbitMqClientException;
import uk.co.techblue.rabbitmq.core.producer.RabbitMqPublish;
import uk.co.techblue.rabbitmq.dto.Connection;
import uk.co.techblue.rabbitmq.dto.Message;

import com.rabbitmq.client.Channel;

/**
 * Wrapper to ease of use to publish messages to message queues and to subscribe consumers to Brokers(exchange).
 */
public final class MessagingCoreImpl implements MessagingCore {

    /**
     * Publish messages to queues.
     *
     * @param message message entity to publish.
     * @param connection the connection
     * @throws RabbitMqClientException the rabbit mq client exception
     */
    @Override
    public void publish(Message message, Connection connection) throws RabbitMqClientException {

        RabbitMqConnectionFactory rabbitMqConnection = new RabbitMqConnectionFactory(connection.getConnectionUri());
        rabbitMqConnection.newConnection();

        try {
            RabbitMqPublish.instance().send(message, rabbitMqConnection);
        } catch (IOException e) {
            throw new RabbitMqClientException("cant publish messages", e);
        } finally {
            rabbitMqConnection.close();
        }
    }

    /**
     * Registers and initializes consumer to consume given Broker(exchange).
     *
     * @param exchangeName the exhange name to use on message subscribe.
     * @param routingKey the routing key to use for the binding to queue.
     * @param connection the connection
     * @return new {@link Channel} which is bind to an exchange by the given parameters
     * @throws RabbitMqClientException the rabbit mq client exception
     */
    @Override
    public Channel subscribe(String exchangeName, String routingKey, Connection connection) throws RabbitMqClientException {

        RabbitMqConnectionFactory rabbitMqConnection = new RabbitMqConnectionFactory(connection.getConnectionUri());
        rabbitMqConnection.newConnection();
        Channel channel = rabbitMqConnection.newChannel();

        try {
            channel.queueBind(channel.queueDeclare().getQueue(), exchangeName, routingKey);
        } catch (IOException e) {
            throw new RabbitMqClientException("Cant bind the queue to exchange", e);
        }
        return channel;
    }
}