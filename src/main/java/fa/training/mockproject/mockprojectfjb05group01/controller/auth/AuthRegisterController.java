package fa.training.mockproject.mockprojectfjb05group01.controller.auth;

import fa.training.mockproject.mockprojectfjb05group01.controller.exception.InternalServerErrorException;
import fa.training.mockproject.mockprojectfjb05group01.dto.request.RegisterRequestDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Role;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import fa.training.mockproject.mockprojectfjb05group01.entity.enums.RoleType;
import fa.training.mockproject.mockprojectfjb05group01.service.RoleService;
import fa.training.mockproject.mockprojectfjb05group01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class AuthRegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("")
    public ModelAndView showRegisterPage() {
        ModelAndView modelAndView = new ModelAndView("auth/register");
        modelAndView.addObject("register", new RegisterRequestDTO());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView registerUser(@Valid @ModelAttribute("register") RegisterRequestDTO userDto, BindingResult result) throws RuntimeException {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.addObject("register", userDto);
            modelAndView.addObject("listRole", roleService.findByRole());
            modelAndView.setViewName("auth/register");
            return modelAndView;
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            modelAndView.addObject("error", "Email is already taken");
        } else if (userService.existsByPhone(userDto.getPhone())) {
            modelAndView.addObject("error", "Phone number is already taken");
        } else {
            User users = new User();
            users.setFirstName(userDto.getFirstName());
            users.setLastName(userDto.getLastName());
            users.setPassword(encoder.encode(userDto.getPassword()));
            users.setEmail(userDto.getEmail());
            users.setPhone(userDto.getPhone());
            users.setActivationStatus(true);
            Role role = roleService.findByRoleType(RoleType.ROLE_HOTEL_ADMIN);
            users.setRole(role);
            userService.addUserToDB(users);
            return new ModelAndView("redirect:/login");
        }
        modelAndView.addObject("registers", userDto);
        modelAndView.addObject("listRole", roleService.findByRole());
        modelAndView.setViewName("auth/register");
        return modelAndView;
    }

}
