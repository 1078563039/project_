package com.example.common.utils.list;

import cn.hutool.core.collection.CollectionUtil;

import java.util.List;
import java.util.function.Function;

public class Tree2List {

    /**
     * 将树形结构的列表转换为普通列表。
     *
     * @param itemList      树形结构的列表
     * @param getChildList  获取子列表的函数
     * @param <T>           列表元素的类型
     * @return              普通列表
     */
    public static <T> List<T> tree2List(List<T> itemList, Function<T, List<T>> getChildList){
        List<T> resultList = CollectionUtil.newArrayList();
        if (CollectionUtil.isEmpty(itemList)){
            return resultList;
        }
        itemList.forEach(item -> {
            resultList.add(item);
            resultList.addAll(tree2List(getChildList.apply(item), getChildList));
        });
        return resultList;
    }

}
