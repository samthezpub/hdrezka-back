package com.example.hdrezka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Film {
    @Id
    @GeneratedValue
    private Long id;

    private Long filmIdOnSite;
    private String name;
    private String imageURL;
}
