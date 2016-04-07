package works.tonny.apps;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.llama.library.utils.ArrayUtils;

public class ComlexDatatable {
	
	@Test
	public void test(){
		String fields = StringUtils.replace("1|2|3 \\|\\|4|5|6", "\\|", "__or_");
		String[] split = fields.split("\\|");
		for (int i = 0; i < split.length; i++) {
			split[i] = StringUtils.replace(split[i], "__or_", "|");
		}
		ArrayUtils.println(split);
	}

}
