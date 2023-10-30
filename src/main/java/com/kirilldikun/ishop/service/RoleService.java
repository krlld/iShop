package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.entity.Role;
import com.kirilldikun.ishop.exception.RoleNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public boolean isValidRole(String role) {
        try {
            Role.valueOf(role.toUpperCase());
            return true;
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }

    public Role mapToRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new RoleNotFoundException();
        }
    }
}
