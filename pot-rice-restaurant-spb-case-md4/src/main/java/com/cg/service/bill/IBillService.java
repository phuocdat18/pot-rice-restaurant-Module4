package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.dto.bill.BillDTO;
import com.cg.model.dto.bill.BillDetailDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IBillService extends IGeneralService<Bill, Long> {
    List<BillDTO> findAllBillDTO ();

    List<BillDTO> findBillByDate();

    List<BillDTO> findAllBillDTOORDER();


    List<BillDTO> findAllBillDTOLOADING();

    List<BillDTO> findAllBillDTOSHIPPING();

    List<BillDTO> findBillDTOByIdUser (Long id);


    List<BillDTO> findBillDTOByIdBill(Long id);
}