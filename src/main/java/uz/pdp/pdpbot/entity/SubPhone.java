package uz.pdp.pdpbot.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "phone")
public class SubPhone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Long buffer;


    @OneToMany(mappedBy = "subPhone",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List <User> users;


}
