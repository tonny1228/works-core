package works.tonny.apps.core;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.EntityService;
import works.tonny.apps.auth.AuthType;
import works.tonny.apps.auth.AuthVerify;

import javax.persistence.RollbackException;

public interface CatalogService extends EntityService<Catalog> {
    /**
     * 新建子目录
     *
     * @param catalog  目录信息
     * @param parentId 父目录id
     */
    @AuthVerify(AuthType.UPDATE)
    String save(Catalog catalog, String parentId);

    /**
     * 移动栏目
     *
     * @param subId子目录字符串id
     * @param parentId       新父栏目id
     */
    @AuthVerify(AuthType.UPDATE)
    void moveCatalog(String subId, String parentId);

    /**
     * 更新栏目状态
     *
     * @param catalogId 栏目id
     * @param status    状态
     */
    @AuthVerify(AuthType.UPDATE)
    Catalog updateStatus(String catalogId, int status);


    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    Catalog get(String id);

    /**
     * 查询目录的根节点
     *
     * @param id目录编号
     * @return 目录
     */
    @AuthVerify(AuthType.LIST)
    Catalog root(String id);

    /**
     * 通过目录层获取下级子目录
     *
     * @param id 目录编号
     * @return List<Catalog> 返回子目录集合
     * @throws
     * @Title: listSubs
     * @Description:
     */
    @AuthVerify(AuthType.LIST)
    List<Catalog> listSubs(String id);

    @AuthVerify(AuthType.LIST)
    List<CatalogTreeNode> subs(String id);

    /**
     * 通过目录层获取下级子目录
     *
     * @param id 目录编号
     * @return List<Catalog> 返回子目录集合
     * @throws
     * @Title: listSubs
     * @Description:
     */
    @AuthVerify(AuthType.LIST)
    List<Catalog> listSubs(String id, int type, int depth);

    /**
     * 通过目录层获取下级子目录
     *
     * @param id 目录编号
     * @return List<Catalog> 返回子目录集合
     * @throws
     * @Title: listSubs
     * @Description:
     */
    @AuthVerify(AuthType.LIST)
    List<Catalog> root();

    /**
     * 注册用户更新监听
     *
     * @param name 监听的名称
     * @param listener 监听器
     */
    // void registerListener(String name, EntityUpdateListener<Catalog> listener);

    /**
     * 删除监听器
     *
     * @param name 监听器名称
     */
    // void removeListener(String name);

    /**
     * 创建复杂查询
     *
     * @return
     */
    CatalogQuery createQuery();

    @AuthVerify(AuthType.DELETE)
    void delete(String[] ids);

    @AuthVerify(AuthType.DELETE)
    void delete(String id);

}
