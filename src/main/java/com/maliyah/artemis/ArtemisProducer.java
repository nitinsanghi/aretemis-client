package com.maliyah.artemis;

import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ArtemisProducer {
    public static void main(String[] args) {

        try (InputStream input = ArtemisProducer.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            prop.load(input);
            String brokerUrl = prop.getProperty("brokerUrl");
            String outwardQueue = prop.getProperty("outwardQueue");
            try (
                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
                JMSContext context = factory.createContext(JMSContext.AUTO_ACKNOWLEDGE);
            ) {

                Queue queue = context.createQueue(outwardQueue);
                JMSProducer producer = context.createProducer();
                
                String message = "Test message from ArtemisProducer";
                producer.send(queue, message);
                System.out.println("Message sent: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
