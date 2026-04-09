package az.cargora.cargora.entity.PackageHistory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "package_history")
@Getter
@Setter
public class PackageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //Package claasi yaradilsin, ondan sonra burda one2many qurmaliyiq(bu class FK dasiyacaq),
    //ve package classinda inverse side etse ela olar.


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackageStatus status;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;



    public PackageHistory() {
    }

    public PackageHistory(PackageStatus status,LocalDateTime timestamp){
        this.status = status;
        this.timestamp = timestamp;

    }

}
