package az.cargora.cargora.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String PIN;

    @Embedded
    private Fullname fullname;

    @ManyToOne
    @JoinColumn(name = "pickup_point_id")
    private PickUpPoint pickUpAdress;

    private String homeAddress;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Package> packages;



}
