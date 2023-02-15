package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Contracts")
public class Contract {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long contractId;
  private long personId;
  private long apartmentId;
  private long contractTypeId;
  private java.sql.Date contractStartDate;
  private java.sql.Date contractEndDate;


  public long getContractId() {
    return contractId;
  }

  public void setContractId(long contractId) {
    this.contractId = contractId;
  }


  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }


  public long getApartmentId() {
    return apartmentId;
  }

  public void setApartmentId(long apartmentId) {
    this.apartmentId = apartmentId;
  }


  public long getContractTypeId() {
    return contractTypeId;
  }

  public void setContractTypeId(long contractTypeId) {
    this.contractTypeId = contractTypeId;
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
