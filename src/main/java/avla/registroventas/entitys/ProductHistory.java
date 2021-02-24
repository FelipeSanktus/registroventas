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
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

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

    public User getProduct() {
        return user;
    }

    public void setProduct(User user) {
        this.user = user;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ProductHistory(User user, String action) {
        this.user = user;
        this.action = action;
        Date dat = new Date();
        this.publishedDate = new java.sql.Date(dat.getTime());
    }

    public ProductHistory(){
        super();
    }
}


