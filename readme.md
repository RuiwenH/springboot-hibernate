# springboot整合hibernate
* 版本：springboot1.5.7 hibernate5
* 功能：使用baseDao（SessionFactory）
* 功能：使用@OneToMany

# 演示（测试地址）
* 通过baseDao查询用户信息：http://localhost:8080/user/detail?id=1
* 程序会报错could not initialize proxy - no Session，见user对象关联获取userrole对象，需要开启OpenSessionInViewFilter
