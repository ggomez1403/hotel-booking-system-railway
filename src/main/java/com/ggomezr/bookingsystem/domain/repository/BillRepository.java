package com.ggomezr.bookingsystem.domain.repository;

import com.ggomezr.bookingsystem.domain.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Override
    Optional<Bill> findById(Integer integer);
    List<Bill> findByBillDetailId(Integer billDetailId);
}
