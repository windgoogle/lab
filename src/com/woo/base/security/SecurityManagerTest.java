package com.woo.base.security;

import java.io.FileInputStream;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class SecurityManagerTest {

    public static void main(String[] args) throws Exception {
        System.out.println("SecurityManager :  "+System.getSecurityManager());

        //FileInputStream fis=new FileInputStream("E:\\test\\protect.txt");

        //System.out.println(System.getProperty("file.encoding"));
        System.getSecurityManager().checkPackageAccess("com.woo");
        AccessController.doPrivileged(new PrivilegedAction(){

            /**
             * Performs the computation.  This method will be called by
             * {@code AccessController.doPrivileged} after enabling privileges.
             *
             * @return a class-dependent value that may represent the results of the
             * computation. Each class that implements
             * {@code PrivilegedAction}
             * should document what (if anything) this value represents.
             * @see AccessController#doPrivileged(PrivilegedAction)
             * @see AccessController#doPrivileged(PrivilegedAction,
             * AccessControlContext)
             */
            @Override
            public Object run() {
                try {
                    FileInputStream fis = new FileInputStream("E:\\test\\protect.txt");
                }catch (Exception e){
                    e.printStackTrace();
                }

                System.out.println(System.getProperty("file.encoding"));
                return null;
            }
        } );
    }
}
