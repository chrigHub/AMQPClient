package jku.bac.amqp.Client;

import jku.bac.amqp.Client.com.ClientCartCom;
import jku.bac.amqp.Client.com.ClientOfferingCom;
import jku.bac.amqp.Client.entity.TransferItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AMQPServicesEndpoint {
    private ClientOfferingCom clientOfferingCom;
    private ClientCartCom clientCartCom;
    private List<TransferItem> itemList;

    public AMQPServicesEndpoint(ClientOfferingCom clientOfferingCom, ClientCartCom clientCartCom) {
        this.clientOfferingCom = clientOfferingCom;
        this.clientCartCom = clientCartCom;
        this.itemList = new ArrayList<>();
    }

    @GetMapping("/offering")
    public String getOffering(){
        List<TransferItem> items= clientOfferingCom.getOffering();
        this.itemList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        for(TransferItem item : items){
            this.itemList.add(item);
            stringBuilder.append("ID: "+ item.getId() +" Label: "+ item.getLabel() +" Prize: "+ item.getPrize() +" Amount: "+ item.getAmount() + " || ");
        }
        return stringBuilder.toString();
    }

    @GetMapping("/toCart")
    public String sendToCart(@RequestParam String label, @RequestParam int amount){
        for(TransferItem item : this.itemList){
            if(item.getLabel().equals(label)){
                clientCartCom.sendToCart(item,amount);
            }
        }
        return "sendToCart done!";
    }
    @GetMapping("/checkout")
    public String checkoutCart(){
        clientCartCom.checkoutCart();
        return "checkout done!";
    }
}