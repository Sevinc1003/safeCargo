package az.cargora.cargora.entity.PackageHistory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "packageHistory")
@Getter
@Setter
public class PackageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
