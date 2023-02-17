package org.example.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "Contracts")
public class Contract {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long contractId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "apartmentID")
  @JsonBackReference
  private Apartment apartment;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "personID")
  @JsonBackReference
  private Person person;


  @ManyToOne
  @JoinColumn(name = "contractTypeID")
  private ContractType contractType;
  private java.sql.Date contractStartDate;
  private java.sql.Date contractEndDate;


  public long getContractId() {
    return contractId;
  }

  public void setContractId(long contractId) {
    this.contractId = contractId;
  }


  public Person getPerson() {
    return person;
  }

  public void setPerson(Person personId) {
    this.person = personId;
  }


  public Apartment getApartment() {
    return apartment;
  }

  public void setApartment(Apartment apartment) {
    this.apartment = apartment;
  }


  public ContractType getContractType() {
    return contractType;
  }

  public void setContractType(ContractType contractTypeId) {
    this.contractType = contractTypeId;
  }


  public java.sql.Date getContractStartDate() {
    return contractStartDate;
  }

  public void setContractStartDate(java.sql.Date contractStartDate) {
    this.contractStartDate = contractStartDate;
  }


  public java.sql.Date getContractEndDate() {
    return contractEndDate;
  }

  public void setContractEndDate(java.sql.Date contractEndDate) {
    this.contractEndDate = contractEndDate;
  }

}
