package com.hytech.coronavirustracker.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hytech.coronavirustracker.models.LocationStats;
import com.hytech.coronavirustracker.service.CoronaVirusDataService;

@Controller
public class HomeController {
	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<LocationStats>  allStats = coronaVirusDataService.getAllStats();
		List<LocationStats>  usStats = coronaVirusDataService.getUsStats();
		List<LocationStats>  prevUsStats = coronaVirusDataService.getPrevUsStats();
		List<LocationStats>  prev2UsStats = coronaVirusDataService.getPrev2UsStats();
		List<LocationStats>  prev3UsStats = coronaVirusDataService.getPrev3UsStats();
		List<LocationStats>  prev4UsStats = coronaVirusDataService.getPrev4UsStats();
		List<LocationStats>  prev5UsStats = coronaVirusDataService.getPrev5UsStats();
		List<LocationStats>  prev6UsStats = coronaVirusDataService.getPrev6UsStats();
		List<LocationStats>  prev7UsStats = coronaVirusDataService.getPrev7UsStats();
		
		List<LocationStats>  usStateStats = new ArrayList<LocationStats>()	;
		
		HashMap<String, LocationStats> usStateStatsMap = new HashMap<String, LocationStats>();
		
		retriveDiffFromPrev(usStateStatsMap, usStats, prevUsStats, prev2UsStats, prev3UsStats, prev4UsStats, prev5UsStats, prev6UsStats, prev7UsStats);
		int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
		
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiff()).sum();
		
		int totalReportedUsCases = allStats.stream().filter(stat -> stat.getCountry().equals("US"))
				.mapToInt(stat -> stat.getLatestTotalCases()).sum();
		int totalWiCases = usStats.stream().filter(stat -> stat.getState().equals("Wisconsin"))
				.mapToInt(stat -> stat.getLatestTotalCases()).sum();
		
		Iterator sit = usStateStatsMap.keySet().iterator();
		while(sit.hasNext()) {
			usStateStats.add(usStateStatsMap.get(sit.next()));
		}
		
		int totalUsDeath = usStateStats.stream().mapToInt(stat -> stat.getTotalDeathCase()).sum();

		//model.addAttribute("locationStats", allStats.stream().filter(stat -> (stat.getLatestTotalCases() > 2000 && stat.getDiff() > 100)).collect(Collectors.toList()));
		model.addAttribute("locationStats", allStats.stream().filter(stat -> (stat.getLatestTotalCases() > 2000 && stat.getDiff() > 100))
				.sorted( (o1, o2) -> o2.getDiff() - o1.getDiff()).collect(Collectors.toList()));
		
		
		model.addAttribute("uslocationStats", usStats.stream().filter(stat -> stat.getLatestTotalCases() > 1000).collect(Collectors.toList()));
		model.addAttribute("wilocationStats", usStats.stream().filter(stat -> stat.getState().equals("Wisconsin") && stat.getLatestTotalCases() > 200)
				.sorted((o1, o2) -> o2.getDiff() - o1.getDiff()).collect(Collectors.toList()));
		
		//model.addAttribute("usStatelocationStats", usStateStats.stream().sorted( (o1, o2) -> o2.getLatestTotalCases() - o1.getLatestTotalCases()).collect(Collectors.toList()));
		model.addAttribute("usStatelocationStats", usStateStats.stream().sorted( (o1, o2) -> o2.getDiff() - o1.getDiff()).collect(Collectors.toList()));
		
		model.addAttribute("totalReportedCases", totalReportedCases);
		model.addAttribute("totalUsDeath", totalUsDeath);
		
		model.addAttribute("totalWiCases", totalWiCases);
		model.addAttribute("totalNewCases", totalNewCases);
		model.addAttribute("totalReportedUsCases", totalReportedUsCases);
		return "home";
	}
	
	private void retriveDiffFromPrev(HashMap<String, LocationStats> usStateStatsMap, List<LocationStats>  usStats, List<LocationStats>  prevUsStats,
			List<LocationStats>  prev2UsStats, List<LocationStats>  prev3UsStats, List<LocationStats>  prev4UsStats, List<LocationStats>  prev5UsStats,
			List<LocationStats>  prev6UsStats, List<LocationStats>  prev7UsStats) {
		
		Iterator<LocationStats> it = usStats.iterator();
		while(it.hasNext()) {
			LocationStats locationStats = it.next();
			updateDiffFromPrev(locationStats, prevUsStats, prev2UsStats, prev3UsStats, prev4UsStats, prev5UsStats, prev6UsStats, prev7UsStats);
			if(usStateStatsMap.get(locationStats.getState()) == null) {
				usStateStatsMap.put(locationStats.getState(), new LocationStats(locationStats.getState(), locationStats.getCountry(), 0,locationStats.getLastDate(), 0));
				
			}
			LocationStats stateLocationStats = usStateStatsMap.get(locationStats.getState());
			stateLocationStats.setDiff(stateLocationStats.getDiff() + locationStats.getDiff() );
			stateLocationStats.setDiffLast2(stateLocationStats.getDiffLast2() + locationStats.getDiffLast2() );
			stateLocationStats.setDiffLast3(stateLocationStats.getDiffLast3() + locationStats.getDiffLast3() );
			stateLocationStats.setDiffLast4(stateLocationStats.getDiffLast4() + locationStats.getDiffLast4() );		
			stateLocationStats.setDiffLast5(stateLocationStats.getDiffLast5() + locationStats.getDiffLast5() );
			stateLocationStats.setDiffLast6(stateLocationStats.getDiffLast6() + locationStats.getDiffLast6() );
			stateLocationStats.setDiffLast7(stateLocationStats.getDiffLast7() + locationStats.getDiffLast7() );
			
			
			stateLocationStats.setTotalDeathCase(stateLocationStats.getTotalDeathCase() + locationStats.getTotalDeathCase() );
			stateLocationStats.setLatestTotalCases(stateLocationStats.getLatestTotalCases() + locationStats.getLatestTotalCases() );
			stateLocationStats.setTotalCases1daybefore(stateLocationStats.getTotalCases1daybefore() + locationStats.getTotalCases1daybefore() );
			stateLocationStats.setTotalCases2daybefore(stateLocationStats.getTotalCases2daybefore() + locationStats.getTotalCases2daybefore() );
			stateLocationStats.setTotalCases3daybefore(stateLocationStats.getTotalCases3daybefore() + locationStats.getTotalCases3daybefore() );		
			stateLocationStats.setTotalCases4daybefore(stateLocationStats.getTotalCases4daybefore() + locationStats.getTotalCases4daybefore() );
			stateLocationStats.setTotalCases5daybefore(stateLocationStats.getTotalCases5daybefore() + locationStats.getTotalCases5daybefore() );
			stateLocationStats.setTotalCases6daybefore(stateLocationStats.getTotalCases6daybefore() + locationStats.getTotalCases6daybefore() );
			stateLocationStats.setTotalCases7daybefore(stateLocationStats.getTotalCases7daybefore() + locationStats.getTotalCases7daybefore() );
		}		
		
	}
	
	private void updateDiffFromPrev(LocationStats locationStats, List<LocationStats>  prevUsStats,
			List<LocationStats>  prev2UsStats, List<LocationStats>  prev3UsStats, List<LocationStats>  prev4UsStats, List<LocationStats>  prev5UsStats,
			List<LocationStats>  prev6UsStats, List<LocationStats>  prev7UsStats) {
		Iterator<LocationStats> it = prevUsStats.iterator();
		
		LocationStats prevStats = findLocationStats(prevUsStats, locationStats.getCombinedKey() );	
		if(prevStats != null) {
			locationStats.setTotalCases1daybefore(prevStats.getLatestTotalCases());
			locationStats.setDiff(locationStats.getLatestTotalCases() - prevStats.getLatestTotalCases() );
		}
		
		LocationStats prev2Stats = findLocationStats(prev2UsStats, locationStats.getCombinedKey() );		
		if(prevStats != null && prev2Stats != null) {
			locationStats.setTotalCases2daybefore(prev2Stats.getLatestTotalCases());
			locationStats.setDiffLast2(prevStats.getLatestTotalCases() - prev2Stats.getLatestTotalCases() );
		}
		
		LocationStats prev3Stats = findLocationStats(prev3UsStats, locationStats.getCombinedKey() );		
		if(prev2Stats != null && prev3Stats != null) {
			locationStats.setTotalCases3daybefore(prev3Stats.getLatestTotalCases());
			locationStats.setDiffLast3(prev2Stats.getLatestTotalCases() - prev3Stats.getLatestTotalCases() );
		}
		
		LocationStats prev4Stats = findLocationStats(prev4UsStats, locationStats.getCombinedKey() );		
		if(prev3Stats != null && prev4Stats != null) {
			locationStats.setTotalCases4daybefore(prev4Stats.getLatestTotalCases());
			locationStats.setDiffLast4(prev3Stats.getLatestTotalCases() - prev4Stats.getLatestTotalCases() );
		}
		
		LocationStats prev5Stats = findLocationStats(prev5UsStats, locationStats.getCombinedKey() );		
		if(prev4Stats != null && prev5Stats != null) {
			locationStats.setTotalCases5daybefore(prev5Stats.getLatestTotalCases());
			locationStats.setDiffLast5(prev4Stats.getLatestTotalCases() - prev5Stats.getLatestTotalCases() );
		}
		
		LocationStats prev6Stats = findLocationStats(prev6UsStats, locationStats.getCombinedKey() );		
		if(prev5Stats != null && prev6Stats != null) {
			locationStats.setTotalCases6daybefore(prev6Stats.getLatestTotalCases());
			locationStats.setDiffLast6(prev5Stats.getLatestTotalCases() - prev6Stats.getLatestTotalCases() );
		}
		
		LocationStats prev7Stats = findLocationStats(prev7UsStats, locationStats.getCombinedKey() );		
		if(prev6Stats != null && prev7Stats != null) {
			locationStats.setTotalCases7daybefore(prev7Stats.getLatestTotalCases());
			locationStats.setDiffLast7(prev6Stats.getLatestTotalCases() - prev7Stats.getLatestTotalCases() );
		}
		
	}
	
	private LocationStats findLocationStats(List<LocationStats>  prevUsStats, String key) {
		Iterator<LocationStats> it = prevUsStats.iterator();
		while(it.hasNext()) {
			LocationStats prevStats = it.next();
			if(prevStats.getCombinedKey().equals(key)) {
				return prevStats;
			}
			
		}
		return null;
	}

}
