package com.appfitgym.linefitgym.config;

import com.appfitgym.config.UserSpecification;
import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.country.City;
import com.appfitgym.model.entities.country.Country;
import com.appfitgym.repository.UserRepository;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UserSpecificationTest {

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<?> criteriaQuery;

    @Mock
    private Root<UserEntity> root;

    @InjectMocks
    private UserSpecification userSpecification;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFilterByUsername() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        List<UserEntity> users = Arrays.asList(userEntity);

        when(userRepository.findAll(any(Specification.class))).thenReturn(users);

        UserSpecification spec = new UserSpecification("username", "test");
        List<UserEntity> result = userRepository.findAll(spec);

        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getUsername());
    }

  @Test
  void shouldFilterByEmail() {
    UserEntity userEntity = new UserEntity();
    userEntity.setEmail("coltarq@gmail.com");
    List<UserEntity> users = Arrays.asList(userEntity);

    when(userRepository.findAll(any(Specification.class))).thenReturn(users);

    UserSpecification spec = new UserSpecification("email", "coltarq@gmail.com");
    List<UserEntity> result = userRepository.findAll(spec);

    assertEquals(1, result.size());
    assertEquals("coltarq@gmail.com", result.get(0).getEmail());
        }

        @Test
        void shouldFilterByFirstName() {
            UserEntity userEntity = new UserEntity();
            userEntity.setFirstName("Ivan");
            List<UserEntity> users = Arrays.asList(userEntity);

            when(userRepository.findAll(any(Specification.class))).thenReturn(users);

            UserSpecification spec = new UserSpecification("firstName", "Ivan");
            List<UserEntity> result = userRepository.findAll(spec);

            assertEquals(1, result.size());
            assertEquals("Ivan", result.get(0).getFirstName());
        }

        @Test
        void shouldFilterByLastName() {
            UserEntity userEntity = new UserEntity();
            userEntity.setLastName("Ivanov");
            List<UserEntity> users = Arrays.asList(userEntity);

            when(userRepository.findAll(any(Specification.class))).thenReturn(users);

            UserSpecification spec = new UserSpecification("lastName", "Ivanov");
            List<UserEntity> result = userRepository.findAll(spec);

            assertEquals(1, result.size());
            assertEquals("Ivanov", result.get(0).getLastName());
        }

        @Test
        void shouldFilterByCountry() {
            UserEntity userEntity = new UserEntity();
            Country country = new Country();
            country.setId(1L)   ;
            country.setName("Bulgaria");
            userEntity.setCountry(country);

            List<UserEntity> users = Arrays.asList(userEntity);

            when(userRepository.findAll(any(Specification.class))).thenReturn(users);

            UserSpecification spec = new UserSpecification("country", "Bulgaria");
            List<UserEntity> result = userRepository.findAll(spec);

            assertEquals(1, result.size());
            assertEquals("Bulgaria", result.get(0).getCountry().getName());
        }

        @Test
        void shouldFilterByCity() {
            UserEntity userEntity = new UserEntity();
            Country country = new Country();
            country.setId(1L)   ;
            country.setName("Bulgaria");

            City city = new City();
            city.setId(1L);
            city.setName("Sofia");
            city.setCountry(country);
            userEntity.setCountry(country);
            userEntity.setCity(city);

            List<UserEntity> users = Arrays.asList(userEntity);

            when(userRepository.findAll(any(Specification.class))).thenReturn(users);

            UserSpecification spec = new UserSpecification("city", "Bulgaria");
            List<UserEntity> result = userRepository.findAll(spec);

            assertEquals(1, result.size());
            assertEquals("Sofia", result.get(0).getCity().getName());
        }



}