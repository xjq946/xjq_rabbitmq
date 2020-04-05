package com.example.xjq_rabbitmq.custom.consumer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

//可以实现Consumer接口但比较麻烦，直接继承DefaultConsumer
public class MyConsumer extends DefaultConsumer {


	public MyConsumer(Channel channel) {
		super(channel);
	}

	//==========================重点=================================
	//envelope对象很重要，envelope.deliveryTag属性很重要，它会自动递增
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		System.err.println("-----------consume message----------");
		System.err.println("consumerTag: " + consumerTag);
		System.err.println("envelope: " + envelope);
		System.err.println("properties: " + properties);
		System.err.println("body: " + new String(body));
	}
	//==========================重点=================================


}
