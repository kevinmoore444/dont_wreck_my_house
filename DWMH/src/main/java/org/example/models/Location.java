package org.example.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Location {
    //Attributes
    private int locationId;
    private User host;

    private String address;
    private String city;
    private String state;
    private String postalCode;
    private BigDecimal standardRate;
    private BigDecimal weekendRate;

    //Constructors
    public Location(){};
    //This built For ease of use w/testing
    public Location(int locationId, User host) {
        this.locationId = locationId;
        this.host = host;
    }
    public Location(int locationId, User host, String address, String city, String state, String postalCode, BigDecimal standardRate, BigDecimal weekendRate) {
        this.locationId = locationId;
        this.host = host;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.standardRate = standardRate;
        this.weekendRate = weekendRate;
    }

    //Getters and Setters
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    public BigDecimal getWeekendRate() {
        return weekendRate;
    }

    public void setWeekendRate(BigDecimal weekendRate) {
        this.weekendRate = weekendRate;
    }
    //Override the equals method to test for equality instead of comparing locations in memory.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (locationId != location.locationId) return false;
        if (!Objects.equals(host, location.host)) return false;
        if (!Objects.equals(address, location.address)) return false;
        if (!Objects.equals(city, location.city)) return false;
        if (!Objects.equals(state, location.state)) return false;
        if (!Objects.equals(postalCode, location.postalCode)) return false;
        if (!Objects.equals(standardRate, location.standardRate)) return false;
        if (!Objects.equals(weekendRate, location.weekendRate)) return false;
        return true;
    }
    //Override the hashcode method. This method ensures that objects with the same values
    // for all the specified fields will have the same hash code, which is crucial for
    // correct functioning when these objects are used in hash-based collections such as HashMap, HashSet, etc.
    @Override
    public int hashCode() {
        int result = locationId;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (standardRate != null ? standardRate.hashCode() : 0);
        result = 31 * result + (weekendRate != null ? weekendRate.hashCode() : 0);
        return result;
    }

}
