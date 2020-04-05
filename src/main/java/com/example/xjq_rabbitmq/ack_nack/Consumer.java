package com.example.xjq_rabbitmq.ack_nack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.36.131");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		
		String exchangeName = "test_ack_exchange";
		String queueName = "test_ack_queue";
		String routingKey = "ack_nack.#";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);

		//==========================重点=================================
		// 手工签收 必须要关闭 autoAck = false
		channel.basicConsume(queueName, false, new MyConsumer(channel));
		//==========================重点=================================
	}
}
