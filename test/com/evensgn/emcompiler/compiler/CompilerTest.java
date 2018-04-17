package com.evensgn.emcompiler.compiler; 

import static org.junit.Assert.*;
import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/** 
* Compiler Tester. 
* 
* @author Zhou Fan  
* @since 03/31/2018 
* @version 1.0 
*/ 
public class CompilerTest {
    @Before
    public void before() throws Exception {
    }
    
    @After
    public void after() throws Exception { 
    }

    @Test
    public void test0() throws Exception {
        assertEquals(1, 1);
    }

    @Test
    public void test1() throws Exception {
        InputStream inS = new FileInputStream("testcase/test.mx");
        OutputStream outS = System.out;
        Compiler compiler = new Compiler(inS, outS);
        compiler.run();
    }

}