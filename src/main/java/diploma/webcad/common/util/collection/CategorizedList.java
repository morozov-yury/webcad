package diploma.webcad.common.util.collection;

import java.util.List;

/**
 * 
 * 
 * @author yarik.pro
 *
 * @param <C>
 * @param <E>
 */
public class CategorizedList<C,E> {
	
	ArrayMap<C,List<E>> body;
	
	public void addCategory(C c, List<E> e) {
		body.put(c, e);
	}
	
	public List<E> getCategory(C c) {
		return body.get(c);
	}
	
	public void removeCategory(C c) {
		body.remove(c);
	}

	//TODO: add functionality, if it is necessary. add comments
}
