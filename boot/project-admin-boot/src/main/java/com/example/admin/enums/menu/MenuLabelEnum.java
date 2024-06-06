package com.example.admin.enums.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuLabelEnum {

    CATALOG(0, "目录", "目录"),
    MENU(1, "菜单", "菜单"),
    BUTTON(2, "按钮", "按钮")

    ;

    private final Integer key;

    private final String value;

    private final String desc;



}
