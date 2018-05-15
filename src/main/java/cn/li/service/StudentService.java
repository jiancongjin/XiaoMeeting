package cn.li.service;

import cn.li.dto.ResponseData;
import cn.li.entity.Student;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface StudentService {

     ResponseData checkNum(String studentNum, String name, HttpSession httpSession);

     ResponseData register(String phone,String password,String studentNum);

     ResponseData login(String studentNum, String password, String imei);

     ResponseData autoLogin(String token);

    ResponseData resetPassword(String phone, String password);

    ResponseData updateUserInfo(Student student);



}
