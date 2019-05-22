package org.dark;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

	//通過下面的代碼，會向RabbitMQ發送“hello world”的消息
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
//		創建連接
		Connection connection = null;
//		創建通道，並向這個通道發送消息
		Channel channel = null;
//		若為localhost其實不需要設置，默認端口5671
//		factory.setHost("localhost");
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			//聲明一個隊列，名稱叫“hello”
			String queueName = "hello";
			channel.queueDeclare(queueName, false, false, false, null);
			
			//聲明發送到消息隊列的消息體
			String message = "Hello World!";
			channel.basicPublish("", queueName, null, message.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				channel.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
