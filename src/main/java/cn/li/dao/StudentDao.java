package cn.li.dao;

import cn.li.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentDao {
    public int addUser(Student student);

    public Student findUserByStuNum(String studentNum,String password);

    public Student findByToken(String token);
}