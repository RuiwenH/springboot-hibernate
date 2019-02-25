package com.reven.core;

/**
 * @ClassName: IBaseService
 * @Description: Service 层 基础接口，其他Service 接口 请继承该接口
 * @author reven
 * @date 2018年8月28日
 * @param <T>
 */
public interface IBaseService<T> {
    void save(T model);// 持久化

    void deleteById(Long id);// 通过主鍵刪除

    void deleteById(Integer id);// 通过主鍵刪除

    void update(T model);// 更新

    T findById(Long id);// 通过ID查找

    T findById(Integer id);// 通过ID查找

}
