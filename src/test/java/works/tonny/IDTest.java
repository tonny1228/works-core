/**
 * 
 */
package works.tonny;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import works.tonny.apps.core.Catalog;
import works.tonny.apps.core.service.IDGeneratorServiceImpl;

/**
 * @author 祥栋
 */
public class IDTest {

	@Test
	public void testID() {
		// Map<String, Object> context = new HashMap<String, Object>();
		// String execute =
		// ELHelper.execute("JDBC-${#date.yyyy-MM-dd}-${#seq.#0#1}", context);
		// System.out.println(execute);
		if (true) {
			return;
		}
		IDGeneratorServiceImpl idGeneratorService = new IDGeneratorServiceImpl();
		String id = null;
		Map<String, Object> context = new HashMap<String, Object>();
		Catalog value = new Catalog();
		value.setType(20);
		context.put("core", value);
		System.out.println(idGeneratorService.create(id, context));
		System.out.println(idGeneratorService.create(id, context));
		System.out.println(idGeneratorService.create(id, context));
		System.out.println(idGeneratorService.create(id, context));
		System.out.println(idGeneratorService.create(id, context));
		value.setType(100);
		System.out.println(idGeneratorService.create(id, context));
		System.out.println(idGeneratorService.create(id, context));
		System.out.println(idGeneratorService.create(id, context));
		System.out.println(idGeneratorService.create(id, context));
		System.out.println(idGeneratorService.create(id, context));
	}

	@Test
	public void AZ() {
		int a = 702;
		for (int i = 0; i < a; i++) {
			int pow = (int) Math.floor(i / 26);
			if (pow > 0)
				System.out.print((char) (64 + pow));
			int y = i % 26;
			System.out.println((char) (64 + y + 1));
		}
	}

	@Test
	public void zz() {
		System.out.println(columnIndex("A"));
	}

	public int columnIndex(String index) {
		int idx = 0;
		for (int i = 0; i < index.length(); i++) {
			idx += (index.charAt(i) - 64) * Math.pow(26, index.length() - i - 1);
		}
		return idx - 1;
	}

	@Test
	public void replaceTest() {
		System.out.println("/t/catalog/cms/list.action?id=cms".replaceAll("/catalog/(.*)list.action\\?id=cms", "$1"));
		String info = "df001:1";
		String username = null;
		String password = null;
		username = StringUtils.substringBefore(info, ":");
		password = StringUtils.substringAfter(info, ":");
		System.out.println(username);
		System.out.println(password);

		System.out.println(StringUtils.join("ssfd,sdfsdf|sdfsf,sdwesdf|sdf3".split("\\|"), ","));
	}
}
