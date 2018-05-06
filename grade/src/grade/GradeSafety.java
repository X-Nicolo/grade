package grade;

import org.json.*;
import java.util.*;



public class GradeSafety {

	/**
	 * 评分类：   GradeSafety
	 * 每一项的评分方法：    Gradexxxxxx      xxxxx为内容编号    如GradeGCT0160()
	 * 方法原型   public String Gradexxxxxx(String param)
	 * 输入参数param，为json字符串；组织方式:{"param1":"data1","param2":"data2",......}
	 * 输出内容，为json字符串；组织方式:{"success":true/false,"score":"80","info":"评分成功/错误信息"}
	 */
	
	
	/**
	 *
	 *  部件得分计算
	 * 
	 * {"构件总数":"n","构件1"："65","构件2"："75"，..."构件n"："100"}
	 * @param gjScore 一个部件对应的构件总数及每个构件的得分值  {"gj_sum":"n","gj1"："65","gj2"："75"，..."gjn"："100"}
	 * @return
	 */
	public static double GCT0160(String gjScores){
		
		JSONObject json = new JSONObject(gjScores);
		int n = 0; 	 
		Iterator<String> it = json.keys();	//获取所有的键
		ArrayList<Double> al = new ArrayList<Double>();
		
		while(it.hasNext()){
			String key = it.next();
			if(key == "gj_sum"){	//.getString(key)： 根据键获取值
				n = Integer.parseInt(json.getString("gj_sum"));
			} else {
				double gjScore = Double.parseDouble(json.getString(key));
				al.add(gjScore);
			}
		}
		
		//集合变数组		
		Double[] arr = al.toArray(new Double[al.size()]);
		
		//n-t对照表
		double t = 0;
		if(n==1){
			t = 1/0.0;
		} else if(n==2){
			t = 10;
		} else if(n==3){
			t = 9.7;
		} else if(n==4){
			t = 9.5;
		} else if(n==5){
			t = 9.2;
		} else if(n>=6 && n<=14){
			t = 8.9-(n-6)*0.2;
		} else if(n>=15 && n<=30){
			t = 7.2-(n-15)*0.12;
		} else if(n>=31 && n<=50){
			t = 5.4-(n-30)*0.05;
		} else if(n>=51 && n<=90){
			t = 4.4-(n-50)*0.04;
		} else if(n>=91 && n<=100){
			t = 2.8-(n-90)*0.03;
		} else if(n>=101 && n<=200){
			t = 2.5-(n-100)*0.002;
		} else if(n>200){
			t = 2.3;
		}	
		
		double ave = 0,sum = 0,min = 0,bjScore = 0;
		int index = 0;
		
		//求均值
		for(int x=0; x<arr.length; x++){
			sum += arr[x];
		}	
		ave = sum/n;
		
		//求最小值
		for(int x=1; x<arr.length; x++){
			
			if(arr[x]<arr[index]){
				index = x;
			}		
		}
		min = arr[index];
	   	
		//计算部件得分
		bjScore = ave - (100-min)/t;
		
		return (double)Math.round(bjScore*10)/10;	//保留一位小数
		
		//按照","将json字符串分割成String数组  
//	    String[] keyValue = json.split(",");
//		
//	    for(int i=0; i<keyValue.length; i++) {
//	    	String s = keyValue[i];  
//	    }  
	        
		
	//    return keyValue[0].toString();
		
	//	JSONObject json = new JSONObject(jsonString);
	//	String value = json.getString("balance"); 	  
		
	//	json.put("age", 23);
	//	return value;
		
	}
	
	public static void main(String[] args)
    {
        JSONObject obj = new JSONObject();

        obj.put("gjsum", "3");
        obj.put("gj1","53.5");
        obj.put("gj2", "75");
        obj.put("gj3", "100");
        
        double d = GCT0160(obj.toString());
            
        System.out.println(d);

    }
	
}	
