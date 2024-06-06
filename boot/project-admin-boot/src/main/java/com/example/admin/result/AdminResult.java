package com.example.admin.result;

import com.example.common.base.result.IBaseResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AdminResult implements IBaseResult {

    USER_IS_NOT_EXIST("200100", "用户信息不存在"),
    USER_USERNAME_IS_EXIST("200101", "用户名已存在"),
    USER_MOBILE_IS_EXIST("200102", "手机号已存在"),
    USER_ADMIN_NOT_DELETE( "200103", "admin用户不允许删除"),
    USER_ROLE_IS_EMPTY("200104", "用户角色为空"),

    DEPT_IS_NOT_EXIST("200200", "部门信息不存在"),
    DEPT_IS_EXIST("200201", "部门信息已存在"),
    DEPT_CODE_IS_EXIST("200202", "部门编码已存在"),
    DEPT_PID_IS_NOT_EXIST("200203", "父级部门不存在"),
    DEPT_NAME_IS_EXIST("200204", "部门名称已存在"),
    DEPT_PID_IS_SELF("200205", "父级部门不能为自己"),
    DEPT_PID_IS_CHILDREN("200206", "父级部门不能为自己的子部门"),
    DEPT_PID_IS_SELF_CHILDREN("200207", "父级部门不能为自己的子部门"),
    DEPT_IS_EXIST_USER("200208", "部门下存在用户，不能删除"),

    ROLE_IS_NOT_EXIST("200300", "角色信息不存在"),

    MENU_PID_IS_NOT_DIR("200400", "菜单的父级必须是目录"),
    MENU_PID_IS_NOT_MENU("200401", "按钮的父级必须是目录"),
    MENU_NAME_IS_EXIST("200402", "菜单名称已存在"),
    MENU_PID_IS_NOT_EXIST("200403", "父级菜单不存在"),
    MENU_PID_IS_SELF("200404", "父级菜单不能为自己"),
    MENU_PID_IS_SELF_CHILDREN("200405", "父级菜单不能为自己的子菜单"), ;

    private final String code;
    private final String msg;

}
