package jku.bac.amqp.Client.com;

import jku.bac.amqp.Client.entity.TransferItem;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ClientCartCom {

    @Autowired
    @Qualifier(value = "cartTemplate")
    private RabbitTemplate template;
    @Autowired
    @Qualifier(value = "itemQueue")
    private Queue itemQueue;
    @Autowired
    @Qualifier(value = "checkoutQueue")
    private Queue checkoutQueue;

    public void sendToCart(TransferItem item, int amount){
        item.setAmount(amount);
        this.template.convertAndSend(itemQueue.getName(),item);
        System.out.println("Item "+ item.getLabel() +" sent to itemQ");
    }

    public void checkoutCart(){
        this.template.convertAndSend(checkoutQueue.getName(),"checkout");
        System.out.println("Sent message: checkout");
    }

}
