package com.ggomezr.bookingsystem.application.service;

import com.ggomezr.bookingsystem.domain.dto.BillDetailDto;
import com.ggomezr.bookingsystem.domain.entity.BillDetail;
import com.ggomezr.bookingsystem.domain.entity.Reservation;
import com.ggomezr.bookingsystem.domain.repository.BillDetailRepository;
import com.ggomezr.bookingsystem.domain.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BillDetailServiceTest {
    @Mock
    private BillDetailRepository billDetailRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private BillDetailService billDetailService;

    @Test
    public void testGetAllBillDetails() {
        BillDetail bd1 = BillDetail.builder().id(1).build();
        BillDetail bd2 = BillDetail.builder().id(2).build();
        when(billDetailRepository.findAll()).thenReturn(List.of(bd1, bd2));

        List<BillDetail> result = billDetailService.getAllBillDetails();

        assertEquals(2, result.size());
        assertTrue(result.contains(bd1));
        assertTrue(result.contains(bd2));
    }

    @Test
    public void testGetBillDetailById() throws Exception {
        Integer id = 1;
        BillDetail billDetail = BillDetail.builder().id(id).build();
        when(billDetailRepository.findById(id)).thenReturn(Optional.of(billDetail));

        Optional<BillDetail> result = billDetailService.getBillDetailById(id);

        assertTrue(result.isPresent());
        assertEquals(billDetail, result.get());
    }

    @Test
    public void testCreateBillDetail() throws Exception {
        Reservation reservation = Reservation.builder()
                .id(1)
                .amount(BigDecimal.TEN)
                .build();

        when(reservationRepository.findById(1))
                .thenReturn(Optional.of(reservation));

        BillDetailDto billDetailDto = new BillDetailDto(null, 1,
                "Some description", null);

        billDetailService.createBillDetail(billDetailDto);

        verify(billDetailRepository).save(argThat(bd -> {
            assertEquals(reservation, bd.getReservation());
            assertEquals(reservation.getAmount(),
                    bd.getReservationAmount());
            assertEquals(billDetailDto.description(),
                    bd.getDescription());
            return true;
        }));
    }

    @Test
    public void testUpdateBillDetail() throws Exception {
        Integer id = 1;
        BillDetail billDetail = BillDetail.builder().id(id).build();
        Reservation reservation = Reservation.builder().id(1).build();

        when(billDetailRepository.findById(id))
                .thenReturn(Optional.of(billDetail));
        when(reservationRepository.findById(1))
                .thenReturn(Optional.of(reservation));

        BillDetailDto dto = new BillDetailDto(null, 1, "new desc", null);

        billDetailService.updateBillDetail(id, dto);

        verify(billDetailRepository).save(argThat(bd -> {
            assertEquals(id, bd.getId());
            assertEquals(reservation, bd.getReservation());
            assertEquals(dto.description(), bd.getDescription());
            return true;
        }));
    }

    @Test
    public void testDeleteBillDetail() {
        Integer id = 1;
        doNothing().when(billDetailRepository).deleteById(id);

        billDetailService.deleteBillDetail(id);

        verify(billDetailRepository).deleteById(id);
    }
}
