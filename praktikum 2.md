# Zweites DBA Praktikum

## Aufgabe 1

Erstellen Sie eine relationale Datenbank mit mindestens 12 Tabellen und füllen Sie Ihre Tabellen mit Datensätzen (mind. 10 Datensätze in Elterntabellen und mind. fünf Datensätze pro Kindtabelle)!

```sql
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
    CID         int(5) auto_increment       primary key,
    CFIRSTNAME  varchar(40)                 not null,
    CLASTNAME   varchar(40)                 not null,
    CSALUTATION enum ('Hr.', 'Fr.', 'Div.') not null,
    CEMAIL      varchar(100)                not null,
    CPHONE      varchar(50)                 not null,
    CBIRTHDATE  date                        not null,
    FK_ACCID    int                         not null,
    foreign key (FK_ACCID) references account(accid)

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
    CREATE TABLE order (oid int(6) not null  auto_increment,
                    ODELIVDATE date,
                    OSTATUS enum('In Bearbeitung', 'Offen', 'Erledigt'),
                    FK_CID int(5),
                    FK_BRANCHID int(3),
                    BCOMMENT varchar(256),
                    OCHANGEDATE timestamp,
                    PRIMARY KEY (oid),
                    foreign key (FK_CID) references costumer(CID)
                    );

 # product
    create table product
(
    PRID     int(5)     auto_increment	primary key,
    PRNAME varchar(40)                  not null,
    PRNO   int(11)                      not null,
    PPRICENETTO FLOAT                   not null,
    PRCOMMENT LONGTEXT                  not null,
    PRIMG VARCHAR(255)					not null,
    PRIMGPATH VARCHAR(255)				not null,
    PRIMODIFDATE     timestamp default current_timestamp()	not null

);
 # service
    create table SERVICE
(
    SERVID     int(11)     auto_increment	primary key,
    SERVTYPE VARCHAR(255)                   not null,
    SERVPRICE FLOAT                         not null

);
    
# orderdetails
    create table orderdetail
(
    ODID     int     auto_increment  primary key,
    FK_OID    int                    not null,
    ODAMOUNT   tinyint(3)            not null,
    FK_PRID int(5)                   not null,
    FK_SERVID int(11)   			 not null,
    ODDISCOUNT int(11)               not null,
    ODATE     timestamp default current_timestamp()	 not null,
    foreign key (FK_PRID) references product(prid),
    foreign key (FK_SERVID) references service(FSERVID)
);
 
#MitarbeiterService
#Mitarbeiter
#Lager
#Lieferant
#Rechnung
```

