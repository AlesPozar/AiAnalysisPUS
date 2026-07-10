# Database Constraint Naming - Pravilo

## OPIS

```text
<prefix>_<ime_tabele>_<stolpec_ali_odnos_med_njimi>
```

## Prefixi

| Prefix | Pomen                        |
| ------ | ------------------------------ |
| `pk`   | Primary key                    |
| `fk`   | Foreign key                    |
| `uk`   | Unique key / unique constraint |
| `idx`  | Index                          |

## Primeri

```text
pk_analysis
fk_question_analysis
uk_question_analysis_position
idx_question_analysis_id
```

## uk primer

V tabeli `question` želimo preprečiti, da bi se par `(analysis_id, position)` podvojil.

ok:

```text
analysis_id | position
------------|---------
1           | 1
1           | 2
2           | 1
```

ni ok:

```text
analysis_id | position
------------|---------
1           | 1
1           | 1
```

par je podvojen

# Uporabljen primer

/entity/.../Question.java 

in 

api/.../chanelog/db.changelog-master.xml

## Liquibase Primer

```xml
<addUniqueConstraint
        tableName="question"
        columnNames="analysis_id, position"
        constraintName="uk_question_analysis_position"/>
```

## JPA Entity Primer

```java
@Table(
        name = "question",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_question_analysis_position",
                        columnNames = {"analysis_id", "position"}
                )
        }
)
```

primer:

```text
fk_question_analysis
uk_question_analysis_position
```

namesto:

```text
this_question_position_must_be_unique_for_each_analysis
```
