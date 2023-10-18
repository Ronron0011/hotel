package fa.training.mockproject.mockprojectfjb05group01.configuration.security;

import fa.training.mockproject.mockprojectfjb05group01.configuration.security.jwt.JwtEntryPoint;
import fa.training.mockproject.mockprojectfjb05group01.configuration.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private  CustomerDetailsService customerDetailsService;
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Value("${security.ignored-resources}")
    private String ignoredResources;
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //lay ra authenticationManager bean
        return  super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //customerDetailsService sẽ cung cấp thông tin về người dùng để Spring Security có thể xác thực
        //cung cap customUserDetails service cho spring security
        auth.userDetailsService(customerDetailsService)
                //Mật khẩu của người dùng sẽ được mã hóa và so sánh với mật khẩu đã được mã hóa trong cơ sở dữ liệu
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void  configure(HttpSecurity http) throws Exception {

        http.csrf()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("GET", "favicon.ico").permitAll()
                .antMatchers("/**/favicon.ico","/static/**", "/img/**").anonymous()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/hotel/**").hasAnyRole("HOTEL_ADMIN","MASTER","HOTEL_EMPLOYEE")
                .antMatchers("/room/**","/service/**","/user/**").hasAnyRole("HOTEL_ADMIN")
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint) //truong hop co loi xay ra
                .accessDeniedHandler(accessDeniedHandler)
                //quản lý phiên làm việc cho ứng dụng và đặt chế độ Stateless,
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        String[] resources = ignoredResources.split(",");
        return (web -> web
                .ignoring()
                .antMatchers(resources));
    }

}
