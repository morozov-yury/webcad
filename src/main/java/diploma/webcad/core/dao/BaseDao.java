package diploma.webcad.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseDao <T, PK extends Serializable> {
	
    T read(PK id);
    
    void delete(T persistentObject);
    
    List<T> list();
    
    List<T> list(String property, Object value);
    
    List<T> listSortedAsc(String property);
    
    T merge(T entity);
    
    void refresh(T entity);
    
    T create(T enity);
    
    void saveOrUpdate(T entity);
    
    void batchSaveOrUpdate(Collection<T> entities);
    
}
