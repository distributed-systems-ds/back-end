package co.com.icesi.Eshop.Unit.util.Matcher;

import co.com.icesi.Eshop.model.Role;
import lombok.AllArgsConstructor;
import org.mockito.ArgumentMatcher;

import java.util.Objects;

@AllArgsConstructor
public class RoleMatcher implements ArgumentMatcher<Role> {
    private Role roleLeft;
    @Override
    public boolean matches(Role roleRight) {
        return Objects.equals(roleRight.getRoleName(), roleLeft.getRoleName()) && Objects.equals(roleRight.getDescription(),roleLeft.getDescription());
    }
}
