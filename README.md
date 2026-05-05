[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![JSON](https://img.shields.io/badge/JSON-555555?style=for-the-badge&logo=json&logoColor=white)
![Shared Preferences](https://img.shields.io/badge/SharedPreferences-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

# **Álcool ou Gasolina**
[O Álcool ou Gasolina é um aplicativo Android desenvolvido para auxiliar motoristas na escolha mais econômica entre álcool (etanol) e gasolina. O projeto foca em boas práticas de UI/UX com Jetpack Compose, persistência de dados local e suporte a múltiplos idiomas. Este projeto faz parte da disciplina de Desenvolvimento de Aplicações Móveis do curso de Sistemas e Mídias Digitais da UFC.](https://docs.google.com/document/d/1w0d_LjN0XMPjgUnryiA8Be6PWIPJKwyQBMC9q4_rzC8)

## **Requisitos Iniciais**
Para o desenvolvimento deste projeto, foram estabelecidos os seguintes requisitos funcionais e técnicos:
* Cálculo de Eficiência: Realizar o cálculo comparativo baseando-se na proporção de preço entre os combustíveis;
* Persistência de Dados: Salvar um histórico de postos consultados, incluindo nome do estabelecimento e preços praticados;
* Geolocalização: Permitir, capturar e armazenar as coordenadas (latitude e longitude) no momento do cadastro do posto para visualização posterior em mapas;
* Internacionalização (i18n): Suporte completo para os idiomas Português (Brasil) e Inglês (EUA), adaptando labels, mensagens e símbolos de moeda ($ vs R$);
* Gerenciamento de Estado: Utilizar o paradigma declarativo do Jetpack Compose para lidar com fluxos de navegação e formulários.

## **Funcionalidades**
* Listagem de Postos: Visualização de todos os postos salvos com resumo de preços;
* Cálculo Dinâmico: Opção de calcular apenas o rendimento ou salvar os dados no banco de dados local;
* Integração com Mapas: Botão para abrir a localização exata do posto em aplicativos de mapa externos;
* CRUD Completo: Possibilidade de inserir, visualizar detalhes, editar e excluir registros de postos.

## **Tecnologias Utilizadas**
* Linguagem: Kotlin;
* UI Framework: Jetpack Compose (Material Design 3);
* Navegação: Compose Navigation com rotas tipadas;
* Localização: Fused Location Provider API;
* Persistência: SharedPreferences com Serialização JSON.

### **Permissão de Localização**
Ao iniciar o aplicativo, é necessário autorizar o acesso à localização do dispositivo.
![Authorization](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/1.png)

### **Página Inicial**
Após autorização de permissão, o aplicativo carrega a página com a lista de postos cadastrados e o botão de cadastro.
![Lista](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/2.png)

### **Cadastro e cálculo**
Ao clicar no botão flutuante "+", uma nova tela é carregada com um formulário de preços e nome do posto. O primeiro botão apenas realiza o cálculo e o segundo botão salva o posto e a localização. Também é possível retornar à pagina anterior com o botão de seta para esquerda.
![Form](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/3.png)
![Calcular](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/4.png)


### **Lista de Postos populada**
Ao clicar no botão de salvar, a página de lista inicial é retornada com o posto cadastrado persistido.
![Cadastrar](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/5.png)

### **Editar um posto**
Ao clicar em um dos postos cadastrados, é possível ver a localização cadastrada do posto no maps; é possível editar; e é possível deletar o cadastro.
![Editar1](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/6.png)
![Editar2](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/7.png)
![Mapa](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/8.png)

### **Exemplo de novo cadastro**
![Segundo1](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/9.png)
![Segundo2](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/10.png)

### **Mudança da linguagem para o português**
![Portugues1](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/11.png)
![Portugues2](https://raw.githubusercontent.com/anebarbosa/alcool_ou_gasolina/refs/heads/main/12.png)
