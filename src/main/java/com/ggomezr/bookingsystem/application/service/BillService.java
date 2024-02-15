package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.BillDto;
import com.ggomezr.bookingsystem.domain.entity.Bill;
import com.ggomezr.bookingsystem.domain.exceptions.BillDetailNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.BillNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.BillDetailRepository;
import com.ggomezr.bookingsystem.domain.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;

    public BillService(BillRepository billRepository, BillDetailRepository billDetailRepository) {
        this.billRepository = billRepository;
        this.billDetailRepository = billDetailRepository;
    }

    public List<Bill> getAllBills(){
        return billRepository.findAll();
    }

    public Optional<Bill> getBillById(Integer id) throws BillNotFoundException {
        return Optional.ofNullable(billRepository.findById(id).orElseThrow(BillNotFoundException::new));
    }

    public List<Bill> getBillByBillDetailId(Integer billDetailId) {
        return billRepository.findByBillDetailId(billDetailId);
    }

    public void createBill(BillDto billDto) throws BillDetailNotFoundException {
        Bill bill = Bill.builder()
                .billDetail(billDetailRepository.findById(billDto.billDetailId()).orElseThrow(BillDetailNotFoundException::new))
                .totalAmount(billDetailRepository.findById(billDto.billDetailId()).orElseThrow(BillDetailNotFoundException::new).getReservationAmount())
                .issuedDate(billDto.issuedDate())
                .build();
        billRepository.save(bill);
    }

    public void updateBill(Integer id, BillDto billDto) throws BillNotFoundException{
        Bill existingBill = billRepository.findById(id).orElseThrow(BillNotFoundException::new);

        existingBill.setBillDetail(billDetailRepository.findById(billDto.billDetailId()).orElseThrow(BillNotFoundException::new));
        existingBill.setIssuedDate(billDto.issuedDate());

        billRepository.save(existingBill);
    }

    public void deleteBill(Integer id){
        billRepository.deleteById(id);
    }
}
