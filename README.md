# Sistema de Administração de Condomínios - Vista Alegre

## 📋 Descrição do Projeto

Sistema completo para gerenciamento de condomínios desenvolvido em Java, aplicando os princípios de Programação Orientada a Objetos (POO). O sistema permite o controle de moradores, apartamentos, visitantes, reservas de áreas comuns, pagamentos e manutenção.

**Desenvolvido para:** Trabalho da P2 - Programação Orientada a Objetos  
**Condomínio:** Vista Alegre

---

## 🎯 Funcionalidades Implementadas

### ✅ Cadastro e Gerenciamento
- **Moradores**: Cadastro com nome, documento, telefone, quantidade de pets e associação a apartamentos
- **Apartamentos**: Registro de número, bloco e vagas de garagem
- **Visitantes**: Controle de entrada e saída com histórico completo

### ✅ Reservas de Áreas Comuns
- Reserva de Academia, Piscina e Salão de Festas
- Limite de 8 horas por reserva
- Verificação automática de conflitos de horário
- Cancelamento com política de multa (menos de 48h antes)
- Persistência em arquivo
- Consulta de disponibilidade por período

### ✅ Sistema Financeiro
- Registro de pagamentos de condomínio
- Cálculo automático de multas para pagamentos atrasados (2%)
- Data de vencimento automática (dia 10 de cada mês)
- Controle de status: pendente, pago, atrasado, cancelado
- Efetivação de pagamentos
- Verificação automática de atrasos ao abrir o menu
- Relatórios financeiros detalhados

### ✅ Manutenção e Suporte
- Abertura de chamados para manutenção
- Controle de status: Aberto, Em Andamento, Fechado
- Registro de custos
- Histórico completo com datas de abertura e fechamento
- Listagem por status
- Persistência de chamados

### ✅ Relatórios
- Relatório financeiro completo (console e TXT)
- Relatório de inadimplência com taxa percentual
- Relatório de visitantes
- Consolidado de receitas e despesas
- Detalhamento de chamados de manutenção
- Balanço mensal do condomínio
- Lista de inadimplentes

### ✅ Persistência de Dados
- Todos os dados salvos em arquivos TXT
- Carregamento automático ao iniciar o sistema
- Salvamento automático ao sair
- Arquivos: `apartamentos.txt`, `moradores.txt`, `visitantes.txt`, `reservas.txt`, `chamados.txt`, `pagamentos.txt`

---

## 🏗️ Estrutura do Projeto e Princípios de POO

### Hierarquia de Classes

```
Pessoa (abstrata)
├── Morador
└── Visitante

AreaComum (abstrata)
├── Academia
├── Piscina
└── SalaoDeFestas
```

### Princípios Aplicados

**1. Encapsulamento**
- Todos os atributos privados com getters/setters apropriados
- Validações nos construtores e métodos
- Proteção de integridade dos dados

**2. Herança**
- `Pessoa` como classe base para `Morador` e `Visitante`
- `AreaComum` como classe base para áreas reserváveis
- Reutilização de código e atributos comuns

**3. Polimorfismo**
- Uso de enums para `AreaReservavel`, `StatusChamado`, `Status` de pagamento
- Métodos sobrescritos (`toString()`)
- Factory methods para criação de objetos

**4. Abstração**
- Classes abstratas `Pessoa` e `AreaComum`
- Separação de responsabilidades em classes específicas
- Interfaces claras entre módulos

---

## 🚀 Como Executar

### Pré-requisitos
- Java JDK 11 ou superior
- Terminal/Prompt de Comando

### Compilação

```bash
# No diretório raiz do projeto
javac src/*.java
```

### Execução

```bash
java -cp src SistemaCondominio
```

**Observação:** Na primeira execução, o sistema utilizará os dados pré-carregados de `apartamentos.txt` e `moradores.txt`. Arquivos ausentes serão criados automaticamente conforme você utiliza o sistema.

### Estrutura de Diretórios

```
POO-P2/
├── src/
│   ├── Academia.java
│   ├── Apartamento.java
│   ├── AreaComum.java
│   ├── AreaReservavel.java
│   ├── CampoInvalidoException.java
│   ├── ChamadoManutencao.java
│   ├── ControleFinanceiro.java
│   ├── ControleVisitante.java
│   ├── GerenciadorReservas.java
│   ├── MenuApartamentos.java
│   ├── MenuMoradores.java
│   ├── MenuPagamentos.java
│   ├── MenuReservas.java
│   ├── MenuVisitantes.java
│   ├── Morador.java
│   ├── OperacaoInvalidaException.java
│   ├── Pagamento.java
│   ├── Persistencia.java
│   ├── Pessoa.java
│   ├── Piscina.java
│   ├── RelatorioFinanceiro.java
│   ├── Reserva.java
│   ├── SalaoDeFestas.java
│   ├── SistemaCondominio.java
│   ├── StatusChamado.java
│   └── Visitante.java
├── apartamentos.txt (dados pré-carregados)
├── moradores.txt (dados pré-carregados)
├── visitantes.txt (gerado pelo sistema)
├── reservas.txt (gerado pelo sistema)
├── chamados.txt (gerado pelo sistema)
├── pagamentos.txt (gerado pelo sistema)
├── relatorio_financeiro.txt (gerado ao criar relatório)
└── README.md
```

---

## 🧪 Como Testar o Sistema

### Teste Rápido Completo:

1. **Compilar e executar:**
   ```bash
   javac src/*.java
   java -cp src SistemaCondominio
   ```

2. **Testar cada módulo:**
    - **Menu 1 (Moradores)**: Liste os 20 moradores pré-carregados
    - **Menu 2 (Apartamentos)**: Veja os 20 apartamentos em 3 blocos
    - **Menu 3 (Visitantes)**:
        - Registre entrada de um visitante
        - Registre saída
        - Gere o relatório de visitas
    - **Menu 4 (Reservas)**:
        - Crie uma reserva para a piscina
        - Tente criar outra no mesmo horário (deve dar conflito)
        - Cancele uma reserva
    - **Menu 5 (Pagamentos)**:
        - Registre um pagamento para um morador
        - Efetue o pagamento
        - Veja a lista de pagamentos atrasados
    - **Menu 6 (Manutenção)**:
        - Abra um chamado
        - Coloque em andamento
        - Feche com um custo (ex: 500.00)
    - **Menu 7 (Relatórios)**:
        - Gere o relatório financeiro no console
        - Salve em TXT
        - Veja o relatório de visitantes

3. **Testar persistência:**
    - Crie dados em cada módulo
    - Salve tudo (Menu principal → opção 8)
    - Feche o programa completamente
    - Abra novamente
    - Verifique se todos os dados foram mantidos

4. **Testar validações:**
    - Tente criar morador sem nome
    - Tente fechar chamado já fechado
    - Tente registrar saída de visitante que já saiu
    - Tente criar reserva no passado

---

## 📊 Tratamento de Erros

O sistema implementa tratamento robusto de erros:

- **Exceções personalizadas**:
    - `CampoInvalidoException`: Campos obrigatórios e validações
    - `OperacaoInvalidaException`: Operações em estados inválidos
- **Validações de entrada**:
    - Campos obrigatórios (nome, documento, telefone)
    - Formatos de data corretos
    - Valores numéricos válidos
    - CPF/documentos não vazios
- **Persistência segura**:
    - Tratamento de arquivos ausentes
    - Recuperação de dados corrompidos
    - Conversão segura de formatos (vírgula/ponto)
- **Regras de negócio**:
    - Conflitos de reserva (verificação de sobreposição)
    - Estados inválidos (ex: fechar chamado já fechado)
    - Horários válidos (8h às 22h para áreas comuns)
    - Datas no futuro para reservas

---

## 🎨 Decisões de Design

### Por que usar arquivos TXT?
- Simplicidade e portabilidade
- Fácil debug e inspeção manual dos dados
- Atende aos requisitos do projeto
- Não requer configuração de banco de dados
- Possibilidade futura de migração para SQL

### Separação em Menus
- Melhor organização e modularização do código
- Facilita manutenção e extensão
- Cada menu tem responsabilidade única (SRP)
- Reutilização de componentes
- Navegação intuitiva para o usuário

### Sistema de IDs Automáticos
- Previne duplicação de registros
- Facilita busca e referência entre objetos
- Mantém integridade referencial ao carregar dados
- Sequencial e previsível

### Factory Methods para Persistência
- Evita uso de reflexão (mais seguro e rápido)
- Construtores privados para restauração
- Métodos estáticos `restaurarDePersistencia()`
- Mantém encapsulamento e imutabilidade

---

## 🔧 Bibliotecas Utilizadas

**Apenas Java Standard Library** (nenhuma dependência externa):

- `java.io.*`: Persistência em arquivos (BufferedReader, BufferedWriter, FileReader, FileWriter)
- `java.util.*`: Estruturas de dados (List, ArrayList, Date, Calendar, Scanner)
- `java.text.SimpleDateFormat`: Formatação e parsing de datas
- `java.util.Locale`: Formatação de números (ponto decimal)

**Por que não usar bibliotecas externas?**
- Requisito do projeto de usar apenas persistência em arquivos
- Simplicidade e facilidade de execução
- Sem necessidade de gerenciar dependências
- Projeto acadêmico focado em POO

---

## 📈 Possíveis Melhorias Futuras

- [ ] Interface gráfica com JavaFX/Swing
- [ ] Geração de relatórios em PDF (usando iText)
- [ ] Integração com Google Drive para backup em nuvem
- [ ] Migração para banco de dados SQL (MySQL/PostgreSQL)
- [ ] Sistema de autenticação e permissões de usuários
- [ ] Notificações automáticas por email para:
    - Pagamentos vencidos
    - Lembretes de reservas
    - Confirmação de chamados fechados
- [ ] Dashboard com gráficos e estatísticas
- [ ] API REST para integração com aplicativos mobile
- [ ] Histórico de alterações (auditoria)
- [ ] Exportação de dados para Excel
- [ ] Backup automático agendado
- [ ] Sistema de agendamento de assembleias
- [ ] Controle de correspondências

---

## 👥 Autor(es)

**Nome:** José Francisco Paes Landim Sobrinho     
**Curso:** Engenharia de Software  
**Email:** jose.sobrinho@somosicev.com

**Nome:** João Guiherme Aragão Malta   
**Curso**: Engenharia de Software  
**Email:** joao.malta@somosicev.com

**Nome:** [Seu Nome Completo]**   
**Curso:** [Seu Curso]  
**Email:** [Seu email acadêmico]

**Nome:** [Seu Nome Completo]**   
**Curso:** [Seu Curso]  
**Email:** [Seu email acadêmico]

**Nome:** [Seu Nome Completo]**   
**Curso:** [Seu Curso]   
**Email:** [Seu email acadêmico]

---

## 📝 Licença

Este projeto foi desenvolvido exclusivamente para fins acadêmicos como parte da avaliação da disciplina de Programação Orientada a Objetos.

---

## 📞 Suporte

Para dúvidas sobre o projeto:
- Abra uma issue no repositório GitHub
- Entre em contato com o autor
- Consulte o professor responsável pela disciplina

Para reportar bugs ou sugerir melhorias, utilize o sistema de Issues do GitHub.

---

## 📚 Referências

- Documentação oficial do Java SE: https://docs.oracle.com/javase/
- Tutorial de POO em Java: https://docs.oracle.com/javase/tutorial/java/concepts/
- Effective Java (3rd Edition) - Joshua Bloch
- Material didático da disciplina
- Stack Overflow: https://stackoverflow.com/

---

**Data de Entrega:** 25-27 de Novembro de 2024  
**Instituição:** iCev - Instituto de Ensino Superior  
**Disciplina:** Programação Orientada a Objetos  
**Professor:** Samuel

---

## 🎯 Status do Projeto

✅ **Projeto Completo e Funcional**

- ✅ Todos os requisitos obrigatórios implementados
- ✅ Persistência funcionando corretamente
- ✅ Tratamento de erros robusto
- ✅ Código organizado e documentado
- ✅ Relatórios funcionais
- ✅ Testes realizados com sucesso
