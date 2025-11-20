package com.f1.app.controller;

import com.f1.app.entity.Driver;
import com.f1.app.entity.Team;
import com.f1.app.service.F1Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class F1ControllerTest {

    @Mock
    private F1Service f1Service;

    @InjectMocks
    private F1Controller f1Controller;

    private Team createMockTeam(Long id) {
        return new Team(id, "Test Team " + id, "Boss " + id, null);
    }

    private Driver createMockDriver(Long id) {
        return new Driver(id, "Test Driver " + id, 1, null);
    }

    @Test
    void getAllTeams_Success() {
        // GIVEN
        List<Team> teams = List.of(createMockTeam(1L), createMockTeam(2L));
        when(f1Service.getAllTeams()).thenReturn(teams);

        // WHEN
        List<Team> result = f1Controller.getAllTeams();

        // THEN
        assertEquals(2, result.size());
        verify(f1Service, times(1)).getAllTeams();
    }

    @Test
    void getTeamById_Success() {
        // GIVEN
        Team team = createMockTeam(1L);
        when(f1Service.getTeamById(1L)).thenReturn(team);

        // WHEN
        Team result = f1Controller.getTeamById(1L);

        // THEN
        assertEquals(1L, result.getId());
        verify(f1Service, times(1)).getTeamById(1L);
    }

    @Test
    void createTeam_Success() {
        // GIVEN
        Team inputTeam = createMockTeam(null);
        Team savedTeam = createMockTeam(1L);
        when(f1Service.createTeam(inputTeam)).thenReturn(savedTeam);

        // WHEN
        Team result = f1Controller.createTeam(inputTeam);

        // THEN
        assertEquals(1L, result.getId());
        verify(f1Service, times(1)).createTeam(inputTeam);
    }

    @Test
    void updateTeam_Success() {
        // GIVEN
        Team inputTeam = createMockTeam(1L);
        Team updatedTeam = createMockTeam(1L);
        updatedTeam.setName("Updated");
        when(f1Service.updateTeam(1L, inputTeam)).thenReturn(updatedTeam);

        // WHEN
        Team result = f1Controller.updateTeam(1L, inputTeam);

        // THEN
        assertEquals("Updated", result.getName());
        verify(f1Service, times(1)).updateTeam(1L, inputTeam);
    }

    @Test
    void deleteTeam_Success() {
        // GIVEN
        Long id = 1L;

        // WHEN
        f1Controller.deleteTeam(id);

        // THEN
        verify(f1Service, times(1)).deleteTeam(id);
    }

    @Test
    void getAllDrivers_Success() {
        // GIVEN
        List<Driver> drivers = List.of(createMockDriver(1L));
        when(f1Service.getAllDrivers()).thenReturn(drivers);

        // WHEN
        List<Driver> result = f1Controller.getAllDrivers();

        // THEN
        assertEquals(1, result.size());
        verify(f1Service, times(1)).getAllDrivers();
    }

    @Test
    void getDriverById_Success() {
        // GIVEN
        Driver driver = createMockDriver(1L);
        when(f1Service.getDriverById(1L)).thenReturn(driver);

        // WHEN
        Driver result = f1Controller.getDriverById(1L);

        // THEN
        assertEquals(1L, result.getId());
        verify(f1Service, times(1)).getDriverById(1L);
    }

    @Test
    void createDriver_Success() {
        // GIVEN
        Driver inputDriver = createMockDriver(null);
        Driver savedDriver = createMockDriver(1L);
        when(f1Service.createDriver(inputDriver)).thenReturn(savedDriver);

        // WHEN
        Driver result = f1Controller.createDriver(inputDriver);

        // THEN
        assertEquals(1L, result.getId());
        verify(f1Service, times(1)).createDriver(inputDriver);
    }

    @Test
    void updateDriver_Success() {
        // GIVEN
        Driver inputDriver = createMockDriver(1L);
        Driver updatedDriver = createMockDriver(1L);
        updatedDriver.setName("Updated Driver");
        when(f1Service.updateDriver(1L, inputDriver)).thenReturn(updatedDriver);

        // WHEN
        Driver result = f1Controller.updateDriver(1L, inputDriver);

        // THEN
        assertEquals("Updated Driver", result.getName());
        verify(f1Service, times(1)).updateDriver(1L, inputDriver);
    }

    @Test
    void deleteDriver_Success() {
        // GIVEN
        Long id = 1L;

        // WHEN
        f1Controller.deleteDriver(id);

        // THEN
        verify(f1Service, times(1)).deleteDriver(id);
    }

    @Test
    void assignDriverToTeam_Success() {
        // GIVEN
        Driver assignedDriver = createMockDriver(1L);
        when(f1Service.assignDriverToTeam(1L, 2L)).thenReturn(assignedDriver);

        // WHEN
        Driver result = f1Controller.assignDriverToTeam(1L, 2L);

        // THEN
        assertEquals(1L, result.getId());
        verify(f1Service, times(1)).assignDriverToTeam(1L, 2L);
    }
}