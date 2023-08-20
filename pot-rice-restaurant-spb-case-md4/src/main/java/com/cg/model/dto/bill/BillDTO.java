package com.cg.model.dto.bill;

import com.cg.model.EPayment;
import com.cg.model.LocationRegion;
import com.cg.model.User;
import com.cg.model.dto.locationRegion.LocationRegionDTO;
import com.cg.model.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BillDTO {
    private Long id;
    private BigDecimal totalAmount;
    private UserDTO userDTO;
    private LocationRegionDTO locationRegionDTO;
    private Date createAt;
    private EPayment status;

    public BillDTO(Long id, BigDecimal totalAmount, User user, LocationRegion locationRegion, Date createAt, EPayment status) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.userDTO = user.toUserDTO();
        this.locationRegionDTO = locationRegion.toLocationRegionDTO();
        this.createAt = createAt;
        this.status = status;
    }
}
