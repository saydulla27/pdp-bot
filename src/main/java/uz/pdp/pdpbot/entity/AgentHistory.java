package uz.pdp.pdpbot.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class AgentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String locationName;
    private String  timeON;
    private String  date;
    private String  timeOFF;
    private String  workTime;
    private String  workShopSize;
    private String lateness;
    private Float OnLat, OnLon;
    private Float OffLat, OffLon;
    private Long buffer;


    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
