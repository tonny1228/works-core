/**
 * 
 */
package works.tonny.apps.core;

import works.tonny.apps.Query;

/**
 * @author 祥栋
 */
public interface CatalogQuery extends Query<CatalogQuery, Catalog> {

	CatalogQuery catalogId(String catalogId);

	CatalogQuery name(String name);

	CatalogQuery nameLike(String name);

	CatalogQuery aliasLike(String alias);

	CatalogQuery status(int status);

	CatalogQuery display(int display);

	CatalogQuery type(int type);

	CatalogQuery idLayer(String idLayer);

	CatalogQuery idLayerLike(String idLayer);

	CatalogQuery depth(int depth);

	CatalogQuery depthGreateThan(int depth);

	CatalogQuery depthLessThan(int depth);

	CatalogQuery parentId(String parentId);

	CatalogQuery orderById();

	CatalogQuery orderByIdLayer(Direction direction);

    CatalogQuery orderByOrderNo();
}