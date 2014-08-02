package diploma.webcad.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseDao <T, PK extends Serializable> {
	
    public T read(PK id);
    
    public void delete(T persistentObject);
    
    public List<T> list();
    
    public List<T> list(String property, Object value);
    
    public List<T> listSortedAsc(String property);
    
    public T merge(T entity);
    
    public void refresh(T entity);
    
    public PK save(T enity);
    
    public void saveOrUpdate(T entity);
    
    public void batchSaveOrUpdate(Collection<T> entities);
    
}
