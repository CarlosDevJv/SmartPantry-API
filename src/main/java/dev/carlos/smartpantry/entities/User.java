package dev.carlos.smartpantry.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String email;
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "user_id")
    private Set<ItemEstoque> item = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "user_id")
    private Set<Receita> receitas = new HashSet<>();

    public User(@Nullable Object principal, @Nullable Object credentials) {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    @Override
    public @Nullable String getPassword() {
        return getSenha();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return getEmail();
    }
}
