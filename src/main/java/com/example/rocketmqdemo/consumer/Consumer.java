package com.example.rocketmqdemo.consumer;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * 消费者
 * 
 * @author yangpengfei
 * @date 2018年4月23日
 */
public class Consumer {

	public static void main(String[] args) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
		consumer.setNamesrvAddr("10.200.9.49:9876");
		try {
			// 订阅PushTopic下Tag为push的消息
			consumer.subscribe("PullTopic", "pull");
			// 程序第一次启动从消息队列头取数据
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
						ConsumeConcurrentlyContext Context) {
					Message msg = list.get(0);
					System.out.println(msg.toString());
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			/**
			 * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
			 */
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}