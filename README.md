
# MS-FEATURE-FLAG

Está é uma aplicação de referência para um microsserviço que implementa feature-flag com as seguintes funcionalidades:




## Funcionalidades

- CRUD de feature flag
- CRUD de Filtro para Feature Flag (mplementa conceito de ALLOW LIST e DENY LIST)
- Testes A/B: permite criação de uma feature com um proporção de descip para funcionalidade A ou B
- Migração: permite a criação de uma Feature para migrar gradualmente de uma versão de funcionalidade para outra baseada em uma lista de IDs, quantidade de empresas a migrar e porcentagem de migrações para aquela quantidade.


## Stack utilizada

**Back-end:** Java 21, Spring Boot 3.2, Docker Compose, Redis 6.0.7, Swagger.


## Documentação da API

#### CRUD de funcionalidades permite o cadastro, consulta, alteração e deleção de feature flag.

```http
  POST GET PUT DELETE (ver Swagger)
```

```javascript
{
  "featureId": "SKDKEHIEGHKDFKSFS",
  "name": "Banner da Promoção",
  "description": "Este banner deve ser exibido no app mobile durante a semana do dia das mães",
  "category": "marketing",
  "type": "banner",
  "status": true,
  "applicationId": "mobileapp",
  "defaultStateFlag": true,
  "userId": "CPF9893489459454",
  "companyId": "cKSKFGISKHKFKSF",
  "filterId": "dennylist001",
  "filterType": "COMPANYID",
  "dateTimeBegin": "01/05/2024 00:00:00",
  "dateTimeEnd": "15/05/2024 23:59:00",
  "testAB": false,
  "probabilityToAllow": 0.00,
  "migration": false,
  "totalOfCompanies": 0,
  "migrationPercentage": 0.00,
  "modifiedAt": "30/04/2024 08:05:32"
}
```

#### CRUD de filtro de funcionalidades. Funciona como uma Allow List ou Denny Lista. Permite lista de IDs únicos de usuários USERID ou Ids únicos de empresas COMPANYID.

```http
  POST GET PUT DELETE (ver Swagger)
```

```javascript
{
  "filterId": "EFFBVONEKFEKFEKFE",
  "filterName": "Lista de Empresas que não devem ter acesso",
  "filterType": "COMPANYID",
  "allowAll": false,
  "filterList": [
    "CNPJ01",
    "CNPJ02",
    "CNPJ0N..."
  ],
  "modifiedAt": "30/04/2024 08:06:78"
}
```

#### Consulta de feature flag. Está é a requisição que a aplicação cliente faz para saber a lista de funcionalidades que podem ser executadas/exibidas.

```http
  GET /v1/featureflag/query
```

REQUEST:

```javascript
{
  "applicationId": "webmobile01",
  "userId": "CPF01",
  "companyId": "CNPJ01
}
```

RESPONSE:

```javascript
{
  "applicationId": "KWINFEIFSFBSF",
  "userId": "CPF01",
  "companyId": "CNPJ01",
  "filterApplied": true,
  "filterAppliedIds": [
    "WKBEGIODVIADEWGW"
  ],
  "featureIds": [
    "feature01"
  ],
  "success": true,
  "errorMessage": ""
}
```

## Uso/Exemplos

### Para acessa Swaguer

```javascript
http://localhost:8080/
```


## Documentação

### Testes A/B

Para criar uma FEATURE FLAG para teste AB, na POST da criação da feature indique TRUE para o campo testeAB e informe a porcentágem de TRUE para exibir a funcionaliade no campo probabilittyToAllow. O valor deve ser um float entre 0.0 e 1.0. Exemplo: 0.3 indica que a funcionalidade em questão vai ser permitida (allow) em 30% das chamadas e negada em 70% das chamadas:

```javascript
  "testAB": true,
  "probabilityToAllow": 0.30,
```

### Migrações

Para utilizar a feature como MIGRAÇÃO de uma funcionalidade de uma versão antiga pra uma nova utilize os campos:
- migration: true para indicar que esta feature é uma migração.
- totalOfCompanies: indica o número total de empresas (IDs ùnicos) que deseja que sejam migradas.
- migrationPercentage: indica a percentagem de empresas com base no índice anterior que devem ser migradas.

Exemplo 1: Se desejamos migrar um total de 1000 empreasa de nossa base de clientes e a primeira leva deve ser de 500 clientes então configuramos a funcionalidade conforme descrito abaixo:

```javascript
  "migration": true,
  "totalOfCompanies": 1000,
  "migrationPercentage": 0.50,
```

Neste caso cada vez que a aplicação cliente fizer um request no sistema o mesmo vai verificar a lista de migrações pelo ID único da compania e se está ainda não foi migrada vai migrar a mesma e adicionar seu ID único na lista de já migradas desde que não exceda as 500 companias da primeira leva de migração (50% de 1000 companias). Assim se a empresa deve migrar a feature flag aparece na lista, caso contrário não aparece.


Exemplo 2: agora que já migramos a primeira leva com sucesso, modificamos a feature flag para a seguinte configuração:

```javascript
  "migration": true,
  "totalOfCompanies": 1000,
  "migrationPercentage": 1.00,
```

Agora, o sistema identificara mais 500 companias que ainda não estão na lista de migrações e promoverá a migração das 500 novas empreasas (50% restantes dos 1000).
## Autor

- [@elcioabrahao](https://www.github.com/elcioabrahao)

