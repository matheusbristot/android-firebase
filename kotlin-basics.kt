class IWantToKnow {

    /**
     * Here we'll use these functions
     */
    fun displayHeroWith(function: Function, hero: Hero) {
        when (function) {
            Function.RUN -> {
                /**
                 * the run function will run all instructions inside the block, providing for you an new THIS context.
                 * this new context is the object caller, in this case is us Hero object
                 * so you can call methods and properties from this object.
                 *
                 *
                 * after run your code, this method will return the last object of the block
                 */
                val theReturn = hero.run {
                    print("Hi! my name is $name and my super power is $power")
                    " -> Hello! i am the return of the function"
                }
                println(theReturn)
            }
            Function.APPLY -> {
                /**
                 * This function is closely equals the RUN function, but don't return nothing!
                 */
                val theReturn = hero.apply {
                    print("$name: SMAAAASHHHH >:(")
                    "I am the last object, but i'll not be returned :( justice NOW!!"
                }
                println(" -> $theReturn")
            }
            Function.WITH -> {
                /**
                 * WITH is the same thing of APPLY function. But it is special... it DON'T need a object to be called, it
                 * is GLOBAL <3
                 */
                with(hero) {
                    println("$name: with big powers come big responsibilities")
                }
            }
            Function.LET -> {
                /**
                 * this method will provide an IT object, that represent the object caller. If you prefer, you can easily give a
                 * name for it
                 *
                 * this method return the last object too :D
                 */
                val theReturn = hero.let { myAmazingHero ->
                    print(myAmazingHero.name)
                    " -> hello! thank you to return myself <3"
                }
                println(theReturn)
            }
            Function.ALSO -> {
                /**
                 * same thing of LET, but don't return nothing
                 */
                val theReturn = hero.also {
                    println(it.name)
                }
                println(theReturn)
            }
        }
    }

    /**
     * CHAPTER II -> KOTLIN IN: USING THIS FUNCTIONS TO THE DARK SIDE >:)
     */
    fun theMysteriousHero(mysteriousHero: MysteriousHero?) {
        var mysteriousHero = mysteriousHero
        /**
         * this block will be executed like a charm
         */
        mysteriousHero?.apply {
            println(name)
            println(power)
        }
        /**
         * iron man is death now!
         */
        mysteriousHero = null

        mysteriousHero?.apply {
            /**
             * theese lines will not be runned
             */
            println(name)
            println(power)
        }

        /**
         * in java, will be like this
         */
        if (mysteriousHero != null) {
            println(mysteriousHero.name)
            println(mysteriousHero.power)
        }

        /**
         * not so good... :(
         * so.... use the dark side power that kotlin provides for you! :D
         */
    }

    fun combineOperators(mysteriousHero: MysteriousHero?) {
        /**
         * Kotlin version
         */
        mysteriousHero?.apply {
            name?.run { capitalize() }?.also { it.toUpperCase() }
            power?.let {
                it.toCharArray()
                        .map { it.toUpperCase() }
                        .also {
                            println("Kotlin Version: $it") }
            }
        }

        /**
         * Java version
         */

        if (mysteriousHero != null) {
            if (mysteriousHero.name != null) {
                val capitalizedName = mysteriousHero.name!!.capitalize()
                capitalizedName.toUpperCase()
            }
            val chars: MutableList<Char> = mutableListOf()
            if (mysteriousHero.power != null) {
                for (item in mysteriousHero.power!!.toCharArray()) {
                    chars.add(item.toUpperCase())
                }
                print("Java Version: $chars")
            }
        }
    }
}


fun main(args: Array<String>) {
    val knowledge = IWantToKnow()
    /**
     * Joking with operators, just uncomment
     */
//    with(knowledge) {
//        displayHeroWith(Function.APPLY, Hero("Hulk", "SMASH"))
//        displayHeroWith(Function.RUN, Hero("Thor", "be a god"))
//        displayHeroWith(Function.LET, Hero("Hulk", "SMASH"))
//        displayHeroWith(Function.WITH, Hero("Spider - man", "force and also the webs"))
//    }

    /**
     * Same results, but different implementations, just uncomment
     */
//    knowledge.combineOperators(MysteriousHero("Captain America", "shield and force"))

    /**
     * only showing one of the usability of these operators, just uncomment
     */

//    knowledge.theMysteriousHero(MysteriousHero("Iron man", "money"))

}

/**
 * A Simple class to represent us MARVEL heroes
 */
class Hero(var name: String, var power: String)

/**
 * WHY MYSTERIOUS?! this object can have NULL values. so we can know or not the name or power of a hero
 */
class MysteriousHero(var name: String?, var power: String?)

/**
 * An enum to choose which function will run
 */
enum class Function { RUN, APPLY, WITH, LET, ALSO }
