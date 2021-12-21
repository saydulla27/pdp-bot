package uz.pdp.pdpbot.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "regions")
public class Regions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String regionName;
    private String locationName;
    private Double lat, lon;
    private Long buffer;


    @OneToMany(mappedBy = "regions",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List <User> users;

    @OneToMany(mappedBy = "regions",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AgentWorkShop> agentWorkShops;


}
