package org.jay;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App 
{
	public static final Integer MENU_NAME = 0;
	public static final Integer MENU_PRICE = 2;
	
	public static final Integer SEQUENCE = 0;
	public static final Integer MARKET_NAME = 1;
	public static final Integer APPROVAL_DATE = 2;
	public static final Integer APPROVAL_TIME = 3;
	public static final Integer MEMBER_NAME = 4;
	public static final Integer TEAM_NAME = 5;
	public static final Integer ITEMS = 6;
	public static final Integer APPROVAL_AUTHORITY = 7;
	public static final Integer PRICE = 8;
	public static final Integer APPROVAL_GROUP = 9;
	public static final Integer APPROVAL_NUMBER = 10;
	
	public static final String MENU_PATH = "/Volumes/Data/Dropbox/backup/menu_recent.xlsx";
	public static final String POS_DATE = "20180303";
	public static final String DAILY_POS_PATH = "/Users/pukekakaster/Downloads/list_"+POS_DATE+".xls";
	
    @SuppressWarnings("resource")
	public static void main( String[] args ) throws Exception
    {
    	
    	/*
    	 * 여기서부터 메뉴 파싱하기
    	 */
    	HashMap<String , Integer> menu = new HashMap<String , Integer>();
    	FileInputStream menu_file=new FileInputStream(MENU_PATH);
    	
    	int menu_rowindex=0;
    	
    	XSSFWorkbook menu_workbook=new XSSFWorkbook(menu_file);
    	XSSFSheet menu_sheet=menu_workbook.getSheetAt(0);
    	int menu_rows=menu_sheet.getPhysicalNumberOfRows();
    	
    	//System.out.println(menu_rows);
    	
    	for(menu_rowindex=0; menu_rowindex<menu_rows; menu_rowindex++){
    		XSSFRow menu_row=menu_sheet.getRow(menu_rowindex);
    		if(menu_row !=null){
    			
    			String menu_name = menu_row.getCell(MENU_NAME).getStringCellValue();
    			Double menu_price = menu_row.getCell(MENU_PRICE).getNumericCellValue();
    			menu.put(menu_name, menu_price.intValue());
    		}
    	}
    	
    	//System.out.println(menu.size());
    	/*
    	 * 메뉴 파싱 완료
    	 */
    	
    	/*
    	 * 여기서부터 일별 매출파싱하기
    	 */
    	
    	FileInputStream fis=new FileInputStream(DAILY_POS_PATH);
    	HSSFWorkbook workbook=new HSSFWorkbook(fis);
    	
    	List<Transaction> transactionlist = new ArrayList<Transaction>();
    	
    	int rowindex=0;
    	
    	HSSFSheet sheet=workbook.getSheetAt(0);
    	int rows=sheet.getPhysicalNumberOfRows();
    	
    	for(rowindex=1; rowindex<rows-1; rowindex++){
    		HSSFRow row=sheet.getRow(rowindex);
    		if(row !=null){
    			
    			Transaction tra = new Transaction();
    			
    			tra.setSeq(Integer.parseInt(row.getCell(SEQUENCE).getStringCellValue()));
 
    			tra.setMarket_name(row.getCell(MARKET_NAME).getStringCellValue());
    			
    			String dateString = row.getCell(APPROVAL_DATE).getStringCellValue() + " " + row.getCell(APPROVAL_TIME).getStringCellValue();
    			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    			Date date = transFormat.parse(dateString);
				tra.setApproval_date(date);
				
				tra.setMember_name(row.getCell(MEMBER_NAME).getStringCellValue());
				tra.setTeam_name(row.getCell(TEAM_NAME).getStringCellValue());
				
    			String items = row.getCell(ITEMS).getStringCellValue();
    			StringTokenizer st = new StringTokenizer(items,",");
    			List<Item> itemlist = new ArrayList<Item>();
    			while(st.hasMoreTokens()){
    				
    				Item item = new Item();
    				String itemname_count = st.nextToken();
    				StringTokenizer st2 = new StringTokenizer(itemname_count,"[\\(\\)]");
    				
    				if(st2.countTokens() ==1){
    					item.setName(st2.nextToken());
    					item.setCount(1);
    					//1 count
    				}
    				
    				if(st2.countTokens()==2){
    					item.setName(st2.nextToken());
    					item.setCount(Integer.parseInt(st2.nextToken()));
    					//multiple count
    				}
    				
    				itemlist.add(item);
    			}
    			
    			tra.setItems(itemlist);
    			tra.setApproval_authority(row.getCell(APPROVAL_AUTHORITY).getStringCellValue());
    			tra.setPrice(Integer.parseInt(row.getCell(PRICE).getStringCellValue().replaceAll("[^0-9]", "")));
    			tra.setApproval_group(row.getCell(APPROVAL_GROUP).getStringCellValue());
    			tra.setApproval_number(Double.parseDouble(row.getCell(APPROVAL_NUMBER).getStringCellValue()));
    			
    			transactionlist.add(tra);
    		}
    	}
    	
    	/*
    	 * 여기까지가 그날그날의 매출 파싱해서 transactionlist 에 담기
    	 */
    	
    	//아이템 단가랑 맞지 않는것 찾기 
    	
    	
    	
    	/*
    	 *  여기서부터 위아래 행 제외하고 갯수 맞는지 확인 후, tab 구분자로 파일 생성하기
    	 */
    	
    	
    	//item 별로 리스트에 넣기
    	if((rows-2) == transactionlist.size()){
    		
    		List<Item> totalitemlist = new ArrayList<Item>();
    		List<Item> cancelitemlist = new ArrayList<Item>();
    		List<Item> forceitemlist = new ArrayList<Item>();
    		
    		for (int i=0; i < transactionlist.size(); i++){
    			
    			
    			Transaction temptr = transactionlist.get(i);
    			
    			if(temptr.getApproval_authority().equals("바코드") && temptr.getApproval_group().equals("승인")){
    				
    				
    				Integer checkprice = 0;
    				
    				for(int j=0; j<temptr.getItems().size(); j++){
    					
    					String itemname = temptr.getItems().get(j).getName();
    					Integer itemcount = temptr.getItems().get(j).getCount();
    					
    					checkprice = checkprice + (menu.get(itemname) * itemcount);
        				totalitemlist.add(temptr.getItems().get(j));
        			}
    				
    				if(!checkprice.equals(temptr.getPrice())){
    					System.out.println(temptr.getSeq()+"_Error!!");
    					checkprice = 0;
    				}else{
    					checkprice = 0;
    				}
    					
    				
    				
    			}
    			
    			if(temptr.getApproval_authority().equals("바코드") && temptr.getApproval_group().equals("승인취소")){
    				for(int j=0; j<temptr.getItems().size(); j++){
        				cancelitemlist.add(temptr.getItems().get(j));
        			}
    			}
    			
    			if(temptr.getApproval_authority().equals("강제인증")){
    				for(int j=0; j<temptr.getItems().size(); j++){
        				forceitemlist.add(temptr.getItems().get(j));
        			}
    			}
    			
    			
    		}
    		
    		
    		//lastitem 해쉬맵에 <아이템,갯수> 로 넣기
    		HashMap<String , Integer> lastitem = new HashMap<String , Integer>();
    		HashMap<String , Integer> clastitem = new HashMap<String, Integer>();
    		HashMap<String, Integer> forceitem = new HashMap<String, Integer>();
    		
    		//승인아이템들...
    		for(int i=0; i<totalitemlist.size(); i++){
    			
    			String itemname = totalitemlist.get(i).getName();
    			Integer itemcount = totalitemlist.get(i).getCount();
    			
    			if(lastitem.get(itemname) != null){
    				
    				Integer totalcount = lastitem.get(itemname)+itemcount;
    				lastitem.put(itemname, totalcount);

    			}else{
    				lastitem.put(itemname, itemcount);
    			}
    			 
    		}
    		
    		//승인취소아이템들...
    		for(int i=0; i<cancelitemlist.size(); i++){
    			
    			String itemname = cancelitemlist.get(i).getName();
    			Integer itemcount = cancelitemlist.get(i).getCount();
    			
    			if(clastitem.get(itemname) != null){
    				
    				Integer totalcount = clastitem.get(itemname)+itemcount;
    				clastitem.put(itemname, totalcount);

    			}else{
    				clastitem.put(itemname, itemcount);
    			}
    			 
    		}

    		//강제인증아이템들....
    		for(int i=0; i<forceitemlist.size(); i++){
    			
    			String itemname = forceitemlist.get(i).getName();
    			Integer itemcount = forceitemlist.get(i).getCount();
    			
    			if(forceitem.get(itemname) != null){
    				
    				Integer totalcount = forceitem.get(itemname)+itemcount;
    				forceitem.put(itemname, totalcount);

    			}else{
    				forceitem.put(itemname, itemcount);
    			}
    			 
    		}
    		
    		
    		
    		//System.out.println(lastitem.get("핫도그O"));
    		
    		// 메뉴 가격과 아이템 갯수 맵핑해서 탭구분자로 파일 만들기 (lastitem)
    		String fileName = "/Users/pukekakaster/Downloads/item_"+POS_DATE+".txt" ;
    		BufferedWriter fw = new BufferedWriter(new FileWriter(fileName, true));
    		
    		//승인아이템들...
    		Set<Entry<String, Integer>> set = lastitem.entrySet();
    		Iterator<Entry<String, Integer>> it = set.iterator();
    		
    		while (it.hasNext()) {
    			Map.Entry<String, Integer> e = (Map.Entry<String, Integer>)it.next();
    			String in = e.getKey();
    			Integer count = e.getValue();
    			Integer price = menu.get(in);
    			
    			fw.write(POS_DATE+"\t"+in+"\t"+count+"\t"+price+"\t"+price*count+"\t"+"승인"+"\n");
    		}
    		
    		//승인취소아이템들...
    		Set<Entry<String, Integer>> cset = clastitem.entrySet();
    		Iterator<Entry<String, Integer>> cit = cset.iterator();
    		
    		while (cit.hasNext()) {
    			Map.Entry<String, Integer> ce = (Map.Entry<String, Integer>)cit.next();
    			String cin = ce.getKey();
    			Integer ccount = ce.getValue();
    			Integer price = menu.get(cin);
    			
    			fw.write(POS_DATE+"\t"+cin+"\t"+ccount+"\t"+price+"\t"+(-price)*ccount+"\t"+"승인취소"+"\n");
    		}
    		
    		//강제인증아이템들...
    		Set<Entry<String, Integer>> fset = forceitem.entrySet();
    		Iterator<Entry<String, Integer>> fit = fset.iterator();
    		/*
    		while (fit.hasNext()) {
    			//Map.Entry<String, Integer> fe = (Map.Entry<String, Integer>)fit.next();
    			String fin = "강제인증요청";
    			Integer fcount = 1;
    			Integer price = 0;
    			
    			fw.write(POS_DATE+"\t"+fin+"\t"+fcount+"\t"+price+"\t"+(price)*fcount+"\t"+"강제인증"+"\n");
    		}
    		*/
    		fw.flush();
            fw.close(); 
            System.out.println("완료!!!");
    		
    	}else{
    		System.out.println("파싱에 오류가 있습니다.");
    	}
    	
    	
    	
    	
    }
}
