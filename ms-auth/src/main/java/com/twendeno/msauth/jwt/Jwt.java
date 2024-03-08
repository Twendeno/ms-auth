package com.twendeno.msauth.jwt;

import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.refreshToken.RefreshToken;
import com.twendeno.msauth.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Jwt extends AbstractEntity {

    private boolean disable ;
    private boolean expired ;
    private String value;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name = "user_uuid")
    private User user;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private RefreshToken refreshToken;

}
