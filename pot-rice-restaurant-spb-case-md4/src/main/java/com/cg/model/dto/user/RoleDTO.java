package com.cg.model.dto.user;

import com.cg.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
//@AllArgsConstructor
public class RoleDTO {

    @NotNull(message = "The role is required")
    private Long id;

    private String code;

    public RoleDTO (Long id, String code) {
        this.id = id;
        this.code = code;
    }
    public Role toRole() {
        return new Role()
                .setId(id)
                .setCode(code);
    }

}
