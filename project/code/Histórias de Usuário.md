# Histórias de Usuário

## Alunos

### História 1: Cadastro de Aluno
**Como um aluno**, quero me cadastrar no sistema, informando meus dados pessoais (nome, email, CPF, RG, endereço), instituição de ensino e curso, 
**para que** eu possa participar do programa de mérito estudantil e receber moedas virtuais.

#### Critérios de Aceitação:
- O aluno deve selecionar sua instituição de uma lista pré-cadastrada.
- O aluno deve ser capaz de criar um login e uma senha.
- Validação de campos obrigatórios (e.g., CPF e email).

---

### História 2: Receber Notificação
**Como um aluno**, quero ser notificado por email sempre que um professor me enviar moedas, 
**para que** eu saiba que fui reconhecido e acompanhe meu saldo de moedas.

#### Critérios de Aceitação:
- O aluno deve receber um email com o valor das moedas recebidas, o nome do professor e o motivo da premiação.

---

### História 3: Consultar Extrato
**Como um aluno**, quero visualizar meu extrato de moedas, com detalhes de transações, 
**para que** eu possa acompanhar meu saldo e o histórico de operações.

#### Critérios de Aceitação:
- O aluno pode ver o total de moedas no saldo atual.
- O extrato deve mostrar cada transação com data, tipo, e valor.

---

### História 4: Trocar Moedas por Vantagens
**Como um aluno**, quero trocar minhas moedas por produtos e descontos oferecidos, 
**para que** eu possa usufruir das recompensas do meu mérito.

#### Critérios de Aceitação:
- O aluno deve poder selecionar uma vantagem disponível, visualizar os detalhes como descrição, foto e custo, e confirmar a troca.
- O valor correspondente deve ser descontado do saldo do aluno.
- O aluno deve receber um email com um cupom e um código para uso na troca presencial.

---

## Professores

### História 5: Consultar Saldo e Extrato de Moedas
**Como um professor**, quero visualizar o saldo de moedas disponíveis e um extrato de todas as transações realizadas, 
**para que** eu possa acompanhar o que distribui para os alunos.

#### Critérios de Aceitação:
- O professor pode ver o saldo de moedas disponível no semestre.
- O extrato deve incluir transações com data, aluno receptor, valor distribuído e motivo.

---

### História 6: Enviar Moedas aos Alunos
**Como um professor**, quero enviar moedas para um aluno, especificando a quantidade e o motivo, 
**para que** eu possa reconhecer seu desempenho e participação em sala de aula.

#### Critérios de Aceitação:
- O professor deve poder selecionar um aluno cadastrado.
- O professor deve especificar o número de moedas e o motivo do envio.
- O professor deve possuir quantidade suficiente de moedas para realizar a transação.
- A quantidade de moedas deve ser descontada do saldo do professor.
- O aluno deve receber um email de notificação.

---

## Empresas Parceiras

### História 7: Cadastro de Empresa Parceira
**Como uma empresa parceira**, quero me cadastrar no sistema, informando meus dados e as vantagens que ofereço, 
**para que** eu possa disponibilizar produtos e serviços em troca de moedas virtuais.

#### Critérios de Aceitação:
- A empresa deve poder se cadastrar, informando nome, CNPJ, endereço e contato.
- A empresa deve poder criar login e senha.
- A empresa deve ter uma interface para cadastrar as vantagens oferecidas, com nome, descrição, foto e custo em moedas.

---

### História 8: Receber Notificação de Resgate de Vantagem
**Como uma empresa parceira**, quero ser notificada por email quando um aluno trocar moedas por um produto/serviço que ofereço, 
**para que** eu possa preparar o item ou aplicar o desconto.

#### Critérios de Aceitação:
- A empresa deve receber um email com os detalhes da troca (nome do aluno, produto ou serviço escolhido, preço e código gerado pelo sistema).
- O código deve facilitar a conferência da troca no momento do resgate.

---
