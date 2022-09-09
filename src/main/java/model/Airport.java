package model;

import java.util.Objects;

public class Airport {
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

    public double distanceTo(Airport airport) {
        double R = 6371;
        double dlon = (airport.longitud - this.longitud) * Math.PI / 180;
        double dlat = (airport.latidud - this.latidud) * Math.PI / 180;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(this.latidud * Math.PI / 180) * Math.cos(airport.latidud * Math.PI / 180)
                * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return c * R;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(!(o instanceof Airport)) {
            return false;
        }
        Airport airport = (Airport) o;
        return this.iata.equals(airport.iata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.iata);
    }
}
