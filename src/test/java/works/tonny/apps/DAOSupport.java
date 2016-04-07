package works.tonny.apps;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Test;

public class DAOSupport {

	@Test
	public void test() {
		SimpleExpression hello = Restrictions.like("hello", "1");
		SimpleExpression type = Restrictions.eq("type", 1);
		Junction m = Restrictions.disjunction().add(Restrictions.like("title", "1")).add(Restrictions.like("key", "1"));
		LogicalExpression or = Restrictions.or(Restrictions.like("title", "1"), Restrictions.like("key", "1"));
	}

	public String i(Criterion... c) {
		StringBuffer buffer = new StringBuffer();
		for (Criterion criterion : c) {
			if (buffer.length() > 0)
				buffer.append(" and ");
			if (criterion instanceof LogicalExpression) {
				buffer.append("(");
			}
			buffer.append(criterion.toString());
			if (criterion instanceof LogicalExpression) {
				buffer.append(")");
			}
		}
		return buffer.toString();
	}

	class Criteria {
		String field;

		Object value;

		String operator;
	}

}
