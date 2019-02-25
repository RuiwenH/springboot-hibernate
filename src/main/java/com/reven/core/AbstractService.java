package com.reven.core;

import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @ClassName:  AbstractService   
 * @author reven
 * @date   2018年8月28日
 * @param <T>
 */

public abstract class AbstractService<T> implements IBaseService<T> {

	@Autowired
	protected IBaseDao<T> baseDao;

	private Class<T> modelClass; 

	@SuppressWarnings("unchecked")
	public AbstractService() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		modelClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(T model) {
        baseDao.save(model);        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        baseDao.delete(id);
        
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        baseDao.delete(id);
        
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(T model) {
        baseDao.update(model);
        
    }

    @Override
    @Transactional(readOnly=true)
    public T findById(Long id) {
        return baseDao.getById(id);
    }
    
    @Override
    @Transactional(readOnly=true)
    public T findById(Integer id) {
        return baseDao.getById(id);
    }
 
}
