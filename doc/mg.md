

-----

Fragen von Grünwoldt

Alle Kunden ausgeben mit Service den sie gebucht haben

```sql
select CLASTNAME,
       SERVNAME
        from order1
        inner join orderdetail o on order1.OID = o.FK_OID
        left join customer c on order1.FK_CID = c.CID
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

