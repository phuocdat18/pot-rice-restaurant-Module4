package com.cg.service.role;

import com.cg.model.ERole;
import com.cg.model.Role;
import com.cg.model.dto.user.RoleDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IRoleService extends IGeneralService<Role, Long> {
    Role findByName(ERole name);
    List<RoleDTO> findAllRoleDTO();
}
