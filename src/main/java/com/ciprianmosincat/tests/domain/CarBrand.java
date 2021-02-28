package com.ciprianmosincat.tests.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class CarBrand {

    @Id
    @Setter(AccessLevel.NONE)
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Car> cars;

    @Builder
    public CarBrand(final String name, final Set<Car> cars) {
        this.name = name;
        this.cars = cars;
    }

    public void addCar(final Car car) {
        if (this.cars == null) {
            this.cars = new HashSet<>();
        }
        this.cars.add(car);
    }

}
