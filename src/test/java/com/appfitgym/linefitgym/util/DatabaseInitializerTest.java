package com.appfitgym.linefitgym.util;

import com.appfitgym.model.entities.UserRole;
import com.appfitgym.model.enums.UserRoleEnum;
import com.appfitgym.repository.CityRepository;
import com.appfitgym.repository.CountryRepository;
import com.appfitgym.repository.UserRepository;
import com.appfitgym.repository.UserRoleRepository;
import com.appfitgym.service.impl.fetchServices.GeoService;
import com.appfitgym.util.DatabaseInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import static org.mockito.Mockito.*;

public class DatabaseInitializerTest {

    @InjectMocks
    private DatabaseInitializer databaseInitializer;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GeoService geoDbService;

    @Mock
    private DataSource dataSource;
    @Mock
    private Statement statement;
    @Mock
    private Connection connection;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.createStatement()).thenReturn(statement);
    }

    @Test
    void shouldInitializeDatabase() {
        when(userRoleRepository.count()).thenReturn(0L);
        when(countryRepository.count()).thenReturn(0L);
        when(cityRepository.count()).thenReturn(0L);
        when(userRepository.count()).thenReturn(0L);

        databaseInitializer.run();

        verify(userRoleRepository, times(UserRoleEnum.values().length)).save(any(UserRole.class));
        verify(geoDbService).initializeCountriesAndCities();

        Resource resource = new ClassPathResource("data.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }
}