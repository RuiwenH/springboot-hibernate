package com.reven.core;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @param <T>
 * @author: zhangke
 * @Createdate: 2014年6月12日 下午3:34:16
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class BaseDaoImpl<T> implements IBaseDao<T> {

    @Resource
    private SessionFactory sessionFactory;

    private Class<T> clazz;

    /**
     * 该类初始化时通过反射机制获取T的真实类型
     * 
     * @author: azzcsimp
     * @Createdate: 2014年6月12日 下午3:58:01
     */
    public BaseDaoImpl() {
        // 获取当前new的对象的泛型的父类类型
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        // 获取第一个类型参数的真实类型

        this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {
        getSession().save(entity);
    }

    @Override
    public void delete(Long id) {
        Object obj = getById(id);
        if (obj != null) {
            getSession().delete(obj);
        }
    }
    
    @Override
    public void delete(Integer id) {
        Object obj = getById(id);
        if (obj != null) {
            getSession().delete(obj);
        }
    }

    @Override
    public void update(T entity) {
        getSession().update(entity);
    }

    @Override
    public T getById(Long id) {
        if (id == null) {
            return null;
        } else {
            return (T) getSession().get(clazz, id);
        }
    }
    
    @Override
    public T getById(Integer id) {
        if (id == null) {
            return null;
        } else {
            return (T) getSession().get(clazz, id);
        }
    }

    @Override
    public List<T> getByIds(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return Collections.EMPTY_LIST;
        }

        return getSession().createQuery(//
                "FROM " + clazz.getSimpleName() + " WHERE id IN(:ids)")//
                .setParameterList("ids", ids)//
                .list();
    }

    @Override
    public List<T> findAll() {
        return getSession().createQuery(//
                "FROM " + clazz.getSimpleName())//
                .list();
    }

    @Override
    public List<T> findListByHql(String queryString, Object... values) {
        // queryString 不能为 null 且必须至少包含一个非空格的字符，否则抛出异常；
        Assert.hasText(queryString);
        // List<T> list = new ArrayList();//Collections.EMPTY_LIST
        Query queryObject = getSession().createQuery(queryString);
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject.list();
    }

    /** 根据语句查询，结果返回一个集合 hql */
    public List<T> findList(String hql) {
        List results = new ArrayList();
        try {
            results = getSession().createQuery(hql).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /** 根据语句查询，结果返回一个集合 sql */
    public List getByJdbcSQL(String sql) {
        List pojolist = new ArrayList();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        Session session = null;
        session = getSession().getSessionFactory().openSession();
//        con = session.connection();
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            while (rs.next()) {
                List temp_entity = new ArrayList();
                for (int i = 1; i <= count; i++) {
                    temp_entity.add(rs.getString(i));
                }
                pojolist.add(temp_entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pojolist;
    }

    /**
     * 获取当前可用的Session对象
     * 
     * @return
     * @author: azzcsimp
     * @Createdate: 2014年6月12日 下午3:49:53
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /** 计算满足条件的行数,这里有参数 */
    public int getTotalCount(Session session, String hql) {
        Long counts = new Long(0);
        hql = hql.replaceAll("(f|F)(r|R)(o|O)(m|M)", "from");
        // 忽略大小写
        hql = hql.replaceAll("((o|O)(r|R)(d|D)(e|E)(r|R))(\\s*)((b|B)(y|Y))", " order by ");
        // 忽略大小写 (\\s*)代表一个或多个空格 因为order by 之间可以有多个空格
        int sql_from = hql.indexOf("from");
        int sql_orderby = hql.indexOf("order by");// 为了改进
        String countStr = "";
        if (sql_orderby > 0) {
            countStr = "select count(*) " + hql.substring(sql_from, sql_orderby);
        } else {
            countStr = "select count(*) " + hql.substring(sql_from);
        }
        Query query = session.createQuery(countStr);
        try {
            List list = query.list();
            if (list == null || list.size() == 0) {
                return 0;
            }
            if (list.size() > 1) {
                counts = Long.parseLong(String.valueOf(list.size()));
            } else {
                counts = (Long) list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return counts.intValue();
    }

    /**
     * 执行一条SQL 语句 retrun Integer
     * 
     */
    public void executeBySQL(final String sql) {
        int result = 0;
        Connection con = null;
        PreparedStatement psmt = null;
        try {
//            con = getSession().connection();
            con.setAutoCommit(false);
            psmt = con.prepareStatement(sql);
            psmt.executeBatch();
            psmt.clearBatch();
            con.commit();
            con.setAutoCommit(true);
            result = 1;
        } catch (Exception e) {
            result = 0;
            e.printStackTrace();
        }
    }
}
