package local;

import local.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomercenterViewHandler {


    @Autowired
    private CustomercenterRepository customercenterRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrdered_then_CREATE_1 (@Payload Ordered ordered) {
        try {
            if (ordered.isMe()) {
                // view 객체 생성
                Customercenter customercenter = new Customercenter();
                // view 객체에 이벤트의 Value 를 set 함
                customercenter.setQty(ordered.getQty());
                customercenter.setOrderId(ordered.getId());
                customercenter.setProductId(ordered.getProductId());
                // view 레파지 토리에 save
                customercenterRepository.save(customercenter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCanceled_then_UPDATE_1(@Payload OrderCanceled orderCanceled) {
        try {
            if (orderCanceled.isMe()) {
                // view 객체 조회
                List<Customercenter> customercenterList = customercenterRepository.findByOrderId(orderCanceled.getId());
                for(Customercenter customercenter : customercenterList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customercenter.setStatus(orderCanceled.getStatus());
                    // view 레파지 토리에 save
                    customercenterRepository.save(customercenter);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenShipped_then_UPDATE_2(@Payload Shipped shipped) {
        try {
            if (shipped.isMe()) {
                // view 객체 조회
                List<Customercenter> customercenterList = customercenterRepository.findByOrderId(shipped.getOrderId());
                for(Customercenter customercenter : customercenterList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customercenter.setStatus(shipped.getStatus());
                    // view 레파지 토리에 save
                    customercenterRepository.save(customercenter);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}