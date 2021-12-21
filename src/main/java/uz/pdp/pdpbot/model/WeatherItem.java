package uz.pdp.pdpbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherItem{
	private String icon;
	private String description;
	private String main;
	private int id;
}
