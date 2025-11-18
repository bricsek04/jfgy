package com.f1.app.controller;

import com.f1.app.entity.Driver;
import com.f1.app.entity.Team;
import com.f1.app.service.F1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class F1Controller {

    @Autowired
    private F1Service f1Service;


    @GetMapping("/teams")
    public List<Team> getAllTeams() {
        return f1Service.getAllTeams();
    }

    @GetMapping("/teams/{id}")
    public Team getTeamById(@PathVariable Long id) {
        return f1Service.getTeamById(id);
    }

    @PostMapping("/teams")
    public Team createTeam(@RequestBody Team team) {
        return f1Service.createTeam(team);
    }

    @PutMapping("/teams/{id}")
    public Team updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return f1Service.updateTeam(id, team);
    }

    @DeleteMapping("/teams/{id}")
    public void deleteTeam(@PathVariable Long id) {
        f1Service.deleteTeam(id);
    }


    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return f1Service.getAllDrivers();
    }

    @GetMapping("/drivers/{id}")
    public Driver getDriverById(@PathVariable Long id) {
        return f1Service.getDriverById(id);
    }

    @PostMapping("/drivers")
    public Driver createDriver(@RequestBody Driver driver) {
        return f1Service.createDriver(driver);
    }

    @PutMapping("/drivers/{id}")
    public Driver updateDriver(@PathVariable Long id, @RequestBody Driver driver) {
        return f1Service.updateDriver(id, driver);
    }

    @DeleteMapping("/drivers/{id}")
    public void deleteDriver(@PathVariable Long id) {
        f1Service.deleteDriver(id);
    }


    @PostMapping("/drivers/{driverId}/assign/{teamId}")
    public Driver assignDriverToTeam(@PathVariable Long driverId, @PathVariable Long teamId) {
        return f1Service.assignDriverToTeam(driverId, teamId);
    }
}