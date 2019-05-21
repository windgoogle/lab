package com.woo.base.winregistry;


import com.ice.jni.registry.NoSuchKeyException;

import com.ice.jni.registry.RegStringValue;

import com.ice.jni.registry.Registry;

import com.ice.jni.registry.RegistryException;

import com.ice.jni.registry.RegistryKey;



/**

 * @author solo L

 *

 */

public class IceDemo {



    /**

     * @param args

     */

    public static void main(String[] args) {



        //打开注册表项并读出相应的值

        try {

            RegistryKey software = Registry.HKEY_LOCAL_MACHINE.openSubKey("SYSTEM");

            RegistryKey currentControlSet = software.openSubKey("CurrentControlSet");

            RegistryKey services = currentControlSet.openSubKey("services");

            String subKey1Value = services.getStringValue("Apache-2.4.33");


            System.out.println(subKey1Value);


            services.closeKey();
            currentControlSet.closeKey();

        } catch (NoSuchKeyException e) {

            e.printStackTrace();

        } catch (RegistryException e) {

            e.printStackTrace();

        }

    }

}