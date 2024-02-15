package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.BillDto;
import com.ggomezr.bookingsystem.domain.entity.Bill;
import com.ggomezr.bookingsystem.domain.entity.BillDetail;
import com.ggomezr.bookingsystem.domain.exceptions.BillNotFoundException;
import com.ggomezr.bookingsystem.domain.repository.BillDetailRepository;
import com.ggomezr.bookingsystem.domain.repository.BillRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private BillDetailRepository billDetailRepository;

    @InjectMocks
    private BillService billService;
    @Test
    public void testGetAllBills() {
        Bill bill1 = Bill.builder().id(1).build();
        Bill bill2 = Bill.builder().id(2).build();
        when(billRepository.findAll()).thenReturn(List.of(bill1, bill2));

        List<Bill> result = billService.getAllBills();

        assertEquals(2, result.size());
        assertTrue(result.contains(bill1));
        assertTrue(result.contains(bill2));
    }

    @Test
    public void testGetBillById() throws BillNotFoundException {
        Integer id = 1;
        Bill bill = Bill.builder().id(id).build();

        when(billRepository.findById(id))
                .thenReturn(Optional.of(bill));

        Optional<Bill> result = billService.getBillById(id);

        assertTrue(result.isPresent());
        assertEquals(bill, result.get());
    }

    @Test
    public void testCreateBill() throws Exception {
        BillDetail billDetail = BillDetail.builder()
                .id(1)
                .reservationAmount(BigDecimal.TEN)
                .build();

        when(billDetailRepository.findById(1))
                .thenReturn(Optional.of(billDetail));

        BillDto dto = new BillDto(null, 1, null, LocalDate.now());

        billService.createBill(dto);

        verify(billRepository).save(argThat(bill -> {
            assertEquals(billDetail.getReservationAmount(),
                    bill.getTotalAmount());
            assertEquals(billDetail, bill.getBillDetail());
            assertEquals(dto.issuedDate(), bill.getIssuedDate());
            return true;
        }));
    }

    @Test
    public void testUpdateBill() throws BillNotFoundException {
        Integer id = 1;
        Integer billDetailId = 2;
        BigDecimal totalAmount = new BigDecimal("100.00");
        LocalDate issuedDate = LocalDate.now();
        BillDto billDto = new BillDto(id, billDetailId, totalAmount, issuedDate);

        Bill existingBill = mock(Bill.class);
        BillDetail billDetail = new BillDetail();

        when(billRepository.findById(id)).thenReturn(Optional.of(existingBill));
        when(billDetailRepository.findById(billDetailId)).thenReturn(Optional.of(billDetail));

        billService.updateBill(id, billDto);

        verify(billRepository).findById(id);
        verify(billDetailRepository).findById(billDetailId);
        verify(existingBill).setBillDetail(billDetail);
        verify(existingBill).setIssuedDate(issuedDate);
        verify(billRepository).save(existingBill);
    }

    @Test
    public void testDeleteBill(){
        Integer id = 1;

        billService.deleteBill(id);

        verify(billRepository).deleteById(id);
    }
}
