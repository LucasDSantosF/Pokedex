<img width="120" height="120" alt="Design sem nome (4)" src="https://github.com/user-attachments/assets/e658796a-bd2b-415c-8715-e2b2226fe694" />

# Pokédex

**Pokédex** é um aplicativo de busca de Pokémons. Ao tocar em um Pokémon, você poderá ver mais detalhes de estatísticas e estilos de combate.

Totalmente desenvolvido em **Jetpack Compose**.

https://github.com/user-attachments/assets/23bfecea-ae93-4331-a649-f17c27c8f6ec

https://github.com/user-attachments/assets/27bc06d0-1169-4e50-a99f-bf6aca6ba65c

<img width="260" height="520" src="https://github.com/user-attachments/assets/20ad2717-b458-489b-b2bf-4a8704456cc8" />
<img width="260" height="520" src="https://github.com/user-attachments/assets/85c86d3c-ebf3-4396-8a39-caca497b35a9" />

## Estrutura e Decisões Técnicas

A arquitetura adotada foi a **MVVM**, que é a que possuo mais domínio.

Para a requisição de dados, a biblioteca **Retrofit** para implifica a comunicação com a API RESTful.

A **Injeção de Dependência** foi implementada com a biblioteca **Kodein**.

Para criação da **ViewModel** e facilitar a **Navegação** foi implementada a biblioteca **voyager**.

Implemetação de ***Workflow do GIthub Actions*** para verificar se os testes estão em funcionamento correto antes do Pull Request ser Merged.

Na criação de **testes unitarios**, as bibliotecas **Coroutines Test** manipular dispatcher e testar funções de suspensas, 
**Mockk** para criar objetos simulados (mocks) para substituir dependências reais, 
**Junit** para fornecer anotações e asserções para definir e executar os testes,
**Turbine** que permite testar fluxos de dados, como StateFlows ou SharedFlows.
