package com.example.userservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String phone;
    private String password;
    private String city;
    private String district;
    private String ward;
    private String detailLocation;

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL)
    private Shop shop;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;
}
