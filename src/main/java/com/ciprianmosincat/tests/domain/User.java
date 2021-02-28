package com.ciprianmosincat.tests.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @Setter(AccessLevel.NONE)
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;
    private BigDecimal amount;

    @ManyToMany(mappedBy = "users")
    private Set<Car> cars = new HashSet<>();

    @Builder
    public User(final String userName, final String firstName, final String lastName, final BigDecimal amount, final Set<Car> cars) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
        this.cars = cars;
    }

    public void addCar(final Car car) {
        if (this.cars == null) {
            this.cars = new HashSet<>();
        }
        this.cars.add(car);
    }

}


