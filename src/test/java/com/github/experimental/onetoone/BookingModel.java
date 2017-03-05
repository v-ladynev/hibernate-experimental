package com.github.experimental.onetoone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author V.Ladynev
 */
@Entity
@Table(name = "booking")
public class BookingModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    String id;

    @OneToOne(mappedBy = "bookingModel", cascade = CascadeType.ALL)
    ClassModel classModel;

    public BookingModel() {

    }

    public BookingModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClassModel getClassModel() {
        return classModel;
    }

    public void setClassModel(ClassModel classModel) {
        this.classModel = classModel;
    }

}



