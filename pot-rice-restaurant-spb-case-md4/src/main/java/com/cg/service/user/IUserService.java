package com.cg.service.user;

import com.cg.model.User;
import com.cg.model.dto.user.UserDTO;
import com.cg.model.dto.user.UserUpdateReqDTO;
import com.cg.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends IGeneralService<User, Long>, UserDetailsService {
    User getByUsername(String username);
    List<UserDTO> findAllUserDTO();

    Optional<User> findByUsername(String username);

    Optional<UserDTO> findUserDTOByUsername(String username);

    Boolean existsByUsername(String email);

    User update(User user, UserUpdateReqDTO userUpdateReqDTO);

}
