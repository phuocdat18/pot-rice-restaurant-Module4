package com.cg.model.dto.user;

import com.cg.model.LocationRegion;
import com.cg.model.User;
import com.cg.model.dto.product.ProductUpdateReqDTO;
import com.cg.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.Valid;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateReqDTO implements Validator {
    ValidateUtils validateUtils = new ValidateUtils();
    private String fullName;
    private String email;
    private String phone;

    public User toUser(User oldUser) {
        return new User()
                .setId(oldUser.getId())
                .setUsername(oldUser.getUsername())
                .setPassword(oldUser.getPassword())
                .setRole(oldUser.getRole())
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                ;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateReqDTO userUpdateReqDTO = (UserUpdateReqDTO) target;

        String fullName = userUpdateReqDTO.fullName;
        String email = userUpdateReqDTO.email;
        String phone = userUpdateReqDTO.phone;


        if (fullName == null) {
            errors.rejectValue("fullName", "fullName.null", "Tên là bắt buộc ");
        } else {
            if (fullName.trim().length() == 0) {
                errors.rejectValue("fullName", "fullName.empty", "Tên không được để trống");
            } else {
                if (!validateUtils.isLetterWithoutNumberValid(fullName)) {
                    errors.rejectValue("fullName", "fullName.match", "Tên không được chứa chữ số và ký tự đặc biệt");
                }
            }
        }

        if (email == null) {
            errors.rejectValue("email", "email.null", "Email là bắt buộc");
        } else {
            if (email.trim().length() == 0) {
                errors.rejectValue("email", "email.empty", "Email không được để trống");
            } else {
                if (!validateUtils.isEmailValid(email)) {
                    errors.rejectValue("email", "email.match", "Email không đúng cú pháp");
                }
            }
        }

        if (phone == null) {
            errors.rejectValue("phone", "phone.null", "Số điện thoại là bắt buộc");
        } else {
            if (phone.trim().length() == 0) {
                errors.rejectValue("phone", "phone.empty", "Số điện thoại không được để trống");
            } else {
                if (!validateUtils.isPhoneValid(phone)) {
                    errors.rejectValue("phone", "phone.match", "Số điện thoại có 10 chữ số và bắt đầu bằng 0");
                }
            }
        }
    }
}
