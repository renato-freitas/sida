# Sida
Sistema de Integração de Dados, Semi-Automático.


Esse projeto é parte do Trabalho de Conclusão de Curso Bacharelado em Ciência
da Computação no IFFCE - campus Aracati.

	Aracati, 12 de fevereiro de 2018.

Att,
Renato Freitas.


# Ferramentas Utilizadas
Esse sistema usa as tecnologias Linked Data e Web Semântica para realizar, de forma semi-automática, a integração de datasets.
Ferramentas como: D2RQ-R2RML, SILK, SIEVE, LDIF foram utilizadas para construção do SIDA.

# Especificação baseado no trabalho: 
## Using Linked Data in Data Integration for Maternal and Infant Death Risk of the SUS in the GISSA Project
``` https://doi.org/10.1145/3126858.3131606 ```
1. Selecionar as fontes de dados que alimentarão a aplicação.
2. Extrair e transformar os dados das fontes selecionadas, possivelmente heterogêneos, em grafos RDF.
3. Identificar links semânticos entre as fontes de dados.
4. Combinar e fundir representações do mesmo objeto em fontes distintas numa visão homogeneizada.
5. Realizar consultas parametrizadas a fonte de dados integrada usando o vocabulário da O D e obter o cálculo da probabilidade do risco de óbito-infantil.
