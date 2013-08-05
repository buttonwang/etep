/*
 * 
 * HibernateGenericDao.java
 * 
 * Created on 2007-11-15
 * 
 * Copyright(C) 2007, by ambow Develope & Research Branch.
 * 
 * Original Author: 彭青
 * Contributor(s):
 * 
 * Changes 
 * *Log*
 */
package com.ambow.core.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.ambow.core.dao.support.Page;
import com.ambow.core.util.BeanUtils;

@SuppressWarnings("unchecked")
public class HibernateGenericDao extends HibernateDaoSupport {
	/**
	 * 根据ID获取对象. 实际调用Hibernate的session.load()方法返回实体或其proxy对象. 如果对象不存在，抛出异常。
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 获取全部对象
	 */
	public <T> List<T> getAll(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 获取全部对象，,带排序字段与升降序参数
	 */
	public <T> List<T> getAll(Class<T> entityClass, String orderBy,
			boolean isAsc) {
		Assert.hasText(orderBy);
		if (isAsc) {
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(
							Order.asc(orderBy)));
		} else {
			return getHibernateTemplate().findByCriteria(
					DetachedCriteria.forClass(entityClass).addOrder(
							Order.desc(orderBy)));
		}
	}

	/**
	 * 保存对象
	 */
	public void save(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}
	
	public HibernateTemplate getTemplate(){
		return getHibernateTemplate();
	}

	/**
	 * 保存对象 ,需要返回值的.
	 * 
	 * @param o
	 *            the o
	 * 
	 * @return the serializable
	 * @author zhumiaowen
	 */
	public Serializable save1(Object o) {
		return getHibernateTemplate().save(o);
	}

	/**
	 * 保存对象 ,批量保存.
	 * 
	 * @param c
	 *            the c
	 * @author zhumiaowen
	 */
	public void saveOrUpdateAll(Collection c) {
		getHibernateTemplate().saveOrUpdateAll(c);
	}

	/**
	 * 批量删除.
	 * 
	 * @param col
	 *            the col
	 * @author zhumiaowen
	 */
	public void removeAll(Collection col) {
		getHibernateTemplate().deleteAll(col);
	}

	/**
	 * 删除对象
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}

	/**
	 * 根据ID删除对象
	 */
	public <T> void removeById(Class<T> entityClass, Serializable id) {
		remove(get(entityClass, id));
	}

	public void flush() {
		getHibernateTemplate().flush();
	}

	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如 dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * 调用方式如下：
	 * 
	 * <pre>
	 *            dao.createQuery(hql)
	 *            dao.createQuery(hql,arg0);
	 *            dao.createQuery(hql,arg0,arg1);
	 *            dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            可变参数.
	 */
	public Query createQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/**
	 * 创建Criteria对象.
	 * 
	 * @param criterions
	 *            可变的Restrictions条件列表,见{@link #createQuery(String,Object...)}
	 */
	public <T> Criteria createCriteria(Class<T> entityClass,
			Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 创建Criteria对象，带排序字段与升降序字段.
	 * 
	 * @see #createCriteria(Class, Criterion[])
	 */
	public <T> Criteria createCriteria(Class<T> entityClass, String orderBy,
			boolean isAsc, Criterion... criterions) {
		Assert.hasText(orderBy);

		Criteria criteria = createCriteria(entityClass, criterions);

		if (isAsc) {
			criteria.addOrder(Order.asc(orderBy));
		} else {
			criteria.addOrder(Order.desc(orderBy));
		}

		return criteria;
	}

	/**
	 * 根据hql查询,直接使用HibernateTemplate的find函数.
	 * 
	 * @param values
	 *            可变参数,见{@link #createQuery(String,Object...)}
	 */
	public List find(String hql, Object... values) {
		Assert.hasText(hql);
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 根据hql查询，只返回符合条件的第一个对象,直接使用HibernateTemplate的find函数.
	 * 
	 * @param values
	 *            可变参数,见{@link #createQuery(String,Object...)}
	 */
	public Object findObjByHql(String hql, Object... values) {
		List list = this.find(hql, values);
		if (list.isEmpty()) {
			return null;
		} else
			return list.get(0);
	}


	/**
	 * 根据条件和聚合函数如Sum,Max,Min,查找唯一记录, 由于没有遇到相关需求,目前只支持一个聚合函数
	 * 
	 * @param entityClass
	 *            实体类型
	 * @param projection
	 *            the projection
	 * @param criterions
	 *            the criterions
	 * 
	 * @return the object
	 * @author zhumiaowen
	 */
	public Object findUnique(Class entityClass, Projection projection, Collection<Criterion> conditions) {
		Criteria criteria = createCriteria(entityClass, conditions
				.toArray(new Criterion[conditions.size()]));
		Assert.notNull(projection);
		return criteria.setProjection(projection)
				.uniqueResult();
	}
	
	/**
	 * 根据条件查找唯一记录
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param criterions
	 *            the criterions
	 * 
	 * @return the object
	 * @author zhumiaowen
	 */
	public Object findUnique(Class entityClass,Collection<Criterion> conditions) {
		Criteria criteria = createCriteria(entityClass, conditions
				.toArray(new Criterion[conditions.size()]));
		Assert.notNull(criteria);
		return criteria.uniqueResult();
	}
	/**
	 * 得到记录总数
	 * 
	 * @param entityClass
	 *            返回的对象类型
	 * @param conditions
	 *            查询条件集合
	 * 
	 * @return the count
	 * @author zhumiaowen
	 */
	private Integer getCount(Class entityClass, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		Assert.notNull(criteria);
		return (Integer) (criteria.setProjection(Projections.rowCount())
				.uniqueResult());
	}

	/**
	 * 根据条件查询
	 * 
	 * @param entityClass
	 *            返回的对象类型
	 * @param conditions
	 *            查询条件集合
	 * @return the list
	 * @author zhumiaowen
	 */
	public <T> List<T> find(Class<T> entityClass,
			Collection<Criterion> conditions) {
		if (conditions == null)
			conditions = new ArrayList<Criterion>();
		Criteria criteria = createCriteria(entityClass,conditions.toArray(new Criterion[conditions.size()]));
		Assert.notNull(criteria);
		return criteria.list();
	}
	/**
	 * 根据条件查询,支持排序
	 * 
	 * @param entityClass
	 *            返回的对象类型
	 * @param conditions
	 *            查询条件集合
	 * @return the list
	 * @author zhumiaowen
	 */
	public <T> List<T> find(Class<T> entityClass,
			Collection<Criterion> conditions,Order order) {
		if (conditions == null)
			conditions = new ArrayList<Criterion>();
		Criteria criteria = createCriteria(entityClass, conditions.toArray(new Criterion[conditions.size()]));
		criteria.addOrder(order);
		Assert.notNull(criteria);
		return criteria.list();
	}

	/**
	 * Gets the count.
	 * 
	 * @param entityClass
	 *            返回的对象类型
	 * @param conditions
	 *            查询条件集合
	 * 
	 * @return the count
	 * @author zhumiaowen
	 */
	public Integer getCount(Class entityClass, Collection<Criterion> conditions) {
		if (conditions == null)
			conditions = new ArrayList<Criterion>();
		return getCount(entityClass, conditions
				.toArray(new Criterion[conditions.size()]));
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return createCriteria(entityClass, Restrictions.eq(propertyName, value))
				.list();
	}

	/**
	 * 根据属性名和属性值查询对象,带排序参数.
	 */
	public <T> List<T> findBy(Class<T> entityClass, String propertyName,
			Object value, String orderBy, boolean isAsc) {
		Assert.hasText(propertyName);
		Assert.hasText(orderBy);
		return createCriteria(entityClass, orderBy, isAsc,
				Restrictions.eq(propertyName, value)).list();
	}

	/**
	 * 根据属性名和属性值查询唯一对象.
	 * 
	 * @return 符合条件的唯一对象 or null if not found.
	 */
	public <T> T findUniqueBy(Class<T> entityClass, String propertyName,
			Object value) {
		Assert.hasText(propertyName);
		return (T) createCriteria(entityClass,
				Restrictions.eq(propertyName, value)).uniqueResult();
	}

	/**
	 * 分页查询函数，使用hql.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize,
			Object... values) {
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

		// Count查询
		String countQueryString = " select count (*) "
				+ removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (Long) countlist.get(0);

		if (totalCount < 1) {
			return new Page();
		}

		// 实际查询返回分页对象
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();

		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 分页查询函数，使用已设好查询条件与排序的<code>Criteria</code>.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Criteria criteria, int pageNo, int pageSize) {
		Assert.notNull(criteria);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		CriteriaImpl impl = (CriteriaImpl) criteria;

		// 先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		Projection projection = impl.getProjection();
		List<CriteriaImpl.OrderEntry> orderEntries;
		try {
			orderEntries = (List) BeanUtils.forceGetProperty(impl,
					"orderEntries");
			BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 执行查询
		long totalCount = (Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult();

		// 将之前的Projection和OrderBy条件重新设回去
		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		try {
			BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}

		// 返回分页对象
		if (totalCount < 1)
			return new Page();

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List list = criteria.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 分页查询函数，根据entityClass和查询条件参数创建默认的<code>Criteria</code>.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize,
			Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, criterions);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 根据条件查询页面列表
	 * 
	 * @param 返回的对象类型
	 * @param 查询条件集合,
	 *            实质为Collection<Expression>
	 * @param pageNo
	 *            页号
	 * @param pageSize
	 *            页面记录数量
	 * 
	 * @return the page
	 * @author zhumiaowen
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize,
			Collection<Criterion> conditions) {
		if (conditions == null)
			conditions = new ArrayList<Criterion>();
		return pagedQuery(entityClass, pageNo, pageSize, conditions
				.toArray(new Criterion[conditions.size()]));
	}
	
	/**
	 * 根据条件查询页面列表, 支持排序
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param pageNo
	 *            the page no
	 * @param pageSize
	 *            the page size
	 * @param conditions
	 *            the conditions
	 * @param order
	 *            the order
	 * 
	 * @return the page
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize,
			Collection<Criterion> conditions,Order order) {
		if (conditions == null)
			conditions = new ArrayList<Criterion>();
		Criteria criteria=createCriteria(entityClass, conditions
				.toArray(new Criterion[conditions.size()]));
		criteria.addOrder(order);
		return pagedQuery(criteria, pageNo, pageSize);
	}
	/**
	 * 分页查询函数，根据entityClass和查询条件参数,排序参数创建默认的<code>Criteria</code>.
	 * 
	 * @param pageNo
	 *            页号,从1开始.
	 * @return 含总记录数和当前页数据的Page对象.
	 */
	public Page pagedQuery(Class entityClass, int pageNo, int pageSize,
			String orderBy, boolean isAsc, Criterion... criterions) {
		Criteria criteria = createCriteria(entityClass, orderBy, isAsc,
				criterions);
		return pagedQuery(criteria, pageNo, pageSize);
	}

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * 
	 * @param names
	 *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 */
	public <T> boolean isUnique(Class<T> entityClass, Object entity,
			String names) {
		Assert.hasText(names);
		Criteria criteria = createCriteria(entityClass).setProjection(
				Projections.rowCount());
		String[] nameList = names.split(",");
		try {
			// 循环加入唯一列
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(
						entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getIdName(entityClass);

			// 取得entity的主键值
			Serializable id = getId(entityClass, entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		return (Integer) criteria.uniqueResult() == 0;
	}

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	public Serializable getId(Class entityClass, Object entity)
			throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		Assert.notNull(entity);
		Assert.notNull(entityClass);
		return (Serializable) PropertyUtils.getProperty(entity,
				getIdName(entityClass));
	}

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	public String getIdName(Class clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = getSessionFactory().getClassMetadata(clazz);
		Assert.notNull(meta, "Class " + clazz
				+ " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName()
				+ " has no identifier property define.");
		return idName;
	}

	/**
	 * 使用原生Sql 查询
	 * 
	 * @param sql
	 *            原生sql 语句
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page pagedExecute(String sql, int pageNo, int pageSize) {
		SQLQuery query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		ScrollableResults scrollableResults = query
				.scroll(ScrollMode.SCROLL_SENSITIVE);
		scrollableResults.last();
		int totalCount = scrollableResults.getRowNumber();

		return getPageResult(query, totalCount + 1, pageNo, pageSize);
	}
	/**
	 * 使用原生Sql 查询 page,带查询条件
	 * 
	 * @param sql
	 *            原生sql 语句
	 * @param pageNo
	 * @param pageSize
	 * @return page
	 * @author zhumiaowen
	 */
	public Page pagedExecute(String sql, int pageNo, int pageSize,Object... params) {
		SQLQuery query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		ScrollableResults scrollableResults = query
				.scroll(ScrollMode.SCROLL_SENSITIVE);
		scrollableResults.last();
		int totalCount = scrollableResults.getRowNumber();

		return getPageResult(query, totalCount + 1, pageNo, pageSize);
	}

	/**
	 * 非分页的原生Sql 查询结果
	 * 
	 * @param sql
	 * @return
	 * 
	 * @author ？
	 */
	public List findExecute(String sql, Object... params) {
		SQLQuery query = this.getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createSQLQuery(sql);

		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String, int, int, Object[])
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String, int, int, Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 通过Query 获得分页查询结果
	 * 
	 * @param query
	 * @param totalCount
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	private Page getPageResult(Query query, int totalCount, int pageNo,
			int pageSize) {
		if (totalCount < 1) {
			return new Page();
		}

		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();

		return new Page(startIndex, totalCount, pageSize, list);
	}
	/**
	 * 用hql执行update和delete操作,用于小批量操作
	 * 
	 * @param hql
	 * @param values
	 */
	public void excuteHql(String hql,Object...values)
	{
		this.getHibernateTemplate().bulkUpdate(hql, values);
	}
}
