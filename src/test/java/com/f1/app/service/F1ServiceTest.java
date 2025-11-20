package com.f1.app.service;

import com.f1.app.entity.Driver;
import com.f1.app.entity.Team;
import com.f1.app.repository.DriverRepository;
import com.f1.app.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class F1ServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private F1Service f1Service;

    private Team createMockTeam(Long id) {
        return new Team(id, "Test Team " + id, "Boss " + id, null);
    }
    private Driver createMockDriver(Long id, Team team) {
        return new Driver(id, "Test Driver " + id, (int) (long) id, team);
    }

    @Test
    void createTeam_Success() {
        // GIVEN
        Team input = createMockTeam(null);
        Team output = createMockTeam(1L);
        when(teamRepository.save(input)).thenReturn(output);

        //WHEN
        Team result = f1Service.createTeam(input);

        //THEN
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        verify(teamRepository, times(1)).save(input);
    }

    @Test
    void getAllTeams_Success() {
        //GIVEN
        List<Team> teams = List.of(createMockTeam(1L), createMockTeam(2L));
        when(teamRepository.findAll()).thenReturn(teams);

        //WHEN
        List<Team> result = f1Service.getAllTeams();

        //THEN
        assertEquals(2, result.size());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void getTeamById_Success() {
        // GIVEN
        Team expected = createMockTeam(1L);
        when(teamRepository.findById(1L)).thenReturn(Optional.of(expected));

        // WHEN
        Team actual = f1Service.getTeamById(1L);

        // THEN
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void getTeamById_NotFound_ThrowsException() {
        // GIVEN
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        // WHEN, THEN
        assertThrows(RuntimeException.class, () -> f1Service.getTeamById(1L));
    }

    @Test
    void updateTeam_Success() {
        // GIVEN
        Team oldTeam = createMockTeam(1L);
        Team newDetails = createMockTeam(1L);
        newDetails.setName("Updated Name");

        when(teamRepository.findById(1L)).thenReturn(Optional.of(oldTeam));
        when(teamRepository.save(any(Team.class))).thenReturn(newDetails);

        // WHEN
        Team updated = f1Service.updateTeam(1L, newDetails);

        // THEN
        assertEquals("Updated Name", updated.getName());
        verify(teamRepository, times(1)).save(oldTeam);
    }

    @Test
    void updateTeam_NotFound_ThrowsException() {
        // GIVEN
        Team newDetails = createMockTeam(1L);
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        // WHEN, THEN
        assertThrows(RuntimeException.class, () -> f1Service.updateTeam(99L, newDetails));
    }

    @Test
    void updateDriver_Success() {
        // GIVEN
        Driver oldDriver = createMockDriver(1L, null);
        Driver newDetails = createMockDriver(1L, null);
        newDetails.setName("Lando Norris");

        when(driverRepository.findById(1L)).thenReturn(Optional.of(oldDriver));
        when(driverRepository.save(any(Driver.class))).thenReturn(newDetails);

        // WHEN
        Driver updated = f1Service.updateDriver(1L, newDetails);

        // THEN
        assertEquals("Lando Norris", updated.getName());
    }

    @Test
    void updateDriver_NotFound_ThrowsException() {
        // GIVEN
        Driver newDetails = createMockDriver(1L, null);
        when(driverRepository.findById(anyLong())).thenReturn(Optional.empty());

        // WHEN, THEN
        assertThrows(RuntimeException.class, () -> f1Service.updateDriver(99L, newDetails));
    }

    @Test
    void deleteDriver_Success() {
        // GIVEN
        Driver driver = createMockDriver(1L, null);
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        // WHEN
        f1Service.deleteDriver(1L);

        // THEN
        verify(driverRepository, times(1)).delete(driver);
    }

    @Test
    void assignDriverToTeam_Success() {
        // GIVEN
        Driver driver = createMockDriver(1L, null);
        Team team = createMockTeam(1L);

        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        when(teamRepository.findById(1L)).thenReturn(Optional.of(team));
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        // WHEN
        Driver result = f1Service.assignDriverToTeam(1L, 1L);

        // THEN
        assertNotNull(result.getTeam());
        assertEquals(1L, result.getTeam().getId());
    }

    @Test
    void assignDriverToTeam_TeamNotFound_ThrowsException() {
        // GIVEN
        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(createMockDriver(1L, null)));
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        // WHEN, THEN
        assertThrows(RuntimeException.class, () -> f1Service.assignDriverToTeam(1L, 99L));
    }

    @Test
    void deleteTeam_DetachesDriversAndDeletesTeam() {
        // GIVEN
        Long teamId = 1L;
        Team team = createMockTeam(teamId);
        Driver driver1 = createMockDriver(10L, team);
        Driver driver2 = createMockDriver(11L, team);

        team.setDrivers(List.of(driver1, driver2));

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        // WHEN
        f1Service.deleteTeam(teamId);

        // THEN
        verify(driverRepository, times(1)).save(driver1);
        verify(driverRepository, times(1)).save(driver2);

        verify(teamRepository, times(1)).delete(team);

        assertNull(driver1.getTeam());
        assertNull(driver2.getTeam());
    }
}