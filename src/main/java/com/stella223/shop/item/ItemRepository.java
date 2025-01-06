package com.stella223.shop.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    //같은 이름의 class 또한 만들어줌, DB 입출력하는 함수 잔뜩 들어있음
    Page<Item> findPageBy(Pageable page);
}
