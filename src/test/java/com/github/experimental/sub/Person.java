package com.github.experimental.sub;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Car> cars = new ArrayList<Car>();

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    private List<Profession> professions = new ArrayList<Profession>();

    public Long getId() {
        return id;
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Profession> getProfessions() {
        return professions;
    }

    public void ListId(Long id) {
        this.id = id;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }

}
