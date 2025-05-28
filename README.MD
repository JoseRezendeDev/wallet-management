# 💰 Desafio Técnico - API de Carteira

## 📄 Resumo

Esta API é responsável por processar **transações em carteiras de usuários**, controlando o **saldo atual** com base em **valores positivos (crédito)** e **negativos (débito)**.

O sistema já contém dois endpoints para as operações necessárias, **onde os contratos não poderão ser alterados**

---

## 🔗 Endpoints disponíveis

### ✅ Consultar saldo
```bash
curl --location 'http://localhost:8080/amount?userId=1'
```

### ✅ Processar transação
```bash
curl --location 'http://localhost:8080/transaction' \
  --header 'Content-Type: application/json' \
  --data '{
      "userId": 1,
      "amount": 10.0
  }'
```

- O campo `amount` pode ser **positivo (crédito)** ou **negativo (débito)**.
- O parâmetro `userId` representa o usuário (definidos na `InputDataSource`).

---

## 📜 Regras

- ❌ **Não é permitido** adicionar bibliotecas que dependam de recursos externos (bancos de dados, filas, etc.).
- ✅ O projeto **deve rodar e buildar localmente** sem necessidade de imagens Docker ou infraestrutura externa.
- ⚠️ **Não alterar a classe `InputDataSource`** (pode apenas movê-la de local, se necessário).
- ⚠️ Os testes serão feitos **somente com os usuários já presentes** na `InputDataSource`.
- ⚠️ Os **contratos dos endpoints (controllers)** não podem ser alterados (path, parâmetros, payload).
- ✅ As classes internas podem ser **refatoradas ou reorganizadas**, desde que os endpoints mantenham o mesmo comportamento esperado.
- ❌ Carteiras de **débito não podem ficar com saldo negativo**. Caso ocorra, a transação deve ser recusada e uma **mensagem de erro retornada**.
- ✅ Carteiras de **crédito podem ficar com saldo negativo**.

---

## 🧪 O que será avaliado

- Boas práticas e arquitetura de código (ex: separação de responsabilidades, clareza e reutilização)
- Qualidade e viabilidade das soluções propostas
- Capacidade de resolver problemas
- Testes unitários (inclua casos relevantes para sua implementação)
- Commits realizados durante o teste

---

## ⚙️ Testando sua solução

O projeto já contém um script utilitário (`script.sh`) que simula múltiplas transações. Você pode usá-lo para testar as regras de negócio:

```bash
chmod +x script.sh
./script.sh
```

---

## 🚀 Desafio Adicional (opcional)

Caso queira ir além, você pode implementar uma **simulação de envio de SMS** para casos de transações recusadas por falta de saldo.

### Cenário:
- Se uma **transação for recusada** por falta de saldo (débito insuficiente), o sistema deve tentar **notificar o usuário via SMS**.
- O serviço de SMS **conhece apenas o ID do usuário** (não é necessário fornecer número de telefone).

Você pode descrever essa solução em:
- Texto explicativo
- Diagrama de arquitetura
- Código comentado ou simulado (mock)

**Nota**: Este item não será testado automaticamente, mas poderá ser discutido em uma etapa futura do processo seletivo.

---

Boa sorte! 🍀