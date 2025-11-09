package com.sapienza.reverie.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="charms")
public class Charm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String description;

    @Column(unique = true, nullable = false)
    private String pictureUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "collected_charms")
    @JsonIgnore
    private List<User> collectors;

}
