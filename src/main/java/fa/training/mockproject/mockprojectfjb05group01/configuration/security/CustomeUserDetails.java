package fa.training.mockproject.mockprojectfjb05group01.configuration.security;

import fa.training.mockproject.mockprojectfjb05group01.dto.HotelDTO;
import fa.training.mockproject.mockprojectfjb05group01.entity.Hotel;
import fa.training.mockproject.mockprojectfjb05group01.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomeUserDetails implements UserDetails {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private boolean activationStatus;
    private Date createdTime;
    private Date updatedTime;
    private List<HotelDTO> managedHotels;
    private Collection<? extends GrantedAuthority> authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    //map tu thong tin user chuyen sang thong tin customerDetails
    public static CustomeUserDetails mapUserToUserDetails(User user,List<HotelDTO> managedHotels) {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleType().name());
        //lay cac quyen tu doi tuong user
        return new CustomeUserDetails(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getActivationStatus(),
                user.getCreatedTime(),
                user.getUpdateTime(),
                managedHotels,
                Collections.singleton(authority)
        );
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.activationStatus;
    }
}
