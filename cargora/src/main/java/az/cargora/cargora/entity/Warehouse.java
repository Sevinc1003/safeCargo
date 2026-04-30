package az.cargora.cargora.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
// Test
@Entity
@Table(name = "warehouses")
@Getter
@Setter
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;

    private String addressTitle;
    private String addressDetails;
    private String ZIPcode;
    private String phoneNumber;

    
    public Warehouse(String country, String addressTitle, String addressDetails, String zIPcode, String phoneNumber) {
        this.country = country;
        this.addressTitle = addressTitle;
        this.addressDetails = addressDetails;
        ZIPcode = zIPcode;
        this.phoneNumber = phoneNumber;
    }

    

}
