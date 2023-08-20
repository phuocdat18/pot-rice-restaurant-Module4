package com.cg.repository;

import com.cg.model.Bill;
import com.cg.model.dto.bill.BillDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO(" +
            "b.id, " +
            "b.totalAmount, " +
            "b.user," +
            "b.locationRegion," +
            "b.createdAt, " +
            "b.status) " +
            "FROM Bill b " +
            "WHERE b.status = 0")
    List<BillDTO> findAllBillDTOORDER ();

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO(" +
            "b.id, " +
            "b.totalAmount, " +
            "b.user," +
            "b.locationRegion," +
            "b.createdAt, " +
            "b.status) " +
            "FROM Bill b " +
            "WHERE b.status = 1")
    List<BillDTO> findAllBillDTOLOADING ();

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO(" +
            "b.id, " +
            "b.totalAmount, " +
            "b.user," +
            "b.locationRegion," +
            "b.createdAt, " +
            "b.status) " +
            "FROM Bill b " +
            "WHERE b.status = 2")
    List<BillDTO> findAllBillDTOSHIPPING ();

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO(" +
            "b.id, " +
            "b.totalAmount, " +
            "b.user," +
            "b.locationRegion," +
            "b.createdAt, " +
            "b.status) " +
            "FROM Bill b " +
            "WHERE b.status = 3")
    List<BillDTO> findAllBillDTO ();
//    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO(" +
//            "b.id, " +
//            "b.totalAmount, " +
//            "b.user," +
//            "b.locationRegion," +
//            "b.createdAt, " +
//            "b.status) " +
//            "FROM Bill b " +
//            "WHERE b.status = 3" )
//    List<BillDTO> findBillDTOByMonth ();
    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO(" +
            "b.id, " +
            "b.totalAmount, " +
            "b.user," +
            "b.locationRegion," +
            "b.createdAt, " +
            "b.status) " +
            "FROM Bill b " +
            "WHERE b.status = 3" +
            "AND b.createdAt = toDate(CURRENT_DATE)")
    List<BillDTO> findAllBillByDate ();

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO(" +
            "b.id, " +
            "b.totalAmount, " +
            "b.user," +
            "b.locationRegion, " +
            "b.createdAt, " +
            "b.status) " +
            "FROM Bill b " +
            "WHERE b.user.id = :id")
    List<BillDTO> findBillDTOByIdUser (Long id);

    @Query("SELECT NEW com.cg.model.dto.bill.BillDTO(" +
            "b.id, " +
            "b.totalAmount, " +
            "b.user," +
            "b.locationRegion, " +
            "b.createdAt, " +
            "b.status) " +
            "FROM Bill b " +
            "WHERE b.id = :id")
    List<BillDTO> findBillDTOByIdBill (Long id);

}