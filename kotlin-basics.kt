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
                    "Hello! i am the return of the function"
                }
                print(theReturn)
            }
            Function.APPLY -> {
                /**
                 * This function is closely equals the RUN function, but don't return nothing!
                 */
                val theReturn = hero.apply {
                    print("$name: SMAAAASHHHH >:(")
                    "I am the last object, but i'll not be returned :( justice NOW!!"
                }
                print(theReturn)
            }
            Function.WITH -> {
                /**
                 * WITH is the same thing of APPLY function. But it is special... it DON'T need a object to be called, it
                 * is GLOBAL <3
                 */
                with(hero) {
                    print("$name: with big powers come big responsibilities")
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
                    "hello! thank you to return myself <3"
                }
                print(theReturn)
            }
            Function.ALSO -> {
                /**
                 * same thing of LET, but don't return nothing
                 */
                val theReturn = hero.also {
                    print(it.name)
                }
                print(theReturn)
            }
        }
    }
}

/**
 * CHAPTER II -> KOTLIN IN: USING THIS FUNCTIONS TO THE DARK SIDE >:)
 */



/**
 * A Simple class to represent us MARVEL heroes
 */
class Hero(val name: String, val power: String)

/**
 * WHY MYSTERIOUS?! this object can have NULL values. so we can know or not the name or power of a hero
 */
class AnMysteriousHero(val name: String?, val power: String?)

/**
 * An enum to choose which function will run
 */
enum class Function { RUN, APPLY, WITH, LET, ALSO }