package com.example.kotlindemo2.mapper

import com.example.kotlindemo2.model.UserInfo
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.SelectProvider

@Mapper
interface UserMapper {

    @Select("""<script>
        select USER_ID, USER_NAME, PASSWORD, DELETED from user where 1=1
        </script>""")
    fun queryUserList(userInfo: UserInfo): List<UserInfo>

    @Insert("""
        insert into user (USER_NAME, PASSWORD, DELETED)
            values (#{username}, #{password}, #{deleted})
        """)
    fun insertUser(userInfo: UserInfo): Int

//    @SelectProvider

}