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
    CREATE TABLE order1 (
        OID int not null  auto_increment primary key  ,
        ODELIVDATE date ,
        OSTATUS enum('In Bearbeitung', 'Offen', 'Erledigt'),
        FK_CID int,
        BCOMMENT varchar(256),
        OCHANGEDATE timestamp,
        foreign key (FK_CID) references costumer(CID)
        );

 # productcategory
    create table productcategory
(
    PCATID     int     auto_increment	primary key,
    PCATNAME varchar(40)                  not null,
    PCATORIGIN enum('Selbst gezüchtet', 'Zukauf')

);
#Lieferant

create table supplier
(
    SUPID     int     auto_increment  primary key,
    SUPNAME   VARCHAR(255)          not null,
    SUPCONTACTNAME   VARCHAR(255)          not null,
    SUPPHONE   VARCHAR(255)          not null,
    SUPEMAIL   VARCHAR(255)          not null,
    SUPWWW   VARCHAR(255)          not null
);
 # product
    create table product
(
    PRID     int     auto_increment	primary key,
    PRNAME varchar(40)                  not null,
    PRAMOUNT int                        not null,
    PPRICENETTO FLOAT                   not null,
    PRIMODIFDATE     timestamp default current_timestamp()	not null,
    FK_PCATID   int                     not null,
    FK_SUPID   int                     not null,
    foreign key (FK_PCATID) references productcategory(PCATID),
    foreign key (FK_SUPID) references supplier(SUPID)

);



 # service
    create table SERVICE
(
    SERVID     int     auto_increment	primary key,
    SERVNAME VARCHAR(255)                   not null,
    SERVPRICE FLOAT                         not null

);

# orderdetails
    create table orderdetail
(
    ODID     int     auto_increment  primary key,
    ODAMOUNT   tinyint           not null,
    FK_OID    int                    not null,
    FK_PRID int                  not null,
    FK_SERVID int         			 not null,
    ODATE     timestamp default current_timestamp()	 not null,
    foreign key (FK_OID) references order1(OID),
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
    ESALARY     FLOAT                       not null
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

#Rechnung
create table invoice
(
    INVID     int     auto_increment  primary key,
    FK_OID    int                    not null,
    INVDATE   date ,
    foreign key (FK_OID) references order1(oid)
);

insert into account(ACCNAME,ACCPWD,ACCTYPE) values
        ('kunde1', 'kunde1pwd', 1),
        ('kunde2', 'kunde2pwd', 1),
        ('kunde3', 'kunde3pwd', 1),
        ('kunde4', 'kunde4pwd', 1),
        ('kunde5', 'kunde5pwd', 1),
        ('kunde6', 'kunde6pwd', 1),
        ('kunde7', 'kunde7pwd', 1),
        ('kunde8', 'kunde8pwd', 1),
        ('kunde9', 'kunde9pwd', 1),
        ('kunde10', 'kunde10pwd', 1),
        ('admin1', 'admin1pwd', 2)
        ;
insert into costumer(CFIRSTNAME,CLASTNAME,CSALUTATION ,CEMAIL,CPHONE,CBIRTHDATE,FK_ACCID) values
      ('Mila',      'Müller',   'Fr.', 'milamueller@icloud.com',    0521654654, '1971-12-08',1),
      ('Emilia',    'Schmidt',  'Fr.', 'emiliaschmidt@verizon.net', 0521545245, '1972-08-13',2),
      ('Leni',      'Schneider','Fr.', 'lenischneider@verizon.net', 052156464,  '1975-08-25',3),
      ('Mira',      'Fischer',  'Fr.', 'mirafischer@aol.com',       0521685745, '1975-09-01',4),
      ('Sophia',    'Weber ',   'Fr.', 'sophiaweber@icloud.com',    0521654821, '1982-08-06',5),
      ('Adam',      'Meyer',    'Hr.', 'adammeyer@msn.com',         0521654826, '1986-08-17',6),
      ('Noah',      'Wagner',   'Hr.', 'noahwagner@icloud.com',     0521729287, '1987-07-28',7),
      ('Felix',     'Becker',   'Hr.', 'felixbecker@outlook.com',   0521569654, '1993-09-21',8),
      ('Leon',      'Schulz',   'Hr.', 'leonschulz@gmail.com',      0521875412, '1996-09-14',9),
      ('Emil',      'Hoffmann', 'Hr.', 'emilhoffmann@sbcglobal.net',0521632548, '2006-10-04',10)
;

insert into adress(FK_CID,ASTREET,ACITY,AFEDSTATE,ACITYCODE,ACOUNTRY)values
       (1, 'Hauptstraße 1', 'Hamburg',  'NRW',  '05996', 'Deutschland' ),
       (2, 'Schulstraße 2', 'Dresden',  'NS',   '60137', 'Deutschland'),
       (3, 'Gartenstr. 3',  'München',  'HE',   '74405', 'Deutschland'),
       (4, 'Bahnhofstr. 4', 'Lübeck',   'BY',   '09610', 'Deutschland'),
       (5, 'Dorfstraße 5',  'Freiburg', 'RP',   '88672', 'Deutschland'),
       (6, 'Bergstraße 6',  'Berlin',   'SH',   '36301', 'Deutschland'),
       (7, 'Birkenweg 7',   'Potsdam',  'SA',   '70427', 'Deutschland'),
       (8, 'Lindenstr. 8',  'Trier',    'TH',   '74057', 'Deutschland'),
       (9, 'Kirchstraße 9', 'Lage',     'BR',   '55899', 'Deutschland'),
       (1, 'Waldstr. 1',    'Herford',  'HB',   '61893', 'Deutschland');

insert into order1(odelivdate, ostatus, fk_cid, bcomment ) value
    ('2022-06-30','Offen',1,'Bitte morgens liefern!'),
    ('2022-07-04','Offen',2,''),
    ('2022-09-14','Erledigt',3,''),
    ('2022-10-11','Erledigt',4,''),
    ('2022-11-29','In Bearbeitung',5,'');



insert into productcategory(pcatname, pcatorigin) values

            ('Schnittblume', 'Zukauf'),
            ('Topfpflanzen', 'Selbst gezüchtet'),
            ('Beetpflanzen', 'Selbst gezüchtet'),
            ('Balkonpflanzen', 'Zukauf'),
            ('Büsche', 'Zukauf')
            ;

insert into supplier(supname, supcontactname, supphone, supemail, supwww) values
    ('Blumen Posch',                            'Elisa Posch',  065464138, 'bestellung@blumen-posch.de',          'www.blumen-posch.de'),
    ('Flower Gmbh Blumenhandel',                'Ayla Bauer',   068565456, 'info@flower-blumenhandel.de',         'www.flower-blumenhandel.de'),
    ('DB Blumeneinzellhandels GmbH & Co. KG',   'Lea Koch',     058646545, 'order@db-blumeneinzellhandels.de',    'www.db-blumeneinzellhandels.de'),
    ('Sperling Blumen Import',                  'Erik Schmitt', 065879634, 'bestellung@sperling.de',              'www.sperling-blumen.de'),
    ('Pflanzen-Discount',                       'Alex Braun',   078465345, 'info@pflanzen-discount.de',           'www.pflanzen-discount.de')
;




insert into product(prname, pramount, ppricenetto, fk_pcatid, fk_supid) values
    ('Gänseblümchen',               99,  0.99,1,1),
    ('Rosen',                       10,  5.00,1,1),
    ('Zamioculcas',                 20,  2.00,2,2),
    ('Bogenhanf',                   5,   3.50,2,2),
    ('Mexikanische Sonnenblume',    10,  9.20,3,3),
    ('Löwenohr',                    10,  5.99,3,3),
    ('Geranie ',                    5,   6.60,4,4),
    ('Petunie ',                    10,  1.99,4,4),
    ('Schmetterlingsflieder',       1,   2.40,5,5),
    ('Lavendelheide',               2,   6.80,5,5)
;

insert into service(servname, servprice) value
    ('Trauerkranz',                     100.00),
    ('gesteckte Arrangement',           200.00),
    ('bepflanzte Trauerschale',         300.00),
    ('Brautstrauß',                     400.00),
    ('Königinnenstrauß',                100.00),
    ('Kutschengesteck',                 200.00),
    ('Autogesteck',                     300.00),
    ('Neuanlage Grabstätte',            400.00),
    ('Grabumrandung mit Heckenpflanzen', 100.00),
    ('Bodendeckende Bepflanzung',       200.00)
;

insert into orderdetail(odamount, fk_oid, fk_prid, fk_servid) value
    (1,2,3,4),
    (5,1,2,3),
    (4,5,1,2),
    (3,4,5,1),
    (2,3,4,5)
;

insert into employee(efirstname, elastname, esalutation, esalary) value
    ('Lio','Krause','Hr.',2000.00),
    ('Emilio','Zimmermann','Hr.',2300.00),
    ('David','Hartmann','Hr.',1900.00),
    ('Ahmad','Schmitz','Hr.',3000.00),
    ('Tom','Hofmann','Div.',3500.00);

insert into employeeService(fk_eid, fk_servid) value
    (1,2),
    (1,3),
    (2,2),
    (3,3),
    (4,4),
    (5,5)
;

insert into invoice(FK_OID, INVDATE) value
    (1,'2022-06-30'),
    (2,'2022-07-04'),
    (3,'2022-09-14'),
    (4,'2022-10-11'),
    (5,'2022-11-29');
```

