/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 * @date 2015-3-26 下午3:29:01
 * @author tonny
 */
package works.tonny.apps;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import works.tonny.Test;
import works.tonny.apps.core.Setting;
import works.tonny.apps.core.SettingService;

/**
 * <p>
 * 
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class SettingTest extends Test {
	@Autowired
	private SettingService service;

	@org.junit.Test
	@Rollback
	public void testSetting() {
//		service.setSetting(new Setting("name", null, "TonnyLiu"));
//		service.setSetting(new TestSetting("name", null, "TonnyLiu"));
//		service.setSetting(new TestSetting1("name", null, "TonnyLiu"));
//		service.deleteSetting(service.getSetting(TestSetting.class, "name"));
	}
}

@Entity
@DiscriminatorValue("test")
class TestSetting extends Setting {
	/**
	 * 
	 */
	public TestSetting() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param key
	 * @param name
	 * @param objectValue
	 */
	public TestSetting(String key, String name, Object objectValue) {
		super(key, name, objectValue);
	}

}

@Entity
@DiscriminatorValue("test1")
class TestSetting1 extends Setting {
	/**
	 * 
	 */
	public TestSetting1() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param key
	 * @param name
	 * @param objectValue
	 */
	public TestSetting1(String key, String name, Object objectValue) {
		super(key, name, objectValue);
	}

}
