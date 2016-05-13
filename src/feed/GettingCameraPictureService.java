package feed;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

 




import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

 


import po.NodeInformationBean;
import po.NodeTypeBean;

public class GettingCameraPictureService {
	private SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat   sDateFormatDetail   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	private  List<String> picAddressList = new ArrayList<String>();
	
	public List<String> getCameraPicturesByIp(String ipAddress) {
		
		 
		 List<String> picAddressListtemp= new ArrayList<String>();
		 String startDate="";
		 String endDate="";
		 Date now=new Date();
		    try {
				//sDateFormatDetail.parse((sDateFormat.format(now)+" 00:00:00")) ;
		    	startDate=sDateFormat.format(now)+" 00:00:00";
		    	endDate=sDateFormatDetail.format(now);
		    	picAddressListtemp=	getCameraPicturesByIpAndDate(ipAddress,startDate,endDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		 
		return picAddressListtemp;
		//return picAddressList;
	}
	
	public List<String> getCameraPicturesByIpAndDate(String ipAddress,String startdate,String enddate) {
		
		picAddressList.clear();
	//	 System.out.println("getCameraPicturesByIpAndDate"+startdate+","+enddate);
        String realPath ="";
        realPath=new File(System.getProperty("catalina.home")).toString().replace('\\','/')+"/cameraPic";
        
		List<File> listSearch =new ArrayList<File>();
		// 获得文件夹下的文件
		File root = new File(realPath);
		if (!root.exists()) {
			return null;
		}
 
		List<File> listFile = Arrays.asList(root.listFiles());
		try { 
			Date startDate=sDateFormatDetail.parse(startdate);
			Date endDate=sDateFormatDetail.parse(enddate);
				for (int i = 0; i < listFile.size(); i++) {
					File file = listFile.get(i);
					String a[]=file.getName().split("_");
					if (a[0].equals(ipAddress)&&new Date(file.lastModified()).after(startDate)&& new Date(file.lastModified()).before(endDate)) {
						 picAddressList.add(file.getName());
			
					}
				}
			} catch (Exception e1) {
				return null;
			}
 
		return picAddressList;
	}

}
