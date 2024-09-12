package com.example.auth.entities;

import com.example.auth.entities.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Table(name = "users")
@Entity
public class User {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Set<Classe> classes;

}
