package com.stella223.shop.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@ToString
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column()
    public String username;
    public String password;
    public String displayName;

    public Member(String userName, String password, String displayName) {
        this.username = userName;
        this.password = password;
        this.displayName = displayName;
    }

    public Member(){
        this(null, null, null);
    }
}
