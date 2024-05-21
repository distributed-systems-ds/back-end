package co.com.icesi.Eshop.Unit.util.Matcher;

import co.com.icesi.Eshop.model.UserPrincipal;
import lombok.AllArgsConstructor;
import org.mockito.ArgumentMatcher;

import java.util.Objects;

@AllArgsConstructor
public class UserMatcher implements ArgumentMatcher<UserPrincipal> {
    private UserPrincipal userPrincipalLeft;
    @Override
    public boolean matches(UserPrincipal userPrincipalRight) {
        return Objects.equals(userPrincipalRight.getEmail(),userPrincipalLeft.getEmail())
                && Objects.equals(userPrincipalRight.getFirstName(),userPrincipalLeft.getFirstName())
                && Objects.equals(userPrincipalRight.getLastName(),userPrincipalLeft.getLastName())
                && Objects.equals(userPrincipalRight.getAddress(),userPrincipalLeft.getAddress())
                && Objects.equals(userPrincipalRight.getBirthDate(),userPrincipalLeft.getBirthDate());
    }
}
