package com.woo.ee;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import java.io.File;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author huangfeng
 */

public class AMQTool {

    private static Context context;
    private static String AMQ_HOME;
    private static String AMQ_HOME_CONF;
    private static ResourceCreateContext creationContext;
    private static String  resourceType;
    private static String [] jndiNames;


    public static void main(String[] args) throws Exception {
        processArguments(args);
        context=createContext();
        createResource();
    }

    public static void processArguments(String [] args) {

        if(args.length<2) {
            System.out.println("The resource's type and name is required!");
            System.exit(1);
        }

         resourceType=args[0];
         jndiNames=args[1].split(",");

        AMQ_HOME=System.getenv("AMQ_HOME");
        if(AMQ_HOME==null||AMQ_HOME.isEmpty()){
            String twHome = null;
            if (System.getenv("TW_HOME") != null) {
                twHome = System.getenv("TW_HOME");
            }

            if(twHome!=null){
                AMQ_HOME=twHome+File.separator+"apache-activemq";
            }else{
                System.out.println("AMQ_HOME not found !");
                System.exit(1);
            }
        }
        if(Files.notExists(Paths.get(AMQ_HOME))){
            System.out.println("AMQ not found at location '"+Paths.get(AMQ_HOME)+"' !");
            System.exit(1);
        }
        AMQ_HOME_CONF=AMQ_HOME+File.separator+"conf";

    }


    public static Context createContext() {
        try {
            Properties props = new Properties();
            props.put("java.naming.factory.initial", "com.sun.jndi.fscontext.RefFSContextFactory");
            if (AMQ_HOME != null) {
                props.put("java.naming.provider.url", "file:" +AMQ_HOME_CONF);
            }
            context = new InitialContext(props);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return context;
    }


 public static void setCreationContext() {
        switch (resourceType) {
            case "Q": creationContext=createQ();break;
            case "T": creationContext=createTopic();break;
            case "CF":creationContext=createCF();break;
            case "XACF":creationContext=createXACF();break;
            default:
                System.out.println("Unkown resource type: " + resourceType);
                System.exit(1);
        }
 }


    public static void createResource() {
        setCreationContext();

        for (String name : jndiNames) {
            try {
                creationContext.prev();
                Object resource = creationContext.create(name);
                amqJndiBind(name, resource);
                creationContext.onSuccess(name);
            }catch (Exception e) {
                creationContext.onException(name);
            }
        }
    }

    public static void amqJndiBind(String name,Object resource)throws  Exception{
        try {

            //resolveName(name);
            context.rebind(name, resource);
        }catch (NameAlreadyBoundException e) {
            System.out.println("Jndi name'" + name + "' already bind !");
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public static ResourceCreateContext createQ() {
        return new ResourceCreateContext() {

            @Override
            public void prev() {

            }

            @Override
            public Object create(String name) {
                return new ActiveMQQueue(name);
            }

            @Override
            public void onException(String name) {
                System.out.println("Create queue '"+name+"' failed !");
            }

            @Override
            public void onSuccess(String name) {
                System.out.println("Create queue '"+name+"' successfully !");
            }

        } ;
    }

    public static ResourceCreateContext createTopic() {
        return new ResourceCreateContext() {

            @Override
            public void prev() {

            }

            @Override
            public Object create(String name) {
                return new ActiveMQTopic(name);
            }

            @Override
            public void onException(String name) {
                System.out.println("Create topic '"+name+"' failed !");
            }

            @Override
            public void onSuccess(String name) {
                System.out.println("Create topic '"+name+"' successfully !");
            }

        } ;
    }


    public static ResourceCreateContext createCF() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        return new ResourceCreateContext() {

            @Override
            public void prev() {

            }

            @Override
            public Object create(String name) {
                return new ActiveMQConnectionFactory();
            }

            @Override
            public void onException(String name) {
                System.out.println("Create connectionFactory '"+name+"' failed !");
            }

            @Override
            public void onSuccess(String name) {
                System.out.println("Create connectionFactory '"+name+"' successfully !");
            }

        } ;
    }


    public static ResourceCreateContext createXACF() {
        ActiveMQXAConnectionFactory xacf = new ActiveMQXAConnectionFactory();
        return new ResourceCreateContext() {

            @Override
            public void prev() {

            }

            @Override
            public Object create(String name) {
                return  new ActiveMQXAConnectionFactory();
            }

            @Override
            public void onException(String name) {
                System.out.println("Create XAConnectionFactory '"+name+"' failed !");
            }

            @Override
            public void onSuccess(String name) {
                System.out.println("Create XAConnectionFactory '"+name+"' successfully !");
            }

        } ;
    }


    private static void resolveName(String name) throws Exception {
        Path jndiPath;
        try {
            jndiPath = Files.createDirectory(Paths.get(AMQ_HOME_CONF + File.separator + name).toAbsolutePath());
        } catch (FileAlreadyExistsException e) {
            System.out.println("File '"+name+"' already exisit!");
        }

    }


    interface ResourceCreateContext {
        void prev();
        Object create(String name) ;
        void onSuccess(String name);
        void onException(String name);
    }

}
