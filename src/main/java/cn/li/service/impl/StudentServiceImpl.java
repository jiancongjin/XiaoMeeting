package cn.li.service.impl;

import cn.li.conf.XiaoMeetingConfig;
import cn.li.dao.StudentDao;
import cn.li.dto.ResponseData;
import cn.li.entity.Student;
import cn.li.service.StudentService;
import cn.li.util.HttpUtils;
import cn.li.util.MD5;
import cn.li.util.ResponseDataUtil;
import cn.li.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    StudentDao studentDao;

    public ResponseData autoLogin(String token) {
        if (!StringUtils.isEmpty(token)) {
            return ResponseDataUtil.build(-1, "参数为空", null);
        }
        Student student = studentDao.findByToken(token);
        if (student == null) {
            return ResponseDataUtil.build(0, "身份过期，请重新登录", null);
        } else {
            return ResponseDataUtil.build(1, "登录成功", null);
        }

    }

    public ResponseData login(String studentNum, String password, String imei) {
        if (!StringUtils.isEmpty(studentNum, password)) {
            return ResponseDataUtil.build(-1, "参数不完整", null);
        } else {
            Student student = studentDao.findUserByNumAndPwd(studentNum, password);
            if (student == null) {
                return ResponseDataUtil.build(0, "账号或密码错误", null);
            } else {
                //登陆成功 返回客户端token
                String token = MD5.md5Password(studentNum + imei + XiaoMeetingConfig.Key);
                Map<String, Object> map = new HashMap<String, Object>();
                student.setToken(token);
                studentDao.updateToken(student);
                map.put("token", token);
                map.put("leaderState", student.getLeaderState());
                map.put("creditScore", student.getCreditScore());
                map.put("email", student.getEmail());

                return ResponseDataUtil.build(1, "登陆成功", map);
            }

        }

    }

    public ResponseData register(String phone, String password, String studentNum) {
        //生成区别用户的唯一标识码token
        if (!StringUtils.isEmpty(phone, password)) {
            return ResponseDataUtil.build(-1, "参数不完整", null);
        }
        Student student = new Student();
        student.setTelephone(phone);
        student.setPassWord(password);
        student.setStudentNum(studentNum);
        student.setName(StringUtils.getUUID());
        System.out.println(student);
        int flag = studentDao.addUser(student);
        if (flag == 1) {
            return ResponseDataUtil.build(1, "注册成功", null);
        }
        return ResponseDataUtil.build(0, "注册失败", null);
    }


    public ResponseData checkNum(String studentNum, String name, HttpSession httpSession) {
        if (studentNum != null && name != null) {//输入的内容非法
            if (studentDao.findUserByStuNum(studentNum) != null) {
                return ResponseDataUtil.build(-3, "该学号已注册", null);
            }
            String result = HttpUtils.sendGet(studentNum);
            if(result==null){
                return ResponseDataUtil.build(-3,"sendGet未成功返回",result);
            }
            System.out.println(result);

            //Map<String,Object> allData = JSON.parseObject(result,Map.class);
            JSONObject jsonObject = JSON.parseObject(result);
            if(jsonObject==null){
                return ResponseDataUtil.build(-4,"jsonObject解析为成功",null);
            }
            if (jsonObject.getInteger("status") != 200 || result == null ) {//请求失败
                return new ResponseData(-2, "网络请求异常");
            } else {
                //Map<String,Object> temp = JSON.parseObject(allData.get("data").toString(),Map.class);
                //Map<String,String> data = JSON.parseObject(temp.get("rows").toString(),Map.class);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray jsonArray = data.getJSONArray("rows");
                JSONObject student = jsonArray.getJSONObject(0);
                //System.out.println("student"+"---->"+student);
                Map<String, Object> map = student;
                if (!name.equals(map.get("xm"))) {//请求学号对应名字与请求名字不符合
                    return ResponseDataUtil.build(0, "信息错误", result);
                } else {//正确绑定情况
                    httpSession.setAttribute("student", map);
                    return ResponseDataUtil.build(1, "正确", result);
                }
            }
        } else {
            return ResponseDataUtil.build(-1, "信息不完整", null);
        }
    }

    public ResponseData resetPassword(String telephone, String password) {
        if(StringUtils.isEmpty(telephone,password)){
            Student student = studentDao.findUserByPhone(telephone);
            if (student == null) {
                return ResponseDataUtil.build(0, "无效的电话号码", null);
            }
            student.setPassWord(password);
            studentDao.updatePassWord(student);
            return ResponseDataUtil.build(1, "修改成功", null);
        }
        return ResponseDataUtil.build(-1,"参数不完整",null);
    }

    public ResponseData updateUserInfo(Student student) {
        studentDao.updateStudent(student);
        return ResponseDataUtil.build(1,"更新成功",null);
    }
}
