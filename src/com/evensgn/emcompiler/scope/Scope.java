package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.Location;
import com.evensgn.emcompiler.utils.CompilerError;
import com.evensgn.emcompiler.utils.SemanticError;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    static private final String KEY_PREFIX = "$";
    static private final String VAR_PREFIX = "$VAR$";
    static private final String CLASS_PREFIX = "$CLASS$";
    static private final String FUNC_PREFIX = "$FUNC$";
    static public final String ARRAY_CLASS_NAME = "#ARRAY";
    static public final String STRING_CLASS_NAME = "#STRING";
    static public final String THIS_PARA_NAME = "this";

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
        if (selfContainsKey(key)) return false;
        entityMap.put(key, entity);
        return true;
    }

    public void putCheck(String name, String key, Entity entity) {
        if (!put(key, entity)) throw new SemanticError(String.format("Symbol name \"%s\" is already defined", name));
    }

    public void putCheck(Location location, String name, String key, Entity entity) {
        if (!put(key, entity)) throw new SemanticError(location, String.format("Symbol name \"%s\" is already defined", name));
    }

    public Entity getCheck(String name, String key) {
        Entity entity = get(key);
        if (entity == null) throw new SemanticError(String.format("Entity \"%s\" with key \"%s\" not found in scope", name, key));
        return entity;
    }

    public Entity getCheck(Location location, String name, String key) {
        Entity entity = get(key);
        if (entity == null) throw new SemanticError(location, String.format("Entity \"%s\" with key \"%s\" not found in scope", name, key));
        return entity;
    }

    public void update(String key, Entity entity) {
        if (!key.startsWith(KEY_PREFIX)) throw new CompilerError(String.format("Scope entity key should start with \'$\', but get %s", key));
        if (!entityMap.containsKey(key)) throw new CompilerError(String.format("Cannot update the key \"%s\" which the scope does not contain", key));
        entityMap.remove(key);
        entityMap.put(key, entity);
    }

    public void assertContainsExactKey(Location location, String name, String key) {
        if (!containsExactKey(key)) throw new SemanticError(location, String.format("Entity \"%s\" with key \"%s\" not found in scope", name, key));
    }

    private boolean selfContainsKey(String key) {
        String name;
        if (key.startsWith(VAR_PREFIX)) {
            name = key.substring(5);
            return entityMap.containsKey(VAR_PREFIX + name) || entityMap.containsKey(FUNC_PREFIX + name);
        }
        else if (key.startsWith(CLASS_PREFIX)) {
            name = key.substring(7);
            return entityMap.containsKey(CLASS_PREFIX + name) || entityMap.containsKey(FUNC_PREFIX + name);
        }
        else if (key.startsWith(FUNC_PREFIX)) {
            name = key.substring(6);
            return entityMap.containsKey(FUNC_PREFIX + name) || entityMap.containsKey(VAR_PREFIX + name) || entityMap.containsKey(CLASS_PREFIX + name);
        }
        else return false;
    }

    public boolean containsKey(String key) {
        Boolean found = selfContainsKey(key);
        if (!isTop && !found) return parent.containsKey(key);
        return found;
    }

    private boolean selfContainsExactKey(String key) {
        return entityMap.containsKey(key);
    }

    public boolean containsExactKey(String key) {
        Boolean found = selfContainsExactKey(key);
        if (!isTop && !found) return parent.containsExactKey(key);
        return found;
    }

    public Scope getParent() {
        return parent;
    }
}
