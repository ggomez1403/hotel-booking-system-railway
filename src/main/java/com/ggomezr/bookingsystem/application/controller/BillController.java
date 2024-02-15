package com.ggomezr.bookingsystem.application.controller;

import com.ggomezr.bookingsystem.application.service.BillService;
import com.ggomezr.bookingsystem.domain.dto.BillDto;
import com.ggomezr.bookingsystem.domain.entity.Bill;
import com.ggomezr.bookingsystem.domain.exceptions.BillDetailNotFoundException;
import com.ggomezr.bookingsystem.domain.exceptions.BillNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bill")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @Operation(summary = "Get all bills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bills found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Bill.class))
                    }),
            @ApiResponse(responseCode = "403", description = "No bills found", content = @Content)
    })
    @GetMapping("/bills")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Bill> getAllBills(){
        return billService.getAllBills();
    }

    @Operation(summary = "Get bill by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Bill.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill not found", content = @Content)
    })
    @GetMapping("/bills/{id}")
    public Optional<Bill> getBillById(@Parameter(description = "Bill id", example = "1")@PathVariable Integer id) throws BillNotFoundException{
        return billService.getBillById(id);
    }

    @Operation(summary = "Get bill by bill detail id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill found successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Bill.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill not found", content = @Content)
    })
    @GetMapping
    public List<Bill> getBillByBillDetail(@Parameter(description = "Bill Detail Id", example = "?billDetailId=1")@RequestParam Integer billDetailId){
        return billService.getBillByBillDetailId(billDetailId);
    }

    @Operation(summary = "Create bill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bill created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Bill.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill could not be created", content = @Content)
    })
    @PostMapping("/bills")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBill(@RequestBody BillDto billDto) throws BillDetailNotFoundException {
        billService.createBill(billDto);
    }

    @Operation(summary = "Update bill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill updated successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Bill.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill could not be updated", content = @Content)
    })
    @PutMapping("/bills/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateBill(@Parameter(description = "Bill id", example = "1")@PathVariable Integer id, @RequestBody BillDto billDto) throws  BillNotFoundException{
        billService.updateBill(id, billDto);
    }

    @Operation(summary = "Delete bill")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill deleted successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Bill.class))
                    }),
            @ApiResponse(responseCode = "403", description = "Bill could not be deleted", content = @Content)
    })
    @DeleteMapping("/bills/{id}")
    public void deleteBill(@Parameter(description = "Bill id", example = "1")@PathVariable Integer id){
        billService.deleteBill(id);
    }
}
