package com.cg.service.billDetail;

import com.cg.model.BillDetail;
import com.cg.model.CartDetail;
import com.cg.model.dto.bill.BillDetailDTO;
import com.cg.repository.BillDetailRepository;
import com.cg.repository.CartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillDetailServiceImpl implements IBillDetailService {

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;



    @Override
    public List<BillDetail> findAll() {
        return billDetailRepository.findAll();
    }

    @Override
    public Optional<BillDetail> findById(Long id) {
        return billDetailRepository.findById(id);
    }

    @Override
    public BillDetail save(BillDetail billDetail) {
        return billDetailRepository.save(billDetail);
    }

    @Override
    public void delete(BillDetail billDetail) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public BillDetail addBillDetail(BillDetail billDetail, CartDetail cartDetail) {

        BillDetail billDetail1 = billDetailRepository.save(billDetail);
        cartDetailRepository.delete(cartDetail);
        return billDetail1 ;
    }

    @Override
    public List<BillDetailDTO> findAllBillDetailDTO(Long id) {
        return billDetailRepository.findAllBillDetailDTO(id);
    }

    @Override
    public List<BillDetailDTO> findBillDetailByBillId(Long id) {
        return billDetailRepository.findBillDetailByBillId(id);
    }
    @Override
    public List<BillDetailDTO> findBillDetailByBillIdStatus(Long id) {
        return billDetailRepository.findBillDetailByBillIdStatus(id);
    }
}