package com.github.experimental.art;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "familia")
public class Familia {

    @Id
    private String codFamilia;

    public Familia() {

    }

    public Familia(String codFamilia) {
        this.codFamilia = codFamilia;
    }

    public String getCodFamilia() {
        return codFamilia;
    }

    public void setCodFamilia(String codFamilia) {
        this.codFamilia = codFamilia;
    }

}
