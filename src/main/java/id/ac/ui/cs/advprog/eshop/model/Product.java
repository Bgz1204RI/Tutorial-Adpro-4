package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private int quantity;

    public String getProductId() {
        return id;
    }

    public void setProductId(String productId) {
        this.id = productId;
    }

    public String getProductName() {
        return name;
    }

    public void setProductName(String productName) {
        this.name = productName;
    }

    public int getProductQuantity() {
        return quantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.quantity = productQuantity;
    }
}