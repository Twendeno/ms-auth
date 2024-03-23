package com.twendeno.msauth.heritage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.twendeno.msauth.business.entity.Business;
import com.twendeno.msauth.heritage.model.*;
import com.twendeno.msauth.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Heritage extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "business_uuid", referencedColumnName = "uuid", nullable = false)
    @JsonIgnore
    private Business business;

    private String lastUserUpdate;

    @Column(unique = true,columnDefinition = "varchar(15)")
    private String reference; // référence

    @Column(unique = true,columnDefinition = "varchar(20)")
    private String matriculation; // immatriculation

    private Instant buyDate; // date d'achat
    private Instant firstRegistration; // première immatriculation
    private int mileage = 0; // kilométrage
    private double price = 0.0; // prix d'achat
    private double sellingPrice = 0.0; // prix de vente
    private double rentPrice = 0.0; // prix de location
    private double deposit = 0.0; // caution

    private Instant lastTechnicalInspection; // dernière visite technique
    private Instant nextTechnicalInspection; // prochaine visite technique

    private Instant lastMaintenance; // dernière maintenance
    private Instant nextMaintenance; // prochaine maintenance

    private Instant lastOilChange; // dernière vidange
    private Instant nextOilChange; // prochaine vidange

    private Instant lastInsurance; // dernière assurance
    private Instant newInsurance; // nouvelle assurance

    // Emergency
    private boolean accident = false; // est urgent
    private boolean breakdown = false; // est en panne
    private boolean maintenance = false; // est en maintenance
    private boolean technicalInspection = false; // est en visite technique
    private boolean oilChange = false; // est en vidange


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    private HealthStatus healthStatus; // état de santé


    //  General information
    private String brand; // marque
    private String model; // modèle

    // Technical information
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    private CarType carType; // type de véhicule
    private double fiscalPower; // puissance fiscale

    // Engine information
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    private FuelType fuelType; // type de carburant

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    private TransmissionType transmission; // transmission

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(50)")
    private BoxType boxType ; // type de boîte

    // Dimensions information
    private int numberOfSeats = 2; // nombre de places
    private int numberOfDoors; // nombre de portes
    private int numberOfGears = 5; // nombre de vitesses
    private double length = 0.0; // longueur cm
    private double width = 0.0; // largeur cm
    private double height = 0.0; // hauteur cm

}
