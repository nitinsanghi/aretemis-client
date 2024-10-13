package com.maliyah.artemis;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class ArtemisTest {
    public static void main(String[] args) {

        String brokerUrl = "tcp://achmqtest.cbonet.cboroot.om:443";
        String outwardQueue = "ach_cbs_RET_outward";
       
        try (
                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
                JMSContext context = factory.createContext(JMSContext.AUTO_ACKNOWLEDGE);) {

            Queue queue = context.createQueue(outwardQueue);
            JMSProducer producer = context.createProducer();

            String message = "Test message from ArtemisProducer";
            producer.send(queue, message);

            System.out.println("Message sent: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
