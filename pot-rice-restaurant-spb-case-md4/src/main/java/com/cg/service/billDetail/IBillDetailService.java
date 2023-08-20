package com.cg.service.billDetail;

import com.cg.model.BillDetail;
import com.cg.model.CartDetail;
import com.cg.model.dto.bill.BillDetailDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IBillDetailService extends IGeneralService<BillDetail, Long> {

    BillDetail addBillDetail(BillDetail billDetail, CartDetail cartDetail);
    List<BillDetailDTO> findAllBillDetailDTO(Long id);
    List<BillDetailDTO> findBillDetailByBillId (Long id);

    List<BillDetailDTO> findBillDetailByBillIdStatus(Long id);
}