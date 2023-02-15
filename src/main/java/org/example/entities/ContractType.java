package org.example.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ContractTypes")
public class ContractType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long contractTypeId;
  private String name;



  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
