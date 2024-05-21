package co.com.icesi.Eshop.Unit.util.Matcher;

import co.com.icesi.Eshop.model.Item;
import lombok.AllArgsConstructor;
import org.mockito.ArgumentMatcher;

import java.util.Objects;

@AllArgsConstructor
public class ItemMatcher implements ArgumentMatcher<Item> {
    public  Item itemLeft;
    @Override
    public boolean matches(Item itemRight) {
        return Objects.equals(itemRight.getName(),itemLeft.getName())
                && Objects.equals(itemRight.getDescription(),itemLeft.getDescription())
                && Objects.equals(itemRight.getPrice(),itemLeft.getPrice())
                && Objects.equals(itemRight.getImageUrl(),itemLeft.getImageUrl())
                && Objects.equals(itemRight.getCategory(),itemLeft.getCategory())
                && Objects.equals(itemRight.getOrderStores(),itemLeft.getOrderStores());
    }
}
