package com.wbw.mapper;

import com.wbw.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface Mapper1 {
    Integer add(@Param("param") User user);
}
