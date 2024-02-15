package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.BillDetailDto;
import com.ggomezr.bookingsystem.domain.entity.BillDetail;
import com.ggomezr.bookingsystem.domain.exceptions.BillDetailNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.ReservationNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.BillDetailRepository;
import com.ggomezr.bookingsystem.domain.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillDetailService {

    private final BillDetailRepository billDetailRepository;
    private final ReservationRepository reservationRepository;

    public BillDetailService(BillDetailRepository billDetailRepository, ReservationRepository reservationRepository) {
        this.billDetailRepository = billDetailRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<BillDetail> getAllBillDetails(){
        return billDetailRepository.findAll();
    }

    public Optional<BillDetail> getBillDetailById(Integer id) throws BillDetailNotFoundException{
        return Optional.ofNullable(billDetailRepository.findById(id).orElseThrow(BillDetailNotFoundException::new));
    }

    public List<BillDetail> getBillDetailsByReservation(Integer reservationId){
        return billDetailRepository.findByReservationId(reservationId);
    }

    public void createBillDetail(BillDetailDto billDetailDto) throws ReservationNotFoundException {
        BillDetail billDetail = BillDetail.builder()
                .reservation(reservationRepository.findById(billDetailDto.reservationId()).orElseThrow(ReservationNotFoundException::new))
                .description(billDetailDto.description())
                .reservationAmount(reservationRepository.findById(billDetailDto.reservationId()).orElseThrow(ReservationNotFoundException::new).getAmount())
                .build();
        billDetailRepository.save(billDetail);
    }

    public void updateBillDetail(Integer id, BillDetailDto billDetailDto) throws BillDetailNotFoundException, ReservationNotFoundException {
        BillDetail existingBillDetail = billDetailRepository.findById(id).orElseThrow(BillDetailNotFoundException::new);

        existingBillDetail.setReservation(reservationRepository.findById(billDetailDto.reservationId()).orElseThrow(ReservationNotFoundException::new));
        existingBillDetail.setDescription(billDetailDto.description());

        billDetailRepository.save(existingBillDetail);
    }

    public void deleteBillDetail(Integer id){
        billDetailRepository.deleteById(id);
    }
}
