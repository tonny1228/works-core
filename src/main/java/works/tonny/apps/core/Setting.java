/**
 * <p>
 * <p/>
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2015
 * </p>
 *
 * @date 2015-3-26 上午10:34:34
 * @author tonny
 */
package works.tonny.apps.core;

import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.annotations.GenericGenerator;
import org.llama.library.utils.IOUtils;
import works.tonny.apps.Entity;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * 系统设置变量保存，保存键值对形式的数据
 * </p>
 *
 * @author tonny
 * @version 1.0.0
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "catalog", length = 50)
@Table(name = "s_setting")
@DiscriminatorValue("system")
public class Setting extends Entity {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String key;

    private String name;

    private String stringValue;

    @Transient
    private Number numberValue;

    @Transient
    private Object objectValue;

    private byte[] objectBytes;

    private String type;

    /**
     *
     */
    public Setting() {
    }

    /**
     * @param name
     * @param objectValue
     */
    public Setting(String key, String name, Object objectValue) {
        super();
        setKey(key);
        setName(name);
        setValue(objectValue);
    }

    /**
     * @see works.tonny.apps.Entity#getId()
     */
    @Override
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @Column(length = 50)
    public String getId() {
        return id;
    }

    @Column(length = 50, name = "setting_key")
    public String getKey() {
        return key;
    }

    @Transient
    public Object getValue() {
        if (stringValue == null || objectBytes == null) {
            return null;
        }
        if ("java.lang.String".equals(type) || "string".equals(type)) {
            return stringValue;
        }
        if ("int".equals(type) || "java.lang.Integer".equals(type)) {
            return NumberUtils.toInt(stringValue);
        }
        if ("long".equals(type) || "java.lang.Long".equals(type)) {
            return NumberUtils.toLong(stringValue);
        }
        if ("date".equals(type) || "java.util.Date".equals(type)) {
            return new Date(NumberUtils.toLong(stringValue));
        }
        if ("float".equals(type) || "java.lang.Float".equals(type)) {
            return NumberUtils.toFloat(stringValue);
        }
        if ("double".equals(type) || "java.lang.Double".equals(type)) {
            return NumberUtils.toDouble(stringValue);
        }
        if ("boolean".equals(type) || "java.lang.Boolean".equals(type)) {
            return NumberUtils.toDouble(stringValue);
        }
        if (stringValue != null) {
            return stringValue;
        }
        try {
            return IOUtils.read(objectBytes);
        } catch (Exception e) {
            return null;
        }
    }

    public void setValue(Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof String) {
            setStringValue((String) value);
        } else if (value instanceof Number) {
            setStringValue(value.toString());
        } else {
            setObjectValue(value);
        }
        this.type = value.getClass().getName();
    }

    /**
     * @return the name
     */
    @Column(length = 50)
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
     * @return the stringValue
     */
    @Column(length = 500)
    public String getStringValue() {
        return stringValue;
    }

    /**
     * @param stringValue the stringValue to set
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * @return the numberValue
     */
    @Transient
    public Number getNumberValue() {
        return (Number) getValue();
    }

    /**
     * @param numberValue the numberValue to set
     */
    public void setNumberValue(Number numberValue) {
        this.numberValue = numberValue;
    }

    /**
     * @return the objectValue
     */
    @Transient
    public Object getObjectValue() {
        return objectValue;
    }

    /**
     * @param objectValue the objectValue to set
     */
    protected void setObjectValue(Object objectValue) {
        this.objectValue = objectValue;
        try {
            this.objectBytes = IOUtils.write(objectValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the type
     */
    @Column(length = 50)
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the objectBytes
     */
    @Column(columnDefinition = "BLOB")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    public byte[] getObjectBytes() {
        return objectBytes;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @param objectBytes the objectBytes to set
     */
    public void setObjectBytes(byte[] objectBytes) {
        this.objectBytes = objectBytes;
    }
}
