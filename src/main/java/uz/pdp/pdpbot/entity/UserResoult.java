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

    private String Ball;
    private String description;
    private long buffer;
    @ManyToOne
    private Survey savol;

    @ManyToOne
    private User user;


}
