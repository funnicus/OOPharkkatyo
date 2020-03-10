package com.company;

//Luokka sisältää yksittäisen varauskohteen tiedot
// ja metodit joilla tietoja voidaan lukea
public class ReservationTarget implements ReservationTargetInterface{
    private String name;
    private String address;
    private String type;

    public ReservationTarget(String name, String address, String type) {
        this.name = name;
        this.address = address;
        this.type = type;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
