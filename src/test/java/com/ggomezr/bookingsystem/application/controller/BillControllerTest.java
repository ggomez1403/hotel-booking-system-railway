package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.BillService;
import com.ggomezr.bookingsystem.domain.dto.BillDto;
import com.ggomezr.bookingsystem.domain.entity.Bill;
import com.ggomezr.bookingsystem.domain.entity.BillDetail;
import com.ggomezr.bookingsystem.domain.exceptions.BillDetailNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.BillNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BillControllerTest {
    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    @Test
    public void testGetAllBills() {
        List<Bill> bills = Arrays.asList(new Bill(), new Bill());
        when(billService.getAllBills()).thenReturn(bills);

        List<Bill> result = billController.getAllBills();

        assertEquals(bills, result);
        verify(billService).getAllBills();
    }

    @Test
    public void testGetBillById() throws BillNotFoundException {
        Integer id = 1;
        Bill bill = Bill.builder().id(id).build();
        when(billService.getBillById(id)).thenReturn(Optional.of(bill));

        Optional<Bill> result = billController.getBillById(id);

        assertEquals(Optional.of(bill), result);
        verify(billService).getBillById(id);
    }

    @Test
    public void testCreateBill() throws BillDetailNotFoundException {
        BillDto billDto = new BillDto(null, 1, BigDecimal.TEN, LocalDate.now());

        billController.createBill(billDto);

        verify(billService).createBill(billDto);
    }

    @Test
    public void testUpdateBill() throws BillNotFoundException {
        Integer id = 1;
        BillDto billDto = new BillDto(null, 1, BigDecimal.TEN, LocalDate.now());

        billController.updateBill(id, billDto);

        verify(billService).updateBill(id, billDto);
    }

    @Test
    public void testDeleteBill() {
        Integer id = 1;

        billController.deleteBill(id);

        verify(billService).deleteBill(id);
    }
}
