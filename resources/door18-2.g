mul: add |
     mul '*' add

add: term |
     add '+' term

term: val |
      '(' mul ')'

val: #'\d+'
