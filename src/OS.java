import java.io.*;

/**
 * Created by huangfeng on 2017/4/1.
 */
public class OS {


    public static void main(String[] args) throws IOException {
        System.out.println("cpu : "+getCpuPercent());
        System.out.println("mem : "+getMemPercentByVmStat());
        //System.out.println("topas : ");
      //  getTopas();
        System.out.println("os : " +System.getProperty("os.name").toLowerCase());
    }

     public static  double getTopas() throws IOException {
        Process process = null;
        double result = 1;
        try {       
            process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "topas -i 3000"});
            BufferedReader reader = null;


            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             for(int i=1;i<30;i++) {
                 System.out.println();
                  System.out.println("row"+i + " : "+reader.readLine());   //row1
             }
             
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.getInputStream().close();
                process.getOutputStream().close();
                process.getErrorStream().close();
                process.destroy();
            }
        }
        return result;
     }

     public static double getMemPercentByVmStat() throws IOException {
         Process process = null;
         double result = 1;
         long memTotal=1;
         long memAvm=1;
         try{
             process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "vmstat -v"});
             BufferedReader reader = null;


             reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             String memPageTotalInfo=reader.readLine();   //row1
             String[] splitMemPageTotalInfo=memPageTotalInfo.split("\\s+");
             System.out.println("================"+splitMemPageTotalInfo[1]);
             memTotal=Long.parseLong(splitMemPageTotalInfo[1]);
         }catch(Exception ex) {
            ex.printStackTrace();
         }finally {
             if (process != null) {
                 process.getInputStream().close();
                 process.getOutputStream().close();
                 process.getErrorStream().close();
                 process.destroy();
             }
         }
         //avm

         try{
             process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "vmstat"});
             BufferedReader reader = null;


             reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             reader.readLine();
             reader.readLine();
             reader.readLine();
             reader.readLine();
             reader.readLine();
             reader.readLine();
             String str = reader.readLine();

             // if(str!=null&&str.toLowerCase().indexOf("idle")>0){
             String[] splitstr = str.split("\\s+");
             for(int i=0; i<splitstr.length ; i++) {
                 System.out.print(i+"="+splitstr[i] +" ");
             }
             System.out.println();
             //  break;
             // }

             String fetched = splitstr[3];
             memAvm=Long.parseLong(fetched);
             System.out.println("=========memAvm:"+memAvm);
             reader.close();
         }catch(Exception ex) {
             ex.printStackTrace();
         }finally {
             if (process != null) {
                 process.getInputStream().close();
                 process.getOutputStream().close();
                 process.getErrorStream().close();
                 process.destroy();
             }
         }
         System.out.println("avm="+memAvm+", total="+memTotal);
         result=((double)memAvm/(double)memTotal)*100;
         return result;
     }


    public static  double getMemPercent() throws IOException {
        Process process = null;
        double result = 1;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "svmon"});
            BufferedReader reader = null;


            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            reader.readLine();   //row1
            String meminfo=reader.readLine();


            // if(str!=null&&str.toLowerCase().indexOf("idle")>0){
            String[] splitstr = meminfo.split("\\s+");

            double memTotal =Long.parseLong( splitstr[1]);
            double memUsed = Long.parseLong(splitstr[2]);
            result=(memUsed/memTotal)*100;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.getInputStream().close();
                process.getOutputStream().close();
                process.getErrorStream().close();
                process.destroy();
            }
        }
        return result;
    }

    public static  double getCpuPercent() throws IOException {
        Process process = null;
        double result = 1;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "vmstat"});
            BufferedReader reader = null;


            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
            String str = reader.readLine();

            // if(str!=null&&str.toLowerCase().indexOf("idle")>0){
            String[] splitstr = str.split("\\s+");
            System.out.println(splitstr[splitstr.length - 2]);
            //  break;
            // }

            String fetched = splitstr[splitstr.length - 2];
            result = (100 - Long.parseLong(fetched));
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.getInputStream().close();
                process.getOutputStream().close();
                process.getErrorStream().close();
                process.destroy();
            }
        }
        return result;
    }
}
