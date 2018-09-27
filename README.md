# android-firebase
Este projeto é voltado aos iniciantes em Android. Tem como objetivo principal, ser apresentado no GDG - Campo Grande / LivingLab no dia 05 de Outubro de 2018

## Branch: feature/add-dependencies

Esta branch é desenvolvida a configuração básica do gradle.build do root do projeto e também no build/app, seguindo alguns padrões.

### Pré requisitos

Você tenha clonado o projeto em sua máquina e dado checkout na branch:

```
git checkout feature/add-dependencies
```

## Branch: feature/create-main-activity

Esta branch é desenvolvida a Main Activity e algumas extensions foram criadas para um desenvolvimento mais rápido durante nosso Workshop.

### Pré requisitos

- Databinding, saber como inicializar na classe
- No Databinding ainda, como passar seu callback no xml.
- ViewModel

```
private lateinit var binding: ActivityMainBinding
```

### Databinding

```
1. private lateinit var binding: ActivityMainBinding
2. No onCreate: binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
3. Ainda no onCreate, é recomendável neste ciclo de vida ok? binding.main = this
```

#### Criamos um método público para capturar o evento de click no textView

```
fun onClickText(view: View)
```

#### No activity_main.xml, após abertura do <layout>

Nomeamos como main, todas bagaça que existe na classe MainActivity, main.joazinho, main.vouembora ...

```
<data>
    <variable
        name="main"
        type="matheusbristot.firebaseandroid.presentation.main.MainActivity" />
</data>
```

#### Bom, como vamos capturar o evento de click do textView, adicionamos esta linha

```
android:onClick="@{main::onClickText}"
```

Viu alguma semelhança? Rsss. Demos um link onClickText no nosso método que vai reproduzir um Toast.


## Configurando o Firebase no projeto:

Acesse
```
https://console.firebase.google.com/u/0/?hl=pt-br
```

Clique aqui:
```
![alt text](http://i.imgur.com/BsXHcqC.png)
```

Preencha
```
http://i.imgur.com/jV4npfx.png
```

Resultado:
```
http://i.imgur.com/Nqs5IzA.png
```

Adicionando o Firebase ao App
Preencha os dados e clique em continuar e aparacerá este resultado:
```
http://i.imgur.com/uv0cEJh.png
http://i.imgur.com/PhlyZxB.png
```
Lembre-se de baixar o google-services.json e adicionar no <projecto>/app/

Após isso, adicione isto, no arquivo <projeto>build.gradle, ou melhor, no módulo FirebaseAndroid:
```
dependencies {
    ...
    classpath "com.google.gms:google-services:$gms_version"
}
```
Agora, vamos colocar isto no build.gradle no nível do App ok? No módulo app

```
dependencies {
    ...
    implementation "com.google.firebase:firebase-core:$firebase_core_version"
}
```
E na última linha do arquivo coloque isto:
```
apply plugin: 'com.google.gms.google-services'
```

Após toda a configuração, você irá colocar isto no onCreate do LoginActivity:

```
FirebaseApp.initializeApp(this)
```

Agora no Logcat deverá aparecer algo parecido com isto:

```
I/FirebaseInitProvider: FirebaseApp initialization successful
```

Configurar o Firebase Authentication:
https://console.firebase.google.com/u/0/project/<firebase-project>/authentication/users?hl=pt-br , troque o <firebase-project> pelo id do projeto do firebase que você criou

Você clica em Configurar método de Login
```
http://i.imgur.com/Hm4heiP.png
```

Em Provedores de Login, escolha E-mail/senha e edite:
Ative a primeira opção, e clique em salvar.
```
http://i.imgur.com/VHn5bZh.png
```
Pronto authentication configurado

### Adicionando o Firebase Crashlytics no projeto

Abra o painel do Console Firebase e navegue na até opção de Qualidade e clique em Crashlytics
```
http://i.imgur.com/mrPgPeO.png
```

Geralmente, o Crashlytics é habilitado apenas para versão Release, mas no Workshop, vamos habilitar para o Debug também.

Então selecione o App Debug
```
http://i.imgur.com/IkPgZmL.png
```

Após isso clique em Configurar o Crashlytics

```
http://i.imgur.com/vMYmvuo.png
```
Clique em Next (ou Próximo)

Abra a documentação sugerida no site

Vamos seguir o passo a passo e vamos mudar apenas uma etapa da configuração baseada na documentação beleza?

Passo 1: Coloque esta linha no ciclo de vida onCreate de qualquer View que você vá acessar durante o Workshop, eu coloquei no LoginActivity.kt
```
Crashlytics.getInstance().crash()
```

Passo 2: Agora vamos adicionar no AndroidManifest.xml, do módulo app, para ativação do modo de depuração do Crashlytics

Dentro da tag de <application> coloque isto:
```
<meta-data
            android:name="firebase_crashlytics_collection_enabled"
            android:value="@bool/enable_crashlytics" />
```

Passo 3: Adicionar no app/build.gradle o boolean enable_crashlytics:

Para cada Build Variants você irá adicionar algo semelhante a isso, se você quer habilitar para .debug, .staging, .release

```
resValue "bool", "enable_crashlytics", "true"
```
Se você não quer habilitar para determinada build:
```
resValue "bool", "enable_crashlytics", "false"
```

Passo 4: Ainda no app/build.gradle, vamos adicionar as dependencias para usar o Crashlytics :D

no inicio do arquivo, as primeiras linhas, adicione isto
```
apply plugin: 'io.fabric'
```

E nas dependencias, dentro do bloco dependencies:
```
implementation "com.crashlytics.sdk.android:crashlytics:$crashlytics_version"
```

Passo 5: Agora no <project>/build.gradle vamos adicionar as dependencias:
Dentro de buildscript { repositories {
Coloque isso:
```
 maven {
           url 'https://maven.fabric.io/public'
        }
```

Agora dentro de buildscript { dependencies {
Coloque isso:
```
classpath "io.fabric.tools:gradle:$fabric_tools_version"
```

Agora dentro de allproject { repositories {
Coloque isso:
```
maven {
            url 'https://maven.google.com/'
        }
```
Agora aperte em Sync Now

Quando você fazer todos os passos, e abrir seu app, e entrar na View que irá ocorrer o crash (forçadamente), você deverá receber em instantes um email parecido com isto.
```
http://i.imgur.com/hkSSKYJ.png
```

Acessando o painel de Crash
```
http://i.imgur.com/0M1pfw2.png
```

Abrindo a ocorrência, teremos mais detalhes, como esse exemplo:
```
http://i.imgur.com/WvESZ4R.png
```

Bom após removermos a linha que causa o crash. Devemos fechar o problema.
```
http://i.imgur.com/1oxJ7DI.png
```

Ficando assim
```
http://i.imgur.com/3uzQznS.png
```

...Se chegou até aqui sem problemas, seu Crashlytics está configurado e verificado.