package com.evensgn.emcompiler.scope; 

import static org.junit.Assert.*;

import com.evensgn.emcompiler.type.IntType;
import com.evensgn.emcompiler.type.Type;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* Scope Tester. 
* 
* @author Zhou Fan
* @since 04/25/2018
* @version 1.0 
*/ 
public class ScopeTest {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testBasicScope() {
        Scope scope = new Scope();
        String key, name;
        name = "aName";
        key = Scope.classKey(name);
        assertTrue(scope.put(key, new VarEntity("test", IntType.getInstance())));
        key = Scope.varKey(name);
        assertTrue(scope.put(key, new VarEntity("test", IntType.getInstance())));
        key = Scope.funcKey(name);
        assertFalse(scope.put(key, new VarEntity("test", IntType.getInstance())));
        Entity entity;
        entity = scope.get(key);
        assertNull(entity);
        key = Scope.classKey(name);
        entity = scope.get(key);
        assertNotNull(entity);
        key = Scope.varKey(name);
        entity = scope.get(key);
        assertNotNull(entity);
    }

    @Test
    public void testParentScope() {
        Scope parentScope = new Scope();
        String key, name;
        name = "jedi";
        key = Scope.classKey(name);
        parentScope.put(key, new VarEntity("test", IntType.getInstance()));
        Scope childScope = new Scope(parentScope, false);
        assertNotNull(childScope.get(key));
        assertTrue(childScope.containsKey(key));
    }
}
