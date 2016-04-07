/**
 * 
 */
package works.tonny.apps.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.llama.library.utils.PagedList;

import works.tonny.apps.Query;
import works.tonny.apps.support.BaseDAOSupport;

/**
 * @author 祥栋
 */
public abstract class AbstractQuery<T extends Query<?, ?>, U> implements Query<T, U>, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String SORTORDER_ASC = "asc";
	public static final String SORTORDER_DESC = "desc";

	private static enum ResultType {
		LIST, LIST_PAGE, SINGLE_RESULT, COUNT
	}

	protected ResultType resultType;

	protected BaseDAOSupport<U> dao;

	protected List<String> order = new ArrayList<String>();

	protected List<String> group = new ArrayList<String>();

	protected T orderBy(String orderBy, Direction direction) {
		addOrder(orderBy, direction);
		return (T) this;
	}

	protected T groupBy(String groupBy) {
		group.add(groupBy);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public U singleResult() {
		this.resultType = ResultType.SINGLE_RESULT;
		PagedList<U> list = executeSubList(0, 1);
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<U> list() {
		this.resultType = ResultType.LIST;
		return executeList();
	}

	/**
	 * @return
	 */
	protected abstract List<U> executeList();

	/**
	 * @return
	 */
	protected abstract PagedList<U> executeList(int page, int pagesize);

	@SuppressWarnings("unchecked")
	@Deprecated
	public PagedList<U> list(int page, int pagesize) {
		this.resultType = ResultType.LIST_PAGE;
		return executeList(page, pagesize);
	}

	protected abstract PagedList<U> executeSubList(int offset, int limit);

	/**
	 * @see works.tonny.apps.Query#listRange(int, int)
	 */
	@Override
	public PagedList<U> listRange(int offset, int limit) {
		return executeSubList(offset, limit);
	}

	public long count() {
		this.resultType = ResultType.COUNT;
		PagedList<U> list = executeList(0, 1);
		return list.getTotal();
	}

	protected void addOrder(String column, Direction sortOrder) {
		order.add(column + " " + sortOrder);
	}
}
