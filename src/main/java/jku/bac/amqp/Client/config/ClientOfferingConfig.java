package jku.bac.amqp.Client.config;

import ch.qos.logback.core.joran.spi.DefaultClass;
import jku.bac.amqp.Client.com.ClientOfferingCom;
import jku.bac.amqp.Client.entity.TransferItem;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ClientOfferingConfig {

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange("clientoffering.rpc");
    }
    @Bean
    public ClientOfferingCom clientOfferingCom() {
        return new ClientOfferingCom();
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        jsonMessageConverter.setClassMapper(classMapper());
        return jsonMessageConverter;
    }

    @Bean(name = "offeringTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public DefaultClassMapper classMapper(){
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        //idClassMapping.put("jku.bac.amqp.Offering.entity.TransferItem", TransferItem.class);
        idClassMapping.put("java.util.ArrayList", ArrayList.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }



}