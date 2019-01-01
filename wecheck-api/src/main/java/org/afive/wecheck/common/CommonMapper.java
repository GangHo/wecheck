package org.afive.wecheck.common;

import java.util.List;

public interface CommonMapper<E, K> {
	
	public List<E> getList();
	public E get(K pk);
	public void register(E vo);
	public void delete(K pk);
	public void update(E vo);
	
}
