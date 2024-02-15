package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.BillDetailService;
import com.ggomezr.bookingsystem.domain.dto.BillDetailDto;
import com.ggomezr.bookingsystem.domain.entity.BillDetail;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BillDetailControllerTest {
    @Mock
    private BillDetailService billDetailService;

    @InjectMocks
    private BillDetailController billDetailController;

    @Test
    public void testGetAllBillDetails() {
        List<BillDetail> expectedBillDetails = List.of(
                new BillDetail(), new BillDetail());

        when(billDetailService.getAllBillDetails())
                .thenReturn(expectedBillDetails);

        List<BillDetail> actualBillDetails =
                billDetailController.getAllBillDetails();

        assertEquals(expectedBillDetails, actualBillDetails);
        verify(billDetailService).getAllBillDetails();
    }

    @Test
    public void testGetBillDetailById() throws Exception {
        BillDetail expectedBillDetail = new BillDetail();
        when(billDetailService.getBillDetailById(1))
                .thenReturn(Optional.of(expectedBillDetail));

        Optional<BillDetail> actualBillDetail =
                billDetailController.getBillDetailById(1);

        assertTrue(actualBillDetail.isPresent());
        assertEquals(expectedBillDetail, actualBillDetail.get());
    }

    @Test
    public void testCreateBillDetail() throws Exception {
        BillDetailDto billDetailDto = new BillDetailDto(1,
                1, "Cargo por servicio", new BigDecimal(10));

        billDetailController.createBillDetail(billDetailDto);

        verify(billDetailService).createBillDetail(billDetailDto);
    }

    @Test
    public void testUpdateBillDetail() throws Exception {
        Integer billDetailId = 1;
        BillDetailDto billDetailDto = new BillDetailDto(1,
                1, "Cargo por servicio", new BigDecimal(10));

        BillDetail billDetail = new BillDetail();
        when(billDetailService.getBillDetailById(billDetailId))
                .thenReturn(Optional.of(billDetail));

        billDetailController.updateBillDetail(billDetailId, billDetailDto);

        verify(billDetailService).updateBillDetail(billDetailId, billDetailDto);
    }

    @Test
    public void testDeleteBillDetail() {
        billDetailController.deleteBillDetail(1);

        verify(billDetailService).deleteBillDetail(1);
    }
}
