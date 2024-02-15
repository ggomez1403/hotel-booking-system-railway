package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.BillDetailService;
import com.ggomezr.bookingsystem.domain.dto.BillDetailDto;
import com.ggomezr.bookingsystem.domain.entity.BillDetail;
import com.ggomezr.bookingsystem.domain.exceptions.BillDetailNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.ReservationNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bill-detail")
public class BillDetailController {

    private final BillDetailService billDetailService;

    public BillDetailController(BillDetailService billDetailService) {
        this.billDetailService = billDetailService;
    }

    @Operation(summary = "Get all bill details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill details found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BillDetail.class))
                    }),
            @ApiResponse(responseCode = "403", description = "No bill details found", content = @Content)
    })
    @GetMapping("/bill-details")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<BillDetail> getAllBillDetails(){
        return billDetailService.getAllBillDetails();
    }

    @Operation(summary = "Get bill detail by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill detail found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BillDetail.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill detail not found", content = @Content)
    })
    @GetMapping("/bill-details/{id}")
    public Optional<BillDetail> getBillDetailById(@Parameter(description = "Bill detail id", example = "1")@PathVariable Integer id) throws BillDetailNotFoundException {
        return billDetailService.getBillDetailById(id);
    }

    @Operation(summary = "Get bill detail by reservation id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill details found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BillDetail.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill details not found", content = @Content)
    })
    @GetMapping
    public List<BillDetail> getBillDetailsByUserId(@Parameter(description = "User id", example = "?userId=1")@RequestParam Integer reservationId) {
        return billDetailService.getBillDetailsByReservation(reservationId);
    }

    @Operation(summary = "Create bill detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bill detail created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BillDetail.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Could not create bill detail", content = @Content)
    })
    @PostMapping("/bill-details")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBillDetail(@RequestBody BillDetailDto billDetailDto) throws ReservationNotFoundException {
        billDetailService.createBillDetail(billDetailDto);
    }

    @Operation(summary = "Update bill detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill detail updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BillDetail.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill detail could not be updated", content = @Content)
    })
    @PutMapping("/bill-details/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateBillDetail(@Parameter(description = "Bill detail id", example = "1")@PathVariable Integer id, @RequestBody BillDetailDto billDetailDto) throws BillDetailNotFoundException, ReservationNotFoundException {
        billDetailService.updateBillDetail(id, billDetailDto);
    }

    @Operation(summary = "Delete bill detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill detail deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = BillDetail.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill detail could not be deleted", content = @Content)
    })
    @DeleteMapping("/bill-details/{id}")
    public void deleteBillDetail(@Parameter(description = "Bill detail id", example = "1")@PathVariable Integer id){
        billDetailService.deleteBillDetail(id);
    }
}
