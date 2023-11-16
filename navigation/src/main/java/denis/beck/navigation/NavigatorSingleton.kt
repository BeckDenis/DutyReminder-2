package denis.beck.navigation

object NavigatorSingleton {

    lateinit var instance : Navigator
        private set

    fun init(navigator: Navigator) {
        instance = navigator
    }

}