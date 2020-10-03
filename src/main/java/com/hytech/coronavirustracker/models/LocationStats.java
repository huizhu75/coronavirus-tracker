package com.hytech.coronavirustracker.models;

public class LocationStats {
	private String state;
	private String country;
	private String combinedKey;
	private int latestTotalCases;
	private int totalCases1daybefore;
	private int totalCases2daybefore;
	private int totalCases3daybefore;
	private int totalCases4daybefore;
	private int totalCases5daybefore;
	private int totalCases6daybefore;
	private int totalCases7daybefore;
	private int totalDeathCase;
	private String lastDate;
	private int diff = 0;
	private int diffLast2 = 0;
	private int diffLast3 = 0;
	private int diffLast4 = 0;
	private int diffLast5 = 0;
	private int diffLast6 = 0;
	private int diffLast7 = 0;
	
	public LocationStats(String state, String country, int latestTotalCases, String lastDate, int diff) {
		super();
		this.state = state;
		this.country = country;
		this.latestTotalCases = latestTotalCases;
		this.lastDate = lastDate;
		this.diff = diff;
	}
	
	
	public String getCombinedKey() {
		return combinedKey;
	}


	public void setCombinedKey(String combinedKey) {
		this.combinedKey = combinedKey;
	}


	public int getTotalDeathCase() {
		return totalDeathCase;
	}


	public void setTotalDeathCase(int totalDeathCase) {
		this.totalDeathCase = totalDeathCase;
	}


	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	public int getDiffLast2() {
		return diffLast2;
	}

	public void setDiffLast2(int diffLast2) {
		this.diffLast2 = diffLast2;
	}

	public int getDiffLast3() {
		return diffLast3;
	}

	public void setDiffLast3(int diffLast3) {
		this.diffLast3 = diffLast3;
	}

	public int getDiffLast4() {
		return diffLast4;
	}

	public void setDiffLast4(int diffLast4) {
		this.diffLast4 = diffLast4;
	}

	public int getDiffLast5() {
		return diffLast5;
	}

	public void setDiffLast5(int diffLast5) {
		this.diffLast5 = diffLast5;
	}
	
	

	public int getTotalCases1daybefore() {
		return totalCases1daybefore;
	}


	public void setTotalCases1daybefore(int totalCases1daybefore) {
		this.totalCases1daybefore = totalCases1daybefore;
	}


	public int getTotalCases2daybefore() {
		return totalCases2daybefore;
	}


	public void setTotalCases2daybefore(int totalCases2daybefore) {
		this.totalCases2daybefore = totalCases2daybefore;
	}


	public int getTotalCases3daybefore() {
		return totalCases3daybefore;
	}


	public void setTotalCases3daybefore(int totalCases3daybefore) {
		this.totalCases3daybefore = totalCases3daybefore;
	}
	
	


	public int getTotalCases4daybefore() {
		return totalCases4daybefore;
	}


	public void setTotalCases4daybefore(int totalCases4daybefore) {
		this.totalCases4daybefore = totalCases4daybefore;
	}


	public int getTotalCases5daybefore() {
		return totalCases5daybefore;
	}


	public void setTotalCases5daybefore(int totalCases5daybefore) {
		this.totalCases5daybefore = totalCases5daybefore;
	}


	public int getTotalCases6daybefore() {
		return totalCases6daybefore;
	}


	public void setTotalCases6daybefore(int totalCases6daybefore) {
		this.totalCases6daybefore = totalCases6daybefore;
	}


	public int getTotalCases7daybefore() {
		return totalCases7daybefore;
	}


	public void setTotalCases7daybefore(int totalCases7daybefore) {
		this.totalCases7daybefore = totalCases7daybefore;
	}


	public int getDiffLast6() {
		return diffLast6;
	}


	public void setDiffLast6(int diffLast6) {
		this.diffLast6 = diffLast6;
	}


	public int getDiffLast7() {
		return diffLast7;
	}


	public void setDiffLast7(int diffLast7) {
		this.diffLast7 = diffLast7;
	}


	@Override
	public String toString() {
		return  state + ", " + country + ", " + latestTotalCases
				+ ", " + lastDate + ", " + diff + ", " + diffLast2 + ", " + diffLast3;
				
	}

	
	
}
