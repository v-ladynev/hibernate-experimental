package com.github.experimental.art;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Articulo",
        uniqueConstraints = @UniqueConstraint(columnNames = { "cod_articulo", "cod_familia" }))
public class Articulo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    // @Id
    @Column(name = "cod_articulo")
    private String codArticulo;

    // @Id
    @ManyToOne
    @JoinColumn(name = "cod_familia")
    private Familia familia;

    public Articulo() {

    }

    public Articulo(String codArticulo, Familia familia) {
        this.codArticulo = codArticulo;
        this.familia = familia;
    }

    public Long getId() {
        return id;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    @Override
    public String toString() {
        return String.format("User codArticulo: %s, familia: %s", codArticulo,
                familia.getCodFamilia());
    }

}
