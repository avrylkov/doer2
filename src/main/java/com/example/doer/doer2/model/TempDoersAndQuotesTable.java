package com.example.doer.doer2.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "temp_doers_quotes_table")
public class TempDoersAndQuotesTable {
    private Long id;
    private String doerName;
    private String doerSurname;
    private String quote;

    @Basic
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator"
    )
    @SequenceGenerator(
            name = "sequence-generator",
            sequenceName = "admintable_seq",
            allocationSize = 1
    )

    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "doername")
    public String getDoerName() {
        return doerName;
    }

    public void setDoerName(String doername) {
        this.doerName = doername;
    }

    @Basic
    @Column(name = "doersurname")
    public String getDoerSurname() {
        return doerSurname;
    }

    public void setDoerSurname(String doersurname) {
        this.doerSurname = doersurname;
    }

    @Basic
    @Column(name = "quote")
    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TempDoersAndQuotesTable that = (TempDoersAndQuotesTable) o;
        return Objects.equals(id, that.id) && Objects.equals(doerName, that.doerName) && Objects.equals(doerSurname, that.doerSurname) && Objects.equals(quote, that.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doerName, doerSurname, quote);
    }
}
