package com.wbw.mapper;

import com.wbw.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface Mapper1 {
    Integer add(@Param("param") User user);

    List<Map<String, String>> get(@Param("id") String id);
}
