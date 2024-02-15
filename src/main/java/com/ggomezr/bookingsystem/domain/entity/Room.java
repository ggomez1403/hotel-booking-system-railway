package com.ggomezr.bookingsystem.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imgUrl;
    private String name;
    private Boolean available;
    private String type;
    private Integer capacity;
    private BigDecimal initialPrice;
    private BigDecimal taxesAndFees;
    private BigDecimal totalPrice;

    @ElementCollection
    @CollectionTable(name="bathroom_amenities", joinColumns=@JoinColumn(name="room_id"))
    private List<String> bathroomAmenities;

    @ElementCollection
    @CollectionTable(name="bedroom_amenities", joinColumns=@JoinColumn(name="room_id"))
    private List<String> bedroomAmenities;

    @ElementCollection
    @CollectionTable(name="entertainment_amenities", joinColumns=@JoinColumn(name="room_id"))
    private List<String> entertainmentAmenities;

    @ElementCollection
    @CollectionTable(name="food_and_drinks_amenities", joinColumns=@JoinColumn(name="room_id"))
    private List<String> foodAndDrinksAmenities;

    @ElementCollection
    @CollectionTable(name="internet_amenities", joinColumns=@JoinColumn(name="room_id"))
    private List<String> internetAmenities;

    @ElementCollection
    @CollectionTable(name="room_more_amenities_amenities", joinColumns=@JoinColumn(name="room_id"))
    private List<String> moreAmenities;

    @ElementCollection
    @CollectionTable(name="room_highlights_amenities", joinColumns=@JoinColumn(name="room_id"))
    private List<String> highlights;

}
