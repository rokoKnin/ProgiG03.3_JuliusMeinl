package com.juliusmeinl.backend.dto;

import java.time.LocalDate;

public class RoomAvailabilityRequest {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Integer currentRoomId;
    
    // getters and setters
    public LocalDate getDateFrom() {
        return dateFrom;
    }
    
    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }
    
    public LocalDate getDateTo() {
        return dateTo;
    }
    
    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
    
    public Integer getCurrentRoomId() {
        return currentRoomId;
    }
    
    public void setCurrentRoomId(Integer currentRoomId) {
        this.currentRoomId = currentRoomId;
    }
}