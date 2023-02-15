USE master
GO

USE PropertyManagementDB
GO

INSERT INTO Buildings
	(Address)
VALUES
	(geography::Point(10,10,4120)),
	(geography::Point(1,1,4120))
GO

INSERT INTO Apartments(
	BuildingID,
	Number
)
VALUES
	(1,1),
	(1,2),
	(1,3),
	(1,4),
	(2,1),
	(2,2),
	(2,4)
GO

INSERT INTO PersonTypes(
	Name
)
VALUES
	('Visitor'),
	('Resident'),
	('Staff')
GO

INSERT INTO People(
 FirstName,LastName,IDNumber,PersonTypeID
 )
 VALUES
	('John','Doe','0101105097089',2)
GO

INSERT INTO People(
	FirstName,
	LastName
)
VALUES
	('Joe','Mama'),
	('Harold','Dover'),
	('Mike','Hutch'),
	('Anna','Koblitz'),
	('Bo','Johnson'),
	('Dixon','Smith'),
	('Gabe','Hitchcock'),
	('Hugh','Jackman'),
	('Jen','Cohen')
GO

INSERT INTO ContractTypes(
	Name
)
VALUES
	('Permanent'),
	('Fixed-Term')
GO

INSERT INTO ApartmentTrafficLog(
	PersonID,
	ApartmentID
)
VALUES
	(1,2),
	(2,2),
	(2,2),
	(1,3),
	(2,3),
	(3,1)
GO

INSERT INTO Contracts(
	PersonID,
	ApartmentID,
	ContractTypeID,
	ContractStartDate
)
VALUES
	(1,1,1,GETDATE())
	
INSERT INTO Contracts(
	PersonID,
	ApartmentID,
	ContractTypeID,
	ContractStartDate,
	ContractEndDate
)
VALUES
	(4,3,2,GETDATE(),GETDATE())