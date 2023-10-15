package com.kirilldikun.ishop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private User user;

    public Person(String name) {
        this.name = name;
    }
}
