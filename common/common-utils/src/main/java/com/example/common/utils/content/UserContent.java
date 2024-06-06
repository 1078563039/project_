package com.example.common.utils.content;

import com.example.admin.api.feign.user.dto.SysUserDto;

public class UserContent {

    public static final ThreadLocal<SysUserDto> holder = new ThreadLocal<>();

    public static void set(SysUserDto adminUser){
        holder.set(adminUser);
    }

    public static SysUserDto get(){
        return holder.get();
    }

    public static void clear(){
        holder.remove();
    }

}
