package az.cargora.cargora.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import az.cargora.cargora.enums.PackageStatus;

@Entity
@Table(name = "package_history")
@Getter
@Setter
public class PackageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package relatedPackage;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageStatus status;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;


    public PackageHistory(Package pkg, PackageStatus status) {
        
        this.timestamp = LocalDateTime.now();
        this.relatedPackage = pkg;
        this.status = status;
    }

}
