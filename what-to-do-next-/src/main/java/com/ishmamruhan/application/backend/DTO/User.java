package com.ishmamruhan.application.backend.DTO;

import com.ishmamruhan.application.backend.Helpers.DateGenerator;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @Column(name = "id")
    private String id;

    @Size(min = 3, max = 20)
    private String userName;

    private String email;

    @Size(min = 6)
    private String password;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "use_role",
            joinColumns = {
                    @JoinColumn(name = "user_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id")
            }
    )
    private Set<Role> roles = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(cascade =  {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "refs")
    private Set<User> users  = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(cascade =  {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<Task> tasks = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User assciatedWith;

    private Boolean isActive;

    private String createAt = DateGenerator.getDate();

    private String updatedAt;
}
