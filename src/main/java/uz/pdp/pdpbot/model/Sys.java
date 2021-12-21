package uz.pdp.pdpbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sys{
	private String country;
	private int sunrise;
	private int sunset;
	private int id;
	private int type;
}
