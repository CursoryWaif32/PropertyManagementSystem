package org.example.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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
  private LocalDateTime contractStartDate;
  private LocalDateTime contractEndDate;


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


  public LocalDateTime getContractStartDate() {
    return contractStartDate;
  }

  public void setContractStartDate(LocalDateTime contractStartDate) {
    this.contractStartDate = contractStartDate;
  }


  public LocalDateTime getContractEndDate() {
    return contractEndDate;
  }

  public void setContractEndDate(LocalDateTime contractEndDate) {
    this.contractEndDate = contractEndDate;
  }

}
