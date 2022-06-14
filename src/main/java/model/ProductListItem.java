/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.dba_floristik.Product;

/**
 *
 * @author marku
 */
public class ProductListItem {

    private Product product;

    private int orderAmount;

    private double priceForAmount;

    public ProductListItem(Product product, int orderAmount) {
        this.product = product;
        this.orderAmount = orderAmount;
        this.priceForAmount = orderAmount * product.getPpricenetto();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        priceForAmount = orderAmount * product.getPpricenetto();
        this.product = product;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        priceForAmount = orderAmount * product.getPpricenetto();
        this.orderAmount = orderAmount;
    }

    public double getPriceForAmount() {
        return priceForAmount;
    }

}
