# Controle de Colaboradores

Este é um projeto desenvolvido em *Spring Boot* para o gerenciamento de colaboradores dentro de uma empresa. O sistema conta com autenticação de usuários, onde *administradores* podem gerenciar outros usuários e colaboradores.

## 🚀 Tecnologias Utilizadas

- *Spring Boot*
- *Banco de Dados H2*
- *Maven*

## 📌 Funcionalidades

### *1️⃣ Autenticação de Usuários*
- Login com usuário e senha
- Dois tipos de usuários:
  - *Admin*: Pode cadastrar, editar e remover outros usuários e colaboradores.
  - *Usuário comum*: Consulta e edita informações.

### *2️⃣ Gerenciamento de Funcionários*
- Criar, editar e excluir funcionários
- Cada funcionário possui um *cargo*
- Os cargos estão vinculados a um *departamento*

### *3️⃣ Estrutura Organizacional*
- Um *funcionário* tem:
  - Nome
  - Cargo
  - Departamento
  - Status (Ativo/Inativo)
- Um *cargo* tem:
  - Nome
  - Departamento vinculado
- Um *departamento* tem:
  - Nome
  - Lista de cargos pertencentes