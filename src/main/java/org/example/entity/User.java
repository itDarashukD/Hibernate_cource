package org.example.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.converter.CustomBirthdayConverter;
import org.hibernate.annotations.BatchSize;

//whole configuration for each relations
@NamedEntityGraph(name = "WithCompany",
        attributeNodes = {@NamedAttributeNode("company"),
                          @NamedAttributeNode(value = "userChats", subgraph = "chats")},
        subgraphs= {@NamedSubgraph(name = "chats",
                                   attributeNodes = @NamedAttributeNode("chat"))})

@EqualsAndHashCode(of = "userName") //if we use exactly Set<User> in Company
@ToString(exclude = {"company", "profile"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // most usefull
    public Integer id;

    @Column(unique = true, nullable = false)
    private String userName;

    private String firstName;

    private String lastname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private Integer age;
    @Enumerated(EnumType.STRING)
    private Role role;

    // instead using age and LacalDate birthday we can create custom type :
    // should create converter : CustomBirthdayConverter
    @Column(name = "custom_birth_date")
    @Convert(converter = CustomBirthdayConverter.class)
    private CustomBirthday customBirthday;

    @Embedded //since it is inner class
    private PersonalInfo personalInfo;

    //    @ManyToOne (cascade = CascadeType.ALL)    //only All can save both types User and Company to DB                     //many users to one company
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    //optional - совпадения для второй сущности при джоине не обязательно должны бытьб Игер - жадная (по-умочанию для сущностей), Lazy - по умолч для Коллекций
    @JoinColumn(name = "company_id")
    // the tables user and company going to join by company_id , company_id == id in Company class
    private Company company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    //to remove and Profile and User in same request, since ot os oneToOne
    private Profile profile;

//    @Builder.Default
//    @ManyToMany
//    @JoinTable(name = "users_chat",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "chat_id"))
//    private List<Chat> chats = new ArrayList<>();
//
//    public void addChat(Chat chat) {
//        chats.add(chat);
//       chat.getUsers().add(this);
//    }

    // if we won't use @ManyToMany but will use 2*OneToMany :
    @Builder.Default
    //то поле userChats будет инициализировано пустым списком (new ArrayList<>()) по умолчанию, если при построении объекта вы явно не установите другое значение для userChats.
    @OneToMany(mappedBy = "user") //in UserChat presrent mapped field user
    private List<UserChat> userChats = new ArrayList<>();

    @BatchSize(size = 3)
    // fetch wired  with User Payment entityes, when calling User from DB banches by 3 element
    @Builder.Default
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();
}
