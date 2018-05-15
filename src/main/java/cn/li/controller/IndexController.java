package cn.li.controller;

import cn.li.dto.ResponseData;
import cn.li.entity.Student;
import cn.li.service.StudentService;
import cn.li.util.ResponseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/xiaomeeting")
public class IndexController {

    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/register1",method = RequestMethod.POST)
    @ResponseBody
    public ResponseData register1(@RequestBody Map<String,String > map, HttpSession httpSession){
        return studentService.checkNum(map.get("studentNum"),map.get("name"),httpSession);
    }
    @ResponseBody
    @RequestMapping(value = "/register2",method = RequestMethod.GET)
    public ResponseData  register2(String phone,String password,HttpSession httpSession){
        System.out.println("phone: "+phone+" password: "+password);
        Map<String,Object> map = (Map<String, Object>) httpSession.getAttribute("student");
        if (map == null) {
            return ResponseDataUtil.build(-1, "非法访问", null);
        }
        String studentNum = (String) map.get("xh");
        System.out.println("学号： "+studentNum);
        return studentService.register(phone,password,studentNum);

    }
    @ResponseBody
    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public ResponseData login(String studentNum, String password, String imei) {
        System.out.println("studentNum"+studentNum+"--"+"password"+password);
        return studentService.login(studentNum, password, imei);
    }
    @ResponseBody
    @RequestMapping("/autoLogin")
    public ResponseData autoLogin(String token) {
        System.out.println("token:  "+token);
        return studentService.autoLogin(token);
    }

    @ResponseBody
    @RequestMapping("/resetPassword")
    public ResponseData resetPassword(@RequestParam(value = "telephone") String telephone, String password) {
        System.out.println("telephone" + telephone + "--" + "password" + password);
        return studentService.resetPassword(telephone, password);
    }

}
