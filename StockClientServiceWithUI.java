package com.javatechie.service;

import com.javatechie.dto.OrderSummaryDTO;
import com.javatechie.dto.StockOrderDTO;
import com.javatechie.grpc.OrderSummary;
import com.javatechie.grpc.StockOrder;
import com.javatechie.grpc.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class StockClientServiceWithUI {

    @GrpcClient("stockService")
    private StockTradingServiceGrpc.StockTradingServiceStub stockServiceStub;

    public OrderSummaryDTO sendBulkOrders(List<StockOrderDTO> ordersDTO) {
        CountDownLatch latch = new CountDownLatch(1);
        final OrderSummaryDTO[] resultHolder = new OrderSummaryDTO[1];

        StreamObserver<OrderSummary> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(OrderSummary summary) {
                // Convert Protobuf OrderSummary to DTO
                resultHolder[0] = new OrderSummaryDTO();
                resultHolder[0].setTotalOrders(summary.getTotalOrders());
                resultHolder[0].setSuccessCount(summary.getSuccessCount());
                resultHolder[0].setTotalAmount(summary.getTotalAmount());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };

        StreamObserver<StockOrder> requestObserver = stockServiceStub.placeBulkOrder(responseObserver);

        // Convert the DTOs to Protobuf messages and send them
        for (StockOrderDTO orderDTO : ordersDTO) {
            StockOrder order = StockOrder.newBuilder()
                    .setOrderId(orderDTO.getOrderId())
                    .setStockSymbol(orderDTO.getStockSymbol())
                    .setOrderType(orderDTO.getOrderType())
                    .setPrice(orderDTO.getPrice())
                    .setQuantity(orderDTO.getQuantity())
                    .build();

            requestObserver.onNext(order);
        }

        requestObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultHolder[0]; // Return DTO to the controller
    }


}
