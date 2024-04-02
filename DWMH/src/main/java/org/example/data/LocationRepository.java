package org.example.data;

import org.example.models.Location;

public interface LocationRepository {
    Location findLocationByID(int locationId);

    Location findLocationByEmail(String email);
}
