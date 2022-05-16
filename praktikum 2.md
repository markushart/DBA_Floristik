# Zweites DBA Praktikum

## Aufgabe 1

Erstellen Sie eine relationale Datenbank mit mindestens 12 Tabellen und füllen Sie Ihre Tabellen mit Datensätzen (mind. 10 Datensätze in Elterntabellen und mind. fünf Datensätze pro Kindtabelle)!

```sql
drop database floristik;
create database floristik;
use floristik;
# account
    create table account
(
    ACCID   int auto_increment				primary key,
    ACCNAME varchar(255) 					not null,
    ACCPWD  varchar(255) 					not null,
    ACCTYPE int          					not null
);
# costumer
create table costumer(
    CID         int      auto_increment       primary key,
    CFIRSTNAME  varchar(40)                 not null,
    CLASTNAME   varchar(40)                 not null,
    CSALUTATION enum ('Hr.', 'Fr.', 'Div.') not null,
    CEMAIL      varchar(100)                not null,
    CPHONE      varchar(50)                 not null,
    CBIRTHDATE  date                        not null,
    FK_ACCID    int                         not null,
    foreign key (FK_ACCID) references account(accid)
    );

# adress
    create table adress
(
    ADRID     int     						auto_increment primary key,
    FK_CID    int     						not null,
    ASTREET   varchar(50)                   not null,
    ACITY     varchar(50)                   not null,
    AFEDSTATE enum ('NRW', 'NS', 'HE', 'BY', 'BW', 'RP', 'SH', 'SA', 'TH', 'BR', 'HB', 'SL', 'MVP', 'BLN') not null,
    ACITYCODE varchar(10)                   not null,
    ACOUNTRY  varchar(30)                   not null,
    ADATE     timestamp default current_timestamp()	not null,
    foreign key (FK_CID) references costumer(cid)
);
# order
    CREATE TABLE order1 (OID int not null  auto_increment,
                    ODELIVDATE date,
                    OSTATUS enum('In Bearbeitung', 'Offen', 'Erledigt'),
                    FK_CID int,
                    BCOMMENT varchar(256),
                    OCHANGEDATE timestamp,
                    PRIMARY KEY (oid),
                    foreign key (FK_CID) references costumer(CID)
                    );

 # product
    create table product
(
    PRID     int     auto_increment	primary key,
    PRNAME varchar(40)                  not null,
    PRAMOUNT int,
    PPRICENETTO FLOAT                   not null,
    PRIMODIFDATE     timestamp default current_timestamp()	not null

);

 # productcategory
    create table productcategory
(
    PCATID     int     auto_increment	primary key,
    PCATNAME varchar(40)                  not null,
    PCATORIGIN enum('Schnittblumen', 'Topfpflanzen', 'Beet/Balkonpflanzen'),
    FK_SUPID int not null,
    foreign key (FK_SUPID)references supplier(SUPID)

);




 # service
    create table SERVICE
(
    SERVID     int     auto_increment	primary key,
    SERVTYPE VARCHAR(255)                   not null,
    SERVPRICE FLOAT                         not null

);

# orderdetails
    create table orderdetail
(
    ODID     int     auto_increment  primary key,
    FK_OID    int                    not null,
    ODAMOUNT   tinyint           not null,
    FK_PRID int                  not null,
    FK_SERVID int         			 not null,
    ODATE     timestamp default current_timestamp()	 not null,
    foreign key (FK_PRID) references product(prid),
    foreign key (FK_SERVID) references service(SERVID)
);

#Mitarbeiter
# employee
create table employee(
    EID         int auto_increment       primary key,
    EFIRSTNAME  varchar(40)                 not null,
    ELASTNAME   varchar(40)                 not null,
    ESALUTATION enum ('Hr.', 'Fr.', 'Div.') not null,
    EEMAIL      varchar(100)                not null,
    EPHONE      varchar(50)                 not null,
    EBIRTHDATE  date                        not null,
    FK_ACCID    int                         not null,
    foreign key (FK_ACCID) references account(accid)
    );

#MitarbeiterService
create table employeeService
(
    ESID     int     auto_increment  primary key,
    FK_EID    int                    not null,
    FK_SERVID int                   not null,
    foreign key (FK_EID) references employee(eid),
    foreign key (FK_SERVID) references service(SERVID)
);



#Lieferant

create table supplier
(
    SUPID     int     auto_increment  primary key,
    SUPNAME   VARCHAR(255)          not null,
    SUPCONTACTNAME   VARCHAR(255)          not null,
    SUPPHONE   VARCHAR(255)          not null,
    SUPEMAIL   VARCHAR(255)          not null,
    SUPWWW   VARCHAR(255)          not null,
    FK_ADRID int                   not null,
    foreign key (FK_ADRID) references adress(ADRID)
);


```

