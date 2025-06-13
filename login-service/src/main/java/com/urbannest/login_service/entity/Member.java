package com.urbannest.login_service.entity;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="member")
@Builder
public class Member {

    private String name;

    private String email;

}

