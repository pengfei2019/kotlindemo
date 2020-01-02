package com.example.kotlindemo2.controller


import com.example.kotlindemo2.model.UserInfo
import com.example.kotlindemo2.model.UserPara
import com.example.kotlindemo2.service.IUserService
import com.sun.org.apache.xml.internal.security.utils.Base64
import org.omg.CORBA.ACTIVITY_REQUIRED
import org.omg.CORBA.TRANSACTION_REQUIRED
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.Key
import java.security.SecureRandom
import java.security.spec.DSAPrivateKeySpec

import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.google.gson.Gson
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.body


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


    @GetMapping("/getUserInfo")
    fun getUser(@RequestParam("name") name: String): String {


//        response.setHeader("Apikey","defg")
        var rtn = cipherTest("中国字","12345678")
        var strDec = decrypt(rtn,"12345678")
        log.info(String(strDec))
        return rtn
    }

    @GetMapping("/getUserList")
    fun getUserList(@RequestBody userPara : UserPara,
                    @RequestParam("userId", required = false) id: String): String {

        var userInfo = UserInfo(userid=null,
                username=null,
                password=null)

        var strBody = userPara

        var userList1 = userService.queryUserList(userInfo)
        var strList = Gson().toJson(userList1)
        var rtn = cipherTest(strList,"12345678")
        return rtn
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

    fun cipherTest(str:String, keyArray: String ): String{
        val transformation = "DES/CBC/PKCS5Padding"
        //1.创建cipher对象:学习查看api文档
        val cipher = Cipher.getInstance(transformation)

        //2.初始化cipher(参数1：加密/解密模式)
        val kf = SecretKeyFactory.getInstance("DES")
        val keySpec = DESKeySpec(keyArray.toByteArray())

        val key:Key = kf.generateSecret(keySpec)
        val iv = IvParameterSpec(keyArray.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE,key, iv)// CBC模式需要额外参数
        //3.加密/解密
        val encrypt = cipher.doFinal(str.toByteArray())
        println("加密后字节数组长度="+encrypt.size)//8
        // base64编码
        return Base64.encode(encrypt)

    }


    fun decrypt(input:String, password:String): ByteArray {
        val transformation = "DES/CBC/PKCS5Padding"
        //1.创建cipher对象:学习查看api文档
        val cipher = Cipher.getInstance(transformation)

        //2.初始化cipher(参数1：加密/解密模式)
        val kf = SecretKeyFactory.getInstance("DES")
        val keySpec = DESKeySpec(password.toByteArray())

        val key:Key = kf.generateSecret(keySpec)

        val iv = IvParameterSpec(password.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE,key, iv)
        //3.加密/解密
        //val encrypt = cipher.doFinal(input.toByteArray())
        //base64解密
        val encrypt = cipher.doFinal(Base64.decode(input))
        return encrypt
    }
}