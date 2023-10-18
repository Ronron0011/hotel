package fa.training.mockproject.mockprojectfjb05group01.configuration.security.jwt;

import fa.training.mockproject.mockprojectfjb05group01.configuration.security.CustomerDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerDetailsService customerDetailsService;


    private String getJwtFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //lay jwt tu request
            String jwt = getJwtFromCookie(request);
            if (StringUtils.hasText(jwt)) {
                if (jwtTokenProvider.validateToken(jwt)) {
                    //lay email tu chuoi jwt
                    String email = jwtTokenProvider.getUserNameFromJwt(jwt);
                    UserDetails userDetails = customerDetailsService.loadUserByUsername(email);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } else {
                    Cookie cookie = new Cookie("jwtToken", null); // Not necessary, but saves bandwidth.
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setMaxAge(0); // Don't set to -1 or it will become a session cookie!
                    response.addCookie(cookie);

                    response.sendRedirect("/login");
                    return;
                }
            } else {
                if (!request.getRequestURI().contains("login")) {
                    response.sendRedirect("/login");
                    return;
                }
            }
        } catch (Exception ex) {
            log.error("Fail on set user authentication", ex);
        }
        filterChain.doFilter(request, response);
    }


    String[] resources = new String[]{
            "/static/**", "/css/**", "/js/**","favicon.ico","img/**","/plugins/**","/fonts/**"
            ,"/assets/**", "/img/**", "/images/**", "images/**", "/resources/**", "/register/**"
    };
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException
    {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> excludeUrlPatterns = List.of(resources);

        return excludeUrlPatterns
                .stream()
                .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }
}
