package com.hytech.coronavirustracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hytech.coronavirustracker.models.LocationStats;

@Service
public class CoronaVirusDataService {
	//private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private static String VIRUS_US_DAY_URL = 
			"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/";
	private List<LocationStats> allStats = new ArrayList<LocationStats>();
	
	private List<LocationStats> usStats = new ArrayList<LocationStats>();
	private List<LocationStats> prevUsStats = new ArrayList<LocationStats>();
	private List<LocationStats> prev2UsStats = new ArrayList<LocationStats>();
	private List<LocationStats> prev3UsStats = new ArrayList<LocationStats>();
	private List<LocationStats> prev4UsStats = new ArrayList<LocationStats>();
	private List<LocationStats> prev5UsStats = new ArrayList<LocationStats>();
	private List<LocationStats> prev6UsStats = new ArrayList<LocationStats>();
	private List<LocationStats> prev7UsStats = new ArrayList<LocationStats>();
	

	public List<LocationStats> getPrev4UsStats() {
		return prev4UsStats;
	}

	public void setPrev4UsStats(List<LocationStats> prev4UsStats) {
		this.prev4UsStats = prev4UsStats;
	}

	public List<LocationStats> getPrev5UsStats() {
		return prev5UsStats;
	}

	public void setPrev5UsStats(List<LocationStats> prev5UsStats) {
		this.prev5UsStats = prev5UsStats;
	}

	public List<LocationStats> getPrev6UsStats() {
		return prev6UsStats;
	}

	public void setPrev6UsStats(List<LocationStats> prev6UsStats) {
		this.prev6UsStats = prev6UsStats;
	}

	public List<LocationStats> getPrev7UsStats() {
		return prev7UsStats;
	}

	public void setPrev7UsStats(List<LocationStats> prev7UsStats) {
		this.prev7UsStats = prev7UsStats;
	}

	public List<LocationStats> getPrev3UsStats() {
		return prev3UsStats;
	}

	public void setPrev3UsStats(List<LocationStats> prev3UsStats) {
		this.prev3UsStats = prev3UsStats;
	}

	public List<LocationStats> getPrev2UsStats() {
		return prev2UsStats;
	}

	public void setPrev2UsStats(List<LocationStats> prev2UsStats) {
		this.prev2UsStats = prev2UsStats;
	}

	public List<LocationStats> getPrevUsStats() {
		return prevUsStats;
	}

	public void setPrevUsStats(List<LocationStats> prevUsStats) {
		this.prevUsStats = prevUsStats;
	}

	public List<LocationStats> getAllStats() {
		return allStats;
	}

	public void setAllStats(List<LocationStats> allStats) {
		this.allStats = allStats;
	}


	public List<LocationStats> getUsStats() {
		return usStats;
	}

	public void setUsStats(List<LocationStats> usStats) {
		this.usStats = usStats;
	}

	@PostConstruct
	@Scheduled(cron = "* 50 * * * *")
	public void fetchVirusData() throws IOException, InterruptedException {
		List<LocationStats> newStats = new ArrayList<LocationStats>();
		
		HttpClient client =  HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		System.out.println("fetchVirusData from " + VIRUS_DATA_URL);
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
	//	System.out.println(httpResponse.body());
		
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		//Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		CSVParser parser = CSVParser.parse(csvBodyReader, CSVFormat.EXCEL.withFirstRecordAsHeader());
		List<String> headers = parser.getHeaderNames();
		System.out.println("hearder size:" + headers.size());
		System.out.println("headers=" + headers);
		Iterable<CSVRecord> records = parser.getRecords();
		
		int i = 0;
		int caseGreater100 = 0;
		int caseGreater500 = 0;
		int diffCountGreaterZero = 0;
		int diffCountGreater10 = 0;
		int diffGreater10TotalGreater100 = 0;
		int diffGreater10TotalGreater200 = 0;
		int diffGreater10TotalGreater200US = 0;
		String lastHeader = headers.get(headers.size() -1);
		
		for (CSVRecord record : records) {
			
		    String state = record.get("Province/State");
		    String country = record.get("Country/Region");
		    String lat = record.get("Lat");
		    String longAtt = record.get("Long");
		    int lastRecordIndex = headers.size() -1;
		    String lastRec = record.get(lastRecordIndex);	
		    String prevlastRec = record.get(lastRecordIndex-1);	
		   
		    
		    if(!lastRec.trim().isEmpty() && !lastRec.equals("0")) {
		    	 //System.out.println("record size:" + record.size());
				// System.out.println(record);
				// System.out.println("lastRec=" + lastRec + "|prevlastRec=" + prevlastRec);
			    int diff = Integer.parseInt(lastRec) - Integer.parseInt(prevlastRec);
			    int diffLast2 = Integer.parseInt(record.get(lastRecordIndex - 1)) - Integer.parseInt(record.get(lastRecordIndex - 2));
			    int diffLast3 = Integer.parseInt(record.get(lastRecordIndex - 2)) - Integer.parseInt(record.get(lastRecordIndex - 3));
			    int diffLast4 = Integer.parseInt(record.get(lastRecordIndex - 3)) - Integer.parseInt(record.get(lastRecordIndex - 4));
			    int diffLast5 = Integer.parseInt(record.get(lastRecordIndex - 4)) - Integer.parseInt(record.get(lastRecordIndex - 5));
			    int diffLast6 = Integer.parseInt(record.get(lastRecordIndex - 5)) - Integer.parseInt(record.get(lastRecordIndex - 6));
			    int diffLast7 = Integer.parseInt(record.get(lastRecordIndex - 6)) - Integer.parseInt(record.get(lastRecordIndex - 7));
			  
			    //LocationStats locationStats = new LocationStats(state, country, Integer.parseInt(lastRec), headers.get(record.size() -1 ));
			    LocationStats locationStats = new LocationStats(state, country, Integer.parseInt(lastRec), headers.get(headers.size() -1 ), diff);
			    locationStats.setLastDate(lastHeader);
			    locationStats.setTotalCases1daybefore(Integer.parseInt(prevlastRec));
			    locationStats.setTotalCases2daybefore(Integer.parseInt(record.get(lastRecordIndex - 2)));
			    locationStats.setTotalCases3daybefore(Integer.parseInt(record.get(lastRecordIndex - 3)));
			    locationStats.setTotalCases4daybefore(Integer.parseInt(record.get(lastRecordIndex - 4)));
			    locationStats.setTotalCases5daybefore(Integer.parseInt(record.get(lastRecordIndex - 5)));
			    locationStats.setTotalCases6daybefore(Integer.parseInt(record.get(lastRecordIndex - 6)));
			    locationStats.setTotalCases7daybefore(Integer.parseInt(record.get(lastRecordIndex - 7)));
			    
			    locationStats.setDiffLast2(diffLast2);
			    locationStats.setDiffLast3(diffLast3);
			    locationStats.setDiffLast4(diffLast4);
			    locationStats.setDiffLast5(diffLast5);
			    locationStats.setDiffLast6(diffLast6);
			    locationStats.setDiffLast7(diffLast7);
			    
			    newStats.add(locationStats);
			    i++;
			    if(locationStats.getLatestTotalCases() >= 100) {
			    	caseGreater100++;
			    }
			    if(locationStats.getLatestTotalCases() >= 500) {
			    	caseGreater500++;
			    	//System.out.println(locationStats);
			    }
			    if(diff > 0) {
			    	//System.out.println(locationStats);
			    	diffCountGreaterZero++;
			    }
			    if(diff >= 10) {			    	
			    	diffCountGreater10++;
			    }
			    if(diff >= 10 && locationStats.getLatestTotalCases() >= 100) {
			    	diffGreater10TotalGreater100++;			  
			    }
			    
			    if(diff >= 10 && locationStats.getLatestTotalCases() >= 200) {
			    	diffGreater10TotalGreater200++;
			    	if(locationStats.getCountry().equals("US")) {
			    		diffGreater10TotalGreater200US++;
			    		//System.out.println(locationStats);
			    	}else if(locationStats.getLatestTotalCases() >= 5000) {
			    		//System.out.println(locationStats);
			    	}		    	
			    }
		    }		    
		    
		  //  System.out.println(locationStats);
		}
		
		
		System.out.println("records size =" + i);
		System.out.println("caseGreater100 =" + caseGreater100);
		System.out.println("caseGreater500 =" + caseGreater500);
		System.out.println("diffCountGreaterZero =" + diffCountGreaterZero);
		System.out.println("diffCountGreater10 =" + diffCountGreater10);
		System.out.println("diffGreater10TotalGreater100 =" + diffGreater10TotalGreater100);
		System.out.println("diffGreater10TotalGreater200 =" + diffGreater10TotalGreater200);
		System.out.println("diffGreater10TotalGreater200US =" + diffGreater10TotalGreater200US);
		allStats = newStats.stream().sorted((o1, o2) -> o2.getLatestTotalCases() - o1.getLatestTotalCases()).collect(Collectors.toList());		
	}
	
	@PostConstruct
	@Scheduled(cron = "* 5 * * * *")
	public void processDailyVirusData() throws IOException, InterruptedException {
		int day = 0;
		usStats = fetchDailyVirusData(day);
		if(usStats.size() == 0) {
			day--;
			usStats = fetchDailyVirusData(day);
		}
		prevUsStats = fetchDailyVirusData(day - 1 );
		prev2UsStats = fetchDailyVirusData(day - 2 );
		prev3UsStats = fetchDailyVirusData(day - 3);	
		prev4UsStats = fetchDailyVirusData(day - 4 );
		prev5UsStats = fetchDailyVirusData(day - 5 );
		prev6UsStats = fetchDailyVirusData(day - 6);
		prev7UsStats = fetchDailyVirusData(day - 7);
	}
	
	public List<LocationStats> fetchDailyVirusData(int day) throws IOException, InterruptedException {
		List<LocationStats> newStats = new ArrayList<LocationStats>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, day);
		String formatedToday = sdf.format(cal.getTime());
		
		//"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/03-25-2020.csv";
		String todayUrl = VIRUS_US_DAY_URL +  formatedToday + ".csv";
		System.out.println("todayUrl=" + todayUrl);
		
		HttpClient client =  HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(todayUrl))
				.build();
		HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
	//	System.out.println(httpResponse.body());
		
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		//Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		CSVParser parser = CSVParser.parse(csvBodyReader, CSVFormat.EXCEL.withFirstRecordAsHeader());
		List<String> headers = parser.getHeaderNames();
		System.out.println("hearder size:" + headers.size());
		System.out.println("headers=" + headers);
		Iterable<CSVRecord> records = parser.getRecords();
		int i = 0;
		
		
		for (CSVRecord record : records) {
			//FIPS,Admin2,Province_State,Country_Region,Last_Update,Lat,Long_,Confirmed,Deaths,Recovered,Active,Combined_Key
		    String state = record.get("Province_State");
		    String country = record.get("Country_Region");
		    String combinedKey = record.get("Combined_Key");
		    
		    int confirmedCount = Integer.parseInt(record.get("Confirmed"));
		    int deathCount = Integer.parseInt(record.get("Deaths"));
		    //int recoveredCount = Integer.parseInt(record.get("Recovered"));
		    //int activeCount = Integer.parseInt(record.get("Active"));
		    
		    		    
		    if(confirmedCount > 0 && country.equals("US")) {
		    	 //System.out.println("record size:" + record.size());
			//	 System.out.println(record);
				
			    LocationStats locationStats = new LocationStats(state, country, confirmedCount, formatedToday , 0);
			    locationStats.setTotalDeathCase(deathCount);
			    locationStats.setCombinedKey(combinedKey);
			    
			    newStats.add(locationStats);
			    i++;
			   
		    }	    
		}				
		System.out.println("fetchDailyVirusData records size =" + i);
		return newStats.stream().sorted((o1, o2) -> o2.getLatestTotalCases() - o1.getLatestTotalCases()).collect(Collectors.toList());
				
	}
	

}
