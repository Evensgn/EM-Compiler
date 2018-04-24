package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.utils.CompilerError;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    static private final String KEY_PREFIX = "$";
    static private final String VAR_PREFIX = "$VAR$";
    static private final String CLASS_PREFIX = "$CLASS$";
    static private final String FUNC_PREFIX = "$FUNC$";

    private Map<String, Entity> entityMap = new HashMap<>();
    private Scope parent;
    private boolean isTop;

    public Scope() {
        this.isTop = true;
    }

    public Scope(Scope parent) {
        this.isTop = false;
        this.parent = parent;
    }

    static public String varKey(String name) {
        return VAR_PREFIX + name;
    }

    static public String classKey(String name) {
        return CLASS_PREFIX + name;
    }

    static public String funcKey(String name) {
        return FUNC_PREFIX + name;
    }

    public Entity get(String key) {
        Entity entity = entityMap.get(key);
        if (isTop || entity != null) return entity;
        else return parent.get(key);
    }

    public boolean put(String key, Entity entity) {
        if (!key.startsWith(KEY_PREFIX)) throw new CompilerError(String.format("Scope entity key should start with \'$\', but get %s", key));
        if (containsKey(key)) return false;
        entityMap.put(key, entity);
        return true;
    }

    public boolean containsKey(String key) {
        String name;
        boolean found;
        if (key.startsWith(VAR_PREFIX)) {
            name = key.substring(5);
            found = entityMap.containsKey(VAR_PREFIX + name) || entityMap.containsKey(FUNC_PREFIX + name);
        }
        else if (key.startsWith(CLASS_PREFIX)) {
            name = key.substring(7);
            found = entityMap.containsKey(CLASS_PREFIX + name) || entityMap.containsKey(FUNC_PREFIX + name);
        }
        else if (key.startsWith(FUNC_PREFIX)) {
            name = key.substring(6);
            found = entityMap.containsKey(FUNC_PREFIX + name) || entityMap.containsKey(VAR_PREFIX + name) || entityMap.containsKey(CLASS_PREFIX + name);
        }
        else return false;
        if (!isTop && !found) return parent.containsKey(key);
        return found;
    }
}
