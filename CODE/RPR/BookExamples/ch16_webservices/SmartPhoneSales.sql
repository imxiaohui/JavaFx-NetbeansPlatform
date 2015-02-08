drop table SalesData;
drop table Company;
drop table ValidationOnly;

/* 
 okay if this statement fails with
 Error code -1, SQL state X0Y68: Schema 'PHONEDATA' already exists.
 */
create schema PhoneData;

create table Company
(CompanyID INTEGER NOT NULL 
                PRIMARY KEY GENERATED ALWAYS AS IDENTITY 
                (START WITH 1, INCREMENT BY 1),
CompanyName varchar(20));

create table SalesData
(SalesID INTEGER NOT NULL 
                PRIMARY KEY GENERATED ALWAYS AS IDENTITY 
                (START WITH 1, INCREMENT BY 1),
CompanyID integer,
SalesYear varchar(10),
UnitsInMillions decimal(7,2),
foreign key (CompanyID)
references Company(CompanyID));

create table ValidationOnly
(Col1 INTEGER NOT NULL, Col2 VARCHAR(15));

insert into Company (CompanyName)
values ('Nokia');
insert into Company (CompanyName)
values ('RIM');
insert into Company (CompanyName)
values ('Apple');
insert into Company (CompanyName)
values ('HTC');
insert into Company (CompanyName)
values ('Samsung');
insert into Company (CompanyName)
values ('Others');

-- // Nokia
-- {new Double(61), new Double(68), new Double(100.1), new Double(77.3)},
-- Nokia SmartPhone Sales Data:
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (1, '2008', 61.0);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (1, '2009', 68.0);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (1, '2010', 100.1);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (1, '2011', 77.3);

-- // ResearchInMotion
-- {new Double(23.1), new Double(37), new Double(48.8), new Double(51.1)},
-- ResearchInMotion SmartPhone Sales Data:
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (2, '2008', 23.1);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (2, '2009', 37);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (2, '2010', 48.8);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (2, '2011', 51.1);

-- // Apple
-- {new Double(11.4), new Double(25), new Double(47.5), new Double(93.2)},
-- Apple SmartPhone Sales Data:
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (3, '2008', 11.4);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (3, '2009', 25);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (3, '2010', 47.5);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (3, '2011', 93.2);

-- // HTC
-- {new Double(5.9), new Double(9), new Double(21.7), new Double(43.5)},
-- HTC SmartPhone Sales Data:
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (4, '2008', 5.9);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (4, '2009', 9);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (4, '2010', 21.7);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (4, '2011', 43.5);
            
-- // Samsung
-- {new Double(4), new Double(7), new Double(22.9), new Double(94)},
-- Samsung SmartPhone Sales Data:
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (5, '2008', 4);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (5, '2009', 7);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (5, '2010', 22.9);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (5, '2011', 94);
             
-- // Others
-- {new Double(33.9), new Double(30), new Double(63.7), new Double(132.3)}
-- Others SmartPhone Sales Data:
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (6, '2008', 33.9);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (6, '2009', 30);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (6, '2010', 63.7);
insert into SalesData (CompanyID, SalesYear, UnitsInMillions)
values (6, '2011', 132.3);


