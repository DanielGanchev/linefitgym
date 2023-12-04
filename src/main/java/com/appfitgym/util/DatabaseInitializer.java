package com.appfitgym.util;

import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.CityRepository;
import com.appfitgym.repository.CountryRepository;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.repository.UserRoleRepository;
import com.appfitgym.service.impl.FetchServices.GeoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    private final GeoService geoDbService;
    private DataSource dataSource;

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;




    public DatabaseInitializer(UserRoleRepository userRoleRepository, UserRepository userRepository, GeoService geoDbService, DataSource dataSource, CountryRepository countryRepository, CityRepository cityRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.geoDbService = geoDbService;
        this.dataSource = dataSource;

        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public void run(String... args) {
        if (userRoleRepository.count() == 0) {
            for (UserRoleEnum roleEnum : UserRoleEnum.values()) {
                UserRole userRole = new UserRole();
                userRole.setRole(roleEnum);
                userRoleRepository.save(userRole);

            }
        }

        if (countryRepository.count() == 0 && cityRepository.count() == 0) {
          geoDbService.initializeCountriesAndCities();
        }


        if (userRepository.count() == 0){
            Resource resource = new ClassPathResource("data.sql");
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
            databasePopulator.execute(dataSource);
        }



    }
}