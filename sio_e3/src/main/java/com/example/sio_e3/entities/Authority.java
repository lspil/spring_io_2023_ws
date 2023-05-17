package com.example.sio_e3.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class Authority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;

  @ManyToOne
  @JoinColumn(name = "user")
  private User user;
}
