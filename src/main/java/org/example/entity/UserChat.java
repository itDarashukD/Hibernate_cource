package org.example.entity;


import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_chat")
public class UserChat { // usually better to use additional entity with 2*OneToMany instead 1* ManyToMany

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // most usefull
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id") // //in User present mapped field "id"
    private User user;

    public void setUser(User user) {
        this.user = user;
        user.getUserChats().add(this);

    }

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    public void setChat(Chat chat) {
        this.chat = chat;
        chat.getUserChats().add(this);
    }


    private Instant created_at;

    private String created_by;

}
