package com.woo.base.exception;

public class Test {

    public static void main(String[] args) throws Exception {
        try {
            try {
               Thread thread= new Thread(new Cal());
               thread.setName("test-thread");
                thread.start();
            }catch(Exception e){
                throw new ArithmeticException("计算错误");
            }finally{
                System.out.println("第一层 finally 块执行。");
            }
        } finally {
            System.out.println("外层 finally 块执行。");
        }

        Thread.sleep(10000);
    }

  public static class Cal implements Runnable  {
      @Override
      public void run() {
          try {
              if (3 > 2) {
                  throw new Exception("结果大于2的异常");
              }
          } catch (Exception e) {
              throw new RuntimeException(e);
          } finally {
              System.out.println(" finally 块执行。");
          }
      }
      }
  }


