package com.example.kotlindemo2.model

data class UserInfo(val userid: Long?,
                    var username: String?,
                    var password: String?,
                    var deleted: Int=0)