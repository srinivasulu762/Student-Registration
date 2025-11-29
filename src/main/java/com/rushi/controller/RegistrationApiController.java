package com.rushi.controller;

import com.rushi.model.Registration;
import com.rushi.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationApiController {

    private final RegistrationService service;

    public RegistrationApiController(RegistrationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Registration> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Registration create(@RequestBody Registration reg) {
        return service.save(reg);
    }

    @PutMapping("/{id}")
    public Registration update(@PathVariable Long id, @RequestBody Registration reg) {
        reg.setId(id);
        return service.save(reg);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
