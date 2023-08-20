package com.cg.service.bill;


import com.cg.model.Bill;
import com.cg.model.LocationRegion;
import com.cg.model.dto.bill.BillDTO;
import com.cg.repository.BillRepository;
import com.cg.repository.LocationRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BillServiceImpl implements IBillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<Bill> findAll() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public Bill save(Bill bill) {
        LocationRegion locationRegion = locationRegionRepository.save(bill.getLocationRegion());
        bill.setLocationRegion(locationRegion);
        return billRepository.save(bill);
    }

    @Override
    public void delete(Bill bill) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<BillDTO> findAllBillDTO() {
        return billRepository.findAllBillDTO();
    }

    @Override
    public List<BillDTO> findBillByDate() {
        return billRepository.findAllBillByDate();
    }

    @Override
    public List<BillDTO> findAllBillDTOORDER() {
        return billRepository.findAllBillDTOORDER();
    }

    @Override
    public List<BillDTO> findAllBillDTOLOADING() {
        return billRepository.findAllBillDTOLOADING();
    }
    @Override
    public List<BillDTO> findAllBillDTOSHIPPING() {
        return billRepository.findAllBillDTOSHIPPING();
    }


    @Override
    public List<BillDTO> findBillDTOByIdUser(Long id) {
        return billRepository.findBillDTOByIdUser(id);
    }

    @Override
    public List<BillDTO> findBillDTOByIdBill(Long id) {
        return billRepository.findBillDTOByIdBill(id);
    }

}