package cc.lovesq.study.dsl

class ILoveYouEveryDay {

    static void main(args) {

        def i = { actby -> return actby("I") }
        def love = { me -> { you, freq -> "${me} love ${you} ${freq}" } }
        def you = { "you" }

        i love(you, "every day")

        //def iloveyou = i love you { every day }
        //println iloveyou

        def every = { interval ->

        }
        def day = {

        }
    }
}
