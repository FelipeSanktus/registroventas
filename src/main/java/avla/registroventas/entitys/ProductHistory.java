package avla.registroventas.entitys;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "userproducthistory")
public class ProductHistory {
    private static String pattern = "dd-MM-yyyy";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @Column(name = "publishedDate")
    private Date publishedDate;

    @Column(name = "action")
    private String action;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ProductHistory(Product product, String action) {
        this.product = product;
        this.action = action;
        Date dat = new Date();
        this.publishedDate = new java.sql.Date(dat.getTime());
    }
}


