package com.example.client.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class VerificationToken {

    //Expiration time is 10 mins
    private static final int  EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private Date exipirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
    nullable = false,
    foreignKey = @ForeignKey(name="FK_USER_VERIFY_TOKEN")
    )
    private User user;


    public VerificationToken(User user,String token){
        super();
        this.token = token;
        this.user = user;
        this.exipirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public VerificationToken(String token){
        super();
        this.token = token;
        this.exipirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(int expirationTime){
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);

        return new Date(calendar.getTime().getTime());
    }

}
