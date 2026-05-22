package com.mark.cafe.producers;

import com.mark.cafe.entity.Order;

import com.mark.cafe.entity.Player;
import com.mark.cafe.service.OrderService;
import com.mark.cafe.view.OutputView;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

import static com.mark.cafe.messages.GameMessage.DOWN_RATING;
import static com.mark.cafe.messages.OrderMessage.DISPLAY_ORDER;

public class OrderProducer implements Runnable{
    private final OrderService orderService;
    private final BlockingQueue<Order> orderQueue;
    private final Player player;
    private final OutputView outputView;

    public OrderProducer(OrderService orderService,
                         BlockingQueue<Order> orderQueue,
                         OutputView outputView,
                         Player player

    ){
        this.orderService = orderService;
        this.orderQueue = orderQueue;
        this.player = player;
        this.outputView = outputView;
    }

    @Override
    public void run() {
        Random random = new Random();
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Order order = orderService.receiveOrder();
                outputView.printMessage(DISPLAY_ORDER);
                boolean isInserted = orderQueue.offer(order);
                if (!isInserted) {
                    outputView.printMessage(DOWN_RATING);
                    player.downRating();
                }
                int orderDelay = 5000 + random.nextInt(1000) + 1;
                Thread.sleep(orderDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
