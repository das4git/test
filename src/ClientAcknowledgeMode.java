import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ClientAcknowledgeMode implements Runnable, FileWriting {
    public void run() {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    "tcp://localhost:61616/admin");
//            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
//                    "vm://localhost");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue("TEST");

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            MessageConsumer consumer = session.createConsumer(destination);
            String text = "Hello, World! from: ClientAcknowledgeMode " + Thread.currentThread().getName() + ":hashCode=" + this.hashCode();
            TextMessage message = session.createTextMessage(text);

            write("Sent message: " + message.getText());
            producer.send(message);
            consumer.receive();


            TextMessage textMessage = (TextMessage) message;
            String s = textMessage.getText();
            write("Received: " + s);

            message.acknowledge();
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
