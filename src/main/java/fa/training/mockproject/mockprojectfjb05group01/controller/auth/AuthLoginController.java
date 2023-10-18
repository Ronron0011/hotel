package fa.training.mockproject.mockprojectfjb05group01.controller.auth;

import fa.training.mockproject.mockprojectfjb05group01.configuration.security.CustomeUserDetails;
import fa.training.mockproject.mockprojectfjb05group01.configuration.security.jwt.JwtTokenProvider;
import fa.training.mockproject.mockprojectfjb05group01.dto.request.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class AuthLoginController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("")
    public ModelAndView showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("/auth/login");
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            modelAndView.addObject("login", new LoginRequestDTO());
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/hotel/list-hotel");

        return  modelAndView;

    }
    @PostMapping("")
    public ModelAndView loginUser(@AuthenticationPrincipal HttpServletResponse httpServletResponse,
                                  @ModelAttribute("login") LoginRequestDTO loginDto,
                                  BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.addObject("login", loginDto);
            modelAndView.setViewName("auth/login");
            return modelAndView;
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomeUserDetails customeUserDetails = (CustomeUserDetails) authentication.getPrincipal();
            //sinh ra jwt tra ve cho client
            String jwt = tokenProvider.generateToken(customeUserDetails);


            Cookie cookie = new Cookie("jwtToken", jwt);
            cookie.setMaxAge(60*60*24);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);


            modelAndView.setViewName("redirect:/hotel/list-hotel");

        }catch (Exception e) {
            return new ModelAndView("redirect:/login?error");
        }
        return modelAndView;
    }
    @GetMapping("/logout")
    public ModelAndView logoutUser(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        // Invalidate the user's authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
        }
        // Remove the JWT token cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwtToken")) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

}
