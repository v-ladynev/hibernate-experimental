package com.github.experimental.onetoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author V.Ladynev
 */
@Entity
@Table(name = "class")
public class ClassModel {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private String id;

    @OneToOne
    @PrimaryKeyJoinColumn
    BookingModel bookingModel;

    public ClassModel() {

    }

    public ClassModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BookingModel getBookingModel() {
        return bookingModel;
    }

    public void setBookingModel(BookingModel bookingModel) {
        this.bookingModel = bookingModel;
    }

}
