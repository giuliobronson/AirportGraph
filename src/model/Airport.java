package model;

public class Airport implements Comparable<Airport>{
    private String iata, name, city, state;
    private double latidud, longitud;

    public Airport(String iata, String name, String city, String state, double latidud, double longitud) {
        this.iata = iata;
        this.name = name;
        this.city = city;
        this.state = state;
        this.latidud = latidud;
        this.longitud = longitud;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLatidud() {
        return latidud;
    }

    public void setLatidud(double latidud) {
        this.latidud = latidud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public int compareTo(Airport tAirport) {
        return this.iata.compareTo(tAirport.iata);
    }
}
