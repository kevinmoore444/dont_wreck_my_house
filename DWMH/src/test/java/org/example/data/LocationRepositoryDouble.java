package org.example.data;

import org.example.models.Location;

import static org.example.TestHelper.makeLocation;

public class LocationRepositoryDouble implements LocationRepository {
    @Override
    public Location findLocationByID(int locationId) {

        return (locationId == 5 ? null : makeLocation(1));
    }

    @Override
    public Location findLocationByEmail(String email) {
        return null;
    }
}
