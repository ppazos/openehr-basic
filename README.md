# openehr-basic

Sample openEHR application to learn design and development concepts

NOTE: this is an updated version of the Grails 2.x app https://github.com/ppazos/openEHR-skeleton and here we updated to Grails 5.x

# Queries

## sistolica de cualquier documento

```sql
select q.magnitude, q.units
from dv_quantity q, item i
where i.type = 'ELEMENT' AND
      i.value_id = q.id AND
      i.path = '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]';
```

## incluyendo nombre

```sql
select t.value, q.magnitude, q.units
from dv_quantity q, item i, dv_text t
where i.name_id = t.id AND
      i.type = 'ELEMENT' AND
      i.value_id = q.id AND
      i.path = '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]';
```

## dentro de un documento específico

```sql
select t.value, q.magnitude, q.units
from dv_quantity q, item i, dv_text t
where i.parent_id = 1 AND
      i.name_id = t.id AND
      i.type = 'ELEMENT' AND
      i.value_id = q.id AND
      i.path = '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]';
```

## dentro de un EHR específico

```sql
select t.value, q.magnitude, q.units
from dv_quantity q, item i, dv_text t, document d
where d.ehr_id = 1 AND
      i.parent_id = d.id AND
      i.name_id = t.id AND
      i.type = 'ELEMENT' AND
      i.value_id = q.id AND
      i.path = '/content[archetype_id=openEHR-EHR-OBSERVATION.presion_arterial.v1]/data[at0001]/events[at0002]/data[at0003]/items[at0004]';
```
