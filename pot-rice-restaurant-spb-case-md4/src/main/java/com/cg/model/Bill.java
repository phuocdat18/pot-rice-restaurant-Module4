package com.cg.model;

import com.cg.model.dto.bill.BillDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bills")
public class Bill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 0, nullable = false)
    private BigDecimal totalAmount;


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "location_region_id", referencedColumnName = "id")
    private LocationRegion locationRegion;

    @Column(name = "bill_status")
    private EPayment status;

    public Bill(BigDecimal totalAmount, User user, LocationRegion locationRegion, EPayment status) {
        this.totalAmount = totalAmount;
        this.user = user;
        this.locationRegion = locationRegion;
        this.status = status;
    }

    public BillDTO toBillDTO() {
        return new BillDTO()
                .setId(id)
                .setTotalAmount(totalAmount)
                .setUserDTO(user.toUserDTO())
                .setLocationRegionDTO(locationRegion.toLocationRegionDTO())
                .setStatus(status);
    }
}
