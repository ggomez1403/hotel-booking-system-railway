package com.ggomezr.bookingsystem.domain.repository;

import com.ggomezr.bookingsystem.domain.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
    @Override
    Optional<BillDetail> findById(Integer integer);

    List<BillDetail> findByReservationId(Integer reservationId);
}
