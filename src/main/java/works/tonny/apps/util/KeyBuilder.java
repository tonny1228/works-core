package works.tonny.apps.util;

import org.llama.library.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * 为缓存提供的key构造器
 * Created by Tonny on 2016/7/6.
 */
public class KeyBuilder {
    private StringBuffer builder = new StringBuffer();

    private static final String SPLIT = "_";

    private String mainKey;

    public KeyBuilder(String mainKey) {
        this.mainKey = mainKey;
    }

    private KeyBuilder appendSplit() {
        builder.append(SPLIT);
        return this;
    }

    public KeyBuilder append(String str) {
        if (str == null) {
            return appendSplit();
        }
        builder.append(str);
        return appendSplit();
    }


    public KeyBuilder append(int str) {
        builder.append(str);
        return appendSplit();
    }

    public KeyBuilder append(Integer str) {
        if (str == null) {
            return appendSplit();
        }
        builder.append(str);
        return appendSplit();
    }

    public KeyBuilder append(long str) {
        builder.append(str);
        return appendSplit();
    }

    public KeyBuilder append(Long str) {
        if (str == null) {
            return appendSplit();
        }
        builder.append(str);
        return appendSplit();
    }

    public KeyBuilder append(Date str) {
        if (str == null) {
            return appendSplit();
        }
        builder.append(DateUtils.toString(str, "yyyyMMDDHHmmss"));
        return appendSplit();
    }

    public KeyBuilder append(float str) {
        builder.append(str);
        return appendSplit();
    }


    public KeyBuilder append(Float str) {
        if (str == null) {
            return appendSplit();
        }
        builder.append(str);
        return appendSplit();
    }


    @Override
    public String toString() {
        return mainKey + builder.toString().replaceAll("[^\\w]", "_");
    }

    public KeyBuilder append(List<String> list) {
        if (list == null || list.isEmpty()) {
            return appendSplit();
        }
        for (String s : list) {
            builder.append(s);
        }
        return appendSplit();
    }

}
