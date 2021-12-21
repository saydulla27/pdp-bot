package uz.pdp.pdpbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main{
	private double temp;
	private double tempMin;
	private int humidity;
	private int pressure;
	private double feelsLike;
	private double tempMax;
}
