/**
 *
 */
package works.tonny.apps.core;

import org.hibernate.annotations.GenericGenerator;
import works.tonny.apps.Entity;
import works.tonny.apps.auth.DataOwnerAware;

import javax.persistence.*;
import java.util.Set;

/**
 * 表单id生成器
 *
 * @author 祥栋
 */
@javax.persistence.Entity
@Table(name = "s_id_generator")
public class IDGenerator extends Entity implements DataOwnerAware<IDGeneratorDataOwner> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 描述
     */
    private String description;

    /**
     * 分段表达式
     */
    private String section;

    /**
     * 分隔符
     */
    private String spliter;


    private Set<IDGeneratorDataOwner> owner;


    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.Entity#getId()
     */
    @Override
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "assigned")
    @Column(length = 50)
    public String getId() {

        return super.getId();
    }

    /**
     * @return the name
     */
    @Column(length = 150)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the expression
     */
    @Column(length = 200)
    public String getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * @return the description
     */
    @Column(length = 500)
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the section
     */
    @Column(length = 300)
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the spliter
     */
    @Column(length = 1)
    public String getSpliter() {
        return spliter;
    }

    /**
     * @param spliter the spliter to set
     */
    public void setSpliter(String spliter) {
        this.spliter = spliter;
    }


    /**
     * @return the owner
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "data")
    public Set<IDGeneratorDataOwner> getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(Set<IDGeneratorDataOwner> owner) {
        this.owner = owner;
    }

    @Override
    @Transient
    public Class getOwnerClass() {
        return IDGeneratorDataOwner.class;
    }
}
