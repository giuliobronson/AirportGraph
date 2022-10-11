package model;

import java.util.Objects;

public class Airport {
    private final String iata;
    private final double latidude, longitude;

    public Airport(String iata, double latidude, double longitude) {
        this.iata = iata;
        this.latidude = latidude;
        this.longitude = longitude;
    }

    public String getIata() {
        return iata;
    }

    public double distanceTo(Airport airport) {
        double R = 6371;
        double dlon = (airport.longitude - this.longitude) * Math.PI / 180;
        double dlat = (airport.latidude - this.latidude) * Math.PI / 180;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(this.latidude * Math.PI / 180) * Math.cos(airport.latidude * Math.PI / 180)
                * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return c * R;
    }

    /**
     * Métodos necessários de serem implementados para determinar como dois aeroportos serão
     * comparados
     */
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
