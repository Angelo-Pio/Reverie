package com.sapienza.reverie.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Should be hashed in a real application

    @Column(unique = true, nullable = false)
    private String profilePictureUrl;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Charm> created_charms;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_collected_charms",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "charm_id")
)
    private List<Charm> collected_charms;



}