package az.cargora.cargora.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "balances")
@Getter
@Setter
public class Balance {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal balance;

    //yuzdeliye nece cedvelde yuvarlaq gosterim bilmirem. arasdirib duzelderem
    // @Digits(integer = 8, fraction = 2)
    // @Column(precision = 10, scale = 2)
    private BigDecimal bonus;
}
