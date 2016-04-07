/**
 * 
 */
package works.tonny.apps.support;

import org.hibernate.SessionFactory;
import org.llama.library.utils.ThreadLocalMap;
import org.springframework.transaction.TransactionDefinition;

import works.tonny.apps.deploy.SessionFactoryManager;

/**
 * @author 祥栋
 * @date 2012-11-15
 * @version 1.0.0
 */
public class HibernateTransactionManager extends org.springframework.orm.hibernate3.HibernateTransactionManager {
	/**
	 * 
	 */
	public static final String USE_TRANSACTION = "useTransaction";

	private SessionFactoryManager factoryManager;

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTransactionManager#getSessionFactory()
	 */
	@Override
	public SessionFactory getSessionFactory() {
		return factoryManager.getSessionFactory();
	}

	public SessionFactoryManager getFactoryManager() {
		return factoryManager;
	}

	public void setFactoryManager(SessionFactoryManager factoryManager) {
		this.factoryManager = factoryManager;
	}

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTransactionManager#doBegin(java.lang.Object,
	 *      org.springframework.transaction.TransactionDefinition)
	 */
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		ThreadLocalMap.getInstance().putObject(USE_TRANSACTION, Boolean.TRUE);
		super.doBegin(transaction, definition);
	}

	/**
	 * @see org.springframework.orm.hibernate3.HibernateTransactionManager#doCleanupAfterCompletion(java.lang.Object)
	 */
	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		ThreadLocalMap.getInstance().putObject(USE_TRANSACTION, null);
	}

}
