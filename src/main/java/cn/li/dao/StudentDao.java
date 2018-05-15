package cn.li.dao;

import cn.li.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentDao {
    int addUser(Student student);

    Student findUserByNumAndPwd(String studentNum, String password);

    Student findByToken(String token);

    Student findUserByStuNum(String studentNum);

    Student findUserByPhone(String telephone);

    int updateToken(Student student);

    int updateStudent(Student student);
}