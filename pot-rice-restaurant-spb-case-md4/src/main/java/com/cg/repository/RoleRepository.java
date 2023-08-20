package com.cg.repository;

import com.cg.model.ERole;
import com.cg.model.Role;
import com.cg.model.dto.user.RoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);

    @Query("SELECT NEW com.cg.model.dto.user.RoleDTO (" +
            "r.id, " +
            "r.code" +
            ") " +
            "FROM Role r"
    )
    List<RoleDTO> findAllRoleDTO();
}
