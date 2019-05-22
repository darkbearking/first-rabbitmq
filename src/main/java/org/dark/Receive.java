package org.dark;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receive {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
//		創建連接
		Connection connection = null;
//		創建通道，並向這個通道發送消息
		Channel channel = null;
		
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			String queueName = "hello";
			channel.queueDeclare(queueName, false, false, false, null);
			
			//通過隊列和通道，創建一個消費者
			Consumer consumer = new DefaultConsumer(channel) {

				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String msg = new String(body, "UTF-8");
					System.out.println("收到的消息 " + msg);
				}
				
			};
			
			//將通道綁定消費者
			channel.basicConsume(queueName, consumer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
