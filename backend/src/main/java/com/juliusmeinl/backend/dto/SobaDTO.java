package com.juliusmeinl.backend.dto;

import com.juliusmeinl.backend.model.VrstaSobe;

import java.time.LocalDate;

public class SobaDTO {
    private Integer roomId;
    private String roomNumber;
    private VrstaSobe roomType;
    private LocalDate datumOd;
    private LocalDate datumDo;

    public SobaDTO(Integer roomId, String roomNumber, VrstaSobe roomType, LocalDate datumOd, LocalDate datumDo) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.datumOd = datumOd;
        this.datumDo = datumDo;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public VrstaSobe getRoomType() {
        return roomType;
    }

    public void setRoomType(VrstaSobe roomType) {
        this.roomType = roomType;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(LocalDate datumOd) {
        this.datumOd = datumOd;
    }

    public LocalDate getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(LocalDate datumDo) {
        this.datumDo = datumDo;
    }
}
