package com.stella223.shop.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
@Getter
@Setter
public class Item { // 원하는 테이블 이름
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; // 기본키
    @Column() // 컬럼 타입 강제로 지정 가능
    public String title; // 문자 255자만 설정 가능
    public Integer price;
    public String username;
    public String url;

    public Item() {
        this(null, null, null, null);
    }

    public Item(String title, Integer price, String username, String url) {
        this.title = title;
        this.price = price;
        this.username = username;
        this.url = url;
    }
}

