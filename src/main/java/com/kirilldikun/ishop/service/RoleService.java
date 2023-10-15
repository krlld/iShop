package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.entity.Role;
import com.kirilldikun.ishop.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
        roleRepo.save(new Role("Admin"));
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    public Optional<Role> save(Role role) {
        return Optional.ofNullable(roleRepo.save(role));
    }

    public Optional<Role> update(Long id, Role role) {
        return Optional.ofNullable(roleRepo.findById(id).orElseThrow().update(role));
    }

    public boolean existsById(Long id) {
        return roleRepo.existsById(id);
    }

    public void delete(Long id) {
        roleRepo.deleteById(id);
    }
}
