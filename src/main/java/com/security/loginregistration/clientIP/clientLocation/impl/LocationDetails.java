package com.security.loginregistration.clientIP.clientLocation.impl;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.security.loginregistration.clientIP.clientLocation.LocationDataFromIP;
import com.security.loginregistration.file.FileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class LocationDetails implements LocationDataFromIP {
    private final FileLoader fileLoader;

    @Autowired
    public LocationDetails(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    @Override
    public String locationData(String ip) throws IOException, GeoIp2Exception {
        Resource resource = new ClassPathResource("static/mmdb/GeoLite2City.mmdb");
        String dbLocation =resource.getFile().getAbsolutePath();

        File database = new File(dbLocation);
        DatabaseReader databaseReader = new DatabaseReader.Builder(database).build();

        InetAddress inetAddress = InetAddress.getByName(ip);
        CityResponse response = databaseReader.city(inetAddress);

        String city = response.getCity().getName();
        String country = response.getCountry().getName();
        String postal = response.getPostal().getCode();
        String state = response.getLeastSpecificSubdivision().getName();

        System.out.println(
                "City: " + city +
                "\nCountry: " + country +
                "\nPostal: " + postal +
                "\nState: " + state
        );

        return  "City: " + city +
                "\nCountry: " + country +
                "\nPostal: " + postal +
                "\nState: " + state;
    }
}
