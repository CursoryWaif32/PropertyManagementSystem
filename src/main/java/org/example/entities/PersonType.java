package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "PersonTypes")
public class PersonType {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long personTypeId;
  private String name;


  public long getPersonTypeId() {
    return personTypeId;
  }

  public void setPersonTypeId(long personTypeId) {
    this.personTypeId = personTypeId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
