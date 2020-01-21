package com.example.kotlindemo2.controller


import com.example.kotlindemo2.model.UserInfo

import com.example.kotlindemo2.service.IUserService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory



@RestController
class UserOperate {

//    @Autowired
//    lateinit var request: ServerRequest
//
//    @Autowired
//    lateinit var response: HttpServletResponse

    val log: Logger = LoggerFactory.getLogger(UserOperate::class.java)

    @Autowired
    lateinit var userService: IUserService


//    @GetMapping("/getUserInfo")
//    fun getUser(@RequestParam("name") name: String): String {
//
//
////        response.setHeader("Apikey","defg")
//        var rtn = cipherTest("中国字","12345678")
//        var strDec = decrypt(rtn,"12345678")
//        log.info(String(strDec))
//        return rtn
//    }

    @GetMapping("/getUserList")
    fun getUserList(@RequestBody userInfo : UserInfo,
                    @RequestParam("userId", required = false) id: String): String {

        var strBody = userInfo

//        var userList1 = userService.queryUserList(userInfo)
        var userList1 = userService.getAllUserList(userInfo)
        var strList = Gson().toJson(userList1)
//        var rtn = cipherTest(strList,"12345678")
        return "test"
    }

    @GetMapping("/user/{userId}")
    fun queryUserById(@PathVariable userId: String): List<UserInfo> {
        var userInfo = UserInfo(userid=userId.toLong(),
                username=null,
                password=null)

        var userList = userService.queryUserList(userInfo)
        return userList
    }

    @PutMapping("/getUser/{userId}")
    fun queryGetUser(@PathVariable userId: String): String {
        return "Put"
    }

//    fun cipherTest(str:String, keyArray: String ): String{
//        val transformation = "DES/CBC/PKCS5Padding"
//        //1.创建cipher对象:学习查看api文档
//        val cipher = Cipher.getInstance(transformation)
//
//        //2.初始化cipher(参数1：加密/解密模式)
//        val kf = SecretKeyFactory.getInstance("DES")
//        val keySpec = DESKeySpec(keyArray.toByteArray())
//
//        val key:Key = kf.generateSecret(keySpec)
//        val iv = IvParameterSpec(keyArray.toByteArray())
//        cipher.init(Cipher.ENCRYPT_MODE,key, iv)// CBC模式需要额外参数
//        //3.加密/解密
//        val encrypt = cipher.doFinal(str.toByteArray())
//        println("加密后字节数组长度="+encrypt.size)//8
//        // base64编码
//        return Base64.encode(encrypt)
//
//    }


//    fun decrypt(input:String, password:String): ByteArray {
//        val transformation = "DES/CBC/PKCS5Padding"
//        //1.创建cipher对象:学习查看api文档
//        val cipher = Cipher.getInstance(transformation)
//
//        //2.初始化cipher(参数1：加密/解密模式)
//        val kf = SecretKeyFactory.getInstance("DES")
//        val keySpec = DESKeySpec(password.toByteArray())
//
//        val key:Key = kf.generateSecret(keySpec)
//
//        val iv = IvParameterSpec(password.toByteArray())
//        cipher.init(Cipher.DECRYPT_MODE,key, iv)
//        //3.加密/解密
//        //val encrypt = cipher.doFinal(input.toByteArray())
//        //base64解密
//        val encrypt = cipher.doFinal(Base64.decode(input))
//        return encrypt
//    }
}