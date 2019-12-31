package com.example.kotlindemo2.service

import com.example.kotlindemo2.model.UserInfo

interface IUserService {
    fun queryUserList(userInfo: UserInfo): List<UserInfo>

    fun insertUser(userInfo: UserInfo): Int
}