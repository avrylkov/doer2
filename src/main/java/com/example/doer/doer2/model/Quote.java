package com.example.doer.doer2.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
public class Quote {
    private Long id;
    private String text;
    private BigInteger likes;
    private Doer doer;

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "quote_seq",
            allocationSize = 1
    )

    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "likes")
    public BigInteger getLikes() {
        return likes;
    }

    public void setLikes(BigInteger likes) {
        this.likes = likes;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "id_doer", referencedColumnName = "id", nullable = false)
    public Doer getDoer() {
        return doer;
    }

    public void setDoer(Doer doer) {
        this.doer = doer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(id, quote.id) && Objects.equals(text, quote.text) && Objects.equals(likes, quote.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, likes);
    }

}
