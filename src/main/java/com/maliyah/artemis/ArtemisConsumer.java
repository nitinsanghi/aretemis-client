package com.maliyah.artemis;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;

import java.io.InputStream;
import java.util.Properties;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ArtemisConsumer {
    public static void main(String[] args) {

        try (InputStream input = ArtemisConsumer.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            // String brokerUrl = "tcp://your-broker-url:61616"; // Replace with your broker URL
            // String inwardQueue = "queue/inwardQueue"; // Replace with your inward queue
            prop.load(input);
            String brokerUrl = prop.getProperty("brokerUrl");
            String inwardQueue = prop.getProperty("inwardQueue");

            try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
                    JMSContext context = factory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

                Queue queue = context.createQueue(inwardQueue);
                JMSConsumer consumer = context.createConsumer(queue);
                Message receivedMessage = consumer.receive(5000); // Wait for up to 5 seconds for a message
                if (receivedMessage != null) {
                    System.out.println("Received message: " + receivedMessage.getBody(String.class));
                } else {
                    System.out.println("No message received within the timeout.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
