package com.example.xjq_rabbitmq.returnlistener;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

public class Producer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.36.131");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_return_exchange";
		//==========================重点=================================
		//String routingKey = "return.save"; //正确的路由key
		String routingKeyError = "abc.save";//错误的路由key
		//==========================重点=================================
		
		String msg = "Hello RabbitMQ Return Message";

		//==========================重点=================================
		channel.addReturnListener(new ReturnListener() {
			@Override
			public void handleReturn(int replyCode, String replyText, String exchange,
					String routingKey, BasicProperties properties, byte[] body) throws IOException {
				
				System.err.println("---------handle  return----------");
				System.err.println("replyCode: " + replyCode);
				System.err.println("replyText: " + replyText);
				System.err.println("exchange: " + exchange);
				System.err.println("routingKey: " + routingKey);
				System.err.println("properties: " + properties);
				System.err.println("body: " + new String(body));
			}
		});
		//mandatory:当路由key错误时并且mandatory为true时会触发监听，当mandatory为false时会自动删除不可路由的消息
		channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());
		//==========================重点=================================
	}
}
