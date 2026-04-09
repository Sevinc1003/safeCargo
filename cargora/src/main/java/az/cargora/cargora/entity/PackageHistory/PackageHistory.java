package az.cargora.cargora.entity.PackageHistory;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "packageHistory")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}
