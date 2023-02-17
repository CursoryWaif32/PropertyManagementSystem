USE master
GO
BEGIN TRY
DROP DATABASE PropertyManagementDB
END TRY
BEGIN CATCH
END CATCH
GO

CREATE DATABASE PropertyManagementDB
GO

USE PropertyManagementDB
GO

CREATE TABLE Buildings(
	BuildingID int IDENTITY(1,1) PRIMARY KEY CLUSTERED NOT NULL,
	Address varchar(255) NOT NULL UNIQUE,

);
GO

CREATE TABLE Apartments(
	Number int NOT NULL,
	BuildingID int NOT NULL FOREIGN KEY REFERENCES Buildings,
	CONSTRAINT uniqueNumberPerBuilding PRIMARY KEY (Number, BuildingID)
);
GO

CREATE TABLE ContractTypes(
	ContractTypeID int IDENTITY(1,1) PRIMARY KEY CLUSTERED NOT NULL,
	Name varchar(50) NULL,
);
GO

CREATE TABLE PersonTypes(
	PersonTypeID int IDENTITY(1,1) PRIMARY KEY CLUSTERED NOT NULL,
	Name varchar(50) NULL,
);
GO

CREATE TABLE People(
	PersonID int IDENTITY(1,1) PRIMARY KEY CLUSTERED NOT NULL,
	FirstName varchar(50) NULL,
	LastName varchar(50) NULL,
	IDNumber char(13) NULL,
	PersonTypeID int NOT NULL FOREIGN KEY REFERENCES PersonTypes DEFAULT 1,
	CONSTRAINT IdIsNumericCheck CHECK ((ISNUMERIC(IDNumber) = 1 OR IDNumber IS NULL)),
);
GO

CREATE TABLE Emails(
	PersonID int FOREIGN KEY REFERENCES People,
	Email varchar(50) NOT NULL PRIMARY KEY CLUSTERED,
);
GO

CREATE TABLE PhoneNumbers(
	PersonID int FOREIGN KEY REFERENCES People,
	PhoneNumber varchar(20) NOT NULL PRIMARY KEY CLUSTERED,
	CONSTRAINT PhoneNumberIsNumericCheck CHECK (ISNUMERIC(PhoneNumber) = 1 ),
);
GO

CREATE TABLE Contracts(
	ContractID int IDENTITY(1,1) PRIMARY KEY CLUSTERED NOT NULL,
	PersonID int FOREIGN KEY REFERENCES People,
	ApartmentNumber int,
	BuildingID int,
	ContractTypeID int NOT NULL FOREIGN KEY REFERENCES ContractTypes,
	ContractStartDate date NOT NULL,
	ContractEndDate date NULL,
	CONSTRAINT startDateConstraint CHECK (ContractStartDate <= GETDATE()),
	CONSTRAINT apartmentForeignKey FOREIGN KEY (ApartmentNumber,BuildingID) REFERENCES Apartments(Number,BuildingID),
);
GO


CREATE TABLE ApartmentTrafficLog(
	ApartmentEntranceLogID int IDENTITY(1,1) PRIMARY KEY CLUSTERED NOT NULL,
	PersonID int FOREIGN KEY REFERENCES People,
	ApartmentNumber int,
	BuildingID int,
	TimeOfEntrance datetime NULL,
	TimeOfExit datetime NULL,
	CONSTRAINT apartment FOREIGN KEY (ApartmentNumber,BuildingID) REFERENCES Apartments(Number, BuildingID),
);
GO
CREATE PROCEDURE uspEndContract
	@ContractID int,
	@EndDate date = NULL
AS
    IF @EndDate IS NULL
        SELECT @EndDate = GETDATE();
	UPDATE Contracts SET ContractEndDate = @EndDate WHERE ContractID = @ContractID
    DECLARE @PersonID  int
    SELECT @PersonID  = PersonID FROM Contracts WHERE ContractID = @ContractID
	UPDATE People SET PersonTypeID=1 WHERE PersonID=@PersonID

GO

CREATE PROCEDURE uspCreateContract
	@PersonID int,
	@ApartmentNumber int,
	@BuildingID int,
	@ContractTypeID int,
	@StartDate date = NULL
AS
	IF @StartDate IS NULL
		SELECT @StartDate = GETDATE();
	UPDATE People SET PersonTypeID = 2 WHERE PersonID = @PersonID
	INSERT INTO Contracts(
		PersonID,
		ContractTypeID,
		ApartmentNumber,
	    BuildingID,
		ContractStartDate
	)
	VALUES
		(@PersonID, @ContractTypeID, @ApartmentNumber, @BuildingID, @StartDate)
GO

CREATE PROCEDURE uspContractChangeApartment
	@ContractID int,
	@NewApartmentNumber int,
	@NewBuildingID int,
	@ChangeDate date = NULL
AS
	DECLARE @ContractType int
    DECLARE @PersonID int
	IF @CHANGEDATE IS NULL
		SELECT @ChangeDate = GETDATE();
	SELECT  @ContractType = ContractTypeID, @PersonID = PersonID FROM Contracts WHERE ContractID = @ContractID
	BEGIN TRANSACTION newContractTransaction;
	BEGIN TRY
	EXEC uspEndContract @ContractID=@ContractID, @EndDate=@ChangeDate
	EXEC uspCreateContract @PersonID=@PersonID, @ApartmentNumber=@NewApartmentNumber, @BuildingID=@NewBuildingID, @ContractTypeID=@ContractType, @StartDate=@ChangeDate
	COMMIT
	END TRY
	BEGIN CATCH
	ROLLBACK
	END CATCH
GO

CREATE FUNCTION udfVisitCount(
	@PersonID int
)
RETURNS int
AS
BEGIN
	DECLARE @visitCount int
	SELECT @visitCount = COUNT(PersonID) FROM ApartmentTrafficLog WHERE PersonID=@PersonID
	RETURN @visitCount	
END
GO

CREATE VIEW vFrequentVisitors
AS
SELECT
	FirstName,
	LastName,
	dbo.udfVisitCount(PersonID) AS TotalVisits
FROM People
WHERE PersonTypeID = 1 AND dbo.udfVisitCount(PersonID) > 0
GO

CREATE VIEW vCurrentResidents
AS
SELECT p.FirstName, p.LastName,a.Number,b.Address
FROM People p
INNER JOIN Contracts c ON p.PersonID = c.PersonID
INNER JOIN Apartments a ON c.ApartmentNumber = a.Number AND c.BuildingID = a.BuildingID
INNER JOIN Buildings b ON a.BuildingID = b.BuildingID
WHERE c.ContractEndDate IS NULL