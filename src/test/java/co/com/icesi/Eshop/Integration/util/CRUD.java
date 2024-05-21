package co.com.icesi.Eshop.Integration.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CRUD {
    C("create"),
    U("update"),
    R("all"),
    D("delete");
    private final String action;
}
