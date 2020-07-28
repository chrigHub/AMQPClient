package jku.bac.amqp.Client.config;

import jku.bac.amqp.Client.com.ClientCartCom;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ClientCartConfig {

    @Bean(name = "checkoutQueue")
    public Queue checkoutQueue(){
        return new Queue("clientcart.checkout.msg");
    }
    @Bean(name = "itemQueue")
    public Queue itemQueue(){
        return new Queue("clientcart.item.msg");
    }

    @Bean
    public ClientCartCom clientCartCom() {
        return new ClientCartCom();
    }

    @Bean(name="cartConverter")
    public MessageConverter jsonMessageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "cartTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

}
