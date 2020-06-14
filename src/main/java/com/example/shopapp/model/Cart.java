package com.example.shopapp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne(mappedBy = "cart")
    private User customer;

    @OneToMany(mappedBy = "cart")
    private Set<Item> items;

    public int getId() {
        return id;
    }

//    public User getCustomer() {
//        return customer;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }


}
