package com.deha.ecommerceapp.dto;

import com.deha.ecommerceapp.model.Order;
import com.deha.ecommerceapp.model.Product;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OrderProductDto {

    private Product product;
    private Integer quantity;
    private Order order;

    public OrderProductDto() {
        this.product = product;
        this.quantity = quantity;
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("order_id", this.order.getId())
                .append("product_id", this.product.getId())
                .append("product_name", this.product.getName())
                .append("price", this.product.getPrice())
                .append("date", this.order.getDateCreated())
                .append("quantity", this.quantity)
                .toString();
    }
}
