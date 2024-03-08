package com.twendeno.msauth.jwt;

import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

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

}
