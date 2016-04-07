/**
 * 
 */
package works.tonny.apps.support.rpc;

/**
 * 
 * @author 祥栋
 * @date 2012-11-20
 * @version 1.0.0
 */
public interface RPCCaller {

	/**
	 * 连接远程接口
	 * 
	 * @return
	 */
	boolean connect();

	/**
	 * 调用远程接口,并接收返回数据
	 * 
	 * @param pameters
	 * @return
	 */
	Result call(Object... pameters);

	/**
	 * 关闭远程接口
	 * 
	 * @return
	 */
	boolean close();

}
