package works.tonny.apps.core;

import works.tonny.apps.auth.DataOwner;

import javax.persistence.*;

/**
 * Created by tonny on 2015/8/20.
 */
@Entity
@Table(name = "s_idg_owner")
public class IDGeneratorDataOwner extends DataOwner<IDGenerator> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "data_id")
    public IDGenerator getData() {
        return super.getData();
    }
}
