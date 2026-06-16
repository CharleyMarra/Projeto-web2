# 🚀 GamerCatalog Engine


### 🌐 Informações Institucionais
* **Entidade de Ensino:** Instituto Federal Goiano (IFG) — Campus Urutaí
* **Escopo Acadêmico:** Disciplina de Programação Web II
* **Equipe:** Ana Lia, Charley Junior e João Pedro

---

# 📋 Sumário

- [Descrição do Problema](#-descrição-do-problema)
- [Requisitos e Casos de Uso](#-requisitos-e-casos-de-uso)
- [Diagrama de Classes](#-diagrama-de-classes)
- [Diagrama Entidade-Relacionamento (DER)](#-diagrama-entidade-relacionamento-der)
- [Projeto e Arquitetura](#-projeto-e-arquitetura)
- [Implementação](#-implementação)
- [Conclusão](#-conclusão)
- [Informações Adicionais](#-informações-adicionais)

---

# 📌 Descrição do Problema

O ecossistema contemporâneo de jogos digitais expandiu-se de tal forma que a catalogação e o acompanhamento de dados tornaram-se fragmentados. Usuários lidam com dispersão ao tentar concentrar em um único local seus históricos de consumo, títulos favoritos, conquistas adquiridas e notas pessoais. 

Por outro lado, para analistas de dados ou aplicações que demandam informações consolidadas, a ausência de uma base relacional unificada que ligue desenvolvedores (*Estúdios*), distribuidores (*Publishers*), expansões (*DLCs*) e consoles cria barreiras de integração.

A **GamerCatalog API** resolve esse problema centralizando esses domínios em uma plataforma digital unificada. O sistema mitiga a redundância de dados, fornece paginação performática para catálogos massivos e oferece aos entusiastas uma estrutura organizada para a manutenção e consulta de dados essenciais do universo gamer.

---

# ✅ Requisitos e Casos de Uso

### Requisitos Funcionais (User Stories & Escopo)

| Código | Descrição de Escopo Técnico |
| :--- | :--- |
| **RF01** | Ciclo completo de CRUD (Cadastro, leitura, atualização e deleção) de Usuários. |
| **RF02** | Catalogação detalhada de jogos digitais contendo metadados essenciais. |
| **RF03** | Mapeamento e associação flexível de títulos para múltiplas Plataformas de hardware. |
| **RF04** | Rastreabilidade e vínculo de jogos aos seus respectivos Estúdios e Publishers. |
| **RF05** | Mecanismo de pontuação indexada (Avaliações numéricas por título). |
| **RF06** | Sistema de fórum e críticas em texto puro (Comentários) por jogo. |
| **RF07** | Criação e manutenção de listas personalizadas de Jogos Favoritos por conta. |
| **RF08** | Inclusão e versionamento de mídias de expansão (DLCs) atreladas ao ID do jogo. |
| **RF09** | Sistema de gamificação através do registro de Conquistas/Troféus. |
| **RF10** | Algoritmo de entrega de dados otimizado com paginação dinâmica e ordenação. |
| **RF11** | Disponibilização de ambiente sandbox interativo para testes via Swagger UI. |

### Restrições e Requisitos Não Funcionais (Quality Attributes)

| Código | Métrica / Atributo de Qualidade |
| :--- | :--- |
| **RNF01** | **Conformidade REST:** Uso obrigatório de respostas padronizadas e semântica de verbos HTTP. |
| **RNF02** | **Linguagem Base:** Construção em Java 21 explorando os novos recursos da linguagem. |
| **RNF03** | **Persistência:** Uso de ORM acoplado ao Spring Data JPA para proteção contra SQL Injection. |
| **RNF04** | **Sanitização de Dados:** Uso obrigatório do padrão DTO mediado pelo compilador MapStruct. |
| **RNF05** | **Modularidade:** Divisão física rígida de pacotes em Controller, Service e Repository. |
| **RNF06** | **Performance de Rede:** Bloqueio de queries em lote sem o uso de paginação estruturada. |

### Casos de Uso Principais

* **UC01 — Manter Conta de Usuário:** O usuário realiza operações cadastrais e altera seus dados na plataforma.
* **UC02 — Catalogar Jogo:** Administradores criam jogos amarrando-os obrigatoriamente a uma plataforma, estúdio e publicadora.
* **UC03 — Interagir com Catálogo:** O usuário cria avaliações ou emite comentários detalhados sobre as experiências jogadas.
* **UC04 — Expandir Títulos:** O sistema acopla conteúdos de DLCs e listas de conquistas indexadas diretamente ao ID de um Jogo raiz.

---

# 🧩 Diagrama de Classes

<img width="2000" height="1414" alt="Design sem nome" src="https://github.com/user-attachments/assets/19cfa3d1-165e-4e5c-9c26-9935109c57a6" />


# 🗄️ Diagrama Entidade-Relacionamento (DER)

<img width="1536" height="768" alt="WhatsApp Image 2026-06-15 at 19 39 30" src="https://github.com/user-attachments/assets/b87f9964-d648-47b5-b07a-5c841301e956" />


# 🏗️ **Projeto e Arquitetura**
A aplicação foi projetada sob o paradigma de Separação de Preocupações (SoC), utilizando um fluxo desacoplado onde as entidades de banco nunca são expostas diretamente na camada de transporte (HTTP).

| Classificação | Componente / Entidade | Descrição Técnica e Escopo no Domínio |
| :--- | :--- | :--- |
| **Raiz do Agregado** | `Jogo` | Entidade centralizadora do catálogo. Gerencia as regras de negócio de mídias e serve de âncora relacional para DLCs e Conquistas. |
| **Entidade Core** | `Usuario` | Representa a conta persistida do jogador na plataforma, atuando como o nó central para interações sociais e listas pessoais. |
| **Entidade Core** | `Plataforma` | Catálogo estrutural que mapeia os ecossistemas de hardware (ex: PC, PlayStation, Xbox, Nintendo Switch) aos quais os jogos pertencem. |
| **Entidade Core** | `Estudio` | Registro do time de desenvolvimento responsável pela criação e codificação da obra digital. |
| **Entidade Core** | `Publisher` | Entidade corporativa responsável pela distribuição comercial, licenciamento e publicação do título no mercado. |
| **Sub-Recurso** | `Dlc` | Extensão direta de conteúdo (expansão, cosméticos) cujo ciclo de vida e existência dependem obrigatoriamente de um ID de `Jogo` ativo. |
| **Sub-Recurso** | `Conquista` | Mapeamento de conquistas, conquistas e troféus atrelados a desafios específicos dentro do escopo de jogabilidade de um título. |
| **Nó de Interação** | `Comentarios` | Registro histórico de críticas textuais e fórum opinativo enviado por um `Usuario` na página de um `Jogo`. |
| **Nó de Interação** | `Avaliacao` | Registro de métricas e notas numéricas de performance atribuídas pelos consumidores para qualificar a experiência do título. |
| **Tabela de Junção** | `Favoritos` | Componente relacional que atua como ponte de referência cruzada dinâmica entre as chaves primárias de `Usuario` e `Jogo`. |
| **Value Object** | `Preco` | Objeto de valor embarcado (`@Embedded`) na entidade `Jogo`. Não possui identidade própria e serve para validar de forma isolada regras de moeda e valores monetários. |

## **STACK TECNOLOGIA**
 
| Componente | Core da Solução | Detalhes do Escopo |
| :--- | :--- | :--- |
| **Runtime Core** | Java 21 / Spring Boot | Plataforma principal de microsserviços. |
| **Persistência** | Spring Data JPA / Hibernate | Abstração da camada de dados relacional. |
| **Engine de Dados** | MySQL Server | Armazenamento de alta integridade. |
| **Data Mapping** | MapStruct | Conversão em tempo de compilação (Entidade ↔ DTO). |
| **Doc Interface** | OpenAPI 3.0 / Swagger UI | Especificação interativa de rotas. |


# ⚙️ **Implementação:**

A GamerCatalog API foi desenvolvida em Java 21 utilizando Spring Boot, Spring Data JPA, MapStruct e MySQL, seguindo uma arquitetura em camadas composta por Controller, Service, Repository, Model, DTO e Mapper.

Os controllers disponibilizam os endpoints REST para gerenciamento de usuários, jogos, plataformas, estúdios, publishers, avaliações, comentários, favoritos, DLCs e conquistas. As regras de negócio são tratadas na camada Service, enquanto a persistência dos dados é realizada pelos Repositories através do Spring Data JPA.

Para aumentar a segurança e reduzir o acoplamento, a aplicação utiliza DTOs e MapStruct para conversão entre entidades e objetos de transferência de dados.

Também foi implementada paginação com Pageable nas consultas, melhorando o desempenho em listagens de grandes volumes de dados. Além disso, a API possui documentação interativa gerada com Swagger/OpenAPI, facilitando testes e integração com outros sistemas.

# **📝 Conclusão**
O desenvolvimento da GamerCatalog API entrega uma infraestrutura robusta focado no gerenciamento centralizado do setor de jogos. A arquitetura implementada comprova que o desacoplamento de software através de DTOs e Mappers protege o domínio contra anomalias de exposição externa.

Adicionalmente, o uso estratégico de paginação nativa (Pageable) nas listagens resolve o gargalo de consumo excessivo de memória em repositórios corporativos. O resultado obtido consolida um Produto Mínimo Viável (MVP) resiliente, escalável e de fácil portabilidade para futuros front-ends web ou mobile.
