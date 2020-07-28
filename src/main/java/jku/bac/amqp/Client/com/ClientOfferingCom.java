package jku.bac.amqp.Client.com;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jku.bac.amqp.Client.entity.TransferItem;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ClientOfferingCom {

    @Autowired
    @Qualifier(value = "offeringTemplate")
    private RabbitTemplate template;
    @Autowired
    private DirectExchange exchange;

    public List<TransferItem> getOffering() {
        ArrayList objArr = (ArrayList) template.convertSendAndReceive(exchange.getName(),"clientoffering","Hello");
        ObjectMapper mapper = new ObjectMapper();
        List<TransferItem> transferItemList = mapper.convertValue(objArr, new TypeReference<List<TransferItem>>() { });
        System.out.println("Response came in!");
        return transferItemList;
    }

}