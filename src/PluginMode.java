import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerPlugin;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.security.*;

import javax.jms.*;
import javax.jms.Connection;

public class PluginMode implements FileWriting {

    private static String brokerURL = "vm://localhost";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    private Destination destination;
    private BrokerService service;

    private String username = "guest";
    private String password = "password";

    public void before() throws Exception {
        factory = new ActiveMQConnectionFactory(username, password, brokerURL);
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("MyQueue");
    }

    public void after() throws Exception {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ex) {}
        }

        if (service != null) {
            try {
                service.stop();
            } catch (Exception ex) {}
        }
    }

    public void run() throws Exception {

        MessageProducer producer = session.createProducer(destination);
        try {
            TextMessage message = session.createTextMessage();
            message.setText("Hello, World! from: PluginMode "
                    + Thread.currentThread().getName() + ":hashCode=" + this.hashCode());
            producer.send(message);
            write("Sent message: " + message.getText());
        } finally {
            producer.close();
        }

        MessageConsumer consumer = session.createConsumer(destination);
        try {
            TextMessage message = (TextMessage) consumer.receive();
            write("Received: " + message.getText());
        } finally {
            consumer.close();
        }
    }


}
