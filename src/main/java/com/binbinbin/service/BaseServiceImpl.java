
package com.binbinbin.service;

import com.binbinbin.util.MyStringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected static final String SUCCESS = "SUCCESS";
    protected static final String ERROR = "ERROR";
    protected Logger logger = Logger.getLogger(BaseServiceImpl.class);

    @Autowired
    protected Mapper<T> mapper;

    public String save(T entity) {
        int result = 0;
        result = this.mapper.insertSelective(entity);
        return result > 0 ? SUCCESS : ERROR;
    }

    public String delete(Object key) {
        int result = 0;
        result = this.mapper.deleteByPrimaryKey(key);
        return result > 0 ? SUCCESS : ERROR;
    }

    public String update(T entity) {
        int result = 0;
        result = this.mapper.updateByPrimaryKeySelective(entity);
        return result > 0 ? SUCCESS : ERROR;
    }
    public String saveOrUpdate(T entity){
        int result = 0;
        T find=findByObject(entity);
        if(find==null){
            result = this.mapper.insertSelective(entity);
        }else {
            result = this.mapper.updateByPrimaryKeySelective(entity);
        }
        return result > 0 ? SUCCESS : ERROR;

    }

	/*public String batchDelete(Object ids[]) {
        try {
			for (int i = 0; i < ids.length; i++) {
				delete(Long.valueOf((String) ids[i]));// TODO
														// 转LONG，不知道其他地方会不会有问题
			}
		} catch (Exception e) {
			logger.error("---batch error---", e);
			return ERROR;
		}
		return SUCCESS;
	}*/

    public T findByPrimaryKey(Object key) {
        return this.mapper.selectByPrimaryKey(key);
    }

    /**
     * 查询单个对象：如果多条记录则会抛出异常
     *
     * @param entity
     * @return
     */
    public T findByObject(T entity) {
        return this.mapper.selectOne(entity);
    }

    public List<T> queryExampleForList(Object example) {
        return this.mapper.selectByExample(example);
    }

    public List<T> queryObjectForList(String order) {
        PageHelper.orderBy(order);
        return this.mapper.selectAll();
    }

    public List<T> queryObjectForList() {
        return this.mapper.selectAll();
    }

    /**
     * 带条件查询所有
     *
     * @param entity
     * @return
     */
    public List<T> queryObjectForList(T entity) {
        return this.mapper.select(entity);
    }

    public List<T> queryObjectForList(T entity, String order) {
        PageHelper.orderBy(order);
        return this.mapper.select(entity);
    }

    public PageInfo<T> queryPageForList() {
        return queryPageForList(null);
    }

    public PageInfo<T> queryPageForList(T entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getParameter("pageNum")+" "+request.getParameter("pageSize")+" "+request.getParameter("sort")+" "+request.getParameter("order"));
        Integer pageNum = MyStringUtil.valueOf(request.getParameter("pageNum"), 1);
        Integer pageSize = MyStringUtil.valueOf(request.getParameter("pageSize"), 10);
        PageHelper.startPage(pageNum,pageSize);
        String orderField = request.getParameter("sort");
        String orderDirection = request.getParameter("order");
        if (MyStringUtil.isNotEmpty(orderField)) {
            PageHelper.orderBy(orderField);
            if (MyStringUtil.isNotEmpty(orderDirection)) {
                PageHelper.orderBy(orderField + " " + orderDirection);
            }
        }
        return new PageInfo<T>(mapper.select(entity));
    }
}
