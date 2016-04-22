package scalastudy.utils

import traits.FileAbility

import scalastudy.traits.LinePrintHandler

/**
 * Created by lovesqcc on 16-4-16.
 */
object DefaultFileUtil extends FileAbility with LinePrintHandler {

    def main (args: Array[String]){
        testExtraFilename
    }

}
