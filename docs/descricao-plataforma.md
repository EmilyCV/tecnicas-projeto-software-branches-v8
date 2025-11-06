# Atores 

## Cliente

Um cliente pode realizar as seguintes tarefas:
    - Comprar um pacote de viagem existente
    - Gerenciar seus dados pessoais
        - Visualizar 
        - Atualizer 
    - Verificar o histório de compras passadas
    - Visualizar os pacotes de viagens cadastrados

## Administrador

Um administrador pode fazer as seguintes tarefas
    - Gerenciar localidades
        - Criar
        - Atualizar
        - Visualizar 
        - Deletar
    - Gerenciar pacotes de viagem
        - Criar
        - Atualizar
        - Visualizar 
        - Deletar
    - Visualizar os pacotes de viagens cadastrados
    - Adicionar politicas de desconto à pacotes de viagem
    - Verificar o histório de venda de pacotes

## Atendente

Um atendente pode fazer as seguintes tarefas
    - Visualizar os pacotes de viagens cadastrados
    - Verificar o histório de venda de pacotes


# Entidade e dados

## Cliente

Um cliente tem os seguintes dados:

- Nome
- Data de Nascimento
- Email
- Telefone
- Endereco
    - CEP
    - Localidade
    - Logradouro
    - Número
    - Complemento

## Contração

- Uma contratação é um registro de contratacao de pacote de viagem por um cliente. É composta pelos seguintes dados:
    - Cliente
    - Pacote
    - Data da contratacao
    - Valor pago
    - Pagamento
    - Data de Ida
    - Data de Volta
    - Número de pessoas

## Pagamento

- A entidade de pagamento representa o registro de pagamento de uma contratação. É composta pelos seguintes dados:
    - ID do pagamento
    - Data do pagamento
    - Valor pago


## Localidade

A localidade representa uma cidade. Uma localidade é composta pelos seguintes dados:

- Nome da cidade
- Estado

## Pacote

Um pacote é composto dos seguintes dados:

- Nome
- Localidade
- Data de Inicio
- Data de fim
- Disponibilidade (informa o número de pacotes que podem ser vendidos em uma determinada data. Não é possivel vender um numero maior de pacotes que o disponivel numa determinada data)
- Desconto 
- Items
- Preço (é a soma do preços de seus items, mais um desconto aplicado)

### Items de Pacote

Cada pacote pode ter três items de pacote. Um item de pacote pode ser um dos três tipos definidos a seguir:

#### Hotel

Representa uma reserva de hotel, sendo composto pelos seguintes dados:

- Nome do hotel
- Endereco
    - CEP
    - Localidade
    - Logradouro
    - numero
    - Complemento
- Preco

#### Translado Aereo

Representa uma reserva de voo numa companhia area, sendo composto pelos seguintes dados:

- Nome da companhia area
- Voo de IDA
    - Numero
    - Data
    - Numero do assento
- Voo de Volta
    - Numero
    - Data
    - Numero do assento
- Preco

#### Alugel de carro

Representa uma reserva de carro numa locadora de carro. É composto pelos seguintes dados:

- Nome da locadora
- Categoria do carro
- Preco


# Casos de Uso

## Compra de pacote

1. No momento da contratacao de pacote deve ser verificado a disponibilidade do pacote no dia escolhido pelo cliente. Não deve ser possivel vender um número maior de pacote do a disponibilidade de pacote para aquela data.
2. Deve ser disparado requisicoes para os sistemas de rede hoteleira, companhia area e locação de carros no momento da contratacao do pacote. O codigo desses sistemas não fazem parte do escopo
3. Ao fim, deve ser feita uma chamada ao serviço de pagamentos do stripe do valor total do pacote. O resultado deve ser guardado em um objeto do tipo pagamento

# Tecnologias

Deve ser usado as seguintes tecnologias
- Spring boot
- Banco de dados relacionais (mysql)
- Liquibase (incluido no modulo de API)
- JPA
- API Rest 

> [!NOTE]
> Nao faz parte do escopo desse projeto a criação do front-end
