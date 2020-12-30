expr: expr op term |
      term

term: val |
      '(' expr ')'

val: #'\d+'

op: '+' | '*'