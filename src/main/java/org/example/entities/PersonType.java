package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "PersonTypes")
public class PersonType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long personTypeId = 1;
  private String name = "Visitor";

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
