<img width="120" height="120" alt="Design sem nome (4)" src="https://github.com/user-attachments/assets/e658796a-bd2b-415c-8715-e2b2226fe694" />


# 🎮 Pokédex Android

Aplicativo Android desenvolvido em Kotlin para consulta e exploração de informações sobre Pokémon, permitindo visualizar dados, estatísticas e características individuais de cada personagem.

O projeto foi desenvolvido com **Jetpack Compose** e utiliza uma arquitetura baseada em **MVVM**, com foco em separação de responsabilidades, consumo de API e testes automatizados.


## 🎯 Objetivos

O projeto foi desenvolvido para explorar:

* desenvolvimento de interfaces com Jetpack Compose;
* integração com APIs REST;
* gerenciamento de estado;
* separação de responsabilidades;
* injeção de dependências;
* testes unitários;
* automação de validações com GitHub Actions.

## 🏗️ Arquitetura

A aplicação utiliza **MVVM (Model-View-ViewModel)** para separar a apresentação, estado da interface e acesso aos dados.

```text
UI
↓
ViewModel
↓
Repository
↓
API REST
```

Essa organização facilita a manutenção e permite testar a lógica da aplicação de forma independente da interface.

## 🛠️ Tecnologias

* Kotlin
* Jetpack Compose
* Retrofit
* Kodein
* Voyager
* Coroutines
* JUnit
* MockK
* Coroutines Test
* Turbine
* GitHub Actions

## 🌐 Integração com API

O aplicativo utiliza uma API REST para obter os dados dos Pokémon.

O Retrofit é responsável pela comunicação HTTP e integração dos dados consumidos pela aplicação.

## 🧪 Testes

A aplicação possui testes unitários utilizando:

* JUnit
* MockK
* Coroutines Test
* Turbine

Essas ferramentas permitem testar comportamentos assíncronos, dependências simuladas e fluxos de estado.

## ⚙️ Integração contínua

O projeto possui workflow com GitHub Actions para automatizar a execução das validações e testes.

## 📱 Funcionalidades

* Consulta de Pokémon
* Visualização de detalhes
* Informações e estatísticas
* Navegação entre telas
* Interface desenvolvida com Jetpack Compose

## 📸 Screenshots

<p align="center">
  <img width="260" height="520" src="https://github.com/user-attachments/assets/ffbed20f-e757-441d-af9e-dde3225a072e" />   
  <img width="260" height="520" src="https://github.com/user-attachments/assets/beae2194-6415-4662-9cf5-a9f4c3e8c873" />
<p/> 

## ▶️ Como executar

Clone o repositório:

```bash
git clone https://github.com/LucasDSantosF/Pokedex.git
```

Abra o projeto no Android Studio e aguarde a sincronização do Gradle.

Depois execute o aplicativo em um dispositivo ou emulador Android compatível.

## 📌 O que este projeto demonstra

Este projeto demonstra experiência prática com:

* desenvolvimento Android moderno;
* Jetpack Compose;
* arquitetura MVVM;
* consumo de APIs REST;
* programação assíncrona;
* testes automatizados;
* integração contínua.

## 👤 Autor

**Lucas dos Santos Francisco**

[GitHub](https://github.com/LucasDSantosF) · [LinkedIn](https://www.linkedin.com/in/lucas-dos-santos-francisco/)

