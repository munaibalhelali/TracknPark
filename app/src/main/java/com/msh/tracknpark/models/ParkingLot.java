package com.msh.tracknpark.models;

public class ParkingLot {
    private boolean status;
    private long time;
    private String type;

    public ParkingLot() {
    }

    public ParkingLot(boolean status, long time, String type) {
        this.status = status;
        this.time = time;
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String toString(){
        return "Status: "+status+"Time: "+time+"type: "+type;
    }
}
