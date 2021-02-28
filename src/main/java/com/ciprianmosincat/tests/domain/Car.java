package com.ciprianmosincat.tests.domain;

import com.ciprianmosincat.tests.domain.enums.TractionMode;
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
public class Car {

    @Id
    @Setter(AccessLevel.NONE)
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private CarBrand brand;

    private BigDecimal price;

    private TractionMode tractionMode;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "car_id"))
    private Set<User> users = new HashSet<>();

    @Builder
    public Car(final String name, final CarBrand brand, final BigDecimal price, final TractionMode tractionMode) {
        this.name = name;
        this.price = price;
        this.tractionMode = tractionMode;
        setBrand(brand);
    }

    public void setBrand(final CarBrand brand) {
        this.brand = brand;
        brand.addCar(this);
    }

    public void addUser(final User user) {
        users.add(user);
        user.addCar(this);
    }

}
