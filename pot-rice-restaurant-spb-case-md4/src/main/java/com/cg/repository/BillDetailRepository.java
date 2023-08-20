package com.cg.repository;

import com.cg.model.BillDetail;
import com.cg.model.dto.bill.BillDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

    @Query("SELECT NEW com.cg.model.dto.bill.BillDetailDTO ( " +
            "bd.id, " +
            "bd.product, " +
            "bd.title, " +
            "bd.unit, " +
            "bd.price, " +
            "bd.quantity, " +
            "bd.amount, " +
            "bd.bill " +
            ") " +
            "FROM BillDetail AS bd " +
            "JOIN Bill b ON bd.bill.id = b.id " +
            "WHERE b.user.id = :id"
    )
    List<BillDetailDTO> findAllBillDetailDTO(Long id);
    @Query("SELECT NEW com.cg.model.dto.bill.BillDetailDTO ( " +
            "bd.id, " +
            "bd.product, " +
            "bd.title, " +
            "bd.unit, " +
            "bd.price, " +
            "bd.quantity, " +
            "bd.amount, " +
            "bd.bill " +
            ") " +
            "FROM BillDetail AS bd " +
            "WHERE bd.bill.id = :id "
    )
    List<BillDetailDTO> findBillDetailByBillId (Long id);

    @Query("SELECT NEW com.cg.model.dto.bill.BillDetailDTO ( " +
            "bd.id, " +
            "bd.product, " +
            "bd.title, " +
            "bd.unit, " +
            "bd.price, " +
            "bd.quantity, " +
            "bd.amount, " +
            "bd.bill, " +
            "b.status" +
            ") " +
            "FROM BillDetail AS bd " +
            "JOIN Bill AS b ON bd.bill.id = b.id " +
            "WHERE bd.bill.id = :id "
    )
    List<BillDetailDTO> findBillDetailByBillIdStatus (Long id);
}