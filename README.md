# android-firebase
Este projeto é voltado aos iniciantes em Android. Tem como objetivo principal, ser apresentado no GDG - Campo Grande / LivingLab no dia 05 de Outubro de 2018

#### Criadores: Matheus Bristot e Gustavo Ross

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

![alt text](http://i.imgur.com/BsXHcqC.png)

Preencha:

![alt text](http://i.imgur.com/jV4npfx.png)

Resultado:

![alt text](http://i.imgur.com/Nqs5IzA.png)

Adicionando o Firebase ao App

Preencha os dados e clique em continuar e aparacerá este resultado:

![alt text](http://i.imgur.com/uv0cEJh.png)

![alt text](http://i.imgur.com/PhlyZxB.png)

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
https://console.firebase.google.com/u/0/project/SEU_PROJETO/authentication/users?hl=pt-br

Troque o SEU_PROJETO pelo id do projeto do firebase que você criou

Você clica em Configurar método de Login

![alt text](http://i.imgur.com/Hm4heiP.png)

Em Provedores de Login, escolha E-mail/senha e edite:
Ative a primeira opção, e clique em salvar.

![alt text](http://i.imgur.com/VHn5bZh.png)

Pronto authentication configurado

### Adicionando o Firebase Crashlytics no projeto

Abra o painel do Console Firebase e navegue na até opção de Qualidade e clique em Crashlytics

![alt text](http://i.imgur.com/mrPgPeO.png)

Geralmente, o Crashlytics é habilitado apenas para versão Release, mas no Workshop, vamos habilitar para o Debug também.

Então selecione o App Debug

![alt text](http://i.imgur.com/IkPgZmL.png)

Após isso clique em Configurar o Crashlytics

![alt text](http://i.imgur.com/vMYmvuo.png)

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

No inicio do arquivo, as primeiras linhas, adicione isto
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

![alt text](http://i.imgur.com/hkSSKYJ.png)

Acessando o painel de Crash

![alt text](http://i.imgur.com/0M1pfw2.png)

Abrindo a ocorrência, teremos mais detalhes, como esse exemplo:

![alt text](http://i.imgur.com/WvESZ4R.png)

Bom após removermos a linha que causa o crash. Devemos fechar o problema.

![alt text](http://i.imgur.com/1oxJ7DI.png)

Ficando assim

![alt text](http://i.imgur.com/3uzQznS.png)

...Se chegou até aqui sem problemas, seu Crashlytics está configurado e verificado.

### Configurar o Firebase Remote Config
No app/build.gradle adicione este código
```
implementation "com.google.firebase:firebase-config:$firebase_remote_config_version"
```
De aquele sync now!

Agora, abra o console do firebase e acesse Ampliar -> Remote Config

![alt text](http://i.imgur.com/sgGC4L4.png)

E clique em configurar

Depois disso você está pronto para criar:

Clique em adicionar parâmetro, e preencha os campos:

![alt text](http://i.imgur.com/QqCthts.png)

Agora vamos inicializar o Config Remote através do Application, no projeto existe o arquivo FirebaseAndroidApplication.kt. Abra ele
Basicamente é um exemplo bem básico, vamos setar valores padrões caso não encontre ao realizar o get em outro lugar
e setamos também o tempo de atualização, e através do activeFetched o app ativa a configuração de busca que copia valores armazenados nela para a configuração ativa.
```
val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        // Aqui você pode colocar itens default antes de ser publicado no Remote Config do Firebase
        val remoteConfigDefaults = mutableMapOf<String, Any>()
        remoteConfigDefaults[REMOTE_APP_VERSION] = "1.0.0"
        remoteConfigDefaults[REMOTE_FORCE_CHECK_VERSION] = false
        remoteConfigDefaults[REMOTE_DAILY_MESSAGE] = ""

        firebaseRemoteConfig.fetch(60) // a cada minuto vou buscar
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) firebaseRemoteConfig.activateFetched()
                }.addOnFailureListener { exception -> Crashlytics.logException(exception) }
    }
```

Agora vamos buscar os valores que estão no Firebase,
Primeiramente instanciamos o remote,
```
val remoteConfig = FirebaseRemoteConfig.getInstance()
```
Agora podemos pegar qualquer valor que estiver no Remote Config, baseado no tipo claro, Boolean escrevemos getBoolean, String escrevemos getString
Um exemplo:
```
val latestVersion = remoteConfig.getString(REMOTE_APP_VERSION)
```

Se você conseguiu pegar o REMOTE_APP_VERSION, seu Remote Config foi adicionado com sucesso.