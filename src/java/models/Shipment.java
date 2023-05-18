package models;

import java.time.LocalDate;

public class Shipment {
    private int shipmentId;
    private String shipmentMethod;
    private LocalDate shipmentDate;
    private String shipmentAddress;
    
    public Shipment(int shipmentId, String shipmentMethod, LocalDate shipmentDate, String shipmentAddress) {
        this.shipmentId = shipmentId;
        this.shipmentMethod = shipmentMethod;
        this.shipmentDate = shipmentDate;
        this.shipmentAddress = shipmentAddress;
    }
    
    public int getShipmentId() {
        return shipmentId;
    }
    
    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }
    
    public String getShipmentMethod() {
        return shipmentMethod;
    }
    
    public void setShipmentMethod(String shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }
    
    public LocalDate getShipmentDate() {
        return shipmentDate;
    }
    
    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }
    
    public String getShipmentAddress() {
        return shipmentAddress;
    }
    
    public void setShipmentAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }
}
