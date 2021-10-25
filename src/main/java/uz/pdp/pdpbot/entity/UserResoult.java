package uz.pdp.pdpbot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity()
public class UserResoult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int Baxo;
    private String description;

    @ManyToOne
    private Survey savol;

    @ManyToOne
    private User user;




}
