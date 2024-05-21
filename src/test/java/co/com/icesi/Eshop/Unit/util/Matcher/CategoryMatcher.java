package co.com.icesi.Eshop.Unit.util.Matcher;

import co.com.icesi.Eshop.model.Category;
import lombok.AllArgsConstructor;
import org.mockito.ArgumentMatcher;

import javax.persistence.OneToMany;
import java.util.Objects;

@AllArgsConstructor
public class CategoryMatcher implements ArgumentMatcher<Category> {
    private Category categoryLeft;
    @Override
    public boolean matches(Category categoryRight) {
        return Objects.equals(categoryRight.getName(),categoryLeft.getName())
                && Objects.equals(categoryRight.getDescription(),categoryLeft.getDescription())
                && Objects.equals(categoryRight.getItems(),categoryLeft.getItems());
    }
}
