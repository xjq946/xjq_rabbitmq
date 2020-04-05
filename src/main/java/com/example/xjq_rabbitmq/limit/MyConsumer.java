package com.example.xjq_rabbitmq.limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {

	//==========================重点=================================
	private Channel channel ;
	//==========================重点=================================
	
	public MyConsumer(Channel channel) {
		super(channel);
		this.channel = channel;
	}

	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		System.err.println("-----------consume message----------");
		System.err.println("consumerTag: " + consumerTag);
		System.err.println("envelope: " + envelope);
		System.err.println("properties: " + properties);
		System.err.println("body: " + new String(body));

		//==========================重点=================================
		//multiple:false表示是否批量处理，因为qos设置为1，所以multiple设置为false
		channel.basicAck(envelope.getDeliveryTag(), false);
		//==========================重点=================================
	}


}
