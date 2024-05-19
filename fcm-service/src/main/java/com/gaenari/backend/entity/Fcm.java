package com.gaenari.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fcm {

  @Id
  @NotNull
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "fcm_id")
  private Long id;

  @NotNull
  @Column(name = "account_id")
  private String accountId;

  @NotNull
  @Column(name = "fcm_token")
  private String fcmToken;

  public void updateToken(String newToken){
    this.fcmToken = newToken;
  }
}
