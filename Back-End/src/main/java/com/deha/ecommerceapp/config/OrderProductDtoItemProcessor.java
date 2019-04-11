package com.deha.ecommerceapp.config;

import com.deha.ecommerceapp.dto.OrderProductDto;
import org.springframework.batch.item.ItemProcessor;

public class OrderProductDtoItemProcessor implements ItemProcessor<OrderProductDto, OrderProductDto> {

    @Override
    public OrderProductDto process(OrderProductDto orderProductDto) throws Exception {
        return orderProductDto;
    }
}
