package org.example.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile {

    @Id
    @Column(name = "user_id")
    private Integer user_id;

    private String street;

    private String language;

    @OneToOne
    @PrimaryKeyJoinColumn // mapping exactly to User id, since it is a key
    private User user;

    public void setUser(User user){
        this.user = user;
        user.setProfile(this);
        user_id = user.getId();
    }


}
