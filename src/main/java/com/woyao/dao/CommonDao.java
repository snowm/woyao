package com.woyao.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.snowm.hibernate.ext.dao.ICommonDao;
import com.snowm.hibernate.ext.domain.DefaultModelImpl;
import com.snowm.hibernate.ext.utils.AliasInfo;

@Repository("commonDao")
public class CommonDao {

	@Resource(name = "commonHibernateDao")
	private ICommonDao commonDao;

	public void flush() {
		commonDao.flush();
	}

	public void clear() {
		commonDao.clear();
	}

	public <M> long count(Class<M> entityClass, Criterion... criterions) {
		return commonDao.count(entityClass, criterions);
	}

	public <M> long count(Class<M> entityClass, List<Criterion> criterions) {
		return commonDao.count(entityClass, criterions);
	}

	public <M> long count(Class<M> entityClass, List<AliasInfo> alias, Criterion... criterions) {
		return commonDao.count(entityClass, alias, criterions);
	}

	public <M> long count(Class<M> entityClass, List<AliasInfo> alias, List<Criterion> criterions) {
		return commonDao.count(entityClass, alias, criterions);
	}
	
	public long count(String hql) {
		return commonDao.count(hql);
	}

	public long count(String hql, Map<String, Object> paramMap) {
		return commonDao.count(hql, paramMap);
	}

	public <M> long countAll(Class<M> entityClass) {
		return commonDao.countAll(entityClass);
	}

	public <M, PK extends Serializable> void delete(Class<M> entityClass, Collection<PK> ids) {
		commonDao.delete(entityClass, ids);
	}

	public <M, PK extends Serializable> void delete(Class<M> entityClass, PK id) {
		commonDao.delete(entityClass, id);
	}

	public <M> void deleteObject(Collection<M> models) {
		commonDao.deleteObject(models);
	}

	public <M> void deleteObject(M model) {
		commonDao.deleteObject(model);
	}

	public int executeUpdate(String hql) {
		return commonDao.executeUpdate(hql);
	}

	public int executeUpdate(String hql, Map<String, Object> paramMap) {
		return commonDao.executeUpdate(hql, paramMap);
	}

	public Object executeQuery(String hql) {
		return commonDao.executeQuery(hql);
	}

	public Object executeQuery(String hql, Map<String, Object> paramMap) {
		return commonDao.executeQuery(hql, paramMap);
	}

	public <M, PK extends Serializable> boolean exists(Class<M> entityClass, PK id) {
		return commonDao.exists(entityClass, id);
	}

	public <M, PK extends Serializable> List<M> get(Class<M> entityClass, Collection<PK> ids) {
		return commonDao.get(entityClass, ids);
	}

	public <M, PK extends Serializable> M get(Class<M> entityClass, PK id) {
		return commonDao.get(entityClass, id);
	}

	public <M> List<M> query(Class<M> entityClass, List<Criterion> criterions) {
		return commonDao.query(entityClass, criterions);
	}

	public <M> List<M> query(Class<M> entityClass, List<Criterion> criterions, long pageNumber, int pageSize) {
		return commonDao.query(entityClass, criterions, pageNumber, pageSize);
	}

    public <M> List<M> query(Class<M> entityClass, List<Criterion> criterions, List<Order> orders){
		return commonDao.query(entityClass, criterions, orders);
    }

    public <M> List<M> query(Class<M> entityClass, List<Criterion> criterions, List<Order> orders, long pageNumber, int pageSize){
		return commonDao.query(entityClass, criterions, orders, pageNumber, pageSize);
    }

    public <M> List<M> queryWithAlias(Class<M> entityClass, List<Criterion> criterions, List<AliasInfo> alias) {
		return commonDao.queryWithAlias(entityClass, criterions, alias);
	}
    
    public <M> List<M> queryWithAlias(Class<M> entityClass, List<Criterion> criterions, List<AliasInfo> alias, long pageNumber, int pageSize) {
		return commonDao.queryWithAlias(entityClass, criterions, alias, pageNumber, pageSize);
	}
    
    public <M> List<M> queryWithAlias(Class<M> entityClass, List<Criterion> criterions, List<AliasInfo> alias, List<Order> orders) {
		return commonDao.queryWithAlias(entityClass, criterions, alias, orders);
	}

    public <M> List<M> queryWithAlias(Class<M> entityClass, List<Criterion> criterions, List<AliasInfo> alias, List<Order> orders, long pageNumber, int pageSize){
		return commonDao.queryWithAlias(entityClass, criterions, alias, orders, pageNumber, pageSize);
	}
    
	public <M> List<M> query(String hql) {
		return commonDao.query(hql);
	}

	public <M> M queryUnique(String hql) {
		List<M> rs = commonDao.query(hql, 1, 1);
		if (rs == null || rs.isEmpty()) {
			return null;
		}
		return rs.get(0);
	}
	
	public <M> List<M> query(String hql, long pageNumber, int pageSize) {
		return commonDao.query(hql, pageNumber, pageSize);
	}

	public <M> List<M> query(String hql, Map<String, Object> paramMap) {
		return commonDao.query(hql, paramMap);
	}
	
	public <M> M queryUnique(String hql, Map<String, Object> paramMap) {
		List<M> rs = commonDao.query(hql, paramMap, 1, 1);
		if (rs == null || rs.isEmpty()) {
			return null;
		}
		return rs.get(0);
	}

	public <M> List<M> query(String hql, Map<String, Object> paramMap, long pageNumber, int pageSize) {
		return commonDao.query(hql, paramMap, pageNumber, pageSize);
	}

	public <M> List<M> queryAll(Class<M> entityClass) {
		return commonDao.queryAll(entityClass);
	}

	public <M> List<M> queryAll(Class<M> entityClass, long pageNumber, int pageSize) {
		return commonDao.queryAll(entityClass, pageNumber, pageSize);
	}

	public <M extends DefaultModelImpl> void merge(M model) {
		model.getModification().refreshLastModifiedDate();
		commonDao.merge(model);
	}
	
	public <M extends DefaultModelImpl> Long save(M model) {
		model.getModification().refreshLastModifiedDate();
		return commonDao.save(model);
	}

	public <M extends DefaultModelImpl> void saveOrUpdate(M model) {
		model.getModification().refreshLastModifiedDate();
		commonDao.saveOrUpdate(model);
	}

	public <M extends DefaultModelImpl> void update(M model) {
		model.getModification().refreshLastModifiedDate();
		commonDao.update(model);
	}

	public <M extends DefaultModelImpl> void logicDelete(Class<M> entityClass,
			Collection<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return;
		}
		for (Long id : ids) {
			this.logicDelete(entityClass, id);
		}
	}

	public <M extends DefaultModelImpl> void logicDelete(Class<M> entityClass, long id) {
		M m = this.get(entityClass, id);
		m.getLogicalDelete().setDeleted(true);
		this.update(m);
	}

	public <M extends DefaultModelImpl> void logicDeleteObject(Collection<M> models) {
		commonDao.deleteObject(models);
		for (M m : models) {
			this.logicDeleteObject(m);
		}
	}

	public <M extends DefaultModelImpl> void logicDeleteObject(M model) {
		model.getLogicalDelete().setDeleted(true);
		this.update(model);
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}
	
}
