package uk.co.techblue;

import java.io.IOException;

import uk.co.techblue.rabbitmq.core.MessagingCore;
import uk.co.techblue.rabbitmq.core.MessagingCoreImpl;
import uk.co.techblue.rabbitmq.core.consumer.RabbitMqConsumer;
import uk.co.techblue.rabbitmq.dto.Connection;
import uk.co.techblue.rabbitmq.dto.Message;
import uk.co.techblue.rabbitmq.dto.constant.ExchangeType;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;

/**
 * The Class RabbitMqProducerTest.
 */
public class RabbitMqProducerTest {

    /** The _send msg. */
    private static MessagingCore _sendMsg = new MessagingCoreImpl();

    /** The Constant VHOST. */
    private static final String VHOST = "/";

    /** The Constant USERNAME. */
    private static final String USERNAME = "dishant.anand";

    /** The Constant PASSWORD. */
    private static final String PASSWORD = "ENTER-YOUR-PASSWORD-HERE";

    /** The Constant SERVER_ADDRESS. */
    private static final String SERVER_ADDRESS = "localhost:5672";//DEFAULT PORT FOR RABBIT-MQ

    /** The _body. */
    private static byte[] _body = "someMessageBody".getBytes();

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String args[]) {
        final Connection connection = new Connection(VHOST, USERNAME, PASSWORD, SERVER_ADDRESS);

        final BasicProperties _prop = new BasicProperties().builder().contentEncoding("UTF-8").contentType("application/json")
                .deliveryMode(2).messageId("someId").build();

        Message msg = new Message(_prop, _body, "TestExchange", "test-exchange",ExchangeType.DIRECT ,1);
        
        _sendMsg.publish(msg, connection);

        final Channel channel = _sendMsg.subscribe("TestExchange", "test-exchange", connection);
        
        try {
            final RabbitMqConsumer rabbitMqConsumer = new RabbitMqConsumer(channel) {
                
                @Override
                public void handleMessage(Message message) {
                    System.out.println(message);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
