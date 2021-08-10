package com.linkage.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

import javax.naming.Context;
import javax.naming.InitialContext;

public class BenchMarkClient {

    protected static AtomicLong count = new AtomicLong();

    protected static AtomicLong threadTime = new AtomicLong(0);
    protected static AtomicLong tpsCount = new AtomicLong(0);


    protected static int threadNum = 500;

    protected static int requestNum = 1000;

    protected static String method;

    protected static Properties props = new Properties();

    private static String testStr = "ghytnteststr";

    static {
        for (int i = 0; i < 5; i++) {
            testStr += testStr;
        }
    }

    public static void main(String[] args) throws Exception {
        parseCommondLineArgs(args);

        InitialContext ctx = new InitialContext(props);

        String jndiName = props.getProperty("ejb.jndiname");
        BenchMarkRemote remote = (BenchMarkRemote) ctx.lookup(jndiName);

        String oldmethod=method.toUpperCase();
        //excute(remote);
        if(oldmethod.equals("ALL")){
            method="int";
            excute(remote);
            method="string";
            excute(remote);
            method="bean";
            excute(remote);
            method="list";
            excute(remote);
        }else{
            excute(remote);
        }
    }

    private static void excute(BenchMarkRemote remote) throws InterruptedException{

        threadTime.set(0);
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new Task(remote));
            threads[i].start();
        }
        // statistics TPS
        long lastTime = System.currentTimeMillis();
        long lastCount = 0;
        System.out.print(method);
        for (int i = 0; i < threadNum; ) {
            if(threads[i].isAlive()){
                Thread.sleep(1000);
                System.out.print(".");
            }else{
                i++;
            }
        }

        DecimalFormat df=new DecimalFormat("0.00000");

        double b_count = (double)(count.get());

        double second=threadTime.get()/1000;

        System.out.println("spent time: "+df.format(second)+"s");

        System.out.println("\n---->result : method: "+method+ " tps is "+df.format(b_count/second));

    }

    public static HashMap<String, String> parseCommondLineArgs(
            Iterator<String> argIterator) {
        HashMap<String, String> optionsMap = new HashMap<String, String>();
        while (argIterator.hasNext()) {
            String option = argIterator.next();
            if (option.startsWith("-")) {
                if (argIterator.hasNext()) {
                    String optionValue = argIterator.next();
                    optionsMap.put(option, optionValue);
                } else {
                    throw new IllegalArgumentException("Argument don't match!");
                }
            } else {
                throw new IllegalArgumentException("Argument don't match!");
            }
        }
        return optionsMap;
    }

    public static void parseCommondLineArgs(String[] args) throws Exception {
        System.out
                .println("Usage: java com.linkage.test.BenchMarkClient -threadNum <threadNum> -requestNum <requestNum> -method <method>");
        HashMap<String, String> optionsMap = parseCommondLineArgs(Arrays
                .asList(args).listIterator());

        String threadNumVal = optionsMap.get("-threadNum");
        if (threadNumVal == null) {
            System.out.println("-threadNum is not specified!");
        } else {
            threadNum = Integer.parseInt(threadNumVal);
        }

        String requestNumVal = optionsMap.get("-requestNum");
        if (requestNumVal == null) {
            System.out.println("-requestNum is not specified!");
        } else {
            requestNum = Integer.parseInt(requestNumVal);
        }
        method = optionsMap.get("-method");
        if (method == null) {
            System.out.println("-method is not specified!");
        }

        props.put("jboss.naming.client.ejb.context", true);
        initJndi("jndi.properties", props);
        System.out.println("providerUrl:"
                + props.getProperty(Context.PROVIDER_URL));
        System.out.println("jndiName:" + props.getProperty("ejb.jndiname"));
        System.out.println("threadNum:" + threadNum);
        System.out.println("executeNum:" + requestNum);
        System.out.println("method:" + method);
    }

    /*
     * get properties from jndi.properties
     */
    private static void initJndi(String propath, Properties props) {
        try {
            props.load(BenchMarkClient.class.getClassLoader()
                    .getResourceAsStream(propath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // use thread to invoke ejb method
    private static class Task implements Runnable {

        private static int threadCount = 0;

        private BenchMarkRemote remote;

        private String threadName = String.valueOf(threadCount++);

        public Task(BenchMarkRemote remote) {
            this.remote = remote;
            Thread.currentThread().setName("TestThread " + threadName);
        }

        public void run() {
            long start = System.currentTimeMillis();
            try {
                for (int i = 0; requestNum == -1 || i < requestNum; i++) {

                    if ("int".equals(method)) {
                        long result = remote.getLong(676342224777980l);
                    }
                    if ("string".equals(method)) {
                        remote.getString(testStr);
                    }
                    if ("bean".equals(method)) {
                        SimpleBean bean = new SimpleBean();
                        SimpleBean retBean = remote.getSimpleBean(bean);
                    }
                    if ("list".equals(method)) {
                        ArrayList<SimpleBean> list = new ArrayList<SimpleBean>();
                        for (int j = 0; j < 5; j++) {
                            SimpleBean bean = new SimpleBean();
                            list.add(bean);
                        }
                        List returnList = remote.getList(list);
                    }
                    count.incrementAndGet();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            long interval = System.currentTimeMillis() - start;
            threadTime.addAndGet(interval);
            System.out.println("Thread " + threadName + " interval: "
            		+ interval);
        }
    }
}
