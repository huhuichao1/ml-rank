package hhc.mllib.label.learn.recommend;

import java.io.Serializable;

/**
 * Created by huhuichao on 2017/12/7.
 */
public class User implements Serializable{


    private static final long serialVersionUID = 8257474753227515107L;
    private int userId;
    private int age;
    private String sex;
    private String job;
    private String zipCode;


    public User(int userId, int age, String sex, String job, String zipCode) {
        this.userId = userId;
        this.age = age;
        this.sex = sex;
        this.job = job;
        this.zipCode = zipCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
