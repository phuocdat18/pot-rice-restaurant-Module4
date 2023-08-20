package com.cg.api;


import com.cg.exception.DataInputException;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.model.dto.user.RoleDTO;
import com.cg.model.dto.user.UserDTO;
import com.cg.model.dto.user.UserUpdateReqDTO;
import com.cg.model.dto.user.UserUpdateResDTO;
import com.cg.service.role.IRoleService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserAPI {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;



    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateUtils validateUtils;


    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        List<UserDTO> userDTOS = userService.findAllUserDTO();

        if (userDTOS.isEmpty()) {
            return new ResponseEntity<>("No customer found.", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }
    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        List<RoleDTO> roleDTOS = roleService.findAllRoleDTO();

        if (roleDTOS.isEmpty()) {
            return new ResponseEntity<>("No roles found.", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(roleDTOS, HttpStatus.OK);
    }
    @PatchMapping("/update/{id}")

    public ResponseEntity<?> updateUser(@PathVariable String id, @ModelAttribute UserUpdateReqDTO userUpdateReqDTO, BindingResult bindingResult) {

        new UserUpdateReqDTO().validate(userUpdateReqDTO, bindingResult);

        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }

        Long userId = Long.parseLong(id);

        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            User user = userService.update(userOptional.get(), userUpdateReqDTO);
            UserUpdateResDTO userUpdateResDTO = user.toUserUpdateResDTO();

            return new ResponseEntity<>(userUpdateResDTO, HttpStatus.OK);
        } else {
            throw new DataInputException("Thông tin người dùng không hợp lệ");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable String id) {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("ID không hợp lệ");
        }
        Long userId = Long.parseLong(id);

        try {
            Optional<User> user = userService.findById(userId);

            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(user.get().toUserDTO(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws IOException {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("ID user không hợp lệ");
        }
        Long userId = Long.parseLong(id);

        Optional<User> user = userService.findById(userId);

        if (user.isPresent()) {
            userService.delete(user.get());

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            throw new DataInputException("Invalid product information");
        }
    }

    @PatchMapping("/disable/{id}")
    public ResponseEntity<?> markProductAsDeleted(@PathVariable String id) {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("ID không hợp lệ");
        }
        Long userId = Long.parseLong(id);

        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDeleted(true); // Thay đổi thuộc tính 'deleted' thành true
            userService.save(user);
            return new ResponseEntity<>("Product has been marked as deleted.", HttpStatus.OK);
        } else {
            throw new DataInputException("Invalid product information");
        }
    }
    @PatchMapping("/restore/{id}")
    public ResponseEntity<?> restoreUser(@PathVariable String id) {
        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("ID không hợp lệ");
        }
        Long userId = Long.parseLong(id);

        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setDeleted(false);
            userService.save(user);
            return new ResponseEntity<>("Product has been marked as deleted.", HttpStatus.OK);
        } else {
            throw new DataInputException("Invalid product information");
        }
    }



}
