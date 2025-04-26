package com.javatechie.controller;

import com.javatechie.dto.OrderSummaryDTO;
import com.javatechie.dto.StockOrderDTO;
import com.javatechie.service.StockClientServiceWithUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class ClientStreamingController {

    @Autowired
    private StockClientServiceWithUI stockClientService;

    @PostMapping("/bulk-order")
    public OrderSummaryDTO placeBulkOrder(@RequestBody List<StockOrderDTO> orders) {
        return stockClientService.sendBulkOrders(orders);
    }
}
