package com.f1.app.service;

import com.f1.app.entity.Driver;
import com.f1.app.entity.Team;
import com.f1.app.repository.DriverRepository;
import com.f1.app.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class F1Service {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DriverRepository driverRepository;


    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team updateTeam(Long id, Team teamDetails) {
        Team team = getTeamById(id);
        team.setName(teamDetails.getName());
        team.setPrincipal(teamDetails.getPrincipal());
        return teamRepository.save(team);
    }

    @Transactional
    public void deleteTeam(Long id) {
        Team team = getTeamById(id);

        if (team.getDrivers() != null) {
            for (Driver driver : team.getDrivers()) {
                driver.setTeam(null);
                driverRepository.save(driver);
            }
        }
        teamRepository.delete(team);
    }


    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
    }

    public Driver createDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(Long id, Driver driverDetails) {
        Driver driver = getDriverById(id);
        driver.setName(driverDetails.getName());
        driver.setRaceNumber(driverDetails.getRaceNumber());
        return driverRepository.save(driver);
    }

    public void deleteDriver(Long id) {
        Driver driver = getDriverById(id);
        driverRepository.delete(driver);
    }


    public Driver assignDriverToTeam(Long driverId, Long teamId) {
        Driver driver = getDriverById(driverId);
        Team team = getTeamById(teamId);

        driver.setTeam(team);
        return driverRepository.save(driver);
    }
}