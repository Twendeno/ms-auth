package com.twendeno.msauth.userBusiness.entity;

import com.twendeno.msauth.business.entity.Business;
import com.twendeno.msauth.shared.model.AbstractEntity;
import com.twendeno.msauth.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "user_business")
public class UserBusiness extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "business_uuid", referencedColumnName = "uuid", nullable = false)
    private Business business;
}
