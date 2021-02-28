package avla.registroventas.entitys;

import java.util.List;

public class SoldResume {
    private List<Product> products;
    private Long totalAmount;
    private int itemQuantiyty;

    public SoldResume(List<Product> products) {

        if(products != null){
            this.products = products;
            this.itemQuantiyty = products.size();
            Long amount = (long) 0;
            for (Product product: products) {
                amount = amount+product.getPrice();
            }
            this.totalAmount = amount;
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getItemQuantiyty() {
        return itemQuantiyty;
    }

    public void setItemQuantiyty(int itemQuantiyty) {
        this.itemQuantiyty = itemQuantiyty;
    }
}
