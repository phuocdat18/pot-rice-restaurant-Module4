package com.cg.controller;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.bill.BillDTO;
import com.cg.model.dto.bill.BillDetailDTO;
import com.cg.service.bill.IBillService;
import com.cg.service.billDetail.IBillDetailService;
import com.cg.service.product.IProductService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class CustomerController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private IProductService productService;

    @Autowired
    private IBillService billService;
    @Autowired
    private IBillDetailService billDetailService;

    @GetMapping
    public String showPageHome(Model model) {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);

        if (!userOptional.isPresent()) {
            throw new DataInputException("User not valid");
        }

        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();

//        username = username.substring(0, username.indexOf("@"));
        model.addAttribute("username", username);
        model.addAttribute("roleCode", roleCode);
        model.addAttribute("active", "shop");
        return "shop/home";
    }

    @GetMapping("/menu")
    public String showPageMenu(Model model) {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);

        if (!userOptional.isPresent()) {
            throw new DataInputException("User not valid");
        }

        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();

//        username = username.substring(0, username.indexOf("@"));
        model.addAttribute("username", username);
        model.addAttribute("roleCode", roleCode);
        model.addAttribute("active", "menu");
        return "shop/menu";
    }

    @GetMapping("/chefs")
    public String showChefs(Model model) {
        String username = appUtils.getPrincipalUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new DataInputException("User not valid");
        }
        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();


        model.addAttribute("username", username);

        model.addAttribute("user", userOptional.get());
        model.addAttribute("roleCode", roleCode);
        model.addAttribute("active", "chefs");
        return "shop/chefs";
    }

    @GetMapping("/product-detail/{id}")
    private String showProductDetail(@PathVariable String id ,Model model) {
        String username = appUtils.getPrincipalUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new DataInputException("User not valid");
        }
        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();


        if (!validateUtils.isNumberValid(id)) {
            throw new DataInputException("Mã sản phẩm không hợp lệ");
        }
        Long productId = Long.parseLong(id);

        Optional<Product> product = productService.findById(productId);

        if (product.isEmpty()) {
            throw new DataInputException("Không tìm thấy sản phẩm");
        }

        model.addAttribute("username", username);

        model.addAttribute("user", userOptional.get());
        model.addAttribute("roleCode", roleCode);
        model.addAttribute("product", product.get().toProductDTO());
        return "shop/menu_detail";
    }


    @GetMapping("/cart")
    private String viewCart(Model model) {
        String username = appUtils.getPrincipalUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new DataInputException("User not valid");
        }
        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();
        model.addAttribute("username", username);

        model.addAttribute("user", userOptional.get());
        model.addAttribute("roleCode", roleCode);

        return "shop/cart_view";
    }


    @GetMapping("/myaccount")
    public String showListProduct(Model model) {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new DataInputException("User not valid");
        }

        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();

        model.addAttribute("username", username);

        model.addAttribute("user", userOptional.get());
        model.addAttribute("roleCode", roleCode);
        return "user_info/user_myaccount";
    }
    @GetMapping("/editInfo")
    public String showUserInfoEdit(Model model) {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new DataInputException("User not valid");
        }

        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();

        model.addAttribute("username", username);
        model.addAttribute("user", userOptional.get());
        model.addAttribute("roleCode", roleCode);
        return "user_info/user_info_edit";
    }
    @GetMapping("/my-order")
    public String showUserOrder(Model model) {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new DataInputException("User not valid");
        }

        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();

        model.addAttribute("username", username);
        model.addAttribute("user", userOptional.get());
        model.addAttribute("roleCode", roleCode);
        return "user_info/user_order";
    }

    @GetMapping("/my-order-detail")
    public String showUserOrderDetail(Model model, @RequestParam long id) {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new DataInputException("User not valid");
        }

        User user = userOptional.get();
        Long userId = user.getId();

        List<BillDTO> userBillDTOs = billService.findBillDTOByIdUser(userId);
        model.addAttribute("bill", userBillDTOs);

        List<BillDTO> billDTOsById = billService.findBillDTOByIdBill(id);
        if (billDTOsById.isEmpty()) {
            throw new DataInputException("Bill not found");
        }
        model.addAttribute("billById", billDTOsById.get(0));

        List<BillDetailDTO> billDetailDTOS = billDetailService.findBillDetailByBillIdStatus(id);
        model.addAttribute("billDetailDTOS", billDetailDTOS);

        String roleCode = user.getRole().getCode();
        model.addAttribute("username", username);
        model.addAttribute("user", user);
        model.addAttribute("roleCode", roleCode);
        model.addAttribute("idBill", id);

        return "user_info/user_order_detail";
    }


}
