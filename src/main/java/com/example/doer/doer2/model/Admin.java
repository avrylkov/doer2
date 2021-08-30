package com.example.doer.doer2.model;

import javax.persistence.*;

@Entity
@Table(name = "Admin")
public class Admin {
    private Long id;
    private String login;
    private String password;

    public void setId(Long id) {
        this.id = id;
    }
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "admin_seq",
            allocationSize = 1
    )
    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
      this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
