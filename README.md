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
