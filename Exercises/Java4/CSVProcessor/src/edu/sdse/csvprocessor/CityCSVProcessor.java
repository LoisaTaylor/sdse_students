package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class CityCSVProcessor {
	
	public void readAndProcess(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			//Discard header row
			br.readLine();
			
			String line;
			Map<String, List<CityRecord>> recordsByCity = new HashMap<String, List<CityRecord>>();
			
			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");
				
				int id = convertToInt(rawValues[0]);
				int year = convertToInt(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToInt(rawValues[3]);
				
				// NEW - add cityRecord 
				CityRecord record = new CityRecord(id, year, city, population);
				//System.out.println(record.toString());
				
				if (recordsByCity.get(record.city) != null){
					List<CityRecord> oldRecords = recordsByCity.get(record.city);
					oldRecords.add(record);
					recordsByCity.put(record.city, oldRecords);
				}
				else {
					List<CityRecord> allRecords = new ArrayList<CityRecord>();
					allRecords.add(record);
					recordsByCity.put(record.city, allRecords);
				}
			}
			
			for (Entry<String, List<CityRecord>> entry : recordsByCity.entrySet()) { 
				List<CityRecord> recordsOfCity = entry.getValue();
	
				int numberOfEnteries = 0;
				int minimumYear = 3000;
				int maximumYear = 0;
				int averagePopulation = 0;
				
				for (CityRecord record : recordsOfCity) {
					numberOfEnteries ++;
					averagePopulation += record.population;
					if (record.year > maximumYear) {
						maximumYear = record.year;
					}
					if (record.year < minimumYear) {
						minimumYear = record.year;
					}
				}
				
				averagePopulation = averagePopulation / numberOfEnteries;
				System.out.format("Average population of %s (%d-%d; %d entries): %d \n", entry.getKey(), minimumYear,maximumYear,numberOfEnteries,averagePopulation);
			}
			
			
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}
	
	private String cleanRawValue(String rawValue) {
		return rawValue.trim();
	}
	
	private int convertToInt(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		return Integer.parseInt(rawValue);
	}
	
	private String convertToString(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		
		return rawValue;
	}
	
	public static void main(String[] args) {
		CityCSVProcessor reader = new CityCSVProcessor();
		//File dataDirectory = new File("../../../../data/");
		File dataDirectory = new File("data/");
		File csvFile = new File(dataDirectory, "Cities.csv");
		
		reader.readAndProcess(csvFile);
	}
}
