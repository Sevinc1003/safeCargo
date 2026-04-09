package az.cargora.cargora.entity;

import org.springframework.data.mapping.AccessOptions.GetOptions.GetNulls;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class PickUpPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;


    private String pickUpAddress;

}
