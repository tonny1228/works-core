package works.tonny.apps.auth;

import java.util.Set;

public interface DataOwnerAware<T extends DataOwner> {

	Set<T> getOwner();

	void setOwner(Set<T> owner);

	Class getOwnerClass();

}
