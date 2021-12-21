package uz.pdp.pdpbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String state, fullName, nameShop,  phoneNumber,brand,dayRegion,shopOrienter;
    private long chatId;
    private long buffer;
    private String operatingModeON;
    private String operatingModeOFF;
    private Double lat, lon;
    private boolean active;
    private int history;




    @ManyToOne(fetch = FetchType.LAZY)
    private Regions regions;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubPhone subPhone;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AgentHistory> agentHistories;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AgentWorkShop> agentWorkShops;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<AgentPlane> agentPlanes;

}
