package project.irfananda.sunshine.model;

/**
 * Created by irfananda on 02/04/16.
 */
public class City {

    /**
     * id : 3093133
     * name : Lodz
     * coord : {"lon":19.466669,"lat":51.75}
     * country : PL
     * population : 0
     */

    private int id;
    private String name;
    /**
     * lon : 19.466669
     * lat : 51.75
     */

    private CoordBean coord;
    private String country;
    private int population;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordBean getCoord() {
        return coord;
    }

    public void setCoord(CoordBean coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public static class CoordBean {
        private double lon;
        private double lat;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
