package com.example.xjq_rabbitmq.deadMessage;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

public class Consumer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("192.168.36.131");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		// 这就是一个普通的交换机 和 队列 以及路由
		String exchangeName = "test_dlx_exchange";
		String routingKey = "deadMessage.#";
		String queueName = "test_dlx_queue";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		//==========================重点=================================
		Map<String, Object> agruments = new HashMap<String, Object>();
		//value的值可以自定义
		agruments.put("x-dead-letter-exchange", "deadMessage.exchange");
		//这个agruments属性，要设置到声明队列上
		channel.queueDeclare(queueName, true, false, false, agruments);
		//==========================重点=================================
		channel.queueBind(queueName, exchangeName, routingKey);
		//==========================重点=================================
		//要进行死信队列的声明:
		channel.exchangeDeclare("deadMessage.exchange", "topic", true, false, null);
		channel.queueDeclare("deadMessage.queue", true, false, false, null);
		channel.queueBind("deadMessage.queue", "deadMessage.exchange", "#");
		//==========================重点=================================
		channel.basicConsume(queueName, true, new MyConsumer(channel));
	}
}