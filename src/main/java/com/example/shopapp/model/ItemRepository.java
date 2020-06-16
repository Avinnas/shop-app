package com.example.shopapp.model;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByCustomer_IdOrderByDateSold(Integer id);
    List<Item> findByProduct_IdAndSoldIsFalseAndCartIsNull(Integer id);
    List<Item> findByProduct_IdAndCart_Id(Integer id, Integer cart_id);
}
