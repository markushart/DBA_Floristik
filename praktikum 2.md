# Zweites DBA Praktikum

## Aufgabe 1

Erstellen Sie eine relationale Datenbank mit mindestens 12 Tabellen und füllen Sie Ihre Tabellen mit Datensätzen (mind. 10 Datensätze in Elterntabellen und mind. fünf Datensätze pro Kindtabelle)!



```sql
drop database floristik;
create database floristik;
use floristik;
# 1. account
    create table account
(
    ACCID   int auto_increment				primary key,
    ACCNAME varchar(255) 					not null,
    ACCPWD  varchar(255) 					not null,
    ACCTYPE int          					not null
);
# 2. costumer
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

# 3. adress
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
# 4. order
    CREATE TABLE order1 (
        OID int not null  auto_increment primary key  ,
        ODELIVDATE date ,
        OSTATUS enum('In Bearbeitung', 'Offen', 'Erledigt'),
        FK_CID int,
        BCOMMENT varchar(256),
        OCHANGEDATE timestamp,
        foreign key (FK_CID) references costumer(CID)
        );

 # 5. productcategory
    create table productcategory
(
    PCATID     int     auto_increment	primary key,
    PCATNAME varchar(40)                  not null,
    PCATORIGIN enum('Selbst gezüchtet', 'Zukauf')

);
# 6. Lieferant

create table supplier
(
    SUPID     int     auto_increment  primary key,
    SUPNAME   VARCHAR(255)          not null,
    SUPCONTACTNAME   VARCHAR(255)          not null,
    SUPPHONE   VARCHAR(255)          not null,
    SUPEMAIL   VARCHAR(255)          not null,
    SUPWWW   VARCHAR(255)          not null
);
 # 7. product
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



 # 8. service
    create table SERVICE
(
    SERVID     int     auto_increment	primary key,
    SERVNAME VARCHAR(255)                   not null,
    SERVPRICE FLOAT                         not null

);

# 9. orderdetails
    create table orderdetail
(
    ODID     int     auto_increment  primary key,
    ODAMOUNT   tinyint           not null,
    FK_OID    int                    not null,
    FK_PRID int                  ,
    FK_SERVID int         			 ,
    ODATE     timestamp default current_timestamp()	 not null,
    foreign key (FK_OID) references order1(OID),
    foreign key (FK_PRID) references product(prid),
    foreign key (FK_SERVID) references service(SERVID)
);


# 10. employee
create table employee(
    EID         int auto_increment       primary key,
    EFIRSTNAME  varchar(40)                 not null,
    ELASTNAME   varchar(40)                 not null,
    ESALUTATION enum ('Hr.', 'Fr.', 'Div.') not null,
    ESALARY     FLOAT                       not null
    );

# 11. MitarbeiterService
create table employeeService
(
    ESID     int     auto_increment  primary key,
    FK_EID    int                    ,
    FK_SERVID int                   ,
    foreign key (FK_EID) references employee(eid),
    foreign key (FK_SERVID) references service(SERVID)
);

#12. Rechnung
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
      ('Sophia',    'Weber',   'Fr.', 'sophiaweber@icloud.com',    0521654821, '1982-08-06',5),
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
    ('2022-07-04','Offen',1,''),
    ('2022-09-14','Erledigt',3,''),
    ('2022-10-11','Erledigt',4,''),
    ('2022-11-29','In Bearbeitung',5,''),
    ('2022-11-30','Erledigt',6,''),
    ('2022-11-30','In Bearbeitung',6,'');



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
    (1,2,3,NULL),
    (5,1,2,NULL),
    (4,5,1,NULL),
    (3,4,5,NULL),
    (2,3,4,NULL),
    (4,6,3,NULL),
    (4,7,6,NULL)
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

## Aufgabe 2

1. Führen Sie über das SQL-Kommando SELECT die folgenden SQL-Abfragen in der Tabelle product durch:

   1. Sortieren Sie die Datensätze mit einer SELECT-Anweisung nach Namen und Typ (ORDER BY - Klausel)!

      ```sql
      select * from product order by PRNAME,FK_PCATID ; # Wenn zuerst nach Name und dann nach Typ (macht weniger Sinn)
      select * from product order by FK_PCATID,PRNAME ; # Zuerst Typ und dann nach Namen sortiert
      ```

      

   2. Selektieren Sie ==nacheinander==? (!) die Namen der Produkte (Alias: Produktname) und Einzelpreis mit einem Einzelpreis zwischen Preis 1 bis Preis 2 (WHERE - Klausel mit logischer AND-Verknüpfung) und den Produktnummern zwischen unterer und oberer Grenze!

      ```sql
      select PRNAME as Produktname, PPRICENETTO from product where PPRICENETTO between 3.5 and 6.5;
      ```

      

      

2. Führen Sie die folgenden SQL-Abfragen als Verbundabfrage über die Tabellen order und customer durch:

   1. Ermitteln Sie Anzahl der Bestellungen eines Kunden (Alias: Anzahl Bestellungen für einen Kunden) (COUNT - Funktion) und geben Sie diese und den Namen des Kunden (Alias: Kunde) aus!

      ```sql
      select count(FK_CID) as Anzahl , concat(CLASTNAME,', ',CFIRSTNAME) as Kunde from order1,costumer where CID=3 and FK_CID=CID;
      ```

      

   2. Ermitteln Sie die Namen aller Kunden (Alias: Kundenname) und die Anzahl der Bestellungen für jeden Kunden (Alias: Anzahl) und Gruppieren Sie die Ergebnisse nach der Anzahl (COUNT und GROUP BY - Klausel)!

      ```sql
      select concat(CLASTNAME,', ',CFIRSTNAME) as Kunde, count(FK_CID) as Anzahl from order1,costumer where FK_CID=CID group by FK_CID;
      ```

      

   3. Ermitteln Sie den Kundennamen (Alias: Kunde) und 

      die Anzahl der Bestellungen für ein konkretes Produkt (via FK_PRID) für diesen Kunden (Alias: Anzahl) (COUNT und GROUP BY, INNER JOIN - Klausel)!
   
      ```sql
      select concat(CLASTNAME,', ',CFIRSTNAME) as Kunde,
             count(FK_CID)  as Anzahl
              from order1
          inner join costumer on order1.FK_CID = costumer.CID
          inner join orderdetail o on order1.OID = o.FK_OID
          where FK_PRID=3
          group by FK_CID;
      ```
   
      

3. Führen Sie die folgenden SQL-Abfragen ggf. als Verbundabfrage über die Tabellen order, customer, orderdetail und product durch:

   1. Ermitteln Sie die Produktnamen (Alias: Produkt) für alle Produkttypen (Alias: Produkttyp) aller Einträge in der Tabelle product.

      ```sql
      select PRNAME as Produkt from product;
      ```

      

   2. Ermitteln Sie die Summe aller Produkte in der Tabelle product.

      ```sql
      select count(PRNAME)  from product;
      ```

      

   3. Ermitteln Sie den Kundennamen (Alias: Kunde), 

      die bestellten Produkte (Alias: Produktname), 

      das Lieferdatum und 

      den jeweiligen Bestellpreis für einen bestimmten Kunden über alle Bestellungseinträge.

      ```sql
      select CLASTNAME as Kunde,
             PRNAME as Produkt,
             ODELIVDATE as Lieferdatum,
              PPRICENETTO as Preis
             from order1
             inner join costumer c on order1.FK_CID = c.CID
             inner join orderdetail o on order1.OID = o.FK_OID
             inner join product p on o.FK_PRID = p.PRID
             where cid=6
      ```

      

   4. Welche Produkte wurden noch nicht bestellt?

      ```sql
      select PRNAME as Produkt
             from order1
             inner join orderdetail o on order1.OID = o.FK_OID
             right join product p on o.FK_PRID = p.PRID
              where OID is null;
      ```

      

   5. Ermitteln Sie die Anzahl an Bestellungen für jedes Produkt!

      ```sql
      select  PRNAME as Produkt,
              sum(ODAMOUNT) as Anzahl
             from product
              left join orderdetail o on product.PRID = o.FK_PRID
              group by Prid;
      ```





-----

Fragen von Grünwoldt

Alle Kunden ausgeben mit Service den sie gebucht haben

```sql
select CLASTNAME,
       SERVNAME
        from order1
        inner join orderdetail o on order1.OID = o.FK_OID
        left join costumer c on order1.FK_CID = c.CID
        left join service s on o.FK_SERVID = s.SERVID
        where FK_SERVID is not null ;
```

Wie oft alle Kunden einen bestimmten Service gekauft haben

```sql
select SERVNAME,
       sum(ODAMOUNT)
        from order1
        inner join orderdetail o on order1.OID = o.FK_OID
        left join service s on  o.FK_SERVID=s.SERVID
        where FK_SERVID is not null
        group by FK_SERVID;
```

