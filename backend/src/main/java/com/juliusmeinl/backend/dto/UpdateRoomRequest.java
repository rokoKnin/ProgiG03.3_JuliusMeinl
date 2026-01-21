package com.juliusmeinl.backend.dto;

public class UpdateRoomRequest {
    private int roomIndex;
    private Integer newRoomId;
    private String newRoomNumber;
    private String newRoomType;
    
    // getters and setters
    public int getRoomIndex() {
        return roomIndex;
    }
    
    public void setRoomIndex(int roomIndex) {
        this.roomIndex = roomIndex;
    }
    
    public Integer getNewRoomId() {
        return newRoomId;
    }
    
    public void setNewRoomId(Integer newRoomId) {
        this.newRoomId = newRoomId;
    }
    
    public String getNewRoomNumber() {
        return newRoomNumber;
    }
    
    public void setNewRoomNumber(String newRoomNumber) {
        this.newRoomNumber = newRoomNumber;
    }
    
    public String getNewRoomType() {
        return newRoomType;
    }
    
    public void setNewRoomType(String newRoomType) {
        this.newRoomType = newRoomType;
    }
}