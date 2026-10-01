ESPECIFICAÇÃO DO PROJETO
Bluetooth Scanner
1. Identificação
Nome: Bluetooth Scanner
Tema: B — Bluetooth Scanner
Curso: Licenciatura em Administração de Sistemas de Informação e Redes (LASIR)
Instituição: Universidade São Tomás de Moçambique (USTM)
Turma: 3L6LASIR 2T
Discente: Ayrine Guivala 202400425, Elvira Mabunda 202400938, e Hélvio Mabjaia 202402490 
Plataforma: Android
Linguagem: Java
________________________________________
2. Objetivo Geral
Desenvolver uma aplicação Android capaz de procurar dispositivos Bluetooth próximos, identificar os dispositivos encontrados e apresentar informações básicas sobre cada dispositivo.
________________________________________
3. Objetivos Específicos
A aplicação deve permitir:
1.	Procurar dispositivos Bluetooth próximos;
2.	Identificar dispositivos encontrados;
3.	Apresentar o nome do dispositivo;
4.	Apresentar o endereço MAC;
5.	Permitir filtrar os resultados pelo nome;
6.	Realizar novas pesquisas;
7.	Utilizar corretamente as permissões Bluetooth;
8.	Implementar pelo menos duas Activities;
9.	Utilizar Intent para comunicação entre Activities;
10.	Utilizar ConstraintLayout e Widgets.
________________________________________
4. Funcionalidades
4.1 Inserção do nome
Na tela inicial existe um campo onde o utilizador pode informar o nome de um dispositivo Bluetooth.
A inserção do nome é utilizada como critério de filtro.
________________________________________
4.2 Início da pesquisa
Ao pressionar o botão:
INICIAR SCANNER
a aplicação abre a tela do scanner utilizando um Intent.
________________________________________
4.3 Descoberta Bluetooth
A ScannerActivity utiliza a API Bluetooth do Android para iniciar uma descoberta de dispositivos próximos.
Os dispositivos encontrados são recebidos através de um BroadcastReceiver.
________________________________________
4.4 Informação dos dispositivos
Cada dispositivo encontrado é apresentado na lista contendo:
Nome do dispositivo
MAC: endereço MAC
________________________________________
4.5 Filtro
Quando o utilizador informa um nome na tela inicial, a aplicação compara o nome informado com o nome dos dispositivos encontrados.
Os dispositivos que não correspondem ao filtro não são adicionados à lista.
A comparação é realizada sem diferenciar letras maiúsculas de minúsculas.
________________________________________
4.6 Nova pesquisa
O botão:
NOVA PROCURA
permite cancelar uma pesquisa anterior, limpar os resultados e iniciar uma nova descoberta Bluetooth.
________________________________________
5. Activities
5.1 MainActivity
Função
Controlar a tela inicial e iniciar a ScannerActivity.
Elementos
•	TextView — título;
•	TextView — descrição;
•	EditText — nome do dispositivo;
•	Button — iniciar scanner.
Responsabilidade
Recolher o nome introduzido pelo utilizador e enviá-lo para a ScannerActivity.
________________________________________
5.2 ScannerActivity
Função
Executar a pesquisa Bluetooth e apresentar os resultados.
Elementos
•	TextView — título;
•	TextView — estado da pesquisa;
•	Button — nova procura;
•	ListView — dispositivos encontrados.
Responsabilidade
Controlar todo o processo de descoberta Bluetooth.
________________________________________
6. Navegação
A navegação entre as Activities ocorre da seguinte forma:
┌─────────────────────┐
│    MainActivity     │
│                     │
│ Nome do dispositivo │
│                     │
│ INICIAR SCANNER     │
└──────────┬──────────┘
           │
           │ Intent
           ↓
┌─────────────────────┐
│  ScannerActivity    │
│                     │
│ Scanner Bluetooth   │
│                     │
│ NOVA PROCURA        │
│                     │
│ Lista de dispositivos│
└─────────────────────┘
________________________________________
7. Intent
A MainActivity cria um Intent para abrir a ScannerActivity.
O nome informado pelo utilizador é enviado através de:
intent.putExtra(
    "NOME_DISPOSITIVO",
    nomeDispositivo
);
Na ScannerActivity o valor é recuperado através de:
getIntent().getStringExtra("NOME_DISPOSITIVO");
________________________________________
8. Interface
As interfaces são construídas utilizando:
ConstraintLayout
O projeto utiliza diferentes Widgets Android.
Widgets principais
Widget	Utilização
TextView	Apresentar textos
EditText	Introduzir o nome
Button	Executar ações
ListView	Apresentar dispositivos
________________________________________
9. Comunicação Bluetooth
A aplicação utiliza a funcionalidade Bluetooth do próprio dispositivo Android.
O processo utiliza:
BluetoothAdapter
        ↓
startDiscovery()
        ↓
BroadcastReceiver
        ↓
BluetoothDevice
        ↓
Nome + MAC
        ↓
ListView
A descoberta é realizada através do hardware Bluetooth do dispositivo físico.
________________________________________
10. Permissões
O projeto declara permissões Bluetooth no AndroidManifest.xml.
As principais permissões utilizadas são:
android.permission.BLUETOOTH
android.permission.BLUETOOTH_ADMIN
android.permission.BLUETOOTH_SCAN
android.permission.BLUETOOTH_CONNECT
android.permission.ACCESS_FINE_LOCATION
O código verifica as permissões antes de iniciar a descoberta.
________________________________________
11. Segurança e compatibilidade
A aplicação verifica as permissões necessárias conforme a versão do Android.
Em versões modernas do Android são utilizadas permissões específicas para operações de Bluetooth.
A aplicação também verifica se o Bluetooth está disponível e ativado.
________________________________________
12. Fluxo de Funcionamento
Utilizador
    ↓
Abre a aplicação
    ↓
Introduz nome (opcional)
    ↓
Pressiona INICIAR SCANNER
    ↓
MainActivity cria Intent
    ↓
ScannerActivity é aberta
    ↓
Verificação das permissões
    ↓
Verificação do Bluetooth
    ↓
Bluetooth inicia descoberta
    ↓
Dispositivos são encontrados
    ↓
BroadcastReceiver recebe os dispositivos
    ↓
Filtro é aplicado
    ↓
Nome + MAC são apresentados
    ↓
Utilizador pode iniciar NOVA PROCURA
________________________________________
13. Requisitos Funcionais
Código	Requisito
RF01	O sistema deve permitir iniciar uma pesquisa Bluetooth.
RF02	O sistema deve procurar dispositivos próximos.
RF03	O sistema deve apresentar o nome do dispositivo.
RF04	O sistema deve apresentar o endereço MAC.
RF05	O sistema deve permitir introduzir um nome para filtragem.
RF06	O sistema deve permitir realizar uma nova pesquisa.
RF07	O sistema deve verificar as permissões Bluetooth.
RF08	O sistema deve verificar se o Bluetooth está ativado.
________________________________________
14. Requisitos Não Funcionais
Código	Requisito
RNF01	A aplicação deve possuir uma interface simples e organizada.
RNF02	A aplicação deve utilizar ConstraintLayout.
RNF03	A aplicação deve ser desenvolvida em Java.
RNF04	A aplicação deve possuir pelo menos duas Activities.
RNF05	A aplicação deve utilizar Intent para navegação.
RNF06	A aplicação deve solicitar as permissões necessárias.
RNF07	A aplicação deve apresentar os resultados de forma compreensível.
________________________________________
15. Tecnologias
•	Java;
•	Android Studio;
•	Android SDK;
•	Bluetooth API;
•	ConstraintLayout;
•	Intent;
•	BroadcastReceiver;
•	ListView;
•	ArrayAdapter;
•	Git;
•	GitHub.
________________________________________
16. Testes Realizados
A aplicação foi testada em dispositivo Android físico.
Teste 1 — Inicialização
Resultado: aplicação inicia corretamente.
Teste 2 — Navegação
Resultado: MainActivity abre ScannerActivity através de Intent.
Teste 3 — Pesquisa Bluetooth
Resultado: dispositivos Bluetooth próximos são encontrados.
Teste 4 — Informação do dispositivo
Resultado: nome e endereço MAC são apresentados.
Teste 5 — Nova pesquisa
Resultado: o botão NOVA PROCURA limpa os resultados anteriores e realiza uma nova pesquisa.
Teste 6 — Permissões
Resultado: o Android solicita as permissões necessárias para utilização do Bluetooth.
________________________________________
17. Execução
Para executar o projeto:
1.	Abrir o projeto no Android Studio;
2.	Aguardar a sincronização do Gradle;
3.	Conectar um dispositivo Android;
4.	Ativar a depuração USB;
5.	Executar a aplicação;
6.	Autorizar as permissões solicitadas;
7.	Ativar o Bluetooth;
8.	Pressionar INICIAR SCANNER;
9.	Aguardar os dispositivos aparecerem.
________________________________________
18. Conclusão
O projeto implementa uma aplicação Android funcional para descoberta de dispositivos Bluetooth.
Foram aplicados conceitos de desenvolvimento Android, programação Java, Activities, Intent, Widgets, ConstraintLayout, permissões e comunicação Bluetooth.
A aplicação permite realizar pesquisas reais através do Bluetooth do dispositivo, apresentar informações dos dispositivos encontrados e realizar novas pesquisas.

