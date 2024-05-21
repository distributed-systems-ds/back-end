package co.com.icesi.Eshop.Unit.util.Matcher;

import co.com.icesi.Eshop.model.OrderStore;
import lombok.AllArgsConstructor;
import org.mockito.ArgumentMatcher;

import java.util.Objects;

@AllArgsConstructor
public class OrderMatcher implements ArgumentMatcher<OrderStore> {
    public OrderStore orderStoreLeft;
    @Override
    public boolean matches(OrderStore orderStoreRight) {
        return Objects.equals(orderStoreRight.getUserPrincipal() , orderStoreLeft.getUserPrincipal())
                && Objects.equals(orderStoreRight.getStatus(),orderStoreLeft.getStatus())
                && Objects.equals(orderStoreRight.getTotal(),orderStoreLeft.getTotal())
                && Objects.equals(orderStoreRight.getItems(),orderStoreLeft.getItems());
    }
}
