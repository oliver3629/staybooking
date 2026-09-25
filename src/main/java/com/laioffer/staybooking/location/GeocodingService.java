package com.laioffer.staybooking.location;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.laioffer.staybooking.model.GeoPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeocodingService {

    private final GeoApiContext context;

    public GeocodingService(GeoApiContext context) {
        this.context = context;
    }

    public GeoPoint getGeoPoint(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
            if (results.length == 0) {
                throw new InvalidAddressException();
            }
            GeocodingResult result = results[0];
            if (result.partialMatch) {
                throw new InvalidAddressException();
            }
            return new GeoPoint(result.geometry.location.lat, result.geometry.location.lng);
        } catch (IOException | ApiException | InterruptedException e) {
            throw new GeocodingException();
        }
    }
}
